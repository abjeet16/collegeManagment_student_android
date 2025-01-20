package com.example.attendanceappstudent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceappstudent.databinding.ItemSubjectAttendanceBinding
import com.example.attendanceappstudent.data_class.SubjectAttendance

class SubjectAttendanceAdapter(
    private val subjectList: List<SubjectAttendance?>?,
    private val onClickListener: (subjectId: Int) -> Unit
) : RecyclerView.Adapter<SubjectAttendanceAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(val binding: ItemSubjectAttendanceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectAttendanceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjectList?.get(position)
        holder.binding.apply {
            tvSubjectName.text = subject?.subjectName
            tvTotalClasses.text = "Total Classes: ${subject?.totalClasses}"
            tvAttendedClasses.text = "Attended: ${subject?.attendedClasses}"
            tvAttendancePercentage.text = "Attendance: ${String.format("%.2f", subject?.attendancePercentage)}%"

            // Set up the click listener
            root.setOnClickListener {
                subject?.subjectId?.let { onClickListener(it) }
            }
        }
    }

    override fun getItemCount(): Int = subjectList?.size ?: 0
}
