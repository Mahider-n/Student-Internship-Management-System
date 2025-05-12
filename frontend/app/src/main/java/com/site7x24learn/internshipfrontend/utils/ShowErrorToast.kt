package com.site7x24learn.internshipfrontend.utils

fun showErrorToast(context: android.content.Context, message: String) {
    android.widget.Toast.makeText(
        context,
        message,
        android.widget.Toast.LENGTH_LONG
    ).show()
}