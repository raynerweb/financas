package br.com.raynerweb.portugal.financas.domain.repository

import br.com.raynerweb.portugal.financas.domain.repository.model.Imposto

interface IRSRepository {

    suspend fun getAll(): List<Imposto>

}