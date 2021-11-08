package com.example.app_07_listacomprasbancodados

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast

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
                database.use {
                    val idProduto = insert("Produtos",
                    "nome" to produto,
                    "quantidade" to qtde,
                    "valor" to valor.toDouble(),
                    "foto" to imageBitmap?.toByteArray()
                    )

                    if(idProduto!=-1L){
                        toast("Item inserido com sucesso!")
                        txtProduto.text.clear()
                        txtQtde.text.clear()
                        txtValor.text.clear()
                    } else{
                        toast("Erro ao inserir no banco de dados")
                    }
                }
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