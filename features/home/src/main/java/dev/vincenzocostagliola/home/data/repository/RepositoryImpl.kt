package dev.vincenzocostagliola.home.data.repository

import dev.vincenzocostagliola.data.error.ErrorManagement

internal class RepositoryImpl(
    private val errorManagement: ErrorManagement
) : Repository {
}