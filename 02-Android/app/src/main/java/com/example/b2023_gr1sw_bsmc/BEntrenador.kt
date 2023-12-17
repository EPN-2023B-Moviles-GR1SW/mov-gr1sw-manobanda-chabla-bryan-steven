package com.example.b2023_gr1sw_bsmc

class BEntrenador(
    var id: Int,
    var descripcion: String?,
    var nombre: String?
) {
    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}