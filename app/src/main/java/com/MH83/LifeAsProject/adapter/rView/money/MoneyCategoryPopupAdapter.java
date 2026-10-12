package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.Custom.IconDataSource;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyCategoryPopup4PNLNew;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamPopupNew;

import java.util.ArrayList;
import java.util.Hashtable;

public class MoneyCategoryPopupAdapter
        extends CustomMoneyAdapter<MoneyCategory, MoneyCategoryPopupAdapter.ItemViewHolder,
        FragmentMoneyDreamPopupNew>
{
    private Hashtable<Long, Icon> _icons;

    public MoneyCategoryPopupAdapter(Context context,
                                     EnumTypeCategory typeCategory,
                                     ArrayList<MoneyCategory> dataSet,
                                     IFragmentViewCustom<MoneyCategory> fragmentViewCustom)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_graph_money_category);

        _icons = IconDataSource.GetHashtable(typeCategory);
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
        holder.SetIcons(_icons);
        super.onBindViewHolder(holder, position);

        holder.itemView.setOnClickListener(view ->
                        ((FragmentMoneyCategoryPopup4PNLNew)_fragmentViewCustom).
                        SelectCategoryId(_dataSet.get(position).get_id()));
    }

    public boolean LeftButtonClick() { return true; }

    public boolean RightButtonClick() { return false; }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyCategory>
    {
        private final TextView textViewName;
        private final TextView textViewSum;
        private final ImageView imageViewIcon;
        private Hashtable<Long, Icon> _icons;
        public void SetIcons(Hashtable<Long, Icon> icons)
        {
            _icons = icons;
        }
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSum = itemView.findViewById(R.id.textViewSum);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        }

        public void setName(String name) { textViewName.setText(name); }
//        public void setSum(EnumTypeCategory typeCategory, Double sum)
//        {
//            if (typeCategory == EnumTypeCategory.Wallet)
//                textViewSum.setText(CustomStaticClass.getRubleSum(sum));
//            else
//                textViewSum.setText("");
//        }
        public void setSumLong(EnumTypeCategory typeCategory, Long sumLong)
        {
            if (typeCategory == EnumTypeCategory.Wallet)
                textViewSum.setText(CustomStaticClass.getRubleSum(sumLong / 100d));
            else
                textViewSum.setText("");
        }
        public void setImage(EnumTypeCategory typeCategory, Long icon)
        {
            if (icon != 0)
                imageViewIcon.setImageResource(_icons.get(icon).get_icon_id());
            else
            {
                if (typeCategory.equals(EnumTypeCategory.Cost))
                    imageViewIcon.setImageResource(R.drawable.icons_circle_yellow_50);
                else
                    imageViewIcon.setImageResource(R.drawable.icons_circle_grey_50);
            }
        }

        @Override
        public void setObject(MoneyCategory moneyCategory)
        {
            super.setObject(moneyCategory);
            setName(moneyCategory.get_name());
//            setSum(moneyCategory.getTypeCategory(), moneyCategory.getSum());
            setSumLong(moneyCategory.getTypeCategory(), moneyCategory.get_sumLong());
            setImage(moneyCategory.getTypeCategory(), moneyCategory.get_icon());
        }
    }
}
