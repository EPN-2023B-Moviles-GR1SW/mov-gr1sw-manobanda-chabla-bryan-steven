package com.example.examen_iib

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spanned
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListViewZoologico : AppCompatActivity() {
    val listaZoologico:ArrayList<Zoologico> = arrayListOf<Zoologico>()
    var idItemSeleccionado = 0

    val crearZoologicoActivityResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    val db = Firebase.firestore
                    val zoologico = db.collection("zoologico")
                    val zoologicoInsertar = hashMapOf(
                        "nombre" to data?.getStringExtra("nombreModificado").toString(),
                        "ubicacion" to data?.getStringExtra("ubicacionModificado").toString(),
                        "capacidad" to data?.getIntExtra("capacidadModificada", 0),
                        "tamanio" to data?.getDoubleExtra("tamanioModificado",0.0),
                        "descripcion" to data?.getStringExtra("descripcionModificada").toString()
                    )
                    zoologico
                        .add(zoologicoInsertar)
                        .addOnSuccessListener {  }
                        .addOnFailureListener(){  }

                    actualizarListView()
                }
            }
        }

    val editarZoologicoActivityResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){

                    val data = result.data
                    val db = Firebase.firestore

                    val zoologicoRef = db.collection("zoologico").document(data?.getStringExtra("idDevuelto")?.toString()!!)
                    val zoologicoActualizado = hashMapOf(
                        "nombre" to data?.getStringExtra("nombreModificado").toString(),
                        "ubicacion" to data?.getStringExtra("ubicacionModificado").toString(),
                        "capacidad" to data?.getIntExtra("capacidadModificada", 0),
                        "tamanio" to data?.getDoubleExtra("tamanioModificado",0.0),
                        "descripcion" to data?.getStringExtra("descripcionModificada").toString()
                    )

                    zoologicoRef.set(
                        zoologicoActualizado
                    )
                        .addOnSuccessListener {  }
                        .addOnFailureListener(){  }

                    actualizarListView()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_zoologico)

        actualizarListView()

        val botonAnadirZoo = findViewById<Button>(
            R.id.btn_lv_crear_zoo
        )
        botonAnadirZoo.setOnClickListener {
            irActividad(FormularioZoologico::class.java)
        }

        val tvInfoZoologico = findViewById<TextView>(R.id.tv_info_zoo)
        val listViewZoologico = findViewById<ListView>(R.id.lv_item_zoo)
        listViewZoologico.setOnItemClickListener { _, _, position, _ ->
            val zoologicoSeleccionado = listaZoologico[position]
            tvInfoZoologico.text = obtenerInformacionZoologico(zoologicoSeleccionado)
            tvInfoZoologico.visibility = View.VISIBLE
        }
    }

    fun actualizarListView(){
        val listViewZoologico = findViewById<ListView>(R.id.lv_item_zoo)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            this.listaZoologico
        )

        listViewZoologico.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listViewZoologico)

        consultarZoologico(adaptador)
    }

    private fun consultarZoologico(adaptador: ArrayAdapter<Zoologico>){
        val db = Firebase.firestore
        val zoologicoRefUnico = db.collection("zoologico")

        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        zoologicoRefUnico
            .get()
            .addOnSuccessListener {
                for (zoo in it){
                    anadirZoologico(zoo, zoo.id)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener(){
            }
    }

    private fun anadirZoologico(
        zoologico: QueryDocumentSnapshot,
        idZoologico: String
    ){
        val nuevoZoologico = Zoologico(
            idZoologico.toString(),
            zoologico.data.get("nombre").toString() as String,
            zoologico.data.get("ubicacion").toString() as String,
            zoologico.data.get("capacidad").toString().toInt() as Int,
            zoologico.data.get("tamanio").toString().toDouble() as Double,
            zoologico.data.get("descripcion").toString() as String
        )

        this.listaZoologico.add(nuevoZoologico)
    }

    private fun limpiarArreglo() {
        this.listaZoologico.clear()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_zoologico, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_zoo -> {
                abrirConParametrosEditarLF(FormularioActualizarZoo::class.java)
                return true
            }
            R.id.mi_eliminar_zoo -> {
                abrirDialogo()
                actualizarListView()
                return true
            }
            R.id.mi_mostrar_fauna -> {
                try {
                    abrirActividadListarLF(ListViewFauna::class.java)
                } catch (e: Exception) {
                    val errorMessage = "Ocurrió un error: ${e.message}"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirConParametrosEditarLF(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        val zoologico = listaZoologico.get(idItemSeleccionado)
        intentExplicito.putExtra("id", zoologico.id)
        intentExplicito.putExtra("nombre", zoologico.nombre)
        intentExplicito.putExtra("ubicacion", zoologico.ubicacion)
        intentExplicito.putExtra("capacidad", zoologico.capacidad)
        intentExplicito.putExtra("tamanio", zoologico.tamanio)
        intentExplicito.putExtra("descripcion", zoologico.descripcion)

        editarZoologicoActivityResult.launch(intentExplicito)
    }

    fun abrirActividadListarLF(clase: Class<*>){
        val intentExplicito = Intent(this, clase)

        intentExplicito.putExtra("idZoo", listaZoologico.get(idItemSeleccionado).id)
        intentExplicito.putExtra("nombreZoo", listaZoologico.get(idItemSeleccionado).nombre)

        startActivity(intentExplicito)
    }


    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Zoológico")
        builder.setMessage("¿Estás seguro de que deseas eliminar el zoológico ${listaZoologico[idItemSeleccionado].nombre}?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            val zoologico = listaZoologico.get(idItemSeleccionado)
            val db = Firebase.firestore
            val zooRef = db.collection("zoologico").document(zoologico.id!!.toString())
            zooRef.delete()
                .addOnSuccessListener {  }
                .addOnFailureListener {  }
            actualizarListView()
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        crearZoologicoActivityResult.launch(intent)
    }

    private fun obtenerInformacionZoologico(zoologico: Zoologico): Spanned {
        val info = ("<b>Nombre:</b> ${zoologico.nombre}<br/>" +
                "<b>Ubicación:</b> ${zoologico.ubicacion}<br/>" +
                "<b>Descripción:</b> ${zoologico.descripcion}<br/>" +
                "<b>Capacidad:</b> ${zoologico.capacidad}<br/>" +
                "<b>Tamaño:</b> ${zoologico.tamanio}")
        return HtmlCompat.fromHtml(info, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}