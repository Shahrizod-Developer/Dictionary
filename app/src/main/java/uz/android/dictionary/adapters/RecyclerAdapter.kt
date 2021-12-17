package uz.android.dictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.android.dictionary.R
import uz.android.dictionary.models.RoomWords


class RecyclerAdapter(var list: List<RoomWords>, val onClick: (words: RoomWords, position: Int) -> Unit):
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
            inner class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){

                private val wordeng: TextView = itemview.findViewById(R.id.wordeng1)

                fun onBind(words: RoomWords, position: Int){
                    wordeng.text = words.wordeng
                    itemView.setOnClickListener{
                        onClick(words, position)
                    }
                }
            }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_words, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }
    override fun getItemCount(): Int = list.size
}