package zzg.com.nfc.net.request;

import java.io.Serializable;

/**
 * @author zhongzhigang
 * @Title: ${FILE_NAME}
 * @Description:
 * @package_name: sanocare.minute.clinic.net.request
 * @date 2017/6/23
 */
public class LoginRequest implements Serializable {
    private String userkey;
    private String userpwd;

    public LoginRequest(String userkey, String userpwd) {
        this.userkey = userkey;
        this.userpwd = userpwd;
    }
}
