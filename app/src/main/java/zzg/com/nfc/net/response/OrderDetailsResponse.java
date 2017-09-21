package zzg.com.nfc.net.response;

/**
 * Created by Administrator on 2017/9/21.
 */

public class OrderDetailsResponse {
    private String OrderCode;

    private double OrderFee;

    private int UserInfoID;

    private int AccountID;

    private String UserName;

    private String UserTel;

    private int OrderQuantity;

    private String OrderAddress;

    private String Remark;

    private boolean IsDeleted;

    private String Created;

    private String Author;

    private boolean Success;

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public double getOrderFee() {
        return OrderFee;
    }

    public void setOrderFee(double orderFee) {
        OrderFee = orderFee;
    }

    public int getUserInfoID() {
        return UserInfoID;
    }

    public void setUserInfoID(int userInfoID) {
        UserInfoID = userInfoID;
    }

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

    public String getUserTel() {
        return UserTel;
    }

    public void setUserTel(String userTel) {
        UserTel = userTel;
    }

    public int getOrderQuantity() {
        return OrderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        OrderQuantity = orderQuantity;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
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
