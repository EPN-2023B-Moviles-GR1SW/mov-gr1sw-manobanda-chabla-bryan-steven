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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class ListViewFauna : AppCompatActivity() {
    var listaFauna: ArrayList<Fauna> = arrayListOf<Fauna>()
    var idItemSeleccionado = 0
    var idZoologico: String? = null

    val callBackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    val db = Firebase.firestore

                    val faunaRef = db.collection("zoologico")
                        .document(data?.getStringExtra("idZooDevuelto").toString())
                        .collection("fauna")
                        .document(data?.getStringExtra("idDevuelto").toString())

                    val faunaActualizada = hashMapOf(
                        "nombre" to data?.getStringExtra("nombreModificado").toString(),
                        "fechaNacimiento" to data?.getStringExtra("fechaNacimientoModificada").toString(),
                        "cantidad" to data?.getIntExtra("cantidadModificado",0),
                        "esDepredador" to data?.getBooleanExtra("esDepredadorModificado",false),
                        "tipo" to data?.getStringExtra("tipoModificado").toString()

                    )

                    faunaRef.set(
                        faunaActualizada
                    )
                        .addOnSuccessListener {  }
                        .addOnFailureListener(){  }

                    actualizarListView()
                }
            }
        }

    val callBackContenidoIntentExplicito1 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){

                    val data = result.data
                    val db = Firebase.firestore

                    val faunaRefUnica = db.collection("zoologico").document(data?.getStringExtra("idZooDevuelto")?.toString()!!).collection("fauna")

                    val faunaActualizada = hashMapOf(
                        "nombre" to data?.getStringExtra("nombreModificado").toString(),
                        "fechaNacimiento" to data?.getStringExtra("fechaNacimientoModificada").toString(),
                        "cantidad" to data?.getIntExtra("cantidadModificado",0),
                        "esDepredador" to data?.getBooleanExtra("esDepredadorModificado",false),
                        "tipo" to data?.getStringExtra("tipoModificado").toString()
                    )

                    faunaRefUnica
                        .add(faunaActualizada)
                        .addOnSuccessListener {  }
                        .addOnFailureListener(){  }

                    actualizarListView()
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_fauna)

        this.idZoologico = intent.getStringExtra("idZoo")

        val nameZoo = findViewById<TextView>(R.id.tv_zoologico)
        nameZoo.setText(intent.getStringExtra("nombreZoo"))

        actualizarListView()

        val botonAnadirFauna = findViewById<Button>(
            R.id.btn_lv_crear_fauna
        )
        botonAnadirFauna.setOnClickListener {
            irActividad(FormularioFauna::class.java)
        }

        val tvInfoFauna = findViewById<TextView>(R.id.tv_info_fauna)
        val listView = findViewById<ListView>(R.id.lv_item_fauna)
        listView.setOnItemClickListener { _, _, position, _ ->
            val faunaSeleccionada = listaFauna[position]
            tvInfoFauna.text = obtenerInformacionFauna(faunaSeleccionada)
            tvInfoFauna.visibility = View.VISIBLE
        }

        //registerForContextMenu(listView)
    }

    fun actualizarListView(){
        val listViewE = findViewById<ListView>(R.id.lv_item_fauna)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaFauna!!
        )

        listViewE.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listViewE)

        consultarFauna(adaptador)
    }

    private fun consultarFauna(adaptador: ArrayAdapter<Fauna>) {
        val db = Firebase.firestore
        val faunaRefUnico = db.collection("zoologico").document(this.idZoologico!!).collection("fauna")

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
        val nuevaFauna = Fauna(
            idFauna,
            fauna.data.get("nombre").toString() as String,
            fauna.data.get("fechaNacimiento").toString() as String,
            fauna.data.get("cantidad").toString().toInt() as Int,
            fauna.data.get("esDepredador").toString().toBoolean() as Boolean,
            fauna.data.get("tipo").toString() as String,
        )
        this.listaFauna.add(nuevaFauna)
    }

    private fun limpiarArreglo() {
        this.listaFauna.clear()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_fauna, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_fauna -> {
                abrirConParametrosEditarFauna(FormularioActualizarFauna::class.java)
                return true
            }
            R.id.mi_eliminar_fauna -> {
                abrirDialogo()
                actualizarListView()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirConParametrosEditarFauna(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        val fauna = listaFauna?.get(idItemSeleccionado)

        intentExplicito.putExtra("idZoologico", this.idZoologico)
        intentExplicito.putExtra("id", fauna?.id)
        intentExplicito.putExtra("nombre", fauna?.nombre)
        intentExplicito.putExtra("fechaNacimiento", fauna?.fechaNacimiento)
        intentExplicito.putExtra("cantidad", fauna?.cantidad)
        intentExplicito.putExtra("esDepredador", fauna?.esDepredador)
        intentExplicito.putExtra("tipo", fauna?.tipo)

        callBackContenidoIntentExplicito.launch(intentExplicito)

    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        intent.putExtra("id", this.idZoologico)
        callBackContenidoIntentExplicito1.launch(intent)
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Fauna")
        builder.setMessage("¿Estás seguro de que deseas eliminar fauna: ${listaFauna[idItemSeleccionado].nombre}?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            val faunaEliminado = listaFauna.get(idItemSeleccionado)
            val db = Firebase.firestore
            val relojRef = db.collection("zoologico")
                .document(this.idZoologico!!)
                .collection("fauna")
                .document(faunaEliminado.id!!)

            relojRef
                .delete()
                .addOnSuccessListener{}
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

    private fun obtenerInformacionFauna(fauna: Fauna): Spanned {
        val info = ("<b>Nombre:</b> ${fauna.nombre}<br/>" +
                "<b>Tipo:</b> ${fauna.tipo}<br/>" +
                "<b>Fecha de nacimiento:</b> ${fauna.fechaNacimiento}<br/>" +
                "<b>Cantidad:</b> ${fauna.cantidad}<br/>" +
                "<b>Es depredador:</b> ${fauna.esDepredador}")
        return HtmlCompat.fromHtml(info, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}