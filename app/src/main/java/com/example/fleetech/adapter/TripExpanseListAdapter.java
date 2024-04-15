package com.example.fleetech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.fleetech.R;
import com.example.fleetech.retrofit.response.JMyPayment;
import com.example.fleetech.retrofit.response.PmtDetail;

import java.util.ArrayList;

public class TripExpanseListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<JMyPayment> deptList;

    public TripExpanseListAdapter(Context context, ArrayList<JMyPayment> deptList) {
        this.context = context;
        this.deptList = deptList;
    }


    @Override
    public PmtDetail getChild(int i, int i2) {
        return deptList.get(i).getPmtDetail().get(i2);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {
        JMyPayment currentParent = (JMyPayment) getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //the first row is used as header
//        if (childPosition == 0) {
//            view = inflater.inflate(R.layout.child_row_trip_expanse, null);
//
//        } else {
//
//            //Here is the ListView of the ChildView
//            if (childPosition > 0 && childPosition < getChildrenCount(groupPosition) - 1) {
                PmtDetail currentChild = getChild(groupPosition, childPosition);
                view = inflater.inflate(R.layout.trip_expanses_list, null);
                TextView sNoTv = view.findViewById(R.id.sNoTv);
                TextView OrderID = view.findViewById(R.id.odidTv);
                TextView PmtType = view.findViewById(R.id.PmtType);
                TextView PMTDate = view.findViewById(R.id.PmtDate);
                TextView Amount_tv = view.findViewById(R.id.Amount_tv);
                TextView utr_no_tv = view.findViewById(R.id.utr_no_tv);

                PmtType.setText(currentChild.getPmtType());
                OrderID.setText(currentChild.getOrderID());
                Amount_tv.setText(currentChild.getAmount());
                PMTDate.setText(currentChild.getPmtDate());
                utr_no_tv.setText(currentChild.getUTRNo());
                sNoTv.setText(String.valueOf(childPosition+1));

    //        }
            //the last row is used as footer
//        if (childPosition == getChildrenCount(groupPosition) - 1) {
//            view = inflater.inflate(R.layout.child_row_trip_expanse, null);
//
//        }
            return view;
////        PmtDetail currentChild = (PmtDetail) getChild(groupPosition, childPosition);
//
//
//        if (childPosition == 0) {
//            LayoutInflater infalInflater = (LayoutInflater)
//                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = infalInflater.inflate(R.layout.child_row_trip_expanse, null);
//        } else {
////
//////Here is the ListView of the ChildView
//        if (childPosition > 0 && childPosition < getChildrenCount(groupPosition) - 1) {
//            PmtDetail currentChild = (PmtDetail) getChild(groupPosition, childPosition - 1);
//            LayoutInflater infalInflater = (LayoutInflater)
//                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = infalInflater.inflate(R.layout.trip_expanses_list, null);
//
//            TextView sNoTv = view.findViewById(R.id.sNoTv);
//            TextView OrderID = view.findViewById(R.id.odidTv);
//            TextView PmtType = view.findViewById(R.id.PmtType);
//            TextView PMTDate = view.findViewById(R.id.PmtDate);
//            TextView Amount_tv = view.findViewById(R.id.Amount_tv);
//            TextView utr_no_tv = view.findViewById(R.id.utr_no_tv);
//
//            PmtType.setText(currentChild.getPmtType());
//            OrderID.setText(currentChild.getOrderID());
//            Amount_tv.setText(currentChild.getAmount());
//            PMTDate.setText(currentChild.getPmtDate());
//            utr_no_tv.setText(currentChild.getUTRNo());
//            sNoTv.setText(String.valueOf(childPosition));
//
//        }
  //     }

    //    return view;
        }

//    @Override
//    public int getChildrenCount(int groupPosition) {
//
//        ArrayList<FuelDetail> productList =
//                (ArrayList<FuelDetail>) deptList.get(groupPosition).getFuelDetail()+2;
//        return productList.size();
//
//   }

    @Override
    public int getChildrenCount(int i) {
        return deptList.get(i).getPmtDetail().size() ;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        JMyPayment headerInfo = (JMyPayment) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.pmt_header_layout, null);
        }
        TextView heading = view.findViewById(R.id.odidTv);
        TextView srNo = view.findViewById(R.id.orderId);
        TextView Source = view.findViewById(R.id.source_tv);
        TextView destination_tv = view.findViewById(R.id.destination_tv);
        TextView dispatch_dt = view.findViewById(R.id.dispatch_dt);
        TextView dispatch_time = view.findViewById(R.id.dispatch_time);
        TextView deliver_dt = view.findViewById(R.id.deliver_dt);
        TextView delivertime = view.findViewById(R.id.delivertime);
        heading.setText(headerInfo.getOrderID().trim());
        Source.setText(headerInfo.getSource().trim());
        destination_tv.setText(headerInfo.getDestination().trim());
        dispatch_dt.setText(headerInfo.getDispatchDate().trim());
        dispatch_time.setText(headerInfo.getDispatchTime().trim());
        deliver_dt.setText(headerInfo.getDeliveryDate().trim());
        delivertime.setText(headerInfo.getDeliveryTime().trim());
//        srNo.setText(String.valueOf(groupPosition+1));


        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
