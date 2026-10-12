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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumUpOrDown;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.view.Money.MoneyIncrease.FragmentMoneyIncreasePopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyIncrease.FragmentMoneyIncreaseTable;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import android.widget.Toast;

public class MoneyIncreaseAdapter
        extends CustomMoneyAdapter<MoneyIncrease, MoneyIncreaseAdapter.ItemViewHolder,
        FragmentMoneyIncreasePopupNew>
{
    public MoneyIncreaseAdapter(Context context, ArrayList<MoneyIncrease> dataSet,
                                IFragmentViewCustom<MoneyIncrease> fragmentViewCustom)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_money_increase);
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
        MoneyIncrease _moneyIncrease = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = _moneyIncrease;

            _fragmentPopupNew = new FragmentMoneyIncreasePopupNew((AppCompatActivity) _context,
                    _moneyIncrease, EnumTypeAction.Update, _fragmentViewCustom, false,
                    _moneyIncrease.get_id(), _moneyIncrease.get_month(), _moneyIncrease.get_year(),
                    _moneyIncrease.get_value()
//                    , _moneyIncrease.getExistIncoming()
            );
            _fragmentPopupNew.PopupShow();

//            _fragmentPopup =
//                    new FragmentMoneyIncreasePopup(_context,
//                            _fragmentViewCustom, EnumTypeAction.Update);
//            _fragmentPopup.PopupShow(LayoutInflater.from(_context),
//                    _moneyIncrease.get_id(), _moneyIncrease.get_month(), _moneyIncrease.get_year(),
//                    _moneyIncrease.get_value(), _moneyIncrease.getExistIncoming(),false);
        });
    }

    @Override
    public boolean canActionRightImage()
    {
        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        return _currentItem.canDelete(_context);
    }
    @Override
    public boolean canActionLeftImage() { return true; }

    public boolean LeftButtonClick()
    {
        ((FragmentMoneyIncreaseTable) _fragmentViewCustom).GoToFragmentMoneyIncoming();
        return true;
    }

    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }

    public int AddItem(MoneyIncrease moneyIncrease)
    {
        Integer month = moneyIncrease.getI_month();
        Integer year = moneyIncrease.getI_year();

        int insertPosition = _dataSet.size();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if ((month < _dataSet.get(i).getI_month() && year.equals(_dataSet.get(i).getI_year()))
                || (year < _dataSet.get(i).getI_year()))
            {
                insertPosition = i;
                break;
            }
        }

        if (insertPosition > 0)
            moneyIncrease.set_upOrDown(_dataSet.get(insertPosition - 1).get_value());
        _dataSet.add(insertPosition, moneyIncrease);
        notifyItemInserted(insertPosition);

        if (insertPosition < _dataSet.size() - 1)
        {
            _dataSet.get(insertPosition + 1).set_upOrDown(_dataSet.get(insertPosition).get_value());
            _dataSet.get(insertPosition + 1).setPercent((100 *
                    (_dataSet.get(insertPosition + 1).get_value() - moneyIncrease.get_value()) /
                    (float) moneyIncrease.get_value()));
            notifyItemChanged(insertPosition + 1);
        }

        return insertPosition;
    }

    public int UpdateItem(MoneyIncrease moneyIncrease)
    {
        long id = moneyIncrease.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (id == _dataSet.get(i).get_id())
            {
                if (moneyIncrease.get_month().equals(_dataSet.get(i).get_month()) &&
                    moneyIncrease.get_year().equals(_dataSet.get(i).get_year()))
                {
                    _dataSet.set(i, moneyIncrease);
                    notifyItemChanged(i);
                    if (i < _dataSet.size() - 1)
                    {
                        _dataSet.get(i + 1).set_upOrDown(_dataSet.get(i).get_value());
                        _dataSet.get(i + 1).setPercent((100 *
                                (_dataSet.get(i + 1).get_value() - _dataSet.get(i).get_value()) /
                                (float) _dataSet.get(i).get_value()));
                        notifyItemChanged(i + 1);
                    }
                    return 0;
                }
                else
                {
                    if (i > 0 && i < _dataSet.size() - 1)
                    {
                        _dataSet.get(i + 1).set_upOrDown(_dataSet.get(i - 1).get_value());
                        _dataSet.get(i + 1).setPercent((100 *
                                (_dataSet.get(i + 1).get_value() - _dataSet.get(i - 1).get_value()) /
                                (float) _dataSet.get(i - 1).get_value()));
                        notifyItemChanged(i + 1);
                    }
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyIncrease);
                }
            }
        }
        return 0;
    }

    private boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (dbHelper.DeleteMoneyIncrease(_currentItem.get_id()))
            {
                return DeleteFromDataset(_currentItem);
            }
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyIncreaseNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {

            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyIncrease) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyIncrease moneyIncreaseDeleted)
    {
        int index = -1;

        for (int i = 0; i < _dataSet.size(); i++)
            if (moneyIncreaseDeleted.get_id() == _dataSet.get(i).get_id())
            {
                index = i;
                break;
            }

        if (index == -1)
            return false;

        if (index > 0 && index < _dataSet.size() - 1)
        {
            _dataSet.get(index + 1).set_upOrDown(_dataSet.get(index - 1).get_value());
            _dataSet.get(index + 1).setPercent((100 *
                    (_dataSet.get(index + 1).get_value() - _dataSet.get(index - 1).get_value()) /
                    (float) _dataSet.get(index - 1).get_value()));
            notifyItemChanged(index + 1);
        }

        _dataSet.remove(index);
        notifyItemRemoved(index);
        return true;
    }

    public void onItemMove(int fromPosition, int toPosition) { }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyIncrease>
    {
        private final TextView textViewDate;
        private final TextView textViewValue;
        private final TextView textViewSign;
        private final TextView textViewPercent;
        private final ImageView imageViewIncrease;
        private final MaterialCardView materialCardViewMain;
        private final LinearLayout linearLayoutMain;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewValue = itemView.findViewById(R.id.textViewValue);
            textViewSign = itemView.findViewById(R.id.textViewSign);
            textViewPercent = itemView.findViewById(R.id.textViewProcent);
            imageViewIncrease = itemView.findViewById(R.id.imageViewIncrease);
            materialCardViewMain = itemView.findViewById(R.id.materialCardViewMain);
            linearLayoutMain = itemView.findViewById(R.id.linearLayoutMain);
        }

        public Long getId() { return _object.get_id(); }
        private void setDate(String date) { textViewDate.setText(date); }
        private void setValue(int value)
        {
            textViewValue.setText(CustomStaticClass.getIntRubleSum(value, true));
        }
        private void setPercent(Float percent)
        {
            if (percent == null)
            {
                textViewSign.setText("");
                textViewPercent.setText("");
            }
            else
            {
                textViewSign.setText(percent > 0 ? "+" : "-");
                textViewPercent.setText(CustomStaticClass.getAbsFloatPercent(percent, true));
            }
        }

        private void setUpOrDown(EnumUpOrDown upOrDown)
        {
            if (upOrDown.equals(EnumUpOrDown.Up))
                imageViewIncrease.setImageDrawable(
                        AppCompatResources.getDrawable(imageViewIncrease.getContext(),
                                R.drawable.icons_top_right_150));
            else
                imageViewIncrease.setImageDrawable(
                        AppCompatResources.getDrawable(imageViewIncrease.getContext(),
                                R.drawable.icons_bottom_right_150));

        }

        private void setArchive(boolean archive)
        {
            if (archive)
                linearLayoutMain.setBackgroundResource(R.color.lightLightGrey);
            else
                linearLayoutMain.setBackgroundResource(0);
        }

        @Override
        public void setObject(MoneyIncrease moneyIncrease)
        {
            super.setObject(moneyIncrease);

            setDate(_object.get_month_year());
            setValue(_object.get_value());
            setPercent(_object.getPercent());
            setUpOrDown(_object.get_upOrDown());
            setArchive(_object.get_archive());
        }
    }
}
