package com.example.artemij.ppplus;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textHeader;

    String[] names = {"Артем", "Саня Ф.", "Саня К.", "Миша Ц.", "Аня", "Юля", "Миша М.", "Настя Б."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "HelveticaNeueCyr-Light.otf");
        textHeader = (TextView)findViewById(R.id.textView1);
        textHeader.setTypeface(myTypeFace);

        ListView studList = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        studList.setAdapter(adapter);
    }
}
