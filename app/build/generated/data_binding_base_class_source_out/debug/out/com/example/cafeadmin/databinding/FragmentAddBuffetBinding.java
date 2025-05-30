// Generated by view binder compiler. Do not edit!
package com.example.cafeadmin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cafeadmin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAddBuffetBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button addBuffetbtn;

  @NonNull
  public final ImageView addImage;

  @NonNull
  public final EditText buffet;

  @NonNull
  public final EditText buffetPrice;

  @NonNull
  public final Spinner categorySpinner;

  @NonNull
  public final EditText description;

  @NonNull
  public final TextView errorMessage;

  @NonNull
  public final Button selectImageBtn;

  @NonNull
  public final Toolbar toolbar;

  private FragmentAddBuffetBinding(@NonNull ScrollView rootView, @NonNull Button addBuffetbtn,
      @NonNull ImageView addImage, @NonNull EditText buffet, @NonNull EditText buffetPrice,
      @NonNull Spinner categorySpinner, @NonNull EditText description,
      @NonNull TextView errorMessage, @NonNull Button selectImageBtn, @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.addBuffetbtn = addBuffetbtn;
    this.addImage = addImage;
    this.buffet = buffet;
    this.buffetPrice = buffetPrice;
    this.categorySpinner = categorySpinner;
    this.description = description;
    this.errorMessage = errorMessage;
    this.selectImageBtn = selectImageBtn;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAddBuffetBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAddBuffetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_add_buffet, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAddBuffetBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addBuffetbtn;
      Button addBuffetbtn = ViewBindings.findChildViewById(rootView, id);
      if (addBuffetbtn == null) {
        break missingId;
      }

      id = R.id.addImage;
      ImageView addImage = ViewBindings.findChildViewById(rootView, id);
      if (addImage == null) {
        break missingId;
      }

      id = R.id.buffet;
      EditText buffet = ViewBindings.findChildViewById(rootView, id);
      if (buffet == null) {
        break missingId;
      }

      id = R.id.buffet_price;
      EditText buffetPrice = ViewBindings.findChildViewById(rootView, id);
      if (buffetPrice == null) {
        break missingId;
      }

      id = R.id.categorySpinner;
      Spinner categorySpinner = ViewBindings.findChildViewById(rootView, id);
      if (categorySpinner == null) {
        break missingId;
      }

      id = R.id.description;
      EditText description = ViewBindings.findChildViewById(rootView, id);
      if (description == null) {
        break missingId;
      }

      id = R.id.errorMessage;
      TextView errorMessage = ViewBindings.findChildViewById(rootView, id);
      if (errorMessage == null) {
        break missingId;
      }

      id = R.id.selectImageBtn;
      Button selectImageBtn = ViewBindings.findChildViewById(rootView, id);
      if (selectImageBtn == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new FragmentAddBuffetBinding((ScrollView) rootView, addBuffetbtn, addImage, buffet,
          buffetPrice, categorySpinner, description, errorMessage, selectImageBtn, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
