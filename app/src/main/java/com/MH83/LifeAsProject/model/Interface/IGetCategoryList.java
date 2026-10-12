package com.MH83.LifeAsProject.model.Interface;

import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;

import java.util.ArrayList;

public interface IGetCategoryList
{
    EnumTypeCategory get_typeCategory();
    ArrayList<EnumCategoryType> get_categoryList();
}
