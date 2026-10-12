package com.MH83.LifeAsProject.view.Money.MoneyAsset;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyAssetAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeView;
import com.MH83.LifeAsProject.data.Money.MoneyAssetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.ICollapseAllAdapter;
import com.MH83.LifeAsProject.interfac.adapter.IExpandAllAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentBack;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCollapseAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentExpandAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentTypeAsset;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyAsset;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
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

public class FragmentMoneyAsset extends CustomFragment
    implements IFragmentBack, IFragmentViewCustom, IFragmentAddItem, IFragmentExpandAll,
        IFragmentCollapseAll, IFragmentSettings, IFragmentTypeAsset
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_asset;
    //endregion

    //region Fragment parameters
    private final FragmentMoneyAsset _this;
    //endregion

    //region Fragment Components
    private TreeView treeViewTypeAsset;
    //endregion

    //region Constructors
    public FragmentMoneyAsset()
    {
        _this = this;
    }
    //endregion

    //region Start Fragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);

        treeViewTypeAsset = view.findViewById(R.id.tree_view);

        SetAdapters();

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (MoneyAssetDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentMoneyAsset"))
        {
//            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyAsset");
        }

        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.TasksProjectLineBasedBorderLineDp);
        MoneyAssetSimpleCallback misc = new MoneyAssetSimpleCallback(treeViewTypeAsset, basedBorderLineLeftDp);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
        itemTouchHelper.attachToRecyclerView(treeViewTypeAsset);

        return view;
    }

    private void SetAdapters()
    {
        MoneyAssetAdapter adapter = new MoneyAssetAdapter(getContext(), this);
        treeViewTypeAsset.setAdapter(adapter);
    }
    //endregion

    //region IFragmentBack
    @Override
    public void ClickToBack()
    {
        if (getActivity() != null)
            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_capital);
    }
    //endregion

    //region IFragmentViewCustom

    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyAsset)
        {
            MoneyAssetAdapter adapter = (MoneyAssetAdapter) treeViewTypeAsset.getAdapter();
            if (adapter != null)
            {
                Long pos = adapter.AddItem((MoneyAsset) object);
                treeViewTypeAsset.smoothScrollToPosition(Math.toIntExact(pos));
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyAsset)
        {
            MoneyAssetAdapter adapter = (MoneyAssetAdapter) treeViewTypeAsset.getAdapter();
            if (adapter != null)
                adapter.UpdateItem((MoneyAsset) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyAsset)
        {
            MoneyAssetAdapter adapter = (MoneyAssetAdapter) treeViewTypeAsset.getAdapter();
            if (adapter != null)
                adapter.DeleteFromDataset((MoneyAsset) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) {
        return false;
    }
    //endregion

    //region IFragmentAddItem
    @Override
    public boolean ShowAddItemButton() { return true; }

    @Override
    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (!MoneyAsset.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick();
                return;
            }

            new FragmentMoneyAssetPopupNew((AppCompatActivity) getContext(),
                    null, EnumTypeAction.Insert, this).PopupShow();
        };
    }
    //endregion

    //region IFragmentExpandAll
    public boolean needExpandButton(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();
        return !global.get_hasUnpaidSubscription();
    }

    @Override
    public View.OnClickListener onClickExpandAll() {
        return view ->
        {
            IExpandAllAdapter adapter = ((IExpandAllAdapter) treeViewTypeAsset.getAdapter());
            if (adapter != null)
                adapter.ExpandAll(true);
        };
    }
    //endregion

    //region IFragmentCollapseAll
    public boolean needCollapseButton(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();
        return !global.get_hasUnpaidSubscription();
    }

    @Override
    public View.OnClickListener onClickCollapseAll() {
        return view ->
        {
            ICollapseAllAdapter adapter = ((ICollapseAllAdapter) treeViewTypeAsset.getAdapter());
            if (adapter != null)
                adapter.CollapseAll(true);
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

            CustomTrack.PaidFormOpenFromMoneyAsset();

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

                    CustomTrack.PaidFormOpenFromMoneyAsset();

                    FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
                            (AppCompatActivity) getContext(), null, EnumTypeAction.Insert,
                            _this,
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

    //region IFragmentSettings
    @Override
    public View.OnClickListener onClickSettings() {
        return null;
    }

    @Override
    public boolean ShowFormSettingButton() {
        return false;
    }
    //endregion

    //region IFragmentTypeAsset
    @Override
    public View.OnClickListener onClickToTypeAsset() {
        return v ->
                ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_typeasset);
    }
    //endregion
}
