package com.myquizz.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {


    String question = "";

    //    final String[] names = {
//            "Gilbert","this is the best guy","Alex","Another flip side","Oliv", "Again and Again the best","Maman","the last word","The world","we are done"
//    };
    private String[] names = new String[0];
    Map<String, String> dictionary = new TreeMap<String, String>();
    ArrayList<String> namesL = new ArrayList<String>();
    ArrayList<String> namesR = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = readFileFromRawFolder();
        inialisationOfListAndQuestion();
        inialisationOfListAndQuestion();
        question = setQuestionToTheTextView();

//        plug data to the list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namesL);
        adapter.notifyDataSetChanged();

        ListView nameList = (ListView) findViewById(R.id.names);
        nameList.setAdapter(adapter);

        nameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String key = namesL.get(position);
                if (dictionary.get(key).equals(question))
                    Toast.makeText(MainActivity.this, "Congratulation good answer", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "Wrong answer", Toast.LENGTH_SHORT).show();

                String newValue = setQuestionToTheTextView();
                while (question.equals(newValue)) {
                    newValue = setQuestionToTheTextView();
                }
                question = newValue;
                Log.d("User click", "Value " + question);
                shuffleListAnswer();


            }
        });


    }

    public String setQuestionToTheTextView() {
        Random random = new Random();
        String value = "";
        int randomNumber = random.nextInt(dictionary.size());
        int counter = 0;

        for (Map.Entry<String, String> mapEnt : dictionary.entrySet()) {
            if (counter == randomNumber) {
                TextView def = (TextView) findViewById(R.id.definition);
                value = mapEnt.getValue();
                def.setText(mapEnt.getValue());
                break;
            }
            counter++;
        }
        return value;
    }


    public void shuffleListAnswer() {
        Collections.shuffle(namesL);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namesL);
//        adapter.notifyDataSetChanged();

        ListView nameList = (ListView) findViewById(R.id.names);
        nameList.setAdapter(adapter);

    }

    /*
     load List and question in different array
    */
    public void inialisationOfListAndQuestion() {
        for (int i = 0; i < names.length; i += 2) {
            namesL.add(names[i]);
        }
        for (int i = 1; i < names.length; i += 2) {
            namesR.add(names[i]);
        }
        for (int i = 0; i <= names.length - 2; i += 2) {
            dictionary.put(names[i], names[i + 1]);
        }

    }
    public String[] readFileFromRawFolder() {
        //        read the file in the raw directory
        InputStream data = getResources().openRawResource(R.raw.data);
        Scanner text = new Scanner(data);
        String result = "";
        while (text.hasNextLine()) {
            result += text.nextLine();
        }
        return result.split("->|,");
    }


}
