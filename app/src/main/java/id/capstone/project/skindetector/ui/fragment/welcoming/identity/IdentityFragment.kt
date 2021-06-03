package id.capstone.project.skindetector.ui.fragment.welcoming.identity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.capstone.project.skindetector.R

/**
 * A simple [Fragment] subclass.
 * Use the [IdentityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IdentityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_identity, container, false)
    }
}