package zzg.com.nfc.net.base;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zzg.com.nfc.net.FastJsonConvertFactory;
import zzg.com.nfc.net.HttpResponse;
import zzg.com.nfc.net.OkHttpFactory;
import zzg.com.nfc.net.exception.APIException;
import zzg.com.nfc.net.exception.ResultStatusEnum;
import zzg.com.nfc.ui.base.BaseActivity;


/**
 * Created by zhongzhigang on 2017/6/6.
 */

public class BaseApiService {
    private static final String OFFICIAL_URL = "https://szyszz.com/";
    //    private static final String DEV_URL = "http://183.232.235.80:8080/";
    private static final String DEV_URL = "http://selfgo.jios.org:666/";
//    private static final String DEV_URL = BuildConfig.SN_DEBUG ?"http://118.178.189.178:8691":"http://192.168.150.93:8080";
//        private static final String DEV_URL = "http://192.168.150.176:8090";
    public static final String BASE_URL = DEV_URL + "";

    //升级相关
    public static final String APP_UPDATE_SERVER_URL = DEV_URL + "/application/checkUpgradeApp";
    public static final String APK_DOWNLOAD_URL = "url";
    public static final String APK_UPDATE_CONTENT = "updateContent";
    public static final String APK_VERSION_CODE = "versionCode";
    public static final String APK_VERSION = "version";
    public static final String APK_UPDATE_FLAG = "status";
    private BaseActivity context;

    private static Retrofit mRetrofit;

    public BaseApiService() {
    }

    public BaseApiService(BaseActivity context) {
        this.context = context;
    }

    public static <T> T createRetrofit(Class<T> claz) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(OkHttpFactory.getInstance().getOkHttpClient())
                    .addConverterFactory(FastJsonConvertFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return mRetrofit.create(claz);
    }

    protected <T> Observable.Transformer<HttpResponse<T>, T> applySchedulers() {
        return (Observable.Transformer<HttpResponse<T>, T>) transformer;
    }

    final Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponse((HttpResponse<Object>) response);
                        }
                    });
        }
    };

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final HttpResponse<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.isOk() && response.isTokenexpired()) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(response.getData());
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        APIException apiException = new APIException(ResultStatusEnum.valueOf(0), "失败");
                        subscriber.onError(apiException);
                        if(response.isOk() && !response.isTokenexpired()){
                            if(context != null){
//                                context.login();
                                context.showMsg("登录失效，请重新登录");
                            }
                        }else {
                            subscriber.onError(apiException);
                        }
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }

            }
        });
    }
}
