package com.example.attendanceappstudent.data_class

import com.google.gson.annotations.SerializedName

data class SubjectAttendance(
    @SerializedName("attendancePercentage")
    val attendancePercentage: Double?,
    @SerializedName("subjectId")
    val subjectId: Int?,
    @SerializedName("attendedClasses")
    val attendedClasses: Int?,
    @SerializedName("subjectName")
    val subjectName: String?,
    @SerializedName("totalClasses")
    val totalClasses: Int?
)