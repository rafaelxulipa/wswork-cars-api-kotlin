package br.com.wswork.cars

import br.com.wswork.cars.model.Carro
import br.com.wswork.cars.model.Marca
import br.com.wswork.cars.model.Modelo
import br.com.wswork.cars.repository.CarroRepository
import br.com.wswork.cars.repository.MarcaRepository
import br.com.wswork.cars.repository.ModeloRepository
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
class CarroControllerIntegrationTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var carroRepo: CarroRepository
    @Autowired lateinit var modeloRepo: ModeloRepository
    @Autowired lateinit var marcaRepo: MarcaRepository

    private lateinit var modelo: Modelo

    @BeforeEach
    fun setUp() {
        carroRepo.deleteAll()
        modeloRepo.deleteAll()
        marcaRepo.deleteAll()
        val marca = marcaRepo.save(Marca(nomeMarca = "TestMarca"))
        modelo = modeloRepo.save(Modelo(marca = marca, nome = "TestModelo", valorFipe = BigDecimal("50000")))
    }

    @Test
    fun `GET api-cars retorna formato compativel com cars json`() {
        carroRepo.save(Carro(modelo = modelo, timestampCadastro = 1696539488L, ano = 2022, combustivel = "FLEX", numPortas = 4, cor = "PRATA"))

        mockMvc.get("/api/cars")
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.cars", hasSize<Any>(1)) }
            .andExpect { jsonPath("$.cars[0].nomeModelo") { value("TestModelo") } }
            .andExpect { jsonPath("$.cars[0].brand") { value(modelo.marca.id) } }
    }

    @Test
    fun `POST api-carros cria carro e retorna 201`() {
        mockMvc.post("/api/carros") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"modeloId":${modelo.id},"ano":2023,"combustivel":"flex","numPortas":4,"cor":"azul"}"""
        }.andExpect {
            status { isCreated() }
            jsonPath("$.id") { exists() }
            jsonPath("$.combustivel") { value("FLEX") }
            jsonPath("$.cor") { value("AZUL") }
        }
    }

    @Test
    fun `POST api-carros retorna 422 com campos invalidos`() {
        mockMvc.post("/api/carros") {
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }.andExpect { status { isUnprocessableEntity() } }
    }

    @Test
    fun `DELETE api-carros retorna 204`() {
        val carro = carroRepo.save(Carro(modelo = modelo, timestampCadastro = 1696539488L, ano = 2020, combustivel = "GASOLINA", numPortas = 4, cor = "PRETA"))
        mockMvc.delete("/api/carros/${carro.id}").andExpect { status { isNoContent() } }
    }

    @Test
    fun `GET api-carros-id retorna 404 se nao existe`() {
        mockMvc.get("/api/carros/99999").andExpect { status { isNotFound() } }
    }
}
