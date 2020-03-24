package com.example.ipapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ipapp.model.PAR

class OpenHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERS) {
    companion object{
        private val DATABASE_VERS = 1
        private val DATABASE_NAME = "IpApp.db"

        private val TABLE_NAME_DOC = "DOC"
        private val COL_AUTO = "autoTranslate"

        private val TABLE_NAME = "PAR"
        private val COL_ID = "parNumber"
        private val COL_DOC_ID = "docId"
        private val COL_TXT = "text"
        private val COL_ORIG = "orig"
        private val COL_RO = "ro"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_PAR_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_DOC_ID INTEGER, $COL_TXT TEXT, $COL_ORIG TEXT, $COL_RO TEXT)")
        val CREATE_DOC_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME_DOC ($COL_DOC_ID INTEGER PRIMARY KEY, $COL_AUTO NUMERIC)")

        db!!.execSQL(CREATE_PAR_TABLE_QUERY)
        db!!.execSQL(CREATE_DOC_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_DOC")

        onCreate(db)
    }

    val allPAR:List<PAR>
        get(){
            val lstPAR = ArrayList<PAR>()
            val selectQuery = "SElECT * FROM $TABLE_NAME"
            val db:SQLiteDatabase = this.writableDatabase
            val cursor:Cursor = db.rawQuery(selectQuery,null)
            if(cursor.moveToFirst()){
                do{
                    val PAR = PAR()
                    PAR.parNumber = cursor.getColumnIndex(COL_ID)
                    PAR.docId = cursor.getColumnIndex(COL_DOC_ID)
                    PAR.text = cursor.getString(cursor.getColumnIndex(COL_TXT))
                    PAR.orig = cursor.getString(cursor.getColumnIndex(COL_ORIG))
                    PAR.ro = cursor.getString(cursor.getColumnIndex(COL_RO))

                    lstPAR.add(PAR)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstPAR
        }

    fun insertPAR(par:PAR){
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_DOC_ID,par.docId)
        values.put(COL_TXT,par.text)
        values.put(COL_ORIG,par.orig)
        values.put(COL_RO,par.ro)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun updatePAR(par:PAR):Int{
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_DOC_ID,par.docId)
        values.put(COL_TXT,par.text)
        values.put(COL_ORIG,par.orig)
        values.put(COL_RO,par.ro)

        return db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(par.parNumber.toString()))
    }

    fun deletePAR(par:PAR){
        val db:SQLiteDatabase = this.writableDatabase
        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(par.parNumber.toString()))
        db.close()
    }
}