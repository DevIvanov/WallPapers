package com.example.data.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "images_table")//, indices = [Index(value = ["link"], unique = true)]
data class ImagesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val link: String?,
    val date: String?,
    val width: String?,
    val height: String?,
    val color: Int,
    val searchLink: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(link)
        parcel.writeString(date)
        parcel.writeString(width)
        parcel.writeString(height)
        parcel.writeInt(color)
        parcel.writeString(searchLink)
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
