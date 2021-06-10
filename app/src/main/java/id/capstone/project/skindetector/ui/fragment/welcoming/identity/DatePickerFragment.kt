package id.capstone.project.skindetector.ui.fragment.welcoming.identity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import id.capstone.project.skindetector.databinding.FragmentDatePickerBinding

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{
    private var _binding :FragmentDatePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDatePickerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }

}