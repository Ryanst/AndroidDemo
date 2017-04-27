package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.widget.TagViewContainer;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zhengjuntong on 3/24/17.
 */

public class TagViewContainerActivity extends BaseActivity {
    @BindView(R.id.container)
    TagViewContainer container;
    @BindView(R.id.btn_add_tag)
    Button btnAddTag;
    @BindView(R.id.et_text)
    EditText etText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etText.getText().toString();
                container.addTag(text);
            }
        });
    }
}
