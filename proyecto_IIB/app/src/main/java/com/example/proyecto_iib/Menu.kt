package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val correoString = intent.getStringExtra("email")
        val uid = intent.getStringExtra("userId")

        var correo = findViewById<TextView>(R.id.tv_correo)
        var play = findViewById<Button>(R.id.btn_play)
        var exit = findViewById<Button>(R.id.btn_exit)
        var standing = findViewById<Button>(R.id.btn_standing)

        correo.text = correoString

        play.setOnClickListener{
            val intent = Intent(this, Escenario::class.java)
            intent.putExtra("email", correoString)
            startActivity(intent)
        }

        standing.setOnClickListener {
            val intent = Intent(this, Standing::class.java)
            startActivity(intent)
        }

        exit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Inicio::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}