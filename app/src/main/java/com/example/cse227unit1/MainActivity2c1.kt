package com.example.cse227unit1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity2c1 : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var userPass: EditText
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity2c1)

        userName = findViewById(R.id.etEmail)
        userPass = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)

        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val email = userName.text.toString().trim()
            val password = userPass.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 6) {
                    registerUser(email, password)
                } else {
                    userPass.error = "Password must be at least 6 characters"
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendVerificationLink()
                    //StartActivity

                } else {
                    Log.e("FireBase_Auth", "Registration Failed", task.exception)
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun sendVerificationLink() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Verification email sent. Please check your inbox.", Toast.LENGTH_LONG).show()

                // Sign out because we don't want them logged in until they verify
                auth.signOut()

                // Optional: Redirect to Login Activity
                // finish()
            } else {
                Toast.makeText(this, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
