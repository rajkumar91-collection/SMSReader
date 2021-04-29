package com.rajkumar.smsreader.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query


@Dao
interface MessageDao {


    @Insert(onConflict = IGNORE)
    fun insertInboxMessage(inboxMessageData: InboxMessageData): Long?


    @Insert(onConflict = IGNORE)
    fun insertSpamMessage(inboxMessageData: SpamMessageData): Long?


    @Query(
        "UPDATE spam SET is_read = :isRead WHERE address = :address"

    )
    fun updateSpam(
        isRead: Boolean?,
        address: String?
    )

    @Query(
        "UPDATE inbox SET is_read = :isRead WHERE address = :address"

    )
    fun updateInbox(
        isRead: Boolean?,
        address: String?
    )



    @Query("SELECT * FROM inbox")
    fun getInboxMessages(): LiveData<List<InboxMessageData>?>

    @Query("SELECT * FROM spam")
    fun getSpamMessages(): LiveData<List<SpamMessageData>?>
}