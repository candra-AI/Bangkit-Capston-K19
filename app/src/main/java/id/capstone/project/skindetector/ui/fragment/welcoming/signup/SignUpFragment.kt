package id.capstone.project.skindetector.ui.fragment.welcoming.signup

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.FragmentSignUpBinding
import id.capstone.project.skindetector.ui.activity.welcome.WelcomeActivity
import id.capstone.project.skindetector.utils.helper.PreferenceHelper


/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    companion object {
        const val RC_SIGN_IN = 100
        private const val TAG = "SignUpFragment"
    }

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val preferenceHelper by lazy { PreferenceHelper(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentSignUpBinding.inflate(inflater, container, false)
        // Initialize Firebase Auth
        auth = Firebase.auth
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userVerificationDone()
        }

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            userVerificationDone()
        }
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
            btnGoogle.setOnClickListener {
                signIn()
            }
            btnCreate.setOnClickListener {
                if (validateInput()) {
                    //Get values from EditText fields
                    val name = edtName.text.toString()
                    val email = edtEmail.text.toString()
                    val password = edtPassword.text.toString()

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                val profileUpdates = userProfileChangeRequest {
                                    displayName = name
//                                    photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
                                }

                                user!!.apply {
                                    updateProfile(profileUpdates)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d(TAG, "User profile updated.")
                                            }
                                        }
                                    sendEmailVerification()
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d(TAG, "Email sent.")
                                            }
                                        }

                                }
                                preferenceHelper.setLogin()
                                userVerificationDone(name)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    context?.applicationContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                userVerificationDone()
                            }
                        }
                }
            }
        }
    }

    private fun userVerificationDone(displayName: String = "") {
        view?.findNavController()
            ?.navigate(SignUpFragmentDirections.actionSignUpFragmentToGreetingsFragment(displayName))
    }

    private fun validateInput(): Boolean {
        with(binding) {
            var validEmail = false
            var validPassword = false
            var validName = false

            //Get values from EditText fields
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            //Handling validation for Email field
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.error = "Please enter valid email!"
            } else {
                validEmail = true
                tilEmail.error = null
            }

            //Handling validation for Password field
            if (password.isEmpty()) {
                tilPassword.error = "Please enter valid password!"
            } else {
                if (password.length > 5) {
                    validPassword = true
                    tilPassword.error = null
                } else {
                    validPassword = false
                    tilPassword.error = "Password is to short!"
                }
            }

            //Handling validation for Name field
            if (name.isEmpty()) {
                tilName.error = "Please fill your name!"
            } else {
                validName = true
                tilName.error = null
            }
            return validName && validEmail && validPassword
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        activity?.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}