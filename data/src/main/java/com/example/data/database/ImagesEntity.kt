package com.example.data.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_table")
data class ImagesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val bitmap: String?,
    val link: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(bitmap)
        parcel.writeString(link)
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
