package com.MH83.LifeAsProject.view.Main.Cabinet;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.common.CabinetAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.CCabinetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentSubscriptionMain;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Custom.CCabinet;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
//import com.huawei.hmf.tasks.OnFailureListener;
//import com.huawei.hms.iap.Iap;
//import com.huawei.hms.iap.IapApiException;
//import com.huawei.hms.iap.entity.OwnedPurchasesReq;
//import com.huawei.hms.iap.entity.OwnedPurchasesResult;
//import com.huawei.hms.iap.entity.ProductInfo;
//import com.huawei.hms.iap.entity.ProductInfoReq;
//import com.huawei.hms.iap.entity.ProductInfoResult;
//import com.huawei.hms.iap.entity.PurchaseIntentReq;
//import com.huawei.hms.iap.entity.PurchaseIntentResult;
//import com.huawei.hms.support.api.client.Status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult;
import ru.rustore.sdk.billingclient.model.purchase.Purchase;
import ru.rustore.sdk.billingclient.model.purchase.PurchaseState;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.billingclient.usecase.PurchasesUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class FragmentCabinet extends Fragment implements IFragmentViewCustom, IFragmentSubscriptionMain
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_cabinet;
    //endregion

    //region Fragment parameters
    private ArrayList<CCabinet> _dataSet;
    private final IFragmentViewCustom _this;
    //endregion

    //region Fragment Components
    RecyclerView rView;
    TextView textViewSubscription;
    TextView textViewGotoSubscription;
    TextView textViewGotoSubscription2;
    ImageView imageViewSubscription;
    ImageView imageViewTelegram;
