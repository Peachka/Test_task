package com.example.testtaskheartrate.data

import android.content.Context
import androidx.room.Room
import com.example.testtaskheartrate.ui.utils.GetCurrentTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "heart-rate-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBPMHistoryDAO(appDatabase: AppDatabase): BPMHistoryDAO {
        return appDatabase.bpmHistoryDAO()
    }

    @Provides
    @Singleton
    fun provideGetCurrentTime(): GetCurrentTime {
        return GetCurrentTime()
    }

    @Provides
    @Singleton
    fun provideBPMRepository(
        bpmHistoryDAO: BPMHistoryDAO
    ): BPMHistoryRepository {
        return BPMHistoryRepositoryImpl(bpmHistoryDAO)
    }


}
