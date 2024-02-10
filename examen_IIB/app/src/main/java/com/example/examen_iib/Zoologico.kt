package com.example.examen_iib

import java.io.Serializable

class Zoologico (
    var id: String,
    var nombre: String?,
    var ubicacion: String?,
    var capacidad: Int?,
    var tamanio: Double?,
    var descripcion: String?,
) : Serializable {
    constructor() : this(
        "",
        null,
        null,
        null,
        null,
        null
    )

    override fun toString(): String {
        return "${nombre}"
    }

}