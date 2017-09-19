package zzg.com.nfc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import zzg.com.nfc.net.api.LoginService;
import zzg.com.nfc.net.base.BaseSubscriber;
import zzg.com.nfc.net.exception.APIException;
import zzg.com.nfc.net.request.RegcardRequest;
import zzg.com.nfc.net.response.RegcardResponse;
import zzg.com.nfc.ui.base.BaseActivity;

public class RegcardActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView mText;
    private String cardNum = null;

    @Override
    protected void setTitleBar() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_regcard;
    }

    @Override
    protected void initView(Bundle var1) {

    }


    public void submit(View view){
        cardNum = "dddddd";
        LoginService.getLoginService().regCard(new RegcardRequest(cardNum)).subscribe(new BaseSubscriber<RegcardResponse>(this) {
            @Override
            protected void onError(APIException ex) {
                        showMsg(ex.getMessage());
            }

            @Override
            public void onNext(RegcardResponse regcardResponse) {

            }
        });
    }

}
