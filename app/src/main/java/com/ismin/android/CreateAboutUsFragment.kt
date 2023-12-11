package com.ismin.android

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.lang.IllegalStateException

class CreateAboutUsFragment : Fragment() {


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
        val view = inflater.inflate(R.layout.fragment_about_us, container, false)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OfficeCreator) {
            this.listener = context;
        } else {
            throw IllegalStateException("$context must implement Abous Us")
        }
    }
}