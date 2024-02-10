package com.example.deber_03_tiktok

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


class TikTokAdapter(private val dataList: List<TikTokItemModel>) : RecyclerView.Adapter<TikTokAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val comentarioImageView: ImageView = itemView.findViewById(R.id.btn_comentario)
        private val likeImageView: ImageView = itemView.findViewById(R.id.btn_like)
        private val shareImageView: ImageView = itemView.findViewById(R.id.btn_share)
        private val favImageView: ImageView = itemView.findViewById(R.id.btn_fav)
        private val personaCircleImageView: CircleImageView = itemView.findViewById(R.id.btn_persona)
        private val agregarImageView: ImageView = itemView.findViewById(R.id.btn_agregar)
        private val cancionTextView: TextView = itemView.findViewById(R.id.txt_cancion)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.txt_descripcion)
        private val usuarioTextView: TextView = itemView.findViewById(R.id.txt_usuario)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        fun bind(item: TikTokItemModel) {
            comentarioImageView.setImageResource(item.comentarioResId)
            likeImageView.setImageResource(item.likeResId)
            shareImageView.setImageResource(item.shareResId)
            favImageView.setImageResource(item.favResId)
            personaCircleImageView.setImageResource(item.personaResId)
            cancionTextView.text = item.cancion
            descripcionTextView.text = item.descripcion
            usuarioTextView.text = item.usuario
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_tik_tok_adapter, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        val soundDiscoImageView: ImageView = holder.itemView.findViewById(R.id.img_disco)
        Glide.with(holder.itemView).asGif().load(R.drawable.disco).into(soundDiscoImageView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}