package br.com.raynerweb.portugal.financas.domain.repository.model

data class Imposto(
    val rendaLimite: Double,
    val casado: Boolean,
    val unicoTitular: Boolean,
    val deficiente: Boolean,
    val qtdeFilhos: Int,
    val porcentagemImposto: String,
    val imposto: Double
)