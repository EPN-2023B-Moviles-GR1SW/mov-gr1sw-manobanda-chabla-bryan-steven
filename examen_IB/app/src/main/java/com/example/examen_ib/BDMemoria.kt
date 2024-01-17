package com.example.examen_ib

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class BDMemoria {
    companion object{
        var arregloZoologico: MutableList<Zoologico> = mutableListOf()
        var arregloFauna: MutableList<Fauna> = mutableListOf()
        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")

        init {
            arregloZoologico
                .add(
                    Zoologico(1,"Zoológico de San Diego","San Diego",1000,40.2,"Renombrado por su conservación y hábitats naturales para los animales.")
                )
            arregloZoologico
                .add(
                    Zoologico(2,"Zoológico de Chapultepec","Mexico City",800,35.0,"Situado en el Bosque de Chapultepec")
                )
            arregloZoologico
                .add(
                    Zoologico(3,"Zoológico de Berlín","Berlín",700,38.9,"Uno de los zoológicos más antiguos del país con importantes programas de conservación.")
                )

            arregloFauna
                .add(
                    Fauna(1,1,"Tigre",dateFormat.parse("15-07-2012"),2,true,"Felino")
                )
            arregloFauna
                .add(
                    Fauna(2,1,"Mono Araña",dateFormat.parse("23-04-2015"),5,false,"Mamífero")
                )
            arregloFauna
                .add(
                    Fauna(3,2,"Elefante",dateFormat.parse("17-11-2019"),15,false,"mamifero")
                )
            arregloFauna
                .add(
                    Fauna(4,2,"León Blanco",dateFormat.parse("20-09-2014"),4,true,"Felino")
                )
            arregloFauna
                .add(
                    Fauna(5,3,"Oso Panda",dateFormat.parse("10-06-2013"),2,false,"Mamífero")
                )
            arregloFauna
                .add(
                    Fauna(6,3,"Jaguar",dateFormat.parse("14-03-2016"),3,true,"Felino")
                )
        }
    }
}