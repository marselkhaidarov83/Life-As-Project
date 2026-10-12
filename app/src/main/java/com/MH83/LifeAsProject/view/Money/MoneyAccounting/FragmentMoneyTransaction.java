package com.MH83.LifeAsProject.view.Money.MoneyAccounting;

import static android.content.Context.NOTIFICATION_SERVICE;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.rView.money.MoneyTransactionAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerPeriodAdapter;
import com.MH83.LifeAsProject.custom.CustomDateCheck;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyInc4CostDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyPNLDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyTargetDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentBack;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMascotToAdd;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentTag;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSettings;
import com.MH83.LifeAsProject.model.Settings.NotifMoneySettings;
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

public class FragmentMoneyTransaction extends CustomFragment
        implements IFragmentViewCustom<Object>,
        IFragmentBack,
        IFragmentLegend, IFragmentTag,
        IFragmentSettings,
        IFragmentMascotToAdd
{
    //region Fragment parameters
    private static final int _layoutId = R.layout.fragment_money_transaction;
    private final FragmentMoneyTransaction _this;
    //endregion

    //region Fragment settings
    EnumTransactionType _transactionType;
    private Calendar _dateStart;
    private Calendar _dateEnd;
    private Long _categoryId;
    public Long GetCategoryId()
    {
        return _categoryId;
    }
    private CustomPeriodClass _prevCustomPeriodClass;
    private CustomPeriodClass _customPeriodClass;
    public CustomPeriodClass getCustomPeriodClass()
    {
        return (CustomPeriodClass) _spinnerDate.getSelectedItem();
    }
    MoneyAccountingSum _moneyAccountingSumCost;
    public MoneyAccountingSum getMASCost() { return _moneyAccountingSumCost; }
    MoneyAccountingSum _moneyAccountingSumIncrease;
    public MoneyAccountingSum getMASInc() { return _moneyAccountingSumIncrease; }
    private int _scrollPosition;
    public int GetScrollPosition()
    {
        _scrollPosition = ((LinearLayoutManager) rView.getLayoutManager()).findFirstVisibleItemPosition();
        return _scrollPosition;
    }
    int _fromItem = R.id.navigation_money_accounting;
    //endregion

    //region Fragment Components
    RecyclerView rView;
    private Spinner _spinnerDate;
    private TextView _textViewName;
    private TextView _textViewSum;
    private ImageView _imageViewDateBack, _imageViewDateForward;
    //endregion

    //region Constructors
    public FragmentMoneyTransaction()
    {
        _this = this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        _categoryId = globalVariable.get_moneyTransactionSettings().get_categoryId();
        _moneyAccountingSumCost = globalVariable.get_pnl_moneyAccountingSumCost();
        _moneyAccountingSumIncrease = globalVariable.get_pnl_moneyAccountingSumIncrease();
        _customPeriodClass = globalVariable.get_pnl_customPeriodClass();
        _scrollPosition = globalVariable.get_pnl_scrollPosition();
        _transactionType = globalVariable.get_pnl_transactionType();

        _fromItem = globalVariable.get_moneyAccountingSettings_fromItem();

        StopNotification();
    }

    private void StopNotification()
    {
        MoneyCategory wallet = MoneyCategoryDataSource.LoadOne(getContext(), _categoryId);
        if (wallet != null && wallet.getTypeCategory().equals(EnumTypeCategory.Wallet))
        {
            Integer targetId = MoneyTargetDataSource.LoadOneIdByWalletId(getContext(), _categoryId);
            NotificationManager notificationManager =
                    (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(NotifMoneySettings.NotifNoAccumulationThisMonth + targetId);
        }
    }

    @Override
    public void onDestroy()
    {
        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
//        globalVariable.set_pnl_scrollPosition(GetScrollPosition());
        globalVariable.set_pnl_transactionType(_transactionType);

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
        SetComponentSettings();
        FillForm();

        rView.scrollToPosition(_scrollPosition);

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
        _spinnerDate = view.findViewById(R.id.spinnerDate);
        _textViewName = view.findViewById(R.id.textViewName);
        _textViewSum = view.findViewById(R.id.textViewSum);
        _imageViewDateBack = view.findViewById(R.id.imageViewDateBack);
        _imageViewDateForward = view.findViewById(R.id.imageViewDateForward);
        rView = view.findViewById(R.id.recycleViewPNL);
    }

    private void SetComponentSettings()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (globalVariable.get_hasUnpaidSubscription())
        {
            _spinnerDate.setEnabled(false);
        }
    }

    private void FillForm()
    {
        SetListeners();
        FillTextViewName();
    }

    private void SetListeners()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        _imageViewDateBack.setOnClickListener(view ->
        {
            if (!CustomDateCheck.CheckPrevDate(globalVariable,
                    ((CustomPeriodClass) _spinnerDate.getSelectedItem())))
                return;

//            if (globalVariable.get_hasUnpaidSubscription())
//            {
//                Calendar calendarPrevMonthBegin = Calendar.getInstance();
//                calendarPrevMonthBegin.add(Calendar.DATE, -3);
//                calendarPrevMonthBegin.set(Calendar.DATE, 1);
//                calendarPrevMonthBegin.set(Calendar.HOUR, 0);
//                calendarPrevMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
//                calendarPrevMonthBegin.set(Calendar.MINUTE, 0);
//                calendarPrevMonthBegin.set(Calendar.SECOND, 0);
//                calendarPrevMonthBegin.set(Calendar.MILLISECOND, 0);
//
//                Calendar calendarThisMonthBegin = Calendar.getInstance();
//                calendarThisMonthBegin.set(Calendar.DATE, 1);
//                calendarThisMonthBegin.set(Calendar.HOUR, 0);
//                calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
//                calendarThisMonthBegin.set(Calendar.MINUTE, 0);
//                calendarThisMonthBegin.set(Calendar.SECOND, 0);
//                calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);
//
//                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).DecShift();
//                Calendar calendar = ((CustomPeriodClass) _spinnerDate.getSelectedItem()).get_start_dateC();
//                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).IncShift();
//
//                if (calendar.getTime().getTime() != calendarPrevMonthBegin.getTime().getTime() &&
//                        calendar.getTime().getTime() != calendarThisMonthBegin.getTime().getTime())
//                    return;
//            }

            ((CustomPeriodClass) _spinnerDate.getSelectedItem()).DecShift();
            ((SpinnerPeriodAdapter) _spinnerDate.getAdapter()).notifyDataSetChanged();
            globalVariable.set_pnl_customPeriodClass((CustomPeriodClass) _spinnerDate.getSelectedItem());
            FillFormAll();
        });

        _imageViewDateForward.setOnClickListener(view ->
        {
            if (!CustomDateCheck.CheckNextDate(globalVariable,
                    ((CustomPeriodClass) _spinnerDate.getSelectedItem())))
                return;

//            if (globalVariable.get_hasUnpaidSubscription())
//            {
//                Calendar calendarPrevMonthBegin = Calendar.getInstance();
//                calendarPrevMonthBegin.add(Calendar.DATE, -3);
//                calendarPrevMonthBegin.set(Calendar.DATE, 1);
//                calendarPrevMonthBegin.set(Calendar.HOUR, 0);
//                calendarPrevMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
//                calendarPrevMonthBegin.set(Calendar.MINUTE, 0);
//                calendarPrevMonthBegin.set(Calendar.SECOND, 0);
//                calendarPrevMonthBegin.set(Calendar.MILLISECOND, 0);
//
//                Calendar calendarThisMonthBegin = Calendar.getInstance();
//                calendarThisMonthBegin.set(Calendar.DATE, 1);
//                calendarThisMonthBegin.set(Calendar.HOUR, 0);
//                calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
//                calendarThisMonthBegin.set(Calendar.MINUTE, 0);
//                calendarThisMonthBegin.set(Calendar.SECOND, 0);
//                calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);
//
//                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).IncShift();
//                Calendar calendar = ((CustomPeriodClass) _spinnerDate.getSelectedItem()).get_start_dateC();
//                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).DecShift();
//
//                if (calendar.getTime().getTime() != calendarPrevMonthBegin.getTime().getTime() &&
//                        calendar.getTime().getTime() != calendarThisMonthBegin.getTime().getTime())
//                    return;
//            }

            ((CustomPeriodClass) _spinnerDate.getSelectedItem()).IncShift();
            ((SpinnerPeriodAdapter) _spinnerDate.getAdapter()).notifyDataSetChanged();
//            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_pnl_customPeriodClass((CustomPeriodClass) _spinnerDate.getSelectedItem());
            FillFormAll();
        });

        rView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) ->
        {
            final GlobalClass globalVariable1 = (GlobalClass) getActivity().getApplicationContext();
            globalVariable1.set_pnl_scrollPosition(GetScrollPosition());
        });

        FillSpinner();
    }

    private void FillFormAll()
    {
        CustomPeriodClass customPeriodClass = (CustomPeriodClass) _spinnerDate.getSelectedItem();
        _dateStart = customPeriodClass.get_start_dateC();
        _dateEnd = customPeriodClass.get_end_dateC();

        FillRViewPNL();
    }

    private void FillTextViewName()
    {
        if (_textViewName != null)
        {
            GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();
            MoneyTransactionSettings moneyTransactionSettings =
                    gVar.get_moneyTransactionSettings();
            Long categoryId = moneyTransactionSettings.get_categoryId();
            if (categoryId != null && categoryId != 0L)
            {
                MoneyCategory moneyCategory =
                        MoneyCategoryDataSource.LoadOne(getContext(), categoryId);
                _textViewName.setText(moneyCategory.get_name());
            }
            else
                _textViewName.setText("");
        }
    }

    public void FillTextViewSum()
    {
        MoneyTransactionAdapter adapter = (MoneyTransactionAdapter) rView.getAdapter();
        if (adapter != null)
            _textViewSum.setText(CustomStaticClass.getRubleSum(
                    adapter.getSumLong(_categoryId, _moneyAccountingSumCost, _moneyAccountingSumIncrease) / 100d));
        else
            _textViewSum.setText("");
    }

    private void FillSpinner()
    {
        if (_customPeriodClass == null)
            return;

        ArrayList<CustomPeriodClass> list = CustomPeriodClass.GetList(getContext());
        SpinnerPeriodAdapter adapter = new SpinnerPeriodAdapter(getContext(), list);
        _spinnerDate.setAdapter(adapter);
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).get_id() == _customPeriodClass.get_id())
            {
                _spinnerDate.setSelection(i);
                ((CustomPeriodClass) _spinnerDate.getSelectedItem())
                        .SetShift(_customPeriodClass.GetShift());
                break;
            }

        _spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (_prevCustomPeriodClass != null)
                    _prevCustomPeriodClass.ResetShift();
                _prevCustomPeriodClass = ((CustomPeriodClass) _spinnerDate.getSelectedItem());
                FillFormAll();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void FillRViewPNL()
    {
        GlobalClass gVar = (GlobalClass) getContext().getApplicationContext();

        ArrayList<MoneyTransaction> dataSet = null;
        if (_transactionType == EnumTransactionType.PNL)
            dataSet = MoneyPNLDataSource.LoadDataInd(getContext(),
                _dateStart, _dateEnd, gVar.get_moneyTransactionSettings());
        else if (_transactionType == EnumTransactionType.Inc4Cost)
            dataSet = MoneyInc4CostDataSource.LoadData(getContext(),
                    _dateStart, _dateEnd);

        MoneyTransactionAdapter adapter = new MoneyTransactionAdapter(getContext(), dataSet,
                this,
                _dateStart, _dateEnd,
                _moneyAccountingSumCost,
                _moneyAccountingSumIncrease,
                gVar.get_moneyTransactionSettings());
        rView.setAdapter(adapter);
        FillTextViewName();
        FillTextViewSum();

        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.MoneyPNLLineBasedBorderLineDp);

        for (int i = 0; i < rView.getItemDecorationCount(); i++)
            rView.removeItemDecorationAt(0);
        MoneyTransactionItemViewDecoration itemViewDecoration = new MoneyTransactionItemViewDecoration(getContext());
        rView.addItemDecoration(itemViewDecoration);

        MoneyTransactionSimpleCallback misc = new MoneyTransactionSimpleCallback(rView, basedBorderLineLeftDp);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
        itemTouchHelper.attachToRecyclerView(rView);
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyTransaction)
        {
            MoneyTransactionAdapter adapter = (MoneyTransactionAdapter) rView.getAdapter();
            if (adapter != null)
            {
                rView.scrollToPosition(Math.toIntExact(adapter.AddItem((MoneyTransaction) object)));
                FillTextViewSum();
            }
            return true;
        }
        else if (object instanceof MoneyTransactionSettings)
        {
            MoneyTransactionSettings settings = (MoneyTransactionSettings) object;
            GlobalClass gV = (GlobalClass) getContext().getApplicationContext();
            gV.set_moneyTransactionSettings(settings);
            FillFormAll();
            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyTransaction)
        {
            MoneyTransaction moneyTransaction = (MoneyTransaction) object;
            if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Cost &&
                    moneyTransaction instanceof MoneyInc4Cost)
                moneyTransaction.set_costCategoryName("");
            MoneyTransactionAdapter adapter = (MoneyTransactionAdapter) rView.getAdapter();
            if (adapter != null)
            {
                adapter.UpdateItem(moneyTransaction);
                FillTextViewSum();
            }
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyTransaction)
        {
            MoneyTransactionAdapter adapter = (MoneyTransactionAdapter) rView.getAdapter();
            if (adapter != null) {
                adapter.DeleteFromDataset((MoneyTransaction) object);
                FillTextViewSum();
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
    //endregion

    //region Click to button
    @Override
    public void ClickToBack()
    {
        if (getActivity() != null)
            ((MainActivity)getActivity()).NavigationItemSelected(_fromItem);
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
        new FragmentMoneyTransactionLegendPopup((AppCompatActivity) getContext()).PopupShow();
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

                    CustomTrack.PaidFormOpenFromPNL();

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

            CustomTrack.PaidFormOpenFromPNL();

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

    //region IFragmentTag
    @Override
    public View.OnClickListener onClickToTag()
    {
        return view ->
        {
            if (getActivity() != null)
                ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_tag,
                        R.id.navigation_money_accounting);
        };
    }
    //endregion

    //region IFragmentSettings
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            new FragmentMoneyTransactionSettings(
                    (AppCompatActivity) getContext(),
                    globalVariable.get_moneyTransactionSettings().Clone(),
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
                new FragmentMoneyTransactionLegendToChangePopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

}