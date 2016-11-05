package com.example.artemij.ppplus;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textHeader;
    Button singleButton;
    ListView studList;

    String[] names;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "HelveticaNeueCyr-Light.otf");
        textHeader = (TextView)findViewById(R.id.textView1);
        textHeader.setTypeface(myTypeFace);

        studList = (ListView) findViewById(R.id.listView);

        //creating the adapter:
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.s_names, android.R.layout.simple_list_item_1);
        studList.setAdapter(adapter);

        //to process the click on list item
        studList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "itemSelect: position = " + position + " id = " + id);
            }
        });

        singleButton = (Button)findViewById(R.id.button);
        singleButton.setTypeface(myTypeFace);
        singleButton.setText("Press me, baby");

        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //getting an array from resources:
        names = getResources().getStringArray(R.array.s_names);

    }
}
