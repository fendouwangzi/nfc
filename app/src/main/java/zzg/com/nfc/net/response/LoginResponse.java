package zzg.com.nfc.net.response;


import zzg.com.nfc.entity.UserInfo;

/**
 * @author zhongzhigang
 * @Title: ${FILE_NAME}
 * @Description:
 * @package_name: sanocare.minute.clinic.net.request
 * @date 2017/6/23
 */
public class LoginResponse {
    private String sccToken;
    private UserInfo sccUser;

    public String getSccToken() {
        return sccToken;
    }

    public void setSccToken(String sccToken) {
        this.sccToken = sccToken;
    }

    public UserInfo getSccUser() {
        return sccUser;
    }

    public void setSccUser(UserInfo sccUser) {
        this.sccUser = sccUser;
    }
}
