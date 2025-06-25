package com.example.listadecomprasnovo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

//Criando a tabela produto do banco de dados
@Entity(tableName = "produtos")
data class ProdutoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "quantidade") val quantidade: Int,
    @ColumnInfo(name = "valor") val valor: Double,
    @ColumnInfo(name = "fotoUri") val fotoUri: String? = null  // salvar URI da imagem
)
