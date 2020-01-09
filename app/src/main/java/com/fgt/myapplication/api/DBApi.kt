package com.fgt.myapplication.api

import android.net.Uri
import com.fgt.myapplication.BuildConfig


object DBApi {
    fun getSearchEvents(event: String?): String {
//        return Uri.parse(
//            BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("searchevents.php")
//            .appendQueryParameter("e", event)
//            .build()
//            .toString()
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/searchevents.php?e="+event

    }

    fun getPrevEvents(idTeam: String?): String {
//        return Uri.parse(
//            BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("eventspastleague.php")
//            .appendQueryParameter("id", idTeam)
//            .build()
//            .toString()
       return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/eventspastleague.php?id="+idTeam
    }

    fun getNextEvents(idTeam: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("eventsnextleague.php")
//            .appendQueryParameter("id", idTeam)
//            .build()
//            .toString()
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/eventsnextleague.php?id="+idTeam

    }

    fun getTeam(idTeam: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("lookupteam.php")
//            .appendQueryParameter("id", idTeam)
//            .build()
//            .toString()
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/lookupteam.php?id="+idTeam

    }

    fun getDetailEvent(idEvent: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("lookupevent.php")
//            .appendQueryParameter("id", idEvent)
//            .build()
//            .toString()
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/lookupevent.php?id="+idEvent

    }
    fun getDetailLiga(idEvent: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("lookupleague.php")
//            .appendQueryParameter("id", idEvent)
//            .build()
//            .toString()
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/lookupleague.php?id="+idEvent

    }
}