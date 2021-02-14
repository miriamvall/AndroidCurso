package com.curso.androidcurso.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.curso.androidcurso.R
import com.curso.androidcurso.model.GestorPartidasDB
import com.curso.androidcurso.model.Partida

class StatsAdapter: RecyclerView.Adapter<StatsAdapter.PartidaViewHolder>() {

    var dataset: List<Partida> = GestorPartidasDB.consultarPartidas()

    inner class PartidaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nombreTextView = itemView.findViewById<TextView>(R.id.name)
        val puntuacionTextView = itemView.findViewById<TextView>(R.id.points)
        val dateTextView = itemView.findViewById<TextView>(R.id.date)

        fun renderPartida(par: Partida) {
            nombreTextView.text = par.getName()
            puntuacionTextView.text = par.getPuntuacion()
            dateTextView.text = par.getDate()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsAdapter.PartidaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.stats_layout, parent, false)
        return PartidaViewHolder((view))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: StatsAdapter.PartidaViewHolder, position: Int) {
        holder.renderPartida(dataset[position])
    }

    fun setData(newDataset: List<Partida>) {
        dataset = newDataset.toMutableList().sortedBy { it.getPuntuacion() }
        notifyDataSetChanged()
    }


}