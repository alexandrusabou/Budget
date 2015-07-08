package androidhive.info.materialdesign.activity;

/**
 * Created by alexa on 7/2/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    /*Asa arata varianta initiala
    public class DbHelper extends SQLiteOpenHelper {
	static String DATABASE_NAME="userdata";
	public static final String TABLE_NAME="user";
	public static final String KEY_FNAME="fname";
	public static final String KEY_LNAME="lname";
	public static final String KEY_ID="id";
     */
    static String DATABASE_NAME="buget.db";
    public static final String TABLE_NAME="conturi";
    public static final String KEY_NUMECONT="numeCont";
    public static final String KEY_CODCONT="codCont";
    public static final String KEY_CODBUGET="codBuget";
    public static final String KEY_ID="id";
    public static final String KEY_ID_BUGET="idBuget";
    public static final String KEY_NUME_BUGET="numeBuget";
//de aici definim tabela buget, cu doua campuri: cod si denumire (categorie) buget
    public static final String TABLE_NUME_BUGET="nBuget";//adica nomenclatorBuget

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE1="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_NUMECONT+" TEXT, "+KEY_CODCONT+" TEXT, "+
               KEY_CODBUGET+" TEXT)";
        Log.d("DB",CREATE_TABLE1);
        db.execSQL(CREATE_TABLE1);
        Log.d("DB", "Linia 48 din DBHelper");
        String CREATE_TABLE2="CREATE TABLE "+TABLE_NUME_BUGET+" ("+KEY_ID_BUGET+" INTEGER PRIMARY KEY, "+KEY_CODBUGET+" TEXT, "+KEY_NUME_BUGET+" TEXT)";
        Log.d("DB",CREATE_TABLE2);
        db.execSQL(CREATE_TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public void insertLabel(String label){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_CODBUGET, label);
        db.insert(TABLE_NUME_BUGET, null, values);
        db.close();
    }
    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NUME_BUGET;

        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public void insertTest() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CODCONT, "unu");
        values.put(KEY_NUMECONT, "Contul unu");
        values.put(KEY_CODBUGET, "categoria 1");
        try {
            long insert = db.insert(TABLE_NAME, null, values);
        } catch (Exception e){
             e.printStackTrace();
    }
        db.close();
    }

}
