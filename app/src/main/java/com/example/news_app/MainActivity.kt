package com.example.news_app

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(),NewsItemClicked {

    private lateinit var mAdapter:NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myRecyclerView = findViewById<RecyclerView>(R.id.list)
        myRecyclerView.layoutManager = LinearLayoutManager (this)
            fetchData()
            mAdapter= NewsListAdapter(this@MainActivity)
        myRecyclerView.adapter=mAdapter



    }

    private fun fetchData(){
        val url="https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=7273834fd2fd4985831efeb95027691f"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
           Response.Listener {
              val newsJsonArray=it.getJSONArray("articles")
              val newsArray=ArrayList<News>()
              for(i in 0 until newsJsonArray.length()){
                  val newsJsonObject=newsJsonArray.getJSONObject(i)
                  val news=News(
                      newsJsonObject.getString("title"),
                      newsJsonObject.getString("author"),
                      newsJsonObject.getString("url"),
                      newsJsonObject.getString("urlToImage"),
                  )
                  newsArray.add(news)
              }
               mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener{


            }


        )
       

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    override fun onItemClicked(item:News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }


}