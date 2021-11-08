package com.example.app_07_listacomprasbancodados

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.db.rowParser
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val produtosAdapter = ProdutoAdapter(this)
        val listViewProduto = findViewById<ListView>(R.id.listViewProdutos)

        listViewProduto.adapter = produtosAdapter

        btnAdicionar.setOnClickListener {
            startActivity<CadastroActivity>()
        }

        listViewProdutos.setOnItemClickListener { adapterView: AdapterView<*>, view, position: Int, id ->
            val item = produtosAdapter.getItem(position)
            produtosAdapter.remove(item)
            produtosGlobal.remove(item)

            var soma = 0.0
            for (item in produtosGlobal) {
                soma += item.valor * item.quantidade
            }
            val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            txtTotal.text = "Total: ${f.format(soma)}"
        }
    }

    override fun onResume() {
        super.onResume()

        val adapter = listViewProdutos.adapter as ProdutoAdapter
        database.use {
            select("produtos").exec{
                val parser = rowParser{id: Int, nome: String, quantidade: Int, valor: Double, foto: ByteArray? ->
                    Produto(id, nome, quantidade, valor, foto?.toBitmap())
                }
                var listaProdutos = parseList(parser)

                adapter.clear()
                adapter.addAll(listaProdutos)

                var soma = 0.0

                for (item in listaProdutos) {
                    soma += item.valor * item.quantidade
                }
                val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                txtTotal.text = "TOTAL: ${f.format(soma)}"
            }
        }
    }
}