package com.ismin.android

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class CreateOfficeFragment : Fragment() {


    private lateinit var listener: OfficeCreator
    private lateinit var edtName: EditText;
    private lateinit var edtType: EditText;
    private lateinit var edtLong: EditText;
    private lateinit var edtLat: EditText;

    private lateinit var btnSave: Button;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_office, container, false)


        edtName = view.findViewById(R.id.f_create_office_edt_name)
        edtType = view.findViewById(R.id.f_create_office_edt_type)
        edtLong = view.findViewById(R.id.f_create_office_edt_long)
        edtLat = view.findViewById(R.id.f_create_office_edt_lat)

        btnSave = view.findViewById(R.id.f_create_office_btn_save)
        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val type = edtType.text.toString()
            val long = edtLong.text.toString().toFloat()
            val lat = edtLat.text.toString().toFloat()

            val office = Office(lat, long, name, type)
            //this.listener.onOfficeCreated(office)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OfficeCreator) {
            this.listener = context;
        } else {
            throw IllegalStateException("$context must implement OfficeCreator")
        }
    }
}