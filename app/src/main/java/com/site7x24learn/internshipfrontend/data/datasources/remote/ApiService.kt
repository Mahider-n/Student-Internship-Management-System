package com.site7x24learn.internshipfrontend.data.datasources.remote

import com.site7x24learn.internshipfrontend.data.datasources.models.request.ApplicationUpdateRequest
import com.site7x24learn.internshipfrontend.data.datasources.models.request.CreateInternshipRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.request.LoginRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.request.SignUpRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateInternshipRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateProfileRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateStatusRequestDto

import com.site7x24learn.internshipfrontend.data.datasources.models.response.ApplicationListResponse
import com.site7x24learn.internshipfrontend.data.datasources.models.response.ApplicationsResponseDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.AuthResponseDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.CreateApplicationResponse
import com.site7x24learn.internshipfrontend.data.datasources.models.response.DropdownDataResponseDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.InternshipListResponseDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.InternshipResponseDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.ProfileDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.SingleApplicationResponse
import com.site7x24learn.internshipfrontend.data.datasources.models.response.UserDto

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Auth Endpoints
    @POST("api/auth/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequestDto
    ): Response<AuthResponseDto>

    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequestDto
    ): Response<AuthResponseDto>

    // Internship Endpoints
    @GET("api/internships/dropdown-data")
    suspend fun getCategories(): Response<DropdownDataResponseDto>

    @POST("api/internships")
    suspend fun createInternship(
        @Body request: CreateInternshipRequestDto
    ): Response<InternshipResponseDto>

    @GET("api/internships")
    suspend fun getInternships(): Response<InternshipListResponseDto>

    @GET("api/internships/{id}")
    suspend fun getInternshipById(@Path("id") id: Int): Response<InternshipResponseDto>

    @PUT("api/internships/{id}")
    suspend fun updateInternship(
        @Path("id") id: Int,
        @Body request: UpdateInternshipRequestDto
    ): Response<BaseResponseDto>

    @DELETE("api/internships/{id}")
    suspend fun deleteInternship(@Path("id") id: Int): Response<BaseResponseDto>

    // Application Endpoints
    @Multipart
    @POST("api/applications")
    suspend fun createApplication(
        @Part("internship_id") internshipId: RequestBody,
        @Part("university") university: RequestBody,
        @Part("degree") degree: RequestBody,
        @Part("graduation_year") graduationYear: RequestBody,
        @Part("linkdIn") linkedIn: RequestBody,
        @Part resume: MultipartBody.Part
    ): Response<CreateApplicationResponse>

    @GET("api/applications")
    suspend fun getUserApplications(): Response<ApplicationListResponse>

    @GET("api/applications/only-admins")
    suspend fun getApplications(): Response<ApplicationsResponseDto>

    @GET("api/applications/{id}")
    suspend fun getApplicationById(@Path("id") id: Int): Response<SingleApplicationResponse>

    @PUT("api/applications/{id}")
    suspend fun updateApplication(
        @Path("id") applicationId: Int,
        @Body request: ApplicationUpdateRequest
    ): Response<Unit>

    @DELETE("api/applications/{id}")
    suspend fun deleteApplication(@Path("id") applicationId: Int): Response<Unit>

    @GET("api/applications/check")
    suspend fun checkExistingApplication(@Query("internshipId") internshipId: Int): Boolean

    @PATCH("api/applications/{id}/status")
    suspend fun updateApplicationStatus(
        @Path("id") id: Int,
        @Body request: UpdateStatusRequestDto
    ): Response<BaseResponseDto>
    @GET("api/users/profile")
    suspend fun getProfile(): Response<ProfileDto>

    @PUT("api/users/{id}")
    suspend fun updateProfile(
        @Path("id") id: Int,
        @Body request: UpdateProfileRequestDto
    ): Response<UserDto>

    @DELETE("api/users/{id}")
    suspend fun deleteProfile(@Path("id") id: Int)

//    // Student Endpoints
//    @POST("api/student/{id}/profile")
//    suspend fun saveProfile(
//        @Body request: StudentProfileRequestDto
//    ): Response<StudentProfileResponseDto>
//
//    @DELETE("api/student/profile")
//    suspend fun deleteProfile(@Path("id") id: Int): Response<StudentProfileResponseDto>
}

data class BaseResponseDto(
    val success: Boolean,
    val message: String?
)










