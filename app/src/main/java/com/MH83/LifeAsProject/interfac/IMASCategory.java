package com.MH83.LifeAsProject.interfac;

import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

public interface IMASCategory
{
    Long getCategoryId();
    String getCategoryName();
    EnumTypeCategory getTypeCategory();
//    Double getSum();
    Long get_sumLong();
    Long get_icon();
}
