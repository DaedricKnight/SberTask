package com.daedricknight.artem.sbertask

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.daedricknight.artem.sbertask.model.entities.Word
import com.daedricknight.artem.sbertask.model.persistence.WordRoomDatabase
import com.daedricknight.artem.sbertask.model.persistence.dao.WordDao
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class WordDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wordDao: WordDao
    private lateinit var db: WordRoomDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, WordRoomDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        wordDao = db.wordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() {
        val word = Word("слово","word","la palabra","a palavra")
        wordDao.insert(word)
        val allWords = wordDao.getAllWords().waitForValue()
        assertEquals(allWords[0].rus, word.rus)
    }

    @Test
    @Throws(Exception::class)
    fun getAllWords() {
        val word = Word("aaa","ааа","ааа","ааа")
        wordDao.insert(word)
        val word2 = Word("bbb","bbb","bbb","bbb")
        wordDao.insert(word2)
        val allWords = wordDao.getAllWords().waitForValue()
        assertEquals(allWords[0].rus, word.rus)
        assertEquals(allWords[1].rus, word2.rus)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        val word = Word("слово","word","la palabra","a palavra")
        wordDao.insert(word)
        val word2 = Word("слово2","word2","la palabra2","a palavra2")
        wordDao.insert(word2)
        wordDao.deleteAll()
        val allWords = wordDao.getAllWords().waitForValue()
        assertTrue(allWords.isEmpty())
    }
}
