package org.tourstart.members;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mainActivity extends AppCompatActivity {

    final String ATTRIBUTE_NAME = "name";
    final String ATTRIBUTE_GENDER = "gender";
    final String ATTRIBUTE_BIRTH_DAY = "birthDay";

    myDataBase myBase;
    ListView listMember;
    ArrayList<Member> members;

    String[] from = {ATTRIBUTE_NAME, ATTRIBUTE_GENDER, ATTRIBUTE_BIRTH_DAY};
    int[] to = { R.id.memberName, R.id.memberGender, R.id.memberBirthDay };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBase = new myDataBase(this);
        members = new ArrayList<>();

        readDataBase();

        ArrayList<Map<String, Object>> data = new ArrayList<>(members.size());
        Map<String, Object> member;

        for(int i = 0; i < members.size(); i++){

            member = new HashMap<>();
            member.put(ATTRIBUTE_NAME, members.get(i).firstName + " " + members.get(i).lastName);
            member.put(ATTRIBUTE_BIRTH_DAY, members.get(i).birthDay);
            member.put(ATTRIBUTE_GENDER, members.get(i).gender);

            data.add(member);
        }

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.list_view_item, from, to);

        listMember = (ListView) findViewById(R.id.membersList);
        listMember.setAdapter(sAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add) {

            Intent intent = new Intent(mainActivity.this, editActivity.class);
            intent.putExtra("firstName", "");
            intent.putExtra("lastName", "");
            intent.putExtra("gender", "");
            intent.putExtra("birthDay", "");
            intent.putExtra("address", "");
            intent.putExtra("location", "");
            intent.putExtra("id", -1);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void readDataBase(){

        SQLiteDatabase database = myBase.getReadableDatabase();
        Cursor cursor = database.query("myTable", null, null, null, null, null, null);

        if(cursor.moveToFirst()){

            int firstNameIndex = cursor.getColumnIndex("firstName");
            int lastNameIndex = cursor.getColumnIndex("lastName");
            int genderIndex = cursor.getColumnIndex("gender");
            int birthDayIndex = cursor.getColumnIndex("birthDay");
            int addressIndex = cursor.getColumnIndex("address");
            int locationIndex = cursor.getColumnIndex("location");
            int index = cursor.getColumnIndex("id");

            do {

                Member member = new Member(
                        cursor.getString(firstNameIndex),
                        cursor.getString(lastNameIndex),
                        cursor.getString(genderIndex),
                        cursor.getString(birthDayIndex),
                        cursor.getString(addressIndex),
                        cursor.getString(locationIndex),
                        cursor.getInt(index)
                );

                members.add(member);

            }while(cursor.moveToNext());
        }

        cursor.close();
        myBase.close();

    }

    public void onClickMember(View view){

        int position = listMember.getPositionForView((View) view.getParent());
        Member memberClick = members.get(position);

        switch (view.getId()){
            case R.id.memberEdit:
                Intent intent = new Intent(mainActivity.this, editActivity.class);
                intent.putExtra("firstName", memberClick.firstName);
                intent.putExtra("lastName", memberClick.lastName);
                intent.putExtra("gender", memberClick.gender);
                intent.putExtra("birthDay", memberClick.birthDay);
                intent.putExtra("address", memberClick.address);
                intent.putExtra("location", memberClick.location);
                intent.putExtra("id", memberClick.id);
                startActivity(intent);
                break;
            case R.id.informationTextMember:
                Intent about = new Intent(mainActivity.this, aboutActivity.class);
                about.putExtra("firstName", memberClick.firstName);
                about.putExtra("lastName", memberClick.lastName);
                about.putExtra("gender", memberClick.gender);
                about.putExtra("birthDay", memberClick.birthDay);
                about.putExtra("address", memberClick.address);
                about.putExtra("location", memberClick.location);
                startActivity(about);
                break;
            default:
                break;
        }

    }
}
