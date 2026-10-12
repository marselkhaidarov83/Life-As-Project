package com.MH83.LifeAsProject.adapter.rView.money;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.TreeViewAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyLotAssetDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyTypeAssetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.interfac.enums.ETypeCapitalData;
import com.MH83.LifeAsProject.interfac.enums.EnumKindAsset;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyLotAsset;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperation;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetSettings;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAsset.FragmentMoneyAssetPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyCapital.FragmentMoneyLotAssetPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class MoneyLotAssetAdapter
        extends TreeViewAdapter<MoneyLotAssetAdapter.ItemViewHolder, MoneyLotAsset,
        FragmentMoneyAssetPopupNew>
        implements IAUDAdapter<MoneyLotAsset>
{
    //region Parameters
    private final Calendar _calendar;
    private Long _sumStartTotal = 0L;
    private Long _sumEndTotal = 0L;
    //endregion

    //region Constructor
    public MoneyLotAssetAdapter(Context context,
                                IFragmentViewCustom<MoneyLotAsset> fragmentViewCustom,
                                Calendar calendar)
    {
        super(context, null, fragmentViewCustom, R.layout.item_tree_money_lot_asset);

        _calendar = calendar;

        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        MoneyAssetSettings moneyAssetSettings = globalVariable.get_moneyAssetSettings();
        MoneyLotAssetSettings moneyLotAssetSettings = globalVariable.get_moneyLotAssetSettings();

        List<MoneyLotAsset> dataSetParent = new ArrayList<>();
        if (moneyLotAssetSettings.get_kindAsset() == null ||
                moneyLotAssetSettings.get_kindAsset() == EnumKindAsset.Blank)
        {
            dataSetParent.add(MoneyLotAsset.FromEnumKindAsset(_context, EnumKindAsset.Conservative));
            dataSetParent.add(MoneyLotAsset.FromEnumKindAsset(_context, EnumKindAsset.Moderate));
            dataSetParent.add(MoneyLotAsset.FromEnumKindAsset(_context, EnumKindAsset.Aggressive));
        }
        else
            dataSetParent.add(MoneyLotAsset.FromEnumKindAsset(_context, moneyLotAssetSettings.get_kindAsset()));

        ArrayList<Long> expandedId = null;
        if (moneyAssetSettings != null)
            expandedId = moneyAssetSettings.get_expandedList();

        TreeItem<MoneyLotAsset> root = new TreeItem<>(null, true);
        for (int i = 0; i < dataSetParent.size(); i++)
        {
            TreeItem<MoneyLotAsset> folderCategory = new TreeItem<>(dataSetParent.get(i), true);
            if (expandedId != null && expandedId.size() > 0)
                folderCategory.setExpanded(expandedId.contains(dataSetParent.get(i).get_id()));
            root.getChildren().add(folderCategory);

            MoneyLotAssetSettings moneyLotAssetSettings1 = moneyLotAssetSettings.Clone();
            moneyLotAssetSettings1.set_kindAsset(EnumKindAsset.valueOf(dataSetParent.get(i).get_comment()));
            List<MoneyLotAsset> dataSet3 = MoneyLotAssetDataSource.LoadData(
                    _context, false, moneyLotAssetSettings1, null);

            for (int j = 0; j < dataSet3.size(); j++)
            {
                TreeItem<MoneyLotAsset> singleCategory2 = null;
                for (TreeItem<MoneyLotAsset> item: folderCategory.getChildren())
                    if (Objects.equals(item.get_object().get_id(),
                            dataSet3.get(j).get_moneyAsset().get_moneyTypeAssetId()))
                    {
                        singleCategory2 = item;
                        break;
                    }

                if (singleCategory2 != null)
                    singleCategory2.setExpandable(true);
                else
                {
                    singleCategory2 = new TreeItem<>(MoneyTypeAssetDataSource.GetMoneyLotAssetFromTypeAssetId(
                            _context, dataSet3.get(j).get_moneyAsset().get_moneyTypeAssetId()),
                            true);
                    if (expandedId != null && expandedId.size() > 0)
                        singleCategory2.setExpanded(expandedId.contains(dataSet3.get(j).get_moneyAsset().get_moneyTypeAssetId()));
                    folderCategory.getChildren().add(singleCategory2);
                    (folderCategory.get_object()).set_hasChild(true);
                }

                TreeItem<MoneyLotAsset> singleCategory3 = new TreeItem<>(dataSet3.get(j), false);
                singleCategory2.getChildren().add(singleCategory3);
                (singleCategory2.get_object()).set_hasChild(true);

                dataSet3.get(j).RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(),
                        _context,
                        null,
                        null);
                singleCategory2.get_object().add_calcCountStart(dataSet3.get(j).get_calcCountStart());
                singleCategory2.get_object().add_calcSumStart(dataSet3.get(j).get_calcSumStart());
                singleCategory2.get_object().add_calcBaseSumPlus(dataSet3.get(j).get_calcBaseSumPlus(_context,
                        dataSet3.get(j).get_moneyAsset().get_kindAsset()));
                singleCategory2.get_object().add_calcNKDStart(dataSet3.get(j).get_calcNKDStart());
                singleCategory2.get_object().add_calcCountThisPeriodPlus(dataSet3.get(j).get_calcCountThisPeriodPlus());
                singleCategory2.get_object().add_calcSumThisPeriodPlus(dataSet3.get(j).get_calcSumThisPeriodPlus());
                singleCategory2.get_object().add_calcNKDThisPeriodPlus(dataSet3.get(j).get_calcNKDThisPeriodPlus());
                singleCategory2.get_object().add_calcCountThisPeriodMinus(dataSet3.get(j).get_calcCountThisPeriodMinus());
                singleCategory2.get_object().add_calcSumThisPeriodMinus(dataSet3.get(j).get_calcSumThisPeriodMinus());
                singleCategory2.get_object().add_calcNKDThisPeriodMinus(dataSet3.get(j).get_calcNKDThisPeriodMinus());
                singleCategory2.get_object().add_calcSumEnd(dataSet3.get(j).get_calcSumEnd());
                singleCategory2.get_object().add_calcNKDEnd(dataSet3.get(j).get_calcNKDEnd());
                singleCategory2.get_object().add_calcPayments(dataSet3.get(j).get_calcPayments());
                singleCategory2.get_object().add_calcDifference(dataSet3.get(j).get_calcDifference());
                singleCategory2.get_object().add_calcDifferenceTotal(dataSet3.get(j).get_calcDifferenceTotal());
            }
        }

        for (TreeItem<MoneyLotAsset> folderCategory: root.getChildren())
        {
            for (TreeItem<MoneyLotAsset> singleCategory2 : folderCategory.getChildren())
            {
                folderCategory.get_object().add_calcCountStart(singleCategory2.get_object().get_calcCountStart());
                folderCategory.get_object().add_calcSumStart(singleCategory2.get_object().get_calcSumStart());
                EnumKindAsset kindAsset = EnumKindAsset.Conservative;
                if (singleCategory2.getChildren().size() > 0)
                    kindAsset = singleCategory2.getChildren().get(0).get_object().get_moneyAsset().get_kindAsset();
                folderCategory.get_object().add_calcBaseSumPlus(singleCategory2.get_object().get_calcBaseSumPlus(_context, kindAsset));
                folderCategory.get_object().add_calcNKDStart(singleCategory2.get_object().get_calcNKDStart());
                folderCategory.get_object().add_calcCountThisPeriodPlus(singleCategory2.get_object().get_calcCountThisPeriodPlus());
                folderCategory.get_object().add_calcSumThisPeriodPlus(singleCategory2.get_object().get_calcSumThisPeriodPlus());
                folderCategory.get_object().add_calcNKDThisPeriodPlus(singleCategory2.get_object().get_calcNKDThisPeriodPlus());
                folderCategory.get_object().add_calcCountThisPeriodMinus(singleCategory2.get_object().get_calcCountThisPeriodMinus());
                folderCategory.get_object().add_calcSumThisPeriodMinus(singleCategory2.get_object().get_calcSumThisPeriodMinus());
                folderCategory.get_object().add_calcNKDThisPeriodMinus(singleCategory2.get_object().get_calcNKDThisPeriodMinus());
                folderCategory.get_object().add_calcSumEnd(singleCategory2.get_object().get_calcSumEnd());
                folderCategory.get_object().add_calcNKDEnd(singleCategory2.get_object().get_calcNKDEnd());
                folderCategory.get_object().add_calcPayments(singleCategory2.get_object().get_calcPayments());
                folderCategory.get_object().add_calcDifference(singleCategory2.get_object().get_calcDifference());
                folderCategory.get_object().add_calcDifferenceTotal(singleCategory2.get_object().get_calcDifferenceTotal());

                for (TreeItem<MoneyLotAsset> singleCategory3 : singleCategory2.getChildren())
                    singleCategory3.get_object().RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(),
                            _context,
                            singleCategory2.get_object().get_calcSumStart(),
                            singleCategory2.get_object().get_calcSumEnd());
            }

            if (folderCategory.get_object().get_calcSumStart() != null)
                _sumStartTotal += folderCategory.get_object().get_calcSumStart();
            if (folderCategory.get_object().get_calcSumEnd() != null)
                _sumEndTotal += folderCategory.get_object().get_calcSumEnd();

            for (TreeItem<MoneyLotAsset> singleCategory2 : folderCategory.getChildren())
                singleCategory2.get_object().RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(),
                        _context,
                        folderCategory.get_object().get_calcSumStart(),
                        folderCategory.get_object().get_calcSumEnd());
        }
        for (TreeItem<MoneyLotAsset> folderCategory: root.getChildren())
            folderCategory.get_object().RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(),
                    _context,
                    _sumStartTotal,
                    _sumEndTotal);

        _setPadding = true;
        setRoot(root);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(View view)
    {
        return new ItemViewHolder(view, _context);
    }
    //endregion

    //region onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        super.onBindViewHolder(holder, position);
        TreeItem<MoneyLotAsset> moneyLotAssetTreeItem = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = moneyLotAssetTreeItem;
            if (_currentItem.get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.Lot) ||
                    _currentItem.get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet) ||
                    _currentItem.get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotCredit))
                new FragmentMoneyLotAssetPopupNew((AppCompatActivity) _context,
                        _currentItem.get_object(), EnumTypeAction.Update, _fragmentViewCustom,
                        _calendar).PopupShow();
        });
    }
    //endregion

    //region Expand, Collapse
    public void SaveExpandedList()
    {
        ArrayList<Long> list = new ArrayList<>();

        for (TreeItem<MoneyLotAsset> moneyLotAssetTreeItem : _dataSet)
            if (
//                    moneyLotAssetTreeItem.get_object().get_moneyTypeAssetId() == 0L &&
                    moneyLotAssetTreeItem.isExpanded())
                list.add(moneyLotAssetTreeItem.get_object().get_id());

        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        globalVariable.get_moneyAssetSettings().set_expandedList(list);
    }

    @Override
    protected void ClickToLeadingIcon(TreeItem<MoneyLotAsset> item,
                                      @androidx.annotation.NonNull MoneyLotAssetAdapter.ItemViewHolder holder)
    {
        super.ClickToLeadingIcon(item, holder);

        SaveExpandedList();
    }

    @Override
    public void ExpandAll(boolean showExpandedButton)
    {
        super.ExpandAll(showExpandedButton);

        SaveExpandedList();
    }

    @Override
    public void CollapseAll(boolean showExpandedButton)
    {
        super.CollapseAllFromEnd(showExpandedButton);

        SaveExpandedList();
    }
    //endregion

    //region Simple Callback
    @Override
    public boolean canActionRightImage()
    {
        return _currentItem.get_object().canDelete(_context);
    }

    @Override
    public boolean canActionLeftImage() { return true; }

    @Override
    public boolean LeftButtonClick() { return false; }

    @Override
    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }
    //endregion

    //region Subscript
    protected void SubscriptionClick()
    {
        if (GlobalClass.SubscrGoogle())
            SubscriptGoogle();

        if (GlobalClass.SubscrRustore())
            SubscriptRustore();
    }

    private void SubscriptRustore()
    {
        RuStoreBillingClient billingClient = CustomSubscription.GetBillingClientRustore(_context);

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
                    (AppCompatActivity) _context, null, EnumTypeAction.Insert, _fragmentViewCustom,
                    cBillingProducts);
            popupNew.PopupShow();
        });

        tList.addOnFailureListener(throwable ->
        {
            Toast.makeText(_context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Billing: getting products", throwable.getMessage());
        });
    }

    private void SubscriptGoogle()
    {
        BillingClient.Builder builder = BillingClient.newBuilder(_context);
        builder.setListener((billingResult, list) -> {});
        builder.enablePendingPurchases();
        BillingClient billingClient = builder.build();

        billingClient.startConnection(new BillingClientStateListener()
        {
            @Override
            public void onBillingSetupFinished(@androidx.annotation.NonNull BillingResult billingResult)
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
                            (AppCompatActivity) _context, null, EnumTypeAction.Insert, _fragmentViewCustom,
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

    //region Add, Update, Delete item
    private void CalcTypeAssetNode(int position, GlobalClass globalVariable)
    {
        _dataSet.get(position).get_object().InitialCalcData();
        for (int p = position + 1; p <= _dataSet.size(); p++)
            if (_dataSet.get(p).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset) ||
                    _dataSet.get(p).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset))
                break;
            else
            {
                _dataSet.get(position).get_object().add_calcCountStart(_dataSet.get(p).get_object().get_calcCountStart());
                _dataSet.get(position).get_object().add_calcSumStart(_dataSet.get(p).get_object().get_calcSumStart());
                _dataSet.get(position).get_object().add_calcBaseSumPlus(_dataSet.get(p).get_object().get_calcBaseSumPlus(_context,
                        _dataSet.get(p).get_object().get_moneyAsset().get_kindAsset()));
                _dataSet.get(position).get_object().add_calcNKDStart(_dataSet.get(p).get_object().get_calcNKDStart());
                _dataSet.get(position).get_object().add_calcCountThisPeriodPlus(_dataSet.get(p).get_object().get_calcCountThisPeriodPlus());
                _dataSet.get(position).get_object().add_calcSumThisPeriodPlus(_dataSet.get(p).get_object().get_calcSumThisPeriodPlus());
                _dataSet.get(position).get_object().add_calcNKDThisPeriodPlus(_dataSet.get(p).get_object().get_calcNKDThisPeriodPlus());
                _dataSet.get(position).get_object().add_calcCountThisPeriodMinus(_dataSet.get(p).get_object().get_calcCountThisPeriodMinus());
                _dataSet.get(position).get_object().add_calcSumThisPeriodMinus(_dataSet.get(p).get_object().get_calcSumThisPeriodMinus());
                _dataSet.get(position).get_object().add_calcNKDThisPeriodMinus(_dataSet.get(p).get_object().get_calcNKDThisPeriodMinus());
                _dataSet.get(position).get_object().add_calcSumEnd(_dataSet.get(p).get_object().get_calcSumEnd());
                _dataSet.get(position).get_object().add_calcNKDEnd(_dataSet.get(p).get_object().get_calcNKDEnd());
                _dataSet.get(position).get_object().add_calcPayments(_dataSet.get(p).get_object().get_calcPayments());
                _dataSet.get(position).get_object().add_calcDifference(_dataSet.get(p).get_object().get_calcDifference());
                _dataSet.get(position).get_object().add_calcDifferenceTotal(_dataSet.get(p).get_object().get_calcDifferenceTotal());
            }
        _dataSet.get(position).get_object().RecalcCalcData(globalVariable.get_capital_customPeriodClass(),
                _context,
                _dataSet.get(position).getParent().get_object().get_calcSumStart(),
                _dataSet.get(position).getParent().get_object().get_calcSumEnd());
        notifyItemChanged(position);
    }

    private void CalcTypeAssetNodeIfNotExpanded(int position, GlobalClass globalVariable)
    {
        CalcTypeAssetNodeIfNotExpanded(_dataSet.get(position), globalVariable);
        notifyItemChanged(position);
    }

    private void CalcTypeAssetNodeIfNotExpanded(TreeItem<MoneyLotAsset> moneyLotAssetTreeItem, GlobalClass globalVariable)
    {
        moneyLotAssetTreeItem.get_object().InitialCalcData();
        for (TreeItem<MoneyLotAsset> item : moneyLotAssetTreeItem.getChildren())
        {
            moneyLotAssetTreeItem.get_object().add_calcCountStart(item.get_object().get_calcCountStart());
            moneyLotAssetTreeItem.get_object().add_calcSumStart(item.get_object().get_calcSumStart());
            moneyLotAssetTreeItem.get_object().add_calcBaseSumPlus(item.get_object().get_calcBaseSumPlus(_context,
                    item.get_object().get_moneyAsset().get_kindAsset()));
            moneyLotAssetTreeItem.get_object().add_calcNKDStart(item.get_object().get_calcNKDStart());
            moneyLotAssetTreeItem.get_object().add_calcCountThisPeriodPlus(item.get_object().get_calcCountThisPeriodPlus());
            moneyLotAssetTreeItem.get_object().add_calcSumThisPeriodPlus(item.get_object().get_calcSumThisPeriodPlus());
            moneyLotAssetTreeItem.get_object().add_calcNKDThisPeriodPlus(item.get_object().get_calcNKDThisPeriodPlus());
            moneyLotAssetTreeItem.get_object().add_calcCountThisPeriodMinus(item.get_object().get_calcCountThisPeriodMinus());
            moneyLotAssetTreeItem.get_object().add_calcSumThisPeriodMinus(item.get_object().get_calcSumThisPeriodMinus());
            moneyLotAssetTreeItem.get_object().add_calcNKDThisPeriodMinus(item.get_object().get_calcNKDThisPeriodMinus());
            moneyLotAssetTreeItem.get_object().add_calcSumEnd(item.get_object().get_calcSumEnd());
            moneyLotAssetTreeItem.get_object().add_calcNKDEnd(item.get_object().get_calcNKDEnd());
            moneyLotAssetTreeItem.get_object().add_calcPayments(item.get_object().get_calcPayments());
            moneyLotAssetTreeItem.get_object().add_calcDifference(item.get_object().get_calcDifference());
            moneyLotAssetTreeItem.get_object().add_calcDifferenceTotal(item.get_object().get_calcDifferenceTotal());
        }

        moneyLotAssetTreeItem.get_object().RecalcCalcData(globalVariable.get_capital_customPeriodClass(),
                _context,
                moneyLotAssetTreeItem.getParent().get_object().get_calcSumStart(),
                moneyLotAssetTreeItem.getParent().get_object().get_calcSumEnd());
    }

    private void CalcKindAssetNode(int position, GlobalClass globalVariable)
    {
        _dataSet.get(position).get_object().InitialCalcData();
        for (int p = position + 1; p <= _dataSet.size(); p++)
            if (_dataSet.get(p).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset))
                break;
            else if (_dataSet.get(p).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
            {
                _dataSet.get(position).get_object().add_calcCountStart(_dataSet.get(p).get_object().get_calcCountStart());
                _dataSet.get(position).get_object().add_calcSumStart(_dataSet.get(p).get_object().get_calcSumStart());
                _dataSet.get(position).get_object().add_calcBaseSumPlus(_dataSet.get(p).get_object().get_calcBaseSumPlus(_context,
                        EnumKindAsset.valueOf(_dataSet.get(position).get_object().get_comment())));
                _dataSet.get(position).get_object().add_calcNKDStart(_dataSet.get(p).get_object().get_calcNKDStart());
                _dataSet.get(position).get_object().add_calcCountThisPeriodPlus(_dataSet.get(p).get_object().get_calcCountThisPeriodPlus());
                _dataSet.get(position).get_object().add_calcSumThisPeriodPlus(_dataSet.get(p).get_object().get_calcSumThisPeriodPlus());
                _dataSet.get(position).get_object().add_calcNKDThisPeriodPlus(_dataSet.get(p).get_object().get_calcNKDThisPeriodPlus());
                _dataSet.get(position).get_object().add_calcCountThisPeriodMinus(_dataSet.get(p).get_object().get_calcCountThisPeriodMinus());
                _dataSet.get(position).get_object().add_calcSumThisPeriodMinus(_dataSet.get(p).get_object().get_calcSumThisPeriodMinus());
                _dataSet.get(position).get_object().add_calcNKDThisPeriodMinus(_dataSet.get(p).get_object().get_calcNKDThisPeriodMinus());
                _dataSet.get(position).get_object().add_calcSumEnd(_dataSet.get(p).get_object().get_calcSumEnd());
                _dataSet.get(position).get_object().add_calcNKDEnd(_dataSet.get(p).get_object().get_calcNKDEnd());
                _dataSet.get(position).get_object().add_calcPayments(_dataSet.get(p).get_object().get_calcPayments());
                _dataSet.get(position).get_object().add_calcDifference(_dataSet.get(p).get_object().get_calcDifference());
                _dataSet.get(position).get_object().add_calcDifferenceTotal(_dataSet.get(p).get_object().get_calcDifferenceTotal());
            }
        _dataSet.get(position).get_object().RecalcCalcData(globalVariable.get_capital_customPeriodClass(),
                _context,
                _sumStartTotal,
                _sumEndTotal);
        notifyItemChanged(position);
    }

    private void CalcKindAssetNodeIfNotExpanded(int position, GlobalClass globalVariable)
    {
        CalcKindAssetNodeIfNotExpanded(_dataSet.get(position), globalVariable);
        notifyItemChanged(position);
    }

    private void CalcKindAssetNodeIfNotExpanded(TreeItem<MoneyLotAsset> moneyLotAssetTreeItem, GlobalClass globalVariable)
    {
        moneyLotAssetTreeItem.get_object().InitialCalcData();
        for (TreeItem<MoneyLotAsset> item : moneyLotAssetTreeItem.getChildren())
        {
            moneyLotAssetTreeItem.get_object().add_calcCountStart(item.get_object().get_calcCountStart());
            moneyLotAssetTreeItem.get_object().add_calcSumStart(item.get_object().get_calcSumStart());
            moneyLotAssetTreeItem.get_object().add_calcBaseSumPlus(item.get_object().get_calcBaseSumPlus(_context,
                    EnumKindAsset.valueOf(moneyLotAssetTreeItem.get_object().get_comment())));
            moneyLotAssetTreeItem.get_object().add_calcNKDStart(item.get_object().get_calcNKDStart());
            moneyLotAssetTreeItem.get_object().add_calcCountThisPeriodPlus(item.get_object().get_calcCountThisPeriodPlus());
            moneyLotAssetTreeItem.get_object().add_calcSumThisPeriodPlus(item.get_object().get_calcSumThisPeriodPlus());
            moneyLotAssetTreeItem.get_object().add_calcNKDThisPeriodPlus(item.get_object().get_calcNKDThisPeriodPlus());
            moneyLotAssetTreeItem.get_object().add_calcCountThisPeriodMinus(item.get_object().get_calcCountThisPeriodMinus());
            moneyLotAssetTreeItem.get_object().add_calcSumThisPeriodMinus(item.get_object().get_calcSumThisPeriodMinus());
            moneyLotAssetTreeItem.get_object().add_calcNKDThisPeriodMinus(item.get_object().get_calcNKDThisPeriodMinus());
            moneyLotAssetTreeItem.get_object().add_calcSumEnd(item.get_object().get_calcSumEnd());
            moneyLotAssetTreeItem.get_object().add_calcNKDEnd(item.get_object().get_calcNKDEnd());
            moneyLotAssetTreeItem.get_object().add_calcPayments(item.get_object().get_calcPayments());
            moneyLotAssetTreeItem.get_object().add_calcDifference(item.get_object().get_calcDifference());
            moneyLotAssetTreeItem.get_object().add_calcDifferenceTotal(item.get_object().get_calcDifferenceTotal());
        }

        moneyLotAssetTreeItem.get_object().RecalcCalcData(globalVariable.get_capital_customPeriodClass(),
                _context,
                _sumStartTotal,
                _sumEndTotal);
    }

    public Long AddItem(MoneyLotAsset moneyLotAssetNew)
    {
        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        MoneyLotAssetSettings moneyLotAssetSettings = globalVariable.get_moneyLotAssetSettings();
        moneyLotAssetNew.RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(), _context, null, null);
        int insertPosition = 0;

        //region Add data
        //Expand Kind Asset Node
        int k = 0;
        for (int i = k; i < _dataSet.size(); i++)
            if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset) &&
                _dataSet.get(i).get_object().get_comment().equals(moneyLotAssetNew.get_moneyAsset().get_kindAsset().toString()))
            {
                if (!_dataSet.get(i).isExpanded())
                {
                    _dataSet.get(i).setExpanded(true);
                    notifyItemChanged(i);
                    insertAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                    notifyItemRangeInserted(i + 1, _dataSet.get(i).getChildren().size());
                }
                _dataSet.get(i).get_object().set_hasChild(true);

                k = i + 1;
                break;
            }

        //Expand Type Asset Node
        boolean findParent = false;
        for (int i = k; i < _dataSet.size(); i++)
            if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset))
                break;
            else if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset) &&
                    _dataSet.get(i).getSValue().equals(moneyLotAssetNew.get_moneyAsset().get_moneyTypeAssetName()))
            {
                if (!_dataSet.get(i).isExpanded())
                {
                    _dataSet.get(i).setExpanded(true);
                    notifyItemChanged(i);
                    insertAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                    notifyItemRangeInserted(i + 1, _dataSet.get(i).getChildren().size());
                }
                _dataSet.get(i).get_object().set_hasChild(true);

                TreeItem<MoneyLotAsset> singleCategory3 = new TreeItem<>(moneyLotAssetNew, false);
                insertPosition = addLevel4ToParent(singleCategory3, i);

                findParent = true;
                break;
            }

        //Create Type Asset Node, if not exist
        if (!findParent)
        {
            k--;
            TreeItem<MoneyLotAsset> singleCategory2 = new TreeItem<>(
                    MoneyTypeAssetDataSource.GetMoneyLotAssetFromTypeAssetId(
                    _context, moneyLotAssetNew.get_moneyAsset().get_moneyTypeAssetId()),
                    true);

            TreeItem<MoneyLotAsset> singleCategory3 = new TreeItem<>(moneyLotAssetNew, false);

            _dataSet.get(k).get_object().set_hasChild(true);
            singleCategory2.get_object().set_parentId(_dataSet.get(k).get_object().get_id());

            if (!_dataSet.get(k).isExpanded())
                _dataSet.get(k).setExpanded(true);

            singleCategory2.setExpanded(true);

            int kk = _dataSet.size();
            for (int i = k + 1; i < _dataSet.size() ; i++)
                if ((_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset) ||
                        _dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset)) &&
                        !Objects.equals(_dataSet.get(i).get_object().getSValue(), _context.getString(R.string.money_category_wallet)))
                {
                    kk = i;
                    break;
                }


            _dataSet.get(k).getChildren().add(singleCategory2);
            _dataSet.add(kk, singleCategory2);
            singleCategory2.getChildren().add(singleCategory3);
            _dataSet.add(kk + 1, singleCategory3);

            notifyItemRangeInserted(kk, 2);
            insertPosition = kk + 1;
        }

        // Обсчет typeAsset
        for (int i = insertPosition - 1; i >= 0; i--)
            if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset) &&
                    _dataSet.get(i).getSValue().equals(moneyLotAssetNew.get_moneyAsset().get_moneyTypeAssetName()))
            {
                CalcTypeAssetNode(i, globalVariable);
                moneyLotAssetNew.RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(), _context,
                        _dataSet.get(i).get_object().get_calcSumStart(),
                        _dataSet.get(i).get_object().get_calcSumEnd());
                break;
            }
        //endregion

        //region Total
        if (moneyLotAssetNew.get_calcSumStart() != null)
            _sumStartTotal += moneyLotAssetNew.get_calcSumStart();
        if (moneyLotAssetNew.get_calcSumEnd() != null)
            _sumEndTotal += moneyLotAssetNew.get_calcSumEnd();
        //endregion

        //region AddToWallet
        int walletsNodeId = 0;
        if (moneyLotAssetNew.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.Lot))
        {
            boolean lotWalletFinded = false;
            for (int i = 0; i < _dataSet.size(); i++)
                if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                    if (Objects.equals(_dataSet.get(i).get_object().get_moneyAsset().get_wallet_id(),
                            moneyLotAssetNew.get_portfolioWalletId()))
                    {
                        for (MoneyLotAssetOperation item : moneyLotAssetNew.get_lotAssetOperations())
                        {
                            boolean added = false;
                            for (int t = 0; t < _dataSet.get(i).get_object().get_lotAssetOperations().size(); t++)
                                if (item.get_calendar().getTime().getTime() <
                                        _dataSet.get(i).get_object().get_lotAssetOperations().get(t).get_calendar().getTime().getTime()) {
                                    _dataSet.get(i).get_object().get_lotAssetOperations().add(t, item);
                                    added = true;
                                    break;
                                }

                            if (!added)
                                _dataSet.get(i).get_object().get_lotAssetOperations().add(0, item);
                        }

                        if (_dataSet.get(i).get_object().get_calcSumStart() != null)
                            _sumStartTotal -= _dataSet.get(i).get_object().get_calcSumStart();
                        if (_dataSet.get(i).get_object().get_calcSumEnd() != null)
                            _sumEndTotal -= _dataSet.get(i).get_object().get_calcSumEnd();

                        _dataSet.get(i).get_object().RecalcCalcData(globalVariable.get_capital_customPeriodClass(),
                                _context, _dataSet.get(i).getParent().get_object().get_calcSumStart(),
                                _dataSet.get(i).getParent().get_object().get_calcSumEnd());

                        if (_dataSet.get(i).get_object().get_calcSumStart() != null)
                            _sumStartTotal += _dataSet.get(i).get_object().get_calcSumStart();
                        if (_dataSet.get(i).get_object().get_calcSumEnd() != null)
                            _sumEndTotal += _dataSet.get(i).get_object().get_calcSumEnd();

                        notifyItemChanged(i);

                        // Обсчет typeAsset
                        for (int t = i - 1; t >= 0; t--)
                            if (_dataSet.get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset) &&
                                    _dataSet.get(t).getSValue().equals(_dataSet.get(i).get_object().get_moneyAsset().get_moneyTypeAssetName()))
                            {
                                walletsNodeId = t;
                                CalcTypeAssetNode(t, globalVariable);
                                break;
                            }

                        lotWalletFinded = true;
                        break;
                    }

            boolean walletsTypeAssetFinded = true;
            if (!lotWalletFinded)
            {
                walletsTypeAssetFinded = false;
                for (int t = 0; t < _dataSet.size(); t++)
                    if (_dataSet.get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
                        if (_dataSet.get(t).getSValue().equals(_context.getString(R.string.money_category_wallet)))
                        {
                            walletsNodeId = t;
                            for (TreeItem<MoneyLotAsset> item : _dataSet.get(t).getChildren())
                                if (Objects.equals(item.get_object().get_moneyAsset().get_wallet_id(),
                                        moneyLotAssetNew.get_portfolioWalletId()))
                                {
                                    for (MoneyLotAssetOperation moneyLotAssetOperation : moneyLotAssetNew.get_lotAssetOperations())
                                    {
                                        boolean added = false;
                                        for (int m = 0; m < item.get_object().get_lotAssetOperations().size(); m++)
                                            if (moneyLotAssetOperation.get_calendar().getTime().getTime() <
                                                    item.get_object().get_lotAssetOperations().get(m).get_calendar().getTime().getTime())
                                            {
                                                item.get_object().get_lotAssetOperations().add(m, moneyLotAssetOperation);
                                                added = true;
                                                break;
                                            }

                                        if (!added)
                                            item.get_object().get_lotAssetOperations().add(0, moneyLotAssetOperation);
                                    }

                                    if (item.get_object().get_calcSumStart() != null)
                                        _sumStartTotal -= item.get_object().get_calcSumStart();
                                    if (item.get_object().get_calcSumEnd() != null)
                                        _sumEndTotal -= item.get_object().get_calcSumEnd();

                                    item.get_object().RecalcCalcData(
                                            globalVariable.get_capital_customPeriodClass(), _context,
                                            item.getParent().get_object().get_calcSumStart(),
                                            item.getParent().get_object().get_calcSumEnd());

                                    if (item.get_object().get_calcSumStart() != null)
                                        _sumStartTotal += item.get_object().get_calcSumStart();
                                    if (item.get_object().get_calcSumEnd() != null)
                                        _sumEndTotal += item.get_object().get_calcSumEnd();
                                }

                            CalcTypeAssetNodeIfNotExpanded(t, globalVariable);
                            walletsTypeAssetFinded = true;
                            break;
                        }
            }

            if (!walletsTypeAssetFinded)
                for (int i = 0; i < _dataSet.size(); i++)
                    if (_dataSet.get(i).get_object().get_name().equals(EnumKindAsset.Conservative.toString()))
                    {
                        for (int t = 0; t < _dataSet.get(i).getChildren().size(); t++)
                            if (_dataSet.get(i).getChildren().get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
                                if (_dataSet.get(i).getChildren().get(t).getSValue().equals(_context.getString(R.string.money_category_wallet)))
                                {
                                    TreeItem<MoneyLotAsset> moneyLotAssetWalletsTypeAsset = _dataSet.get(i).getChildren().get(t);
                                    for (int m = 0; m < moneyLotAssetWalletsTypeAsset.getChildren().size(); m++)
                                        if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                                            if (Objects.equals(moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_moneyAsset().get_wallet_id(),
                                                    moneyLotAssetNew.get_portfolioWalletId()))
                                                {
                                                    if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart() != null)
                                                        _sumStartTotal -= moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart();
                                                    if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd() != null)
                                                        _sumEndTotal -= moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd();

                                                    MoneyLotAsset moneyLotAsset4Wallet = MoneyLotAssetDataSource.LoadData4WalletAsset(
                                                            _context,
                                                            moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_moneyAsset().get_wallet_id());
                                                    moneyLotAsset4Wallet.RecalcCalcData(globalVariable.get_capital_customPeriodClass(), _context,
                                                            null, null);

                                                    moneyLotAssetWalletsTypeAsset.getChildren().set(m, new TreeItem<>(moneyLotAsset4Wallet, false));

                                                    if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart() != null)
                                                        _sumStartTotal += moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart();
                                                    if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd() != null)
                                                        _sumEndTotal += moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd();

                                                    break;
                                                }
                                    CalcTypeAssetNodeIfNotExpanded(moneyLotAssetWalletsTypeAsset, globalVariable);
                                    break;
                                }

                        CalcKindAssetNodeIfNotExpanded(i, globalVariable);
                        walletsNodeId = -1;
                        break;
                    }
        }
        //endregion

        //region KindAsset
        for (int i = insertPosition - 1; i >= 0; i--)
            if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset) &&
                    _dataSet.get(i).get_object().get_comment().equals(moneyLotAssetNew.get_moneyAsset().get_kindAsset().toString()))
            {
                CalcKindAssetNode(i, globalVariable);
                break;
            }

        for (int i = walletsNodeId - 1; i >= 0; i--)
            if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset) &&
                    _dataSet.get(i).get_object().get_comment().equals(EnumKindAsset.Conservative.toString()))
            {
                CalcKindAssetNode(i, globalVariable);
                break;
            }
        //endregion

        SaveExpandedList();

        return (long) insertPosition;
    }

    public int addLevel4ToParent(TreeItem<MoneyLotAsset> item, int dataPos)
    {
        int kk = -1;
        _dataSet.get(dataPos).getChildren().add(item);

        if (!_dataSet.get(dataPos).isExpanded())
        {
            _dataSet.get(dataPos).setExpanded(true);
            notifyItemChanged(dataPos);
            insertAllExpandedItemRecursive(_dataSet.get(dataPos), dataPos + 1);
            notifyItemRangeInserted(dataPos + 1, _dataSet.get(dataPos).getChildren().size());
        }

        if (_dataSet.get(dataPos).isExpanded())
        {
            kk = _dataSet.size();
            for (int k = dataPos + 1; k < _dataSet.size(); k++)
                if (_dataSet.get(k).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset) ||
                        _dataSet.get(k).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset))
                {
                    kk = k;
                    break;
                }

            if (!_dataSet.contains(item))
            {
                _dataSet.add(kk, item);
                notifyItemInserted(kk);
            }
            else
            {
                kk = _dataSet.indexOf(item);
                notifyItemInserted(kk);
            }
        }

        return kk;
    }

    public Long UpdateItem(MoneyLotAsset moneyLotAssetNew)
    {
        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        MoneyLotAssetSettings moneyLotAssetSettings = globalVariable.get_moneyLotAssetSettings();
        moneyLotAssetNew.RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(), _context, null, null);

        MoneyLotAsset moneyLotAssetOld = null;
        int update_position = 0;
        for (int i = 0; i < _dataSet.size(); i++)
        {
            moneyLotAssetOld = _dataSet.get(i).get_object();
            if ((moneyLotAssetOld.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.Lot) ||
                    moneyLotAssetOld.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                    && Objects.equals(moneyLotAssetNew.get_id(), moneyLotAssetOld.get_id()))
            {
                update_position = i;
                _dataSet.get(i).set_object(moneyLotAssetNew);
                notifyItemChanged(i);

                for (int k = i - 1; k >= 0; k--)
                {
                    MoneyLotAsset moneyLotAsset2 = _dataSet.get(k).get_object();
                    if (moneyLotAsset2.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset) &&
                            _dataSet.get(k).getSValue().equals(moneyLotAssetNew.get_moneyAsset().get_moneyTypeAssetName()))
                    {
                        CalcTypeAssetNode(k, globalVariable);
                        notifyItemChanged(k);

                        break;
                    }
                }

                break;
            }
        }

        if (moneyLotAssetNew.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
        {
            for (int i = update_position - 1; i >= 0; i--)
                if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset) &&
                        _dataSet.get(i).get_object().get_comment().equals(moneyLotAssetNew.get_moneyAsset().get_kindAsset().toString()))
                {
                    //region Total
                    if (moneyLotAssetOld != null)
                    {
                        if (moneyLotAssetOld.get_calcSumStart() != null)
                            _sumStartTotal -= moneyLotAssetOld.get_calcSumStart();
                        if (moneyLotAssetOld.get_calcSumEnd() != null)
                            _sumEndTotal -= moneyLotAssetOld.get_calcSumEnd();
                    }
                    if (moneyLotAssetNew.get_calcSumStart() != null)
                        _sumStartTotal += moneyLotAssetNew.get_calcSumStart();
                    if (moneyLotAssetNew.get_calcSumEnd() != null)
                        _sumEndTotal += moneyLotAssetNew.get_calcSumEnd();
                    //endregion

                    CalcKindAssetNode(i, globalVariable);
                    break;
                }

            return moneyLotAssetNew.get_id();
        }

        //region Total
        if (moneyLotAssetOld != null)
        {
            if (moneyLotAssetOld.get_calcSumStart() != null)
                _sumStartTotal -= moneyLotAssetOld.get_calcSumStart();
            if (moneyLotAssetOld.get_calcSumEnd() != null)
                _sumEndTotal -= moneyLotAssetOld.get_calcSumEnd();
        }
        if (moneyLotAssetNew.get_calcSumStart() != null)
            _sumStartTotal += moneyLotAssetNew.get_calcSumStart();
        if (moneyLotAssetNew.get_calcSumEnd() != null)
            _sumEndTotal += moneyLotAssetNew.get_calcSumEnd();
        //endregion

        //region AddToWallet
        int walletsNodeId = -1;
        if (moneyLotAssetNew.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.Lot))
        {
            boolean lotWalletFinded = false;
            for (int i = 0; i < _dataSet.size(); i++)
                if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                    if (Objects.equals(_dataSet.get(i).get_object().get_moneyAsset().get_wallet_id(),
                            moneyLotAssetNew.get_portfolioWalletId()))
                    {
                        if (_dataSet.get(i).get_object().get_calcSumStart() != null)
                            _sumStartTotal -= _dataSet.get(i).get_object().get_calcSumStart();
                        if (_dataSet.get(i).get_object().get_calcSumEnd() != null)
                            _sumEndTotal -= _dataSet.get(i).get_object().get_calcSumEnd();

                        MoneyLotAsset moneyLotAsset4Wallet = MoneyLotAssetDataSource.LoadData4WalletAsset(
                                _context, _dataSet.get(i).get_object().get_moneyAsset().get_wallet_id());
                        moneyLotAsset4Wallet.RecalcCalcData(globalVariable.get_capital_customPeriodClass(), _context,
                                _dataSet.get(i).getParent().get_object().get_calcSumStart(),
                                _dataSet.get(i).getParent().get_object().get_calcSumEnd());
                        TreeItem<MoneyLotAsset> moneyLotAssetTreeItem = new TreeItem<>(moneyLotAsset4Wallet, false);
                        TreeItem<MoneyLotAsset> walletsTreeItem = _dataSet.get(i).getParent();
                        for (int k = 0; k < walletsTreeItem.getChildren().size(); k++)
                            if (Objects.equals(walletsTreeItem.getChildren().get(k).get_object().get_id(),
                                    moneyLotAsset4Wallet.get_id()))
                            {
                                walletsTreeItem.getChildren().set(k, moneyLotAssetTreeItem);
                                break;
                            }
                        _dataSet.set(i, moneyLotAssetTreeItem);

                        if (_dataSet.get(i).get_object().get_calcSumStart() != null)
                            _sumStartTotal += _dataSet.get(i).get_object().get_calcSumStart();
                        if (_dataSet.get(i).get_object().get_calcSumEnd() != null)
                            _sumEndTotal += _dataSet.get(i).get_object().get_calcSumEnd();

                        notifyItemChanged(i);

                        for (int t = i - 1; t >= 0; t--)
                            if (_dataSet.get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
                                if (_dataSet.get(t).getSValue().equals(
                                        _dataSet.get(i).get_object().get_moneyAsset().get_moneyTypeAssetName()))
                                {
                                    walletsNodeId = t;
                                    CalcTypeAssetNode(t, globalVariable);
                                    break;
                                }

                        lotWalletFinded = true;
                        break;
                    }

            boolean walletsTypeAssetFinded = true;
            if (!lotWalletFinded)
            {
                walletsTypeAssetFinded = false;
                for (int t = 0; t < _dataSet.size(); t++)
                    if (_dataSet.get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
                        if (_dataSet.get(t).getSValue().equals(_context.getString(R.string.money_category_wallet)))
                        {
                            walletsNodeId = t;
                            for (int i = 0; i < _dataSet.get(t).getChildren().size(); i++)
                                if (Objects.equals(_dataSet.get(t).getChildren().get(i).get_object().get_moneyAsset().get_wallet_id(),
                                        moneyLotAssetNew.get_portfolioWalletId()))
                                {
                                    if (_dataSet.get(t).getChildren().get(i).get_object().get_calcSumStart() != null)
                                        _sumStartTotal -= _dataSet.get(t).getChildren().get(i).get_object().get_calcSumStart();
                                    if (_dataSet.get(t).getChildren().get(i).get_object().get_calcSumEnd() != null)
                                        _sumEndTotal -= _dataSet.get(t).getChildren().get(i).get_object().get_calcSumEnd();

                                    MoneyLotAsset moneyLotAsset4Wallet = MoneyLotAssetDataSource.LoadData4WalletAsset(
                                            _context, _dataSet.get(t).getChildren().get(i).get_object().get_moneyAsset().get_wallet_id());
                                    moneyLotAsset4Wallet.RecalcCalcData(globalVariable.get_capital_customPeriodClass(), _context,
                                            _dataSet.get(i).get_object().get_calcSumStart(),
                                            _dataSet.get(i).get_object().get_calcSumEnd());
                                    _dataSet.get(t).getChildren().set(i, new TreeItem<>(moneyLotAsset4Wallet, false));

                                    if (_dataSet.get(t).getChildren().get(i).get_object().get_calcSumStart() != null)
                                        _sumStartTotal += _dataSet.get(t).getChildren().get(i).get_object().get_calcSumStart();
                                    if (_dataSet.get(t).getChildren().get(i).get_object().get_calcSumEnd() != null)
                                        _sumEndTotal += _dataSet.get(t).getChildren().get(i).get_object().get_calcSumEnd();

                                    break;
                                }

                            CalcTypeAssetNodeIfNotExpanded(t, globalVariable);
                            walletsTypeAssetFinded = true;

                            break;
                        }
            }

            if (!walletsTypeAssetFinded)
                for (int i = 0; i < _dataSet.size(); i++)
                    if (_dataSet.get(i).get_object().get_name().equals(_context.getString(R.string.Money_KindAsset_Conservative)))
                    {
                        TreeItem<MoneyLotAsset> moneyLotAssetWalletsTypeAsset = null;
                        for (int t = 0; t < _dataSet.get(i).getChildren().size(); t++)
                            if (_dataSet.get(i).getChildren().get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
                                if (_dataSet.get(i).getChildren().get(t).getSValue().equals(_context.getString(R.string.money_category_wallet)))
                                {
                                    MoneyLotAsset moneyLotAsset4Wallet = null;
                                    moneyLotAssetWalletsTypeAsset = _dataSet.get(i).getChildren().get(t);
                                    for (int m = 0; m < moneyLotAssetWalletsTypeAsset.getChildren().size(); m++)
                                        if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                                            if (Objects.equals(moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_moneyAsset().get_wallet_id(),
                                                    moneyLotAssetNew.get_portfolioWalletId()))
                                            {
                                                if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart() != null)
                                                    _sumStartTotal -= moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart();
                                                if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd() != null)
                                                    _sumEndTotal -= moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd();

                                                moneyLotAsset4Wallet = MoneyLotAssetDataSource.LoadData4WalletAsset(
                                                        _context,
                                                        moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_moneyAsset().get_wallet_id());
                                                moneyLotAsset4Wallet.RecalcCalcData(globalVariable.get_capital_customPeriodClass(), _context,
                                                        moneyLotAssetWalletsTypeAsset.get_object().get_calcSumStart(),
                                                        moneyLotAssetWalletsTypeAsset.get_object().get_calcSumEnd());
                                                moneyLotAssetWalletsTypeAsset.getChildren().set(m, new TreeItem<>(moneyLotAsset4Wallet, false));

                                                if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart() != null)
                                                    _sumStartTotal += moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart();
                                                if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd() != null)
                                                    _sumEndTotal += moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd();

                                                break;
                                            }
                                    CalcTypeAssetNodeIfNotExpanded(moneyLotAssetWalletsTypeAsset, globalVariable);
                                    if (moneyLotAsset4Wallet != null)
                                        moneyLotAsset4Wallet.RecalcCalcData(globalVariable.get_capital_customPeriodClass(), _context,
                                                moneyLotAssetWalletsTypeAsset.get_object().get_calcSumStart(),
                                                moneyLotAssetWalletsTypeAsset.get_object().get_calcSumEnd());

                                    break;
                                }

                        CalcKindAssetNodeIfNotExpanded(i, globalVariable);
                        if (moneyLotAssetWalletsTypeAsset != null)
                            CalcTypeAssetNodeIfNotExpanded(moneyLotAssetWalletsTypeAsset, globalVariable);

                        walletsNodeId = -1;
                        break;
                    }
        }
        //endregion

        //region KindAsset
        for (int i = update_position - 1; i >= 0; i--)
            if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset) &&
                    _dataSet.get(i).get_object().get_comment().equals(moneyLotAssetNew.get_moneyAsset().get_kindAsset().toString()))
            {
                CalcKindAssetNode(i, globalVariable);
                break;
            }

        for (int i = walletsNodeId - 1; i >= 0; i--)
            if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset) &&
                    _dataSet.get(i).get_object().get_comment().equals(EnumKindAsset.Conservative.toString()))
            {
                CalcKindAssetNode(i, globalVariable);
                break;
            }
        //endregion

        return moneyLotAssetNew.get_id();
    }

    public boolean DeleteFromDataset(TreeItem<MoneyLotAsset> moneyLotAssetTreeItem) throws Exception
    {
        return DeleteFromDataset(moneyLotAssetTreeItem.get_object());
    }

    public boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (dbHelper.DeleteMoneyAsset(_currentItem.get_object().get_id()))
                return DeleteFromDataset(_currentItem.get_object());
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyAssetNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyAsset) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyLotAsset moneyLotAssetDeleted)
    {
        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        MoneyLotAssetSettings moneyLotAssetSettings = globalVariable.get_moneyLotAssetSettings();
        moneyLotAssetDeleted.RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(), _context, null, null);

        //region Delete data
        int kindAssetPos = -1;
        for (int i = 0; i < _dataSet.size(); i++)
        {
            MoneyLotAsset moneyLotAsset = _dataSet.get(i).get_object();
            if (Objects.equals(moneyLotAsset.get_id(), moneyLotAssetDeleted.get_id()) &&
                    moneyLotAsset.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.Lot))
            {
                _dataSet.remove(i);
                int rangeStart = i;
                int rangeCount = 1;
                for (int k = i - 1; k >= 0; k--)
                {
                    MoneyLotAsset moneyLotAsset2 = _dataSet.get(k).get_object();
                    if (moneyLotAsset2.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset) &&
                            _dataSet.get(k).getSValue().equals(moneyLotAssetDeleted.get_moneyAsset().get_moneyTypeAssetName()))
                    {
                        CalcTypeAssetNode(k, globalVariable);

                        for (int m = k - 1; m >= 0; m--)
                            if (_dataSet.get(m).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset))
                            {
                                kindAssetPos = m;
                                break;
                            }

                        _dataSet.get(k).getChildren().remove(i - k - 1);
                        moneyLotAsset2.set_hasChild(_dataSet.get(k).getChildren().size() > 0);

                        if (_dataSet.get(k).getChildren().size() == 0)
                        {
                            _dataSet.remove(k);
                            for (TreeItem<MoneyLotAsset> item: _dataSet.get(kindAssetPos).getChildren())
                                if (item.get_object().get_typeMoneyLotAsset().equals(moneyLotAsset2.get_typeMoneyLotAsset()))
                                {
                                    _dataSet.get(kindAssetPos).getChildren().remove(item);
                                    break;
                                }
                            _dataSet.get(kindAssetPos).get_object().set_hasChild(_dataSet.get(kindAssetPos).getChildren().size() == 0);
                            rangeStart--;
                            rangeCount++;
                        }

                        break;
                    }
                }
                notifyItemRangeRemoved(rangeStart, rangeCount);
                break;
            }
        }
        //endregion

        //region Total
        if (moneyLotAssetDeleted.get_calcSumStart() != null)
            _sumStartTotal -= moneyLotAssetDeleted.get_calcSumStart();
        if (moneyLotAssetDeleted.get_calcSumEnd() != null)
            _sumEndTotal -= moneyLotAssetDeleted.get_calcSumEnd();
        //endregion

        //region AddToWallet
        int walletsNodeId = -1;
        if (moneyLotAssetDeleted.get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.Lot))
        {
            boolean lotWalletFinded = false;
            for (int i = 0; i < _dataSet.size(); i++)
                if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                    if (Objects.equals(_dataSet.get(i).get_object().get_moneyAsset().get_wallet_id(),
                            moneyLotAssetDeleted.get_portfolioWalletId()))
                    {
                        MoneyLotAsset moneyLotAsset4Wallet = MoneyLotAssetDataSource.LoadData4WalletAsset(
                                _context, _dataSet.get(i).get_object().get_moneyAsset().get_wallet_id());

                        if (_dataSet.get(i).get_object().get_calcSumStart() != null)
                            _sumStartTotal -= _dataSet.get(i).get_object().get_calcSumStart();
                        if (_dataSet.get(i).get_object().get_calcSumEnd() != null)
                            _sumEndTotal -= _dataSet.get(i).get_object().get_calcSumEnd();

                        moneyLotAsset4Wallet.RecalcCalcData(globalVariable.get_capital_customPeriodClass(), _context,
                                _dataSet.get(i).getParent().get_object().get_calcSumStart(),
                                _dataSet.get(i).getParent().get_object().get_calcSumEnd());
                        TreeItem<MoneyLotAsset> moneyLotAssetTreeItem = new TreeItem<>(moneyLotAsset4Wallet, false);
                        TreeItem<MoneyLotAsset> walletsTreeItem = _dataSet.get(i).getParent();
                        for (int k = 0; k < walletsTreeItem.getChildren().size(); k++)
                            if (Objects.equals(walletsTreeItem.getChildren().get(k).get_object().get_id(),
                                    moneyLotAsset4Wallet.get_id()))
                            {
                                walletsTreeItem.getChildren().set(k, moneyLotAssetTreeItem);
                                break;
                            }
                        _dataSet.set(i, moneyLotAssetTreeItem);

                        if (_dataSet.get(i).get_object().get_calcSumStart() != null)
                            _sumStartTotal += _dataSet.get(i).get_object().get_calcSumStart();
                        if (_dataSet.get(i).get_object().get_calcSumEnd() != null)
                            _sumEndTotal += _dataSet.get(i).get_object().get_calcSumEnd();

                        notifyItemChanged(i);

                        for (int t = i - 1; t >= 0; t--)
                            if (_dataSet.get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
                                if (_dataSet.get(t).getSValue().equals(
                                        _dataSet.get(i).get_object().get_moneyAsset().get_moneyTypeAssetName()))
                                {
                                    walletsNodeId = t;
                                    CalcTypeAssetNode(t, globalVariable);
                                    break;
                                }

                        lotWalletFinded = true;
                        break;
                    }

            boolean walletsTypeAssetFinded = true;
            if (!lotWalletFinded)
            {
                walletsTypeAssetFinded = false;
                for (int t = 0; t < _dataSet.size(); t++)
                    if (_dataSet.get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
                        if (_dataSet.get(t).getSValue().equals(_context.getString(R.string.money_category_wallet)))
                        {
                            walletsNodeId = t;
                            for (int i = 0; i < _dataSet.get(t).getChildren().size(); i++)
                                if (Objects.equals(_dataSet.get(t).getChildren().get(i).get_object().get_moneyAsset().get_wallet_id(),
                                        moneyLotAssetDeleted.get_portfolioWalletId()))
                                {
                                    if (_dataSet.get(t).getChildren().get(i).get_object().get_calcSumStart() != null)
                                        _sumStartTotal -= _dataSet.get(t).getChildren().get(i).get_object().get_calcSumStart();
                                    if (_dataSet.get(t).getChildren().get(i).get_object().get_calcSumEnd() != null)
                                        _sumEndTotal -= _dataSet.get(t).getChildren().get(i).get_object().get_calcSumEnd();

                                    MoneyLotAsset moneyLotAsset4Wallet = MoneyLotAssetDataSource.LoadData4WalletAsset(
                                            _context, _dataSet.get(t).getChildren().get(i).get_object().get_moneyAsset().get_wallet_id());
                                    moneyLotAsset4Wallet.RecalcCalcData(globalVariable.get_capital_customPeriodClass(), _context,
                                            _dataSet.get(t).get_object().get_calcSumStart(),
                                            _dataSet.get(t).get_object().get_calcSumEnd());
                                    _dataSet.get(t).getChildren().set(i, new TreeItem<>(moneyLotAsset4Wallet, false));

                                    if (_dataSet.get(t).getChildren().get(i).get_object().get_calcSumStart() != null)
                                        _sumStartTotal += _dataSet.get(t).getChildren().get(i).get_object().get_calcSumStart();
                                    if (_dataSet.get(t).getChildren().get(i).get_object().get_calcSumEnd() != null)
                                        _sumEndTotal += _dataSet.get(t).getChildren().get(i).get_object().get_calcSumEnd();

                                    break;
                                }

                            CalcTypeAssetNodeIfNotExpanded(t, globalVariable);
                            walletsTypeAssetFinded = true;

                            break;
                        }
            }

            if (!walletsTypeAssetFinded)
                for (int i = 0; i < _dataSet.size(); i++)
                    if (_dataSet.get(i).get_object().get_name().equals(EnumKindAsset.Conservative.toString()))
                    {
                        for (int t = 0; t < _dataSet.get(i).getChildren().size(); t++)
                            if (_dataSet.get(i).getChildren().get(t).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.TypeAsset))
                                if (_dataSet.get(i).getChildren().get(t).getSValue().equals(_context.getString(R.string.money_category_wallet)))
                                {
                                    TreeItem<MoneyLotAsset> moneyLotAssetWalletsTypeAsset = _dataSet.get(i).getChildren().get(t);
                                    for (int m = 0; m < moneyLotAssetWalletsTypeAsset.getChildren().size(); m++)
                                        if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.LotWallet))
                                            if (Objects.equals(moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_moneyAsset().get_wallet_id(),
                                                    moneyLotAssetDeleted.get_portfolioWalletId()))
                                            {
                                                if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart() != null)
                                                    _sumStartTotal -= moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart();
                                                if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd() != null)
                                                    _sumEndTotal -= moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd();

                                                MoneyLotAsset moneyLotAsset4Wallet = MoneyLotAssetDataSource.LoadData4WalletAsset(
                                                        _context,
                                                        moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_moneyAsset().get_wallet_id());
                                                moneyLotAsset4Wallet.RecalcCalcData(globalVariable.get_capital_customPeriodClass(), _context, null, null);
                                                moneyLotAssetWalletsTypeAsset.getChildren().set(m, new TreeItem<>(moneyLotAsset4Wallet, false));

                                                if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart() != null)
                                                    _sumStartTotal += moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumStart();
                                                if (moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd() != null)
                                                    _sumEndTotal += moneyLotAssetWalletsTypeAsset.getChildren().get(m).get_object().get_calcSumEnd();

                                                break;
                                            }
                                    CalcTypeAssetNodeIfNotExpanded(moneyLotAssetWalletsTypeAsset, globalVariable);
                                    break;
                                }

                        CalcKindAssetNodeIfNotExpanded(i, globalVariable);
                        walletsNodeId = -1;
                        break;
                    }
        }
        //endregion

        //region KindAsset
        CalcKindAssetNode(kindAssetPos, globalVariable);

        for (int i = walletsNodeId - 1; i >= 0; i--)
            if (_dataSet.get(i).get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset) &&
                    _dataSet.get(i).get_object().get_comment().equals(EnumKindAsset.Conservative.toString()))
            {
                CalcKindAssetNode(i, globalVariable);
                break;
            }
        //endregion

        return true;
    }
    //endregion

    //region GetSummaryLotAsset
    public MoneyLotAsset getSummaryLotAsset()
    {
        MoneyLotAsset moneyLotAssetSummary = MoneyLotAsset.GetSummaryMoneyLotAsset(_context);

        for (TreeItem<MoneyLotAsset> item: _dataSet)
            if (item.get_object().get_typeMoneyLotAsset().equals(MoneyLotAsset.EnumTypeMoneyLotAsset.KindAsset))
            {
                moneyLotAssetSummary.add_calcCountStart(item.get_object().get_calcCountStart());
                moneyLotAssetSummary.add_calcSumStart(item.get_object().get_calcSumStart());
                moneyLotAssetSummary.add_calcBaseSumPlus(item.get_object().get_calcBaseSumPlus(_context,
                        EnumKindAsset.Conservative));
                moneyLotAssetSummary.add_calcNKDStart(item.get_object().get_calcNKDStart());
                moneyLotAssetSummary.add_calcCountThisPeriodPlus(item.get_object().get_calcCountThisPeriodPlus());
                moneyLotAssetSummary.add_calcSumThisPeriodPlus(item.get_object().get_calcSumThisPeriodPlus());
                moneyLotAssetSummary.add_calcNKDThisPeriodPlus(item.get_object().get_calcNKDThisPeriodPlus());
                moneyLotAssetSummary.add_calcCountThisPeriodMinus(item.get_object().get_calcCountThisPeriodMinus());
                moneyLotAssetSummary.add_calcSumThisPeriodMinus(item.get_object().get_calcSumThisPeriodMinus());
                moneyLotAssetSummary.add_calcNKDThisPeriodMinus(item.get_object().get_calcNKDThisPeriodMinus());
                moneyLotAssetSummary.add_calcSumEnd(item.get_object().get_calcSumEnd());
                moneyLotAssetSummary.add_calcNKDEnd(item.get_object().get_calcNKDEnd());
                moneyLotAssetSummary.add_calcPayments(item.get_object().get_calcPayments());
                moneyLotAssetSummary.add_calcDifference(item.get_object().get_calcDifference());
                moneyLotAssetSummary.add_calcDifferenceTotal(item.get_object().get_calcDifferenceTotal());
            }
        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        moneyLotAssetSummary.RecalcCalcData(globalVariable.get_capital_customPeriodClass(),
                _context,
                null,
                null);
        moneyLotAssetSummary.RecalcCalcData(globalVariable.get_capital_customPeriodClass(),
                _context,
                moneyLotAssetSummary.get_calcSumStart(),
                moneyLotAssetSummary.get_calcSumEnd());

        return moneyLotAssetSummary;
    }
    //endregion

