package com.ryanst.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.util.DataCleanManager;


/**
 * Created by zhengjuntong on 17/7/4.
 */

public class DemoActivity extends BaseActivity {

    private EditText editText;
    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        editText = (EditText) findViewById(R.id.et_url);
        btn1 = (Button) findViewById(R.id.btn_clear);
        btn2 = (Button) findViewById(R.id.btn_url);
        editText.setText("http://planner.speiyou.com/plan_report/templates/parent_briefReport.html?id=1e228af4a4ba27c5c2afa5885da3c7cd&cityCode=0532&stuId=ff808081508bc7b601508ddb40d014ff&uname=%E5%BC%A0%E9%A6%A8%E4%BA%881");
        init();
    }

    public void init() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemoActivity.this, WebActivity.class);
                String url = editText.getText().toString();
                if (TextUtils.isEmpty(url)) {
                    Toast.makeText(DemoActivity.this, "url 为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

    public void clearCache() {
        DataCleanManager.cleanInternalCache(this);
    }
}

