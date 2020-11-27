package com.example.playground.ui.main.adapters

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playground.R
import com.example.playground.ui.main.Musica
import kotlinx.android.synthetic.main.item_musica.view.*
import kotlin.random.Random
import kotlin.random.nextInt

class MusicaAdapter (
    private val musicas: MutableList<Musica>,
    private val clickListener: (Musica) -> Unit
) : RecyclerView.Adapter<MusicaAdapter.MusicaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_musica, parent, false)
        return MusicaViewHolder(view)
    }

    override fun getItemCount(): Int = musicas.size

    override fun onBindViewHolder(holder: MusicaViewHolder, position: Int) {
        holder.bind(musicas[position], clickListener)
    }

    inner class MusicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.tv_number.background = itemView.ovalRandomColor()
        }

        fun bind(
            musica: Musica,
            clickListener: (Musica) -> Unit
        ) {
            itemView.apply {
                tv_number.text = musica.id.toString()
                tv_title.text = musica.titulo
                setOnClickListener {
                    clickListener(musica)
                }
            }
        }

    }
}

fun View.ovalRandomColor() : ShapeDrawable{
    return ShapeDrawable(OvalShape()).apply {
        intrinsicHeight = height
        intrinsicWidth = width
        paint.color = Color.rgb(Random.nextInt(0 .. 255), Random.nextInt(0 .. 255),Random.nextInt(0 .. 255))
    }
}