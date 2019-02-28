package com.daedricknight.artem.sbertask.view.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daedricknight.artem.sbertask.R
import com.daedricknight.artem.sbertask.model.entities.Word

open class BaseAdapter(val context: Context) : RecyclerView.Adapter<BaseAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var mWords :List<Word> // Cached copy of words

    init{
        setWords(listOf(Word("","","","")))
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.word_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.word_list_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = mWords[position]
        holder.wordItemView.text = current.rus
    }

    internal fun setWords(words: List<Word>) {
        this.mWords = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = mWords.size
}