package com.rajkumar.smsreader.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "spam")
data  class SpamMessageData(


    @PrimaryKey
    @ColumnInfo(name = "message_id")
    var messageID: String,

    @ColumnInfo(name = "address")
    var address: String? ,

    @ColumnInfo(name = "message")
    var message: String? ,


    @ColumnInfo(name = "time_stamp")
    var timeStamp: Long ,


    @ColumnInfo(name = "index_on_icc")
    var indexOnIcc: Int ,


    @ColumnInfo(name = "is_read")
    var isRead: Boolean //Y or N


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readBoolean()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(messageID)
        parcel.writeString(address)
        parcel.writeString(message)
        parcel.writeLong(timeStamp)
        parcel.writeInt(indexOnIcc)
        parcel.writeBoolean(isRead)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InboxMessageData> {
        override fun createFromParcel(parcel: Parcel): InboxMessageData {
            return InboxMessageData(parcel)
        }

        override fun newArray(size: Int): Array<InboxMessageData?> {
            return arrayOfNulls(size)
        }
    }
}





