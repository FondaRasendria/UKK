package com.example.uklkasir

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uklkasir.adapter.MejaAdapter
import com.example.uklkasir.adapter.TransaksiAdapter
import com.example.uklkasir.userdatabase.CafeDatabase
import com.example.uklkasir.userdatabase.Transaksi

class ListTransaksiActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView

    lateinit var adapter: TransaksiAdapter

    lateinit var db: CafeDatabase

    private var listTransaksi = arrayListOf<Transaksi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_transaksi)

        recycler = findViewById(R.id.recyclerTransaksi)
        db = CafeDatabase.getInstance(applicationContext)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = TransaksiAdapter(listTransaksi)
        recycler.adapter = adapter
        adapter.onHolderClick = {
            val moveIntent = Intent(this@ListTransaksiActivity, ListDetailTransaksiActivity::class.java)
            moveIntent.putExtra("id_transaksi", it.id_transaksi)
            startActivity(moveIntent)
        }

        swipeToGesture(recycler)
    }

    override fun onResume() {
        super.onResume()
        getTransaksi()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun getTransaksi(){
        listTransaksi.clear()
        listTransaksi.addAll(db.cafeDao().getAllTransaksi())
        adapter.notifyDataSetChanged()
    }

    private fun swipeToGesture(itemRv: RecyclerView){
        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val actionBtnTapped = false

                try{
                    when(direction){
                        ItemTouchHelper.LEFT -> {
                            var adapter: TransaksiAdapter = itemRv.adapter as TransaksiAdapter
                            if(adapter.getItem(position).status == "Dibayar"){
                                db.cafeDao().deleteTransaksi(adapter.getItem(position))
                                var listDetail = db.cafeDao().getDetailTransaksifromTransaksi(adapter.getItem(position).id_transaksi!!)
                                for(i in 0..listDetail.size - 1) run {
                                    db.cafeDao().deleteDetailTransaksi(listDetail[i])
                                }
                                adapter.notifyItemRemoved(position)
                                val intent = intent
                            }
                            else{
                                Toast.makeText(applicationContext, "Status Transaksi harus dibayar sebelum menghapus", Toast.LENGTH_SHORT).show()
                            }
                            finish()
                            startActivity(intent)
                        }
                        ItemTouchHelper.RIGHT -> {
                            var adapter: TransaksiAdapter = itemRv.adapter as TransaksiAdapter
                            var transaksi = adapter.getItem(position)
                            val moveIntent = Intent(this@ListTransaksiActivity, EditTransaksiActivity::class.java)
                            moveIntent.putExtra("ID", transaksi.id_transaksi)
                            startActivity(moveIntent)
                        }
                    }
                }
                catch (e: Exception){
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(itemRv)
    }
}