package id.capstone.project.skindetector.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import id.capstone.project.skindetector.databinding.ActivitySplashBinding
import id.capstone.project.skindetector.ui.activity.home.HomeActivity
import id.capstone.project.skindetector.ui.activity.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (account != null) {
                startActivity(Intent(this, HomeActivity::class.java).apply {
                    putExtra(HomeActivity.EXTRA_USER, account)
                })
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
            finish()
        }, 2000L)
    }
}