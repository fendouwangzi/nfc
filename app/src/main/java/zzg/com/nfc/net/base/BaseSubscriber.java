package zzg.com.nfc.net.base;

import android.os.Handler;
import android.util.Log;

import rx.Subscriber;
import zzg.com.nfc.net.exception.APIException;
import zzg.com.nfc.net.exception.ResultStatusEnum;
import zzg.com.nfc.ui.base.BaseActivity;
import zzg.com.nfc.util.NetUtils;

/**
 * @author zhongzhigang
 * @Title: ${FILE_NAME}
 * @Description:
 * @package_name: sanocare.minute.clinic.net.base
 * @date 2017/7/18
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private BaseActivity context;

    public BaseSubscriber(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetUtils.hasNetwork(context)) {
            context.showMsg("网络不可用，请先确保网络畅通");
            onCompleted();
            return;
        }
        // 显示进度条
        context.showProgressDialog();
    }

    @Override
    public void onCompleted() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭等待进度条
                context.dismissProgressDialog();
            }
        }, 500);
    }

    @Override
    public void onError(Throwable e) {
        //        e.printStackTrace();
        if (e instanceof APIException) {
            onError((APIException) e);
        } else {
            Log.e("zgzg","eee----------="+e.toString());
//            context.showMsg("网络异常");
            onError(new APIException(ResultStatusEnum.NETWORK_CANNOT_LINK,ResultStatusEnum.NETWORK_CANNOT_LINK.description));
        }
        onCompleted();
    }

    /**
     * 错误回调
     */
    protected abstract void onError(APIException ex);

}
