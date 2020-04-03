package com.example.zoomwroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.database.MyDataBase;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    private ArrayList<DriveRequest> requests;
    private OnRequestClickListener onRequestClickListener;
    private final LayoutInflater mInflater;


    public RequestAdapter(Context context, ArrayList<DriveRequest> requests, OnRequestClickListener onRequestClickListener) {
        mInflater = LayoutInflater.from(context);
        this.requests = requests;
        this.onRequestClickListener = onRequestClickListener;
    }

    @Override
    public RequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.driverequest_layout_view, parent, false);
        return new MyViewHolder(itemView, onRequestClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (requests != null) {
            DriveRequest current = requests.get(position);
            Rider currentRequestRider = MyDataBase.getInstance().getRider(current.getRiderID());
            holder.riderNameTextView.setText(currentRequestRider.getName());
            holder.driveRequestStatusTextView.setText(DriveRequest.giveStatus(current.getStatus()));

            // changes the background of the record if it is currently active
            if (current.getStatus() == 3) {
                holder.requestView.setBackgroundColor(
                        ContextCompat.getColor(holder.requestView.getContext(), R.color.colorAccent));
            }
        } else {
            holder.driveRequestStatusTextView.setText("");
            holder.riderNameTextView.setText("No records.");
        }
    }

    /**
     * This method updates the request list
     * @param driveRequests
     */
    public void setRequests(ArrayList<DriveRequest> driveRequests) {
        requests = driveRequests;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (requests != null) {
            return requests.size();
        } else return 0;
    }

    /**
     * This methods finds the request by index
     * @param position
     * @return return the drive request in the position specified
     */
    public DriveRequest getRequestAtPosition(int position) {
        return requests.get(position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView riderNameTextView;
        private final TextView driveRequestStatusTextView;
        private final LinearLayout requestView;
        OnRequestClickListener onRequestClickListener;

        private MyViewHolder(View itemView, OnRequestClickListener listener) {
            super(itemView);
            riderNameTextView = itemView.findViewById(R.id.drive_request_user_name_textView);
            driveRequestStatusTextView = itemView.findViewById(R.id.drive_request_status_textView);
            requestView = itemView.findViewById(R.id.drive_request_linear_layout);

            this.onRequestClickListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRequestClickListener.onRequestClick(getAdapterPosition());
        }
    }



    public interface OnRequestClickListener {
        void onRequestClick(int position);
    }

}
