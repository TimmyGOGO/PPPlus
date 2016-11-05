package com.example.artemij.ppplus;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
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
        //mode of choosing items:
        studList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //creating the adapter:
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.s_names, android.R.layout.simple_list_item_multiple_choice);
        studList.setAdapter(adapter);

        singleButton = (Button)findViewById(R.id.button);
        singleButton.setTypeface(myTypeFace);
        singleButton.setText("Press me");

        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "checked: ");
                SparseBooleanArray barray = studList.getCheckedItemPositions();
                for (int i = 0; i < barray.size(); i++) {
                    int key = barray.keyAt(i);
                    if (barray.get(key)) {
                        Log.d(LOG_TAG, names[key]);
                    }

                }
            }
        });


        //getting an array from resources:
        names = getResources().getStringArray(R.array.s_names);

    }
}
