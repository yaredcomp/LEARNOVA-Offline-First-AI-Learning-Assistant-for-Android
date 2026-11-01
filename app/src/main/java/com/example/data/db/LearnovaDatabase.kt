package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        WorkspaceEntity::class,
        DocumentEntity::class,
        DocumentChunkEntity::class,
        ChatMessageEntity::class,
        QuizEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LearnovaDatabase : RoomDatabase() {
    abstract fun workspaceDao(): WorkspaceDao
    abstract fun documentDao(): DocumentDao
    abstract fun documentChunkDao(): DocumentChunkDao
    abstract fun chatMessageDao(): ChatMessageDao
    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile
        private var INSTANCE: LearnovaDatabase? = null

        fun getDatabase(context: Context): LearnovaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LearnovaDatabase::class.java,
                    "learnova_academic_vault.db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
