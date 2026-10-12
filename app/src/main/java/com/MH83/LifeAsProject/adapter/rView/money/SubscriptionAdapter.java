package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;

import java.util.ArrayList;

public class SubscriptionAdapter
        extends CustomMoneyAdapter<CBillingProduct, SubscriptionAdapter.ItemViewHolder,
        FragmentSubscriptionPopupNew>
        implements IAUDAdapter<CBillingProduct>
{
    ItemViewHolder _prevHolder;

    public SubscriptionAdapter(Context context, ArrayList<CBillingProduct> dataSet,
                               IFragmentViewCustom<CBillingProduct> fragmentCustom)
    {
        super(context, dataSet, fragmentCustom, R.layout.item_subscription);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(_layoutId, parent, false), _context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        super.onBindViewHolder(holder, position);
        holder.setSelected(false);
        CBillingProduct cBillingProduct = _dataSet.get(position);

        if (cBillingProduct.get_id().equals("semiyearly_subscription_001"))
        {
            _currentItem = cBillingProduct;
            holder.setSelected(true);
            _prevHolder = holder;
        }

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = cBillingProduct;
            if (_prevHolder != null)
                _prevHolder.setSelected(false);

            _prevHolder = holder;
            holder.setSelected(true);
        });
    }

    @Override
    public boolean canActionRightImage() { return false;}
    @Override
    public boolean canActionLeftImage() { return false; }

    public boolean LeftButtonClick() { return false; }

    public boolean RightButtonClick()
    {
        return false;
    }

    @Override
    public Long AddItem(CBillingProduct object) {
        return 0L;
    }

    @Override
    public Long UpdateItem(CBillingProduct object) { return 0L; }

    @Override
    public boolean DeleteFromDataset(CBillingProduct cBillingProduct) {
        return false;
    }

    public static class ItemViewHolder extends CustomItemViewHolder<CBillingProduct>
    {
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewPrice;
        private final LinearLayout linearLayoutSubscription;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            linearLayoutSubscription = itemView.findViewById(R.id.linearLayoutSubscription);
        }

        public void setName(String name) { textViewName.setText(name); }
        public void setDescription(String description) { textViewDescription.setText(description); }
        public void setPrice(String price) { textViewPrice.setText(price); }
        public void setSelected(boolean selected)
        {
            if (selected)
                linearLayoutSubscription.setBackgroundResource(R.color.lightGrey);
            else
                linearLayoutSubscription.setBackgroundResource(R.color.white);
        }

        @Override
        public void setObject(CBillingProduct object)
        {
            super.setObject(object);
            setName(_object.get_name());
            setDescription(_object.get_description());
            setPrice(_object.get_price());
        }
    }
}
