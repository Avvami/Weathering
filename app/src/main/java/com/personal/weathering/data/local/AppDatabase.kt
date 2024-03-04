package com.personal.weathering.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [PreferencesEntity::class, FavoriteEntity::class, SearchResultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract val preferencesDao: PreferencesDao
    abstract val favoritesDao: FavoritesDao
    abstract val searchHistoryDao: SearchHistoryDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = "app_database"
                ).fallbackToDestructiveMigration().addCallback(prepopulateCallback).build().also { Instance = it }
            }
        }

        private val prepopulateCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val defaultPreferences = ContentValues().apply {
                    put("cityId", 2643743)
                    put("currentCity", "London")
                    put("lat", 51.50853)
                    put("lon", -0.12574)
                    put("searchLanguageCode", "en")
                    put("useLocation", false)
                    put("useCelsius", true)
                    put("useKmPerHour", true)
                    put("useHpa", true)
                    put("useUSaq", true)
                }
                db.insert("preferencesentity", SQLiteDatabase.CONFLICT_REPLACE, defaultPreferences)
            }
        }
    }
}