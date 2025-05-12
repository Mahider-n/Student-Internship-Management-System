package com.site7x24learn.internshipfrontend.data.datasources.models.response


import com.google.gson.annotations.SerializedName
import com.site7x24learn.internshipfrontend.domain.models.application.Application
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationStatus

data class ApplicationResponse(
    @SerializedName("application_id") val applicationId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("internship_id") val internshipId: Int,
    val university: String,
    val degree: String,
    @SerializedName("graduation_year") val graduationYear: Int, // Changed to Int
    @SerializedName("linkedIn") val linkedIn: String,
    val status: String,
    @SerializedName("applied_at") val appliedAt: String,
    @SerializedName("resume_id") val resumeId: Int,
    @SerializedName("attachment_path") val attachmentPath: String?,
    @SerializedName("internship_title") val internshipTitle: String?,
    @SerializedName("company_name") val companyName: String?
) {
    fun toDomain(): Application {
        return Application(
            id = applicationId,  // Fixed parameter name
            userId = userId,
            internshipId = internshipId,
            university = university,
            degree = degree,
            graduationYear = graduationYear,
            linkdIn = linkedIn,
            status = ApplicationStatus.fromString(status),  // Proper enum conversion
            appliedAt = appliedAt,
            resumeId = resumeId,
            attachmentPath = attachmentPath,  // Added missing field
            internshipTitle = internshipTitle,
            companyName = companyName
        )
    }
}
data class ApplicationListResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<ApplicationResponse>,
    @SerializedName("message") val message: String? = null
)

/**
 * For endpoints returning a single application
 */
data class SingleApplicationResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: ApplicationResponse,
    @SerializedName("message") val message: String? = null
)