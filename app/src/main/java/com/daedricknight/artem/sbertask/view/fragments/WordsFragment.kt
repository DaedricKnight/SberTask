package com.daedricknight.artem.sbertask.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daedricknight.artem.sbertask.R
import com.daedricknight.artem.sbertask.viewmodel.WordViewModel
import android.arch.lifecycle.ViewModelProviders
import com.daedricknight.artem.sbertask.view.adapters.BaseAdapter

class WordsFragment: Fragment() {

    lateinit var mWordViewModel: WordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.words_layout, container, false)
        val activity = activity
        val recyclerView = rootView.findViewById(R.id.recyclerview) as RecyclerView
        val adapter = BaseAdapter(context!!)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        activity?.let { FragmentActivity ->
            mWordViewModel = ViewModelProviders.of(FragmentActivity).get(WordViewModel::class.java)

            mWordViewModel.allWords.observe(this, Observer { words ->
                words?.let {
                    adapter.setWords(it)
                }
            })
        }

        return rootView
    }
}