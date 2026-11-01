package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workspaces")
data class WorkspaceEntity(
    @PrimaryKey val id: String,
    val title: String,
    val courseCode: String,
    val category: String,
    val description: String,
    val documentCount: Int,
    val flashcardCount: Int,
    val quizCount: Int,
    val lastStudied: String,
    val isPinned: Boolean
)

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val title: String,
    val fileName: String,
    val fileType: String,
    val pageCount: Int,
    val fileSizeFormatted: String,
    val isVectorized: Boolean,
    val createdAt: String,
    val excerpt: String
)

@Entity(tableName = "document_chunks")
data class DocumentChunkEntity(
    @PrimaryKey val id: String,
    val documentId: String,
    val workspaceId: String,
    val documentTitle: String,
    val pageNumber: Int,
    val slideNumber: Int,
    val heading: String,
    val text: String,
    val tokenCount: Int,
    val confidence: Float
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val sender: String,
    val content: String,
    val timestamp: String,
    val latencyFormatted: String,
    val tokensPerSec: Int,
    val modelName: String,
    val isZeroEgress: Boolean,
    val codeSnippet: String?,
    val formula: String?
)

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val title: String,
    val topic: String,
    val difficulty: String,
    val questionCount: Int,
    val createdAt: String,
    val lastScorePercent: Int?
)
