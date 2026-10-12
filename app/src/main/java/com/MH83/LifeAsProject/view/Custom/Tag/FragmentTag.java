package com.MH83.LifeAsProject.view.Custom.Tag;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.common.TagAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.ParamDataSource;
import com.MH83.LifeAsProject.data.Custom.TagDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTagTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentBack;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoCabinet;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoSocial;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMascotToAdd;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Custom.Tag;
import com.MH83.LifeAsProject.model.Custom.TagSettings;
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

public class FragmentTag extends Fragment implements IFragmentViewCustom,
        IFragmentBack, IFragmentAddItem, IFragmentLegend, IFragmentGotoSocial, IFragmentGotoCabinet,
        IFragmentSettings, IFragmentMascotToAdd
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_tag;
    //endregion

    //region Fragment parameters
    private final FragmentTag _this;
    private ArrayList<Tag> _dataSet = null;
    //endregion

    //region Fragment Components
    private RecyclerView recyclerViewTag;
    private FragmentTagPopupNew _fragmentPopup;
    //endregion

    //region Fragment settings
    //endregion

    //region Constructors
    public FragmentTag()
    {
        _this = this;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//        globalVariable.set_IFragmentViewCustom(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        LoadData();

        View view = inflater.inflate(_layoutId, container, false);
        FindComponents(view);
        SetListeners();

        TagAdapter adapter = new TagAdapter(getContext(), _dataSet, this);
        recyclerViewTag.setAdapter(adapter);

        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.MoneyTargetLineBasedBorderLineDp);

        TagSimpleCallback misc = new TagSimpleCallback(recyclerViewTag, basedBorderLineLeftDp);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
        itemTouchHelper.attachToRecyclerView(recyclerViewTag);

        return view;
    }

    private int GetWidthDp(DisplayMetrics displaymetrics)
    {
        int baseWidthTargetDp = (int) (displaymetrics.widthPixels / displaymetrics.scaledDensity);
        return baseWidthTargetDp - 5;
    }

    private void LoadData()
    {
        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
        gVar.set_enumTagTypeData(EnumTagTypeData.PNL);
        _dataSet = (ArrayList<Tag>) TagDataSource.LoadData(getContext(),
                    gVar.get_TagSettings(), false);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (ParamDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentTag"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentTag");
        }
    }

    private void SetListeners() { }

    private void FindComponents(View view)
    {
        recyclerViewTag = view.findViewById(R.id.recycleViewTag);
    }
    //endregion

    //region Set Params
    //endregion

    //region IFragmentAddItem
    public boolean ShowAddItemButton() { return true; }

    @Override
    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (!Tag.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick();
                return;
            }

            _fragmentPopup = new FragmentTagPopupNew((AppCompatActivity) getContext(), null,
                    EnumTypeAction.Insert, _this);
            _fragmentPopup.PopupShow();
        };
    }
    //endregion

    //region Click to button
    public void ClickToBack()
    {
        if (getActivity() != null)
            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_pnl);
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
        new FragmentTagLegendPopup((AppCompatActivity) getContext()).PopupShow();
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

                    CustomTrack.PaidFormOpenFromTag();

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
            ArrayList<CBillingProduct> cBillingProducts = new ArrayList<>();
            for (Product product: products)
                if (product.getProductStatus() == ProductStatus.ACTIVE)
                    if (GlobalClass.TestPaid() || product.getProductType() == ProductType.SUBSCRIPTION)
                        cBillingProducts.add(new CBillingProduct(product.getProductId(),
                                product.getTitle(), product.getDescription(), product.getPriceLabel()));

            CustomTrack.PaidFormOpenFromTag();

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

    //region IFragmentGotoSocial
    @Override
    public boolean ShowGotoCabinetButton(Context context)
    {
        GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        return globalVariable.get_showToBuyButtons();
    }
    //endregion

    //region IFragmentGotoCabinet
    @Override
    public boolean ShowGotoSocialButton(Context context)
    {
        GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
        return globalVariable.get_showToBuyButtons();
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof Tag)
        {
            TagAdapter adapter = ((TagAdapter) recyclerViewTag.getAdapter());
            if (adapter != null)
            {
                int pos = adapter.AddItem((Tag) object);
                recyclerViewTag.smoothScrollToPosition(pos);
            }

            _fragmentPopup = null;

            return true;
        }
        else if (object instanceof TagSettings)
        {
            TagSettings tagSettings = (TagSettings) object;
            GlobalClass gV = (GlobalClass) getContext().getApplicationContext();
            if (!gV.get_hasUnpaidSubscription())
                gV.set_TagSettings(tagSettings);

            LoadData();
            TagAdapter adapter = new TagAdapter(getContext(), _dataSet, this);
            recyclerViewTag.setAdapter(adapter);
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof Tag)
        {
            TagAdapter adapter = ((TagAdapter) recyclerViewTag.getAdapter());
            if (adapter != null)
            {
                int pos = adapter.UpdateItem((Tag) object);
                recyclerViewTag.smoothScrollToPosition(pos);
            }

            _fragmentPopup = null;

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof Tag)
        {
            TagAdapter adapter = ((TagAdapter) recyclerViewTag.getAdapter());
            if (adapter != null)
                adapter.DeleteFromDataset((Tag) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion

    //region IFragmentSettings
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            globalVariable.set_enumTagTypeData(EnumTagTypeData.PNL);
            new FragmentTagSettings(
                    (AppCompatActivity) getContext(),
                    globalVariable.get_TagSettings().Clone(),
                    this).PopupShow();
        };
    }

    @Override
    public boolean ShowFormSettingButton()
    {
        return true;
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
                new FragmentTagLegendToAddPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion
}