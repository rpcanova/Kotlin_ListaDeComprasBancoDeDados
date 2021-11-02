package com.example.app_06_listacomprasclassedados

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
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
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
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

        adapter.clear()
        adapter.addAll(produtosGlobal)

        var soma = 0.0
        for (item in produtosGlobal) {
            soma += item.valor * item.quantidade
        }
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        txtTotal.text = "Total: ${f.format(soma)}"
    }
}