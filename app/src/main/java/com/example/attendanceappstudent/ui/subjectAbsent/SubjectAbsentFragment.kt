package com.example.attendanceappstudent.ui.subjectAbsent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.attendanceappstudent.databinding.FragmentSubjectAbsentBinding

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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
