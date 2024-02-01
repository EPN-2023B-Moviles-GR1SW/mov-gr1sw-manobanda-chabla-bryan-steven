package com.example.deber_02

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
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat

class ListViewFauna : AppCompatActivity() {
    var idItemSeleccionado = -1

    private lateinit var  arregloE: ArrayAdapter<Fauna>
    private lateinit var  fauna: ArrayList<Fauna>
    var idLigaAux = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_fauna)

        EBaseDatos.BDatos = ESqliteHelper(this)

        val zooSeleccionado = intent.getSerializableExtra("zoologico") as Zoologico
        val zooId = zooSeleccionado.id

        val nameZoo = findViewById<TextView>(R.id.tv_zoologico)
        nameZoo.text = zooSeleccionado.nombre

        if(zooId != -1){
            fauna = obtenerFaunaZoo(zooId)

            val listView = findViewById<ListView>(R.id.lv_item_fauna)
            arregloE = ArrayAdapter(this, android.R.layout.simple_list_item_1, fauna)
            listView.adapter = arregloE
            arregloE.notifyDataSetChanged()

            registerForContextMenu(listView)
            idLigaAux = zooId

        } else{
            Toast.makeText(this, "Error al obtener el ID del Zoo", Toast.LENGTH_SHORT).show()
        }

        val botonAnadirFauna = findViewById<Button>(
            R.id.btn_lv_crear_fauna
        )
        botonAnadirFauna.setOnClickListener {
            val idZoo = zooSeleccionado.id
            val intent =Intent(this, FormularioFauna::class.java)
            intent.putExtra("idZoo",idZoo)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val faunaActualida = obtenerFaunaZoo(idLigaAux)
        arregloE.clear()
        arregloE.addAll(faunaActualida)
        arregloE.notifyDataSetChanged()
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

        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicionSeleccionada = info.position
        val faunaSeleccionado = fauna[posicionSeleccionada]
        val idFaunaSeleccionado = faunaSeleccionado.id

        return when (item.itemId){
            R.id.mi_editar_fauna -> {
                val intent = Intent(this, FormularioActualizarFauna::class.java)
                intent.putExtra("fauna", faunaSeleccionado)
                startActivity(intent)
                return true
            }
            R.id.mi_eliminar_fauna -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Eliminar Fauna")
                builder.setMessage("¿Estás seguro de que deseas eliminar fauna:")
                builder.setPositiveButton("Aceptar") { _, _ ->
                    val dbHelper = ESqliteHelper(this)
                    val conf = dbHelper.eliminarFaunaFormulario(idFaunaSeleccionado)
                    dbHelper.close()
                    fauna.removeAt(posicionSeleccionada)
                    arregloE.notifyDataSetChanged()

                }
                builder.setNegativeButton(
                    "Cancelar",
                    null
                )

                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun obtenerFaunaZoo(faunaId: Int): ArrayList<Fauna> {
        val dbHelperEquipo = ESqliteHelper(this)
        val fauna = dbHelperEquipo.obtenerFaunaDeZoo(faunaId)
        dbHelperEquipo.close()
        return fauna
    }
}