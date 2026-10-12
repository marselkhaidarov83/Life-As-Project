package com.MH83.LifeAsProject.view.Money.MoneyDream;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.MH83.LifeAsProject.adapter.rView.money.MoneyDreamAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.data.Money.MoneyDreamDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentFinishedTargets;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMascotToAdd;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSetPopupImage;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetList;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetListForYear;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyDream;
import com.MH83.LifeAsProject.model.Money.MoneyDreamSettings;
import com.MH83.LifeAsProject.model.Settings.NotifProjectSettings;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class FragmentMoneyDream extends CustomFragment implements IFragmentViewCustom,
        IFragmentAddItem, IFragmentSetPopupImage, IFragmentLegend, IFragmentSettings,
        IFragmentFinishedTargets, IFragmentShowTargetListForYear, IFragmentShowTargetList,
        IFragmentMascotToAdd
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_dream;
    //endregion

    //region Fragment parameters
    private final FragmentMoneyDream _this;
    private ArrayList<MoneyDream> _dataSet;
    private FragmentMoneyDreamPopupNew _fragmentPopup;
    //endregion

    //region Fragment Components
    private RecyclerView rView;
    //endregion

    //region Fragment settings
    private int _scrollPosition = 0;
    public int GetScrollPosition()
    {
        _scrollPosition = ((GridLayoutManager) rView.getLayoutManager()).findFirstVisibleItemPosition();
        return _scrollPosition;
    }
    //endregion

    //region Constructors
    public FragmentMoneyDream()
    {
        _this = this;
    }

    public FragmentMoneyDream(int scrollPosition)
    {
        _this = this;
        _scrollPosition = scrollPosition;
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

    public static void StopNotification(Context context)
    {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotifProjectSettings.NotifIdNeedDream);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);
        FindComponents(view);

        RefreshFormData();

        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.MoneyDreamLineBasedBorderLineDp);

        MoneyDreamSimpleCallback misc = new MoneyDreamSimpleCallback(rView, basedBorderLineLeftDp);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
        itemTouchHelper.attachToRecyclerView(rView);

        if (_scrollPosition != 0)
            rView.scrollToPosition(_scrollPosition);

        return view;
    }

    private void RefreshFormData()
    {
        LoadData();
        MoneyDreamAdapter adapter = new MoneyDreamAdapter(getContext(), _dataSet,
                this, GetHeightDream());
        rView.setAdapter(adapter);
    }

    private void LoadData()
    {
        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();

        _dataSet = (ArrayList<MoneyDream>) MoneyDreamDataSource.LoadData(getContext(),
                gVar.get_moneyDreamSettings());

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (!globalVariable.get_legendShowedHT("FragmentMoneyDream") ||
                MoneyDreamDataSource.GetCount(getContext()) == 0)
        {
//            ClickToLegend();
            globalVariable.set_legendShowedHT("FragmentMoneyDream");
        }
    }

    private void FindComponents(View view)
    {
        rView = view.findViewById(R.id.recycleViewDreams);
    }

    private int GetHeightDream()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int heightDream = displaymetrics.heightPixels;
        heightDream += getResources().getDimension(R.dimen.UnknownToolbarHeight);
        heightDream -= getResources().getDimension(R.dimen.Fragment_MainActivity_Toolbar_Height) +
                getResources().getDimension(R.dimen.MoneyDreamBottomToolbarHeight);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            heightDream -= getResources().getDimension(R.dimen.UnknownPortraitHeight);
            heightDream /= 5;
        }
        else
        {
            heightDream -= getResources().getDimension(R.dimen.UnknownLandscapeHeight);
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
                heightDream /= 3;
            else
                heightDream /= 2;
        }

        return heightDream;
    }
    //endregion

    //region Click to button
    public void GoToFragmentMoneyDreamTarget()
    {
        if (getActivity() != null)
        {
            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_moneyTargetDreamId(getDreamId());
            globalVariable.set_moneyTargetTargetId(0L);
            globalVariable.set_moneyTargetYear(null);
            globalVariable.set_moneyTargetEnumTargetStatus4Settings(null);
            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_target);
        }
    }

    private Long getDreamId()
    {
        MoneyDreamAdapter moneyDreamAdapter = ((MoneyDreamAdapter) rView.getAdapter());
        if (moneyDreamAdapter != null)
        {
            MoneyDream moneyDream = moneyDreamAdapter.getCurrentItem();
            if (moneyDream != null)
                return moneyDream.get_id();
            else
                return 0L;
        }
        else
            return 0L;
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyDream)
        {
            MoneyDream moneyDream = (MoneyDream) object;
            MoneyDreamAdapter moneyDreamAdapter = ((MoneyDreamAdapter) rView.getAdapter());
            if (moneyDreamAdapter != null) {
                Long pos = moneyDreamAdapter.AddItem(moneyDream);
                _scrollPosition = Math.toIntExact(pos);
                rView.smoothScrollToPosition(Math.toIntExact(pos));
            }

            _fragmentPopup = null;

            if (MoneyDreamDataSource.GetCount(getContext()) >= 3)
                StopNotification(getActivity());

            return true;
        }
        else if (object instanceof MoneyDreamSettings)
        {
            MoneyDreamSettings moneyDreamSettings = (MoneyDreamSettings) object;
            GlobalClass gV = (GlobalClass) getContext().getApplicationContext();
            if (!gV.get_hasUnpaidSubscription())
                gV.set_moneyDreamSettings(moneyDreamSettings);
            RefreshFormData();
        }

        return true;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyDream)
        {
            MoneyDream moneyDream = (MoneyDream) object;
            MoneyDreamAdapter moneyDreamAdapter = ((MoneyDreamAdapter) rView.getAdapter());
            if (moneyDreamAdapter != null) {
                Long pos = moneyDreamAdapter.UpdateItem(moneyDream);
                if (pos != 0) {
                    _scrollPosition = Math.toIntExact(pos);
                    rView.smoothScrollToPosition(Math.toIntExact(pos));
                }
            }

            _fragmentPopup = null;
            return true;
        }

        return true;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyDream)
        {
            MoneyDream moneyDream = (MoneyDream) object;

            MoneyDreamAdapter moneyDreamAdapter = ((MoneyDreamAdapter) rView.getAdapter());
            if (moneyDreamAdapter != null)
                moneyDreamAdapter.DeleteFromDataset(moneyDream);

            return true;
        }

        return true;
    }

    @Override
    public boolean refreshViewOnSelect(Object object)
    {
        return false;
    }
    //endregion

    //region IFragmentAddItem
    public boolean ShowAddItemButton() { return true; }

    private void AddDream()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (!MoneyDream.canAdd(getContext(), globalVariable))
        {
            SubscriptionClick();
            return;
        }

        globalVariable.get_imagePath(true);
        _fragmentPopup = new FragmentMoneyDreamPopupNew((AppCompatActivity) getContext(),
                null, EnumTypeAction.Insert, _this);
        _fragmentPopup.PopupShow();
    }

    public View.OnClickListener onClickToAdd()
    {
        return view -> AddDream();
    }
    //endregion

    //region IFragmentSettings
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            new FragmentMoneyDreamSettings(
                    (AppCompatActivity) getContext(),
                    globalVariable.get_moneyDreamSettings().Clone(),
                    this).PopupShow();
        };
    }

    @Override
    public boolean ShowFormSettingButton() {
        return true;
    }
    //endregion

    //region IFragmentSetPopupImage
    public void setPopupImage(Uri uri)
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (_fragmentPopup == null)
            _fragmentPopup = (FragmentMoneyDreamPopupNew) globalVariable.get_popup();

        if (_fragmentPopup != null)
            _fragmentPopup.set_Image(uri);

        if (rView.getAdapter() != null)
            ((MoneyDreamAdapter) rView.getAdapter()).setPopupImage(uri);
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
        new FragmentMoneyDreamLegendPopup((AppCompatActivity) getContext()).PopupShow();
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
                new FragmentMoneyDreamLegendToAddPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

    //region IFragmentFinishedTargets
    @Override
    public View.OnClickListener onClickToFinishedTargets()
    {
        return view ->
        {
            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_moneyTargetDreamId(null);
            globalVariable.set_moneyTargetTargetId(0L);
            globalVariable.set_moneyTargetYear(null);
            globalVariable.set_moneyTargetEnumTargetStatus4Settings(EnumTargetStatus4Settings.Finished);
            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_target);
        };
    }
    //endregion

    //region IFragmentShowTargetListForYear
    @Override
    public View.OnClickListener onClickToShowTargetListForYear()
    {
        return view ->
        {
            Calendar calendar = Calendar.getInstance();

            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_moneyTargetDreamId(null);
            globalVariable.set_moneyTargetTargetId(0L);
            globalVariable.set_moneyTargetYear(calendar.get(Calendar.YEAR));
            globalVariable.set_moneyTargetEnumTargetStatus4Settings(EnumTargetStatus4Settings.All);
            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_target);
        };
    }
    //endregion

    //region IFragmentShowTargetList
    @Override
    public View.OnClickListener onClickToShowTargetList()
    {
        return view ->
        {
            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_moneyTargetDreamId(null);
            globalVariable.set_moneyTargetTargetId(0L);
            globalVariable.set_moneyTargetYear(null);
            globalVariable.set_moneyTargetEnumTargetStatus4Settings(EnumTargetStatus4Settings.All);
            ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_target);
        };
    }
    //endregion

    //region Subscription
    private void SubscriptionClick()
    {
        if (GlobalClass.SubscrGoogle())
            SubscriptGoogle();
        else if (GlobalClass.SubscrRustore())
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

                    CustomTrack.PaidFormOpenFromDream();

                    FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
                            (AppCompatActivity) getContext(), null, EnumTypeAction.Insert, _this,
                            cBillingProducts);
                    popupNew.PopupShow();
                });
            }

            @Override
            public void onBillingServiceDisconnected()
            {
                Log.e("Billing: getting product details Google", "Service Disconnected");
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

            CustomTrack.PaidFormOpenFromDream();

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