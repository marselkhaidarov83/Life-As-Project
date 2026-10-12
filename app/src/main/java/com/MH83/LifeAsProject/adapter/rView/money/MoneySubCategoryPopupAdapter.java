package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.data.Custom.IconDataSource;
import com.MH83.LifeAsProject.interfac.ISetSelSubCategoryId;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamPopupNew;

import java.util.ArrayList;
import java.util.Hashtable;

public class MoneySubCategoryPopupAdapter
        extends CustomMoneyAdapter<MoneyCategory, MoneySubCategoryPopupAdapter.ItemViewHolder,
        FragmentMoneyDreamPopupNew>
{
    ItemViewHolder prevViewHolder;
    private Long _selMoneyCategory;
    private String _selMoneyCategoryName;
    private Long _selMoneyCategoryIcon;
    private final boolean _editable;
    private final Hashtable<Long, Icon> _icons;

    public MoneySubCategoryPopupAdapter(Context context,
                                        EnumTypeCategory typeCategory,
                                        ArrayList<MoneyCategory> dataSet,
                                        IFragmentViewCustom<MoneyCategory> fragmentViewCustom,
                                        Long selMoneyCategory,
                                        String selMoneyCategoryName,
                                        Long selMoneyCategoryIcon,
                                        boolean editable)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_graph_money_category);
        _selMoneyCategory = selMoneyCategory;
        _selMoneyCategoryName = selMoneyCategoryName;
        _selMoneyCategoryIcon = selMoneyCategoryIcon;
        _editable = editable;

        if (dataSet.size() == 1)
        {
            _selMoneyCategory = dataSet.get(0).getCategoryId();
            _selMoneyCategoryName = dataSet.get(0).getCategoryName();
            _selMoneyCategoryIcon = dataSet.get(0).get_icon();
        }

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
        holder.setIcons(_icons);
        super.onBindViewHolder(holder, position);
        if (_selMoneyCategory != null && _dataSet.get(position).get_id() == _selMoneyCategory)
        {
            holder.SetSelected(true);
            prevViewHolder = holder;
        }

        if (_editable)
            holder.itemView.setOnClickListener(view ->
            {
                ((ISetSelSubCategoryId) _fragmentViewCustom).SetSelSubCategoryId(
                        _dataSet.get(position).get_id(), _dataSet.get(position).get_name(),
                        _dataSet.get(position).get_icon());
                ((ISetSelSubCategoryId) _fragmentViewCustom).ClearFocus();

                if (prevViewHolder != null)
                    prevViewHolder.SetSelected(false);
                holder.SetSelected(true);

                prevViewHolder = holder;
                _selMoneyCategory = _dataSet.get(position).get_id();
                _selMoneyCategoryName = _dataSet.get(position).get_name();
                _selMoneyCategoryIcon = _dataSet.get(position).get_icon();
            });
    }

    public Long get_selMoneyCategoryId() { return _selMoneyCategory; }
    public String get_selMoneyCategoryName() { return _selMoneyCategoryName; }
    public Long get_selMoneyCategoryIcon() { return _selMoneyCategoryIcon; }

    public boolean LeftButtonClick() { return true; }

    public boolean RightButtonClick() { return false; }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyCategory>
    {
        private final TextView textViewName;
        private final TextView textViewSum;
        private final ImageView imageViewIcon;
        private final LinearLayout linearLayout;
        private Hashtable<Long, Icon> _icons = new Hashtable<>();
        public void setIcons(Hashtable<Long, Icon> icons) { _icons = icons; }
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSum = itemView.findViewById(R.id.textViewSum);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            linearLayout = itemView.findViewById(R.id.linear_layout_item);
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
                try
                {
                    imageViewIcon.setImageResource(_icons.get(icon).get_icon_id());
                }
                catch (Exception e) {}
            else
            {
                if (typeCategory.equals(EnumTypeCategory.Increase))
                    imageViewIcon.setImageResource(R.drawable.icons_circle_green_50);
                else
                    imageViewIcon.setImageResource(R.drawable.icons_circle_yellow_50);
            }
        }
        @SuppressLint("ResourceAsColor")
        public void SetSelected(boolean selected)
        {
            if (selected)
                linearLayout.setBackgroundResource(R.color.lightGrey);
            else
                linearLayout.setBackgroundResource(R.color.white);
        }

        @SuppressLint("ResourceAsColor")
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
