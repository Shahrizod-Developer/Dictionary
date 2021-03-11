package uz.android.dictionary.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.android.dictionary.daos.RoomDao
import uz.android.dictionary.models.RoomWords

@Database(entities = [RoomWords::class], version = 1)
abstract class RoomDbHelper: RoomDatabase() {

    abstract fun roomDao(): RoomDao

    object DatabaseBuilder{
        private var instance: RoomDbHelper? = null

        fun getInstance(context: Context): RoomDbHelper{
            if(instance == null){
                synchronized(RoomDbHelper::class.java){
                    instance = builRoomDb(context)
                }
            }
            return instance!!
        }

        private fun builRoomDb(context: Context) =
            Room.databaseBuilder(
                    context.applicationContext,
                    RoomDbHelper::class.java,
                    "room_db.db"
            ).allowMainThreadQueries()
                .build()
    }
}