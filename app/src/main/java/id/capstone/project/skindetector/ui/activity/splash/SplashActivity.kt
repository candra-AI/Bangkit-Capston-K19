package id.capstone.project.skindetector.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.capstone.project.skindetector.databinding.ActivitySplashBinding
import id.capstone.project.skindetector.ui.activity.home.HomeActivity
import id.capstone.project.skindetector.ui.activity.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            if (true) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
            finish()
        }, 2000L)
    }
}