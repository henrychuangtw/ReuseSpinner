package tw.henrychuang.lib;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;


/**
 * Created by henry.chuang on 2016/4/7.
 */
public class SpinnerActivity extends Activity {
    public static String Extra_Resource = "SpinnerActivity.Extra_Resource";
    public static String Extra_SelectOne = "SpinnerActivity.Extra_SelectOne";
    public static String Result_Data = "SpinnerActivity.Result_Data";

    private String TAG = SpinnerActivity.class.getSimpleName();
    private Context mContext;
    private CustomSpinner mSpinner;
    private ArrayAdapter<String> mArrayAdapter;
    private ArrayList<Map.Entry<String,String>> mArrayList_resource;
    private ArrayList<String> mArrayList_value;
    private ArrayList<String> mArrayList_key;
    private String mSelectOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spinner_activity);

        mSpinner = (CustomSpinner) findViewById(R.id.spinner_activity_spinner);
        mContext = SpinnerActivity.this;

        mArrayList_resource = (ArrayList<Map.Entry<String,String>>) getIntent().getSerializableExtra(Extra_Resource);
        if(mArrayList_resource == null){
            finish();
        }

        mSelectOne = getIntent().getExtras().getString(Extra_SelectOne, getResources().getString(R.string.str_select));
        Log.i(TAG,"prompt string: " + mSelectOne);

        mArrayList_value = new ArrayList<>();
        mArrayList_key = new ArrayList<>();

        mArrayList_key.add("-999");
        mArrayList_value.add(mSelectOne);

        for (int i = 0; i < mArrayList_resource.size(); i++){
            Map.Entry<String, String> entry = mArrayList_resource.get(i);
            Log.i(TAG, String.format("entry: %s , %s", entry.getKey(), entry.getValue()));
            mArrayList_key.add(entry.getKey());
            mArrayList_value.add(entry.getValue());
        }


        mArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_option, mArrayList_value){
            @Override
            public boolean isEnabled(int position) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    return false;
                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                TextView tv = (TextView) view;
                if(tv.getText().toString().equalsIgnoreCase(mSelectOne)){
                    tv.setTextColor(Color.GRAY);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        Drawable drawable;
                        drawable = mContext.getDrawable(R.drawable.borderbottom);
                        tv.setBackground(drawable);
                    }

                }else {
                    tv.setTextColor(Color.BLACK);
                    if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        tv.setBackground(null);
                    } else {
                        tv.setBackgroundDrawable(null);
                    }
                }

                return view;
            }

        };


        mSpinner.setAdapter(mArrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    String key = mArrayList_key.get(position);
                    String value = mArrayList_value.get(position);

                    Log.i(TAG, String.format("select item: %s , %s", key, value));

                    Intent intent = new Intent();
                    intent.putExtra(Result_Data, new AbstractMap.SimpleEntry<>(key, value));
                    setResult(RESULT_OK, intent);
                    finish();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        mSpinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
            @Override
            public void onSpinnerOpened() {


            }

            @Override
            public void onSpinnerClosed() {
                if(mSpinner.getSelectedItemPosition() == 0)
                    finish();

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("henrytest", "onBackPressed");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");

        mSpinner.performClick();
    }



}
