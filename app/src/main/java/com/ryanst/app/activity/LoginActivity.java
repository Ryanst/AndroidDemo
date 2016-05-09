package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ryanst.app.core.BaseActivity;

/**
 * Created by kevin on 16/4/27.
 */
public class LoginActivity extends BaseActivity {

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int USERNAME_LENGTH = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initTextInput();
    }

    private void initTextInput() {
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");

        final EditText userName = (EditText) findViewById(R.id.username);

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int usrLength = userName.getText().length();
                if (usrLength < USERNAME_LENGTH) {
                    usernameWrapper.setHintTextAppearance(R.style.textShorter);
                } else if (usrLength > USERNAME_LENGTH) {
                    usernameWrapper.setHintTextAppearance(R.style.textError);
                } else {
                    usernameWrapper.setHintTextAppearance(R.style.textOk);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final EditText password = (EditText) findViewById(R.id.password);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int pwdLength = password.getText().length();
                if (pwdLength < PASSWORD_MIN_LENGTH) {
                    passwordWrapper.setHintTextAppearance(R.style.textShorter);
                } else {
                    passwordWrapper.setHintTextAppearance(R.style.textOk);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
