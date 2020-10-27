package com.jr.lectorbarras.ui.adapter

import com.jr.lectorbarras.data.model.ArticuloResponse

interface ArticuloListener {

    fun onArticulosClicked(articuloResponse: ArticuloResponse , position: Int)
}