package com.example.cse227unit1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class FireBasec1c2 : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var register: Button
    private lateinit var login: Button
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fire_basec1c2)

        email = findViewById(R.id.et1)
        password = findViewById(R.id.et2)
        register = findViewById(R.id.bt1)
        login = findViewById(R.id.bt2)

        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            val e = email.text.toString().trim()
            val p = password.text.toString().trim()

            if (e.isNotEmpty() && p.isNotEmpty()) {
                registerUser(e, p)
            } else {
                Toast.makeText(this, "Please enter all details to register", Toast.LENGTH_SHORT).show()
            }
        }

        login.setOnClickListener {
            val e = email.text.toString().trim()
            val p = password.text.toString().trim()

            if (e.isNotEmpty() && p.isNotEmpty()) {
                loginUser(e, p)
            } else {
                Toast.makeText(this, "Please enter all details to login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(e: String, p: String) {
        auth.createUserWithEmailAndPassword(e, p)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendVerificationLink()
                } else {
                    Log.e("Firebase_Auth", "Registration Failed", task.exception)
                    Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun sendVerificationLink() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Verification email sent! Please check your inbox.", Toast.LENGTH_LONG).show()
                auth.signOut() // Sign out until they verify
            } else {
                Toast.makeText(this, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(e: String, p: String) {
        auth.signInWithEmailAndPassword(e, p)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
                        // Move to Home Activity here:
                        // startActivity(Intent(this, HomeActivity::class.java))
                    } else {
                        Toast.makeText(this, "Please verify your email first.", Toast.LENGTH_LONG).show()
                        auth.signOut() // Force sign out if not verified
                    }
                } else {
                    Log.e("Firebase_Auth", "Login Failed", task.exception)
                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
