package zzg.com.nfc.net.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/19.
 */

public class RegcardRequest implements Serializable {
    private String cardkey;

    public RegcardRequest(String cardkey) {
        this.cardkey = cardkey;
    }
}
