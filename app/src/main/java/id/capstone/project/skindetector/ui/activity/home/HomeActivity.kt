package id.capstone.project.skindetector.ui.activity.home

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.ActivityHomeBinding
import id.capstone.project.skindetector.ui.fragment.main.camera.CameraFragment.Companion.GALLERY_REQUEST
import id.capstone.project.skindetector.ui.fragment.other.detectionresult.DetectionResultFragment
import java.io.FileNotFoundException
import java.io.InputStream


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var backPressCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_message,
                R.id.navigation_camera,
                R.id.navigation_premium,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bubbleTabBar.addBubbleListener {
            onNavDestinationSelected(it, navController)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bubbleTabBar.setSelectedWithId(destination.id, false)
        }

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            if (backPressCount == 0) {
                Toast.makeText(this, "Press once again to exit!", Toast.LENGTH_SHORT).show()
                backPressCount++
            } else
                super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST -> {

                    try {
                        val imageUri: Uri = data?.data as Uri
//                        val imageStream: InputStream? = contentResolver.openInputStream(imageUri)
//                        val selectedImage = BitmapFactory.decodeStream(imageStream)
                        val fragment = DetectionResultFragment().apply {
                            imagePath = imageUri
                        }
                        findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_camera_to_detectionResultFragment)
//                        supportFragmentManager.commitNow(allowStateLoss = true) {
//                            add(
//                                R.id.nav_host_fragment,
//                                fragment,
//                                DetectionResultFragment::class.java.simpleName
//                            )
//                        }
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                        Toast.makeText(
                            applicationContext,
                            "Something went wrong",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
                else -> Toast.makeText(this, "Not Found! $requestCode", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onNavDestinationSelected(
        itemId: Int,
        navController: NavController
    ): Boolean {
        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
        if (navController.currentDestination!!.parent!!.findNode(itemId) is ActivityNavigator.Destination) {
            builder.setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
        } else {
            builder.setEnterAnim(R.animator.nav_default_enter_anim)
                .setExitAnim(R.animator.nav_default_exit_anim)
                .setPopEnterAnim(R.animator.nav_default_pop_enter_anim)
                .setPopExitAnim(R.animator.nav_default_pop_exit_anim)
        }
        builder.setPopUpTo(itemId, true)
        val options = builder.build()
        return try {
            //TODO provide proper API instead of using Exceptions as Control-Flow.
            navController.navigate(itemId, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}