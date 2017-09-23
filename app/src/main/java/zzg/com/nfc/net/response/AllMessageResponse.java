package zzg.com.nfc.net.response;

/**
 * Created by Administrator on 2017/9/23.
 */

public class AllMessageResponse {
    private String Tel;

    private String Manager;

    private String Content;

    private boolean IsDeleted;

    private String Created;

    private String Author;

    private boolean Success;

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getManager() {
        return Manager;
    }

    public void setManager(String manager) {
        Manager = manager;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
