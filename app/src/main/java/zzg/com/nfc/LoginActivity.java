package zzg.com.nfc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import zzg.com.nfc.common.MyApplication;
import zzg.com.nfc.net.api.LoginService;
import zzg.com.nfc.net.base.BaseApiService;
import zzg.com.nfc.net.base.BaseSubscriber;
import zzg.com.nfc.net.exception.APIException;
import zzg.com.nfc.net.request.LoginRequest;
import zzg.com.nfc.net.response.LoginResponse;
import zzg.com.nfc.ui.base.BaseActivity;
import zzg.com.nfc.util.RomUtil;
import zzg.com.nfc.weiget.InputDialog;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity  {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;

    @Override
    protected void setTitleBar() {
        titleBar.getTitle().setText("客户端");
//        titleBar.setTitleSubText(getSubText());
//        titleBar.getRight_button().setBackgroundResource(isWhite ? R.drawable.ic_menu : R.drawable.ic_menu_white);
        titleBar.getRight_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIp();
            }
        });
        titleBar.getLeft_button().setBackgroundResource(R.drawable.white);
    }

    private void setIp() {
        new InputDialog.Builder(this)
                .setTitle("设置服务器地址")
                .setInputDefaultText(BaseApiService.DEV_URL)
                .setInputMaxWords(200)
                .setInputHint("http://")
                .hidePassword(false)
                .setPositiveButton("确定", new InputDialog.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        if(!TextUtils.isEmpty(inputText))
                        BaseApiService.DEV_URL = inputText + "";
                    }
                })
                .setNegativeButton("取消", new InputDialog.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        // TODO
                    }
                })
                .setOnCancelListener(new InputDialog.OnCancelListener() {
                    @Override
                    public void onCancel(CharSequence inputText) {
                        // TODO
                    }
                })
                .interceptButtonAction(new InputDialog.ButtonActionIntercepter() { // 拦截按钮行为
                    @Override
                    public boolean onInterceptButtonAction(int whichButton, CharSequence inputText) {
                        if ("/sdcard/my".equals(inputText) && whichButton == DialogInterface.BUTTON_POSITIVE) {
                            // TODO 此文件夹已存在，在此做相应的提示处理
                            // 以及return true拦截此按钮默认行为
                            return true;
                        }
                        return false;
                    }
                })
                .show();
    }

    private String getSubText() {
        String text = "Android" + Build.VERSION.RELEASE;
        if (RomUtil.isMIUI()) {
            text += "-MIUI" + RomUtil.getMIUIVersion();
        } else if (RomUtil.isFlyme()) {
            text += "-Flyme" + RomUtil.getFlymeVersionCode();
        } else if (RomUtil.isEMUI()) {
            text += "-EMUI" + RomUtil.getEMUIVersion();
        }
        return text;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle var1) {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        titleBar.getLeft_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threeClickFinish();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        //直接调用短信接口发短信
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("15274962315", null, "test", null, null);

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;

        }

        if(!cancel){
            if (TextUtils.isEmpty(password)) {
                mPasswordView.setError(getString(R.string.error_field_required));
                focusView = mPasswordView;
                cancel = true;
            }
            // Check for a valid password, if the user entered one.
//            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//                mPasswordView.setError(getString(R.string.error_invalid_password));
//                focusView = mPasswordView;
//                cancel = true;
//            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            LoginService.getLoginService(this).login(new LoginRequest(email,password)).subscribe(new BaseSubscriber<LoginResponse>(this) {

                @Override
                public void onCompleted() {
                    super.onCompleted();
                }

                @Override
                protected void onError(APIException ex) {

                }

                @Override
                public void onNext(LoginResponse loginResponse) {
                    MyApplication.getInstance().userInfo = loginResponse;
                    dismissProgressDialog();
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this,RegcardActivity.class));
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"onBackPressed--------------");
        return;
    }

    private long[] mHits = new long[10]; // 数组长度代表点击次数

    private void threeClickFinish() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 2000)) {
            Intent home=new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
        }
    }

}

