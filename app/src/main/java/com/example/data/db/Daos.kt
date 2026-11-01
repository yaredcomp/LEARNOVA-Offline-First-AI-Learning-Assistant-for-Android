package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkspaceDao {
    @Query("SELECT * FROM workspaces ORDER BY isPinned DESC, id ASC")
    fun getAllWorkspaces(): Flow<List<WorkspaceEntity>>

    @Query("SELECT * FROM workspaces WHERE id = :id LIMIT 1")
    suspend fun getWorkspaceById(id: String): WorkspaceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkspace(workspace: WorkspaceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkspaces(workspaces: List<WorkspaceEntity>)

    @Query("DELETE FROM workspaces WHERE id = :id")
    suspend fun deleteWorkspace(id: String)
}

@Dao
interface DocumentDao {
    @Query("SELECT * FROM documents WHERE workspaceId = :workspaceId ORDER BY createdAt DESC")
    fun getDocumentsByWorkspace(workspaceId: String): Flow<List<DocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: DocumentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocuments(documents: List<DocumentEntity>)
}

@Dao
interface DocumentChunkDao {
    @Query("SELECT * FROM document_chunks WHERE workspaceId = :workspaceId")
    suspend fun getChunksByWorkspace(workspaceId: String): List<DocumentChunkEntity>

    @Query("SELECT * FROM document_chunks WHERE text LIKE '%' || :query || '%' AND workspaceId = :workspaceId")
    suspend fun searchChunks(workspaceId: String, query: String): List<DocumentChunkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChunks(chunks: List<DocumentChunkEntity>)
}

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages WHERE workspaceId = :workspaceId ORDER BY timestamp ASC")
    fun getMessages(workspaceId: String): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages WHERE workspaceId = :workspaceId")
    suspend fun clearHistory(workspaceId: String)
}

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes WHERE workspaceId = :workspaceId ORDER BY createdAt DESC")
    fun getQuizzes(workspaceId: String): Flow<List<QuizEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: QuizEntity)
}
