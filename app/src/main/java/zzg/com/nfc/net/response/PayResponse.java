package zzg.com.nfc.net.response;

/**
 * Created by Administrator on 2017/9/23.
 */

public class PayResponse {
    private boolean Success;
    private String Message;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
