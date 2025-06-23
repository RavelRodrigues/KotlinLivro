package com.example.listadecomprasnovo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.util.Locale
import com.example.listadecomprasnovo.R

// Criando o adaptador do produto.O adaptador precisa sempre dos dois parametros
class ProductAdapter (contexto: Context): ArrayAdapter<Produto>(contexto, 0){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val v: View

        if(convertView != null){
            v = convertView
        }else{
            //inflar o layout
            v = LayoutInflater.from(context).inflate(R.layout.list_view_item,parent,false)
        }

        //pegando o item na sua posicao e atribuindo a variavel para usar posteriormente
        val item = getItem(position)

        //Validando que o valor digitado nao seja nulo
        item?.let{
            //trazendo os componentes
            val txt_product = v.findViewById<TextView>(R.id.txt_item_product)
            val txt_qtd = v.findViewById<TextView>(R.id.txt_qtd)
            val txt_value = v.findViewById<TextView>(R.id.txt_value)
            val img_product = v.findViewById<ImageView>(R.id.img_photo_product)


            //atribuindo os valores digitados
            txt_qtd.text = item.quantidade.toString()
            txt_product.text = item.nome
            txt_value.text = item.valor.toString()

            //Obtendo a instação de formatação do objeto
            val f = NumberFormat.getCurrencyInstance(Locale("pt","br"))
            //formatando a variável no formato moeda
            txt_value.text = f.format(item.valor)


            //Se o usuario nao inserir foto sera colocada uma padrao
            if (item.foto != null) {
                img_product.setImageBitmap(item.foto)
            }
        }
        return v
    }

}

