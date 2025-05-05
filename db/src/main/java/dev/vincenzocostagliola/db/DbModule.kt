package dev.vincenzocostagliola.db

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DBModule {

    @Provides
    @Singleton
    internal fun provideDb(
        @ApplicationContext context: Context
    ): DodoDB {
        return DodoDB.getDatabase(context = context)
    }
}