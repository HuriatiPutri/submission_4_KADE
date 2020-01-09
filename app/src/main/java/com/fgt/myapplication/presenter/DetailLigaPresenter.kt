package com.fgt.myapplication.presenter

import com.fgt.myapplication.DetailLigaView
import com.fgt.myapplication.DetailView
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.api.DBApi
import com.fgt.myapplication.model.DetailEventResponse
import com.fgt.myapplication.model.DetailLigaResponse
import com.fgt.myapplication.model.TeamResponse
import com.fgt.myapplication.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailLigaPresenter(private val view: DetailLigaView, private val apiRepository: ApiRepository, private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()
) {


    fun getDetailLiga(idEvent: String?) {
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequest(DBApi.getDetailLiga(idEvent)).await(), DetailLigaResponse::class.java)

                view.getDetailLiga(data.leagues)

        }
    }
}