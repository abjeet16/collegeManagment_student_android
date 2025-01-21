package com.example.attendanceappstudent.helper

class ApiLinkHelper {
    val BASE_URL: String = "http://192.168.29.30:8081/api/v1/"

    fun loginUserApiUri(): String {
        return BASE_URL + "auth/user/login"
    }

    fun getUserProfileApiUri(): String? {
        return BASE_URL+"User/my_profile"
    }

    fun getStudentAttendanceApiUri(): String? {
        return BASE_URL+"student/my_overall_attendences"
    }

    fun getSubjectAbsentDetailsApiUri(subjectId: Int): String? {
        return BASE_URL+"student/subject/${subjectId}/absents"
    }
}