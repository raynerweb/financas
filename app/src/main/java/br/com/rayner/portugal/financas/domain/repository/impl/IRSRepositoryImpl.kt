package br.com.rayner.portugal.financas.domain.repository.impl

import br.com.rayner.portugal.financas.domain.repository.IRSRepository
import br.com.rayner.portugal.financas.domain.repository.model.Imposto
import br.com.rayner.portugal.financas.domain.service.IRSService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IRSRepositoryImpl @Inject constructor(
    private val irsService: IRSService,
) : IRSRepository {

    override suspend fun getAll() = withContext(context = Dispatchers.IO) {
        return@withContext irsService.getAll().map { dto ->
            Imposto(
                casado = dto.casado,
                deficiente = dto.deficiente,
                qtdeFilhos = dto.qtdeFilhos,
                porcentagemImposto = 100.0.div(dto.porcentagemImposto.replace("%", "").toDouble()),
                rendaLimite = dto.rendaLimite.replace(",", "").toDouble()
            )
        }
    }

}