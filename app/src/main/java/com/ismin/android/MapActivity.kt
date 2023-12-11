package com.ismin.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


val CREATED_BOOK = "CREATED_BOOK"

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = SupportMapFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.a_main_lyt, mapFragment)
            .commit()

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap){

        val office_list = intent.getSerializableExtra("offices") as Array<Pair<Int, Office>>?
        val office_temp = office_list
        if (office_temp != null) {
            //add all offices received
        for(office : Pair<Int, Office> in office_temp){
            map.addMarker(
            MarkerOptions()
                        .position(LatLng(office.second.lat.toDouble(), office.second.long.toDouble()))
                        .title(office.second.libele).snippet(office.second.caracteristique)
                )
        }
            map.moveCamera(CameraUpdateFactory.zoomTo(14f))
            map.moveCamera(
                CameraUpdateFactory.newLatLng(
                LatLng(
                    office_temp[0].second.lat.toDouble(),
                    office_temp[0].second.long.toDouble())))
        }




    }
}