package br.com.wswork.cars.repository

import br.com.wswork.cars.model.Carro
import br.com.wswork.cars.model.Marca
import br.com.wswork.cars.model.Modelo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MarcaRepository : JpaRepository<Marca, Long> {
    fun existsByNomeMarcaIgnoreCase(nomeMarca: String): Boolean
}

interface ModeloRepository : JpaRepository<Modelo, Long> {
    @Query("SELECT m FROM Modelo m JOIN FETCH m.marca")
    fun findAllWithMarca(): List<Modelo>
}

interface CarroRepository : JpaRepository<Carro, Long> {
    @Query("SELECT c FROM Carro c JOIN FETCH c.modelo m JOIN FETCH m.marca")
    fun findAllWithModeloAndMarca(): List<Carro>
}