//    @Override
//    public void onItemMove(int fromPosition, int toPosition)
//    {
//        if (_dataSet.get(fromPosition).get_object().get_moneyTypeAssetId() == 0)
//            return;
//
//        Long updateId = _dataSet.get(fromPosition).get_object().get_id();
//
//        //Изменения для 3-его уровня
//        {
//            int oldParentIndex = 0;
//            TreeItem<MoneyAsset> oldParent = null;
//            int start = toPosition;
//            int end = fromPosition;
//            int endForUpdate = fromPosition;
//            if (fromPosition < toPosition)
//            {
//                start = fromPosition;
//                end = toPosition;
//                endForUpdate = toPosition;
//            }
//
//            for (int i = fromPosition; i >= 0; i--)
//                if (_dataSet.get(i).get_object().get_moneyTypeAssetId() == 0L)
//                {
//                    oldParent = _dataSet.get(i);
//                    oldParentIndex = i;
//                    break;
//                }
//
//            if (fromPosition < toPosition)
//                for (int i = start; i < end; i++)
//                {
//                    if (_dataSet.get(i + 1).get_object().get_moneyTypeAssetId() != 0L)
//                    {
//                        int pos = _dataSet.get(i).get_object().get_position();
//                        _dataSet.get(i).get_object().set_position(_dataSet.get(i + 1).get_object().get_position());
//                        _dataSet.get(i + 1).get_object().set_position(pos);
//                        Collections.swap(oldParent.getChildren(), i - oldParentIndex - 1,i - oldParentIndex);
//                    }
//                    else
//                    {
//                        Long parentId = _dataSet.get(i + 1).get_object().get_id();
//                        _dataSet.get(i).get_object().set_position(1);
//                        _dataSet.get(i).get_object().set_moneyTypeAssetId(parentId);
//
//                        if (!_dataSet.get(i + 1).isExpanded())
//                            Expand(_dataSet.get(i + 1).get_object().get_id());
//
//                        for (int k = i + 2; k < _dataSet.size(); k++)
//                            if (Objects.equals(_dataSet.get(k).get_object().get_moneyTypeAssetId(), parentId))
//                            {
//                                _dataSet.get(k).get_object().set_position(_dataSet.get(k).get_object().get_position() + 1);
//                                endForUpdate = k;
//                            }
//
//                        int childIndex = 0;
//                        if (oldParent.isExpanded())
//                            childIndex = oldParent.getChildren().size() - 1;
//
//                        _dataSet.get(i + 1).getChildren().add(0, oldParent.getChildren().get(childIndex));
//                        oldParent.getChildren().remove(childIndex);
//                        oldParent = _dataSet.get(i + 1);
//                    }
//
//                    Collections.swap(_dataSet, i, i + 1);
//                }
//            else
//                for (int i = end; i > start; i--)
//                {
//                    if (_dataSet.get(i - 1).get_object().get_moneyTypeAssetId() != 0L)
//                    {
//                        int pos = _dataSet.get(i).get_object().get_position();
//                        _dataSet.get(i).get_object().set_position(_dataSet.get(i - 1).get_object().get_position());
//                        _dataSet.get(i - 1).get_object().set_position(pos);
//                        Collections.swap(oldParent.getChildren(), i - oldParentIndex - 1,i - oldParentIndex - 2);
//                    }
//                    else
//                    {
//                        if (i - 1 == 0)
//                        {
//                            _dataSet.get(i).get_object().set_position(1);
//                            _dataSet.get(i).get_object().set_moneyTypeAssetId(0L);
//                            int parentId = 0;
//                            for (int k = 0; k < _dataSet.size(); k++)
//                                if (_dataSet.get(k).get_object().get_moneyTypeAssetId() == parentId &&
//                                        !Objects.equals(_dataSet.get(k).get_object().get_id(), updateId))
//                                {
//                                    _dataSet.get(k).get_object().set_position(_dataSet.get(k).get_object().get_position() + 1);
//                                    endForUpdate = k;
//                                }
//
//                            oldParent.getChildren().remove(0);
//                            oldParent = null;
//                        }
//                        else
//                        {
//                            TreeItem<MoneyAsset> newParent = null;
//                            for (int k = i - 2; k >= 0; k--)
//                                if (_dataSet.get(k).get_object().get_moneyTypeAssetId() == 0L)
//                                {
//                                    newParent = _dataSet.get(k);
//                                    break;
//                                }
//
//                            _dataSet.get(i).get_object().set_moneyTypeAssetId(newParent.get_object().get_id());
//                            if (!newParent.isExpanded())
//                            {
//                                Expand(newParent.get_object().get_id());
//                                i = i + newParent.getChildren().size();
//                                end = end + newParent.getChildren().size();
//                                endForUpdate = endForUpdate + newParent.getChildren().size();
//                                start = start + newParent.getChildren().size();
//                            }
//                            _dataSet.get(i).get_object().set_position(_dataSet.get(i - 2).get_object().get_position() + 1);
//
//                            int childIndex = 0;
//                            if (!oldParent.isExpanded())
//                                childIndex = oldParent.getChildren().size() - 1;
//
//                            newParent.getChildren().add(oldParent.getChildren().get(childIndex));
//                            oldParent.getChildren().remove(childIndex);
//                            oldParent = newParent;
//                        }
//                    }
//
//                    Collections.swap(_dataSet, i, i - 1);
//                }
//
//            try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
//            {
//                for (int i = start; i <= endForUpdate; i++)
//                    if (_dataSet.get(i).get_object().get_moneyTypeAssetId() != 0L ||
//                            (_dataSet.get(i).get_object().get_moneyTypeAssetId() == 0L && (i == 0)))
//                    {
//                        if (Objects.equals(_dataSet.get(i).get_object().get_id(), updateId))
//                        {
//                            if (!dbHelper.UpdateMoneyAsset(_dataSet.get(i).get_object()))
//                                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyAsset),
//                                        Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            if (!dbHelper.UpdateMoneyAssetPosition(_dataSet.get(i).get_object().get_id(),
//                                    _dataSet.get(i).get_object().get_position()))
//                                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyAssetPosition),
//                                        Toast.LENGTH_SHORT).show();
//                        }
//                    }
//            }
//            catch (Exception e)
//            {
//                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyAssetPosition) +
//                        e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            notifyItemMoved(fromPosition, toPosition);
//            for (int i = start; i <= endForUpdate; i++)
//                notifyItemChanged(i);
//        }
//    }

    //region ItemViewHolder
    public static class ItemViewHolder extends TreeViewAdapter.ItemViewHolder<TreeItem<MoneyLotAsset>>
    {
        private final TextView _textViewName;
        private final TextView _textViewSumStart;
        private final TextView _textViewSumEnd;
        private final TextView _textViewSumThisPeriodPlus;
        private final TextView _textViewSumThisPeriodMinus;
        private final TextView _textViewDifference;
        private final TextView _textViewDifferenceTotal;

        public ItemViewHolder(@androidx.annotation.NonNull View itemView, Context context)
        {
            super(itemView, context);
            _textViewName = itemView.findViewById(R.id.textViewName);
            _textViewSumStart = itemView.findViewById(R.id.textViewSumStart);
            _textViewSumEnd = itemView.findViewById(R.id.textViewSumEnd);
            _textViewSumThisPeriodPlus = itemView.findViewById(R.id.textViewSumThisPeriodPlus);
            _textViewSumThisPeriodMinus = itemView.findViewById(R.id.textViewSumThisPeriodMinus);
            _textViewDifference = itemView.findViewById(R.id.textViewDifference);
            _textViewDifferenceTotal = itemView.findViewById(R.id.textViewDifferenceTotal);
        }

        private void setName(String name, boolean isParent, int depth)
        {
            if (_textViewName != null)
            {
                _textViewName.getLayoutParams().width =
                        (int) (_context.getResources().getDimension(
                                                        R.dimen.Money_LotAsset_RView_Item_Width) -
                                                ((depth - 1) * _context.getResources().getDimension(
                                                        R.dimen.Money_LotAsset_RView_Item_Shift)));

                _textViewName.setText(name);
                _textViewName.setTypeface(null, isParent ? Typeface.BOLD : Typeface.NORMAL);
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
//        @SuppressLint("SetTextI18n")
//        private void setCountStart(Integer countStart)
//        {
//            if (_textViewCountStart != null)
//            {
//                if (countStart != null)
//                    _textViewCountStart.setText(countStart + " " +
//                            _context.getString(R.string.UnitOfMeasurementPieces));
//                else
//                    _textViewCountStart.setText("");
//            }
//
//        }
//        private void setPriceStart(Long priceStart)
//        {
//            if (_textViewPriceStart != null)
//            {
//                if (priceStart != null)
//                    setLongToTextView(_textViewPriceStart, priceStart);
//                else
//                    _textViewPriceStart.setText("");
//            }
//        }
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

        //        private void setCountEnd(Integer countEnd)
//        {
//            if (_textViewCountEnd != null)
//            {
//                if (countEnd != null)
//                    _textViewCountEnd.setText(String.valueOf(countEnd));
//                else
//                    _textViewCountEnd.setText("");
//            }
//        }
//        private void setPriceEnd(Long priceEnd)
//        {
//            if (_textViewPriceEnd != null)
//            {
//                if (priceEnd != null)
//                    setLongToTextView(_textViewPriceEnd, priceEnd);
//                else
//                    _textViewPriceEnd.setText("");
//            }
//        }
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
//        private void setPayments(Long payments)
//        {
//            if (_textViewPayments != null)
//            {
//                if (payments != null)
//                {
//                    if (payments > 0L)
//                        setLongToTextView(_textViewPayments, "+", payments, null,"");
//                    else
//                        setLongToTextView(_textViewPayments, "-", payments, null, "");
//                }
//                else
//                    _textViewPayments.setText("");
//            }
//        }
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

        @Override
        public void setObject(TreeItem<MoneyLotAsset> moneyLotAssetTreeItem)
        {
            super.setObject(moneyLotAssetTreeItem);

            MoneyLotAsset moneyLotAsset = moneyLotAssetTreeItem.get_object();
            GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
            MoneyLotAssetSettings moneyLotAssetSettings = globalVariable.get_moneyLotAssetSettings();
            moneyLotAsset.RecalcCalcData(moneyLotAssetSettings.get_customPeriodClass(), _context,
                    moneyLotAssetTreeItem.getParent() == null || moneyLotAssetTreeItem.getParent().get_object() == null ? null :
                    moneyLotAssetTreeItem.getParent().get_object().get_calcSumStart(),
                    moneyLotAssetTreeItem.getParent() == null || moneyLotAssetTreeItem.getParent().get_object() == null ? null :
                    moneyLotAssetTreeItem.getParent().get_object().get_calcSumEnd());

            setName(moneyLotAsset.getSValue(), false, moneyLotAssetTreeItem.getDepth());
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
    }
    //endregion
}
