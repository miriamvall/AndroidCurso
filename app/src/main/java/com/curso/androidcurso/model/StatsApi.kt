package com.curso.androidcurso.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object StatsApi {

    private val ip = "15.237.86.120"
    private val client = OkHttpClient()

    fun getAllRecords(callback: (List<Partida>) -> Unit, error: () -> Unit) {
        val requestGet = Request.Builder().get().url("http://$ip:8080/scores").build()

        client.newCall(requestGet).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body!!

                val json = Gson().fromJson(responseBody.string(), JsonObject::class.java)
                val jsonScores = json.getAsJsonArray("records")

                val newScores = mutableListOf<Partida>()

                for(scoreJson in jsonScores) {
                    newScores.add(Gson().fromJson(scoreJson, Partida::class.java))
                }
                callback(newScores)
            }

            override fun onFailure(call: Call, e: IOException) {
                println(e.toString())
                error()
            }
        })
    }

    fun addNewRecord(record: Partida, success: () -> Unit, error: () -> Unit){
        val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val recordJson = Gson().toJson(record).toRequestBody(jsonMediaType)

        val requestPost = Request
                .Builder()
                .post(recordJson)
                .url("http://$ip:8080/scores")
                .build()

        client.newCall(requestPost).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Error whe adding new record")
                println(e.toString())
                e.printStackTrace()
                error()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    println("New record added to API")
                    success()
                } else {
                    println("Error when adding new record")
                    println(response.toString())
                    println(response.body?.toString())
                    error()
                }
            }
        })
    }






}