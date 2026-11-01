package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ai.ModelManager
import com.example.data.repository.LearnovaRepository
import com.example.ui.LearnovaApp
import com.example.ui.theme.LearnovaTheme

class MainActivity : ComponentActivity() {

    private lateinit var repository: LearnovaRepository
    private lateinit var modelManager: ModelManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        repository = LearnovaRepository(applicationContext)
        modelManager = ModelManager()

        setContent {
            LearnovaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LearnovaApp(
                        repository = repository,
                        modelManager = modelManager
                    )
                }
            }
        }
    }
}
