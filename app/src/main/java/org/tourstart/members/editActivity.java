package org.tourstart.members;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;

public class editActivity extends AppCompatActivity {

    int DIALOG_DATE = 1;
    int myYear = 2015;
    int myMonth = 01;
    int myDay = 01;

    myDataBase myBase;
    int id;

    EditText firstName;
    EditText lastName;
    EditText address;
    TextView birthDay;
    TextView location;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        myBase = new myDataBase(this);

        firstName = (EditText)findViewById(R.id.firstNameInput);
        lastName = (EditText)findViewById(R.id.lastNameInput);
        address = (EditText)findViewById(R.id.addressInput);
        spinner = (Spinner)findViewById(R.id.genderInput);
        birthDay = (TextView)findViewById(R.id.birthDayInput);
        location = (TextView)findViewById(R.id.mapLocationInput);

        Intent intent = getIntent();

        id = intent.getIntExtra("id", -1);

        if(id != -1){

            firstName.setText(intent.getCharSequenceExtra("firstName"));
            lastName.setText(intent.getCharSequenceExtra("lastName"));
            address.setText(intent.getCharSequenceExtra("address"));
            location.setText(intent.getCharSequenceExtra("location"));
            birthDay.setText(intent.getCharSequenceExtra("birthDay"));

            String tmp_gender = intent.getStringExtra("gender");

            if(tmp_gender.equals("Man")){
                spinner.setSelection(0);
            }
            else if(tmp_gender.equals("Woman")){
                spinner.setSelection(1);
            }
            else {
                spinner.setSelection(2);
            }

        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_selection, R.layout.spinner_layot);
        spinner.setAdapter(adapter);
        spinner.setSelection(2);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.birthDayInput:
                        showDialog(DIALOG_DATE);
                        break;
                    case R.id.mapLocationInput:
                        Intent intent = new Intent(editActivity.this, mapsActivity.class);
                        startActivityForResult(intent, 1);
                        break;
                    default:
                        break;
                }
            }
        };

        birthDay.setOnClickListener(listener);
        location.setOnClickListener(listener);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("id", id);
        outState.putString("firstName", firstName.getText().toString());
        outState.putString("lastName", lastName.getText().toString());
        outState.putString("address", address.getText().toString());
        outState.putString("gender", spinner.getSelectedItem().toString());
        outState.putString("birthDay", birthDay.getText().toString());
        outState.putString("location", location.getText().toString());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        id = savedInstanceState.getInt("id");
        firstName.setText(savedInstanceState.getCharSequence("firstName"));
        lastName.setText(savedInstanceState.getCharSequence("lastName"));
        address.setText(savedInstanceState.getCharSequence("address"));
        birthDay.setText(savedInstanceState.getCharSequence("birthDay"));
        location.setText(savedInstanceState.getCharSequence("location"));

        String tmp_gender = savedInstanceState.getString("gender");

        if(tmp_gender.equals("Man")){
            spinner.setSelection(0);
        }
        else if(tmp_gender.equals("Woman")){
            spinner.setSelection(1);
        }
        else {
            spinner.setSelection(2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }

        location.setText(data.getStringExtra("location"));

    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear + 1;
            myDay = dayOfMonth;

            if(myMonth < 10 && myDay < 10) {
                birthDay.setText("0" + myDay + ".0" + myMonth + "." + myYear);
            }
            else {
                if(myDay < 10){
                    birthDay.setText("0" + myDay + "." + myMonth + "." + myYear);
                }
                else{
                    if(myMonth < 10){
                        birthDay.setText(myDay + ".0" + myMonth + "." + myYear);
                    }
                    else{
                        birthDay.setText(myDay + "." + myMonth + "." + myYear);
                    }
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public void WriteToBase(){

        SQLiteDatabase database = myBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("firstName", firstName.getText().toString());
        contentValues.put("lastName", lastName.getText().toString());
        contentValues.put("address", address.getText().toString());
        contentValues.put("gender", spinner.getSelectedItem().toString());
        contentValues.put("birthDay", birthDay.getText().toString());
        contentValues.put("location", location.getText().toString());

        if(id == -1){
            database.insert("myTable", null, contentValues);
        } else {
            database.update("myTable", contentValues, "id = " + id, null);
        }

        database.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {

            WriteToBase();
            Intent intent = new Intent(editActivity.this, mainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
