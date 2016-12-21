package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hl_th.myapplication.R;

import java.util.ArrayList;

import entities.ItemNavigation;

public class ItemListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<ItemNavigation> arr;

    public ArrayList<ItemNavigation> getArr() {
        return arr;
    }

    public void setArr(ArrayList<ItemNavigation> arr) {
        this.arr = arr;
    }

    private int my_layout;
    private final int TYPE_ITEM = 0;


    public ItemListViewAdapter(Context mContext, int layout, ArrayList<ItemNavigation> arr) {
        super();
        this.mContext = mContext;
        this.arr = arr;
        this.my_layout = layout;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        int count = 0;
        if (arr != null) {
            count = arr.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {

        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        holder = new ViewHolder();

        if (convertView == null) {

            convertView = mInflater.inflate(my_layout,parent, false);
            holder.itemIcon = (ImageView) convertView.findViewById(R.id.itemIcon);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ItemNavigation itemNavigation = (ItemNavigation) arr.get(position);
        if (itemNavigation != null) {
            holder.itemIcon.setImageResource(itemNavigation.getItemIcon());
            holder.itemName.setText(itemNavigation.getItemName());


        }
        return convertView;

    }

    public class ViewHolder {
        public TextView itemName;
        public ImageView itemIcon;

    }

}
