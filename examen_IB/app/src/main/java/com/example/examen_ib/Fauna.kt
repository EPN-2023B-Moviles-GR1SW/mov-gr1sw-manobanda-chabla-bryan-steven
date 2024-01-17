package com.example.examen_ib

import java.io.Serializable
import java.util.Date

class Fauna(
    var id: Int,
    var idZoologico: Int,
    var nombre: String?,
    var fechaNacimiento: Date?,
    var cantidad: Int?,
    var esDepredador: Boolean,
    var tipo: String?
): Serializable {
    constructor() : this(
        0,
        0,
        null,
        null,
        null,
        false,
        null
    )

    override fun toString(): String {
        return "${nombre}"
    }

}