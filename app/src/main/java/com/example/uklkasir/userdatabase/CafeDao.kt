package com.example.uklkasir.userdatabase

import androidx.room.*

@Dao
interface CafeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTransaksi(detailTransaksi: DetailTransaksi)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenu(menu: Menu)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeja(meja: Meja)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaksi(transaksi: Transaksi)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE email = :mEmail AND password = :mPass")
    fun login(mEmail: String, mPass: String): List<User>

    @Query("SELECT * FROM Menu")
    fun getAllMenu(): List<Menu>

    @Query("SELECT * FROM Transaksi")
    fun getAllTransaksi(): List<Transaksi>

    @Query("SELECT * FROM Meja")
    fun getAllMeja(): List<Meja>

    @Query("SELECT * FROM DetailTransaksi WHERE id_transaksi = :idTransaksi")
    fun getDetailTransaksifromTransaksi(idTransaksi: Int): List<DetailTransaksi>

    @Query("SELECT * FROM Menu WHERE jenis = :jenisMenu")
    fun getMenuFilterJenis(jenisMenu: String): List<Menu>

    @Delete
    fun deleteMenu(menu: Menu)

    @Query("UPDATE Menu SET nama_menu = :namaMenu, jenis = :Jenis, harga = :Harga WHERE id_menu = :id")
    fun updateMenu(namaMenu: String, Jenis: String, Harga: Int, id: Int)

    @Query("SELECT * FROM Menu WHERE id_menu = :Id")
    fun getMenu(Id: Int): Menu

    @Query("UPDATE Meja SET nomor_meja = :namaMeja, used = :Used WHERE id_meja = :id")
    fun updateMeja(namaMeja: String, id: Int, Used: Boolean)

    @Delete
    fun deleteMeja(meja: Meja)

    @Query("SELECT nomor_meja FROM Meja WHERE used = 0")
    fun getAllNamaMeja(): List<String>

    @Query("SELECT id_meja FROM Meja WHERE nomor_meja = :namaMeja")
    fun getIdMejaFromNama(namaMeja: String): Int

    @Query("SELECT id_transaksi FROM Transaksi WHERE tgl_transaksi = :tglTransaksi AND id_user = :idUser AND id_meja = :idMeja AND nama_pelanggan = :namaPelanggan AND status = :Status")
    fun getIdTransaksiFromOther(tglTransaksi: String, idUser: Int, idMeja: Int, namaPelanggan: String, Status: String): Int

    @Query("SELECT * FROM Meja WHERE id_meja = :id")
    fun getMeja(id: Int): Meja

    @Delete
    fun deleteTransaksi(transaksi: Transaksi)

    @Delete
    fun deleteDetailTransaksi(detailTransaksi: DetailTransaksi)

    @Query("UPDATE Transaksi SET nama_pelanggan = :namaPelanggan, status = :Status WHERE id_transaksi = :idTransaksi")
    fun updateTransaksi(namaPelanggan: String, Status: String, idTransaksi: Int)

    @Query("SELECT * FROM DetailTransaksi WHERE id_transaksi = :Id")
    fun getDetailTransaksi(Id: Int): List<DetailTransaksi>

    @Query("SELECT * FROM Transaksi WHERE id_transaksi = :Id")
    fun getTransaksi(Id: Int): Transaksi

    @Query("SELECT COUNT(id_detail_transaksi) as count, id_menu FROM DetailTransaksi GROUP BY id_menu LIMIT 5")
    fun getCountDetailTransaksi(): List<CountMenu>

    @Query("SELECT COUNT(id_transaksi) as count, tgl_transaksi FROM Transaksi GROUP BY tgl_transaksi LIMIT 10")
    fun getCountTransaksi(): List<CountTransaksi>

    @Query("SELECT MAX(count) FROM (SELECT COUNT(id_detail_transaksi) as count FROM DetailTransaksi GROUP BY id_menu)")
    fun getMaxCountDetailTransaksi(): Int
}