package com.fgt.myapplication.api

import com.fgt.myapplication.BuildConfig
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun testDoRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/search_all_teams.php?l=English%20Premier%20League\\"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}