package com.example.listadecomprasnovo

import android.graphics.BitmapFactory
import java.io.File

object ProdutoMapper {

    // Converte Produto (UI Model) para ProdutoEntity (Banco de Dados)
    fun toEntity(produto: Produto, fotoPath: String? = null): ProdutoEntity {
        return ProdutoEntity(
            id = produto.id,
            nome = produto.nome,
            quantidade = produto.quantidade,
            valor = produto.valor,
            fotoUri = fotoPath // Salva o caminho da foto
        )
    }

    // Converte ProdutoEntity (Banco de Dados) para Produto (UI Model)
    fun fromEntity(entity: ProdutoEntity): Produto {
        return Produto(
            id = entity.id,
            nome = entity.nome,
            quantidade = entity.quantidade,
            valor = entity.valor,
            foto = entity.fotoUri?.let { path ->
                // 🟢 AQUI você coloca o código que verifica se é um arquivo local
                val file = File(path)
                if (file.exists() && file.canRead()) {
                    BitmapFactory.decodeFile(path)
                } else {
                    null
                }
            }
        )
    }
}
