package zzg.com.nfc.net;


public class HttpResponse<T > {

    private boolean ok;

    private String message;

    private boolean tokenexpired;

    private T data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isTokenexpired() {
        return tokenexpired;
    }

    public void setTokenexpired(boolean tokenexpired) {
        this.tokenexpired = tokenexpired;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
