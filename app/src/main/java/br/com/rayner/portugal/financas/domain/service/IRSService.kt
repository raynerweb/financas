package br.com.rayner.portugal.financas.domain.service

import br.com.rayner.portugal.financas.domain.service.dto.ImpostoDto

interface IRSService {

    suspend fun getAll(): List<ImpostoDto>

}