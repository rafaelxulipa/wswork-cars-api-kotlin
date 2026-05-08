package br.com.wswork.cars

import br.com.wswork.cars.dto.MarcaRequest
import br.com.wswork.cars.exception.ResourceNotFoundException
import br.com.wswork.cars.model.Marca
import br.com.wswork.cars.repository.MarcaRepository
import br.com.wswork.cars.service.MarcaService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class MarcaServiceTest {

    @Mock lateinit var repo: MarcaRepository
    @InjectMocks lateinit var service: MarcaService

    @Test
    fun `listarTodas retorna lista mapeada`() {
        `when`(repo.findAll()).thenReturn(listOf(Marca(id = 1L, nomeMarca = "Toyota")))
        val result = service.listarTodas()
        assertThat(result).hasSize(1)
        assertThat(result[0].nomeMarca).isEqualTo("Toyota")
    }

    @Test
    fun `buscarPorId lanca excecao se nao encontrado`() {
        `when`(repo.findById(99L)).thenReturn(Optional.empty())
        assertThatThrownBy { service.buscarPorId(99L) }
            .isInstanceOf(ResourceNotFoundException::class.java)
            .hasMessageContaining("99")
    }

    @Test
    fun `criar lanca excecao se marca duplicada`() {
        `when`(repo.existsByNomeMarcaIgnoreCase("Toyota")).thenReturn(true)
        assertThatThrownBy { service.criar(MarcaRequest("Toyota")) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `criar salva nova marca`() {
        `when`(repo.existsByNomeMarcaIgnoreCase("Honda")).thenReturn(false)
        `when`(repo.save(any())).thenReturn(Marca(id = 10L, nomeMarca = "Honda"))
        val result = service.criar(MarcaRequest("Honda"))
        assertThat(result.id).isEqualTo(10L)
        assertThat(result.nomeMarca).isEqualTo("Honda")
    }
}
