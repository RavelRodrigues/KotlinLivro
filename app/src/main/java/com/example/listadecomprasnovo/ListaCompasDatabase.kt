package com.example.listadecomprasnovo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



//ListaComprasDatabase é onde o Room vai gerenciar o banco de dados e as operações CRUD
@Database(entities = [Produto::class], version = 1)
abstract class ListaComprasDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {
        @Volatile
        private var INSTANCE: ListaComprasDatabase? = null

        fun getDatabase(context: Context): ListaComprasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ListaComprasDatabase::class.java,
                    "listaCompras.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
