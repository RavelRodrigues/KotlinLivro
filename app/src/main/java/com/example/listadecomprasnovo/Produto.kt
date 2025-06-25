package com.example.listadecomprasnovo

import android.graphics.Bitmap

//Crianda a classe de dados e deixando opicional a foto, caso não coloque será definida como nulo.
data class Produto(val id: Int,
                   val nome: String,
                   val quantidade: Int,
                   val valor: Double,
                   val foto: Bitmap?=null
)
