package com.example.attendanceappstudent.data_class


import com.google.gson.annotations.SerializedName

data class StudentAttendance(
    @SerializedName("percentageCount")
    val percentageCount: Double?,
    @SerializedName("subjectAttendances")
    val subjectAttendances: List<SubjectAttendance?>?
)