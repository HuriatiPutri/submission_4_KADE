package com.fgt.myapplication

import com.fgt.myapplication.model.DetailEvent
import com.fgt.myapplication.model.DetailLiga
import com.fgt.myapplication.model.Team

interface DetailLigaView {
    fun getDetailLiga(data: List<DetailLiga>)
}