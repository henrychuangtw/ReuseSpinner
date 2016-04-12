package tw.henrychuang.commonspinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import tw.henrychuang.lib.SpinnerActivity;


public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private int mRequestCode_select_country = 1001;
    private int mRequestCode_select_country_prompt = 1002;
    private TextView mTextView_Country, mTextView_Country_prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView_Country = (TextView)findViewById(R.id.txt_country);
        mTextView_Country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] array = getResources().getStringArray(R.array.array_country);

                ArrayList<Map.Entry<String,String>> arrayList = new ArrayList<>();
                for (String str:array) {
                    String[] ary_split = str.split(",");
                    arrayList.add(new AbstractMap.SimpleEntry<>(ary_split[0], ary_split[1]));
                }

                Intent intent = new Intent(MainActivity.this, SpinnerActivity.class);
                intent.putExtra(SpinnerActivity.Extra_Resource, arrayList);
                startActivityForResult(intent, mRequestCode_select_country);

            }
        });

        mTextView_Country_prompt = (TextView)findViewById(R.id.txt_country_prompt);
        mTextView_Country_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] array = getResources().getStringArray(R.array.array_country);

                ArrayList<Map.Entry<String,String>> arrayList = new ArrayList<>();
                for (String str:array) {
                    String[] ary_split = str.split(",");
                    arrayList.add(new AbstractMap.SimpleEntry<>(ary_split[0], ary_split[1]));
                }

                Intent intent = new Intent(MainActivity.this, SpinnerActivity.class);
                intent.putExtra(SpinnerActivity.Extra_Resource, arrayList);
                intent.putExtra(SpinnerActivity.Extra_SelectOne, getResources().getString(R.string.select_country_prompt));
                startActivityForResult(intent, mRequestCode_select_country_prompt);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == mRequestCode_select_country && resultCode == RESULT_OK){
            if(data != null){
                Map.Entry<String,String> entry = (Map.Entry<String,String>) data.getSerializableExtra(SpinnerActivity.Result_Data);
                if(entry != null){
                    Log.i(TAG, String.format("get result -> key:%s , value:%s", entry.getKey(), entry.getValue()));
                    mTextView_Country.setText(entry.getValue());

                }

            }

        }else  if(requestCode == mRequestCode_select_country_prompt && resultCode == RESULT_OK){
            if(data != null){
                Map.Entry<String,String> entry = (Map.Entry<String,String>) data.getSerializableExtra(SpinnerActivity.Result_Data);
                if(entry != null){
                    Log.i(TAG, String.format("get result -> key:%s , value:%s", entry.getKey(), entry.getValue()));
                    mTextView_Country_prompt.setText(entry.getValue());

                }

            }

        }

    }


}
