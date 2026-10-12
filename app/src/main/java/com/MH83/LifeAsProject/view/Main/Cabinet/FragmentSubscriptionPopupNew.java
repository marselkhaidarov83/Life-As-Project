package com.MH83.LifeAsProject.view.Main.Cabinet;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.BILLING_UNAVAILABLE;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.DEVELOPER_ERROR;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.ERROR;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.ITEM_NOT_OWNED;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.ITEM_UNAVAILABLE;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.NETWORK_ERROR;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.SERVICE_DISCONNECTED;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.USER_CANCELED;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.SubscriptionAdapter;
import com.MH83.LifeAsProject.adapter.viewPager.ViewPagerAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.interfac.IFragmentSubscriptionMain;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.MainActivity;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.material.tabs.TabLayout;
//import com.huawei.hms.iap.Iap;
//import com.huawei.hms.iap.IapApiException;
//import com.huawei.hms.iap.entity.ProductInfo;
//import com.huawei.hms.iap.entity.ProductInfoReq;
//import com.huawei.hms.iap.entity.ProductInfoResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import me.relex.circleindicator.CircleIndicator3;
import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.billingclient.usecase.PurchasesUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class FragmentSubscriptionPopupNew extends CustomPopupBottomFragment<CBillingProduct>
    implements IFragmentViewCustom<CBillingProduct>
{
    public static class ProductComparator implements Comparator<CBillingProduct>
    {
        public int compare(CBillingProduct left, CBillingProduct right)
        {
            Integer price1;
            if (!left.get_price().contains("."))
                price1 = Integer.parseInt(left.get_price());
            else
                price1 = Integer.parseInt(
                    left.get_price().substring(0, left.get_price().indexOf(".")));

            int price2;
            if (!right.get_price().contains("."))
                price2 = Integer.parseInt(right.get_price());
            else
                price2 = Integer.parseInt(
                        right.get_price().substring(0, right.get_price().indexOf(".")));

            return price1.compareTo(price2);
        }
    }

    //region Constants
    private static final int _layoutId = R.layout.popup_subscription_new;
    //endregion

    //region Fragment parameters
    private ArrayList<CBillingProduct> _dataSet;
    private final FragmentSubscriptionPopupNew _this;
    private Integer page;
    private final Integer maxPage = 16;
    //endregion

    //region Fragment Components
    RecyclerView rView;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    CircleIndicator3 circleIndicator;
    //endregion

    //region Constructors
    public FragmentSubscriptionPopupNew()
    {
        super(_layoutId,
                R.string.fragmentSubscriptionPopupSelectTitle,
                R.string.fragmentSubscriptionPopupSelectTitle,
                0);

        _this = this;
    }

    public FragmentSubscriptionPopupNew(AppCompatActivity activity,
                                        CBillingProduct data,
                                        EnumTypeAction typeAction,
                                        IFragmentViewCustom iFragmentViewCustom,
                                        ArrayList<CBillingProduct> dataSet)
    {
        super(activity, _layoutId, data, typeAction,
                null,
                null,
                R.string.fragmentSubscriptionPopupSelectTitle,
                R.string.fragmentSubscriptionPopupSelectTitle,
                0,
                iFragmentViewCustom,
                null,
                null);

        _dataSet = dataSet;
        if (_dataSet != null)
            _dataSet.sort(new ProductComparator());
        _this = this;
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        rView = view.findViewById(R.id.recycleViewSubscription);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        circleIndicator = view.findViewById(R.id.circleIndicator);

        _focusedViewToAdd = null;
        _focusedViewToUpdate = null;
        _showInputToAdd = false;
        _showInputToUpdate = false;
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] { });
    }

    @Override
    public void PopupShow()
    {
        super.PopupShow();

        CustomTrack.PaidFormOpened();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT)
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        else
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Subscription_Width) /*our width*/,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        if (_dataSet != null)
        {
            SubscriptionAdapter adapter = new SubscriptionAdapter(getContext(),
                    _dataSet, this);
            rView.setAdapter(adapter);
        }
        else
        {
            if (GlobalClass.SubscrRustore())
                GetDataForRustore();
            else if (GlobalClass.SubscrGoogle())
                GetDataFromGoogle();
//            else if (GlobalClass.SubscrHuawei())
//                GetDataFromHuawei();
        }

        SetViewPagerAdapter();
    }

    @Override
    protected void SetTimers()
    {
        super.SetTimers();

        page = - 1;
        final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(() ->
                {
                    page++;
                    page = page % maxPage;

                    viewPager.setCurrentItem(page);
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 3000); //3000 millisecond for replace the viewpager page
    }

    private void SetViewPagerAdapter()
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                if (page >= 0)
                    page = position;
            }
        });

        circleIndicator.setViewPager(viewPager);
        adapter.registerAdapterDataObserver(circleIndicator.getAdapterDataObserver());
    }

    private void GetDataForRustore()
    {
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(getContext());

        ProductsUseCase productsUseCase = billingClient.getProducts();
        Task<List<Product>> tList = productsUseCase.getProducts(CustomSubscription.GetProductListRustore());

        tList.addOnSuccessListener(products ->
        {
            _dataSet = new ArrayList<>();
            for (Product product: products)
                if (product.getProductStatus() == ProductStatus.ACTIVE)
                    if (GlobalClass.TestPaid() || product.getProductType() == ProductType.SUBSCRIPTION)
                        _dataSet.add(new CBillingProduct(product.getProductId(),
                                product.getTitle(), product.getDescription(), product.getPriceLabel()));

            if (_dataSet != null)
                _dataSet.sort(new ProductComparator());

            SubscriptionAdapter adapter = new SubscriptionAdapter(getContext(),
                    _dataSet, _this);
            rView.setAdapter(adapter);
        });

        tList.addOnFailureListener(throwable ->
        {
            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Billing rustore: getting products", throwable.getMessage());
        });
    }

    private void GetDataFromGoogle()
    {
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

                List<String> ids = CustomSubscription.GetProductListGoogle();
                List<QueryProductDetailsParams.Product> products = ids.stream().map(productId ->
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

                    _dataSet = new ArrayList<>();

                    for (ProductDetails productDetail: list)
                    {
                        if (GlobalClass.TestPaid() ||
                                productDetail.getProductType().equals(BillingClient.ProductType.SUBS))
                            _dataSet.add(new CBillingProduct(productDetail.getProductId(),
                                    productDetail.getName(), "",
                                    productDetail.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice(),
                                    productDetail));
                    }

                    if (_dataSet != null)
                        _dataSet.sort(new ProductComparator());

                    SubscriptionAdapter adapter = new SubscriptionAdapter(getContext(),
                            _dataSet, _this);
                    rView.setAdapter(adapter);
                });
            }

            @Override
            public void onBillingServiceDisconnected()
            {
                Log.e("Billing: getting product details Google", "Serivce Disconnected");
            }
        });
    }

