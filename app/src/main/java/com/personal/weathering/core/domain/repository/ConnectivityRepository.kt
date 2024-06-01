package com.personal.weathering.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    fun getCurrentNetworkStatus(): Boolean
    fun observe(): Flow<Boolean>
}