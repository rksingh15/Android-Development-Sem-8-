package com.example.cse227unit1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class realtimeDataBasePart1 : AppCompatActivity() {

    lateinit  var regNumber : EditText
    lateinit  var cgpa : EditText
    lateinit  var phoneNumber : EditText
    lateinit  var address : EditText
    lateinit var savebtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_realtime_data_base_part1)

        regNumber = findViewById<EditText>(R.id.etID)
        cgpa= findViewById<EditText>(R.id.etCgpa)
        phoneNumber = findViewById<EditText>(R.id.etPhone)
        address = findViewById<EditText>(R.id.etAddress)
        savebtn = findViewById<Button>(R.id.btnSave)

        savebtn.setOnClickListener {

            var r= regNumber.text.trim().toString()
            var cgpa= cgpa.text.trim().toString()
            var phoneNum= phoneNumber.text.trim().toString()
            var add= address.text.trim().toString()


            var db = FirebaseDatabase.getInstance().getReference("KOMO4")
                                                      // for making file inside
            val hm = HashMap<String, Any>()

            hm.put("reg",r)
            hm.put("cgpa",cgpa)
            hm.put("phoneNumber",phoneNum)
            hm.put("add",add)

            db.child("KOMO4").setValue(hm)  // majedar hai
                .addOnCompleteListener {

                    Toast.makeText(this, "Data Add Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }


        }

    }


}