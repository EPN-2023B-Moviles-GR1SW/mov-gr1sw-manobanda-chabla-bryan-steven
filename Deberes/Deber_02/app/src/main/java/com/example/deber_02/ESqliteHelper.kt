package com.example.deber_02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date

class ESqliteHelper(contexto: Context?):SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Crear la tabla "ZOOLOGICO"
        val scriptSQLCrearTablaZoologico =
            """
                CREATE TABLE ZOOLOGICO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                ubicacion VARCHAR(50),
                capacidad INTEGER,
                tamanio DOUBLE,
                descripcion VARCHAR(50) 
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaZoologico)

        val scriptSQLCrearTablaFauna =
            """
                CREATE TABLE FAUNA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                idZoologico INTEGER,
                nombre VARCHAR(50),
                fechaNacimiento VARCHAR(50),
                cantidad INTEGER,
                esDepredador BOOLEAN,
                tipo VARCHAR(50),
                FOREIGN KEY (idZoologico) REFERENCES ZOOLOGICO(id) ON DELETE CASCADE 
                )
            
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaFauna)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    //Crear una nuevo zoo en la BD
    fun crearZoologico(
        nombre: String,
        ubicacion: String,
        capacidad: Int?,
        tamanio: Double?,
        descripcion: String?,
    ): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("ubicacion", ubicacion)
        valoresAGuardar.put("capacidad", capacidad)
        valoresAGuardar.put("tamanio", tamanio)
        valoresAGuardar.put("descripcion", descripcion)

        // Insertar los valores en la tabla "ZOOLOGICO"
        val resultadoGuardar = basedatosEscritura
            .insert(
                "ZOOLOGICO",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    // Método para eliminar un zoo de la BD
    fun eliminarZooFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        // Eliminar zpp con el ID proporcionado
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ZOOLOGICO",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarZooFormulario(
        nombre: String,
        ubicacion: String,
        capacidad: Int?,
        tamanio: Double?,
        descripcion: String?,
        id: Int,
    ): Boolean{

        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("ubicacion", ubicacion)
        valoresAActualizar.put("capacidad", capacidad)
        valoresAActualizar.put("tamanio", tamanio)
        valoresAActualizar.put("descripcion", descripcion)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadosActualizacion = conexionEscritura
            .update(
                "ZOOLOGICO",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadosActualizacion.toInt() == -1) false else true
    }

    fun obtenerTodosZoo(): ArrayList<Zoologico> {
        // Obtener una instancia de la base de datos en modo lectura
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM ZOOLOGICO
    """.trimIndent()
        // Ejecutar la consulta
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val arregloZoo= ArrayList<Zoologico>()
        // Comprobar si la consulta devolvió resultados
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val ubicacion = resultadoConsultaLectura.getString(2)
                val capacidad = resultadoConsultaLectura.getInt(3)
                val tamanio = resultadoConsultaLectura.getDouble(4)
                val descripcion = resultadoConsultaLectura.getString(5)

                val zoo = Zoologico(id, nombre, ubicacion, capacidad, tamanio, descripcion)
                arregloZoo.add(zoo)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return arregloZoo
    }


    fun crearFauna(
        idZoologico: Int,
        nombre: String?,
        fechaNacimiento: Date?,
        cantidad: Int?,
        esDepredador: Boolean,
        tipo: String?
        ): Boolean{
        val esDepredador = if(esDepredador) 1 else 0

        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val fecha = dateFormat.format(fechaNacimiento)
        valoresAGuardar.put("idZoologico", idZoologico)
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaNacimiento", fecha)
        valoresAGuardar.put("cantidad", cantidad)
        valoresAGuardar.put("esDepredador", esDepredador)
        valoresAGuardar.put("tipo", tipo)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "FAUNA",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true

    }

    fun eliminarFaunaFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "FAUNA",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarFaunaFormulario(
        idFauna: Int,
        nombre: String?,
        fechaNacimiento: Date?,
        cantidad: Int?,
        esDepredador: Boolean,
        tipo: String?
        ): Boolean{

        val depredador = if(esDepredador) 1 else 0

        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val fecha = dateFormat.format(fechaNacimiento)
        valoresAActualizar.put("id", idFauna)
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaNacimiento", fecha)
        valoresAActualizar.put("cantidad", cantidad)
        valoresAActualizar.put("esDepredador", depredador)
        valoresAActualizar.put("tipo", tipo)

        val parametrosConsultaActualizar = arrayOf(idFauna.toString())
        val resultadosActualizacion = conexionEscritura
            .update(
                "FAUNA",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadosActualizacion.toInt() == -1) false else true
    }


    fun obtenerFaunaDeZoo(zooId: Int): ArrayList<Fauna> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura =
            """
        SELECT * FROM FAUNA WHERE idZoologico = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(zooId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)

        val arregloFauna = ArrayList<Fauna>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val id = resultadoConsultaLectura.getInt(0)
                val idZoologico = resultadoConsultaLectura.getInt(1)
                val nombre = resultadoConsultaLectura.getString(2)
                val fechaNacimiento = resultadoConsultaLectura.getString(3)
                val cantidad = resultadoConsultaLectura.getInt(4)
                val esDepredador = resultadoConsultaLectura.getInt(5) == 1
                val tipo = resultadoConsultaLectura.getString(6)
                val fecha = dateFormat.parse(fechaNacimiento)
                val equipo = Fauna(id, idZoologico, nombre, fecha, cantidad, esDepredador, tipo)
                arregloFauna.add(equipo)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return arregloFauna
    }




}