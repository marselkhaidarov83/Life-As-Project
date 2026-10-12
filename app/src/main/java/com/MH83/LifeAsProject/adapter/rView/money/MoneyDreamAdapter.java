package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.model.Money.MoneyDream;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.model.Money.MoneyDreamSettings;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDream;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import android.widget.Toast;

public class MoneyDreamAdapter
        extends CustomMoneyAdapter<MoneyDream, MoneyDreamAdapter.ItemViewHolder,
        FragmentMoneyDreamPopupNew> implements IAUDAdapter<MoneyDream>
{
    private final int _heightDream;
    MoneyDreamSettings moneyDreamSettings;
    private final GlobalClass gVar;

    public MoneyDreamAdapter(Context context, ArrayList<MoneyDream> dataSet,
                                IFragmentViewCustom<MoneyDream> fragmentMoneyDreamCustom,
                                int heightDream)
    {
        super(context, dataSet, fragmentMoneyDreamCustom, R.layout.item_money_dream);
        gVar = (GlobalClass) context.getApplicationContext();
        _heightDream = heightDream;

        for (int i = 0; i < dataSet.size(); i++)
            dataSet.get(i).set_descPosition(i + 1);

        GlobalClass gVar = (GlobalClass) context.getApplicationContext();
        moneyDreamSettings = gVar.get_moneyDreamSettings();
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
        MoneyDream _moneyDream = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = _moneyDream;

            final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
            globalVariable.get_imagePath(true);
            _fragmentPopupNew = new FragmentMoneyDreamPopupNew((AppCompatActivity) _context, _currentItem,
                    EnumTypeAction.Update, _fragmentViewCustom);
            _fragmentPopupNew.PopupShow();
        });

        holder.materialCardViewStatus.setOnClickListener(view ->
        {
            _currentItem = _moneyDream;
            ((FragmentMoneyDream) _fragmentViewCustom).GoToFragmentMoneyDreamTarget();
        });
    }

    @Override
    public boolean canActionRightImage()
    {
        return _currentItem.canDelete(_context);
    }
    @Override
    public boolean canActionLeftImage() { return true; }

    public boolean LeftButtonClick()
    {
        ((FragmentMoneyDream) _fragmentViewCustom).GoToFragmentMoneyDreamTarget();
        return true;
    }

    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }

    public Long AddItem(MoneyDream moneyDream)
    {
        if (!gVar.get_hasUnpaidSubscription())
            if (!moneyDreamSettings.get_showArchive() && moneyDream.is_archive())
                return 0L;

        int insertPosition = _dataSet.size();

        _dataSet.add(insertPosition, moneyDream);
        notifyItemInserted(insertPosition);

        return (long) insertPosition;
    }

    public Long UpdateItem(MoneyDream moneyDream)
    {
        if (!gVar.get_hasUnpaidSubscription())
            if (!moneyDreamSettings.get_showArchive() && moneyDream.is_archive())
                DeleteFromDataset(moneyDream);

        Long id = moneyDream.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                _dataSet.set(i, moneyDream);
                notifyItemChanged(i);
                break;
            }

        _fragmentPopupNew = null;
        return 0L;
    }

    private boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (_currentItem.DeleteFromDatabase(dbHelper, _context))
                return DeleteFromDataset(_currentItem);
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyDreamNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyDream) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyDream moneyDream)
    {
        File internalStorage = _context.getDir(_context.getString(R.string.picDirectoryDream), Context.MODE_PRIVATE);
        File filePath = new File(internalStorage, moneyDream.get_id() + ".jpeg");
        File file = new File(filePath.getPath());
        if (file.exists())
            if (!file.delete())
                Log.i("FileDeleting", "FileDeletingError");

        int index = -1;
        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).get_id(), moneyDream.get_id()))
            {
                index = i;
                break;
            }

        if (index == -1)
            return false;

        _dataSet.remove(index);
        notifyItemRemoved(index);

        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            for (int k = index; k < _dataSet.size(); k++)
            {
                _dataSet.get(k).set_position(_dataSet.get(k).get_position() - 1);
                dbHelper.UpdateMoneyDreamPosition(_dataSet.get(k).get_id(),
                        _dataSet.get(k).get_position());
                notifyItemChanged(k);
            }
        }
        catch (Exception e) {}

        return true;
    }

    public void onItemMove(int fromPosition, int toPosition)
    {
        int start = toPosition;
        int end = fromPosition;
        if (fromPosition < toPosition)
        {
            start = fromPosition;
            end = toPosition;
        }

        if (fromPosition < toPosition)
            for (int i = start; i < end; i++)
            {
                int pos = _dataSet.get(i).get_position();
                _dataSet.get(i).set_position(_dataSet.get(i + 1).get_position());
                _dataSet.get(i).set_descPosition(i + 2);
                _dataSet.get(i + 1).set_position(pos);
                _dataSet.get(i + 1).set_descPosition(i + 1);
                Collections.swap(_dataSet, i, i + 1);
            }
        else
            for (int i = end; i > start; i--)
            {
                int pos = _dataSet.get(i).get_position();
                _dataSet.get(i).set_position(_dataSet.get(i - 1).get_position());
                _dataSet.get(i).set_descPosition(i);
                _dataSet.get(i - 1).set_position(pos);
                _dataSet.get(i - 1).set_descPosition(i + 1);
                Collections.swap(_dataSet, i, i - 1);
            }

        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            for (int i = start; i <= end; i++)
                if (!dbHelper.UpdateMoneyDreamPosition(_dataSet.get(i).get_id(),
                        _dataSet.get(i).get_position()))
                    Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyDreamPosition),
                            Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyDreamPosition) +
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        notifyItemMoved(fromPosition, toPosition);
        for (int i = start; i <= end; i++)
            notifyItemChanged(i);
    }

    public void setPopupImage(Uri uri)
    {
        if (_fragmentPopupNew != null)
            _fragmentPopupNew.set_Image(uri);
    }

    public class ItemViewHolder extends CustomItemViewHolder<MoneyDream>
    {
        private final TextView textViewName;
        private final ImageView imageViewImage;
        private final MaterialCardView materialCardViewImageView;
        private final MaterialCardView materialCardViewDream;
        private final MaterialCardView materialCardViewStatus;
        private final ImageView imageViewStatus;
        private final TextView textViewStatus;
        private final ConstraintLayout constraintLayoutMain;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewImage = itemView.findViewById(R.id.imageViewImage);
            materialCardViewImageView = itemView.findViewById(R.id.materialCardViewImageView);
            materialCardViewDream = itemView.findViewById(R.id.materialCardViewDream);
            materialCardViewStatus = itemView.findViewById(R.id.materialCardViewStatus);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            constraintLayoutMain = itemView.findViewById(R.id.constraintLayoutMain);
        }

        public Long getId() { return _object.get_id(); }

        private void setHeightDream()
        {
            materialCardViewDream.getLayoutParams().height = _heightDream;
        }
        public void setName(String name) { textViewName.setText(name); }

        @SuppressLint("SetTextI18n")
        public void setPosition(Integer position)
        {
            ConstraintLayout.LayoutParams layoutParams =
                    (ConstraintLayout.LayoutParams) materialCardViewImageView.getLayoutParams();
            ConstraintLayout.LayoutParams layoutParamsTV =
                    (ConstraintLayout.LayoutParams) textViewName.getLayoutParams();
            ConstraintLayout.LayoutParams layoutParamsStatus =
                    (ConstraintLayout.LayoutParams) materialCardViewStatus.getLayoutParams();

            if (Math.floorMod(position, 2) == 0)
            {
                textViewName.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                layoutParams.endToEnd = R.id.constraintLayoutMain;
                layoutParams.startToStart = -1;

                layoutParamsTV.startToStart = R.id.constraintLayoutMain;
                layoutParamsTV.endToStart = R.id.materialCardViewImageView;
                layoutParamsTV.endToEnd = -1;
                layoutParamsTV.startToEnd = -1;

                layoutParamsStatus.startToStart = R.id.constraintLayoutMain;
                layoutParamsStatus.endToEnd = -1;
            }
            else
            {
                textViewName.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                layoutParams.startToStart = R.id.constraintLayoutMain;
                layoutParams.endToEnd = -1;

                layoutParamsTV.endToEnd = R.id.constraintLayoutMain;
                layoutParamsTV.startToEnd = R.id.materialCardViewImageView;
                layoutParamsTV.startToStart = -1;
                layoutParamsTV.endToStart = -1;

                layoutParamsStatus.endToEnd = R.id.constraintLayoutMain;
                layoutParamsStatus.startToStart = -1;
            }

            materialCardViewStatus.setLayoutParams(layoutParamsStatus);
            materialCardViewImageView.setLayoutParams(layoutParams);
            textViewName.setLayoutParams(layoutParamsTV);
        }
        public void setImage(Long id)
        {
            File internalStorage = _context.getDir(
                    DBHelper.getDbName(_context) +
                            _context.getString(R.string.picDirectoryDream),
                    Context.MODE_PRIVATE);
            File filePath = new File(internalStorage, id + ".jpeg");

            try
            {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getPath());
                imageViewImage.setImageBitmap(bitmap);
            }
            catch (Exception ex)
            {
                Log.i("DATABASE", "Problem reading picture", ex);
            }
        }

        private void setTextStatus(String textStatus)
        {
            textViewStatus.setText(textStatus);
        }

        private void setStatus(int statusColor)
        {
            imageViewStatus.setImageResource(statusColor);
        }

        private void setArchive(boolean isArchive)
        {
            constraintLayoutMain.setBackgroundResource(isArchive ? R.color.lightLightGrey : 0);
        }

        @Override
        public void setObject(MoneyDream object)
        {
            super.setObject(object);
            setHeightDream();
            setName(_object.get_name());
            setPosition(_object.get_descPosition());
            setImage(getId());
            setTextStatus(_object.getTextStatus());
            setStatus(_object.getStatusColor());
            setArchive(_object.is_archive());
        }
    }
}
