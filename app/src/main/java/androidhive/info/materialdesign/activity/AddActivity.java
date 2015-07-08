package androidhive.info.materialdesign.activity;

/**
 * Created by alexa on 7/2/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import androidhive.info.materialdesign.R;

/**
 * activity to get input from user and insert into SQLite database
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class AddActivity extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {
    private Button btn_save;
    private EditText edit_numeCont, edit_codCont, edit_codBuget;
    private DbHelper mHelper;
    private SQLiteDatabase dataBase;
    private String id,numeCont,codCont,codBuget,label;
    private boolean isUpdate;
    private Spinner spinner;
    private Button btnAdd;          //e vorba de butonul de la add cod buget


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
//instantiere widgeturilor din zona categorie buget

        Button btn_save;
        btn_save = (Button)findViewById(R.id.save_btn);
        edit_numeCont=(EditText)findViewById(R.id.txt_numeCont);
        edit_codCont=(EditText)findViewById(R.id.txt_codCont);
        btnAdd=(Button)findViewById(R.id.btn_add);
        EditText inputLabel=(EditText)findViewById(R.id.input_label);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                EditText inputLabel=(EditText)findViewById(R.id.input_label);
               String label = inputLabel.getText().toString();
                if (label.trim().length() > 0) {
                    DbHelper db = new DbHelper(getApplicationContext());
                    //insert new label into database
                    db.insertLabel(label);
                    // making input filed text to blank
                    inputLabel.setText("");
                    // Hiding the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);
                    // loading spinner with newly added data
                    loadSpinnerData();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter label name",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
            id=getIntent().getExtras().getString("ID");
            numeCont=getIntent().getExtras().getString("numeCont");
            codCont=getIntent().getExtras().getString("codCont");
            codBuget=getIntent().getExtras().getString("codBuget");
            edit_numeCont.setText(numeCont);
            edit_codCont.setText(codCont);
//            edit_codBuget.setText(codBuget);
        }
        btn_save.setOnClickListener(this);
        mHelper=new DbHelper(this);
    }

    // saveButton click event
    public void onClick(View v) {
        numeCont=edit_numeCont.getText().toString();
        codCont=edit_codCont.getText().toString().trim();
        codBuget="doi";
//        codBuget=edit_codBuget.getText().toString().trim();
        if(numeCont.length()>0 && codCont.length()>0 && codBuget.length()>0)
        {
            saveData();
        }
        else
        {
            AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddActivity.this);
            alertBuilder.setTitle("Invalid Data");
            alertBuilder.setMessage("Please, Enter valid data");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            alertBuilder.create().show();
        }

    }

    /**
     * save data into SQLite
     */
    private void saveData(){
        dataBase=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(DbHelper.KEY_NUMECONT,numeCont);
        values.put(DbHelper.KEY_CODCONT,codCont );
        values.put(DbHelper.KEY_CODBUGET, codBuget);


//        System.out.println("");
        if(isUpdate)
        {
            //update database with new data
            int stare1=(int) dataBase.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID+"="+id, null);
        }
        else
        {
            //insert data into database
            int stare2= (int) dataBase.insert(DbHelper.TABLE_NAME, null, values);
//            mHelper.insertTest();
        }
        //close database
        dataBase.close();
        finish();


    }
    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        Log.d("DB", "Prima linie din loadspinner");
        // database handler
        DbHelper db = new DbHelper(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

        String label = parent.getItemAtPosition(position).toString();
        // Showing selected spinner ite
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();

        return;
    }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}

