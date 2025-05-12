package com.site7x24learn.internshipfrontend.data.datasources.models.response

import com.google.gson.annotations.SerializedName

data class CreateApplicationResponse(
    @SerializedName("application_id") val applicationId: Int
)
