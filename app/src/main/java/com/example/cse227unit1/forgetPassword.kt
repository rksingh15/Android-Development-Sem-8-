package com.example.cse227unit1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

// ... baki imports sahi hain

class forgetPassword : AppCompatActivity() { // Class name capital hona chahiye rule ke hisab se (ForgetPasswordActivity)
    private lateinit var userName: EditText
    private lateinit var userPass: EditText
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var forg : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forget_password)

        userName = findViewById(R.id.etEmail)
        userPass = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        forg = findViewById(R.id.tvForgotPassword)

        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val s = userName.text.toString().trim()
            val p = userPass.text.toString().trim()

            if (s.isNotEmpty() && p.isNotEmpty()) {
                LoginDATA(s, p)
            } else {
                Toast.makeText(this, "Fields empty hain!", Toast.LENGTH_SHORT).show()
            }
        }

        // Click listener for Forgot Password
        forg.setOnClickListener {
            handleForgetPassword()
        }
    }

    fun LoginDATA(e: String, p: String) {
        auth.signInWithEmailAndPassword(e, p)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this , MainActivity2c2::class.java )) // for next page
                } else {
                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Function ka naam change kiya taaki class name se mix na ho
    fun handleForgetPassword() {
        val e = userName.text.toString().trim()

        if (e.isNotEmpty()) {
            auth.sendPasswordResetEmail(e)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@forgetPassword, "Reset link sent to $e", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@forgetPassword, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            // Agar email empty hai toh error dikhao
            userName.error = "Please enter email first"
            Toast.makeText(this, "Pehle Email likho!", Toast.LENGTH_SHORT).show()
        }
    }
}