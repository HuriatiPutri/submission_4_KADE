package com.fgt.myapplication.presenter

import com.fgt.myapplication.DetailLigaView
import com.fgt.myapplication.MainView
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.api.DBApi
import com.fgt.myapplication.model.DetailLiga
import com.fgt.myapplication.model.DetailLigaResponse
import com.fgt.myapplication.model.Events
import com.fgt.myapplication.model.EventsResponse
import com.fgt.myapplication.util.CoroutineContextProviderTest
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailLigaPresenterTest {

    @Mock
    private lateinit var view: DetailLigaView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: DetailLigaPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLigaPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }
    @Test
    fun getDetailLiga() {
        val teams: List<DetailLiga> = listOf()
        val response = DetailLigaResponse(teams)
        val league = "4335"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    DetailLigaResponse::class.java
                )
            ).thenReturn(response)

            presenter.getDetailLiga(league)

//            Mockito.verify(view).showLoading()
            Mockito.verify(view).getDetailLiga(teams)
//            Mockito.verify(view).hideLoading()
        }
    }
}