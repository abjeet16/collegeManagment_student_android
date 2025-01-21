package com.example.attendanceappstudent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceappstudent.data_class.SubjectAndDateDTO
import com.example.attendanceappstudent.databinding.ItemSubjectAbsentAttendanceBinding

class SubjectAbsentAttendanceAdapter(
    private val subjectList: List<SubjectAndDateDTO.SubjectAndDateDTOItem>
) : RecyclerView.Adapter<SubjectAbsentAttendanceAdapter.SubjectAbsentAttendanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectAbsentAttendanceViewHolder {
        val binding = ItemSubjectAbsentAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectAbsentAttendanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectAbsentAttendanceViewHolder, position: Int) {
        val item = subjectList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = subjectList.size

    class SubjectAbsentAttendanceViewHolder(private val binding: ItemSubjectAbsentAttendanceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubjectAndDateDTO.SubjectAndDateDTOItem) {
            binding.tvSchedulePeriod.text = "Schedule Period: ${item.schedulePeriod}"
            binding.tvDate.text = "Date: ${item.date}"
        }
    }
}

