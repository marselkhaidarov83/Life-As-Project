package com.MH83.LifeAsProject.view.Money.MoneyCategory;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.adapter.rView.money.MoneyCategoryAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMascotToAdd;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowBlankData;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTransactionList;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.custom.treeview.TreeView;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.adapter.ICollapseAllAdapter;
import com.MH83.LifeAsProject.interfac.adapter.IExpandAllAdapter;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentBack;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCollapseAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentExpandAll;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
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

public class FragmentMoneyCategory extends CustomFragment implements IFragmentAddItem,
        IFragmentBack, IFragmentViewCustom,
        IFragmentExpandAll, IFragmentCollapseAll,
        IFragmentLegend, IFragmentShowTransactionList, IFragmentSettings,
        IFragmentShowBlankData, IFragmentMascotToAdd
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_category;
    //endregion

    //region Fragment parameters
    private FragmentMoneyCategoryPopupNew _fragmentPopup;
    private final FragmentMoneyCategory _this;
    //endregion

    //region Fragment Components
    TreeView treeView;
    //endregion

    //region Fragment settings
    private int _fromItem;

    public boolean ShowButtonShowList(EnumTypeCategory typeCategory, Context context)
    {
        GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        if (globalVariable.get_hasUnpaidSubscription())
            return false;
        return typeCategory.equals(EnumTypeCategory.Cost);
    }
    //endregion

    //region Constructors
    public FragmentMoneyCategory() { _this = this; }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
        _fromItem = globalVar.get_moneyCategorySettings_fromItem();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);

        treeView = view.findViewById(R.id.treeViewCategory);
        LoadData();

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (MoneyCategoryDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentMoneyCategory"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyCategory");
        }

        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.MoneyCategoryLineBasedBorderLineDp);

//        MoneyCategorySimpleCallback misc = new MoneyCategorySimpleCallback(treeView, basedBorderLineLeftDp);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
//        itemTouchHelper.attachToRecyclerView(treeView);

