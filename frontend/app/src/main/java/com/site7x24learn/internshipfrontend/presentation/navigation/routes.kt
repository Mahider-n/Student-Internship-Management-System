package com.site7x24learn.internshipfrontend.presentation.navigation

object Routes {
    const val LOGIN = "login"
    const val SIGN_UP = "signup"
    const val ADD_INTERNSHIP = "add_internship"
//    const val EDIT_INTERNSHIP="edit_internship/{internshipId}"
    const val ADMIN_DASHBOARD = "admin_dashboard"
    const val STUDENT_DASHBOARD = "student_dashboard"
//    const val APPLY_INTERNSHIP = "apply_internship/{internshipId}"
//    const val APPLY_INTERNSHIP = "apply_internship/{internshipId}?applicationId={applicationId}"
    const val INTERNSHIP_LIST="internship_list"
    const val STUDENT_APPLICATIONS = "student_applications"
    const val PROFILE = "profile"
    const val STUDENT_STATUS="student_status"
    const val LANDING_PAGE="landing"
    const val WAITING_PAGE="waiting"
//    const val UPDATE_APPLICATION = "update_application/{applicationId}"


//    fun editInternshipRoute(internshipId:Int)="edit_internship/$internshipId"
    // ... existing routes
    const val EDIT_INTERNSHIP = "edit_internship/{internshipId}"

    fun editInternshipRoute(internshipId: Int) = "edit_internship/$internshipId"

    const val APPLY_INTERNSHIP = "apply_internship/{internshipId}?applicationId={applicationId}"
    //    fun applyInternshipRoute(internshipId: Int, applicationId: Int = -1) =
//        "apply_internship/$internshipId?applicationId=$applicationId"
    fun applyInternshipRoute(internshipId: Int, applicationId: Int = -1): String {
        return "apply_internship/$internshipId?applicationId=$applicationId"
    }

}