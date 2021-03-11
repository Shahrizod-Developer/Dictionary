package uz.android.dictionary.daos

import androidx.room.*
import uz.android.dictionary.models.RoomWords
import kotlin.reflect.KClass

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inset(roomWords: RoomWords): Long

    @Query("select * from roomwords")
    fun getAllWords(): List<RoomWords>

    @Query("select * from roomwords order by roomwords.wordeng")
    fun getSortedList(): List<RoomWords>

    @Delete
    fun delete(roomWords: RoomWords)

    @Update
    fun update(roomWords: RoomWords)
}