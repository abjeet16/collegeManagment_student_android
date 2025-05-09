package com.example.attendanceappstudent.ui.home

import android.graphics.Color
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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendanceappstudent.R
import com.example.attendanceappstudent.adapter.SubjectAttendanceAdapter
import com.example.attendanceappstudent.data_class.StudentAttendance
import com.example.attendanceappstudent.data_class.SubjectAttendance
import com.example.attendanceappstudent.databinding.FragmentHomeBinding
import com.example.attendanceappstudent.network.ApiClient
import com.example.attendanceappstudent.ui.subjectAbsent.SubjectAbsentFragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
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
                studentAttendance = success
                setUpAttendance(studentAttendance)
                setUpRecyclerView(studentAttendance.subjectAttendances)
            },
            onError = { errorMessage ->
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

        val presentCount = studentAttendance.percentageCount!!.toFloat()
        val absentCount = 100 - presentCount

        if (presentCount == 0f){
            binding.pieChart.visibility = View.GONE
        }
        binding.apply {
            val entries = listOf(
                PieEntry(presentCount, "Present"),
                PieEntry(absentCount, "Absent")
            )
            // Create PieDataSet
            val dataSet = PieDataSet(entries, "").apply {
                colors = listOf(
                    ContextCompat.getColor(requireContext(), R.color.present),
                    ContextCompat.getColor(requireContext(), R.color.absent)
                )
                valueTextSize = 14f                    // Set text size
                valueTextColor = Color.WHITE          // Set text color
                valueFormatter = PercentFormatter(pieChart) // Attach PercentFormatter with the pie chart
            }
            // Configure PieChart
            pieChart.apply {
                setUsePercentValues(true)            // Enable percentage mode
                isDrawHoleEnabled = true             // Optional: Hole in the center
                setEntryLabelTextSize(12f)           // Label size
                setEntryLabelColor(Color.WHITE)      // Label color
                description.isEnabled = false        // Disable description
                legend.isEnabled = false              // Show legend
            }
            // Create PieData and set it to PieChart
            val pieData = PieData(dataSet).apply {
                setValueTextSize(14f)                // Value text size
                setValueTextColor(Color.WHITE)       // Value text color
            }
            // Set data and refresh the chart
            pieChart.data = pieData
            pieChart.invalidate() // Refresh chart
        }
    }
    fun onClickListener(subjectId: Int){
        val action = HomeFragmentDirections.actionHomeToSubjectAbsentFragment(subjectId)
        requireActivity().findNavController(R.id.nav_host_fragment_content_main).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}