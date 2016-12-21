package com.wolfie.kidspend2.view.adapter;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.view.adapter.viewholder.ItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class GroupingRecyclerAdapter extends PlaceholderRecyclerAdapter<ItemViewHolder> {

    private OnItemInListClickedListener mOnItemInListClickedListener;
    private List<Spend> mSpends = new ArrayList<>();

    public GroupingRecyclerAdapter() {
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflateView(parent, R.layout.view_list_item);
        return new ItemViewHolder(view, mOnItemInListClickedListener);
    }

    private View inflateView(ViewGroup parent, @LayoutRes int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    /**
     * Update the viewHolder with the contents of the item at the given position in the data set.
     */
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Spend spend = mSpends.get(position);
        if (holder != null && spend != null) {
            holder.bind(spend);
        }
    }

    @Override
    public int getItemCount() {
        return mSpends.size();
    }

    /**
     * @return the Spend at the specified position in the adapter.
     */
    public Object getItemAt(int position) {
        return mSpends.get(position);
    }

    /**
     * Load the specified SpendGroup into the adapter.  Only a single list is shown.
     * The Spends in this list may be aggregated, which means they cannot be clicked on
     * and updated to the database (and have a -1 id to indicate this).
     * @param spends List of spends to display.
     */
    public void setGroups(List<Spend> spends) {
        mSpends.clear();
        mSpends.addAll(spends);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mSpends.clear();
        notifyDataSetChanged();
    }

    public void setOnItemInListClickerListener(OnItemInListClickedListener listener) {
        mOnItemInListClickedListener = listener;
    }

    public interface OnItemInListClickedListener {
        void onListItemClick(Spend selectedSpend);
    }

}
