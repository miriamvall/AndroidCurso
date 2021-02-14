package com.curso.androidcurso.model

import android.content.Context

object GestorPartidas {

    // components

    private var partidas: List<Partida> = emptyList()

    private lateinit var context: Context

    fun provideContext(context: Context) {
        this.context = context
    }


    fun addPartida(partida: Partida){
        val newDataset = partidas.toMutableList()
        newDataset.add(partida)
        this.partidas = newDataset
    }

    fun consultarPartidas() : List<Partida>{
        return partidas
    }

}