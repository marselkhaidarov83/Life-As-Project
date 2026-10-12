package com.MH83.LifeAsProject.model.Custom;

import android.content.Context;
import android.content.SharedPreferences;

import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;

import java.util.Hashtable;

public class TagSettings extends CustomMoneyObject
{
    //region Parameters
    private EnumTagTypeData _tagTypeData;
    public EnumTagTypeData get_tagTypeData() { return _tagTypeData; }
    public void set_tagTypeData(EnumTagTypeData tagTypeData) { _tagTypeData = tagTypeData; }

    private Boolean _showArchive;
    public Boolean get_showArchive() { return _showArchive; }
    public void set_showArchive(Boolean showArchive) { _showArchive = showArchive; }
    //endregion

    //region Constructors
    public TagSettings(EnumTagTypeData tagTypeData,
                       Boolean showArchive)
    {
        _tagTypeData = tagTypeData;
        _showArchive = showArchive;
    }

    public TagSettings Clone()
    {
        return new TagSettings(_tagTypeData, _showArchive);
    }
    //endregion

    //region CustomMoneyObject
    @Override
    public String getSValue() { return ""; }

    @Override
    public void set_id(Long id) {}

    @Override
    public boolean canDelete(Context context) { return false; }
    //endregion

    //region WorkWithDatabase
    public void AddToDatabase(DBHelper dbHelper, Context context) throws Exception { }

    public boolean UpdateInDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }

    public boolean DeleteFromDatabase(DBHelper dbHelper, Context context) throws Exception
    {
        return false;
    }
    //endregion

    //region SaveToSharedPreferences
    public static SharedPreferences.Editor SaveToSharedPreferences(
            SharedPreferences.Editor editor,
            Hashtable<EnumTagTypeData, TagSettings> tagSettingsHT)
    {
        int i = 0;
        if (tagSettingsHT != null)
            for (EnumTagTypeData enumTagTypeData : tagSettingsHT.keySet())
            {
                TagSettings tagSettings =
                        tagSettingsHT.get(enumTagTypeData);
                editor.putString("TagSettings." + i + ".EnumTagTypeData",
                        enumTagTypeData.toString());
                editor.putBoolean("TagSettings." + i + ".ShowArchive",
                        tagSettings.get_showArchive());
                i++;
            }
        editor.putInt("TagSettings.Count", i);

        return editor;
    }

    public static Hashtable<EnumTagTypeData, TagSettings> GetFromSharedPreferences(
            SharedPreferences settings)
    {
        Hashtable<EnumTagTypeData, TagSettings> ht = new Hashtable<>();

        int count = settings.getInt("TagSettings.Count", 0);
        for (int i = 0; i < count; i++)
        {
            EnumTagTypeData enumTagTypeData =
                    EnumTagTypeData.valueOf(
                    settings.getString("TagSettings." + i + ".EnumTagTypeData",
                            EnumTagTypeData.PNL.name()));

            ht.put(enumTagTypeData, new TagSettings(enumTagTypeData,
                    settings.getBoolean("TagSettings." + i + ".ShowArchive", true)));
        }

        return ht;
    }
    //endregion
}
