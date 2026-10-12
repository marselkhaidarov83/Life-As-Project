package com.MH83.LifeAsProject.view.Money.MoneyCredit;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyCreditAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Money.MoneyCreditDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentBack;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class FragmentMoneyCredit extends CustomFragment implements IFragmentViewCustom<MoneyCredit>,
        IFragmentBack, IFragmentAddItem, IFragmentLegend
{
    //region Fragment parameters
    private final int _layoutId = R.layout.fragment_money_credit;
    private final FragmentMoneyCredit _this;
    private CustomPopupBottomFragment _fragmentPopupNew;
    //endregion

    //region Fragment settings
    //endregion

    //region Fragment Components
    RecyclerView recyclerViewCredit;
    //endregion

    //region Constructors
    public FragmentMoneyCredit()
    {
        _this = this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
//        _categoryId = globalVariable.get_pnl_categoryId();
    }

    @Override
    public void onDestroy()
    {
//        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
//        globalVariable.set_pnl_scrollPosition(GetScrollPosition());

        super.onDestroy();
    }

    //endregion

    //region Start Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);

        FindComponents(view);
        FillRViewPNL();

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (MoneyPNLDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentMoneyPNL"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyPNL");
        }

        return view;
    }

    private void FindComponents(View view)
    {
        recyclerViewCredit = view.findViewById(R.id.recycleViewCredit);
    }

    private void FillRViewPNL()
    {
        ArrayList<MoneyCredit> dataSet = MoneyCreditDataSource.LoadData(getContext());

        MoneyCreditAdapter adapter = new MoneyCreditAdapter(getContext(), dataSet, this);
        recyclerViewCredit.setAdapter(adapter);

//        for (int i = 0; i < rView.getItemDecorationCount(); i++)
//            rView.removeItemDecorationAt(0);
//        MoneyTransactionItemViewDecoration itemViewDecoration = new MoneyTransactionItemViewDecoration(getContext());
//        rView.addItemDecoration(itemViewDecoration);

//        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.MoneyPNLLineBasedBorderLineDp);
//        MoneyPNLSimpleCallback misc = new MoneyPNLSimpleCallback(rView, basedBorderLineLeftDp);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
//        itemTouchHelper.attachToRecyclerView(rView);
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(MoneyCredit moneyCredit)
    {
        MoneyCreditAdapter adapter = (MoneyCreditAdapter) recyclerViewCredit.getAdapter();
        if (adapter != null)
            recyclerViewCredit.scrollToPosition(adapter.AddItem(moneyCredit));

        return true;
    }

    @Override
    public boolean refreshViewOnUpdate(MoneyCredit moneyCredit)
    {
        MoneyCreditAdapter adapter = (MoneyCreditAdapter) recyclerViewCredit.getAdapter();
        if (adapter != null)
            recyclerViewCredit.scrollToPosition(adapter.UpdateItem(moneyCredit));

        return true;
    }

    @Override
    public boolean refreshViewOnDelete(MoneyCredit moneyCredit)
    {
        MoneyCreditAdapter adapter = (MoneyCreditAdapter) recyclerViewCredit.getAdapter();
        if (adapter != null)
            adapter.DeleteFromDataset(moneyCredit);

        return true;
    }

    @Override
    public boolean refreshViewOnSelect(MoneyCredit moneyCredit) { return false; }
    //endregion

    //region ClickToBack
    @Override
    public void ClickToBack()
    {
        if (getActivity() != null)
            ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_money_accounting);
    }
    //endregion

    //region IFragmentAddItem
    public boolean ShowAddItemButton()
    {
        return true;
    }

    @Override
    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (!MoneyCredit.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick(globalVariable);
                return;
            }

            _fragmentPopupNew = new FragmentMoneyCreditPopupNew((AppCompatActivity) getActivity(),
                    null, EnumTypeAction.Insert, _this);
            _fragmentPopupNew.PopupShow();
        };
    }
    //endregion

    //region IFragmentLegend
    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }

    private void ClickToLegend()
    {
        new FragmentMoneyCreditLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

    //region Subscription
    private void SubscriptionClick(GlobalClass globalVariable)
    {
        if (globalVariable.SubscrGoogle())
            SubscriptGoogle(globalVariable);

        if (globalVariable.SubscrRustore())
            SubscriptRustore(globalVariable);
    }

    private void SubscriptGoogle(GlobalClass globalVariable)
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

                    ArrayList<CBillingProduct> cBillingProducts = new ArrayList<>();

                    for (ProductDetails productDetail: list)
                    {
                        if (GlobalClass.TestPaid() ||
                                productDetail.getProductType().equals(BillingClient.ProductType.SUBS))
                            cBillingProducts.add(new CBillingProduct(productDetail.getProductId(),
                                    productDetail.getName(), "",
                                    productDetail.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice(),
                                    productDetail));
                    }

                    CustomTrack.PaidFormOpenFromCredit();

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

    private void SubscriptRustore(GlobalClass globalVariable)
    {
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(getContext());

        ProductsUseCase productsUseCase = billingClient.getProducts();
        Task<List<Product>> tList = productsUseCase.getProducts(CustomSubscription.GetProductListRustore());

        tList.addOnSuccessListener(products ->
        {
            ArrayList<CBillingProduct> cBillingProducts = new ArrayList<>();
            for (Product product: products)
                if (product.getProductStatus() == ProductStatus.ACTIVE)
                    if (GlobalClass.TestPaid() || product.getProductType() == ProductType.SUBSCRIPTION)
                        cBillingProducts.add(new CBillingProduct(product.getProductId(),
                                product.getTitle(), product.getDescription(), product.getPriceLabel()));

            CustomTrack.PaidFormOpenFromCredit();

            FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
                    (AppCompatActivity) getContext(), null, EnumTypeAction.Insert, this,
                    cBillingProducts);
            popupNew.PopupShow();
        });

        tList.addOnFailureListener(throwable ->
        {
            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Billing: getting products", throwable.getMessage());
        });
    }
    //endregion
}