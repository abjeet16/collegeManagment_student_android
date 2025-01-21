package com.example.attendanceappstudent.data_class


import com.google.gson.annotations.SerializedName

class SubjectAndDateDTO : ArrayList<SubjectAndDateDTO.SubjectAndDateDTOItem>(){
    data class SubjectAndDateDTOItem(
        @SerializedName("date")
        val date: String?,
        @SerializedName("schedulePeriod")
        val schedulePeriod: Int?
    )
}