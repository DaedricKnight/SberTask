package com.daedricknight.artem.sbertask.view.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.daedricknight.artem.sbertask.BuildConfig
import com.daedricknight.artem.sbertask.R
import com.daedricknight.artem.sbertask.model.entities.Word
import com.daedricknight.artem.sbertask.model.network.RetrofitFactory
import com.daedricknight.artem.sbertask.view.adapters.BaseAdapter
import com.daedricknight.artem.sbertask.viewmodel.WordViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TranslateFragment : Fragment() {

    lateinit var mWordViewModel: WordViewModel

    private val service = RetrofitFactory.makeRetrofitService()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        val rootView: View = inflater.inflate(R.layout.translate_layout, container, false)
        val editTranslate = rootView.findViewById(R.id.translate_text) as EditText
        val recyclerView = rootView.findViewById(R.id.translateView) as RecyclerView
        activity?.let {
            mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        }

        val adapter = BaseAdapter(context!!)
        adapter.setWords(listOf(Word("","","","")))
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        recyclerView.adapter = adapter
        editTranslate.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText == searchFor || searchText.isEmpty())
                    return

                searchFor = searchText

                GlobalScope.launch(Dispatchers.Main) {
                    delay(500)
                    if (searchText != searchFor)
                        return@launch

                    val enTranslate = getTranslate(searchFor,"ru-en")

                    mWordViewModel.insert(getListTranslated(enTranslate,searchFor))

                    adapter.setWords(listOf(Word(enTranslate!![0],"","","")))
                    recyclerView.adapter=adapter
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        })

        return rootView
    }

    private suspend fun getListTranslated(en_translate:List<String>?,searchFor:String):Word{

        return  Word(searchFor,
            en_translate!![0],
            getTranslate(searchFor,"ru-es")!![0],
            getTranslate(searchFor,"ru-pt")!![0])
    }

    private suspend fun getTranslate(searchFor:String, lang:String):List<String>?{
        return service.getTranslate(BuildConfig.api_key,searchFor,lang)
            .await()
            .body()
            ?.text
    }
}