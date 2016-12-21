package com.example.hl_th.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import adapter.CustomExpandListView;
import entities.BaseItem;

public class Test extends AppCompatActivity {
    ExpandableListView expandableListView;
    CustomExpandListView customExpandListView;
    ArrayList<String> header_expand;
    HashMap<String, ArrayList<BaseItem>> item_expand_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareDataForExpand();
        customExpandListView = new CustomExpandListView(this, header_expand, item_expand_list);
        expandableListView.setAdapter(customExpandListView);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(Test.this,"ksadfskladf",Toast.LENGTH_LONG).show();
                return false;
            }
        });


    }
    private void prepareDataForExpand() {
        header_expand = new ArrayList<>();
        item_expand_list = new HashMap<>();
        ArrayList<BaseItem> arrayList = new ArrayList<>();

        arrayList.add(new BaseItem("A", R.mipmap.ic_atm_marker));
        arrayList.add(new BaseItem("A", R.mipmap.ic_atm_marker));
        arrayList.add(new BaseItem("A", R.mipmap.ic_atm_marker));
        arrayList.add(new BaseItem("A", R.mipmap.ic_atm_marker));
        arrayList.add(new BaseItem("A", R.mipmap.ic_atm_marker));
        arrayList.add(new BaseItem("A", R.mipmap.ic_atm_marker));
        arrayList.add(new BaseItem("A", R.mipmap.ic_atm_marker));

        header_expand.add(new String("Bank and Address"));

        item_expand_list.put(header_expand.get(0), arrayList);
    }
}
