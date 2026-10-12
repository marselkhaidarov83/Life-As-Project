package com.MH83.LifeAsProject.custom;

import android.content.Context;


import java.util.Arrays;
import java.util.List;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory;

public class CustomSubscription
{
    public static RuStoreBillingClient GetBillingClientRustore(Context context)
    {
        return RuStoreBillingClientFactory.INSTANCE.create(
                        context,
                        "2063543840",
                        "commh83lifeasprojectscheme",
                        null,
                        null,
                        null,
                        true);
    }

    public static List<String> GetProductListRustore()
    {
        return Arrays.asList(
                "quarterly_subscription_001",
                "semiyearly_subscription_001",
                "yearly_subscription_001",
//                "test_subscription_001",
//                "test_subscription_002",
//                "test_subscription_003",
//                "test_subscription_004",
//                "test_subscription_005",
//                "test_subscription_006",
//                "test_subscription_007",
//                "test_subscription_008",
//                "test_subscription_009",
//                "test_subscription_010",
                "test_test_test35",
                "test_test_test36",
                "test_test_test37",
                "test_test_test38",
                "test_test_test39",
                "test_test_test41",
                "test_test_test42",
                "test_test_test43",
                "test_test_test44",
                "test_test_test45",
                "test_test_test46",
                "test_test_test47",
                "test_test_test48",
                "test_test_test49",
                "test_test_test50"
                );
    }

    public static List<String> GetProductListGoogle()
    {
        return Arrays.asList(
                "lip_subscription",
                "quarterly_subscription_001",
                "semiyearly_subscription_001",
                "yearly_subscription_001"
        );
    }

    public static List<String> GetProductListHuawei()
    {
        return Arrays.asList
                (
                "product_test"
                        , "test_002"
        );
    }
    // priceType: 0: consumable; 1: non-consumable; 2: subscription
    public static int GetPriceTypeHuawei() { return 0; }
}
