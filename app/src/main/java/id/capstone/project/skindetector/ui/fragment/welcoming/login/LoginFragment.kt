package id.capstone.project.skindetector.ui.fragment.welcoming.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.FragmentLoginBinding
import id.capstone.project.skindetector.ui.activity.welcome.WelcomeActivity.Companion.fromHtml

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvSignUp.apply {
                text =
                    fromHtml(
                        "<font color='#000000'>Haven\'t account yet? </font><font color='#0c0099'>Sign Up here</font>"
                    )
                setOnClickListener {
                    view.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}