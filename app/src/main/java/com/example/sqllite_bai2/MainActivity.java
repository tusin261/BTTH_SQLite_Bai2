package com.example.sqllite_bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    ListView listView;
    ArrayList<Place> placeList;
    CustomAdapter adapter;
    Button btnAdd;
    ImageView btnDelete;
    DataPlace dataPlace;
    EditText edtT;
    ArrayList idList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       dataPlace = new DataPlace(this,"placedb.sqlite",null,1);
        listView = findViewById(R.id.lvItem);
        btnAdd = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        edtT = findViewById(R.id.edtT);
        placeList = new ArrayList<>();
        idList = new ArrayList();
        placeList = getNameList();
        adapter = new CustomAdapter(placeList,this,R.layout.listitem);
        listView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dataPlace.deleteAll();
                dataPlace.addPlace(new Place(edtT.getText().toString()));
                placeList.clear();
                getNameList();
                adapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView btnDelete = view.findViewById(R.id.btnDelete);
                ImageView btnEdit = view.findViewById(R.id.btnEdit);
               btnDelete.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dataPlace.removePlace(position,placeList);
                       Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_SHORT);
                   }
               });
               String name = edtT.getText().toString();
               btnEdit.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                      dataPlace.updatePlace(name,placeList,position);
                   }
               });

            }
        });
        adapter.notifyDataSetChanged();


    }
    private ArrayList getNameList(){
        placeList.clear();
        idList.clear();
        for(Iterator iterator = dataPlace.getAll().iterator();iterator.hasNext(); ){
            Place place =(Place) iterator.next();
            placeList.add(place);
            idList.add(place.getId());
        }
        return placeList;
    }
}