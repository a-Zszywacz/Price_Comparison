package com.example.price_comparison;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.price_comparison.customlist.SingleProduct;

import java.util.ArrayList;
import java.util.List;

public class MySQLite extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public MySQLite(Context context) {
        super(context, "productDB", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String DATABASE_CREATE = "create table products "+
                "(_id integer primary key autoincrement,"+
                "name text not null,"+
                "price text not null,"+
                "shop text not null,"+
                "address text not null);";
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }
    public void delete() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public void dodaj(SingleProduct product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("shop", product.getShop());
        values.put("address", product.getAddress());
        db.insert("products", null, values);
        db.close();
    }

    public void usun(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("products", "_id = ?", new String[] { id } );
        db.close();
    }

    public int aktualizuuj(SingleProduct product){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("shop", product.getShop());
        values.put("address", product.getAddress());

        int i = db.update("products",values,"_id = ?", new String[]{String.valueOf(product.getId())});

        db.close();

        return 1;
    }

    public SingleProduct pobierz(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("products",
                new String[] { "_id", "name", "price", "shop", "address" },
                "_id = ?",
                new String[] { String.valueOf(id) },
                null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        SingleProduct product = new SingleProduct(cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        product.setId(Integer.parseInt(cursor.getString(0)));

        return product;
    }

    public Cursor lista(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from products", null);
    }

    public List<SingleProduct> getProducts(){

        List<SingleProduct> products = new ArrayList<SingleProduct>();
        SQLiteDatabase ourDatabase = this.getReadableDatabase();
        String[] field = {"name","price", "shop", "address" };
        Cursor c = ourDatabase.query("products", field, null, null, null, null, null);

        int iname = c.getColumnIndex("name");
        int iprice = c.getColumnIndex("price");
        int ishop = c.getColumnIndex("shop");
        int iaddress = c.getColumnIndex("address");

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String name = c.getString(iname);
            String price = c.getString(iprice);
            String shop = c.getString(ishop);
            String address = c.getString(iaddress);
            products.add(new SingleProduct(name, price, shop, address));

        }

        return products;
    }

}
