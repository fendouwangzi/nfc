package zzg.com.nfc.net.response;

/**
 * Created by Administrator on 2017/9/19.
 */

public class RefreshTokenResponse {
    private String IssuedToken;

    private int AccountID;

    private String UserKey;

    private String ExpiryDate;

    private boolean Expired;

    private String Remark;

    private boolean IsDeleted;

    private String Created;

    private String Author;

    private boolean Success;

    public String getIssuedToken() {
        return IssuedToken;
    }

    public void setIssuedToken(String issuedToken) {
        IssuedToken = issuedToken;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public boolean isExpired() {
        return Expired;
    }

    public void setExpired(boolean expired) {
        Expired = expired;
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