//    ImageView imageViewVK;
    //endregion

    //region Constructors
    public FragmentCabinet()
    {
        _this = this;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        LoadData();
        View view = inflater.inflate(_layoutId, container, false);

        TextView textView = view.findViewById(R.id.textViewPrivacyLink);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        FindComponents(view);
        SetListeners();

        final GlobalClass globalVariable = (GlobalClass) view.getContext().getApplicationContext();
        if (!GlobalClass.TestPaid())
            UpdateSubscriptionInfo();

        CabinetAdapter adapter = new CabinetAdapter(getContext(), _dataSet, this,
                globalVariable.get_cCabinet());
        rView.setAdapter(adapter);

        return view;
    }

    private void LoadData()
    {
        if (_dataSet == null)
            _dataSet = (ArrayList<CCabinet>) CCabinetDataSource.LoadData(getContext(), false);
    }

    private void FindComponents(View view)
    {
        rView = view.findViewById(R.id.RViewCabinet);
        textViewSubscription = view.findViewById(R.id.textViewSubscription);
        textViewGotoSubscription = view.findViewById(R.id.textViewGotoSubscription);
        textViewGotoSubscription2 = view.findViewById(R.id.textViewGotoSubscription2);
        imageViewSubscription = view.findViewById(R.id.imageViewSubscription);
        imageViewTelegram = view.findViewById(R.id.imageViewTelegram);
//        imageViewVK = view.findViewById(R.id.imageViewVK);
    }
    //endregion

    //region UpdateSubscription
    @SuppressLint("SetTextI18n")
    private void UpdatePaymentSubscriptionInfoFromGV(GlobalClass globalVariable)
    {
        textViewSubscription.setText(R.string.SubscriptionActive);
        textViewSubscription.setTextColor(getContext().getColor(android.R.color.secondary_text_light));

        textViewGotoSubscription.setTextColor(getContext().getColor(android.R.color.secondary_text_light));
        textViewGotoSubscription.setText("с " + CustomStaticClass.getDate4View(getContext(),
                        globalVariable.get_startDate(), true));

        textViewGotoSubscription2.setTextColor(getContext().getColor(android.R.color.secondary_text_light));
        textViewGotoSubscription2.setText("до " + CustomStaticClass.getDate4View(getContext(),
                globalVariable.get_endDate(), true));

        imageViewSubscription.setOnClickListener(null);
        textViewGotoSubscription.setOnClickListener(null);
        textViewGotoSubscription2.setOnClickListener(null);
    }

    @SuppressLint("SetTextI18n")
    private void UpdateTestSubscriptionInfoFromGV(GlobalClass globalVariable)
    {
        textViewSubscription.setText(R.string.TestSubscriptionActive);
        textViewSubscription.setTextColor(getContext().getColor(android.R.color.secondary_text_light));

        textViewGotoSubscription.setText("с " + CustomStaticClass.getDate4View(getContext(),
                globalVariable.get_firstOpenDate(), true));

        textViewGotoSubscription2.setText("до " + CustomStaticClass.getDate4View(getContext(),
                globalVariable.get_firstOpenDateTo(), true));
    }

    @SuppressLint("SetTextI18n")
    private void UpdateUnpaidSubscriptionInfoFromGV()
    {
        textViewSubscription.setText(R.string.UnpaidSubscriptionActive);
        textViewSubscription.setTextColor(getContext().getColor(android.R.color.secondary_text_light));
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void UpdateSubscriptionInfo()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (globalVariable.get_hasPaymentSubscription())
        {
            UpdatePaymentSubscriptionInfoFromGV(globalVariable);
            return;
        }
        else if (globalVariable.get_hasTestSubscription())
            UpdateTestSubscriptionInfoFromGV(globalVariable);
        else if (globalVariable.get_hasUnpaidSubscription())
            UpdateUnpaidSubscriptionInfoFromGV();

        if (GlobalClass.SubscrGoogle())
            UpdateSubscrInfoFromGoogle(globalVariable);
        else if (GlobalClass.SubscrRustore())
            UpdateSubscrInfoFromRustore(globalVariable);
//        else if (GlobalClass.SubscrHuawei())
//            UpdateSubscrInfoFromHuawei(globalVariable);
    }

    private Handler updateUIHandler = null;
    private final static int MESSAGE_UPDATE_TEXT_CHILD_THREAD =1;
    private void createUpdateUiHandler()
    {
        if(updateUIHandler == null)
        {
            updateUIHandler = new Handler()
            {
                @SuppressLint("HandlerLeak")
                @Override
                public void handleMessage(Message msg) {
                    // Means the message is sent from child thread.
                    if(msg.what == MESSAGE_UPDATE_TEXT_CHILD_THREAD)
                    {
                        // Update ui in main thread.
                        updateUI();
                    }
                }
            };
        }
    }
    @SuppressLint("SetTextI18n")
    private void updateUI()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        textViewSubscription.setText(R.string.SubscriptionActive);
        textViewSubscription.setTextColor(getContext().getColor(android.R.color.secondary_text_light));

        textViewGotoSubscription.setText("с " +
                CustomStaticClass.getDate4View(getContext(), globalVariable.get_startDate(), true));
        textViewGotoSubscription2.setTextColor(getContext().getColor(android.R.color.secondary_text_light));

        textViewGotoSubscription2.setText("до " +
                CustomStaticClass.getDate4View(getContext(), globalVariable.get_endDate(), true));

        textViewGotoSubscription.setTextColor(getContext().getColor(android.R.color.secondary_text_light));

        imageViewSubscription.setOnClickListener(null);
        textViewGotoSubscription.setOnClickListener(null);
        textViewGotoSubscription2.setOnClickListener(null);
    }

    private void UpdateSubscrInfoFromGoogle(GlobalClass globalVariable)
    {
        createUpdateUiHandler();

        BillingClient.Builder builder = BillingClient.newBuilder(getContext());
        builder.setListener((billingResult, list) -> {});
        builder.enablePendingPurchases();
        BillingClient billingClient = builder.build();

        billingClient.startConnection(new BillingClientStateListener()
        {
            //                skuList.add("android.test.purchased");
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult)
            {
                if (billingResult.getResponseCode() != OK)
                    return;

                QueryPurchasesParams.Builder builder = QueryPurchasesParams.newBuilder();
                builder.setProductType(BillingClient.SkuType.SUBS);
                QueryPurchasesParams params1 = builder.build();

                billingClient.queryPurchasesAsync(params1, (billingResult1, list) ->
                {
                    if (billingResult1.getResponseCode() != OK)
                        return;

                    for (com.android.billingclient.api.Purchase purchase: list)
                    {
                        //TODO: найти параметр
                        if (purchase.getPurchaseState() == 1)
                        {
                            globalVariable.set_purchaseId(purchase.getOrderId());
                            globalVariable.set_productId(purchase.getProducts().get(0));

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date(purchase.getPurchaseTime()));
                            globalVariable.set_startDate(calendar);
                            globalVariable.set_endDate(calendar);

                            List<String> ids = Collections.singletonList(purchase.getProducts().get(0));
                            List<QueryProductDetailsParams.Product> products = ids.stream().map( productId ->
                                    QueryProductDetailsParams.Product.newBuilder()
                                            .setProductId(productId)
                                            .setProductType(BillingClient.ProductType.SUBS)
                                            .build()
                            ).collect(Collectors.toList());

                            QueryProductDetailsParams.Builder builder2 = QueryProductDetailsParams.newBuilder();
                            builder2.setProductList(products);
                            QueryProductDetailsParams params2 = builder2.build();

                            billingClient.queryProductDetailsAsync(params2, (billingResult2, list2) ->
                            {
                                if (billingResult2.getResponseCode() != OK)
                                    return;

                                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

                                if (list2.size() == 1)
                                {
                                    ProductDetails productDetails2 = list2.get(0);

                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.set(Calendar.DATE, calendar.get(Calendar.DATE));
                                    calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                                    calendar1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

                                    String s = productDetails2.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0).getBillingPeriod();
                                    String s1 = s.substring(1, 2);
                                    String s2 = s.substring(2, 3);

                                    switch (s2)
                                    {
                                        case "Y":
                                            calendar1.add(Calendar.YEAR, Integer.parseInt(s1));
                                            break;

                                        case "M":
                                            calendar1.add(Calendar.MONTH, Integer.parseInt(s1));
                                            break;

                                        default:
                                            calendar1.add(Calendar.DATE, Integer.parseInt(s1));
                                    }

                                    globalVariable.set_endDate(calendar1);
                                    globalVariable.SaveSharedPreferences();

                                    // Build message object.
                                    Message message = new Message();
                                    // Set message type.
                                    message.what = MESSAGE_UPDATE_TEXT_CHILD_THREAD;
                                    // Send message to main thread Handler.
                                    updateUIHandler.sendMessage(message);
                                }
                            });

                            globalVariable.SaveSharedPreferences();
                            break;
                        }
                    }
                });
            }

            @Override
            public void onBillingServiceDisconnected()
            {
                Log.e("Billing: getting purchase info Google", "Serivce Disconnected");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void UpdateSubscrInfoFromRustore(GlobalClass globalVariable)
    {
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(getContext());

        PurchasesUseCase purchasesUseCase = billingClient.getPurchases();
        Task<List<Purchase>> tList = purchasesUseCase.getPurchases();

        tList.addOnSuccessListener(purchases ->
        {
            for (Purchase purchase: purchases)
            {
                if (CustomSubscription.GetProductListRustore().contains(purchase.getProductId()) &&
                        purchase.getPurchaseState() == PurchaseState.CONFIRMED &&
                        purchase.getProductType() == ProductType.SUBSCRIPTION)
                {
                    globalVariable.set_purchaseId(purchase.getPurchaseId());
                    globalVariable.set_productId(purchase.getProductId());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(purchase.getPurchaseTime());
                    globalVariable.set_startDate(calendar);
                    globalVariable.set_endDate(calendar);

                    textViewSubscription.setText(R.string.SubscriptionActive);
                    textViewSubscription.setTextColor(getContext().getColor(android.R.color.secondary_text_light));

                    textViewGotoSubscription.setText("с " +
                            CustomStaticClass.getDate4View(getContext(), calendar, true));
                    textViewGotoSubscription2.setTextColor(getContext().getColor(android.R.color.secondary_text_light));
                    textViewGotoSubscription.setTextColor(getContext().getColor(android.R.color.secondary_text_light));

                    imageViewSubscription.setOnClickListener(null);
                    textViewGotoSubscription.setOnClickListener(null);
                    textViewGotoSubscription2.setOnClickListener(null);

                    Task<List<Product>> ttask =
                            billingClient.getProducts().getProducts(Collections.singletonList(purchase.getProductId()));

                    ttask.addOnSuccessListener(products ->
                    {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.DATE, calendar.get(Calendar.DATE));
                        calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                        calendar1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

                        if (products.get(0).getSubscription() != null)
                        {
                            calendar1.add(Calendar.DATE, products.get(0).getSubscription().getSubscriptionPeriod().getDays());
                            calendar1.add(Calendar.MONTH, products.get(0).getSubscription().getSubscriptionPeriod().getMonths());
                            calendar1.add(Calendar.YEAR, products.get(0).getSubscription().getSubscriptionPeriod().getYears());
                        }

                        if (products.get(0).getSubscription().getFreeTrialPeriod() != null)
                        {
                            calendar1.add(Calendar.DATE, products.get(0).getSubscription().getFreeTrialPeriod().getDays());
                            calendar1.add(Calendar.MONTH, products.get(0).getSubscription().getFreeTrialPeriod().getMonths());
                            calendar1.add(Calendar.YEAR, products.get(0).getSubscription().getFreeTrialPeriod().getYears());
                        }

                        globalVariable.set_endDate(calendar1);

                        textViewGotoSubscription2.setText("до " +
                                CustomStaticClass.getDate4View(getContext(), calendar1, true));

                        globalVariable.SaveSharedPreferences();
                    });

                    globalVariable.SaveSharedPreferences();

                    return;
                }
            }

            globalVariable.ClearSubscription4Test();
        });

        tList.addOnFailureListener(throwable ->
        {
            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Billing: getting purchase info", throwable.getMessage());
        });
    }

