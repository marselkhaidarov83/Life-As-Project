package com.MH83.LifeAsProject.adapter.rView.money;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.custom.TreeViewAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyIncomingListDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyIncreaseDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeData;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyIncoming;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingList;
import com.MH83.LifeAsProject.model.Money.MoneyIncomingAndCostSettings;
import com.MH83.LifeAsProject.model.Money.MoneyIncrease;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyCosts.FragmentMoneyCostPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyIncoming.FragmentMoneyIncomingAndCost;
import com.MH83.LifeAsProject.view.Money.MoneyIncoming.FragmentMoneyIncomingAndCostPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyIncoming.FragmentMoneyIncomingPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class MoneyIncomingAndCostAdapter
        extends TreeViewAdapter<MoneyIncomingAndCostAdapter.ItemViewHolder, MoneyIncomingList,
        FragmentMoneyIncomingAndCostPopupNew> implements IAUDAdapter<MoneyIncoming>
{
    private final int _maxCountMoneyIncrease;
    private final ArrayList<Long> _increaseIds;
    private final int _countMoneyIncrease;
    private final int _activeColumn;
    private Hashtable<Long, MoneyIncrease> _moneyIncrease;
    private MoneyIncomingAndCostSettings _moneyIncomingSettings;
    private final Long _firstMoneyIncreaseId;

    public MoneyIncomingAndCostAdapter(Context context, IFragmentViewCustom fragmentViewCustom,
                                       ArrayList<Long> increaseIds,
                                       Long firstMoneyIncreaseId,
                                       int maxCountMoneyIncrease, int activeColumn,
                                       Hashtable<Long, MoneyIncrease> moneyIncrease,
                                       MoneyIncomingAndCostSettings moneyIncomingSettings)
    {
        super(context, null, fragmentViewCustom, R.layout.item_tree_money_incoming);
        _increaseIds = increaseIds;
        _firstMoneyIncreaseId = firstMoneyIncreaseId;
        _countMoneyIncrease = moneyIncomingSettings.get_countMoneyIncrease();
        _maxCountMoneyIncrease = maxCountMoneyIncrease;
        _activeColumn = activeColumn;
        _moneyIncomingSettings = moneyIncomingSettings;

        LoadData(context, moneyIncrease);
        SetData(_moneyIncomingSettings.get_expandedList());

        if (moneyIncomingSettings.get_typeData() == EnumTypeData.Fact)
            for (Long key: moneyIncrease.keySet())
                moneyIncrease.get(key).set_value(getSum(_increaseIds.indexOf(key)));
    }

    private void LoadData(Context context, Hashtable<Long, MoneyIncrease> moneyIncrease)
    {
        if (_moneyIncomingSettings.get_typeData().equals(EnumTypeData.Plan))
            _moneyIncrease = MoneyIncreaseDataSource.LoadDataHashTable(context, _moneyIncomingSettings);
        else
            _moneyIncrease = moneyIncrease;
    }

    public void RefreshData(MoneyIncomingAndCostSettings moneyIncomingSettings)
    {
        _moneyIncomingSettings = moneyIncomingSettings;
        SetData(_moneyIncomingSettings.get_expandedList());
    }

    public Double getSum(int column)
    {
        if (column == -1)
            return 0d;

        Long sum = 0L;

        for (TreeItem<MoneyIncomingList> item: _dataSet)
            if (item.get_object().isParent())
                sum += item.get_object().getMoneyIncoming(_increaseIds.get(column)).get_sumLong();

        return sum / 100d;
    }

    private void SetData(ArrayList<Long> expandedList)
    {
        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();

        List<MoneyIncomingList> loadDataSetParent = MoneyIncomingListDataSource.LoadDataParent(
                _context, false,
                _moneyIncomingSettings);

        if (loadDataSetParent == null)
            return;

        TreeItem<MoneyIncomingList> root = new TreeItem<>(null, true);
        root.setExpanded(true);

        ArrayList<Long> parents = new ArrayList<>();
        Hashtable<Long, TreeItem<MoneyIncomingList>> folders = new Hashtable<>();
        for (int i = 0; i < loadDataSetParent.size(); i++)
        {
            TreeItem<MoneyIncomingList> folderCategory = new TreeItem<>(loadDataSetParent.get(i), true);
            MoneyIncomingList mil = folderCategory.get_object();
            for (int t = 0; t <= _maxCountMoneyIncrease; t++)
            {
                MoneyIncoming moneyIncoming = new MoneyIncoming(0L,
                        mil.getMoneyCategoryId(), _increaseIds.get(t), 0L,
                        0f, EnumTypeIncoming.Sum, 0L);
                mil.setMoneyIncoming(_increaseIds.get(t), moneyIncoming);
            }

            folders.put(loadDataSetParent.get(i).getMoneyCategoryId(), folderCategory);
            parents.add(loadDataSetParent.get(i).getMoneyCategoryId());
        }

        ArrayList<Long> _incrIds = new ArrayList<>();
        for (int i = 0; i < _increaseIds.size(); i++)
//            if (Objects.equals(_firstMoneyIncreaseId, _increaseIds.get(i))
//                    ||
//                    globalVariable.get_hasPaymentOrTestSubscription() ||
//                    _moneyIncomingSettings.get_typeData().equals(EnumTypeData.Plan)
//            )
                _incrIds.add(_increaseIds.get(i));

        List<MoneyIncomingList> loadDataSet = MoneyIncomingListDataSource.LoadDataChildren(
            _context, parents, _incrIds, _moneyIncomingSettings);

        for (int k = 0; k < loadDataSet.size(); k++)
        {
            TreeItem<MoneyIncomingList> singleCategory = new TreeItem<>(loadDataSet.get(k), false);
            TreeItem<MoneyIncomingList> folderCategory = folders.get(loadDataSet.get(k).getMoneyCategory().get_parentId());
            MoneyIncomingList mil = folderCategory.get_object();

            boolean existSum = false;
            for (int t = 0; t <= _maxCountMoneyIncrease; t++)
            {
                if (singleCategory.get_object().getMoneyIncoming(_increaseIds.get(t)) != null)
                {
                    mil.getMoneyIncoming(_increaseIds.get(t)).set_sumLong(
                            mil.getMoneyIncoming(_increaseIds.get(t)).get_sumLong() +
                                    singleCategory.get_object().getMoneyIncoming(_increaseIds.get(t)).get_sumLong());
                    existSum = true;
                }
            }

            if (_moneyIncomingSettings.get_showBlankData() || existSum)
            {
                folderCategory.getChildren().add(singleCategory);
                (folderCategory.get_object().getMoneyCategory()).set_hasChild(true);
            }
        }

        for (Long id: parents)
            if (folders.get(id).getChildren().size() > 0)
            {
                folders.get(id).setExpanded(expandedList != null &&
                        expandedList.contains(folders.get(id).get_object().getMoneyCategoryId()));
                root.getChildren().add(folders.get(id));
            }

        CalcIncreasePercent(root);
        CalcPercent(root);

        setRoot(root, false);
    }

    private void CalcPercent(TreeItem<MoneyIncomingList> root)
    {
        for (int i = 0; i < _countMoneyIncrease; i++)
            CalcPercentOnce(root.get_object(), i);
        for (TreeItem<MoneyIncomingList> item: root.getChildren())
            CalcPercent(item);
    }

    private void CalcPercentOnce(MoneyIncomingList mil, int i)
    {
        if (mil != null && mil.getMoneyIncoming(_increaseIds.get(i)) != null &&
                _moneyIncrease.get(_increaseIds.get(i)) != null &&
                _moneyIncrease.get(_increaseIds.get(i)).get_value() != null &&
                _moneyIncrease.get(_increaseIds.get(i)).get_value() != 0)
            mil.getMoneyIncoming(_increaseIds.get(i)).set_percent(
                    (int) (100 * mil.getMoneyIncoming(_increaseIds.get(i)).get_sumLong() / 100 /
                    _moneyIncrease.get(_increaseIds.get(i)).get_value()));
    }

    private void CalcIncreasePercent(TreeItem<MoneyIncomingList> root)
    {
        for (int i = 0; i < _countMoneyIncrease; i++)
            CalcIncreasePercentOnce(root.get_object(), i);
        for (TreeItem<MoneyIncomingList> item: root.getChildren())
            CalcIncreasePercent(item);
    }

    private void CalcIncreasePercentOnce(MoneyIncomingList mil, int i)
    {
        if (mil == null)
            return;

        int increasePercent = 0;
        boolean increasePercentExist = false;

        int k = i - 1;
        if (k == -1)    k = _maxCountMoneyIncrease;

        if (mil.getMoneyIncoming(_increaseIds.get(i)) != null &&
            mil.getMoneyIncoming(_increaseIds.get(k)) != null &&
            mil.getMoneyIncoming(_increaseIds.get(k)).get_sumLong() != 0)
        {
            increasePercentExist = true;
            increasePercent = (int) (100 * (mil.getMoneyIncoming(_increaseIds.get(i)).get_sumLong() -
                    mil.getMoneyIncoming(_increaseIds.get(k)).get_sumLong()) /
                    mil.getMoneyIncoming(_increaseIds.get(k)).get_sumLong());
        }

        if (mil.getMoneyIncoming(_increaseIds.get(i)) != null)
        {
            mil.getMoneyIncoming(_increaseIds.get(i)).set_increasePercentExist(increasePercentExist);
            mil.getMoneyIncoming(_increaseIds.get(i)).set_increasePercent(increasePercent);
        }
    }

    public void SaveExpandedList(boolean showExpandedButton)
    {
        ArrayList<Long> list = new ArrayList<>();

        for (TreeItem<MoneyIncomingList> mil : _dataSet)
            if (mil.get_object().isParent() && mil.isExpanded())
                list.add(mil.get_object().getMoneyCategoryId());

        _moneyIncomingSettings.set_expandedList(list);
        _moneyIncomingSettings.set_showExpandButton(showExpandedButton);
        GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();
        globalVar.set_moneyIncomingAndCostSettings(_moneyIncomingSettings);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(View view)
    {
        return new ItemViewHolder(view, _context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        super.onBindViewHolder(holder, position);

        MoneyCategory moneyCategory = _dataSet.get(position).get_object().getMoneyCategory();
        MoneyIncomingList moneyIncomingList = _dataSet.get(position).get_object();

        holder.ChangeWidthToTextViews(!_dataSet.get(position).get_object().isParent(), _countMoneyIncrease);
        holder.setActiveColumn(_activeColumn, _maxCountMoneyIncrease, moneyCategory,
                _moneyIncomingSettings);
        holder.set_type4Category(moneyIncomingList, _moneyIncomingSettings);
        holder.SetCountMoneyIncrease(_countMoneyIncrease, _maxCountMoneyIncrease);
        holder.setName(_dataSet.get(position).get_object().getSValue());
        if (_dataSet.get(position).get_object().getMoneyCategory().isParent())
            holder.setTypeFace(Typeface.BOLD);
        else
            holder.setTypeFace(Typeface.NORMAL);

        if (moneyCategory.isParent())
            holder.textViewName.setOnClickListener(view ->
                    ChangeExpandedToItem(_dataSet.get(position), holder));

        for (int i = 0; i < _countMoneyIncrease; i++)
            if (_increaseIds.size() > i)
            {
                MoneyIncoming moneyIncoming = moneyIncomingList.getMoneyIncoming(_increaseIds.get(i));
                if (moneyIncoming != null && moneyIncoming.get_sumLong() != 0)
                {
                    GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();

                    holder.SetIncoming(i, String.valueOf(moneyIncoming.get_StringSum(
                            _moneyIncomingSettings.get_showIncreasePercent(),
                            _moneyIncomingSettings.get_showPercent(),
                            _moneyIncomingSettings.get_typeData(),
                            globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                                    _context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)));
                }
                else
                    holder.SetIncoming(i, "");

                int final_I = i;

                if (!moneyCategory.isParent())
                    holder.textViewIncoming[i].setOnClickListener(view ->
                    {
                        if (_moneyIncomingSettings.get_typeData() == EnumTypeData.Fact)
                            return;

                        _currentItem = _dataSet.get(position);
                        _currentId1 = _increaseIds.get(final_I);

                        EnumTypeAction typeAction = EnumTypeAction.Update;
                        if (moneyIncoming == null)
                        {
                            final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
                            if (!MoneyIncoming.canAdd(_context, globalVariable))
                            {
                                SubscriptionClick(globalVariable);
                                return;
                            }

                            typeAction = EnumTypeAction.Insert;

                        }

                        if (_moneyIncomingSettings.get_typeCategory().equals(EnumTypeCategory.Increase))
                            _fragmentPopupNew = new FragmentMoneyIncomingPopupNew(
                                    (AppCompatActivity) _context, moneyIncoming, typeAction,
                                    _fragmentViewCustom, moneyCategory,
                                    _moneyIncrease.get(_increaseIds.get(final_I)));
                        else
                            _fragmentPopupNew = new FragmentMoneyCostPopupNew(
                                    (AppCompatActivity) _context, moneyIncoming, typeAction,
                                    _fragmentViewCustom, moneyCategory,
                                    _moneyIncrease.get(_increaseIds.get(final_I)));


                        _fragmentPopupNew.PopupShow();
                    });
            }
    }

    @Override
    protected void ClickToLeadingIcon(TreeItem<MoneyIncomingList> item, @androidx.annotation.NonNull ItemViewHolder holder)
    {
        super.ClickToLeadingIcon(item, holder);

        SaveExpandedList(_moneyIncomingSettings.get_showExpandButton());
    }

    @Override
    public void ExpandAll(boolean showExpandedButton)
    {
        super.ExpandAll(showExpandedButton);

        SaveExpandedList(showExpandedButton);
    }

    @Override
    public void CollapseAll(boolean showExpandedButton)
    {
        super.CollapseAll(showExpandedButton);

        SaveExpandedList(showExpandedButton);
    }

    protected void SubscriptionClick(GlobalClass globalVariable)
    {
        if (GlobalClass.SubscrGoogle())
            SubscriptGoogle(globalVariable);

        if (GlobalClass.SubscrRustore())
            SubscriptRustore(globalVariable);
    }

    private void SubscriptRustore(GlobalClass globalVariable)
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

            CustomTrack.PaidFormOpenFromIncAndCost();

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

    private void SubscriptGoogle(GlobalClass globalVariable)
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

                    CustomTrack.PaidFormOpenFromIncAndCost();

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

    @Override
    public boolean canActionRightImage()
    {
        return false;
    }
    @Override
    public boolean canActionLeftImage() { return true; }

    @Override
    public boolean LeftButtonClick() {
        return false;
    }

    @Override
    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }

    public Long AddItem(MoneyIncoming moneyIncoming)
    {
        return AddOrUpdateItem(moneyIncoming);
    }

    public Long UpdateItem(MoneyIncoming moneyIncoming)
    {
        return AddOrUpdateItem(moneyIncoming);
    }

    private Long AddOrUpdateItem(MoneyIncoming moneyIncomingNew)
    {
        return AddOrUpdateOrDeleteItem(moneyIncomingNew, moneyIncomingNew.get_categoryId(),
                moneyIncomingNew.get_increaseId());
    }

    private Long AddOrUpdateOrDeleteItem(MoneyIncoming moneyIncoming, Long categoryId, Long increaseId)
    {
        Long id = -1L;
        int delta = 0;
        if (moneyIncoming != null)
        {
            id = moneyIncoming.get_id();
            delta += moneyIncoming.get_sumLong();
        }

        for (int i = 0; i < _dataSet.size(); i++)
        {
            MoneyIncomingList moneyIncomingOld = _dataSet.get(i).get_object();
            if (Objects.equals(categoryId, moneyIncomingOld.getMoneyCategoryId()))
            {
                for (int k = 0; k < _countMoneyIncrease; k++)
                    if (Objects.equals(increaseId, _increaseIds.get(k)))
                    {
                        if (moneyIncomingOld.getMoneyIncoming(_increaseIds.get(k)) != null)
//                            delta -= moneyIncomingOld.getMoneyIncoming(_increaseIds.get(k)).get_sum();
                            delta -= moneyIncomingOld.getMoneyIncoming(_increaseIds.get(k)).get_sumLong();
                        moneyIncomingOld.setMoneyIncoming(_increaseIds.get(k), moneyIncoming);
                        CalcIncreasePercentOnce(moneyIncomingOld, k);
                        CalcPercentOnce(moneyIncomingOld, k);
                        if (k + 1 < _countMoneyIncrease)
                        {
                            CalcIncreasePercentOnce(moneyIncomingOld, k + 1);
                            CalcPercentOnce(moneyIncomingOld, k + 1);
                        }
                        notifyItemChanged(i);

                        UpdateParentSum(moneyIncomingOld.getMoneyCategory().get_parentId(), delta, k);
                        ((FragmentMoneyIncomingAndCost)_fragmentViewCustom).FillResultOnce(k, false);
                        break;
                    }
                break;
            }
        }

        _fragmentPopupNew = null;
        return id;
    }

    public boolean DeleteFromDataset(MoneyIncoming milDeleted)
    {
        AddOrUpdateOrDeleteItem(null, milDeleted.get_categoryId(),
                milDeleted.get_increaseId());

        return true;
    }

    public boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            MoneyIncoming moneyIncomingDeleted = _currentItem.get_object().getMoneyIncoming(_currentId1);
            if (dbHelper.DeleteMoneyIncoming(moneyIncomingDeleted.get_id()))
                return DeleteFromDataset(moneyIncomingDeleted);
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyIncomingNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyIncoming) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    private void UpdateParentSum(Long parentCategoryId, int deltaSum, int k)
    {
        for (int i = 0; i < _dataSet.size(); i++)
            if (_dataSet.get(i).get_object().getMoneyCategoryId() == parentCategoryId)
            {
//                _dataSet.get(i).get_object().getMoneyIncoming(_currentId1).set_sum(
//                        _dataSet.get(i).get_object().getMoneyIncoming(_currentId1).get_sum() + deltaSum);
                _dataSet.get(i).get_object().getMoneyIncoming(_currentId1).set_sumLong(
                        _dataSet.get(i).get_object().getMoneyIncoming(_currentId1).get_sumLong() + deltaSum);
                CalcIncreasePercentOnce(_dataSet.get(i).get_object(), k);
                CalcPercentOnce(_dataSet.get(i).get_object(), k);
                if (k + 1 < _countMoneyIncrease)
                {
                    CalcIncreasePercentOnce(_dataSet.get(i).get_object(), k + 1);
                    CalcPercentOnce(_dataSet.get(i).get_object(), k + 1);
                }
                notifyItemChanged(i);
                break;
            }
    }

    public static class ItemViewHolder extends TreeViewAdapter.ItemViewHolder<TreeItem<MoneyIncomingList>>
    {
        private final TextView textViewName;
        private final TextView[] textViewIncoming;
        int _activeCol = 0;
        private final com.google.android.material.card.MaterialCardView[] cardViewIncoming;
        public void setName(String name) { textViewName.setText(name);}
        public void setTypeFace(Integer typeface) { textViewName.setTypeface(null, typeface); }
        public void SetIncoming(int i, String value)
        {
            textViewIncoming[i].setText(value);
        }
        @SuppressLint("ResourceAsColor")
        public void setActiveColumn(int column, int maxCnt, MoneyCategory moneyCategory,
                                    MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
        {
            _activeCol = column;
            for (int i = 0; i < maxCnt; i++)
                if (i == column)
                {
                    if (moneyCategory.isParent() || moneyIncomingAndCostSettings.get_categoryType().equals(EnumCategoryType.All))
                        textViewIncoming[i].setBackgroundResource(R.color.lightLightGrey);
                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    textViewIncoming[i].setTypeface(boldTypeface);
                }
        }
        public void SetCountMoneyIncrease(int cnt, int maxCnt)
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity)_context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            for (int i = 1; i < cnt; i++)
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardViewIncoming[i].getLayoutParams();
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                params.weight = 1;
                params.rightMargin = (int) (5 * displaymetrics.scaledDensity);
                cardViewIncoming[i].setLayoutParams(params);
            }
            for (int i = cnt; i < maxCnt; i++)
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardViewIncoming[i].getLayoutParams();
                params.width = 0;
                params.weight = 0;
                params.rightMargin = 0;
                cardViewIncoming[i].setLayoutParams(params);
            }

            final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                if (_context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    textViewName.getLayoutParams().width = displaymetrics.widthPixels / 2;
                }
                else
                {
                    textViewName.getLayoutParams().width = displaymetrics.widthPixels / 4;
                }
            }
        }

        public void ChangeWidthToTextViews(boolean needPadding, int cnt)
        {
            for (int i = 0; i < cnt; i++)
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardViewIncoming[i].getLayoutParams();
                params.leftMargin = needPadding ? 50 : 0;
                cardViewIncoming[i].setLayoutParams(params);
            }
        }

        public ItemViewHolder(@androidx.annotation.NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);

            textViewIncoming = new TextView[]
                    {
                            itemView.findViewById(R.id.textViewIncoming1),
                            itemView.findViewById(R.id.textViewIncoming2),
                            itemView.findViewById(R.id.textViewIncoming3),
                            itemView.findViewById(R.id.textViewIncoming4),
                            itemView.findViewById(R.id.textViewIncoming5)
                    };
            cardViewIncoming = new com.google.android.material.card.MaterialCardView[]
                    {
                            itemView.findViewById(R.id.cardViewIncoming1),
                            itemView.findViewById(R.id.cardViewIncoming2),
                            itemView.findViewById(R.id.cardViewIncoming3),
                            itemView.findViewById(R.id.cardViewIncoming4),
                            itemView.findViewById(R.id.cardViewIncoming5)
                    };
        }

        public void set_type4Category(MoneyIncomingList moneyIncomingList,
                                      MoneyIncomingAndCostSettings moneyIncomingAndCostSettings)
        {
            if (moneyIncomingList.getMoneyCategory().isParent())
                return;

            if (moneyIncomingAndCostSettings.get_categoryType().equals(EnumCategoryType.All))
                return;

            switch (moneyIncomingList.getMoneyCategory().get_type())
            {
                case Project:
                    for (int i = 0; i < 5; i++)
                        textViewIncoming[i].setBackgroundResource(R.color.lightGreen);
                    break;
                case Advanced:
                case WalletAccumulation:
                    for (int i = 0; i < 5; i++)
                        textViewIncoming[i].setBackgroundResource(R.color.lightOrange);
                    break;
                case Base:
                    for (int i = 0; i < 5; i++)
                        textViewIncoming[i].setBackgroundResource(R.color.lightLightGrey);
                    break;
                default:
            }
        }
    }
}
