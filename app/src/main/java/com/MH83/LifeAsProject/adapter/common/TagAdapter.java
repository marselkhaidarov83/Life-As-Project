package com.MH83.LifeAsProject.adapter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.model.Custom.TagSettings;
import com.MH83.LifeAsProject.view.Custom.Tag.FragmentTagPopupNew;

import java.util.ArrayList;
import java.util.Objects;

public class TagAdapter
        extends CustomMoneyAdapter<Tag, TagAdapter.ItemViewHolder,
        FragmentTagPopupNew>
{
    private final TagSettings tagSettings;
    private final GlobalClass gVar;

    public TagAdapter(Context context, ArrayList<Tag> dataSet,
                      IFragmentViewCustom<Tag> fragmentViewCustom)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_tag);

        gVar = (GlobalClass) context.getApplicationContext();
        tagSettings = gVar.get_TagSettings();
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
        Tag tag = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = tag;

            _fragmentPopupNew = new FragmentTagPopupNew((AppCompatActivity) _context,
                    tag, EnumTypeAction.Update, _fragmentViewCustom);
            _fragmentPopupNew.PopupShow();
        });

        holder.imageViewArchive.setOnClickListener(view ->
        {
            Tag tagClone = tag.CloneFull();
            tagClone.inverse_archive();
            try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
            {
                if (tagClone.UpdateInDatabase(dbHelper, _context))
                    UpdateItem(tagClone);
                else
                    Toast.makeText(_context, _context.getString(R.string.errorUpdateTag), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(_context, _context.getString(R.string.errorUpdateTag) + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean canActionRightImage()
    {
        return _currentItem.canDelete(_context);
    }
    @Override
    public boolean canActionLeftImage() { return false; }

    public boolean LeftButtonClick() { return false; }

    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }

    public int AddItem(Tag tag)
    {
        if (!gVar.get_hasUnpaidSubscription())
            if (!tagSettings.get_showArchive() && tag.get_archive())
                return 0;

        int insertPosition = _dataSet.size();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (tag.get_name().compareTo(_dataSet.get(i).get_name()) < 0)
            {
                insertPosition = i;
                break;
            }
        }

        _dataSet.add(insertPosition, tag);
        notifyItemInserted(insertPosition);

        return insertPosition;
    }

    public int UpdateItem(Tag tag)
    {
        if (!gVar.get_hasUnpaidSubscription())
            if (!tagSettings.get_showArchive() && tag.get_archive())
                DeleteFromDataset(tag);

        long id = tag.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (id == _dataSet.get(i).get_id())
            {
                if (tag.get_name().equals(_dataSet.get(i).get_name()))
                {
                    _dataSet.set(i, tag);
                    notifyItemChanged(i);
                    return i;
                }
                else
                {
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(tag);
                }
            }
        }
        return 0;
    }

    private boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (dbHelper.DeleteTag(_currentItem.get_id()))
            {
                return DeleteFromDataset(_currentItem);
            }
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedTagNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {

            Toast.makeText(_context, _context.getString(R.string.errorDeleteTag) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(Tag tag)
    {
        int index = -1;

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(tag.get_id(), _dataSet.get(i).get_id()))
            {
                index = i;
                break;
            }

        if (index == -1)
            return false;

        _dataSet.remove(index);
        notifyItemRemoved(index);
        return true;
    }

    public void onItemMove(int fromPosition, int toPosition) { }

    public static class ItemViewHolder extends CustomItemViewHolder<Tag>
    {
        private final TextView textViewName;
        private final TextView textViewComment;
        public final ImageView imageViewArchive;
        private final LinearLayout linearLayoutItem;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            imageViewArchive = itemView.findViewById(R.id.imageViewArchive);
            linearLayoutItem = itemView.findViewById(R.id.linearLayoutItem);
        }

        public Long getId() { return _object.get_id(); }
        private void setName(String name)
        {
            textViewName.setText(name);
        }
        private void setComment(String comment)
        {
            textViewComment.setText(comment);
        }
        private void setArchive(boolean archive)
        {
            if (archive)
                imageViewArchive.setImageResource(R.drawable.icon_archive);
            else
                imageViewArchive.setImageResource(R.drawable.icons_nonarchive);

            linearLayoutItem.setBackgroundResource(archive ? R.color.lightLightGrey : 0);
        }

        @Override
        public void setObject(Tag tag)
        {
            super.setObject(tag);

            setName(_object.get_name());
            setComment(_object.get_comment());
            setArchive(_object.get_archive());
        }
    }
}
