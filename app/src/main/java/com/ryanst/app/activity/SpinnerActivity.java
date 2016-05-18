package com.ryanst.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ryanst.app.Class.Person;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kevin on 16/5/11.
 */
public class SpinnerActivity extends BaseActivity {
    @BindView(R.id.sp_spinner)
    Spinner spSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        ButterKnife.bind(this);
        initSpinner();
    }

    private void initSpinner() {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("张三", "上海 "));
        persons.add(new Person("李四", "上海 "));
        persons.add(new Person("王五", "北京"));
        persons.add(new Person("赵六", "广州 "));
        MyAdapter _MyAdapter = new MyAdapter(this, persons);
        spSpinner.setAdapter(_MyAdapter);
        initSpinnerItemOnClick();
    }

    private void initSpinnerItemOnClick() {
        spSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                Toast.makeText(SpinnerActivity.this, "你点击的是:" + str, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public class MyAdapter extends BaseAdapter {
        private List<Person> mList;
        private Context mContext;

        public MyAdapter(Context pContext, List<Person> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 下面是重要代码
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
            convertView = _LayoutInflater.inflate(R.layout.spinner_item, null);
            if (convertView != null) {
                TextView _TextView1 = (TextView) convertView.findViewById(R.id.textView1);
                TextView _TextView2 = (TextView) convertView.findViewById(R.id.textView2);
                _TextView1.setText(mList.get(position).getPersonName());
                _TextView2.setText(mList.get(position).getPersonAddress());
            }
            return convertView;
        }
    }


}
