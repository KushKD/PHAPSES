package com.doi.himachal.epasshp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.doi.himachal.Adapter.HistoryAdapter;
import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.database.DatabaseHandler;
import com.doi.himachal.utilities.DateTime;
import com.doi.himachal.utilities.Econstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class History extends AppCompatActivity implements View.OnClickListener {

    EditText edit_text_search;
    ListView list;
    TextView fromdate, todate;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String GetFromDate = null;
    private String GetToDate = null;
    List<ResponsePojo> database_history = null;
    EditText mySearchView = null ;
    private LinearLayout search;
    HistoryAdapter adapter;

    DatabaseHandler DB = new DatabaseHandler(History.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //TextView
        fromdate = (TextView) findViewById(R.id.fromdate);
        todate = (TextView) findViewById(R.id.todate);
        list = (ListView)findViewById(R.id.list);

        search = (LinearLayout)findViewById(R.id.search);
        mySearchView = (EditText) findViewById( R.id.edit_text_search ) ;

        SimpleDateFormat fromdateFormat = new SimpleDateFormat("dd-MM-yyyy",  Locale.US);
        //fromdate.setText(fromdateFormat.format(new Date())); // it will show 16/07/2013
        fromdate.setText(DateTime.getDateWithOffset(-7));

        SimpleDateFormat todateFormat = new SimpleDateFormat("dd-MM-yyyy");
        todate.setText(todateFormat.format(new Date())); // it will show 16/07/2013

        dateFormatter = new SimpleDateFormat(Econstants.Date_Format, Locale.US);
        setDateTimeField();

        try{
            database_history = new ArrayList<>();
            database_history =  DB.GetAllOfflineDataViaFunction(fromdate.getText().toString(),todate.getText().toString());
            Log.e("size",Integer.toString(database_history.size()));

            adapter = new HistoryAdapter(History.this,database_history);
            list.setAdapter(adapter);
        }catch(Exception ex){
            Log.e("Error",ex.getLocalizedMessage());
        }

        mySearchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString(), new Filter.FilterListener() {
                    public void onFilterComplete(int count) {
                        if(count==0){
                            list.setVisibility(View.GONE);
                        }else{
                            list.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                // PSDashboardDetailsManufacturing.this.adapter.getFilter().filter(s);



            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ResponsePojo acts_holder = (ResponsePojo) parent.getItemAtPosition( position);
                //CD.showDialog_Acts(ActsActivity.this, acts_holder);

            }
        });

    }

    private void setDateTimeField() {
        fromdate.setOnClickListener(this);
        todate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromdate.setText(dateFormatter.format(newDate.getTime()));
                Log.e("From Date", dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                todate.setText(dateFormatter.format(newDate.getTime()));
                Log.e("to Date", dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v) {
        if (v == fromdate) {
            fromDatePickerDialog.show();
        } else if (v == todate) {
            toDatePickerDialog.show();
        }
    }
}
