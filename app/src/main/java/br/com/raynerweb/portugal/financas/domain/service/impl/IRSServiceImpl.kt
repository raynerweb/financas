package br.com.raynerweb.portugal.financas.domain.service.impl

import android.content.Context
import br.com.raynerweb.portugal.financas.domain.service.IRSService
import br.com.raynerweb.portugal.financas.domain.service.dto.ImpostoDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import javax.inject.Inject

class IRSServiceImpl @Inject constructor(
    private val context: Context
) : IRSService {

    private lateinit var impostoDtos: List<ImpostoDto>

    override suspend fun getAll(): List<ImpostoDto> = withContext(Dispatchers.IO) {
        if (!::impostoDtos.isInitialized) {
            context.assets.open("irs_table.json").let { stream ->
                val json = stream.bufferedReader().use(BufferedReader::readText)
                Gson().fromJson(json, object : TypeToken<List<ImpostoDto>>() {}.type)
            }
        } else impostoDtos
    }
}