package com.example.zoomwroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoomwroom.Entities.QRBucks;

import java.util.ArrayList;
import java.util.Locale;

public class QRBucksAdapter extends RecyclerView.Adapter<QRBucksAdapter.QRBucksViewHolder> {
    private ArrayList<QRBucks> qrBucks;
    private OnQRBucksClickListener onQRBucksClickListener;
    private final LayoutInflater mInflater;

    public QRBucksAdapter(Context context, ArrayList<QRBucks> qrBucks, OnQRBucksClickListener onQRBucksClickListener) {
        mInflater = LayoutInflater.from(context);
        this.qrBucks = qrBucks;
        this.onQRBucksClickListener = onQRBucksClickListener;

    }

    @Override
    public QRBucksAdapter.QRBucksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.qrbucks_layout_view, parent, false);
        return new QRBucksViewHolder(itemView, onQRBucksClickListener);
    }

    @Override
    public void onBindViewHolder(QRBucksViewHolder holder, int position) {
        if (qrBucks != null) {
            QRBucks currentBuck = qrBucks.get(position);
            holder.riderNameTextView.setText(currentBuck.getRiderName());
            holder.offeredFareTextView.setText(String.format(Locale.CANADA,"$ %2.1f", currentBuck.getAmountOfMoneyOwed()));
            holder.statusTextView.setText(QRBucks.giveStatus(currentBuck.getStatus()));

            if (currentBuck.getStatus() == 1) {
                holder.qrBucksView.setBackgroundColor(
                        ContextCompat.getColor(holder.qrBucksView.getContext(), R.color.colorAccent));
            }

        } else {
            holder.statusTextView.setText("");
            holder.riderNameTextView.setText("");
            holder.offeredFareTextView.setText("");
        }
    }

    public void setQrBucks(ArrayList<QRBucks> qrBucks) {
        this.qrBucks = qrBucks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (qrBucks != null) {
            return qrBucks.size();
        } else return 0;
    }

    public QRBucks getQRBuckAtPosition(int position) {
        return qrBucks.get(position);
    }

    class QRBucksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView riderNameTextView;
        private final TextView offeredFareTextView;
        private final TextView statusTextView;
        private final LinearLayout qrBucksView;
        OnQRBucksClickListener onQRBucksClickListener;

        private QRBucksViewHolder(View itemView, OnQRBucksClickListener listener) {
            super(itemView);
            riderNameTextView = itemView.findViewById(R.id.qr_bucks_rider_full_name_textView);
            offeredFareTextView = itemView.findViewById(R.id.qr_bucks_fare_amount_textView);
            statusTextView = itemView.findViewById(R.id.qr_bucks_status_textView);
            qrBucksView = itemView.findViewById(R.id.qr_bucks_linear_layout);

            this.onQRBucksClickListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onQRBucksClickListener.onQrBucksClick(getAdapterPosition());
        }
    }

    public interface OnQRBucksClickListener {
        void onQrBucksClick(int position);
    }

}
