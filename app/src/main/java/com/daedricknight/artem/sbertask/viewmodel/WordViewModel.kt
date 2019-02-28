package com.daedricknight.artem.sbertask.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.daedricknight.artem.sbertask.model.entities.Word
import android.arch.lifecycle.LiveData
import com.daedricknight.artem.sbertask.model.persistence.WordRoomDatabase
import com.daedricknight.artem.sbertask.model.persistence.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class WordViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val mRepository: WordRepository

    internal val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application).wordDao()
        mRepository = WordRepository(wordsDao)
        allWords = mRepository.allWords
    }

    fun insert(word: Word) = scope.launch(Dispatchers.IO) {
        mRepository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}