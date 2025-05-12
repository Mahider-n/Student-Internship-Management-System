package com.site7x24learn.internshipfrontend.utils



import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone

object DateUtils {
    /**
     * Formats an ISO date string to YYYY-MM-DD format
     */
    fun formatDateString(isoDate: String?): String {
        if (isoDate.isNullOrEmpty()) return ""

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(isoDate)

            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            isoDate // Return original if parsing fails
        }
    }

    /**
     * Checks if a string matches ISO 8601 date format
     */
    fun isIsoDate(dateString: String?): Boolean {
        return dateString?.matches(Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z")) ?: false
    }
}