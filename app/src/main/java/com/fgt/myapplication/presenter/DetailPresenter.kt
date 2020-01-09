package com.fgt.myapplication.presenter

import com.fgt.myapplication.DetailView
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.api.DBApi
import com.fgt.myapplication.model.DetailEventResponse
import com.fgt.myapplication.model.TeamResponse
import com.fgt.myapplication.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView, private val apiRepository: ApiRepository, private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeam(idTeam: String?) {
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequest(DBApi.getTeam(idTeam)).await(), TeamResponse::class.java)

                view.showTeam(data.teams.first())

        }
    }

    fun getDetailEvent(idEvent: String?) {
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequest(DBApi.getDetailEvent(idEvent)).await(), DetailEventResponse::class.java)

                view.getDetailEvent(data.events)

        }
    }
}