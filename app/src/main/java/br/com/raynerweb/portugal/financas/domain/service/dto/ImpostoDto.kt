package br.com.raynerweb.portugal.financas.domain.service.dto

import com.google.gson.annotations.SerializedName

data class ImpostoDto(
    @SerializedName("renda_limite")
    val rendaLimite: String,
    val casado: Boolean,
    @SerializedName("qtde_titular")
    val qtdeTitular: Int,
    val deficiente: Boolean,
    @SerializedName("qtde_filhos")
    val qtdeFilhos: Int,
    @SerializedName("percent_text")
    val porcentagemImposto: String
)
