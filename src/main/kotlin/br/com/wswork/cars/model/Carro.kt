package br.com.wswork.cars.model

import jakarta.persistence.*

@Entity
@Table(name = "carro")
class Carro(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "timestamp_cadastro", nullable = false)
    var timestampCadastro: Long,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modelo_id", nullable = false)
    var modelo: Modelo,

    @Column(nullable = false)
    var ano: Int,

    @Column(nullable = false)
    var combustivel: String,

    @Column(name = "num_portas", nullable = false)
    var numPortas: Int,

    @Column(nullable = false)
    var cor: String
)
