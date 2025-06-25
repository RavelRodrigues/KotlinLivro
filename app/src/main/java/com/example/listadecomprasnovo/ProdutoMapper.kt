package com.example.listadecomprasnovo

import android.graphics.BitmapFactory
import android.net.Uri


object ProdutoMapper {
    // Converte Produto (UI Model) para ProdutoEntity (Banco de Dados)
    fun toEntity(produto: Produto, fotoUri: String?): ProdutoEntity {
        return ProdutoEntity(

            nome = produto.nome,
            quantidade = produto.quantidade,
            valor = produto.valor,
            fotoUri = produto.foto?.let { it.toUri().toString() }
        )
    }
    // Converte ProdutoEntity (Banco de Dados) para Produto (UI Model)
    fun fromEntity(entity: ProdutoEntity): Produto {
        return Produto(
            id = entity.id,
            nome = entity.nome,
            quantidade = entity.quantidade,
            valor = entity.valor,
            foto = entity.fotoUri?.let { BitmapFactory.decodeFile(Uri.parse(it).path) }  // Convertendo a URI de volta para Bitmap, se necessário
        )
    }
}

