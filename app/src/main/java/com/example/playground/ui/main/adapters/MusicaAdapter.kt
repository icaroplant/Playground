package com.example.playground.ui.main.adapters

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.databinding.ItemMusicaBinding
import com.example.playground.extensions.inflater
import kotlin.random.Random
import kotlin.random.nextInt

class MusicaAdapter(
    private val musicas: List<MusicEntity>,
    private val clickListener: (MusicEntity) -> Unit,
    private val longClickListener: (MusicEntity) -> Boolean
) : RecyclerView.Adapter<MusicaAdapter.MusicaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicaViewHolder {
        return MusicaViewHolder(
            ItemMusicaBinding.inflate(
                parent.context.inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = musicas.size

    override fun onBindViewHolder(holder: MusicaViewHolder, position: Int) {
        holder.bind(musicas[position], clickListener, longClickListener)
    }

    inner class MusicaViewHolder(val binding: ItemMusicaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvNumber.background = itemView.ovalRandomColor()
        }

        fun bind(
            musica: MusicEntity,
            clickListener: (MusicEntity) -> Unit,
            longClickListener: (MusicEntity) -> Boolean
        ) {
            with(binding) {
                tvNumber.text = musica.id.toString()
                tvTitle.text = musica.name
                root.setOnClickListener {
                    clickListener(musica)
                }
                root.setOnLongClickListener {
                    longClickListener(musica)
                }
            }
        }

    }
}

fun View.ovalRandomColor(): ShapeDrawable {
    return ShapeDrawable(OvalShape()).apply {
        intrinsicHeight = height
        intrinsicWidth = width
        paint.color =
            Color.rgb(Random.nextInt(0..255), Random.nextInt(0..255), Random.nextInt(0..255))
    }
}