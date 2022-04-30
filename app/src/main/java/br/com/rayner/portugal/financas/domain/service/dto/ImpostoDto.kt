package br.com.rayner.portugal.financas.domain.service.dto

import com.google.gson.annotations.SerializedName

data class ImpostoDto(
    @SerializedName("renda_limite")
    val rendaLimite: String,
    val casado: Boolean,
    val deficiente: Boolean,
    @SerializedName("qtde_filhos")
    val qtdeFilhos: Int,
    @SerializedName("percent_text")
    val porcentagemImposto: String
)
