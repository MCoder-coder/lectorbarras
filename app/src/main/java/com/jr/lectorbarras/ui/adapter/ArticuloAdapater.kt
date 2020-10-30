package com.jr.lectorbarras.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ArticulosJson

class ArticuloAdapater(val articulosList: List<ArticulosJson>, val artListenr: ArticuloListener ): RecyclerView.Adapter<ArticuloAdapater.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_articulos, parent, false
        )
    )

    override fun onBindViewHolder(holder: ArticuloAdapater.ViewHolder, position: Int) {
        Log.i("tag adapter", "onBindViewHolder")
        val art = articulosList[position]
        holder.tvIdArticulo.text = art.nombre
        holder.tvCodeBarras.text = art.codbarras
        holder.preciolista1.text = art.precio_lista_1.toString()
        holder.stock.text =  art.stock.toString()
        holder.codigositema.text = art.cod_articulo
        holder.itemView.setOnClickListener {

            artListenr.onArticulosClicked(art, position)

        }
    }

    override fun getItemCount(): Int {
        Log.i("tag adapter", "getImtemCount")
        Log.i("tag adapter", articulosList.toString())
        return articulosList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvIdArticulo = itemView.findViewById<TextView>(R.id.IdArticulo)
        var tvCodeBarras = itemView.findViewById<TextView>(R.id.codigobarras)
        val preciolista1 = itemView.findViewById<TextView>(R.id.preciolista1)
        val stock = itemView.findViewById<TextView>(R.id.tvstock)
        val codigositema = itemView.findViewById<TextView>(R.id.codigosistema)

    }


}