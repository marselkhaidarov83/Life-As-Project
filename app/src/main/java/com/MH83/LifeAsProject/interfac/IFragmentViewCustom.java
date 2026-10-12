package com.MH83.LifeAsProject.interfac;

import androidx.fragment.app.FragmentActivity;

public interface IFragmentViewCustom<TDataset>
{
    boolean refreshViewOnAdd(TDataset object);
    boolean refreshViewOnUpdate(TDataset object);
    boolean refreshViewOnDelete(TDataset object);
    boolean refreshViewOnSelect(TDataset object);
    FragmentActivity getActivity();
}


