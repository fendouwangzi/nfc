package zzg.com.nfc.net.response;


/**
 * @author zhongzhigang
 * @Title: ${FILE_NAME}
 * @Description:
 * @package_name: sanocare.minute.clinic.net.request
 * @date 2017/6/23
 */
public class LoginResponse {
    private int AccountID;

    private String UserName;

    private String UserKey;

    private int UserInfoID;

    private String IssuedToken;

    private int Permissions;

    private String Remark;

    private boolean IsDeleted;

    private String Created;

    private String Author;

    private boolean Success;

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public int getUserInfoID() {
        return UserInfoID;
    }

    public void setUserInfoID(int userInfoID) {
        UserInfoID = userInfoID;
    }

    public String getIssuedToken() {
        return IssuedToken;
    }

    public void setIssuedToken(String issuedToken) {
        IssuedToken = issuedToken;
    }

    public int getPermissions() {
        return Permissions;
    }

    public void setPermissions(int permissions) {
        Permissions = permissions;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
