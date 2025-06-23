package com.example.listadecomprasnovo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.listadecomprasnovo.R





class CadastroActivity : AppCompatActivity() {


    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)

        //Criando ação no botao da imagem para abrir galeria
        val img_photo_product = findViewById<ImageView>(R.id.img_photo_product)
        img_photo_product.setOnClickListener {
            abrirGaleria()
        }



        //Trazendo as classes
        val btn_insert = findViewById<Button>(R.id.btn_insert_product)
        val txt_product = findViewById<EditText>(R.id.txt_product)
        val txt_qtd = findViewById<EditText>(R.id.txt_qtd)
        val txt_value = findViewById<EditText>(R.id.txt_value)

        //Acao do botao
        btn_insert.setOnClickListener {
            val products = txt_product.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_value.text.toString()

            if(products.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {

                //Criando um objeto do tipo produto e passando o nome, quantidade, valor e img
                val prod = Produto(products, qtd.toInt(), valor.toDouble(), imageBitMap)

                //Adicionar o item a variavel produtosGlobal
                Utils.produtosGlobal.add(prod)

                //Limpar as caixas de interacao com o usuario
                txt_product.text.clear()
                txt_qtd.text.clear()
                txt_value.text.clear()

                //Reportando para o usuario preencher o campo que estiver faltando, realizando o if na mesma linha
            }else {
                txt_product.error = if(txt_product.text.isEmpty()) "Preencha o nome do produto" else null
                txt_qtd.error = if(txt_qtd.text.isEmpty()) "Preencha a quantidade do produto" else null
                txt_value.error = if(txt_value.text.isEmpty()) "Preencha o valor do produto" else null
            }


        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //Metodo para abrir a galeria e usuario escolher foto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->

                //Lendo a URI da imagem
                val inputStream = contentResolver.openInputStream(uri)
                //Transformando o resultado em bitmap
                imageBitMap = BitmapFactory.decodeStream(inputStream)
                //Trazendo o componente da foto e Exibindo a imagem no app
                val img_photo_product = findViewById<ImageView>(R.id.img_photo_product)
                img_photo_product.setImageBitmap(imageBitMap)
            }
        }

    }



    //Metodo para abrir a galeria, que é chamado quando há um clique na imagem
    fun abrirGaleria(){
        //Definindo ação de conteudo
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        //Definindo o filtro para imagens
        intent.type = "image/*"

        //Inicializando a activity com resultado
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }
}