package com.example.attendanceappstudent.ui.internalsmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InternalsMarksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This Feature is not available"
    }
    val text: LiveData<String> = _text
}