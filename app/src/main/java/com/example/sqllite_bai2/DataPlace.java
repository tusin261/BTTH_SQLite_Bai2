package com.example.sqllite_bai2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataPlace extends SQLiteOpenHelper{

    public DataPlace(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Place ("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"name TEXT NOT NULL)";
        db.execSQL(sql);
    }
    public void addPlace(Place place){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",place.getName());
        db.insert("Place",null,values);
    }
    public void removePlace(int position, List<Place> list){
        SQLiteDatabase db = this.getWritableDatabase();
        String name = list.get(position).getName();
        db.delete("Place","name=?",new String[]{String.valueOf(name)});
    }
    public void updatePlace(String name1, List<Place> places, int position) {
        String name = places.get(position).getName();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name1);
        db.update("Place", values, "name = ?", new String[] { name });
        db.close();
    }

    public List<Place> getAll() {
        List<Place> list = new ArrayList<>();
        String sql = "select * from Place";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do {
                Place place = new Place();
                place.setId(cursor.getInt(0));
                place.setName(cursor.getString(1));
                list.add(place);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Place");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
