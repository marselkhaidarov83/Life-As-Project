package com.MH83.LifeAsProject.view.Money.MoneyCapital;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyLotAssetAdapter;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerPeriodAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeView;
import com.MH83.LifeAsProject.data.Money.MoneyLotAssetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.ICollapseAllAdapter;
import com.MH83.LifeAsProject.interfac.adapter.IExpandAllAdapter;
import com.MH83.LifeAsProject.interfac.enums.ETypeCapitalData;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAsset;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCollapseAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentExpandAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentPortfolio;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowPercent;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyLotAsset;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperation;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetSettings;
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

public class FragmentMoneyCapital extends CustomFragment
    implements IFragmentPortfolio, IFragmentAsset, IFragmentSettings, IFragmentViewCustom,
        IFragmentExpandAll, IFragmentCollapseAll, IFragmentAddItem,
        IFragmentShowPercent
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_capital;
    //endregion

    //region Fragment parameters
    private final FragmentMoneyCapital _this;
    private CustomPeriodClass _prevCustomPeriodClass;
    //endregion

    //region Fragment Components
    private TreeView treeViewCapital;
    private ImageView _imageViewDateBack, _imageViewDateForward;
    private TextView _textViewSumStart, _textViewSumEnd, _textViewSumThisPeriodPlus,
            _textViewSumThisPeriodMinus, _textViewDifference, _textViewDifferenceTotal;
    private Spinner _spinnerDate;
    //endregion

    //region Constructors
    public FragmentMoneyCapital()
    {
        _this = this;
    }
    //endregion

    //region IFragmentPortfolio
    @Override
    public View.OnClickListener onClickToPortfolio()
    {
        return v ->
                ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_portfolio);
    }
    //endregion

    //region IFragmentAsset
    @Override
    public View.OnClickListener onClickToAsset()
    {
        return v ->
                ((MainActivity) getActivity()).NavigationItemSelected(R.id.navigation_money_asset);
    }
    //endregion

    //region IFragmentSettings
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            new FragmentMoneyCapitalSettings(
                    (AppCompatActivity) getContext(),
                    globalVariable.get_moneyLotAssetSettings().Clone(),
                    this).PopupShow();
        };
    }

    @Override
    public boolean ShowFormSettingButton() { return true; }
    //endregion

    //region Start Fragment
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        GlobalClass global = (GlobalClass) getActivity().getApplicationContext();
        if (global.get_hasUnpaidSubscription())
        {
            MoneyLotAssetSettings moneyLotAssetSettings =
                    global.get_moneyLotAssetSettings();

            moneyLotAssetSettings.set_typeAsset(0L);
            moneyLotAssetSettings.set_kindAsset(EnumKindAsset.Blank);
            moneyLotAssetSettings.set_typeCapitalData(ETypeCapitalData.Sum);
            moneyLotAssetSettings.set_groupPortfolioId(0L);
            moneyLotAssetSettings.set_portfolioId(0L);

            global.set_moneyLotAssetSettings(moneyLotAssetSettings);
        }

        View view = inflater.inflate(_layoutId, container, false);

        treeViewCapital = view.findViewById(R.id.tree_view);
        _spinnerDate = view.findViewById(R.id.spinnerDate);
        _imageViewDateBack = view.findViewById(R.id.imageViewDateBack);
        _imageViewDateForward = view.findViewById(R.id.imageViewDateForward);

        _textViewSumStart = view.findViewById(R.id.textViewSumStart);
        _textViewSumEnd = view.findViewById(R.id.textViewSumEnd);
        _textViewSumThisPeriodPlus = view.findViewById(R.id.textViewSumThisPeriodPlus);
        _textViewSumThisPeriodMinus = view.findViewById(R.id.textViewSumThisPeriodMinus);
        _textViewDifference = view.findViewById(R.id.textViewDifference);
        _textViewDifferenceTotal = view.findViewById(R.id.textViewDifferenceTotal);

        SetAdapters();
