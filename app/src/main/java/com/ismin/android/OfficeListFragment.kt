package com.ismin.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_OFFICES = "param1"

class OfficeListFragment : Fragment() {
    private var offices: ArrayList<Office> = arrayListOf()
    private lateinit var officeAdapter: OfficeAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            offices = it.getSerializable(ARG_OFFICES) as ArrayList<Office>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_office_list, container, false)

        recyclerView = view.findViewById(R.id.f_office_list_rcv_offices)
        officeAdapter = OfficeAdapter(offices)
        recyclerView.adapter = officeAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(offices: ArrayList<Office>) =
            OfficeListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_OFFICES, offices)
                }
            }
    }
}