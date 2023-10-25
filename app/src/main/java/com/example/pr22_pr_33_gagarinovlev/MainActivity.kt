package com.example.pr22_pr_33_gagarinovlev

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var get_info_btn:Button
    private lateinit var city_ed:EditText
    private lateinit var city_tw:TextView
    private lateinit var temperature_tw:TextView
    private lateinit var pressure_tw:TextView
    private lateinit var wind_speed_tw:TextView


    private val key = "b90c599213c588f7a248b47b21c5351c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city_ed=findViewById(R.id.city_ed)

        city_tw=findViewById(R.id.city_tw)
        temperature_tw=findViewById(R.id.temperature_tw)
        pressure_tw=findViewById(R.id.pressure_tw)
        wind_speed_tw=findViewById(R.id.wind_speed_tw)



        get_info_btn=findViewById(R.id.get_info_btn)
        get_info_btn.setOnClickListener {
            val city=city_ed.text.toString()
            getWeather(city)
        }
    }

    private fun getWeather(city:String){
        if(city.isEmpty())
        {
            Snackbar.make(findViewById(android.R.id.content), R.string.error_empty_city, Snackbar.LENGTH_LONG).setActionTextColor(
                Color.RED).show()
            return
        }

        val url=
            "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                response->
                val obj= JSONObject(response)

                val main = obj.getJSONObject("main")
                city_tw.text=resources.getString(R.string.city)+obj.getString("name")
                temperature_tw.text=resources.getString(R.string.temperature)+main.getString("temp")
                pressure_tw.text=resources.getString(R.string.pressure) + main.getString("pressure")

                val wind=obj.getJSONObject("wind")
                wind_speed_tw.text=resources.getString(R.string.wind_speed)+wind.getString("speed")
            }
        ) {
            Snackbar.make(
                findViewById(android.R.id.content),
                R.string.error_unknown_city,
                Snackbar.LENGTH_LONG
            ).setActionTextColor(
                Color.RED
            ).show()
        }

        queue.add(stringRequest)
    }
}