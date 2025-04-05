// Generated by view binder compiler. Do not edit!
package com.projekt.dzienniczek.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.projekt.dzienniczek.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRegistrationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonLogin;

  @NonNull
  public final EditText editTextLogin;

  @NonNull
  public final EditText editTextPassword;

  @NonNull
  public final EditText editTextRepeatPassword;

  @NonNull
  public final TextView textLogin;

  @NonNull
  public final TextView textPassword;

  @NonNull
  public final TextView textRegistration;

  @NonNull
  public final TextView textRepeatPassword;

  private FragmentRegistrationBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonLogin, @NonNull EditText editTextLogin,
      @NonNull EditText editTextPassword, @NonNull EditText editTextRepeatPassword,
      @NonNull TextView textLogin, @NonNull TextView textPassword,
      @NonNull TextView textRegistration, @NonNull TextView textRepeatPassword) {
    this.rootView = rootView;
    this.buttonLogin = buttonLogin;
    this.editTextLogin = editTextLogin;
    this.editTextPassword = editTextPassword;
    this.editTextRepeatPassword = editTextRepeatPassword;
    this.textLogin = textLogin;
    this.textPassword = textPassword;
    this.textRegistration = textRegistration;
    this.textRepeatPassword = textRepeatPassword;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRegistrationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRegistrationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_registration, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRegistrationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonLogin;
      Button buttonLogin = ViewBindings.findChildViewById(rootView, id);
      if (buttonLogin == null) {
        break missingId;
      }

      id = R.id.editTextLogin;
      EditText editTextLogin = ViewBindings.findChildViewById(rootView, id);
      if (editTextLogin == null) {
        break missingId;
      }

      id = R.id.editTextPassword;
      EditText editTextPassword = ViewBindings.findChildViewById(rootView, id);
      if (editTextPassword == null) {
        break missingId;
      }

      id = R.id.editTextRepeatPassword;
      EditText editTextRepeatPassword = ViewBindings.findChildViewById(rootView, id);
      if (editTextRepeatPassword == null) {
        break missingId;
      }

      id = R.id.textLogin;
      TextView textLogin = ViewBindings.findChildViewById(rootView, id);
      if (textLogin == null) {
        break missingId;
      }

      id = R.id.textPassword;
      TextView textPassword = ViewBindings.findChildViewById(rootView, id);
      if (textPassword == null) {
        break missingId;
      }

      id = R.id.text_registration;
      TextView textRegistration = ViewBindings.findChildViewById(rootView, id);
      if (textRegistration == null) {
        break missingId;
      }

      id = R.id.textRepeatPassword;
      TextView textRepeatPassword = ViewBindings.findChildViewById(rootView, id);
      if (textRepeatPassword == null) {
        break missingId;
      }

      return new FragmentRegistrationBinding((ConstraintLayout) rootView, buttonLogin,
          editTextLogin, editTextPassword, editTextRepeatPassword, textLogin, textPassword,
          textRegistration, textRepeatPassword);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
