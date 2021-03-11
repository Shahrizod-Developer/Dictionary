package uz.android.dictionary.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RoomWords(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var wordeng: String? = null,
    var worduz: String? = null,
    var wordru: String? = null)