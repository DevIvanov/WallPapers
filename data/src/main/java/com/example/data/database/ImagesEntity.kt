package com.example.data.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "image_table", indices = [Index(value = ["urlFull"], unique = true)])
data class ImagesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val urlFull: String?,
    val urlRegular: String?,
    val date: String?,
    val width: String?,
    val height: String?,
    val color: String?,
    val name: String?,
    val description: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(urlFull)
        parcel.writeString(urlRegular)
        parcel.writeString(date)
        parcel.writeString(width)
        parcel.writeString(height)
        parcel.writeString(color)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImagesEntity> {
        override fun createFromParcel(parcel: Parcel): ImagesEntity {
            return ImagesEntity(parcel)
        }

        override fun newArray(size: Int): Array<ImagesEntity?> {
            return arrayOfNulls(size)
        }
    }
}
