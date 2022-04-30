package br.com.raynerweb.portugal.financas.di

import br.com.raynerweb.portugal.financas.domain.repository.IRSRepository
import br.com.raynerweb.portugal.financas.domain.repository.impl.IRSRepositoryImpl
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