//        treeView.scrollToPosition(_scrollPosition);

        return view;
    }

    private void LoadData()
    {
        GlobalClass global = (GlobalClass) getContext().getApplicationContext();
        MoneyCategoryAdapter adapter = new MoneyCategoryAdapter(getContext(),
                this, global.get_moneyCategorySettingsHT());
        treeView.setAdapter(adapter);
    }
    //endregion

    //region IFragmentExpandAll
    public boolean needExpandButton(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        if (globalVar.get_moneyCategorySettingEnumTypeCategory().equals(EnumTypeCategory.Wallet))
            return false;

        return globalVar.get_moneyCategorySettingsHT().get_showExpandButton();
    }

    @Override
    public View.OnClickListener onClickExpandAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyCategorySettingsHT().set_showExpandButton(false);
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();

            IExpandAllAdapter adapter = ((IExpandAllAdapter) treeView.getAdapter());
            if (adapter != null)
                adapter.ExpandAll(true);
        };
    }
    //endregion

    //region IFragmentCollapseAll
    public boolean needCollapseButton(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        if (globalVar.get_moneyCategorySettingEnumTypeCategory().equals(EnumTypeCategory.Wallet))
            return false;

        return !globalVar.get_moneyCategorySettingsHT().get_showExpandButton();
    }

    @Override
    public View.OnClickListener onClickCollapseAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyCategorySettingsHT().set_showExpandButton(true);
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();

            ICollapseAllAdapter adapter = ((ICollapseAllAdapter) treeView.getAdapter());
            if (adapter != null)
                adapter.CollapseAll(true);
        };
    }

    //endregion

    //region IFragmentSettings
    @Override
    public boolean ShowFormSettingButton() { return true; }

    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            new FragmentMoneyCategorySettings(
                    (AppCompatActivity) getContext(),
                    globalVariable.get_moneyCategorySettingsHT().Clone(),
                    this).PopupShow();
        };
    }
    //endregion

    //region IFragmentShowBlankData
    @Override
    public boolean ShowBlankData(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return !globalVar.get_moneyCategorySettingsHT().get_showBlankData();
    }

    @Override
    public boolean HideBlankData(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyCategorySettingsHT().get_showBlankData();
    }

    @Override
    public View.OnClickListener onClickToShowBlankData()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyCategorySettingsHT().set_showBlankData(true);
            ((MoneyCategoryAdapter) treeView.getAdapter()).RefreshData(globalVar.get_moneyCategorySettingsHT());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }

    @Override
    public View.OnClickListener onClickToHideBlankData()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            globalVar.get_moneyCategorySettingsHT().set_showBlankData(false);
            ((MoneyCategoryAdapter) treeView.getAdapter()).RefreshData(globalVar.get_moneyCategorySettingsHT());
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region Click to button
    @Override
    public void ClickToBack()
    {
        if (getActivity() != null)
            ((MainActivity)getActivity()).NavigationItemSelected(_fromItem);
    }

    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }

    private void ClickToLegend()
    {
        new FragmentMoneyCategoryLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

    //region IFragmentAddItem
    public boolean ShowAddItemButton() { return true; }

    @Override
    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (!MoneyCategory.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick();
                return;
            }

            GlobalClass globalVar = (GlobalClass) getActivity().getApplicationContext();
            _fragmentPopup = new FragmentMoneyCategoryPopupNew((AppCompatActivity) getContext(),
                    null, EnumTypeAction.Insert, this,
                    globalVar.get_moneyCategorySettingEnumTypeCategory());
            _fragmentPopup.PopupShow();
        };
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof  MoneyCategory)
        {
            MoneyCategory moneyCategory = (MoneyCategory) object;

            MoneyCategoryAdapter adapter = (MoneyCategoryAdapter) treeView.getAdapter();
            if (adapter != null)
            {
                Long pos = adapter.AddItem(moneyCategory);
                treeView.smoothScrollToPosition(Math.toIntExact(pos));
            }

            _fragmentPopup = null;

            return true;
        }
        else if (object instanceof MoneyInc4Cost)
        {
            MoneyInc4Cost moneyInc4Cost = (MoneyInc4Cost) object;
            MoneyCategoryAdapter adapter = (MoneyCategoryAdapter) treeView.getAdapter();
            if (adapter != null)
                adapter.ChangeByInc4Cost(moneyInc4Cost);

            _fragmentPopup = null;
            return true;
        }
        else if (object instanceof MoneyCategorySettings)
        {
            MoneyCategorySettings moneyCategorySettings = (MoneyCategorySettings) object;
            GlobalClass gV = (GlobalClass) getContext().getApplicationContext();
            gV.set_moneyCategorySettingsHT(moneyCategorySettings);
            LoadData();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyCategory)
        {
            MoneyCategory moneyCategory = (MoneyCategory) object;

            MoneyCategoryAdapter adapter = (MoneyCategoryAdapter) treeView.getAdapter();
            if (adapter != null)
                adapter.UpdateItem(moneyCategory);

            _fragmentPopup = null;
            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyCategory)
        {
            MoneyCategory moneyCategory = (MoneyCategory) object;

            MoneyCategoryAdapter adapter = (MoneyCategoryAdapter) treeView.getAdapter();
            if (adapter != null)
                adapter.DeleteFromDataset(moneyCategory);

            _fragmentPopup = null;

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion

    //region IFragmentShowList
    @Override
    public View.OnClickListener onClickToShowList()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
            globalVariable.set_moneyAccountingSettings_fromItem(R.id.navigation_money_category);

//            globalVariable.set_pnl_categoryId(-1L);
            globalVariable.get_moneyTransactionSettingsHT(EnumTransactionType.Inc4Cost).set_categoryId(null);
            globalVariable.get_moneyTransactionSettingsHT(EnumTransactionType.Inc4Cost).set_subCategoryId(null);
            globalVariable.set_pnl_moneyAccountingSumCost(null);
            globalVariable.set_pnl_moneyAccountingSumIncrease(null);
            CustomPeriodClass customPeriodClass =
                    new CustomPeriodClass(3, getContext().getString(R.string.Month));
            globalVariable.set_pnl_customPeriodClass(customPeriodClass);
            globalVariable.set_pnl_scrollPosition(0);
            globalVariable.set_pnl_transactionType(EnumTransactionType.Inc4Cost);

            if (getActivity() != null)
                ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_pnl);
        };
    }
    //endregion

    //region Subscription
    private void SubscriptionClick()
    {
        if (GlobalClass.SubscrGoogle())
            SubscriptGoogle();

        if (GlobalClass.SubscrRustore())
            SubscriptRustore();
    }

    private void SubscriptRustore()
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

            CustomTrack.PaidFormOpenFromCategory();

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

                    CustomTrack.PaidFormOpenFromCategory();

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
    //endregion

    //region IFragmentMascotToAdd
    public boolean ShowMascotButtonToAdd(Context context)
    {
        GlobalClass gV = (GlobalClass) context.getApplicationContext();
        return gV.get_showMascot();
    }

    public View.OnClickListener onClickToMascotToAdd()
    {
        return view ->
                new FragmentMoneyCategoryLegendToAddPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion
}