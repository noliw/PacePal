package com.nolawiworkineh.core.database.di

import androidx.room.Room
import com.nolawiworkineh.core.database.RoomLocalRunDataSource
import com.nolawiworkineh.core.database.RunDatabase
import com.nolawiworkineh.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RunDatabase::class.java,
            "run.db"
        ).build()
    }
    single { get<RunDatabase>().runDao }

    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}