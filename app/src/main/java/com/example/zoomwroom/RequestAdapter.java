package com.example.zoomwroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.database.MyDataBase;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    private ArrayList<DriveRequest> requests;

    private final LayoutInflater mInflater;


    public RequestAdapter(Context context, ArrayList<DriveRequest> requests) {
        mInflater = LayoutInflater.from(context);
        this.requests = requests;
    }

    @Override
    public RequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.driverequest_layout_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (requests != null) {
            DriveRequest current = requests.get(position);
            Rider currentRequestRider = MyDataBase.getRider(current.getRiderID());
            holder.riderNameTextView.setText(currentRequestRider.getName());
            holder.driveRequestStatusTextView.setText(giveStatus(current.getStatus()));


            // to be implemented
            holder.viewDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


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

    public DriveRequest getRequestAtPosition(int position) {
        return requests.get(position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView riderNameTextView;
        private final TextView driveRequestStatusTextView;
        private final LinearLayout requestView;
        private final Button viewDetailsButton;

        private MyViewHolder(View itemView) {
            super(itemView);
            riderNameTextView = itemView.findViewById(R.id.drive_request_user_name_textView);
            driveRequestStatusTextView = itemView.findViewById(R.id.drive_request_status_textView);
            requestView = itemView.findViewById(R.id.drive_request_linear_layout);
            viewDetailsButton = itemView.findViewById(R.id.request_details_btn);
        }

    }

    private String giveStatus(int status) {
        String strStatus;

        switch (status) {
            case 1:
                strStatus = "Accepted (Awaiting Rider Confirmation)";
                break;
            case 2:
                strStatus = "Confirmed";
                break;
            case 3:
                strStatus = "Ongoing";
                break;
            case 4:
                strStatus = "Completed";
                break;
            case 5:
                strStatus = "Cancelled";
                break;
            case 6:
                strStatus = "Declined";
                break;
            case 7:
                strStatus = "Aborted";
                break;
            default:
                strStatus = "Unknown";
        }

        return strStatus;
    }
    //////


//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView textView;
//        public TextView texView2;
//        public MyViewHolder(TextView v) {
//            super(v);
//            textView = v;
//        }
//    }
//
//    public RequestAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }
//
//    @Override
//    public RequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.driverequest_layout_view, parent, false);
//
//
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//
//        holder.textView.setText(mDataset[position]);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataset.length;
//    }

}
