package zzg.com.nfc.net.api;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import zzg.com.nfc.net.HttpResponse;
import zzg.com.nfc.net.base.BaseApiService;
import zzg.com.nfc.net.request.LoginRequest;
import zzg.com.nfc.net.request.MessageSendRequest;
import zzg.com.nfc.net.request.PayRequest;
import zzg.com.nfc.net.request.RegcardRequest;
import zzg.com.nfc.net.response.AllMessageResponse;
import zzg.com.nfc.net.response.LoginResponse;
import zzg.com.nfc.net.response.OrderDetailsResponse;
import zzg.com.nfc.net.response.PayResponse;
import zzg.com.nfc.net.response.RefreshTokenResponse;
import zzg.com.nfc.net.response.RegcardResponse;
import zzg.com.nfc.ui.base.BaseActivity;

/**
 * @author zhongzhigang
 * @Title: ${FILE_NAME}
 * @Description:
 * @package_name: sanocare.minute.clinic.net.api
 * @date 2017/6/23
 */
public class LoginService extends BaseApiService {

    private interface LoginServiceAPi {

        @POST("/api/auths/login")
        public Observable<HttpResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

        @POST("/api/auths/refreshtoken")
        public Observable<HttpResponse<RefreshTokenResponse>> refreshToken();

        @POST("/api/cards/regcard")
        public Observable<HttpResponse<RegcardResponse>> regCard(@Body RegcardRequest regcardRequest);



        @POST("/api/orders/orderpay")
        public Observable<HttpResponse<PayResponse>> pay(@Body PayRequest payRequest);

        @GET("/api/orders/getorders/{cardkey}")
        public Observable<HttpResponse<List<OrderDetailsResponse>>> getorders(@Path("cardkey") String cardkey);

        @GET("/api/orders/orders")
        public Observable<HttpResponse<List<OrderDetailsResponse>>> getAllOrders();

        @GET("/api/timers/message")
        public Observable<HttpResponse<List<AllMessageResponse>>> allMessage();

        @POST("/api/timers/flagmessage")
        public Observable<HttpResponse<RegcardResponse>> messageSend(@Body MessageSendRequest messageSendRequest);

    }

    private static LoginService INSTANCE;

    private LoginService(BaseActivity content) {
            super(content);
    }

    public static LoginService getLoginService(BaseActivity content) {
        if(INSTANCE == null){
            INSTANCE = new LoginService(content);
        }
        return INSTANCE;
    }


    public Observable<LoginResponse> login(LoginRequest loginRequest) {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .login(loginRequest)
                .compose(this.<LoginResponse>applySchedulers());
    }

    public Observable<RefreshTokenResponse> refreshToken() {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .refreshToken()
                .compose(this.<RefreshTokenResponse>applySchedulers());
    }

    public Observable<RegcardResponse> regCard(RegcardRequest regcardRequest) {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .regCard(regcardRequest)
                .compose(this.<RegcardResponse>applySchedulers());
    }

    public Observable<RegcardResponse> messageSend(MessageSendRequest messageSendRequest) {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .messageSend(messageSendRequest)
                .compose(this.<RegcardResponse>applySchedulers());
    }

    public Observable<PayResponse> pay(PayRequest payRequest) {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .pay(payRequest)
                .compose(this.<PayResponse>applySchedulers());
    }

    public Observable<List<OrderDetailsResponse>> getorders(String cardkey) {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .getorders(cardkey)
                .compose(this.<List<OrderDetailsResponse>>applySchedulers());
    }

    public Observable<List<OrderDetailsResponse>> getAllOrders( ) {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .getAllOrders()
                .compose(this.<List<OrderDetailsResponse>>applySchedulers());
    }

    public Observable<List<AllMessageResponse>> getAllMessage( ) {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .allMessage()
                .compose(this.<List<AllMessageResponse>>applySchedulers());
    }
}
