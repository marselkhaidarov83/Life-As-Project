package com.MH83.LifeAsProject.interfac.adapter;

public interface IAUDAdapter<TDataSet>
{
    Long AddItem(TDataSet object);
    Long UpdateItem(TDataSet object);
    boolean DeleteFromDataset(TDataSet dataSet);
}
