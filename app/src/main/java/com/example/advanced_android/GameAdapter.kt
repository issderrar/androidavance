package com.example.advanced_android

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout.view.*

class GameAdapter(private val gameList: ArrayList<Game>, var listener: (Int) -> Unit) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return GameViewHolder(viewHolder, listener)
    }

    override fun getItemCount(): Int = gameList.size

    override fun onBindViewHolder(
        holder: GameViewHolder,
        position: Int
    ) = holder.bind(
        gameList[position]
    )

    class GameViewHolder(itemView: View, var listener: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView =
            itemView.findViewById(R.id.textView3)

        fun bind(game: Game) {
            textView.text = game.name
            Picasso.get().load(game.imgUrl).into(itemView.imageView3)

            itemView.setOnClickListener {
                listener(game.id)
            }
        }
    }
}