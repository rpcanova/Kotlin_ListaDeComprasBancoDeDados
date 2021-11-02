package com.example.app_06_listacomprasclassedados

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 101
    var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        fun abrirGaleria() {
            val intent = Intent(Intent.ACTION_GET_CONTENT)

            intent.type = "image/*"

            startActivityForResult(
                Intent.createChooser(intent, "Selecione uma imagem"),
                COD_IMAGE
            )
        }

        imgFotoProduto.setOnClickListener {
            abrirGaleria()
        }

        btnInserir.setOnClickListener {
            val produto = txtProduto.text.toString()
            val qtde = txtQtde.text.toString()
            val valor = txtValor.text.toString()

            if (produto.isNotEmpty() && qtde.isNotEmpty() && valor.isNotEmpty()) {
                val prod = Produto(produto, qtde.toInt(), valor.toDouble())
                produtosGlobal.add(prod)
                txtProduto.text.clear()
                txtQtde.text.clear()
                txtValor.text.clear()
            } else {
                if (txtProduto.text.isEmpty()) {
                    txtProduto.error = "Preencha o nome do Produto"
                } else null

                if (txtQtde.text.isEmpty()) {
                    txtQtde.error = "Preencha a quantidade"
                } else null

                if (txtValor.text.isEmpty()) {
                    txtValor.error = "Preencha o valor"
                } else null
            }
        }
    }
}