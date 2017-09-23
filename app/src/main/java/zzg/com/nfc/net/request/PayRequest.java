package zzg.com.nfc.net.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/19.
 */

public class PayRequest implements Serializable {
    private String cardkey;
    private String ordercode;
    private String paypwd;

    public PayRequest(String cardkey, String ordercode, String paypwd) {
        this.cardkey = cardkey;
        this.ordercode = ordercode;
        this.paypwd = paypwd;
    }

    public String getCardkey() {
        return cardkey;
    }

    public void setCardkey(String cardkey) {
        this.cardkey = cardkey;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getPaypwd() {
        return paypwd;
    }

    public void setPaypwd(String paypwd) {
        this.paypwd = paypwd;
    }
}
