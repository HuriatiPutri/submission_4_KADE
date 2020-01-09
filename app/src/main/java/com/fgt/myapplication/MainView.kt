package com.fgt.myapplication

import com.fgt.myapplication.model.DetailEvent
import com.fgt.myapplication.model.Events


interface MainView {

    fun showLoading()
    fun hideLoading()
    fun toast()
    fun showPrevEvent(data: List<Events>)
    fun showNextEvent(data: List<Events>)
    fun showSearchEvent(data: List<Events>)
}