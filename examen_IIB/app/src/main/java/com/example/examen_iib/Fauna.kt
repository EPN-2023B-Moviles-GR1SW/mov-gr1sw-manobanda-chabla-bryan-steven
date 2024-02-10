package com.example.examen_iib

import java.io.Serializable
import java.util.Date

class Fauna(
    var id: String,
    var nombre: String?,
    var fechaNacimiento: String?,
    var cantidad: Int?,
    var esDepredador: Boolean,
    var tipo: String?
): Serializable {
    constructor() : this(
        "",
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