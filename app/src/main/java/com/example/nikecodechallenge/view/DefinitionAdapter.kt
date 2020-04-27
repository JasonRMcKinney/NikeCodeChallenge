package com.example.nikecodechallenge.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikecodechallenge.R
import com.example.nikecodechallenge.model.DescriptionItem
import com.example.nikecodechallenge.model.DescriptionResponse

class DefinitionAdapter : RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder>() {

    var dataSet: DescriptionResponse? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefinitionViewHolder {
        return DefinitionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() =
        dataSet?.list?.size ?: 0


    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        dataSet?.list?.let {
            holder.onBind(it[position])
        }
    }

    class DefinitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDescriptionItem: TextView = itemView.findViewById(R.id.tv_ud_description)
        private val tvThumbsUpNum: TextView = itemView.findViewById(R.id.tv_up_vote)
        private val tvThumbsDownNum: TextView = itemView.findViewById(R.id.tv_down_vote)

        fun onBind(dataItem: DescriptionItem) {
            tvDescriptionItem.text = dataItem.definition
            tvThumbsUpNum.text = dataItem.thumbs_up.toString()
            tvThumbsDownNum.text = dataItem.thumbs_down.toString()
        }
    }

}