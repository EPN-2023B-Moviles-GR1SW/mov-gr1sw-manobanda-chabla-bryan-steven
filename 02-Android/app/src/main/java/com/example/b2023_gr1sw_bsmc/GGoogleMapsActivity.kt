package com.example.b2023_gr1sw_bsmc

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class GGoogleMapsActivity : AppCompatActivity() {

    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync {
            googleMap ->
            with(googleMap){
                establecerConfiguracionMapa()
            }
        }
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val permisoFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisoFineLocation == PackageManager
                .PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled=true
        }
    }

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
        val permisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation =ContextCompat
            .checkSelfPermission(
                contexto,
                permisoFineLocation // Permiso a chequear
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permisoFineLocation, permisoCoarse), //ARREGLO DE PERMISOS,
                1 // CODIGO PETICION DE PERMISOS
            )
        }
    }
}