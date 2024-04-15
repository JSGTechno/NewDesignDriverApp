package com.example.fleetech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fleetech.R;
import com.example.fleetech.retrofit.response.FuelDetail;
import com.example.fleetech.retrofit.response.JMyPayment;
import com.example.fleetech.retrofit.response.PmtDetail;

import java.util.ArrayList;

public class MyListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<JMyPayment> deptList;

    public MyListAdapter(Context context, ArrayList<JMyPayment> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public FuelDetail getChild(int i, int i2) {
        return deptList.get(i).getFuelDetail().get(i2);

    }

//    @Override
//    public FuelDetail getChild(int groupPosition, int childPosition) {
//        ArrayList<FuelDetail> productList =
//                (ArrayList<FuelDetail>) deptList.get(groupPosition).getFuelDetail();
//
//        return productList.get(childPosition);
//    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        FuelDetail fuelInfo = getChild(groupPosition, childPosition);
        if (!fuelInfo.getOrderID().isEmpty()) {


            if (childPosition == 0) {
                LayoutInflater infalInflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = infalInflater.inflate(R.layout.child_row, null);
            }

            if (childPosition > 0 && childPosition < getChildrenCount(groupPosition) - 1) {

                LayoutInflater infalInflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = infalInflater.inflate(R.layout.fuel_expanse_list, null);

                TextView sequence = view.findViewById(R.id.fuelFillTv);
                TextView OrderID = view.findViewById(R.id.odidTv);
                TextView FillDateTv = view.findViewById(R.id.FillDateTv);
                TextView FuelPumpTv = view.findViewById(R.id.FuelPumpTv);
                TextView sNoTv = view.findViewById(R.id.sNoTv);
                sequence.setText(fuelInfo.getFuelFill());
                OrderID.setText(fuelInfo.getOrderID());
                FillDateTv.setText(fuelInfo.getFillDate());
                FuelPumpTv.setText(fuelInfo.getFuelPump());
                sNoTv.setText(String.valueOf(childPosition));

            }
        } else {
            Toast.makeText(context, "No record found!!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

//    @Override
//    public int getChildrenCount(int groupPosition) {
//
//        ArrayList<FuelDetail> productList =
//                (ArrayList<FuelDetail>) deptList.get(groupPosition).getFuelDetail();
//        return productList.size() ;
//
//    }

    //    @Override
//    public int getChildrenCount(int i) {
//        return deptList.get(i).getFuelDetail().size() + 1;
//    }
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return this.deptList.get(groupPosition).getFuelDetail().size();
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
