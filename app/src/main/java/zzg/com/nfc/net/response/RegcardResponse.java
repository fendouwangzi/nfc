package zzg.com.nfc.net.response;

/**
 * Created by Administrator on 2017/9/19.
 */

public class RegcardResponse {
    private boolean IsDeleted;

    private String Created;

    private boolean Success;

    private String Message;

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
