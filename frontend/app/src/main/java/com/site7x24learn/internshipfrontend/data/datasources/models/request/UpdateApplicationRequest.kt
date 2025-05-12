package com.site7x24learn.internshipfrontend.data.datasources.models.request

import com.google.gson.annotations.SerializedName


data class ApplicationUpdateRequest(
    val university: String,
    val degree: String,
    @SerializedName("graduation_year") // Add if using Gson
    val graduation_year: Int,
    @SerializedName("linkdIn")
    val linkdIn: String? = null
)
//data class ApplicationUpdateRequest(
//    val university: String,
//    val degree: String,
//    val graduation_year: Int,
//    val linkdIn: String? = null
//)