package com.example.uklkasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uklkasir.adapter.ItemAdapter
import com.example.uklkasir.adapter.MejaAdapter
import com.example.uklkasir.userdatabase.CafeDatabase
import com.example.uklkasir.userdatabase.Meja

class ListMejaActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var addButton: ImageButton

    lateinit var adapter: MejaAdapter
    private var listMeja = mutableListOf<Meja>()

    lateinit var db: CafeDatabase

    var role = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_meja)

        recycler = findViewById(R.id.recyclerMeja)
        addButton = findViewById(R.id.buttonAdd)

        db = CafeDatabase.getInstance(applicationContext)

        role = intent.getStringExtra("role")!!

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = MejaAdapter(listMeja)
        recycler.adapter = adapter

        if(role == "Admin"){
            swipeToGesture(recycler)
        }
        else{
            addButton.isEnabled = false
            addButton.visibility = View.INVISIBLE
        }

        addButton.setOnClickListener{
            val moveIntent = Intent(this@ListMejaActivity, AddMejaActivity::class.java)
            startActivity(moveIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        getMeja()
    }

    fun getMeja(){
        listMeja.clear()
        listMeja.addAll(db.cafeDao().getAllMeja())
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
                            var adapter: MejaAdapter = itemRv.adapter as MejaAdapter
                            db.cafeDao().deleteMeja(adapter.getItem(position))
                            adapter.notifyItemRemoved(position)
                            val intent = intent
                            finish()
                            startActivity(intent)
                        }
                        ItemTouchHelper.RIGHT -> {
                            val moveIntent = Intent(this@ListMejaActivity, EditMejaActivity::class.java)
                            var adapter: MejaAdapter = itemRv.adapter as MejaAdapter
                            var meja = adapter.getItem(position)
                            moveIntent.putExtra("ID", meja.id_meja)
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