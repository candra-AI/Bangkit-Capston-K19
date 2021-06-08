package id.capstone.project.skindetector.ui.fragment.welcoming.identity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.FragmentHomeBinding
import id.capstone.project.skindetector.databinding.FragmentIdentityBinding

/**
 * A simple [Fragment] subclass.
 * Use the [IdentityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IdentityFragment : Fragment() {
    private var _binding: FragmentIdentityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIdentityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}