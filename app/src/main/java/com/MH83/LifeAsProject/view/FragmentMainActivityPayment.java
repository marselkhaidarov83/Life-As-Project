package com.MH83.LifeAsProject.view;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.interfac.IFragmentSubscriptionMain;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;
//import com.huawei.hms.iap.Iap;
//import com.huawei.hms.iap.IapApiException;
//import com.huawei.hms.iap.entity.ProductInfo;
//import com.huawei.hms.iap.entity.ProductInfoReq;
//import com.huawei.hms.iap.entity.ProductInfoResult;
//import com.huawei.hms.iap.entity.PurchaseIntentReq;
//import com.huawei.hms.iap.entity.PurchaseIntentResult;
//import com.huawei.hms.support.api.client.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.billingclient.usecase.PurchasesUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class FragmentMainActivityPayment extends Fragment implements IFragmentViewCustom, IFragmentSubscriptionMain
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_main_payment_activity;
    //endregion

    //region Fragment parameters
    private final IFragmentViewCustom _this;
    //endregion

    //region Fragment Components
    private TextView textViewLegendLinkBot;
    private Button buttonPaySubscription;
    private Button buttonKeySubmit;
    private EditText editTextKey;
    //endregion

    //region Constructors
    public FragmentMainActivityPayment()
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
        View view = inflater.inflate(_layoutId, container, false);

        textViewLegendLinkBot = view.findViewById(R.id.textViewLegendLinkBot);
        textViewLegendLinkBot.setMovementMethod(LinkMovementMethod.getInstance());

        FindComponents(view);
        SetListeners();

        return view;
    }

    private void FindComponents(View view)
    {
        textViewLegendLinkBot = view.findViewById(R.id.textViewLegendLinkBot);
        buttonPaySubscription = view.findViewById(R.id.buttonPaySubscription);
        buttonKeySubmit = view.findViewById(R.id.buttonKeySubmit);
        editTextKey = view.findViewById(R.id.editTextKey);
    }
    //endregion

    //region UpdateSubscription

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void UpdateSubscriptionInfo()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        ((MainActivity) getActivity()).UpdateSubscriptionInfo(globalVariable);

        Handler handler = new Handler();
        handler.postDelayed(() ->
                { // Код, который нужно запустить после времени
                    ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_dream);
                }, 3000);
    }
    //endregion

    //region SetListeners
    private void SetListeners()
    {
        buttonPaySubscription.setOnClickListener(SubscriptionClickListener());

        buttonKeySubmit.setOnClickListener(view ->
        {
            try
            {
                String key = editTextKey.getText().toString();
                UUID uuid = UUID.fromString(key);
                GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
                globalVariable.set_unpaidKey(uuid);

                ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_dream);
            }
            catch (Exception e)
            {
                Toast.makeText(
                        getContext().getApplicationContext(),
                        "Укажите корректный ключ",
                        Toast.LENGTH_SHORT).show();
            }
        });
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
                    Log.d("Error subscript Google", e.getMessage());
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
    {
        UpdateSubscriptionInfo();
    }
    //endregion
}