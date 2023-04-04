package com.dhruv.myfirstkmm.android.di

import android.app.Application
import com.dhruv.NoteAppKMM.database.NoteDatabase
import com.dhruv.myfirstkmm.Data.local.DatabaseDriverFactory
import com.dhruv.myfirstkmm.Data.note.SqlDelightDataSource
import com.dhruv.myfirstkmm.Domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
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
    fun provideSqlDriver(app: Application) : SqlDriver{
        return DatabaseDriverFactory(app).createDriver()
    }
    @Provides
    @Singleton
    fun provideDataSource(sqldriver : SqlDriver) : NoteDataSource{
        return SqlDelightDataSource(NoteDatabase(sqldriver))
    }
}