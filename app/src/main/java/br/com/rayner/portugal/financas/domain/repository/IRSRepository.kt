package br.com.rayner.portugal.financas.domain.repository

import br.com.rayner.portugal.financas.domain.repository.model.Imposto

interface IRSRepository {

    suspend fun getAll(): List<Imposto>

}