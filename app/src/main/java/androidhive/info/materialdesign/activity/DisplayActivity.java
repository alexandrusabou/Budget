package androidhive.info.materialdesign.activity;

 import java.util.ArrayList;

 import android.app.Activity;
 import android.app.AlertDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.os.Bundle;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.widget.AdapterView;
 import android.widget.AdapterView.OnItemClickListener;
 import android.widget.AdapterView.OnItemLongClickListener;
 import android.widget.ListView;
 import android.widget.Toast;

 import androidhive.info.materialdesign.R;

/**
 * activity to display all records from SQLite database
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class DisplayActivity extends Activity {

    private DbHelper mHelper;
    private SQLiteDatabase dataBase;

    private ArrayList<String> conturiId = new ArrayList<String>();
    private ArrayList<String> conturi_numeCont = new ArrayList<String>();
    private ArrayList<String> conturi_codCont = new ArrayList<String>();
    private ArrayList<String> conturi_codBuget = new ArrayList<String>();

    private ListView conturiList;
    private AlertDialog.Builder build;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);

        conturiList = (ListView) findViewById(R.id.List);

        mHelper = new DbHelper(this);

        //add new record
        findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),
                        AddActivity.class);
                i.putExtra("update", false);
                startActivity(i);

            }
        });

        //click to update data
        conturiList.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Intent i = new Intent(getApplicationContext(),
                        AddActivity.class);
                i.putExtra("numeCont", conturi_numeCont.get(arg2));
                i.putExtra("codCont", conturi_codCont.get(arg2));
                i.putExtra("codBuget", conturi_codBuget.get(arg2));
                i.putExtra("ID", conturiId.get(arg2));
                i.putExtra("update", true);
                startActivity(i);

            }
        });

        //long click to delete data
        conturiList.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {

                build = new AlertDialog.Builder(DisplayActivity.this);
                build.setTitle("Delete contul " + conturi_codCont.get(arg2) + "/"
                        + conturi_numeCont.get(arg2));
                build.setMessage("Do you want to delete ?");
                build.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Toast.makeText(
                                        getApplicationContext(),
                                        conturi_codCont.get(arg2) + " "
                                                + conturi_numeCont.get(arg2)
                                                + " is deleted.", 3000).show();

                                dataBase.delete(
                                        DbHelper.TABLE_NAME,
                                        DbHelper.KEY_ID + "="
                                                + conturiId.get(arg2), null);
                                displayData();
                                dialog.cancel();
                            }
                        });

                build.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = build.create();
                alert.show();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    /**
     * displays data from SQLite
     */
    private void displayData() {
        dataBase = mHelper.getReadableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DbHelper.TABLE_NAME, null);

        conturiId.clear();
        conturi_codCont.clear();
        conturi_numeCont.clear();
        conturi_codBuget.clear();
        if (mCursor.moveToFirst()) {
            do {
                conturiId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                conturi_codCont.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_CODCONT)));
                conturi_numeCont.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_NUMECONT)));
                conturi_codBuget.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_CODBUGET)));

            } while (mCursor.moveToNext());
        }
        DisplayAdapter disadpt = new DisplayAdapter(DisplayActivity.this,conturiId, conturi_codCont, conturi_numeCont,
                conturi_codBuget);
        conturiList.setAdapter(disadpt);
        mCursor.close();
    }



}
