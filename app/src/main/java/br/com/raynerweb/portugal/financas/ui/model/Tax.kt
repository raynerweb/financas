package br.com.raynerweb.portugal.financas.ui.model

data class Tax(
    val rendaLiquida: String,
    val rendaLimite: String,
    val doisTitulares: String,
    val casado: String,
    val deficiente: String,
    val qtdeFilhos: String,
    val porcentagemImposto: String
)