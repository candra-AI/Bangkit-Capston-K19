package id.capstone.project.skindetector.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
                // Set a key to a string.
                FirebaseCrashlytics.getInstance()
                    .setCustomKey("userLoggedIn", account.displayName ?: "Display Name is Null")
                FirebaseCrashlytics.getInstance()
                    .setCustomKey("userEmail", account.email ?: "Email is Null")
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