//    @SuppressLint("SetTextI18n")
//    private void UpdateSubscrInfoFromHuawei(GlobalClass globalVariable)
//    {
//        //TODO: Переделать на Huawei
//        //TODO: получить информацию о подписках Huawei
//        // Construct an OwnedPurchasesReq object.
//        OwnedPurchasesReq req = new OwnedPurchasesReq();
//        req.setPriceType(CustomSubscription.GetPriceTypeHuawei());
//        final Activity activity = getActivity();
//        // Call the obtainOwnedPurchaseRecord API.
//        com.huawei.hmf.tasks.Task<OwnedPurchasesResult> task =
//                Iap.getIapClient(activity).obtainOwnedPurchaseRecord(req);
//        task
//                .addOnSuccessListener(result ->
//                {
//                    // Obtain the execution result.
//                })
//                .addOnFailureListener(new OnFailureListener()
//                {
//                    @Override
//                    public void onFailure(Exception e)
//                    {
//                        if (e instanceof IapApiException)
//                        {
//                            IapApiException apiException = (IapApiException) e;
//                            int returnCode = apiException.getStatusCode();
//                            Log.e("Billing: getting purchase info Huawei", e.getMessage() + ": " +
//                                    returnCode);
//                        }
//                        else
//                        {
//                            Log.e("Billing: getting purchase info Huawei", e.getMessage());
//                        }
//                    }
//                });
//    }
    //endregion

    //region SetListeners
    private void SetListeners()
    {
        imageViewSubscription.setOnClickListener(SubscriptionClickListener());
        textViewGotoSubscription.setOnClickListener(SubscriptionClickListener());
        textViewGotoSubscription2.setOnClickListener(SubscriptionClickListener());

        imageViewTelegram.setOnClickListener(view ->
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getContext().getString(R.string.TelegramChannelUrl)));
            startActivity(browserIntent);
        });

