package zzg.com.nfc.net.api;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import sanocare.minute.clinic.net.HttpResponse;
import sanocare.minute.clinic.net.base.BaseApiService;
import sanocare.minute.clinic.net.request.LoginRequest;
import sanocare.minute.clinic.net.response.LoginResponse;

/**
 * @author zhongzhigang
 * @Title: ${FILE_NAME}
 * @Description:
 * @package_name: sanocare.minute.clinic.net.api
 * @date 2017/6/23
 */
public class LoginService extends BaseApiService {

    private interface LoginServiceAPi {

        @POST("/operation/user/login")
        public Observable<HttpResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

        @GET("/operation/user/logout")
        public Observable<HttpResponse<Boolean>> logout();

    }

    private static final LoginService INSTANCE = new LoginService();

    private LoginService() {

    }

    public static LoginService getLoginService() {
        return INSTANCE;
    }

    /**
     * param count
     * loginRequestram password
     *
     * @return
     */
    public Observable<LoginResponse> login(LoginRequest loginRequest) {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .login(loginRequest)
                .compose(this.<LoginResponse>applySchedulers());
    }

    /**
     * param count
     loginRequestram password
     * @return
     */
    public Observable<Boolean> logout() {
        return createRetrofit(LoginService.LoginServiceAPi.class)
                .logout()
                .compose(this.<Boolean>applySchedulers());
    }
}
