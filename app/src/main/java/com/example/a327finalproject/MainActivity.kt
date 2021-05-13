package com.example.a327finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    //роаспарсенный обьект из апи
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRequest()
    }

    private fun initRequest(){
        val gson = Gson()
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/567189?api_key=${API.key}")
            .build()

        client.newCall(request).enqueue(object : Callback {
            //Переопределяем метод, что будет, если мы не сможем получить ответ на запрос
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            //Переопределяем метод, что будет, если мы сможем получить ответ на запрос
            override fun onResponse(call: Call, response: Response) {
                //Здесь тоже надо обернуть в try-catch
                try {
                    val responseBody = response.body()
                    movie = gson.fromJson(responseBody?.string(), Movie::class.java)
                    println(" !!! ${movie?.title}")
                    println(" !!! ${movie?.genres}")
                    println(" !!! ${movie?.overview}")
                } catch (e: Exception) {
                    println(response)
                    e.printStackTrace()
                }
            }
        })
    }

}