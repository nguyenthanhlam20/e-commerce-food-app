package vn.edu.fpt.fa24.Services.Callbacks;

public abstract class ResponseCallBack<T> {
    // This method accepts a parameter of type T
    public abstract void onSuccess(T response);

    // This method handles error messages
    public abstract void onFailure(String error);
}