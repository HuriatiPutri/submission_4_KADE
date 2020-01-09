package com.fgt.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Item (val id: String?,val name: String?, val desc: String?, val image: Int?) : Parcelable