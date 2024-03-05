package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            val logearseIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            respuestaLoginAuthUi.launch(logearseIntent)
        }
    }

    val respuestaLoginAuthUi = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode === RESULT_OK) {
            if (res.idpResponse != null) {
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(res: IdpResponse) {
        if (res.isNewUser == true) {
            registrarUsuarioPorPrimeraVez(res)
        }
        val intent = Intent(this, Menu::class.java)
        val currentUser = FirebaseAuth.getInstance().currentUser
        intent.putExtra("userId", currentUser?.uid)
        intent.putExtra("email", currentUser?.email)
        startActivity(intent)
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        val userMap = hashMapOf(
            "userId" to currentUser?.uid,
            "email" to currentUser?.email,
            "puntacion" to 0
        )

        db.collection("users").document(currentUser?.uid ?: "")
            .set(userMap)
    }
}