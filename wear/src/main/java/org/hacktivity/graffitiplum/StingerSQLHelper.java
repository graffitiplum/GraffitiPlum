package org.hacktivity.graffitiplum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.SecureRandom;
import java.util.ArrayList;

public class StingerSQLHelper extends SQLiteOpenHelper {

    private static int DATABASE_NUM_MEMBERS = 32768;

    private static final String TABLE_POOLS = "pools";

    private static final String KEY_ID = "id";
    private static final String KEY_POOL = "pooldata";

    private static final String[] COLUMNS = {KEY_ID,KEY_POOL};

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "PoolsDB";

    public StingerSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table.
        String CREATE_POOLS_TABLE = "CREATE TABLE pools ( " +
                "id INT, " +
                "pooldata CHAR(128))";

        db.execSQL(CREATE_POOLS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS pools");

        // create fresh table
        this.onCreate(db);
    }

    public void addPool(String pool) {

        SecureRandom sr = new SecureRandom();

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_ID, sr.nextLong() % DATABASE_NUM_MEMBERS);
        values.put(KEY_POOL, pool);

        // 3. insert
        db.insert(TABLE_POOLS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public String getPool(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_POOLS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        //int id = Integer.parseInt(cursor.getString(0));
        return(cursor.getString(1));

    }

    public String getRandomPool() {

        ArrayList <String> hash = new ArrayList<String>();
        ArrayList<Integer> id = new ArrayList<Integer>();

        SecureRandom sr = new SecureRandom();

        // TODO: fetch a random hash from the DB.

        SQLiteDatabase db = this.getWritableDatabase();


        // TODO: takes too long to cycle through every item.
        String query = "SELECT * FROM pools ORDER BY RANDOM() LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                id.add(Integer.parseInt(cursor.getString(0)));
                hash.add(cursor.getString(1));

                // Add book to books
            } while (cursor.moveToNext());
        }

        if ( hash.size() == 0) {
            return(null);
        }
        else {
            return hash.get(sr.nextInt() % hash.size());
        }
    }


    /*

    // Get All Pools
    public List<String> getAllPools() {
        List<String> pools = new LinkedList<String>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_POOLS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        // return books
        return books;
    }


    // Updating single book
    public int updateBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle()); // get title
        values.put("author", book.getAuthor()); // get author

        // 3. updating row
        int i = db.update(TABLE_BOOKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single book
    public void deleteBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BOOKS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(book.getId()) });

        // 3. close
        db.close();

        Log.d("deleteBook", book.toString());

    }

    */

}
