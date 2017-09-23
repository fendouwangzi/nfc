package zzg.com.nfc.net.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/19.
 */

public class MessageSendRequest implements Serializable {
    private String messageid;

    public MessageSendRequest(String messageid) {
        this.messageid = messageid;
    }
}
