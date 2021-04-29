package com.rajkumar.smsreader.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [InboxMessageData::class, SpamMessageData::class], version = 1,exportSchema = false)
public abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao



    companion object {
        const val DATABASE_NAME = "messages_db"
        @Volatile
        private var INSTANCE: MessageDatabase? = null

        fun getDatabase(context: Context): MessageDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(ProfileDatabaseCallback())
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

    private class ProfileDatabaseCallback(
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                Log.d("MyApp","Database Created")
            }
        }


    }
}

