package com.daedricknight.artem.sbertask.model.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.daedricknight.artem.sbertask.model.entities.Word
import com.daedricknight.artem.sbertask.model.persistence.dao.WordDao
import android.arch.persistence.room.Room
import android.content.Context

@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        protected var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}