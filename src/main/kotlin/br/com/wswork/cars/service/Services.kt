package br.com.wswork.cars.service

import br.com.wswork.cars.dto.*
import br.com.wswork.cars.exception.ResourceNotFoundException
import br.com.wswork.cars.model.Carro
import br.com.wswork.cars.model.Marca
import br.com.wswork.cars.model.Modelo
import br.com.wswork.cars.repository.CarroRepository
import br.com.wswork.cars.repository.MarcaRepository
import br.com.wswork.cars.repository.ModeloRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class MarcaService(private val repo: MarcaRepository) {

    fun listarTodas(): List<MarcaResponse> = repo.findAll().map(MarcaResponse::from)

    fun buscarPorId(id: Long): MarcaResponse = MarcaResponse.from(findOrThrow(id))

    @Transactional
    fun criar(request: MarcaRequest): MarcaResponse {
        if (repo.existsByNomeMarcaIgnoreCase(request.nomeMarca))
            throw IllegalArgumentException("Já existe uma marca com o nome: ${request.nomeMarca}")
        return MarcaResponse.from(repo.save(Marca(nomeMarca = request.nomeMarca)))
    }

    @Transactional
    fun atualizar(id: Long, request: MarcaRequest): MarcaResponse {
        val marca = findOrThrow(id).apply { nomeMarca = request.nomeMarca }
        return MarcaResponse.from(repo.save(marca))
    }

    @Transactional
    fun deletar(id: Long) { findOrThrow(id); repo.deleteById(id) }

    private fun findOrThrow(id: Long) = repo.findById(id).orElseThrow { ResourceNotFoundException("Marca", id) }
}

@Service
class ModeloService(private val repo: ModeloRepository, private val marcaRepo: MarcaRepository) {

    fun listarTodos(): List<ModeloResponse> = repo.findAllWithMarca().map(ModeloResponse::from)

    fun buscarPorId(id: Long): ModeloResponse = ModeloResponse.from(findOrThrow(id))

    @Transactional
    fun criar(request: ModeloRequest): ModeloResponse {
        val marca = marcaRepo.findById(request.marcaId).orElseThrow { ResourceNotFoundException("Marca", request.marcaId) }
        return ModeloResponse.from(repo.save(Modelo(marca = marca, nome = request.nome, valorFipe = request.valorFipe)))
    }

    @Transactional
    fun atualizar(id: Long, request: ModeloRequest): ModeloResponse {
        val modelo = findOrThrow(id)
        val marca = marcaRepo.findById(request.marcaId).orElseThrow { ResourceNotFoundException("Marca", request.marcaId) }
        modelo.apply { this.marca = marca; nome = request.nome; valorFipe = request.valorFipe }
        return ModeloResponse.from(repo.save(modelo))
    }

    @Transactional
    fun deletar(id: Long) { findOrThrow(id); repo.deleteById(id) }

    private fun findOrThrow(id: Long) = repo.findById(id).orElseThrow { ResourceNotFoundException("Modelo", id) }
}

@Service
class CarroService(private val repo: CarroRepository, private val modeloRepo: ModeloRepository) {

    fun listarParaFrontend(): List<CarroListagemResponse> =
        repo.findAllWithModeloAndMarca().map(CarroListagemResponse::from)

    fun listarTodos(): List<CarroResponse> =
        repo.findAllWithModeloAndMarca().map(CarroResponse::from)

    fun buscarPorId(id: Long): CarroResponse = CarroResponse.from(findOrThrow(id))

    @Transactional
    fun criar(request: CarroRequest): CarroResponse {
        val modelo = modeloRepo.findById(request.modeloId).orElseThrow { ResourceNotFoundException("Modelo", request.modeloId) }
        val carro = Carro(
            modelo = modelo,
            timestampCadastro = Instant.now().epochSecond,
            ano = request.ano,
            combustivel = request.combustivel.uppercase(),
            numPortas = request.numPortas,
            cor = request.cor.uppercase()
        )
        return CarroResponse.from(repo.save(carro))
    }

    @Transactional
    fun atualizar(id: Long, request: CarroRequest): CarroResponse {
        val carro = findOrThrow(id)
        val modelo = modeloRepo.findById(request.modeloId).orElseThrow { ResourceNotFoundException("Modelo", request.modeloId) }
        carro.apply {
            this.modelo = modelo; ano = request.ano
            combustivel = request.combustivel.uppercase()
            numPortas = request.numPortas; cor = request.cor.uppercase()
        }
        return CarroResponse.from(repo.save(carro))
    }

    @Transactional
    fun deletar(id: Long) { findOrThrow(id); repo.deleteById(id) }

    private fun findOrThrow(id: Long) = repo.findById(id).orElseThrow { ResourceNotFoundException("Carro", id) }
}
