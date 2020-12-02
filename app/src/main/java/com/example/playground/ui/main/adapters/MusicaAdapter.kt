package com.example.playground.ui.main.adapters

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playground.R
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.ui.main.Musica
import kotlinx.android.synthetic.main.item_musica.view.*
import kotlin.random.Random
import kotlin.random.nextInt

class MusicaAdapter (
    private val musicas: List<MusicEntity>,
    private val clickListener: (MusicEntity) -> Unit,
    private val longClickListener: (MusicEntity) -> Boolean
) : RecyclerView.Adapter<MusicaAdapter.MusicaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_musica, parent, false)
        return MusicaViewHolder(view)
    }

    override fun getItemCount(): Int = musicas.size

    override fun onBindViewHolder(holder: MusicaViewHolder, position: Int) {
        holder.bind(musicas[position], clickListener, longClickListener)
    }

    inner class MusicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.tv_number.background = itemView.ovalRandomColor()
        }

        fun bind(
            musica: MusicEntity,
            clickListener: (MusicEntity) -> Unit,
            longClickListener: (MusicEntity) -> Boolean
        ) {
            itemView.apply {
                tv_number.text = musica.id.toString()
                tv_title.text = musica.name
                setOnClickListener {
                    clickListener(musica)
                }
                setOnLongClickListener{
                    longClickListener(musica)
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