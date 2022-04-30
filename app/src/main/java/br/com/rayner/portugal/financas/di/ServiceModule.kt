package br.com.rayner.portugal.financas.di

import android.content.Context
import br.com.rayner.portugal.financas.domain.service.IRSService
import br.com.rayner.portugal.financas.domain.service.impl.IRSServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun irsService(@ApplicationContext context: Context): IRSService = IRSServiceImpl(context)


}