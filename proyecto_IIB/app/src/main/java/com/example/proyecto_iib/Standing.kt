package com.example.proyecto_iib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Standing : AppCompatActivity() {
    var listaUsuarios: ArrayList<Usuario> = arrayListOf<Usuario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standing)

        actualizarListView()
    }

    fun actualizarListView(){
        val listViewE = findViewById<ListView>(R.id.lv_item_user)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaUsuarios!!
        )

        listViewE.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listViewE)

        consultarFauna(adaptador)
    }

    private fun consultarFauna(adaptador: ArrayAdapter<Usuario>) {
        val db = Firebase.firestore
        val faunaRefUnico = db.collection("users")

        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        faunaRefUnico
            .get()
            .addOnSuccessListener {
                for (fauna in it){
                    anadirFauna(fauna, fauna.id)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {  }
    }

    private fun anadirFauna(fauna: QueryDocumentSnapshot, idFauna: String) {
        val nuevaUsuario = Usuario(
            fauna.data.get("email").toString() as String,
            fauna.data.get("puntacion").toString() as String,
        )
        this.listaUsuarios.add(nuevaUsuario)
    }

    private fun limpiarArreglo() {
        this.listaUsuarios.clear()
    }
}