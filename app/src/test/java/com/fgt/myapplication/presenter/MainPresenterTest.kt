package com.fgt.myapplication.presenter

import com.fgt.myapplication.MainView
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.model.EventResponse
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

class MainPresenterTest {

    @Mock
    private lateinit var view: MainView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }

    @Test
    fun getSearchEventsList() {
        val teams: MutableList<Events> = mutableListOf()
        val response = EventResponse(teams)
        val league = "Barcelona"
        val idL = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventResponse::class.java
                )
            ).thenReturn(response)

            presenter.getSearchEventsList(league,idL)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showSearchEvent(teams)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getPrevEventsList() {
        val teams: MutableList<Events> = mutableListOf()
        val response = EventsResponse(teams)
        val league = "4335"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventsResponse::class.java
                )
            ).thenReturn(response)

            presenter.getPrevEventsList(league)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showPrevEvent(teams)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getNextEventsList() {
        val teams: MutableList<Events> = mutableListOf()
        val response = EventsResponse(teams)
        val league = "4335"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventsResponse::class.java
                )
            ).thenReturn(response)

            presenter.getNextEventsList(league)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showNextEvent(teams)
            Mockito.verify(view).hideLoading()
        }
    }

}