package id.capstone.project.skindetector.ui.fragment.welcoming.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.FragmentSignUpBinding
import id.capstone.project.skindetector.ui.activity.welcome.WelcomeActivity

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvToLogin.apply {
                text =
                    WelcomeActivity.fromHtml(
                        "<font color='#000000'>Have an account? </font><font color='#0c0099'>Login Now!</font>"
                    )
                setOnClickListener {
                    view.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}