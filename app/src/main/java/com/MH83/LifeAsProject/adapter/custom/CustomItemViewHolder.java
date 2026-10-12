package com.MH83.LifeAsProject.adapter.custom;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.adapter.interfac.LifesItemViewHolder;

public class CustomItemViewHolder<TDataSet> extends RecyclerView.ViewHolder
        implements LifesItemViewHolder<TDataSet>
{
    protected TDataSet _object;
    @Override
    public TDataSet getObject() { return _object; }
    protected final Context _context;
    @Override
    public void setObject(TDataSet object) { _object = object; }
    public CustomItemViewHolder(@NonNull View itemView, Context context)
    {
        super(itemView);
        _context = context;
    }
}
