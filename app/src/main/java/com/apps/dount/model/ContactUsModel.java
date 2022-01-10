package com.apps.dount.model;

import android.content.Context;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.apps.dount.BR;
import com.apps.dount.R;


public class ContactUsModel extends BaseObservable {
    private String name;
    private String email;
    private String title;
    private String message;
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_title = new ObservableField<>();
    public ObservableField<String> error_message = new ObservableField<>();


    public boolean isDataValid(Context context) {

        if (!name.isEmpty() &&
                !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !title.isEmpty() &&
                !message.isEmpty()

        ) {


            error_name.set(null);
            error_email.set(null);
            error_title.set(null);
            error_message.set(null);


            return true;

        } else {

            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_required));
            } else {
                error_name.set(null);

            }


            if (email.isEmpty()) {
                // Log.e("D;dlllld","d;ldldlldl");
                error_email.set(context.getString(R.string.field_required));
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error_email.set(context.getString(R.string.inv_email));
            } else {
                error_email.set(null);

            }

            if (title.isEmpty()) {
                error_title.set(context.getString(R.string.field_required));
            } else {
                error_title.set(null);

            }

            if (message.isEmpty()) {
                error_message.set(context.getString(R.string.field_required));
            } else {
                error_message.set(null);

            }

            return false;

        }

    }

    public ContactUsModel() {
        name = "";
        email = "";
        title = "";
        message = "";
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);

    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);

    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);

    }
}
