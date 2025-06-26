package com.example.listadecomprasnovo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var productsAdapter: ProductAdapter
    private lateinit var listView: ListView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializa o ListView e o Adapter uma vez só
        listView = findViewById(R.id.list_view_products)
        productsAdapter = ProductAdapter(this)
        listView.adapter = productsAdapter


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //botao adicionar
        val btn_add = findViewById<Button>(R.id.btn_add)
        btn_add.setOnClickListener {
            //Criando uma Intent explicita que levara para a tela de cadastro de produto
            val intent = Intent(this, CadastroActivity::class.java)

            //Iniciando a atividade
            startActivity(intent)
        }


        //Declaracao do adaptador
        val productsAdapter = ProductAdapter(this)


        /* Iremos transferir esse código que exibe os dados para o metodo onResumo la embaixo, pois
            ele irá sempre ser chamado independente se a tela esta sendo criada ou não
        //Recebendo a lista(produtosGlobal) como parametro e adicionando todos os elementos
        val produtosGlobal = Utils.produtosGlobal
        productsAdapter.addAll(produtosGlobal)


        //Ligacao com a listView para exibir os itens
        val listView = findViewById<ListView>(R.id.list_view_products)
        listView.adapter = productsAdapter */


        // Corroutine para acessar o banco

    }
    override fun onResume() {
        super.onResume()


        lifecycleScope.launch {
            // 1. Acessar o banco
            val db = AppDatabase.getInstance(applicationContext)
            val produtoDao = db.produtoDao()

            // 2. Buscar do banco
            val produtosEntity = produtoDao.obterTodosProdutos()

            // 3. Converter para Produto
            val produtos = produtosEntity.map { ProdutoMapper.fromEntity(it) }

            // 4. Atualizar o adapter
            productsAdapter.clear()
            productsAdapter.addAll(produtos)
            productsAdapter.notifyDataSetChanged()

            // 5. Atualizar o total
            val soma = produtos.sumOf { it.valor * it.quantidade }
            val txt_total_value = findViewById<TextView>(R.id.txt_total_value)
            val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            txt_total_value.text = "Total: ${f.format(soma)}"

            // 6. Excluir (caso deseje manter isso no banco depois, veja observação abaixo)
            listView.setOnItemLongClickListener { _, _, position, _ ->
                val item = productsAdapter.getItem(position)

                if (item != null) {
                    lifecycleScope.launch {
                        val produtoDao = AppDatabase.getInstance(applicationContext).produtoDao()
                        produtoDao.deletar(ProdutoMapper.toEntity(item, item.foto?.toString()))

                        productsAdapter.remove(item)
                        productsAdapter.notifyDataSetChanged()

                        Toast.makeText(this@MainActivity, "Item excluído: ${item.nome}", Toast.LENGTH_SHORT).show()
                    }
                }

                true
            }


        }
    }





}