//    private void GetDataFromHuawei()
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
//                    _dataSet = new ArrayList<>();
//                    for (ProductInfo product : productList)
//                        if (product.getStatus() == 0)
//                            if (GlobalClass.TestPaid() || product.getPriceType() == CustomSubscription.GetPriceTypeHuawei())
//                                _dataSet.add(new CBillingProduct(product.getProductId(),
//                                        product.getProductName(), product.getProductDesc(),
//                                        String.valueOf(product.getMicrosPrice() / 1000000)));
//
//                    if (_dataSet != null)
//                        _dataSet.sort(new ProductComparator());
//
//                    SubscriptionAdapter adapter = new SubscriptionAdapter(getContext(), _dataSet, _this);
//                    rView.setAdapter(adapter);
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

    //region Set Params
    protected void SetValues(Object[] objects) { }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return false;
    }

    protected boolean CheckFormValues()
    {
        if (GetDataFromForm() == null)
        {
            Toast.makeText(getContext(), R.string.SubscriptionNotSelected,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (GlobalClass.SubscrGoogle())
        {
            CustomTrack.PaidGoogleStarted(GetDataFromForm().get_price());
            CheckFormValueGoogle();
        }
        else if (GlobalClass.SubscrRustore())
        {
            CustomTrack.PaidRustoreStarted(GetDataFromForm().get_price());
            CheckFromValueRustore();
        }
        else if (GlobalClass.SubscrHuawei())
        {
            CustomTrack.PaidHuaweiStarted(GetDataFromForm().get_price());
            CheckFromValueHuawei();
        }

        return true;
    }

    private void CheckFromValueRustore()
    {
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(getContext());

        PurchasesUseCase purchasesUseCase = billingClient.getPurchases();
        Task<PaymentResult> res = purchasesUseCase.purchaseProduct(GetDataFromForm().get_id());

        res.addOnSuccessListener(paymentResult ->
        {
            Activity act = _activity;
            if (act == null)
                act = _iFragmentViewCustom.getActivity();

            final GlobalClass globalVariable1 = (GlobalClass) act.getApplicationContext();

            if (paymentResult instanceof PaymentResult.Success)
            {
                Log.d("Rustore Billing: purchase product",
                        act.getApplicationContext().getResources().getString(R.string.PurchaseSuccess));
                Toast.makeText(act.getApplicationContext(), R.string.PurchaseSuccess,
                        Toast.LENGTH_SHORT).show();

                ((MainActivity) act).UpdateSubscriptionInfo(globalVariable1);

                CustomTrack.PaidRustoreSuccess(GetDataFromForm().get_price());

                if (_iFragmentViewCustom instanceof IFragmentSubscriptionMain)
                    ((IFragmentSubscriptionMain) _iFragmentViewCustom).refreshViewOnSubscript(GetDataFromForm());
            }
            else if (paymentResult instanceof PaymentResult.Cancelled)
            {
                Log.d("Rustore Billing: purchase product",
                        act.getApplicationContext().getResources().getString(R.string.PurchaseCancelled));
                purchasesUseCase.deletePurchase(GetDataFromForm().get_id());
                Toast.makeText(act.getApplicationContext(), R.string.PurchaseCancelled,
                        Toast.LENGTH_SHORT).show();

                CustomTrack.PaidRustoreCanceled(GetDataFromForm().get_price());
            }
            else if (paymentResult instanceof PaymentResult.Failure)
            {
                Log.d("Rustore Billing: purchase product",
                        act.getApplicationContext().getResources().getString(R.string.PurchaseFailure) + ": " +
                                ((PaymentResult.Failure) paymentResult).getErrorCode());
                purchasesUseCase.deletePurchase(GetDataFromForm().get_id());
                Toast.makeText(act.getApplicationContext(), R.string.PurchaseFailure,
                        Toast.LENGTH_SHORT).show();

                CustomTrack.PaidRustoreFailure(GetDataFromForm().get_price());
            }
            else if (paymentResult instanceof PaymentResult.InvalidPaymentState)
            {
                Log.d("Rustore Billing: purchase product",
                        act.getApplicationContext().getResources().getString(R.string.PurchaseInvalidPaymentState));
                purchasesUseCase.deletePurchase(GetDataFromForm().get_id());
                Toast.makeText(act.getApplicationContext(), R.string.PurchaseInvalidPaymentState,
                        Toast.LENGTH_SHORT).show();

                CustomTrack.PaidRustoreInvalidPaymentState(GetDataFromForm().get_price());
            }
        });

        res.addOnFailureListener(throwable ->
        {
            Activity act = _activity;
            if (act == null)
                act = _iFragmentViewCustom.getActivity();

            Toast.makeText(act.getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Billing: purchase product", throwable.getMessage());

            CustomTrack.PaidRustoreFailureFail(GetDataFromForm().get_price());
        });
    }

    private void CheckFormValueGoogle()
    {
        BillingClient.Builder builder = BillingClient.newBuilder(getContext());
        builder.setListener((billingResult, list) ->
        {
            Activity act = _activity;
            if (act == null)
                act = _iFragmentViewCustom.getActivity();

            final GlobalClass globalVariable1 = (GlobalClass) act.getApplicationContext();

            int okCode = billingResult.getResponseCode();

            switch (okCode)
            {
                case OK:
                    Log.d("Google Billing: purchase product",
                            act.getApplicationContext().getResources().getString(R.string.PurchaseSuccess));
                    Toast.makeText(act.getApplicationContext(), R.string.PurchaseSuccess,
                            Toast.LENGTH_SHORT).show();

                    ((MainActivity) act).UpdateSubscriptionInfo(globalVariable1);

                    CustomTrack.PaidGoogleSuccess(GetDataFromForm().get_price());

                    if (_iFragmentViewCustom instanceof IFragmentSubscriptionMain)
                        ((IFragmentSubscriptionMain) _iFragmentViewCustom).refreshViewOnSubscript(GetDataFromForm());

                    break;

//                case SERVICE_TIMEOUT:
                case FEATURE_NOT_SUPPORTED:
                case SERVICE_DISCONNECTED:
                case SERVICE_UNAVAILABLE:
                case BILLING_UNAVAILABLE:
                case ITEM_UNAVAILABLE:
                case DEVELOPER_ERROR:
                case ERROR:
                case NETWORK_ERROR:
                    Log.d("Google Billing: purchase product",
                            act.getApplicationContext().getResources().getString(R.string.PurchaseFailure) + ": " +
                                    okCode);
                    Toast.makeText(act.getApplicationContext(), R.string.PurchaseFailure,
                            Toast.LENGTH_SHORT).show();

                    CustomTrack.PaidGoogleFailure(GetDataFromForm().get_price());

                    break;

                case USER_CANCELED:
                    Log.d("Google Billing: purchase product",
                            act.getApplicationContext().getResources().getString(R.string.PurchaseCancelled));
                    Toast.makeText(act.getApplicationContext(), R.string.PurchaseCancelled,
                            Toast.LENGTH_SHORT).show();

                    CustomTrack.PaidGoogleCanceled(GetDataFromForm().get_price());

                    break;

                case ITEM_ALREADY_OWNED:
                case ITEM_NOT_OWNED:
                    Log.d("Google Billing: purchase product",
                            act.getApplicationContext().getResources().getString(R.string.PurchaseInvalidPaymentState));
                    Toast.makeText(act.getApplicationContext(), R.string.PurchaseInvalidPaymentState,
                            Toast.LENGTH_SHORT).show();

                    CustomTrack.PaidGoogleInvalidPaymentState(GetDataFromForm().get_price());

                    break;

                default:
            }
        });
        builder.enablePendingPurchases();
        BillingClient billingClient = builder.build();

        billingClient.startConnection(new BillingClientStateListener()
        {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult)
            {
                if (billingResult.getResponseCode() != OK)
                    return;

                BillingFlowParams.Builder builder = BillingFlowParams.newBuilder();

                ProductDetails productDetails = (ProductDetails) GetDataFromForm().get_detail();
                List<String> ids = Collections.singletonList(GetDataFromForm().get_id());
                List<BillingFlowParams.ProductDetailsParams> productDetailsParams = ids.stream().map(
                        productId ->
                                BillingFlowParams.ProductDetailsParams.newBuilder()
                                        .setProductDetails(productDetails)
                                        .setOfferToken(productDetails.getSubscriptionOfferDetails().get(0).getOfferToken())
                                        .build()
                ).collect(Collectors.toList());

                builder.setProductDetailsParamsList(productDetailsParams);
                BillingFlowParams mParams = builder.build();
                billingClient.launchBillingFlow(_activity, mParams);
            }

            @Override
            public void onBillingServiceDisconnected()
            {
                Log.e("Billing: purchase subscribe Google", "Serivce Disconnected");
            }
        });
    }

    private void CheckFromValueHuawei()
    {
        //TODO: Переделать на Huawei
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(getContext());

        PurchasesUseCase purchasesUseCase = billingClient.getPurchases();
        Task<PaymentResult> res = purchasesUseCase.purchaseProduct(GetDataFromForm().get_id());

        res.addOnSuccessListener(paymentResult ->
        {
            Activity act = _activity;
            if (act == null)
                act = _iFragmentViewCustom.getActivity();

            final GlobalClass globalVariable1 = (GlobalClass) act.getApplicationContext();

            if (paymentResult instanceof PaymentResult.Success)
            {
                Log.d("Rustore Billing: purchase product",
                        act.getApplicationContext().getResources().getString(R.string.PurchaseSuccess));
                Toast.makeText(act.getApplicationContext(), R.string.PurchaseSuccess,
                        Toast.LENGTH_SHORT).show();

                ((MainActivity) act).UpdateSubscriptionInfo(globalVariable1);

                CustomTrack.PaidRustoreSuccess(GetDataFromForm().get_price());

                if (_iFragmentViewCustom instanceof IFragmentSubscriptionMain)
                    ((IFragmentSubscriptionMain) _iFragmentViewCustom).refreshViewOnSubscript(GetDataFromForm());
            }
            else if (paymentResult instanceof PaymentResult.Cancelled)
            {
                Log.d("Rustore Billing: purchase product",
                        act.getApplicationContext().getResources().getString(R.string.PurchaseCancelled));
                purchasesUseCase.deletePurchase(GetDataFromForm().get_id());
                Toast.makeText(act.getApplicationContext(), R.string.PurchaseCancelled,
                        Toast.LENGTH_SHORT).show();

                CustomTrack.PaidRustoreCanceled(GetDataFromForm().get_price());
            }
            else if (paymentResult instanceof PaymentResult.Failure)
            {
                Log.d("Rustore Billing: purchase product",
                        act.getApplicationContext().getResources().getString(R.string.PurchaseFailure) + ": " +
                                ((PaymentResult.Failure) paymentResult).getErrorCode());
                purchasesUseCase.deletePurchase(GetDataFromForm().get_id());
                Toast.makeText(act.getApplicationContext(), R.string.PurchaseFailure,
                        Toast.LENGTH_SHORT).show();

                CustomTrack.PaidRustoreFailure(GetDataFromForm().get_price());
            }
            else if (paymentResult instanceof PaymentResult.InvalidPaymentState)
            {
                Log.d("Rustore Billing: purchase product",
                        act.getApplicationContext().getResources().getString(R.string.PurchaseInvalidPaymentState));
                purchasesUseCase.deletePurchase(GetDataFromForm().get_id());
                Toast.makeText(act.getApplicationContext(), R.string.PurchaseInvalidPaymentState,
                        Toast.LENGTH_SHORT).show();

                CustomTrack.PaidRustoreInvalidPaymentState(GetDataFromForm().get_price());
            }
        });

        res.addOnFailureListener(throwable ->
        {
            Activity act = _activity;
            if (act == null)
                act = _iFragmentViewCustom.getActivity();

            Toast.makeText(act.getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Billing: purchase product", throwable.getMessage());

            CustomTrack.PaidRustoreFailureFail(GetDataFromForm().get_price());
        });
    }


    protected boolean CheckFormValuesForDelete() { return true;}
    //endregion

    //region Get Params
    //endregion

    //region Save Params
    protected CBillingProduct GetDataFromForm()
    {
        try {
            return ((SubscriptionAdapter) rView.getAdapter()).getCurrentItem();
        } catch (Exception e) {
            return null;
        }
    }
    //endregion

    //region Click to button
    @Override
    public boolean refreshViewOnAdd(CBillingProduct object) { return false; }

    @Override
    public boolean refreshViewOnUpdate(CBillingProduct object) { return false; }

    @Override
    public boolean refreshViewOnDelete(CBillingProduct object) { return false; }

    @Override
    public boolean refreshViewOnSelect(CBillingProduct object) { return false; }
    //endregion
}