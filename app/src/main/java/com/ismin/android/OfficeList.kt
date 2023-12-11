package com.ismin.android

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

class OfficeList {

    var storage = HashMap<String, Office>()

    fun addOffice(office: Office) {
        storage[office.libele] = office
    }

    fun addOffices(offices : List<Office>){
        offices.forEach{office -> addOffice(office)}
    }

    fun getOffice(name: String): Office {
        val office = storage[name]
        if (office == null) {
            throw IllegalArgumentException("Unknown name")
        }
        return office
    }

    fun removeOffice(office: Office) {
        storage.remove(office.libele)
    }

    fun getAllOffices(): ArrayList<Office> {
        return  ArrayList(storage.values
            .sortedBy { office -> office.libele })
    }

    fun getOfficesOf(type: String): OfficeList {
        val storage_temp =  storage.filterValues { office -> office.caracteristique.equals(type) }
        val temp_list = OfficeList()
        temp_list.storage = storage_temp as HashMap<String, Office>
        return temp_list
    }

    fun getTotalNumberOfOffices(): Int {
        return storage.size
    }

    fun getOfficesInArea(longitude: Float, latitude: Float, dist_limit: Double): List<Office> {

        return storage.filterValues {office ->
            SphericalUtil.computeDistanceBetween(LatLng(office.lat.toDouble(), office.long.toDouble()), LatLng(latitude.toDouble(), longitude.toDouble())) < 1000000.0 * dist_limit }
            .values
            .sortedBy { office -> office.libele }
    }

    fun clear() {
        storage.clear()
    }
}


