package com.example.b2023_gr1sw_bsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion(
    private val contexto: FRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val reciclerView: RecyclerView
): RecyclerView.Adapter<FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder>() {
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val nombreTexView: TextView
        val descripcionTextView: TextView
        val likesTextView: TextView
        val actionButton: Button
        var numeroLikes = 0

        init {
            nombreTexView = view.findViewById(R.id.tv_nombre)
            descripcionTextView = view.findViewById(R.id.tv_descripcion)
            likesTextView = view.findViewById(R.id.tv_likes)
            actionButton = view.findViewById(R.id.btn_dar_like)
            actionButton.setOnClickListener { anadirLike() }
        }

        fun anadirLike(){
            numeroLikes = numeroLikes +1
            likesTextView.text = numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder,
        position: Int
    ) {
        val entrenadorActual = this.lista[position]
        holder.nombreTexView.text = entrenadorActual.nombre
        holder.descripcionTextView.text = entrenadorActual.descripcion
        holder.likesTextView.text ="0"
        holder.actionButton.text = "ID: ${entrenadorActual.id} "+
                "Nombre: ${entrenadorActual.nombre}"
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

}