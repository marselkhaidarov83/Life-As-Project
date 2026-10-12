package com.MH83.LifeAsProject.view.Custom.Param;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.common.ParamAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.ParamDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentBack;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoCabinet;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoSocial;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Custom.Param;
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

public class FragmentParam extends Fragment implements IFragmentViewCustom,
        IFragmentBack, IFragmentAddItem, IFragmentLegend, IFragmentGotoSocial, IFragmentGotoCabinet
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_param;
    //endregion

    //region Fragment parameters
    private final FragmentParam _this;
    private ArrayList<Param> _dataSet = null;
    //endregion

    //region Fragment Components
    private RecyclerView recyclerViewParam;
    private FragmentParamPopupNew _fragmentPopup;
    //endregion

    //region Fragment settings
    private int _scrollPosition = 0;
    public int GetScrollPosition()
    {
        _scrollPosition = ((LinearLayoutManager) recyclerViewParam.getLayoutManager()).findFirstVisibleItemPosition();
        return _scrollPosition;
    }
    //endregion

    //region Constructors
    public FragmentParam()
    {
        _this = this;
    }

    public FragmentParam(int scrollPosition)
    {
        _scrollPosition = scrollPosition;
        _this = this;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
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

        SetComponentsSize();

        ParamAdapter adapter = new ParamAdapter(getContext(), _dataSet, this);
        recyclerViewParam.setAdapter(adapter);

        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.MoneyTargetLineBasedBorderLineDp);

        ParamSimpleCallback misc = new ParamSimpleCallback(recyclerViewParam, basedBorderLineLeftDp);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
        itemTouchHelper.attachToRecyclerView(recyclerViewParam);

        if (_scrollPosition != 0)
            recyclerViewParam.scrollToPosition(_scrollPosition);

        return view;
    }

    private void SetComponentsSize()
    {
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//        {
//            recyclerViewParam.getLayoutParams().width = GetRViewWidth();
//        }
//        else
//        {
//            recyclerViewParam.getLayoutParams().height = GetRViewHeight();
//        }
    }

    private int GetRViewHeight()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

//        if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//            return (int) (5 * (GetHeightTarget() + getResources().getDimension(R.dimen.BasePadding)));
//        else
//            return (int) (2 * (GetHeightTarget() + getResources().getDimension(R.dimen.BasePadding)));
        return 0;
    }

    private int GetWidthDp(DisplayMetrics displaymetrics)
    {
        int baseWidthTargetDp = (int) (displaymetrics.widthPixels / displaymetrics.scaledDensity);
        return baseWidthTargetDp - 5;
    }

    private int GetRViewWidth()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return (int) (displaymetrics.scaledDensity * GetWidthDp(displaymetrics) * 7 / 10);
    }

    private void LoadData()
    {
        if (_dataSet == null)
            _dataSet = (ArrayList<Param>) ParamDataSource.LoadData(getContext());

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (ParamDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentParam"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentParam");
        }
    }

    private void SetListeners() { }

    private void FindComponents(View view)
    {
        recyclerViewParam = view.findViewById(R.id.recycleViewParam);
    }

//    private int GetHeightTarget()
//    {
//        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//
//        int heightTarget = displaymetrics.heightPixels;
//        heightTarget += getResources().getDimension(R.dimen.UnknownToolbarHeight);
//        heightTarget -= getResources().getDimension(R.dimen.MoneyDreamHeadToolbarHeight) +
//                getResources().getDimension(R.dimen.MoneyDreamBottomToolbarHeight);
//
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//        {
//            heightTarget -= getResources().getDimension(R.dimen.BasePadding);
//            heightTarget -= getResources().getDimension(R.dimen.UnknownPortraitHeight);
//            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                heightTarget /= 6;
//            else
//                heightTarget /= 3;
//        }
//        else
//        {
//            heightTarget -= getResources().getDimension(R.dimen.UnknownLandscapeHeight);
//            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
//                heightTarget /= 3;
//            else
//            {
//                heightTarget += getResources().getDimension(R.dimen.BasePadding);
//                heightTarget /= 1;
//            }
//        }
//
//        return heightTarget;
//    }
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
            if (!Param.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick(globalVariable);
                return;
            }

            _fragmentPopup = new FragmentParamPopupNew((AppCompatActivity) getContext(), null,
                    EnumTypeAction.Insert, _this);
            _fragmentPopup.PopupShow();
        };
    }
    //endregion

    //region Click to button
    public void ClickToBack()
    {
        if (getActivity() != null)
            ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_money_target);
    }

    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof Param)
        {
            ParamAdapter adapter = ((ParamAdapter) recyclerViewParam.getAdapter());
            if (adapter != null)
            {
                int pos = adapter.AddItem((Param) object);
                recyclerViewParam.smoothScrollToPosition(pos);
            }

            _fragmentPopup = null;

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof Param)
        {
            ParamAdapter adapter = ((ParamAdapter) recyclerViewParam.getAdapter());
            if (adapter != null)
            {
                int pos = adapter.UpdateItem((Param) object);
                recyclerViewParam.smoothScrollToPosition(pos);
            }

            _fragmentPopup = null;

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof Param)
        {
            ParamAdapter adapter = ((ParamAdapter) recyclerViewParam.getAdapter());
            if (adapter != null)
                adapter.DeleteFromDataset((Param) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }

    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }

    private void ClickToLegend()
    {
        new FragmentParamLegendPopup((AppCompatActivity) getContext()).PopupShow();
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

                    CustomTrack.PaidFormOpenFromParam();

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

            CustomTrack.PaidFormOpenFromParam();

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
}