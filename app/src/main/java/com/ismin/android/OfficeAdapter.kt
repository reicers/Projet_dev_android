package com.ismin.android

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OfficeAdapter(private var offices: List<Office>) : RecyclerView.Adapter<OfficeViewHolder>() {

    val liste_office_favorite = OfficeList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeViewHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_office, parent, false)
        return OfficeViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: OfficeViewHolder, position: Int) {
        val office = offices[position]
        holder.txvName.text = office.libele
        holder.txvType.text = office.caracteristique
        holder.txvLong.text = office.long.toString()
        holder.txvLat.text = office.lat.toString()

        if(liste_office_favorite.storage.get(office.libele) != null){
            holder.btnFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD700")))
        }
        else{
            holder.btnFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#959595")))
        }

        holder.btnFavorite.setOnClickListener{
            if(liste_office_favorite.storage.get(office.libele) == null){
                holder.btnFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD700")))
                liste_office_favorite.addOffice(office)
            }
            else{
                holder.btnFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#959595")))
                liste_office_favorite.removeOffice(office)
            }
        }
    }

    override fun getItemCount(): Int {
        return offices.size
    }

    fun updateOffices(allOffices: List<Office>) {
        offices = allOffices
        notifyDataSetChanged()
    }

}