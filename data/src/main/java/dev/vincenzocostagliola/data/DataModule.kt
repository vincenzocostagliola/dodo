package dev.vincenzocostagliola.data


import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.data.datapersistence.DataPersistence
import dev.vincenzocostagliola.data.datapersistence.DataPersistenceImpl
import dev.vincenzocostagliola.data.datapersistence.DataPersistenceSP
import dev.vincenzocostagliola.data.datapersistence.createSharedPref
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule() {

    @Provides
    @Singleton
    internal fun provideErrorManagement(): ErrorManagement {
        return ErrorManagement()
    }

    @Provides
    @Singleton
    internal fun provideSharedPreference(
        context: Context
    ): DataPersistenceSP {
        return DataPersistenceSP(
            sharedPreferences = context.createSharedPref()
        )
    }


    @Provides
    @Singleton
    internal fun provideDataPersistence(
        sp: DataPersistenceSP
    ): DataPersistence {
        return DataPersistenceImpl(sp)
    }
}