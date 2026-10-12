package com.MH83.LifeAsProject.adapter.interfac;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface LifesItemViewHolder<TDataSet>
{
    TDataSet getObject ();
    void setObject(TDataSet object);
}
