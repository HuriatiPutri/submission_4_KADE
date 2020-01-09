package com.fgt.myapplication.presenter

import com.fgt.myapplication.DetailLigaView
import com.fgt.myapplication.DetailView
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.model.DetailEvent
import com.fgt.myapplication.model.DetailEventResponse
import com.fgt.myapplication.model.DetailLiga
import com.fgt.myapplication.model.DetailLigaResponse
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

class DetailPresenterTest {
    @Mock
    private lateinit var view: DetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }
    @Test
    fun getDetailEvent() {
        val teams: List<DetailEvent> = listOf()
        val response = DetailEventResponse(teams)
        val league = "4335"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    DetailEventResponse::class.java
                )
            ).thenReturn(response)

            presenter.getDetailEvent(league)

//            Mockito.verify(view).showLoading()
            Mockito.verify(view).getDetailEvent(teams)
//            Mockito.verify(view).hideLoading()
        }
    }
}