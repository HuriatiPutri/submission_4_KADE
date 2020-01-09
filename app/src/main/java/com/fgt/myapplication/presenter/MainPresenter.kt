package com.fgt.myapplication.presenter

import android.widget.Toast
import com.fgt.myapplication.MainView
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.api.DBApi
import com.fgt.myapplication.model.DetailEventResponse
import com.fgt.myapplication.model.EventResponse
import com.fgt.myapplication.model.Events
import com.fgt.myapplication.model.EventsResponse
import com.fgt.myapplication.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView, private val apiRepository: ApiRepository,
                    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getSearchEventsList(idEvent: String?, idL:String?) {
        view.showLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequest(DBApi.getSearchEvents(idEvent)).await(),
                EventResponse::class.java)

                view.hideLoading()
                if(data.event != null) {
                   var data =  data.event.filter { it.idLeaque.equals(idL) && it.sport.equals("Soccer") }
                    view.showSearchEvent(data)
                }else
                    view.toast()

        }
    }

    fun getPrevEventsList(idEvent: String?) {
        view.showLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequest(DBApi.getPrevEvents(idEvent)).await(),
                EventsResponse::class.java)

                view.hideLoading()
                if(data.events != null)
                view.showPrevEvent(data.events)
                else
                    view.toast()

        }
    }

    fun getNextEventsList(idEvent: String?) {
        view.showLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequest(DBApi.getNextEvents(idEvent)).await(),
                EventsResponse::class.java)

                view.hideLoading()
                if(data.events != null)
                view.showNextEvent(data.events)
                else
                view.toast()

        }
    }
}