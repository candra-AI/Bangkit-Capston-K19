package id.capstone.project.skindetector.ui.fragment.main.home

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.FragmentHomeBinding
import id.capstone.project.skindetector.ui.activity.welcome.WelcomeActivity
import id.capstone.project.skindetector.ui.adapter.ListDoctorAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.IOException
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val viewModel: HomeViewModel by viewModel()
    private val doctorAdapter: ListDoctorAdapter by lazy { ListDoctorAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Initialize Firebase Auth
        auth = Firebase.auth
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            gotoLogin()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userLoggedIn = auth.currentUser
        with(binding) {
            tvName.text = getString(R.string.hello_name, userLoggedIn?.displayName)
            rvListDoctor.apply {
                adapter = doctorAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
        doctorAdapter.setDoctors(viewModel.getListDoctor() )
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }

    private fun gotoLogin() {
        context?.startActivity(Intent(context, WelcomeActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getAddress(lat: Double, lng: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            var add: String = obj.getAddressLine(0)
            add = """
            $add
            ${obj.getCountryName()}
            """.trimIndent()
            add = """
            $add
            ${obj.getCountryCode()}
            """.trimIndent()
            add = """
            $add
            ${obj.getAdminArea()}
            """.trimIndent()
            add = """
            $add
            ${obj.getPostalCode()}
            """.trimIndent()
            add = """
            $add
            ${obj.getSubAdminArea()}
            """.trimIndent()
            add = """
            $add
            ${obj.getLocality()}
            """.trimIndent()
            add = """
            $add
            ${obj.getSubThoroughfare()}
            """.trimIndent()
            Log.v("IGA", "Address$add")
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}