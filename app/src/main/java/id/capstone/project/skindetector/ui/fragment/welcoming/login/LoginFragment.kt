package id.capstone.project.skindetector.ui.fragment.welcoming.login

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
import com.google.firebase.ktx.Firebase
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.FragmentLoginBinding
import id.capstone.project.skindetector.ui.activity.welcome.WelcomeActivity
import id.capstone.project.skindetector.ui.activity.welcome.WelcomeActivity.Companion.fromHtml
import id.capstone.project.skindetector.ui.fragment.welcoming.signup.SignUpFragment

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    companion object {
        private const val TAG = "LoginFragment"
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

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
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Initialize Firebase Auth
        auth = Firebase.auth
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
            btnLogin.setOnClickListener {
                if (validateInput()) {
                    //Get values from EditText fields
                    val email = edtEmail.text.toString()
                    val password = edtPassword.text.toString()

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")
                                updateUI()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(
                                    context, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
            btnGoogle.setOnClickListener {
                signIn()
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        activity?.startActivityForResult(signInIntent, SignUpFragment.RC_SIGN_IN)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateInput(): Boolean {
        with(binding) {
            var validEmail = false
            var validPassword = false

            //Get values from EditText fields
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
            return validEmail && validPassword
        }
    }

    private fun updateUI() {
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_greetingsFragment)
    }
}