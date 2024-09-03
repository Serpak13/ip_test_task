package com.serpak.ip_test_task.di

import android.app.Application
import androidx.room.Room
import com.serpak.ip_test_task.db.AppDatabase
import com.serpak.ip_test_task.repository.ItemRepoImpl
import com.serpak.ip_test_task.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "ip_test_task"  // Имя базы данных на устройстве
        )
            .createFromAsset("data.db")  // Имя файла в папке assets
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideItemRepo(db:AppDatabase): ItemRepository{
        return ItemRepoImpl(db.itemDao)
    }

}