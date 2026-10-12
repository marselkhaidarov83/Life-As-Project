package com.MH83.LifeAsProject.view.Money.MoneyIncrease;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Money.MoneyIncreaseDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyDreamSettings;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.model.Money.MoneyIncreaseSettings;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentTableGraph;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeView;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class FragmentMoneyIncreaseList extends CustomFragment implements IFragmentViewCustom,
        IFragmentAddItem, IFragmentTableGraph, IFragmentLegend, IFragmentSettings
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_increase_list;
    //endregion

    //region Fragment parameters
    private final FragmentMoneyIncreaseList _this;
    IFragmentViewCustom<MoneyIncrease> _fragment = null;
    private ArrayList<MoneyIncrease> _dataSet = null;
    //endregion

    //region Fragment settings
    EnumTypeView _typeView = EnumTypeView.Table;
    private int _countMax = 60;
    private int _countProgress = 60;
    private int _shiftMax = 0;
    private int _shiftProgress = 0;
    private  int _scrollPosition = 0;
    public int GetScrollPosition()
    {
        if (_fragment instanceof FragmentMoneyIncreaseTable)
            return ((FragmentMoneyIncreaseTable)_fragment).getScrollPosition();
        else
            return _scrollPosition;
    }
    public Long getIncreaseId()
    {
        if (_typeView == EnumTypeView.Table)
            return ((FragmentMoneyIncreaseTable) _fragment).getIncreaseId();
        else
            return 0L;
    }
    public EnumTypeView GetTypeView() { return _typeView; }
    public int GetCountProgress()
    {
        if (_fragment instanceof FragmentMoneyIncreaseGraph)
            return ((FragmentMoneyIncreaseGraph)_fragment).getCountProgress();
        else
            return _countProgress;
    }

    public int GetShiftProgress()
    {
        if (_fragment instanceof FragmentMoneyIncreaseGraph)
            return ((FragmentMoneyIncreaseGraph)_fragment).getShiftProgress();
        else
            return _shiftProgress;
    }

    public int GetCountMax()
    {
        if (_fragment instanceof FragmentMoneyIncreaseGraph)
            return ((FragmentMoneyIncreaseGraph)_fragment).getCountMax();
        else
            return _countMax;
    }

    public int GetShiftMax()
    {
        if (_fragment instanceof FragmentMoneyIncreaseGraph)
            return ((FragmentMoneyIncreaseGraph)_fragment).getShiftMax();
        else
            return _shiftMax;
    }
    //endregion

    //region Constructors
    public FragmentMoneyIncreaseList()
    {
        _this = this;
    }

    public FragmentMoneyIncreaseList(int scrollPosition, EnumTypeView typeView,
                                     int countProgress, int shiftProgress,
                                     int countMax, int shiftMax)
    {
        _this = this;
        _scrollPosition = scrollPosition;
        _typeView = typeView;
        _countProgress = countProgress;
        _shiftProgress = shiftProgress;
        _countMax = countMax;
        _shiftMax = shiftMax;
    }
    //endregion

    //region Start Fragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LoadData();

        View view = inflater.inflate(_layoutId, container, false);

        setFragmentTableOrGraph();

        return view;
    }

    private void LoadData()
    {
        if (_dataSet == null)
        {
            GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
            _dataSet = (ArrayList<MoneyIncrease>) MoneyIncreaseDataSource.LoadPlanDataWithPercent(
                    getContext(), gVar.get_moneyIncreaseSettings());
        }

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (MoneyIncreaseDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentMoneyIncrease"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyIncrease");
        }
    }

    private void setFragmentTableOrGraph()
    {
        _fragment = null;

        if (_typeView == EnumTypeView.Table)
            _fragment = new FragmentMoneyIncreaseTable(_dataSet, _scrollPosition);
        else if (_typeView == EnumTypeView.Graph)
            _fragment = new FragmentMoneyIncreaseGraph(_dataSet, _countMax, _shiftMax,
                    _countProgress, _shiftProgress);

        if (_fragment != null)
            openFragmentContainerMain((Fragment) _fragment);
    }

    private void openFragmentContainerMain(Fragment fragment)
    {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null)
        {
            FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.replace(R.id.containerFMI, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    //endregion

    //region Click to button
    @Override
    public View.OnClickListener onClickToSetTable()
    {
        return view ->
        {
            if (_typeView == EnumTypeView.Graph)
            {
                _typeView = EnumTypeView.Table;
                if (_fragment != null)
                {
                    _countMax = ((FragmentMoneyIncreaseGraph)_fragment).getCountMax();
                    _countProgress = ((FragmentMoneyIncreaseGraph)_fragment).getCountProgress();
                    _shiftMax = ((FragmentMoneyIncreaseGraph)_fragment).getShiftMax();
                    _shiftProgress = ((FragmentMoneyIncreaseGraph)_fragment).getShiftProgress();
                }
                setFragmentTableOrGraph();

                if (getActivity() != null)
                    ((MainActivity)getActivity()).RefreshToolbarTopRightBase();
            }
        };
    }

    @Override
    public View.OnClickListener onClickToSetGraph()
    {
        return view ->
        {
            if (_typeView == EnumTypeView.Table)
            {
                _typeView = EnumTypeView.Graph;
                if (_fragment != null)
                    _scrollPosition = ((FragmentMoneyIncreaseTable)_fragment).getScrollPosition();
                setFragmentTableOrGraph();

                if (getActivity() != null)
                    ((MainActivity)getActivity()).RefreshToolbarTopRightBase();
            }
        };
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyIncrease)
        {
            _fragment.refreshViewOnAdd((MoneyIncrease) object);
            return true;
        }
        else if (object instanceof MoneyIncreaseSettings)
        {
            MoneyIncreaseSettings moneyIncreaseSettings = (MoneyIncreaseSettings) object;
            GlobalClass gV = (GlobalClass) getContext().getApplicationContext();
            gV.set_moneyIncreaseSettings(moneyIncreaseSettings);
            _dataSet = null;
            LoadData();
            setFragmentTableOrGraph();

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object) { return false; }

    @Override
    public boolean refreshViewOnDelete(Object object) { return false; }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }

    //endregion

    //region
    private void ClickToLegend()
    {
        new FragmentMoneyIncreaseLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }

    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }
    //endregion

    //region IFragmentAddItem
    public boolean ShowAddItemButton() { return true; }

    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (!MoneyIncrease.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick();
                return;
            }

            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            String sMonth;
            if (month < 10)
                sMonth = "0" + month;
            else
                sMonth = String.valueOf(month);

            FragmentMoneyIncreasePopupNew fragmentPopupNew =
                    new FragmentMoneyIncreasePopupNew((AppCompatActivity) getContext(),
                            null, EnumTypeAction.Insert, _this, false,
                            0L,
                            sMonth,
                            String.valueOf(calendar.get(Calendar.YEAR)),
                            null
//                            ,false
                    );
            fragmentPopupNew.PopupShow();
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

            CustomTrack.PaidFormOpenFromIncrease();

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

                    CustomTrack.PaidFormOpenFromIncrease();

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

    //region IFragmentSettings
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            new FragmentMoneyIncreaseSettings(
                    (AppCompatActivity) getContext(),
                    globalVariable.get_moneyIncreaseSettings().Clone(),
                    this).PopupShow();
        };
    }

    @Override
    public boolean ShowFormSettingButton()
    {
        return true;
    }
    //endregion
}