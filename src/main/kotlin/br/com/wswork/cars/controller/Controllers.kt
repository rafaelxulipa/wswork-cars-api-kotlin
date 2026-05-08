package br.com.wswork.cars.controller

import br.com.wswork.cars.dto.*
import br.com.wswork.cars.service.CarroService
import br.com.wswork.cars.service.MarcaService
import br.com.wswork.cars.service.ModeloService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/marcas")
class MarcaController(private val service: MarcaService) {

    @GetMapping fun listar() = service.listarTodas()
    @GetMapping("/{id}") fun buscar(@PathVariable id: Long) = service.buscarPorId(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criar(@Valid @RequestBody request: MarcaRequest) = service.criar(request)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @Valid @RequestBody request: MarcaRequest) =
        service.atualizar(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Long) = service.deletar(id)
}

@RestController
@RequestMapping("/api/modelos")
class ModeloController(private val service: ModeloService) {

    @GetMapping fun listar() = service.listarTodos()
    @GetMapping("/{id}") fun buscar(@PathVariable id: Long) = service.buscarPorId(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criar(@Valid @RequestBody request: ModeloRequest) = service.criar(request)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @Valid @RequestBody request: ModeloRequest) =
        service.atualizar(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Long) = service.deletar(id)
}

@RestController
class CarroController(private val service: CarroService) {

    /** Item 2: endpoint no formato cars.json para o frontend React */
    @GetMapping("/api/cars")
    fun listarParaFrontend() = mapOf("cars" to service.listarParaFrontend())

    @GetMapping("/api/carros") fun listar() = service.listarTodos()
    @GetMapping("/api/carros/{id}") fun buscar(@PathVariable id: Long) = service.buscarPorId(id)

    @PostMapping("/api/carros")
    @ResponseStatus(HttpStatus.CREATED)
    fun criar(@Valid @RequestBody request: CarroRequest) = service.criar(request)

    @PutMapping("/api/carros/{id}")
    fun atualizar(@PathVariable id: Long, @Valid @RequestBody request: CarroRequest) =
        service.atualizar(id, request)

    @DeleteMapping("/api/carros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Long) = service.deletar(id)
}
