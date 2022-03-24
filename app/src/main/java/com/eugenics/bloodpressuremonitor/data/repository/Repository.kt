package com.eugenics.bloodpressuremonitor.data.repository

import com.eugenics.bloodpressuremonitor.data.datasource.local.ILocalDataSource
import com.eugenics.bloodpressuremonitor.domain.IRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: ILocalDataSource
) : IRepository {
    override val local: ILocalDataSource
        get() = localDataSource
}