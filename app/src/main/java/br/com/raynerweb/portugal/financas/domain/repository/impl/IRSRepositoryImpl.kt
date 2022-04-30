package br.com.raynerweb.portugal.financas.domain.repository.impl

import br.com.raynerweb.portugal.financas.domain.repository.IRSRepository
import br.com.raynerweb.portugal.financas.domain.repository.model.Imposto
import br.com.raynerweb.portugal.financas.domain.service.IRSService
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
                imposto = dto.porcentagemImposto.replace("%", "").toDouble().div(100.0),
                porcentagemImposto = dto.porcentagemImposto,
                rendaLimite = dto.rendaLimite.replace(",", "").toDouble()
            )
        }
    }

}