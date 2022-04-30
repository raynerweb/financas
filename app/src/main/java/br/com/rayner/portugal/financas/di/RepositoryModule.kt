package br.com.rayner.portugal.financas.di

import br.com.rayner.portugal.financas.domain.repository.IRSRepository
import br.com.rayner.portugal.financas.domain.repository.impl.IRSRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideIRSRepository(irsRepositoryImpl: IRSRepositoryImpl): IRSRepository

}