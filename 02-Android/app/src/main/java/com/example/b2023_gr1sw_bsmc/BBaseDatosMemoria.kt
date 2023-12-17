package com.example.b2023_gr1sw_bsmc

class BBaseDatosMemoria {

    //EMPEZAR COMPANION OBJECT
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Bryan", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "Andres", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Anahi", "c@c.com")
                )
        }
    }
}