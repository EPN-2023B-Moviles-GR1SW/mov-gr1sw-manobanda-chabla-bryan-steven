package com.example.deber_03_tiktok

data class TikTokItemModel(
    val comentarioResId: Int,
    val likeResId: Int,
    val shareResId: Int,
    val favResId: Int,
    val personaResId: Int,
    val cancion: String,
    val descripcion: String,
    val usuario: String
)
