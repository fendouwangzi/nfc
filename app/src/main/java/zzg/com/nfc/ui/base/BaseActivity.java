package zzg.com.nfc.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.device.DeviceManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zzg.com.nfc.R;
import zzg.com.nfc.net.api.LoginService;
import zzg.com.nfc.net.base.BaseSubscriber;
import zzg.com.nfc.net.exception.APIException;
import zzg.com.nfc.net.response.RefreshTokenResponse;
import zzg.com.nfc.util.AppUtil;
import zzg.com.nfc.util.ToastUtils;
import zzg.com.nfc.weiget.CustomTitleBar;

/**
 * Created: AriesHoo on 2017/7/3 16:04
 * Function: title 基类
 * Desc:
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    protected CustomTitleBar titleBar;
    protected Activity mContext;
    protected boolean mIsFirstShow = true;
    private Unbinder mUnBinder;
    protected int type = 0;
    protected boolean isWhite =true;

    protected abstract void setTitleBar();

    protected boolean isShowLine() {
        return true;
    }

    @LayoutRes
    protected abstract int getLayout();

    protected void loadData() {
    }

    protected void beforeSetView() {
    }

    protected void beforeInitView() {
    }

    protected abstract void initView(Bundle var1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//
        super.onCreate(savedInstanceState);

        new DeviceManager().enableHomeKey(false);
        this.mContext = this;
        this.beforeSetView();
        this.setContentView(this.getLayout());
        mUnBinder = ButterKnife.bind(this);
        initTitle();
        this.beforeInitView();
        this.initView(savedInstanceState);
    }

    protected void initTitle() {
        titleBar = (CustomTitleBar) findViewById(R.id.titleBar);
        if (titleBar == null) {
            return;
        }
        setTitleBar();
    }

    public void startActivity(Activity mContext, Class<? extends Activity> activity, Bundle bundle) {
        AppUtil.startActivity(mContext, activity, bundle);
    }

    public void startActivity(Class<? extends Activity> activity, Bundle bundle) {
        startActivity(mContext, activity, bundle);
    }

    public void startActivity(Class<? extends Activity> activity) {
        startActivity(activity, null);
    }

    public View getRootView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        new DeviceManager().enableHomeKey(true);
    }

    protected void onResume() {
        if (this.mIsFirstShow) {
            this.mIsFirstShow = false;
            this.loadData();
        }

        super.onResume();
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.common_dialog);
            mProgressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.progress));
            mProgressDialog.setMessage("请稍后。。。");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    public void setProgressDialogMsg(String message) {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(message);
        }else {
            mProgressDialog = new ProgressDialog(this, R.style.common_dialog);
            mProgressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.progress));
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void dismissProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideSoftKeyBoard(EditText editText) {
        // 先隐藏键盘
        try {
            ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMsg(String msg) {
        ToastUtils.showShort(this, msg);
    }


    public void refreshToken(){
        LoginService.getLoginService(this).refreshToken().subscribe(new BaseSubscriber<RefreshTokenResponse>(this) {
            @Override
            protected void onError(APIException ex) {

            }

            @Override
            public void onNext(RefreshTokenResponse refreshTokenResponse) {

            }
        });
    }

}
