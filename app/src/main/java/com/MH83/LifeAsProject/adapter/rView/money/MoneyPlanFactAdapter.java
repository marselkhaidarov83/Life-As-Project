package com.MH83.LifeAsProject.adapter.rView.money;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.TreeViewAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.data.Money.MoneyPlanFactDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeIncoming;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyPlan;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFact;
import com.MH83.LifeAsProject.model.Money.MoneyPlanFactSettings;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyCostPlanPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyIncomingPlanPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyPlanFact;
import com.MH83.LifeAsProject.view.Money.MoneyPlanFact.FragmentMoneyPlanPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.Calendar;
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

public class MoneyPlanFactAdapter
        extends TreeViewAdapter<MoneyPlanFactAdapter.ItemViewHolder, MoneyPlanFact,
        FragmentMoneyPlanPopupNew> implements IAUDAdapter<MoneyPlanFact>
{
    private final MoneyPlanFactSettings _moneyPlanFactSettings;
    private Long _sumPlan;
    private Long _sumFact;
    private Integer _dataType;

    public MoneyPlanFactAdapter(Context context, IFragmentViewCustom fragmentViewCustom,
                                MoneyPlanFactSettings moneyPlanFactSettings,
                                Integer dataType)
    {
        super(context, null, fragmentViewCustom, R.layout.item_tree_money_planfact);

        _moneyPlanFactSettings = moneyPlanFactSettings;
        _dataType = dataType;

        SetData(_moneyPlanFactSettings.get_expandedList());
    }

    public Double getSum(int column)
    {
        if (column == -1)
            return 0d;

        Long sum = 0L;

        switch (column)
        {
            case 1:
                for (TreeItem<MoneyPlanFact> item: _dataSet)
                    if (item.get_object().isParent())
                        if (item.get_object().get_moneyPlan() != null &&
                                item.get_object().get_moneyPlan().get_sumLong() != null)
                            sum += item.get_object().get_moneyPlan().get_sumLong();
                break;
            case 2:
                for (TreeItem<MoneyPlanFact> item: _dataSet)
                    if (item.get_object().isParent())
                        if (item.get_object().get_factValue() != null)
                            sum += item.get_object().get_factValue();
                break;
            case 3:
                for (TreeItem<MoneyPlanFact> item: _dataSet)
                    if (item.get_object().isParent())
                        if (item.get_object().get_deviation() != null)
                            sum += item.get_object().get_deviation();
                break;
        }

        return sum / 100d;
    }

    private void SetData(ArrayList<Long> expandedList)
    {
        GlobalClass gVar = (GlobalClass) _context.getApplicationContext();
        List<MoneyPlanFact> loadDataSetParent = MoneyPlanFactDataSource.LoadDataParent(
                _context, false,
                _moneyPlanFactSettings);

        if (loadDataSetParent == null)
            return;

        TreeItem<MoneyPlanFact> root = new TreeItem<>(null, true);
        root.setExpanded(true);

        ArrayList<Long> parents = new ArrayList<>();
        Hashtable<Long, TreeItem<MoneyPlanFact>> folders = new Hashtable<>();
        for (int i = 0; i < loadDataSetParent.size(); i++)
        {
            TreeItem<MoneyPlanFact> folderCategory = new TreeItem<>(loadDataSetParent.get(i), true);
            MoneyPlanFact moneyPlanFact = folderCategory.get_object();
            MoneyPlan moneyPlan = new MoneyPlan(0L, moneyPlanFact.getMoneyCategory(),
                    _moneyPlanFactSettings.get_customPeriodClass().get_start_dateC().get(Calendar.MONTH),
                    _moneyPlanFactSettings.get_customPeriodClass().get_start_dateC().get(Calendar.YEAR),
                    0L, EnumTypeIncoming.Sum, 0L);
            moneyPlanFact.set_moneyPlan(moneyPlan);

            folders.put(loadDataSetParent.get(i).getMoneyCategoryId(), folderCategory);
            parents.add(loadDataSetParent.get(i).getMoneyCategoryId());
        }

        List<MoneyPlanFact> loadDataSet = MoneyPlanFactDataSource.LoadDataChildren(
            _context, parents, _moneyPlanFactSettings);

        for (int k = 0; k < loadDataSet.size(); k++)
        {
            TreeItem<MoneyPlanFact> singleCategory = new TreeItem<>(loadDataSet.get(k), false);
            TreeItem<MoneyPlanFact> folderCategory = folders.get(loadDataSet.get(k).getMoneyCategory().get_parentId());
            MoneyPlanFact moneyPlanFact = folderCategory.get_object();

            boolean existSum = false;
            if (singleCategory.get_object().get_moneyPlan() != null &&
                    singleCategory.get_object().get_moneyPlan().get_sumLong() != null)
            {
                moneyPlanFact.get_moneyPlan().set_sumLong(
                        moneyPlanFact.get_moneyPlan().get_sumLong() +
                                singleCategory.get_object().get_moneyPlan().get_sumLong());
                existSum = true;
            }

            if (singleCategory.get_object().get_factValue() != null)
            {
                moneyPlanFact.inc_factValue(singleCategory.get_object().get_factValue());
                existSum = true;
            }

            if (gVar.get_hasUnpaidSubscription() ||
                    _moneyPlanFactSettings.get_showBlankData() || existSum)
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

        _sumPlan = 0L;
        _sumFact = 0L;
        for (Long id: parents)
        {
            if (folders.get(id).get_object().get_moneyPlan().get_sumLong() != null)
                _sumPlan += folders.get(id).get_object().get_moneyPlan().get_sumLong();
            if (folders.get(id).get_object().get_factValue() != null)
                _sumFact += folders.get(id).get_object().get_factValue();
        }

        CalcPercent(root);

        setRoot(root, false);
    }

    private void CalcPercent(TreeItem<MoneyPlanFact> root)
    {
        CalcPercentOnce(root.get_object());
        for (TreeItem<MoneyPlanFact> item: root.getChildren())
            CalcPercent(item);
    }

    private void CalcPercentOnce(MoneyPlanFact moneyPlanFact)
    {
        if (moneyPlanFact != null &&
                moneyPlanFact.get_moneyPlan() != null &&
                moneyPlanFact.get_moneyPlan().get_sumLong() != null &&
                _sumPlan != 0)
            moneyPlanFact.get_moneyPlan().set_percent(
                    (int) (100 * moneyPlanFact.get_moneyPlan().get_sumLong() / _sumPlan));

        if (moneyPlanFact != null && moneyPlanFact.get_factValue() != null &&
            _sumFact != 0)
            moneyPlanFact.set_percentFact((int) (100 * moneyPlanFact.get_factValue() / _sumFact));
    }

    public void SaveExpandedList(boolean showExpandedButton)
    {
        ArrayList<Long> list = new ArrayList<>();

        for (TreeItem<MoneyPlanFact> mil : _dataSet)
            if (mil.get_object().isParent() && mil.isExpanded())
                list.add(mil.get_object().getMoneyCategoryId());

        _moneyPlanFactSettings.set_expandedList(list);
        _moneyPlanFactSettings.set_showExpandButton(showExpandedButton);
        GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();
        globalVar.set_moneyPlanFactSettings(_moneyPlanFactSettings);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(View view)
    {
        return new ItemViewHolder(view, _context);
    }

    public void set_dataType(Integer dataType)
    {
        this._dataType = dataType;
        notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.setDataType(_dataType);
        super.onBindViewHolder(holder, position);

        MoneyCategory moneyCategory = _dataSet.get(position).get_object().getMoneyCategory();
        MoneyPlanFact moneyPlanFact = _dataSet.get(position).get_object();
        MoneyPlan moneyPlan = moneyPlanFact.get_moneyPlan();

        if (_dataSet.get(position).get_object().getMoneyCategory().isParent())
            holder.setTypeFace(Typeface.BOLD);
        else
            holder.setTypeFace(Typeface.NORMAL);

        if (moneyCategory.isParent())
            holder.textViewName.setOnClickListener(view ->
                    ChangeExpandedToItem(_dataSet.get(position), holder));
        else
        {
            if (_moneyPlanFactSettings.get_customPeriodClass().get_id() == CustomPeriodClass.MONTH_ID)
                holder.textViewPlan.setOnClickListener(view ->
                {
                    _currentItem = _dataSet.get(position);
                    EnumTypeAction typeAction = EnumTypeAction.Update;
                    if (moneyPlan == null)
                    {
                        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
                        if (!MoneyPlan.canAdd(_context, globalVariable))
                        {
                            SubscriptionClick();
                            return;
                        }
                        typeAction = EnumTypeAction.Insert;
                    }

                    if (_moneyPlanFactSettings.get_typeCategory().equals(EnumTypeCategory.Increase))
                        new FragmentMoneyIncomingPlanPopupNew((AppCompatActivity) _context, moneyPlanFact,
                                typeAction, _fragmentViewCustom, moneyCategory,
                                _moneyPlanFactSettings.get_customPeriodClass().get_start_dateC().get(Calendar.MONTH),
                                _moneyPlanFactSettings.get_customPeriodClass().get_start_dateC().get(Calendar.YEAR)).PopupShow();
                    else
                        new FragmentMoneyCostPlanPopupNew((AppCompatActivity) _context, moneyPlanFact,
                                typeAction, _fragmentViewCustom, moneyCategory,
                                _moneyPlanFactSettings.get_customPeriodClass().get_start_dateC().get(Calendar.MONTH),
                                _moneyPlanFactSettings.get_customPeriodClass().get_start_dateC().get(Calendar.YEAR)).PopupShow();

                });
        }

        holder.textViewFact.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                Long categoryId;
                Long subCategoryId = null;
                if (moneyPlanFact.isParent())
                {
                    categoryId = moneyPlanFact.getMoneyCategoryId();
                }
                else
                {
                    categoryId = _dataSet.get(position).getParent().get_object().getMoneyCategoryId();
                    subCategoryId = moneyPlanFact.getMoneyCategoryId();
                }

                GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();
                globalVar.set_pnl_transactionType(EnumTransactionType.PNL);
                globalVar.get_moneyTransactionSettings().set_typeCategory(
                        _moneyPlanFactSettings.get_typeCategory());

                ((FragmentMoneyPlanFact) _fragmentViewCustom).NavigateToPNL(
                        categoryId, subCategoryId);

                return false;
            }
        });
    }

    @Override
    protected void ClickToLeadingIcon(TreeItem<MoneyPlanFact> item, @androidx.annotation.NonNull ItemViewHolder holder)
    {
        super.ClickToLeadingIcon(item, holder);

        SaveExpandedList(_moneyPlanFactSettings.get_showExpandButton());
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

    //region Subscription
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

            CustomTrack.PaidFormOpenFromPlan();

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

                    CustomTrack.PaidFormOpenFromPlan();

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

    //region Buttons click
    @Override
    public boolean canActionRightImage()
    {
        return false;
    }
    @Override
    public boolean canActionLeftImage() { return false; }

    @Override
    public boolean LeftButtonClick()
    {
        return false;
    }

    @Override
    public boolean RightButtonClick()
    {
        return false;
    }
    //endregion

    public Long AddItem(MoneyPlanFact moneyPlanFact)
    {
        return AddOrUpdateItem(moneyPlanFact);
    }

    public Long UpdateItem(MoneyPlanFact moneyPlanFact)
    {
        return AddOrUpdateItem(moneyPlanFact);
    }

    private Long AddOrUpdateItem(MoneyPlanFact moneyPlanFact)
    {
        return AddOrUpdateOrDeleteItem(moneyPlanFact, moneyPlanFact.getMoneyCategoryId());
    }

    @SuppressLint("NotifyDataSetChanged")
    private Long AddOrUpdateOrDeleteItem(MoneyPlanFact moneyPlanFact,
                                         long moneyCategoryId)
    {
        long id = -1L;
        int delta = 0;
        if (moneyPlanFact != null)
        {
            id = moneyPlanFact.get_id();
            delta += moneyPlanFact.get_moneyPlan().get_sumLong();
        }

        for (int i = 0; i < _dataSet.size(); i++)
        {
            MoneyPlanFact moneyPlanFactOld = _dataSet.get(i).get_object();
            if (moneyCategoryId == moneyPlanFactOld.getMoneyCategoryId())
            {
                if (moneyPlanFactOld.get_moneyPlan() != null &&
                        moneyPlanFactOld.get_moneyPlan().get_sumLong() != null)
                    delta -= moneyPlanFactOld.get_moneyPlan().get_sumLong();
                if (moneyPlanFact == null)
                    moneyPlanFactOld.set_moneyPlan(null);
                else
                    moneyPlanFactOld.set_moneyPlan(moneyPlanFact.get_moneyPlan());
                CalcPercentOnce(moneyPlanFactOld);
                notifyItemChanged(i);

                _sumPlan += delta;
                UpdateParentSum(moneyPlanFactOld.getMoneyCategory().get_parentId(), delta);
                ((FragmentMoneyPlanFact)_fragmentViewCustom).FillResultOnce(1, _dataType,false);
                ((FragmentMoneyPlanFact)_fragmentViewCustom).FillResultOnce(3, _dataType,true);

                GlobalClass gVar = (GlobalClass) _context.getApplicationContext();
                if (gVar.get_moneyPlanFactSettings().get_showPercent())
                {
                    CalcPercent(_root);
                    notifyDataSetChanged();
                }

                break;
            }
        }
        return id;
    }

    public boolean DeleteFromDataset(MoneyPlanFact moneyPlanFactDeleted)
    {
        AddOrUpdateOrDeleteItem(null
                , moneyPlanFactDeleted.getMoneyCategoryId());

        return true;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    private void UpdateParentSum(Long parentCategoryId, int deltaSum)
    {
        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).get_object().getMoneyCategoryId(), parentCategoryId))
            {
                _dataSet.get(i).get_object().get_moneyPlan().set_sumLong(
                        _dataSet.get(i).get_object().get_moneyPlan().get_sumLong() + deltaSum);
                CalcPercentOnce(_dataSet.get(i).get_object());
                notifyItemChanged(i);
                break;
            }
    }

    public static class ItemViewHolder extends TreeViewAdapter.ItemViewHolder<TreeItem<MoneyPlanFact>>
    {
        private Integer _dataType;
        public void setDataType(Integer dataType) { _dataType = dataType; }

        private final TextView textViewName;
        public final TextView textViewPlan, textViewFact, textViewDelta;
        final com.google.android.material.card.MaterialCardView
                cardViewPlan, cardViewFact, cardViewDelta;

        private void setName(String name) { textViewName.setText(name); }
        private void setPlan(MoneyPlan moneyPlan)
        {
            if (_dataType == null || _dataType.equals(1))
            {
                if (moneyPlan != null && moneyPlan.get_sumLong() != null && moneyPlan.get_sumLong() != 0L)
                {
                    GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();

                    textViewPlan.setText(String.valueOf(moneyPlan.get_StringSum(
                            false,
                            globalVar.get_moneyPlanFactSettings().get_showPercent() &&
                                        !globalVar.get_hasUnpaidSubscription(),
                            globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                                    _context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)));
                }
                else
                    textViewPlan.setText("");
            }

            if (Objects.equals(_dataType,1))
            {
                cardViewPlan.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                ((LinearLayout.LayoutParams) cardViewPlan.getLayoutParams()).weight = 1;
                ((LinearLayout.LayoutParams) cardViewPlan.getLayoutParams()).setMarginEnd(
                        (int) _context.getResources().getDimension(R.dimen.BasePadding));
            }
            else if (_dataType != null)
            {
                cardViewPlan.getLayoutParams().width = 0;
                ((LinearLayout.LayoutParams) cardViewPlan.getLayoutParams()).weight = 0;
                ((LinearLayout.LayoutParams) cardViewPlan.getLayoutParams()).setMarginEnd(0);
                ((LinearLayout.LayoutParams) cardViewPlan.getLayoutParams()).setMargins(0, 0, 0, 0);
            }
        }
        private void setFact(MoneyPlanFact moneyPlanFact)
        {
            if (_dataType == null || _dataType.equals(2))
            {
                if (moneyPlanFact.get_factValue() != null && moneyPlanFact.get_factValue() != 0L)
                {
                    GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();

                    textViewFact.setText(String.valueOf(moneyPlanFact.get_StringFact(
                            false,
                            globalVar.get_moneyPlanFactSettings().get_showPercent() &&
                                    !globalVar.get_hasUnpaidSubscription(),
                            globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                                    _context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)));
                }
                else
                    textViewFact.setText("");
            }

            if (Objects.equals(_dataType,2))
            {
                cardViewFact.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                ((LinearLayout.LayoutParams) cardViewFact.getLayoutParams()).weight = 1;
                ((LinearLayout.LayoutParams) cardViewFact.getLayoutParams()).setMarginEnd(
                        (int) _context.getResources().getDimension(R.dimen.BasePadding));
            }
            else if (_dataType != null)
            {
                cardViewFact.getLayoutParams().width = 0;
                ((LinearLayout.LayoutParams) cardViewFact.getLayoutParams()).weight = 0;
                ((LinearLayout.LayoutParams) cardViewFact.getLayoutParams()).setMarginEnd(0);
                ((LinearLayout.LayoutParams) cardViewFact.getLayoutParams()).setMargins(0, 0, 0, 0);
            }
        }
        private void setDeviation(MoneyPlanFact moneyPlanFact)
        {
            if (_dataType == null || _dataType.equals(3))
            {
                if (moneyPlanFact.get_deviation() != null && moneyPlanFact.get_deviation() != 0L)
                {
                    GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();

                    textViewDelta.setText(String.valueOf(moneyPlanFact.get_StringDeviation(
                            false,
                            false,
                            globalVar.get_typeDevice() == EnumTypeDevice.Phone &&
                                    _context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)));

                    textViewDelta.setBackgroundResource(moneyPlanFact.get_deviation() >= 0d ?
                            R.color.lightLightGreen : R.color.lightlightRed);
                }
                else
                {
                    textViewDelta.setText("");
                    textViewDelta.setBackgroundResource(0);
                }
            }

            if (Objects.equals(_dataType,3))
            {
                cardViewDelta.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                ((LinearLayout.LayoutParams) cardViewDelta.getLayoutParams()).weight = 1;
                ((LinearLayout.LayoutParams) cardViewDelta.getLayoutParams()).setMarginEnd(
                        (int) _context.getResources().getDimension(R.dimen.BasePadding));
            }
            else if (_dataType != null)
            {
                cardViewDelta.getLayoutParams().width = 0;
                ((LinearLayout.LayoutParams) cardViewDelta.getLayoutParams()).weight = 0;
                ((LinearLayout.LayoutParams) cardViewDelta.getLayoutParams()).setMarginEnd(0);
                ((LinearLayout.LayoutParams) cardViewDelta.getLayoutParams()).setMargins(0, 0, 0, 0);
            }
        }

        public void setTypeFace(Integer typeface) { textViewName.setTypeface(null, typeface); }

        public ItemViewHolder(@androidx.annotation.NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);

            textViewPlan = itemView.findViewById(R.id.textViewPlan);
            textViewFact = itemView.findViewById(R.id.textViewFact);
            textViewDelta = itemView.findViewById(R.id.textViewDelta);

            cardViewPlan = itemView.findViewById(R.id.cardViewPlan);
            cardViewFact = itemView.findViewById(R.id.cardViewFact);
            cardViewDelta = itemView.findViewById(R.id.cardViewDelta);
        }

        @Override
        public void setObject(TreeItem<MoneyPlanFact> treeItemMoneyPlanFact)
        {
            super.setObject(treeItemMoneyPlanFact);

            MoneyPlanFact moneyPlanFact = treeItemMoneyPlanFact.get_object();
            setName(moneyPlanFact.getSValue());
            setPlan(moneyPlanFact.get_moneyPlan());
            setFact(moneyPlanFact);
            setDeviation(moneyPlanFact);
        }
    }
}
