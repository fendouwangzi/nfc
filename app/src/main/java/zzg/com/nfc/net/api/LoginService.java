package zzg.com.nfc.net.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import zzg.com.nfc.net.HttpResponse;
import zzg.com.nfc.net.base.BaseApiService;
import zzg.com.nfc.net.request.LoginRequest;
import zzg.com.nfc.net.request.RegcardRequest;
import zzg.com.nfc.net.response.LoginResponse;
import zzg.com.nfc.net.response.RefreshTokenResponse;
import zzg.com.nfc.net.response.RegcardResponse;

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

    }

    private static final LoginService INSTANCE = new LoginService();

    private LoginService() {

    }

    public static LoginService getLoginService() {
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
}
