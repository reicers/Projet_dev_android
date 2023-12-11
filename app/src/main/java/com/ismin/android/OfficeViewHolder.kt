package com.ismin.android

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OfficeViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    var txvName = rootView.findViewById<TextView>(R.id.r_office_txv_name)
    var txvType = rootView.findViewById<TextView>(R.id.r_office_txv_type)
    var txvLong = rootView.findViewById<TextView>(R.id.r_office_txv_long)
    var txvLat = rootView.findViewById<TextView>(R.id.r_office_txv_lat)

    var btnFavorite = rootView.findViewById<FloatingActionButton>(R.id.r_office_btn_favorite)
}