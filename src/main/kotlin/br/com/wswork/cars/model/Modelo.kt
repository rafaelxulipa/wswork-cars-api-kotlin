package br.com.wswork.cars.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "modelo")
class Modelo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    var marca: Marca,

    @Column(nullable = false)
    var nome: String,

    @Column(name = "valor_fipe", nullable = false)
    var valorFipe: BigDecimal,

    @OneToMany(mappedBy = "modelo", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val carros: MutableList<Carro> = mutableListOf()
)
