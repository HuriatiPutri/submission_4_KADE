package com.fgt.myapplication

import com.fgt.myapplication.model.DetailEvent
import com.fgt.myapplication.model.Team

interface DetailView {

    fun showTeam(data: Team)
    fun getDetailEvent(data: List<DetailEvent>)
}