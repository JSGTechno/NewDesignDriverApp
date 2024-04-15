package com.example.fleetech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.fleetech.R;
import com.example.fleetech.retrofit.response.FuelDetail;
import com.example.fleetech.retrofit.response.JMyPayment;

import java.util.List;

public class MyExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    List<JMyPayment> parentObjects;

    public MyExpandableAdapter(Context context, List<JMyPayment> parentObjects)
    {
        this.context = context;
        this.parentObjects = parentObjects;
    }
    @Override
    public int getGroupCount() {
        return parentObjects.size();
    }

     //Add 2 to childcount. The first row and the last row are used as header and footer to childview
    @Override
    public int getChildrenCount(int i) {
        return parentObjects.get(i).getFuelDetail().size() ;
    }

    @Override
    public JMyPayment getGroup(int i) {
        return parentObjects.get(i);
    }

    @Override
    public FuelDetail getChild(int i, int i2) {
        return parentObjects.get(i).getFuelDetail().get(i2);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup)
    {
        JMyPayment headerInfo = parentObjects.get(i);
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
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        JMyPayment currentParent = getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //the first row is used as header
//        if(childPosition ==0)
//        {
//            view = inflater.inflate(R.layout.child_row, null);
//        }

        //Here is the ListView of the ChildView
//        if(childPosition>0 && childPosition<getChildrenCount(groupPosition)-1)
//        {
            FuelDetail currentChild = getChild(groupPosition,childPosition);
            view = inflater.inflate(R.layout.fuel_expanse_list,null);
            TextView sequence = view.findViewById(R.id.fuelFillTv);
            TextView OrderID = view.findViewById(R.id.odidTv);
            TextView FillDateTv = view.findViewById(R.id.FillDateTv);
            TextView FuelPumpTv = view.findViewById(R.id.FuelPumpTv);
            TextView sNoTv = view.findViewById(R.id.sNoTv);
            sequence.setText(currentChild.getFuelFill());
            OrderID.setText(currentChild.getOrderID());
            FillDateTv.setText(currentChild.getFillDate());
            FuelPumpTv.setText(currentChild.getFuelPump());
            sNoTv.setText(String.valueOf(childPosition+1));
     //   }
        //the last row is used as footer
//        if(childPosition == getChildrenCount(groupPosition)-1)
//        {
//            view = inflater.inflate(R.layout.child_row,null);
//            TextView txtFooter = (TextView)view.findViewById(R.id.txtFooter);
//            txtFooter.setText(currentParent.textToFooter);
//        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }
}