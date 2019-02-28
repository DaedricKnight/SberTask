package com.daedricknight.artem.sbertask.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey



@Entity(tableName = "word_table")
data class Word(@PrimaryKey
                @ColumnInfo(name = "rus")
                val rus: String,
                @ColumnInfo(name = "eng")
                val eng: String,
                @ColumnInfo(name = "esp")
                val esp: String,
                @ColumnInfo(name = "por")
                val por: String)