//        imageViewVK.setOnClickListener(view ->
//        {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(getContext().getString(R.string.VkontakteChannelUrl)));
//            startActivity(browserIntent);
//        });
    }

    private View.OnClickListener SubscriptionClickListener()
    {
        return view ->
        {
            if (GlobalClass.SubscrGoogle())
                try
                {
                    SubscriptGoogle();
                }
                catch (Exception e)
                {
                    Log.d("Error", e.getMessage());
                }
            else if (GlobalClass.SubscrRustore())
                SubscriptRustore();
//            else if (GlobalClass.SubscrHuawei())
//                SubscriptHuawei();
        };
    }

    private void SubscriptGoogle()
    {
        BillingClient.Builder builder = BillingClient.newBuilder(getContext());
        builder.setListener((billingResult, list) -> {});
        builder.enablePendingPurchases();
        BillingClient billingClient = builder.build();

        billingClient.startConnection(new BillingClientStateListener()
        {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult)
            {
                if (billingResult.getResponseCode() != OK)
                    return;

                List<String> ids = CustomSubscription.GetProductListGoogle();
                List<QueryProductDetailsParams.Product> products = ids.stream().map( productId ->
                        QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(productId)
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                ).collect(Collectors.toList());

                QueryProductDetailsParams.Builder builder = QueryProductDetailsParams.newBuilder();
                builder.setProductList(products);
                QueryProductDetailsParams params1 = builder.build();

                billingClient.queryProductDetailsAsync(params1, (billingResult1, list) ->
                {
                    if (billingResult1.getResponseCode() != OK)
                        return;

                    final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
                    ArrayList<CBillingProduct> cBillingProducts = new ArrayList<>();

                    for (ProductDetails productDetail: list)
                    {
//                            if (product.getProductStatus() == ProductStatus.ACTIVE)
                                if (GlobalClass.TestPaid() ||
                                        productDetail.getProductType().equals(BillingClient.ProductType.SUBS))
                                    cBillingProducts.add(new CBillingProduct(productDetail.getProductId(),
                                           productDetail.getName(), "",
                                            productDetail.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice(),
                                            productDetail));
                    }

                    CustomTrack.PaidFormOpenFromCabinet();

                    FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
                            (AppCompatActivity) getContext(), null, EnumTypeAction.Insert, _this,
                            cBillingProducts);
                    popupNew.PopupShow();

                });
            }

            @Override
            public void onBillingServiceDisconnected()
            {
                Log.e("Billing: getting product details Google", "Serivce Disconnected");
            }
        });
    }

    private void SubscriptRustore()
    {
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(getContext());

        ProductsUseCase productsUseCase = billingClient.getProducts();
        Task<List<Product>> tList = productsUseCase.getProducts(CustomSubscription.GetProductListRustore());

        tList.addOnSuccessListener(products ->
        {
            if (products.size() == 1)
            {
                PurchasesUseCase purchasesUseCase = billingClient.getPurchases();
                Task<PaymentResult> res = purchasesUseCase.purchaseProduct(products.get(0).getProductId());

                res.addOnSuccessListener(paymentResult ->
                {
                    if (paymentResult instanceof PaymentResult.Success)
                    {
                        Log.d("Billing: purchase product",
                                getResources().getString(R.string.PurchaseSuccess));
                        Toast.makeText(getContext(), R.string.PurchaseSuccess,
                                Toast.LENGTH_SHORT).show();
                        UpdateSubscriptionInfo();
                    }
                    else if (paymentResult instanceof PaymentResult.Cancelled)
                    {
                        Log.d("Billing: purchase product",
                                getResources().getString(R.string.PurchaseCancelled));
                        Toast.makeText(getContext(), R.string.PurchaseCancelled,
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (paymentResult instanceof PaymentResult.Failure)
                    {
                        Log.d("Billing: purchase product",
                                getResources().getString(R.string.PurchaseFailure) + ": " +
                                        ((PaymentResult.Failure)paymentResult).getErrorCode());
                        Toast.makeText(getContext(), R.string.PurchaseFailure,
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (paymentResult instanceof PaymentResult.InvalidPaymentState)
                    {
                        Log.d("Billing: purchase product",
                                getResources().getString(R.string.PurchaseInvalidPaymentState));
                        Toast.makeText(getContext(), R.string.PurchaseInvalidPaymentState,
                                Toast.LENGTH_SHORT).show();
                    }
                });

                res.addOnFailureListener(throwable ->
                {
                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Billing Rustore: purchase product", throwable.getMessage());
                });
            }
            else
            {
                final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
                ArrayList<CBillingProduct> cBillingProducts = new ArrayList<>();
                for (Product product: products)
                    if (product.getProductStatus() == ProductStatus.ACTIVE)
                        if (GlobalClass.TestPaid() || product.getProductType() == ProductType.SUBSCRIPTION)
                            cBillingProducts.add(new CBillingProduct(product.getProductId(),
                                    product.getTitle(), product.getDescription(), product.getPriceLabel()));

                CustomTrack.PaidFormOpenFromCabinet();

                FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
                        (AppCompatActivity) getContext(), null, EnumTypeAction.Insert, this,
                        cBillingProducts);
                popupNew.PopupShow();
            }
        });

        tList.addOnFailureListener(throwable ->
        {
            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Billing: getting products", throwable.getMessage());
        });
    }

//    private void SubscriptHuawei()
//    {
//        // Only those products already configured in AppGallery Connect can be queried.
//        List<String> productIdList = CustomSubscription.GetProductListHuawei();
//        ProductInfoReq req = new ProductInfoReq();
//        // priceType: 0: consumable; 1: non-consumable; 2: subscription
//        req.setPriceType(CustomSubscription.GetPriceTypeHuawei());
//        req.setProductIds(productIdList);
//        // Obtain the Activity object that calls the API.
//        Activity activity = getActivity();
//
//        // Call the obtainProductInfo API to obtain the details of the product configured in AppGallery Connect.
//        com.huawei.hmf.tasks.Task<ProductInfoResult> task = Iap.getIapClient(activity).obtainProductInfo(req);
//        task
//                .addOnSuccessListener(result ->
//                {
//                    // Obtain the product details returned upon a successful API call.
//                    List<ProductInfo> productList = result.getProductInfoList();
//                    if (productList.size() == 1)
//                    {
//                        //TODO: сразу подписываемся на Huawei
//                        // Construct a PurchaseIntentReq object.
//                        PurchaseIntentReq req2 = new PurchaseIntentReq();
//                        req2.setProductId(productList.get(0).getProductId());
//                        req2.setPriceType(productList.get(0).getPriceType());
//                        req2.setDeveloperPayload("test");
//// Call the createPurchaseIntent API to create a managed product order.
//                        com.huawei.hmf.tasks.Task<PurchaseIntentResult> task2 =
//                                Iap.getIapClient(activity).createPurchaseIntent(req2);
//                        task2.
//                                addOnSuccessListener(result1 ->
//                                {
//                                    // Obtain the order creation result.
//                                    Status status = result1.getStatus();
//                                    if (status.hasResolution())
//                                    {
//                                        try
//                                        {
//                                            // 6666 is a constant defined by yourself.
//                                            // Open the checkout screen returned.
//                                            status.startResolutionForResult(activity, 6666);
//                                        }
//                                        catch (IntentSender.SendIntentException exp) { }
//                                    }
//                                }).
//                                addOnFailureListener(e ->
//                                {
//                                    if (e instanceof IapApiException)
//                                    {
//                                        IapApiException apiException = (IapApiException) e;
//                                        Status status = apiException.getStatus();
//                                        int returnCode = apiException.getStatusCode();
//                                        Log.e("Billing huawei: purchase products", e.getMessage() + ": " +
//                                                returnCode);
//                                    }
//                                    else
//                                    {
//                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        Log.e("Billing huawei: purchase product", e.getMessage());
//                                    }
//                                });
//                    }
//                    else
//                    {
//                        ArrayList<CBillingProduct> cBillingProducts = new ArrayList<>();
//                        for (ProductInfo product : productList)
//                            if (product.getStatus() == 0)
//                                if (GlobalClass.TestPaid() || product.getPriceType() == CustomSubscription.GetPriceTypeHuawei())
//                                    cBillingProducts.add(new CBillingProduct(product.getProductId(),
//                                            product.getProductName(), product.getProductDesc(),
//                                            String.valueOf(product.getMicrosPrice() / 1000000)));
//
//                        CustomTrack.PaidFormOpenFromCabinet();
//
//                        FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
//                                (AppCompatActivity) getContext(), null, EnumTypeAction.Insert,
//                                _this, cBillingProducts);
//                        popupNew.PopupShow();
//                    }
//                })
//                .addOnFailureListener(e ->
//                {
//                    if (e instanceof IapApiException)
//                    {
//                        IapApiException apiException = (IapApiException) e;
//                        int returnCode = apiException.getStatusCode();
//                        Toast.makeText(getContext(), e.getMessage() + ": " +
//                                returnCode, Toast.LENGTH_SHORT).show();
//                        Log.e("Billing huawei: getting products", e.getMessage() + ": " +
//                                returnCode);
//                    }
//                    else
//                    {
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("Billing huawei: getting products", e.getMessage());
//                    }
//                });
//    }
    //endregion

    //region Click to button
    @Override
    public boolean refreshViewOnAdd(Object object) { return false; }

    @Override
    public boolean refreshViewOnUpdate(Object object) { return false; }

    @Override
    public boolean refreshViewOnDelete(Object object) { return false; }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }

    @Override
    public void refreshViewOnSubscript(CBillingProduct cBillingProduct)
    { UpdateSubscriptionInfo(); }
    //endregion
}