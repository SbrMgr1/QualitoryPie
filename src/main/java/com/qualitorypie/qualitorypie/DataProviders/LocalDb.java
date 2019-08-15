package com.qualitorypie.qualitorypie.DataProviders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.qualitorypie.qualitorypie.Models.BaseModel;

import java.util.Iterator;
import java.util.Map;

public class LocalDb extends SQLiteOpenHelper {
    private static final String DB_NAME = "qualitory_pie";
    private static final String delete_exising_table_in_this_version = "";
    private BaseModel MODEL;

    public LocalDb(Context context, BaseModel model) {
        super(context, DB_NAME, null, 1);
        MODEL = model;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String stmt_open = "CREATE TABLE IF NOT EXISTS " + MODEL.getTableName() + " (";
        String create_table = "";
        Iterator it = MODEL.getTableSchema().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (create_table != "") {
                create_table += ",";
            }
            create_table += pair.getKey() + " " + pair.getValue();
            it.remove(); // avoids a ConcurrentModificationException
        }
        String stmt_close = ")";
        create_table = stmt_open + create_table + stmt_close;
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + MODEL.getTableName());
        onCreate(db);
    }

    public boolean addData(Map<String, String> datas) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (delete_exising_table_in_this_version.equals(MODEL.getTableName())) {
            onUpgrade(db, 1, 1);
        } else {
            onCreate(db);
        }
        ContentValues contentValues = new ContentValues();

        Iterator it = datas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            contentValues.put(String.valueOf(pair.getKey()), String.valueOf(pair.getValue()));
            it.remove();
        }

        long result = db.insert(MODEL.getTableName(), null, contentValues);

        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean updateData(Map<String, String> datas, Integer product_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (delete_exising_table_in_this_version.equals(MODEL.getTableName())) {
            onUpgrade(db, 1, 1);
        } else {
            onCreate(db);
        }
        ContentValues contentValues = new ContentValues();

        Iterator it = datas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            contentValues.put(String.valueOf(pair.getKey()), String.valueOf(pair.getValue()));
            it.remove();
        }
        String where = MODEL.getPrimaryField() + "=?";
        String[] whereArgs = new String[]{String.valueOf(product_id)};

        long result = db.update(MODEL.getTableName(), contentValues, where, whereArgs);

        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getData(Integer page_size, Integer next_offset, Map<String, String> queries) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (delete_exising_table_in_this_version.equals(MODEL.getTableName())) {
            onUpgrade(db, 1, 1);
        } else {
            onCreate(db);
        }
        String query = "SELECT * FROM " + MODEL.getTableName();
        Iterator it = queries.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            query += " WHERE " + String.valueOf(pair.getKey()) + " LIKE '%" + String.valueOf(pair.getValue()) + "%'";
            it.remove();
        }
        query += " LIMIT " + page_size + " OFFSET " + next_offset;

        Log.d("here", query);

        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData(Integer page_size, Integer next_offset) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (delete_exising_table_in_this_version.equals(MODEL.getTableName())) {
            onUpgrade(db, 1, 1);
        } else {
            onCreate(db);
        }
        String query = "SELECT * FROM " + MODEL.getTableName();
        query += " LIMIT " + page_size + " OFFSET " + next_offset;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor rowQuery(String stmt) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (delete_exising_table_in_this_version.equals(MODEL.getTableName())) {
            onUpgrade(db, 1, 1);
        } else {
            onCreate(db);
        }
        String query = stmt;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getSingleData(Integer product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (delete_exising_table_in_this_version.equals(MODEL.getTableName())) {
            onUpgrade(db, 1, 1);
        } else {
            onCreate(db);
        }
        String query = "SELECT * FROM " + MODEL.getTableName();
        query += " WHERE " + MODEL.getPrimaryField() + " = " + product_id;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean deleteData(int primary_id_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (delete_exising_table_in_this_version.equals(MODEL.getTableName())) {
            onUpgrade(db, 1, 1);
        } else {
            onCreate(db);
        }
        String where = MODEL.getPrimaryField() + "=?";
        String[] whereArgs = new String[]{String.valueOf(primary_id_value)};
        ContentValues contentValues = new ContentValues();
        contentValues.put("deleted", 1);
        long result = db.update(MODEL.getTableName(), contentValues, where, whereArgs);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean completeRemoveData(int primary_id_value) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (delete_exising_table_in_this_version.equals(MODEL.getTableName())) {
            onUpgrade(db, 1, 1);
        } else {
            onCreate(db);
        }
        String where = MODEL.getPrimaryField() + "=? and deleted=?";
        String[] whereArgs = new String[]{String.valueOf(primary_id_value), "1"};

        long result = db.delete(MODEL.getTableName(), where, whereArgs);
        if (result == -1) {
            return false;
        }
        return true;
    }
}
