package com.curso.androidcurso.model

import java.util.*
import java.io.Serializable

class Partida(
    private val name: String,
    private val score: String,
    private val date: String
): Serializable {
    fun getName(): String {
        return this.name
    }
    fun getDate(): String {
        return this.date
    }

    fun getPointsAsTime(): String {
        val min: String = this.score.substring(0,2)
        val sec: String = this.score.substring(3)
        return min.plus(sec)
    }

    fun getPuntuacion(): String{
        return this.score
    }
}