package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hl_th.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

import entities.BaseItem;

/**
 * Created by HL_TH on 12/17/2016.
 */

public class CustomExpandListView extends BaseExpandableListAdapter {

    Context context;

    LayoutInflater layoutInflater;

    ArrayList<String> headerList;
    HashMap<String, ArrayList<BaseItem>> item_list;

    public CustomExpandListView(Context context, ArrayList<String> headerList, HashMap<String, ArrayList<BaseItem>> item_list) {
        this.context = context;
        this.headerList = headerList;
        this.item_list = item_list;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return headerList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return item_list.get(headerList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return headerList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.item_list.get(this.headerList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String name_group = (String) getGroup(i);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_expand_group, viewGroup,false);

            TextView textView = (TextView) view.findViewById(R.id.id_name_header_group);
            textView.setText(name_group);
        }
        return view;


    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder=null;
        BaseItem itemAtem = (BaseItem) getChild(i, i1);
        if (view == null) {
            viewHolder= new ViewHolder();
            view = layoutInflater.inflate(R.layout.layout_expand_item, viewGroup,false);
            viewHolder.textView = (TextView) view.findViewById(R.id.id_text_item_expand);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.id_icon_item_expand);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.textView.setText(" "+i1+"             "+itemAtem.getName());
        viewHolder.imageView.setBackgroundResource(itemAtem.getIcon());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
