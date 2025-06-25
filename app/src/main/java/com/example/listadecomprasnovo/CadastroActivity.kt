package com.example.listadecomprasnovo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private val COD_IMAGE = 101
    private var imageBitMap: Bitmap? = null

    var imageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)

        val img_photo_product = findViewById<ImageView>(R.id.img_photo_product)
        img_photo_product.setOnClickListener {
            abrirGaleria()
        }

        //Trazendo do layout
        val btn_insert = findViewById<Button>(R.id.btn_insert_product)
        val txt_product = findViewById<EditText>(R.id.txt_product)
        val txt_qtd = findViewById<EditText>(R.id.txt_qtd)
        val txt_value = findViewById<EditText>(R.id.txt_value)

        //Botão para inserir o produto
        btn_insert.setOnClickListener {
            val products = txt_product.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_value.text.toString()

            if (products.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {

                // Criando um objeto ProdutoEntity para o Room
                val prod = ProdutoEntity(
                    nome = products,
                    quantidade = qtd.toInt(),
                    valor = valor.toDouble(),
                    fotoUri = imageUri //Aqui passa a URI como string
                )

                // Inserindo com coroutine (Room exige isso!)
                lifecycleScope.launch {
                    val db = AppDatabase.getInstance(applicationContext)
                    val produtoDao = db.produtoDao()

                    val idPoduto = produtoDao.inserir(prod)

                    Toast.makeText(applicationContext, "Item inserido com sucesso", Toast.LENGTH_SHORT).show()

                    txt_product.text.clear()
                    txt_qtd.text.clear()
                    txt_value.text.clear()
                }

            } else {
                txt_product.error = if (txt_product.text.isEmpty()) "Preencha o nome do produto" else null
                txt_qtd.error = if (txt_qtd.text.isEmpty()) "Preencha a quantidade do produto" else null
                txt_value.error = if (txt_value.text.isEmpty()) "Preencha o valor do produto" else null
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                imageUri = uri.toString()
                val inputStream = contentResolver.openInputStream(uri)
                imageBitMap = BitmapFactory.decodeStream(inputStream)
                val img_photo_product = findViewById<ImageView>(R.id.img_photo_product)
                img_photo_product.setImageBitmap(imageBitMap)
            }
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }
}
