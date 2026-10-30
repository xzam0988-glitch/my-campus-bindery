package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.local.CampusBinderDatabase
import com.example.data.preferences.UserSessionManager
import com.example.data.repository.*
import com.example.ui.navigation.MainAppNavigation
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.*
import com.example.util.configureAppCheck

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // STEP 1: Configure App Check with launch intent before any UI or Firebase operations
        configureAppCheck(this, intent)

        enableEdgeToEdge()

        // STEP 2: Initialize Room Database & Repositories
        val database = CampusBinderDatabase.getDatabase(applicationContext)
        val sessionManager = UserSessionManager(applicationContext)

        val userRepository = UserRepository(database.userDao(), sessionManager)
        val binderRepository = BinderRepository(database.courseDao(), database.noteDao())
        val scheduleRepository = ScheduleRepository(database.scheduleDao())
        val recordingRepository = AudioRecordingRepository(database.recordingDao(), database.transcriptDao())
        val aiRepository = AiRepository(database.summaryDao(), database.quizDao(), applicationContext)
        val syncRepository = SyncRepository(applicationContext, sessionManager)

        // STEP 3: Initialize ViewModels
        val authViewModel = AuthViewModel(userRepository)
        val dashboardViewModel = DashboardViewModel(
            userRepository = userRepository,
            binderRepository = binderRepository,
            scheduleRepository = scheduleRepository,
            recordingRepository = recordingRepository,
            aiRepository = aiRepository,
            syncRepository = syncRepository
        )
        val binderViewModel = BinderViewModel(binderRepository)
        val scheduleViewModel = ScheduleViewModel(scheduleRepository)
        val audioViewModel = AudioViewModel(recordingRepository, aiRepository, applicationContext)
        val aiFeaturesViewModel = AiFeaturesViewModel(aiRepository)
        val profileViewModel = ProfileViewModel(userRepository)

        // STEP 4: Render UI
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CreamBackground
                ) {
                    MainAppNavigation(
                        authViewModel = authViewModel,
                        dashboardViewModel = dashboardViewModel,
                        binderViewModel = binderViewModel,
                        scheduleViewModel = scheduleViewModel,
                        audioViewModel = audioViewModel,
                        aiFeaturesViewModel = aiFeaturesViewModel,
                        profileViewModel = profileViewModel
                    )
                }
            }
        }
    }
}
