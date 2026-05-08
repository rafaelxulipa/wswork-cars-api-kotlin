package br.com.wswork.cars.model

import jakarta.persistence.*

@Entity
@Table(name = "marca")
class Marca(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "nome_marca", nullable = false, unique = true)
    var nomeMarca: String,

    @OneToMany(mappedBy = "marca", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val modelos: MutableList<Modelo> = mutableListOf()
)
