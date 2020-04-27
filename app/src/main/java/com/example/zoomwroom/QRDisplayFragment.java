package com.example.zoomwroom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.zoomwroom.Entities.QRCode;


public class QRDisplayFragment extends DialogFragment {

    private String message;

    public QRDisplayFragment(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.qr_display_fragment, null);
        ImageView qrImageView = view.findViewById(R.id.qr_display);
        Button completeButton = view.findViewById(R.id.qr_display_button);

        QRCode.setQRCodeToImageView(message, qrImageView);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();
        completeButton.setOnClickListener(v -> dialog.dismiss());

        return dialog;
    }

}
