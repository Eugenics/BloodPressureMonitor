package com.eugenics.bloodpressuremonitor.di

import com.eugenics.bloodpressuremonitor.data.repository.Repository
import com.eugenics.bloodpressuremonitor.domain.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface BindsRepository {
    @Binds
    @ViewModelScoped
    fun bindRepository(repository: Repository): IRepository
}