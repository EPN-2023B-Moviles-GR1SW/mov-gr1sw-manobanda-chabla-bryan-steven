package com.example.proyecto_iib

import java.io.Serializable

class Usuario(
    var email: String?,
    var puntuacion: String?,
): Serializable {
    constructor() : this(
        "",
        "",
    )

    override fun toString(): String {
        return "${email} \n Puntuacion: ${puntuacion}"
    }

}