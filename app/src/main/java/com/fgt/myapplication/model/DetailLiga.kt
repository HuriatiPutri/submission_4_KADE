package com.fgt.myapplication.model

import com.google.gson.annotations.SerializedName

data class DetailLiga(
    @SerializedName("idLeague")
    var idLeague: String? = null,

    @SerializedName("strLeague")
    var league: String? = null,

    @SerializedName("strDescriptionEN")
    var desc: String? = null


)