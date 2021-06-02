package id.capstone.project.skindetector.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.capstone.project.skindetector.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager

        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this, HomeActivity::class.java))
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }, 2000L)
    }
}