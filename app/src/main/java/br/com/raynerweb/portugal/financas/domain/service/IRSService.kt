package br.com.raynerweb.portugal.financas.domain.service

import br.com.raynerweb.portugal.financas.domain.service.dto.ImpostoDto

interface IRSService {

    suspend fun getAll(): List<ImpostoDto>

}