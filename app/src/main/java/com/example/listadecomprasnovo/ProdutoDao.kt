package com.example.listadecomprasnovo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//Manipulações na tabela do BD
@Dao
interface ProdutoDao{
    @Insert
    suspend fun inserir(produtos: ProdutoEntity): Long

    @Query("SELECT * FROM produtos WHERE id = :id")
    suspend fun obterProdutoPorId(id: Int): ProdutoEntity?

    @Query("SELECT * FROM produtos")
    suspend fun obterTodosProdutos(): List<ProdutoEntity>

    @Update
    suspend fun atualizar(produtos: ProdutoEntity)

    @Delete
    suspend fun deletar(produtos: ProdutoEntity)

    @Query("SELECT * FROM produtos WHERE quantidade > 0")
    suspend fun obterProdutosDisponiveis(): List<ProdutoEntity>

    // Atualizar a quantidade de um produto
    @Query("UPDATE produtos SET quantidade = :quantidade WHERE id = :id")
    suspend fun atualizarQuantidade(id: Int, quantidade: Int)

    // Atualizar a foto do produto
    @Query("UPDATE produtos SET fotoUri = :foto WHERE id = :id")
    suspend fun atualizarFoto(id: Int, foto: String)
}