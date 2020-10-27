package com.jr.lectorbarras.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ArticuloResponse

class ArticuloAdapater (val articulosList: ArrayList<ArticuloResponse> ,val articulosListener: ArticuloListener, context: Context): RecyclerView.Adapter<ArticuloAdapater.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_articulos, parent, false
        )
    )

    override fun onBindViewHolder(holder: ArticuloAdapater.ViewHolder, position: Int) {

        val articulos = articulosList[position]
        holder.tvIdArticulo.text = articulos.cod_articulo
        holder.tvCodeBarras.text = articulos.codbarras
        holder.itemView.setOnClickListener{
            articulosListener.onArticulosClicked(articulos, position)
        }

    }

    override fun getItemCount(): Int {
        return articulosList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvIdArticulo = itemView.findViewById<TextView>(R.id.tvIdArticulo)
        var tvCodeBarras = itemView.findViewById<TextView>(R.id.tvcodigobarras)



    }


}