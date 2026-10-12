package com.MH83.LifeAsProject.data.Custom;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Custom.Icon;
import java.util.ArrayList;
import java.util.Hashtable;

public class IconDataSource
{
    public static ArrayList<Icon> LoadData(EnumTypeCategory typeCategory)
    {
        ArrayList<Icon> data = new ArrayList<>();

        switch (typeCategory)
        {
            case Increase:
                data.add(new Icon(1L, R.drawable.icons_icon_idea_100));
                data.add(new Icon(2L, R.drawable.icons_icon_business_100));
                data.add(new Icon(3L, R.drawable.icons_business_framework_100));
                data.add(new Icon(4L, R.drawable.icons_crowdfunding_100));
                data.add(new Icon(5L, R.drawable.icons_icon_merchant_account_100));
                data.add(new Icon(6L, R.drawable.icons_icon_management_100));
                data.add(new Icon(7L, R.drawable.icons_icon_gift_100));
                data.add(new Icon(8L, R.drawable.icons_credit_purple_100));
                data.add(new Icon(9L, R.drawable.icons_dollar_bitcoin_exchange_100));
                data.add(new Icon(10L, R.drawable.icons_icon_refund_100));
                data.add(new Icon(11L, R.drawable.icons_tasks_project_purple));
                data.add(new Icon(12L, R.drawable.icons_icon_android_phone_100));
                data.add(new Icon(13L, R.drawable.icons_icon_telegram_100));
                data.add(new Icon(14L, R.drawable.icons_telegram_app_100));
                data.add(new Icon(15L, R.drawable.icons_icon_auto_100));
                data.add(new Icon(16L, R.drawable.icons_icon_downtown_100));
                break;
            case Wallet:
                data.add(new Icon(101L, R.drawable.icons_money_bag_ruble_100));
                data.add(new Icon(102L, R.drawable.icons_icon_bagofmoney_100));
                data.add(new Icon(103L, R.drawable.icons_icon_wallet_100));
                data.add(new Icon(104L, R.drawable.icons_cion_wallet2_100));
                data.add(new Icon(105L, R.drawable.icons_icon_magnetic_card_100));
                data.add(new Icon(106L, R.drawable.icons8_icon_mastercard_creditcard_100));
                data.add(new Icon(107L, R.drawable.icons_icon_bank_cards_100));
                data.add(new Icon(108L, R.drawable.icons_icon_mir_100));
                data.add(new Icon(109L, R.drawable.icons_icon_visa_100));
                data.add(new Icon(110L, R.drawable.icons8_icon_mastercard_100));
                data.add(new Icon(111L, R.drawable.icons_icon_merchant_account_100));
                data.add(new Icon(112L, R.drawable.icons_icon_bank_safe_100));
                data.add(new Icon(113L, R.drawable.icons_icon_cocktail_100));
                data.add(new Icon(114L, R.drawable.icons_woman_sunbathing_100));
                data.add(new Icon(115L, R.drawable.icons_icon_protect_100));
                data.add(new Icon(116L, R.drawable.icons_icon_money_box_100));
                data.add(new Icon(117L, R.drawable.icons_icon_downtown_100));
                data.add(new Icon(118L, R.drawable.icons_tooth_100));
                data.add(new Icon(119L, R.drawable.icons_icon_auto_100));
                data.add(new Icon(120L, R.drawable.icons_tasks_project_purple));
                data.add(new Icon(121L, R.drawable.icons_city_block));
                break;
            case Cost:
                data.add(new Icon(201L, R.drawable.icons_icon_wheat_100));
                data.add(new Icon(202L, R.drawable.icons_pizza_100));
                data.add(new Icon(203L, R.drawable.icons_icon_icecream_100));
                data.add(new Icon(204L, R.drawable.icons_icon_cocktail_100));
                data.add(new Icon(205L, R.drawable.icons_icon_animal_100));
                data.add(new Icon(206L, R.drawable.icons_woman_sunbathing_100));
                data.add(new Icon(207L, R.drawable.icons_icon_auto_100));
                data.add(new Icon(208L, R.drawable.icons_cion_public_transportation_100));
                data.add(new Icon(209L, R.drawable.icons_icon_weightlifting_100));
                data.add(new Icon(210L, R.drawable.icons_icon_protect_100));
                data.add(new Icon(211L, R.drawable.icons_teaching_100));
                data.add(new Icon(212L, R.drawable.icons_icon_classroom_100));
                data.add(new Icon(213L, R.drawable.icons_literature_100));
                data.add(new Icon(214L, R.drawable.icons_property_100));
                data.add(new Icon(215L, R.drawable.icons_icon_heart_with_pulse_100));
                data.add(new Icon(216L, R.drawable.icons_icon_medical_doctor_100));
                data.add(new Icon(217L, R.drawable.icons_icon_doctors_bag_100));
                data.add(new Icon(218L, R.drawable.icons_icon_pill_100));
                data.add(new Icon(219L, R.drawable.icons_tooth_100));
                data.add(new Icon(220L, R.drawable.icons_icon_downtown_100));
                data.add(new Icon(221L, R.drawable.icons_icon_architect_100));
                data.add(new Icon(222L, R.drawable.icons_icon_plumbing_100));
                data.add(new Icon(223L, R.drawable.icons_icon_gift_100));
                data.add(new Icon(224L, R.drawable.icons_credit_purple_100));
                data.add(new Icon(225L, R.drawable.icons_tasks_project_purple));
                data.add(new Icon(226L, R.drawable.icons_icon_featurephone_100));
                data.add(new Icon(227L, R.drawable.icons_icon_iphone_100));
                data.add(new Icon(228L, R.drawable.icons_icon_android_phone_100));
                data.add(new Icon(229L, R.drawable.icons_icon_boots_100));
                data.add(new Icon(230L, R.drawable.icons_icon_carservice));
                data.add(new Icon(231L, R.drawable.icons_icon_children));
                data.add(new Icon(232L, R.drawable.icons_icon_exhaust_pipe));
                data.add(new Icon(233L, R.drawable.icons_icons_family));
                data.add(new Icon(234L, R.drawable.icons_energy_100));
                data.add(new Icon(235L, R.drawable.icons_icon_gaspump));
                data.add(new Icon(236L, R.drawable.icons_graduation_cap));
                data.add(new Icon(237L, R.drawable.icons_haircut));
                data.add(new Icon(238L, R.drawable.icons_magazine));
                data.add(new Icon(239L, R.drawable.icons_maintenance));
                data.add(new Icon(240L, R.drawable.icons_man_combing_hair));
                data.add(new Icon(241L, R.drawable.icons_mortarboard));
                data.add(new Icon(242L, R.drawable.icons_paint_roller));
                data.add(new Icon(243L, R.drawable.icons_scanner));
                data.add(new Icon(244L, R.drawable.icons_shirt));
                data.add(new Icon(245L, R.drawable.icons_students));
                data.add(new Icon(246L, R.drawable.icons_tshirt));
                data.add(new Icon(247L, R.drawable.icons_tax));
                data.add(new Icon(248L, R.drawable.icons_website));
                data.add(new Icon(249L, R.drawable.icons_woman_combing_hair));
                data.add(new Icon(250L, R.drawable.icons_other));
                break;
            default:
        }

        return data;
    }

    public static Hashtable<Long, Icon> GetHashtable(EnumTypeCategory typeCategory)
    {
        Hashtable<Long, Icon> hashtable = new Hashtable<>();
        ArrayList<Icon> data = LoadData(typeCategory);

        for (Icon item: data)
            hashtable.put(item.get_id(), item);

        return hashtable;
    }
}
