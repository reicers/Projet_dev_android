package com.ismin.android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val SERVER_BASE_URL = "https://app-ecb49171-fd94-4e48-be45-fce3d715948f.cleverapps.io/" +
        ""

class MainActivity : AppCompatActivity(), OfficeCreator {
    //Main activity with 3 fragment
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val office = it.data?.getSerializableExtra(CREATED_BOOK) as Office
                Toast.makeText(this, "Office to place:$office", Toast.LENGTH_LONG).show()
            }
        }
    private var office_list = OfficeList()

    private var json = ""

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    private val officeService = retrofit.create(OfficeService::class.java)

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_create_office)
    }

    private val floatingOfficeListButton: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_list_office)
    }

    private val floatingAboutUsButton: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_about_us)
    }

    private val floatingMapButton: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_map)
    }

    private val floatingRefreshButton: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_refresh)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Let's get the data from the server dataset
        if(office_list.storage.size == 0) {

            officeService.findOffice()
                .enqueue(object : Callback<List<Office>> {
                    override fun onResponse(
                        call: Call<List<Office>>,
                        response: Response<List<Office>>
                    ) {
                        val allOffices: List<Office> = response.body()!!
                        office_list.addOffices(allOffices)
                        displayListFragment()
                    }

                    override fun onFailure(call: Call<List<Office>>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }

        floatingOfficeListButton.setOnClickListener {
            displayListFragment()
        }

        floatingActionButton.setOnClickListener {
            displayCreateOfficeFragment()
        }

        floatingAboutUsButton.setOnClickListener {
            displayAboutUsFragment()
        }

        floatingRefreshButton.setOnClickListener {
            refreshFragment()
        }

        floatingMapButton.setOnClickListener {

            val intent = Intent(this, MapActivity::class.java)
            var tempList = OfficeList()
            tempList.addOffices(office_list.getOfficesInArea(43.4525982F, 5.4717363F, 100.0).take(1000))
            office_list = OfficeList()
            displayListFragment()

            val array: Array<Pair<String, Office>> = tempList.storage.toList().toTypedArray();

            //val array: Array<Pair<String, Office>> = office_list.storage.toList().toTypedArray();
            intent.putExtra("offices", array);
            startForResult.launch(intent)
        }

    }

    //Buttons allow smooth navigation between each fragments (they disapear on their own fragment)
    private fun displayListFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.a_main_lyt_fragment,
            OfficeListFragment.newInstance(office_list.getAllOffices())
        )
        transaction.commit()
        floatingOfficeListButton.visibility = View.GONE
        floatingAboutUsButton.visibility = View.VISIBLE
        floatingActionButton.visibility = View.VISIBLE
        floatingMapButton.visibility = View.VISIBLE
        floatingRefreshButton.visibility = View.VISIBLE
    }

    private fun displayCreateOfficeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.a_main_lyt_fragment,
            CreateOfficeFragment()
        )
        transaction.commit()
        floatingOfficeListButton.visibility = View.VISIBLE
        floatingActionButton.visibility = View.GONE
        floatingAboutUsButton.visibility = View.VISIBLE
        floatingMapButton.visibility = View.GONE
        floatingRefreshButton.visibility = View.GONE
    }

    private fun displayAboutUsFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.a_main_lyt_fragment,
            CreateAboutUsFragment()
        )
        transaction.commit()
        floatingOfficeListButton.visibility = View.VISIBLE
        floatingAboutUsButton.visibility = View.GONE
        floatingActionButton.visibility = View.VISIBLE
        floatingMapButton.visibility = View.GONE
        floatingRefreshButton.visibility = View.GONE

    }


    private fun refreshFragment() {
        //refresh fragment
        officeService.findOffice()
            .enqueue(object : Callback<List<Office>> {
                override fun onResponse(
                    call: Call<List<Office>>,
                    response: Response<List<Office>>
                ) {
                    val allOffices: List<Office> = response.body()!!
                    office_list.addOffices(allOffices)
                    displayListFragment()
                }

                override fun onFailure(call: Call<List<Office>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                office_list.clear()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onOfficeCreated(office: Office) {
        officeService.createBook(office)
            .enqueue {
                onResponse = {
                    val officeFromServer: Office? = it.body()
                    office_list.addOffice(officeFromServer!!)
                    displayListFragment()
                }

                onFailure = {
                    Toast.makeText(this@MainActivity, it?.message, Toast.LENGTH_SHORT).show()
                }
            }

        displayListFragment()
    }
}