package com.example.news_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val  listener: NewsItemClicked):RecyclerView.Adapter<NewsViewHolder> (){

    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)            // converting the xml to view
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])            // The function to be performmed on click of the item should be handled by the mainActivity so we genearate a call back through the MainActivity
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem=items[position]
        holder.titleView.text=currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(update:ArrayList<News>){
        items.clear()
        items.addAll(update)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val titleView:TextView=itemView.findViewById(R.id.title1)
    val container = itemView.findViewById<ConstraintLayout>(R.id.container)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}
interface NewsItemClicked{
    fun onItemClicked(item:News)
}