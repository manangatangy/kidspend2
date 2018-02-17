package com.wolfie.kidspend2.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.view.adapter.GroupingRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private ViewGroup mItemView;

    @BindView(R.id.item_layout)
    View mLayoutView;

    @BindView(R.id.item_text_amount)
    TextView mAmountTextView;

    @BindView(R.id.item_text_desc)
    TextView mDescTextView;

    private Spend mSpend;
    private GroupingRecyclerAdapter.OnItemInListClickedListener mListener;

    public ItemViewHolder(View itemView, GroupingRecyclerAdapter.OnItemInListClickedListener listener) {
        super(itemView);
        mItemView = (ViewGroup)itemView;
        mListener = listener;
        ButterKnife.bind(this, itemView);
    }

    /**
     * If {@link Spend#isNew()} returns true then the spend cannot be edited.
     * Instead the item represents a subtotal for that spend type.
     * In that case, the description is formatted from the spendType.
     */
    public void bind(Spend spend, boolean showDateAndType) {
        mSpend = spend;
        // Note that the content text may or may not actually be showing (depending on the expanded state).
        mAmountTextView.setText("$" + spend.getAmountAsString());
        if (spend.isNew()) {
            mDescTextView.setText("- " + spend.getSpendType());
        } else {
            String str = "- " + spend.getCreated();
            if (showDateAndType) {
                str = str + " - " + spend.getSpendType();
            }
            mDescTextView.setText(str);
        }
        mLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onListItemClick(mSpend);
                }
            }
        });
    }

}
