package com.example.proyecto_iib

import android.app.Dialog
import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Random

class Escenario : AppCompatActivity() {
    lateinit var tvContador: TextView
    lateinit var tvNombre: TextView
    lateinit var tvTiempo: TextView
    lateinit var imgZombie: ImageView
    var contador = 0
    lateinit var aleatorio: Random
    var anchoPantalla = 0
    var altoPantalla = 0
    lateinit var miDialog: Dialog
    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escenario)
        tvContador = findViewById(R.id.tv_zombie_kill)
        tvNombre = findViewById(R.id.tv_player_name)
        tvTiempo = findViewById(R.id.tv_time)
        imgZombie = findViewById(R.id.iv_zombie)
        val correoString = intent.getStringExtra("email")

        tvNombre.text = correoString

        miDialog = Dialog(this@Escenario)

        Pantalla()
        CuentaAtras()

        imgZombie.setOnClickListener(View.OnClickListener {
            if (!gameOver){
                contador++
                tvContador.setText(contador.toString())
                Handler().postDelayed({
                    Movimiento()
                }, 200)
            }
        })
    }

        private fun Pantalla(){
            val windowManager = windowManager
            val display = windowManager.defaultDisplay
            val point = Point()
            display.getSize(point)
            anchoPantalla = point.x
            altoPantalla = point.y
            aleatorio = Random()
        }

        private fun Movimiento(){
            val min = 0
            val MaximoX = anchoPantalla - imgZombie.width
            val MaximoY = altoPantalla - imgZombie.height

            val randomX = aleatorio.nextInt(((MaximoX-min)+1)+min)
            val randomY = aleatorio.nextInt(((MaximoY-min)+1)+min)

            imgZombie.x = randomX.toFloat()
            imgZombie.y = randomY.toFloat()
        }

        private fun CuentaAtras(){
            object : CountDownTimer(10000, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    val segundosRestantes = millisUntilFinished / 1000
                    tvTiempo.setText(segundosRestantes.toString()+"s")
                }

                override fun onFinish() {
                    tvTiempo.setText("0s")
                    gameOver = true
                    MensajeGameOver()
                }
            }.start()
        }

    private fun MensajeGameOver(){
        miDialog.setContentView(R.layout.gameover)
        var killed = miDialog.findViewById<TextView>(R.id.tv_puntaje)
        var again = miDialog.findViewById<Button>(R.id.btn_again)
        var menu = miDialog.findViewById<Button>(R.id.btn_menu)
        killed.setText(contador.toString())

        again.setOnClickListener {
            contador = 0
            miDialog.dismiss()
            tvContador.text = "0"
            gameOver = false
            CuentaAtras()
            Movimiento()
        }

        menu.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val db = FirebaseFirestore.getInstance()

            val userDocRef = db.collection("users").document(currentUser?.uid ?: "")

            userDocRef.update("puntacion", contador)
                .addOnSuccessListener {
                    val intent = Intent(this, Menu::class.java)
                    intent.putExtra("userId", currentUser?.uid)
                    intent.putExtra("email", currentUser?.email)
                    startActivity(intent)
                }
                .addOnFailureListener {}
        }
        miDialog.show()
    }
}