package com.example.examen_ib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat

class ListViewFauna : AppCompatActivity() {
    var arregloFauna = BDMemoria.arregloFauna
    var adaptador: ArrayAdapter<Fauna>? = null
    var idItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_fauna)

        val zooSeleccionado = intent.getSerializableExtra("zoologico") as Zoologico
        arregloFauna = BDMemoria.arregloFauna.filter { it.idZoologico == zooSeleccionado.id }.toMutableList()

        val nameZoo = findViewById<TextView>(R.id.tv_zoologico)
        nameZoo.text = zooSeleccionado.nombre

        val listView = findViewById<ListView>(R.id.lv_item_fauna)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloFauna
        )

        listView.adapter = adaptador
        adaptador!!.notifyDataSetChanged()

        val botonAnadirFauna = findViewById<Button>(
            R.id.btn_lv_crear_fauna
        )
        botonAnadirFauna.setOnClickListener {
            val idZoo = zooSeleccionado.id
            val intent =Intent(this, FormularioFauna::class.java)
            intent.putExtra("idZoo",idZoo)
            startActivity(intent)
        }

        val tvInfoFauna = findViewById<TextView>(R.id.tv_info_fauna)

        listView.setOnItemClickListener { _, _, position, _ ->
            val faunaSeleccionada = arregloFauna[position]
            tvInfoFauna.text = obtenerInformacionFauna(faunaSeleccionada)
            tvInfoFauna.visibility = View.VISIBLE
        }

        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        val zooSeleccionado = intent.getSerializableExtra("zoologico") as Zoologico
        arregloFauna = BDMemoria.arregloFauna.filter { it.idZoologico == zooSeleccionado.id }.toMutableList()
        adaptador?.clear()
        adaptador?.addAll(arregloFauna)
        adaptador?.notifyDataSetChanged()
    }

    fun actualizarLista(
        adaptador: ArrayAdapter<Fauna>?
    ){
        adaptador?.notifyDataSetChanged()
    }

    fun actualizarFauna(){
        val faunaSeleccionado = arregloFauna[idItemSeleccionado]
        val intent = Intent(this, FormularioActualizarFauna::class.java)
        intent.putExtra("fauna", faunaSeleccionado)
        startActivity(intent)
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
                actualizarFauna()
                return true
            }
            R.id.mi_eliminar_fauna -> {
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Fauna")
        builder.setMessage("¿Estás seguro de que deseas eliminar fauna: ${arregloFauna[idItemSeleccionado].nombre}?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            val faunaEliminado = arregloFauna[idItemSeleccionado]
            BDMemoria.arregloFauna.remove(faunaEliminado)
            arregloFauna.removeAt(idItemSeleccionado)
            actualizarLista(adaptador)
            adaptador?.clear()
            adaptador?.addAll(arregloFauna)
            adaptador?.notifyDataSetChanged()
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