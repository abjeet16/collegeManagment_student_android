package com.example.attendanceappstudent.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendanceappstudent.R
import com.example.attendanceappstudent.adapter.SubjectAttendanceAdapter
import com.example.attendanceappstudent.data_class.StudentAttendance
import com.example.attendanceappstudent.data_class.SubjectAttendance
import com.example.attendanceappstudent.databinding.FragmentHomeBinding
import com.example.attendanceappstudent.network.ApiClient
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var studentAttendance: StudentAttendance
    private lateinit var adapter: SubjectAttendanceAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences = requireContext().getSharedPreferences("UserSession", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if (token != null){
            getAttendance(token)
        }

        return root
    }

    private fun getAttendance(token: String) {
        ApiClient.getInstance(requireContext()).getUserAttendance(
            token = token,
            onSuccess = { success ->
                // Handle success: do something with the attendance data
                Log.d("responseSB", "Attendance retrieved: $success")
                studentAttendance = success
                setUpAttendance(studentAttendance)
                setUpRecyclerView(studentAttendance.subjectAttendances)
                // Update UI or process the attendance object
                // For example, show the data in a TextView
            },
            onError = { errorMessage ->
                // Handle error: display error message or log it
                Log.e("MainActivity", "Error retrieving attendance: $errorMessage")

                // Show the error to the user
                Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setUpRecyclerView(subjectAttendances: List<SubjectAttendance?>?) {
        adapter = SubjectAttendanceAdapter(subjectAttendances, onClickListener = ::onClickListener)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setUpAttendance(studentAttendance: StudentAttendance) {

        binding.apply {
            val entries = listOf(
                studentAttendance.percentageCount?.let { PieEntry(it.toFloat(), "Present") },
                PieEntry(100-studentAttendance.percentageCount!!.toFloat(), "Absent")
            )

            // Create PieDataSet
            val dataSet = PieDataSet(entries,"")
            dataSet.colors = listOf(
                ContextCompat.getColor(requireContext(), R.color.present),
                ContextCompat.getColor(requireContext(), R.color.absent)
            )

            // Create PieData
            val pieData = PieData(dataSet)
            pieData.setValueTextSize(12f)
            pieData.setValueTextColor(android.graphics.Color.WHITE)

            // Set the data to the chart
            pieChart.data = pieData
            pieChart.description.isEnabled = false
            pieChart.isDrawHoleEnabled = true
            pieChart.setHoleColor(android.graphics.Color.WHITE)
            pieChart.setUsePercentValues(true)
            //pieChart.centerText = "OverAll Attendance"
            pieChart.animateY(1000)
            pieChart.legend.isEnabled = false

            pieChart.invalidate() // Refresh the chart
        }
    }
    fun onClickListener(subjectId: Int){
        Toast.makeText(requireContext(), "Subject ID: $subjectId", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}