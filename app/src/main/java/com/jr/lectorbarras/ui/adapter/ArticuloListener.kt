package com.jr.lectorbarras.ui.adapter

import com.jr.lectorbarras.data.model.ArticuloResponse
import com.jr.lectorbarras.data.model.ArticulosJson

interface ArticuloListener {

    fun onArticulosClicked(articulosJson: ArticulosJson, position: Int)
}