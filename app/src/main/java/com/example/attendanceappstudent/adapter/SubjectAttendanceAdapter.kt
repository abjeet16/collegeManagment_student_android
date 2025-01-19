package com.example.attendanceappstudent.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceappstudent.R
import com.example.attendanceappstudent.data_class.SubjectAttendance

class SubjectAttendanceAdapter(
    private val subjectList: List<SubjectAttendance?>?
) : RecyclerView.Adapter<SubjectAttendanceAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectName: TextView = itemView.findViewById(R.id.tvSubjectName)
        val totalClasses: TextView = itemView.findViewById(R.id.tvTotalClasses)
        val attendedClasses: TextView = itemView.findViewById(R.id.tvAttendedClasses)
        val attendancePercentage: TextView = itemView.findViewById(R.id.tvAttendancePercentage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subject_attendance, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjectList?.get(position)
        if (subject != null) {
            holder.subjectName.text = subject.subjectName
        }
        holder.totalClasses.text = "Total Classes: ${subject?.totalClasses}"
        holder.attendedClasses.text = "Attended: ${subject?.attendedClasses}"
        holder.attendancePercentage.text = "Attendance: ${subject?.attendancePercentage}%"
    }

    override fun getItemCount(): Int = subjectList?.size ?: 0
}
