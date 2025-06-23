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




    }
    override fun onResume() {
        super.onResume()

        //Limpando antes de adicionar novos produtos para nao duplicar
        productsAdapter.clear()

        //Recebendo a lista(produtosGlobal) como parametro e adicionando todos os elementos
        productsAdapter.addAll(Utils.produtosGlobal)
        productsAdapter.notifyDataSetChanged()

        //Realiza o somatorio
        val soma = Utils.produtosGlobal.sumOf{ it.valor * it.quantidade }

        //Formatando para exibir e importando o txt_total_value
        val txt_total_value = findViewById<TextView>(R.id.txt_total_value)
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        txt_total_value.text = "Total: ${ f.format(soma)}"




        //Excluindo itens
        listView.setOnItemLongClickListener{
                adapterView: AdapterView<*>, view: View, position: Int, id: Long->

            //Buscando o item
            val item = productsAdapter.getItem(position)

            //Removendo
            productsAdapter.remove(item)

            Toast.makeText(this, "Item excluído: $item", Toast.LENGTH_SHORT).show()

            //Retorno informando que o click foi realizado com sucesso
            true

        }
    }




}