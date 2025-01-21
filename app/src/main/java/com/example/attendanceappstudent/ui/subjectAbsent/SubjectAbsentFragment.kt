package com.example.attendanceappstudent.ui.subjectAbsent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendanceappstudent.adapter.SubjectAbsentAttendanceAdapter
import com.example.attendanceappstudent.data_class.SubjectAndDateDTO
import com.example.attendanceappstudent.databinding.FragmentSubjectAbsentBinding
import com.example.attendanceappstudent.network.ApiClient

class SubjectAbsentFragment : Fragment() {

    private var _binding: FragmentSubjectAbsentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubjectAbsentBinding.inflate(inflater, container, false)

        val args = SubjectAbsentFragmentArgs.fromBundle(requireArguments())
        val subjectId = args.subjectId

        val sharedPreferences = requireContext().getSharedPreferences("UserSession", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if (token != null){
            getAbsentDetails(subjectId,token)
        }

        return binding.root
    }

    private fun getAbsentDetails(subjectId: Int, token: String) {
        ApiClient.getInstance(requireContext()).getSubjectAbsentDetails(
            token = token,
            subjectId = subjectId,
            onSuccess = { success ->
                setUpRecyclerview(success)
            },
            onError = { errorMessage ->
                // Handle error: display error message or log it
                Log.e("MainActivity", "Error retrieving attendance: $errorMessage")

                // Show the error to the user
                Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setUpRecyclerview(subjectList: List<SubjectAndDateDTO.SubjectAndDateDTOItem>) {
        val adapter = SubjectAbsentAttendanceAdapter(subjectList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
