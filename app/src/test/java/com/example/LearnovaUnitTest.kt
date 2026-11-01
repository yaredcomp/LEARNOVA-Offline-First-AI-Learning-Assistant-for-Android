package com.example

import com.example.ai.ModelManager
import com.example.ai.Needle3Runtime
import com.example.ai.RAGEngine
import com.example.model.DocumentChunk
import com.example.model.ModelStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LearnovaUnitTest {

    @Test
    fun testRAGChunkingAndExtraction() {
        val fullText = """
            Slide 14: AVL Trees: Double Rotations
            A Left-Right rotation consists of a single left rotation around the left child
            followed by a single right rotation around the node itself.
            This restores the AVL invariant in O(1) constant time.
        """.trimIndent()

        val chunks = RAGEngine.chunkDocumentText(
            docId = "test-doc-1",
            workspaceId = "cs-301",
            docTitle = "CS2040_W04_Slides.pdf",
            fullText = fullText,
            chunkSizeTokens = 15,
            overlapTokens = 5
        )

        assertTrue(chunks.isNotEmpty())
        assertEquals("cs-301", chunks.first().workspaceId)
        assertEquals(14, chunks.first().slideNumber)
    }

    @Test
    fun testRAGWorkspaceIsolation() {
        val chunks = listOf(
            DocumentChunk("c1", "d1", "cs-301", "AVL Slides", 14, 14, "AVL", "AVL tree rotation balance factor", 10),
            DocumentChunk("c2", "d2", "rm-502", "Thesis Notes", 1, 1, "Qualitative", "Qualitative methodology", 10)
        )

        // Querying CS-301 should never retrieve RM-502 chunks
        val (retrievedText, citations) = RAGEngine.retrieveGroundedContext(
            query = "rotation balance",
            workspaceId = "cs-301",
            chunks = chunks
        )

        assertTrue(retrievedText.contains("AVL"))
        assertTrue(!retrievedText.contains("Qualitative"))
        assertEquals(1, citations.size)
        assertEquals("AVL Slides", citations.first().documentTitle)
    }

    @Test
    fun testNeedle3DeviceAudit() {
        // Test with 4GB RAM simulation
        val fourGbBytes = 4L * 1024L * 1024L * 1024L
        val audit = Needle3Runtime.auditDevice(fourGbBytes)

        assertNotNull(audit.compatibilityGrade)
        assertNotNull(audit.recommendation)
    }

    @Test
    fun testModelCatalogIntegrity() {
        val catalog = ModelManager.defaultCatalog()
        val needle3 = catalog.firstOrNull { it.id == "needle3-default" }

        assertNotNull("Needle3 must be in default catalog", needle3)
        assertEquals(ModelStatus.INSTALLED_READY, needle3?.status)
        assertEquals("Cactus Compute • Hugging Face", needle3?.publisher)
        assertTrue(needle3?.capabilities?.contains("Offline Chat") == true)
    }
}
