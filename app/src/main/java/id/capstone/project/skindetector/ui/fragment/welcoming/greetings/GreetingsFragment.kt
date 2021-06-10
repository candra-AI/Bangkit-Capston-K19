package id.capstone.project.skindetector.ui.fragment.welcoming.greetings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.FragmentGreetingsBinding
import id.capstone.project.skindetector.utils.helper.PreferenceHelper

/**
 * A simple [Fragment] subclass.
 * Use the [GreetingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GreetingsFragment : Fragment() {
    private var _binding: FragmentGreetingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val args: GreetingsFragmentArgs by navArgs()
    private val preferenceHelper by lazy { PreferenceHelper(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGreetingsBinding.inflate(inflater, container, false)
        // Initialize Firebase Auth
        auth = Firebase.auth
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            reload()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = auth.currentUser

        // Set a key to a string.
        FirebaseCrashlytics.getInstance()
            .setCustomKey("userLoggedIn", user?.displayName ?: "Display Name is Null")
        FirebaseCrashlytics.getInstance()
            .setCustomKey("userEmail", user?.email ?: "Email is Null")

        with(binding) {
            if (user != null) {
                tvName.text = getString(R.string.hi_name, user.displayName ?: args.displayName)
                ivProfile.load(user.photoUrl) {
                    crossfade(true)
                    crossfade(1000)
                    transformations(RoundedCornersTransformation(30f))
                }
            }
            btnStart.setOnClickListener {
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun reload() {
        view?.findNavController()?.navigate(R.id.action_greetingsFragment_to_signUpFragment)
    }
}