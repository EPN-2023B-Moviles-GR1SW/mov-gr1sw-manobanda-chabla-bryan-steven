package com.example.examen_ib

import java.io.Serializable

class Zoologico (
    var id: Int,
    var nombre: String?,
    var ubicacion: String?,
    var capacidad: Int?,
    var tamanio: Double?,
    var descripcion: String?,
) : Serializable {
    constructor() : this(
        0,
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