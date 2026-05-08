package br.com.wswork.cars.dto

import br.com.wswork.cars.model.Carro
import br.com.wswork.cars.model.Marca
import br.com.wswork.cars.model.Modelo
import jakarta.validation.constraints.*
import java.math.BigDecimal

// ── Marca ──────────────────────────────────────────────────────────────────

data class MarcaRequest(
    @field:NotBlank(message = "Nome da marca é obrigatório")
    val nomeMarca: String
)

data class MarcaResponse(val id: Long, val nomeMarca: String) {
    companion object {
        fun from(m: Marca) = MarcaResponse(m.id, m.nomeMarca)
    }
}

// ── Modelo ─────────────────────────────────────────────────────────────────

data class ModeloRequest(
    @field:NotNull(message = "ID da marca é obrigatório")
    val marcaId: Long,

    @field:NotBlank(message = "Nome do modelo é obrigatório")
    val nome: String,

    @field:NotNull @field:PositiveOrZero
    val valorFipe: BigDecimal
)

data class ModeloResponse(
    val id: Long,
    val marcaId: Long,
    val nomeMarca: String,
    val nome: String,
    val valorFipe: BigDecimal
) {
    companion object {
        fun from(m: Modelo) = ModeloResponse(m.id, m.marca.id, m.marca.nomeMarca, m.nome, m.valorFipe)
    }
}

// ── Carro ──────────────────────────────────────────────────────────────────

data class CarroRequest(
    @field:NotNull(message = "ID do modelo é obrigatório")
    val modeloId: Long,

    @field:NotNull @field:Min(1900)
    val ano: Int,

    @field:NotBlank(message = "Combustível é obrigatório")
    val combustivel: String,

    @field:NotNull @field:Min(2) @field:Max(4)
    val numPortas: Int,

    @field:NotBlank(message = "Cor é obrigatória")
    val cor: String
)

data class CarroResponse(
    val id: Long,
    val timestampCadastro: Long,
    val modeloId: Long,
    val nomeModelo: String,
    val marcaId: Long,
    val nomeMarca: String,
    val ano: Int,
    val combustivel: String,
    val numPortas: Int,
    val cor: String
) {
    companion object {
        fun from(c: Carro) = CarroResponse(
            c.id, c.timestampCadastro,
            c.modelo.id, c.modelo.nome,
            c.modelo.marca.id, c.modelo.marca.nomeMarca,
            c.ano, c.combustivel, c.numPortas, c.cor
        )
    }
}

/** Formato compatível com cars.json — item 2 do teste */
data class CarroListagemResponse(
    val id: Long,
    val timestampCadastro: Long,
    val modeloId: Long,
    val ano: Int,
    val combustivel: String,
    val numPortas: Int,
    val cor: String,
    val nomeModelo: String,
    val valor: BigDecimal,
    val brand: Long
) {
    companion object {
        fun from(c: Carro) = CarroListagemResponse(
            c.id, c.timestampCadastro, c.modelo.id,
            c.ano, c.combustivel, c.numPortas, c.cor,
            c.modelo.nome, c.modelo.valorFipe, c.modelo.marca.id
        )
    }
}
