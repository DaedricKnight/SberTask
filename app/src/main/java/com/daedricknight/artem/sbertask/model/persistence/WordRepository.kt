package com.daedricknight.artem.sbertask.model.persistence

import com.daedricknight.artem.sbertask.model.entities.Word
import com.daedricknight.artem.sbertask.model.persistence.dao.WordDao
import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread


class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}