//        SetSummary();
        SetListeners();

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (MoneyLotAssetDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentMoneyCapital"))
        {
//            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyCapital");
        }

        return view;
    }

    private void SetAdapters()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        ArrayList<CustomPeriodClass> list = CustomPeriodClass.GetList(getContext());
        SpinnerPeriodAdapter adapter = new SpinnerPeriodAdapter(getContext(), list);
        _spinnerDate.setAdapter(adapter);
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).get_id() == globalVariable.get_capital_customPeriodClass().get_id())
            {
                _spinnerDate.setSelection(i);
                ((CustomPeriodClass) _spinnerDate.getSelectedItem())
                        .SetShift(globalVariable.get_capital_customPeriodClass().GetShift());
                break;
            }

        SetTreeViewAdapter();
    }

    private void SetTreeViewAdapter()
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.get_moneyLotAssetSettings().set_customPeriodClass(
                (CustomPeriodClass) _spinnerDate.getSelectedItem());

        MoneyLotAssetAdapter adapter = new MoneyLotAssetAdapter(getContext(),
                this,
                globalVariable.get_capital_customPeriodClass().get_end_dateC());
        treeViewCapital.setAdapter(adapter);

        SetSummary();
    }
    //endregion

    //region SetSummary
    private void SetSummary()
    {
        MoneyLotAsset moneyLotAsset = ((MoneyLotAssetAdapter) treeViewCapital.getAdapter()).getSummaryLotAsset();

        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        MoneyLotAssetSettings moneyLotAssetSettings = globalVariable.get_moneyLotAssetSettings();

        if (moneyLotAssetSettings.get_typeCapitalData().equals(ETypeCapitalData.Sum))
        {
            setSumStart(moneyLotAsset.get_calcSumStart(), moneyLotAsset.get_calcNKDStart());
            setSumThisPeriodPlus(moneyLotAsset.get_calcSumThisPeriodPlus(), moneyLotAsset.get_calcNKDThisPeriodPlus());
            setSumThisPeriodMinus(moneyLotAsset.get_calcSumThisPeriodMinus(), moneyLotAsset.get_calcNKDThisPeriodMinus());
            setSumEnd(moneyLotAsset.get_calcSumEnd(), moneyLotAsset.get_calcNKDEnd());
            setDifference(moneyLotAsset.get_typeMoneyLotAsset(), moneyLotAsset.get_calcDifference(),
                    moneyLotAsset.get_calcPayments());
            setDifferenceTotal(moneyLotAsset.get_typeMoneyLotAsset(), moneyLotAsset.get_calcDifferenceTotal());
        }
        else
        {
            setPercentStart(moneyLotAsset.get_calcSumStartPercent());
            setPercentEnd(moneyLotAsset.get_calcSumEndPercent());
            setSumThisPeriodPlus(null, null);
            setSumThisPeriodMinus(null, null);
            setProfitability(moneyLotAsset.get_typeMoneyLotAsset(), moneyLotAsset.get_calcProfitabilityThisPeriod());
            setProfitabilityTotal(moneyLotAsset.get_typeMoneyLotAsset(), moneyLotAsset.get_calcProfitabilityTotal());
        }
    }

    @SuppressLint("SetTextI18n")
    private void setLongToTextView(TextView textView, String s, Long sum, Long sum2, String s2)
    {
        String sum2S;
        if (sum2 == null || sum2 == 0L)
            sum2S = "";
        else if (Math.floorMod(sum2, 100L) == 0)
            sum2S = " (" + CustomStaticClass.getIntFormatSum((int) Math.floorDiv(sum2, 100L), false) + ")";
        else
            sum2S = " (" + CustomStaticClass.getFormatSum(sum2 / 100d, false) + ")";

        if (sum == 0L)
            textView.setText("");
        else if (Math.floorMod(sum, 100L) == 0)
        {
            textView.setText(s + CustomStaticClass.getIntFormatSum((int) Math.floorDiv(sum, 100L), false) +
                    sum2S + s2);
        }
        else
        {
            textView.setText(s + CustomStaticClass.getFormatSum(sum / 100d, false) +
                    sum2S + s2);
        }
    }
    @SuppressLint("SetTextI18n")
    private void setLongToTextViewWith0(TextView textView, String s, Long sum, String s2)
    {
        if (sum == 0L)
            textView.setText(s + "0" + s2);
        else if (Math.floorMod(sum, 100L) == 0)
        {
            textView.setText(s + CustomStaticClass.getIntFormatSum((int) Math.floorDiv(sum, 100L), false) + s2);
//                textView.setText(s + Math.floorDiv(sum, 100L) + s2);
        }
        else
        {
            textView.setText(s + CustomStaticClass.getFormatSum(sum / 100d, false) + s2);
//                textView.setText(s + sum / 100d + s2);
        }
    }

    private void setSumStart(Long sumStart, Long nkdStart)
    {
        if (_textViewSumStart != null)
        {
            if (sumStart != null)
                setLongToTextView(_textViewSumStart, "", sumStart, nkdStart, "");
            else
                _textViewSumStart.setText("");
        }
    }

    private void setSumThisPeriodPlus(Long sumThisPeriod, Long nkdThisPeriod)
    {
        if (_textViewSumThisPeriodPlus != null)
        {
            if (sumThisPeriod != null)
                setLongToTextView(_textViewSumThisPeriodPlus, "", sumThisPeriod, nkdThisPeriod, "");
            else
                _textViewSumThisPeriodPlus.setText("");
        }
    }

    private void setSumThisPeriodMinus(Long sumThisPeriod, Long nkdThisPeriod)
    {
        if (_textViewSumThisPeriodMinus != null)
        {
            if (sumThisPeriod != null)
                setLongToTextView(_textViewSumThisPeriodMinus, "", sumThisPeriod, nkdThisPeriod, "");
            else
                _textViewSumThisPeriodMinus.setText("");
        }
    }

    private void setSumEnd(Long sumEnd, Long sumNKDEnd)
    {
        if (_textViewSumEnd != null)
        {
            if (sumEnd != null)
                setLongToTextView(_textViewSumEnd, "", sumEnd, sumNKDEnd,"");
            else
                _textViewSumEnd.setText("");
        }
    }

    private void setDifference(MoneyLotAsset.EnumTypeMoneyLotAsset typeMoneyLotAsset,
                               Long difference, Long diffNKD)
    {
        if (_textViewDifference != null)
        {
            if (difference != null)
            {
                if (difference > 0L)
                {
                    if (typeMoneyLotAsset.equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                        setLongToTextViewWith0(_textViewDifference, "+", difference, "");
                    else
                        setLongToTextView(_textViewDifference, "+", difference, diffNKD,"");
                }
                else
                {
                    if (typeMoneyLotAsset.equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                        setLongToTextViewWith0(_textViewDifference, "", difference, "");
                    else
                        setLongToTextView(_textViewDifference, "", difference, diffNKD, "");
                }
            }
            else
                _textViewDifference.setText("");
        }
    }

    private void setDifferenceTotal(MoneyLotAsset.EnumTypeMoneyLotAsset typeMoneyLotAsset,
                                    Long difference)
    {
        if (_textViewDifferenceTotal != null)
        {
            if (difference != null)
            {
                if (difference > 0L)
                {
                    if (typeMoneyLotAsset.equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                        setLongToTextViewWith0(_textViewDifferenceTotal, "+", difference, "");
                    else
                        setLongToTextView(_textViewDifferenceTotal, "+", difference, null,"");
                }
                else
                {
                    if (typeMoneyLotAsset.equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                        setLongToTextViewWith0(_textViewDifferenceTotal, "", difference, "");
                    else
                        setLongToTextView(_textViewDifferenceTotal, "", difference, null, "");
                }
            }
            else
                _textViewDifferenceTotal.setText("");
        }
    }

    private void setPercentStart(Long percentStart)
    {
        if (_textViewSumStart != null)
        {
            if (percentStart != null)
                setLongToTextView(_textViewSumStart, "", percentStart, null, "%");
            else
                _textViewSumStart.setText("");
        }
    }

    private void setPercentEnd(Long percentEnd)
    {
        if (_textViewSumEnd != null)
        {
            if (percentEnd != null)
                setLongToTextView(_textViewSumEnd, "", percentEnd, null, "%");
            else
                _textViewSumEnd.setText("");
        }
    }

    private void setProfitability(MoneyLotAsset.EnumTypeMoneyLotAsset typeMoneyLotAsset, Long profitability)
    {
        if (_textViewDifference != null)
        {
            if (profitability != null)
            {
                if (profitability > 0L)
                {
                    if (typeMoneyLotAsset.equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                        setLongToTextViewWith0(_textViewDifference, "+", profitability, "%");
                    else
                        setLongToTextView(_textViewDifference, "+", profitability, null,"%");
                }
                else
                {
//                        if (typeMoneyLotAsset.equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                    setLongToTextViewWith0(_textViewDifference, "", profitability, "%");
//                        else
//                            setLongToTextView(_textViewDifference, "", profitability, null,"%");
                }
            }
            else
                _textViewDifference.setText("");
        }
    }
    private void setProfitabilityTotal(MoneyLotAsset.EnumTypeMoneyLotAsset typeMoneyLotAsset, Long profitability)
    {
        if (_textViewDifferenceTotal != null)
        {
            if (profitability != null)
            {
                if (profitability > 0L)
                {
                    if (typeMoneyLotAsset.equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                        setLongToTextViewWith0(_textViewDifferenceTotal, "+", profitability, "%");
                    else
                        setLongToTextView(_textViewDifferenceTotal, "+", profitability, null,"%");
                }
                else
                {
                    if (typeMoneyLotAsset.equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                        setLongToTextViewWith0(_textViewDifferenceTotal, "", profitability, "%");
                    else
                        setLongToTextView(_textViewDifferenceTotal, "", profitability, null,"%");
                }
            }
            else
                _textViewDifferenceTotal.setText("");
        }
    }
    //endregion

    //region SetListeners
    private void SetListeners()
    {
        _imageViewDateBack.setOnClickListener(view ->
        {
            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            ((CustomPeriodClass) _spinnerDate.getSelectedItem()).DecShift();
            globalVariable.set_capital_customPeriodClass(((CustomPeriodClass) _spinnerDate.getSelectedItem()));
            ((SpinnerPeriodAdapter) _spinnerDate.getAdapter()).notifyDataSetChanged();
            SetTreeViewAdapter();
        });

        _imageViewDateForward.setOnClickListener(view ->
        {
            GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            ((CustomPeriodClass) _spinnerDate.getSelectedItem()).IncShift();
            globalVariable.set_capital_customPeriodClass(((CustomPeriodClass) _spinnerDate.getSelectedItem()));
            ((SpinnerPeriodAdapter) _spinnerDate.getAdapter()).notifyDataSetChanged();
            SetTreeViewAdapter();
        });

        _spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (_prevCustomPeriodClass != null)
                    _prevCustomPeriodClass.ResetShift();
                _prevCustomPeriodClass = ((CustomPeriodClass) _spinnerDate.getSelectedItem());
                SetTreeViewAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyLotAssetOperation)
        {
            MoneyLotAssetOperation moneyLotAssetOperation = (MoneyLotAssetOperation) object;
            MoneyLotAsset moneyLotAsset = MoneyLotAssetDataSource.LoadData(
                    getContext(), false, null,
                    moneyLotAssetOperation.get_moneyLotAsset().get_id()).get(0);
            ((MoneyLotAssetAdapter) treeViewCapital.getAdapter()).AddItem(moneyLotAsset);
            SetSummary();

            return true;
        }
        else if (object instanceof MoneyLotAssetSettings)
        {
            MoneyLotAssetSettings moneyLotAssetSettings = (MoneyLotAssetSettings) object;
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.set_moneyLotAssetSettings(moneyLotAssetSettings);
            globalVar.SaveSharedPreferencesCapitalSettings();
            SetTreeViewAdapter();
//            SetSummary();

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyLotAsset)
        {
            ((MoneyLotAssetAdapter) treeViewCapital.getAdapter()).UpdateItem((MoneyLotAsset) object);
            SetSummary();

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyLotAsset)
        {
            ((MoneyLotAssetAdapter) treeViewCapital.getAdapter()).DeleteFromDataset((MoneyLotAsset) object);
            SetSummary();

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) {
        return false;
    }
    //endregion

    //region IFragmentCollapseAll
    @Override
    public View.OnClickListener onClickCollapseAll()
    {
        return view ->
        {
            ICollapseAllAdapter adapter = ((ICollapseAllAdapter) treeViewCapital.getAdapter());
            if (adapter != null)
                adapter.CollapseAll(true);
        };
    }

    @Override
    public boolean needCollapseButton(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();
        return !global.get_hasUnpaidSubscription();
    }
    //endregion

    //region IFragmentExpandAll
    @Override
    public View.OnClickListener onClickExpandAll() {
        return view ->
        {
            IExpandAllAdapter adapter = ((IExpandAllAdapter) treeViewCapital.getAdapter());
            if (adapter != null)
                adapter.ExpandAll(true);
        };
    }

    @Override
    public boolean needExpandButton(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();
        return !global.get_hasUnpaidSubscription();
    }
    //endregion

    //region IFragmentAddItem
    @Override
    public boolean ShowAddItemButton() {
        return true;
    }

    @Override
    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (!MoneyLotAssetOperation.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick();
                return;
            }

            new FragmentMoneyLotAssetOperationPopupNew((AppCompatActivity) getContext(),
                    null,
                    EnumTypeAction.Insert, null, this, null, true,
                    globalVariable.get_capital_customPeriodClass()).PopupShow();
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

            CustomTrack.PaidFormOpenFromMoneyLotAssetOperation();

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

                    CustomTrack.PaidFormOpenFromMoneyLotAssetOperation();

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

    //region IFragmentShowPercent
    @Override
    public boolean ShowPercent(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyLotAssetSettings().get_typeCapitalData().equals(ETypeCapitalData.Percent);
    }

    @Override
    public boolean HidePercent(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        return globalVar.get_moneyLotAssetSettings().get_typeCapitalData().equals(ETypeCapitalData.Sum);
    }

    @Override
    public View.OnClickListener onClickToShowPercent()
    {
        return v ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            MoneyLotAssetSettings moneyLotAssetSettings = globalVar.get_moneyLotAssetSettings();
            moneyLotAssetSettings.set_typeCapitalData(ETypeCapitalData.Sum);
            globalVar.set_moneyLotAssetSettings(moneyLotAssetSettings);
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
            SetTreeViewAdapter();
        };
    }

    @Override
    public View.OnClickListener onClickToHidePercent()
    {
        return v ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            MoneyLotAssetSettings moneyLotAssetSettings = globalVar.get_moneyLotAssetSettings();
            moneyLotAssetSettings.set_typeCapitalData(ETypeCapitalData.Percent);
            globalVar.set_moneyLotAssetSettings(moneyLotAssetSettings);
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
            SetTreeViewAdapter();
        };
    }
    //endregion
}
