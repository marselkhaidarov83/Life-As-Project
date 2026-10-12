package com.MH83.LifeAsProject.adapter.rView.money;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.IconDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumCategoryType;
import com.MH83.LifeAsProject.interfac.enums.EnumIndependent;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.custom.TreeViewAdapter;
import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.model.Money.MoneyCategorySettings;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyInc4CostIndependent;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyCategory.FragmentMoneyCategoryPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyInc4Cost.FragmentMoneyInc4CostPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

public class MoneyCategoryAdapter
        extends TreeViewAdapter<MoneyCategoryAdapter.ItemViewHolder, MoneyCategory,
                                FragmentMoneyCategoryPopupNew>
        implements IAUDAdapter<MoneyCategory>
{
    private final EnumTypeCategory _typeCategory;
    private MoneyCategorySettings _moneyCategorySettings;
    private final Hashtable<Long, Icon> _icons;
    private final GlobalClass gVar;

    public MoneyCategoryAdapter(Context context,
                                IFragmentViewCustom<MoneyCategory> fragmentViewCustom,
                                MoneyCategorySettings moneyCategorySettings)
    {
        super(context, null, fragmentViewCustom, R.layout.item_tree_money_category);

        gVar = (GlobalClass) context.getApplicationContext();
        _moneyCategorySettings = moneyCategorySettings;
        _typeCategory = gVar.get_moneyCategorySettingEnumTypeCategory();
        _icons = IconDataSource.GetHashtable(_typeCategory);
        SetData(_moneyCategorySettings.get_expandedList());
    }

    private void SetData(List<Long> expandedId)
    {
        List<MoneyCategory> dataSetParent = MoneyCategoryDataSource.LoadDataParent(_context,
                false, _moneyCategorySettings);

        TreeItem<MoneyCategory> root = new TreeItem<>(null, true);
        for (int i = 0; i < dataSetParent.size(); i++)
        {
            TreeItem<MoneyCategory> folderCategory = new TreeItem<>(dataSetParent.get(i),
                    dataSetParent.get(i).getTypeCategory() != EnumTypeCategory.Wallet);
            if (expandedId != null && expandedId.size() > 0)
                folderCategory.setExpanded(expandedId.contains(dataSetParent.get(i).getCategoryId()));

            List<MoneyCategory> dataSet = MoneyCategoryDataSource.LoadDataChildren(_context,
                    dataSetParent.get(i).get_id(), _moneyCategorySettings);

            for (int k = 0; k < dataSet.size(); k++)
            {
                TreeItem<MoneyCategory> singleCategory = new TreeItem<>(dataSet.get(k), false);

                if (gVar.get_hasUnpaidSubscription() ||
                        _moneyCategorySettings.get_showBlankData() ||
                        singleCategory.get_object().get_sumLong() != 0L)
                {
                    folderCategory.getChildren().add(singleCategory);
                    (folderCategory.get_object()).set_hasChild(true);
                    (folderCategory.get_object()).changeSumLong(singleCategory.get_object().get_sumLong());
                }
            }

            if (gVar.get_hasUnpaidSubscription() || _moneyCategorySettings.get_showBlankData())
                root.getChildren().add(folderCategory);
            else
            {
                if (_typeCategory.equals(EnumTypeCategory.Wallet))
                {
                    if (folderCategory.get_object().get_sumLong() != 0L)
                        root.getChildren().add(folderCategory);
                }
                else
                {
                    if (folderCategory.getChildren().size() > 0)
                        root.getChildren().add(folderCategory);
                }
            }
        }

        setRoot(root);
    }

    public void RefreshData(MoneyCategorySettings moneyCategorySettings)
    {
        _moneyCategorySettings = moneyCategorySettings;
        SetData(_moneyCategorySettings.get_expandedList());
    }

    @Override
    public ItemViewHolder onCreateViewHolder(View view)
    {
        ImageView imageView = view.findViewById(R.id.imageViewTransferBudget);
//        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
//        if (globalVariable.get_hasUnpaidSubscription())
//        {
//            imageView.setVisibility(View.INVISIBLE);
//            imageView.getLayoutParams().width = 0;
//        }

        return new ItemViewHolder(view, _context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.setIcons(_icons);
        super.onBindViewHolder(holder, position);
        TreeItem<MoneyCategory> treeItem = _dataSet.get(position);
        MoneyCategory moneyCategory = treeItem.get_object();

        holder.imageViewTransferBudget.setOnClickListener(view ->
        {
            GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
            if (!MoneyInc4CostIndependent.canAdd(_context, globalVariable))
            {
                SubscriptionClick(false);
                return;
            }

            globalVariable.set_MAccTypeCategory(EnumTypeCategory.Wallet);

            new FragmentMoneyInc4CostPopupNew(
                    (AppCompatActivity) _context,
                    null,
                    null,
                    EnumTypeAction.Insert,
                    null,
                    _fragmentViewCustom,
                    null,
                    treeItem.getParent().get_object(),
                    moneyCategory.get_id(),
                    moneyCategory.get_icon(),
                    Calendar.getInstance(),
                    Calendar.getInstance(),
                    EnumIndependent.Independent,
                    moneyCategory.get_sumLong() < 0L ? -moneyCategory.get_sumLong() : 0).PopupShow();

        });

        holder.itemView.setOnClickListener(view ->
        {
            moneyCategory.set_hasChild(treeItem.getChildren().size() > 0);
//            _currentItem = _dataSet.get(position);
            _currentItem = treeItem;

            _fragmentPopupNew = new FragmentMoneyCategoryPopupNew((AppCompatActivity) _context,
                    moneyCategory, EnumTypeAction.Update, _fragmentViewCustom,
                    _typeCategory);
            _fragmentPopupNew.PopupShow();
        });
    }

    public void SaveExpandedList(boolean showExpandedButton)
    {
        _moneyCategorySettings.set_expandedList(GetExpandedId());
        _moneyCategorySettings.set_showExpandButton(showExpandedButton);
        GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();
        globalVar.set_moneyCategorySettingsHT(_moneyCategorySettings);
    }

    @Override
    protected void ClickToLeadingIcon(TreeItem<MoneyCategory> item, @androidx.annotation.NonNull ItemViewHolder holder)
    {
        super.ClickToLeadingIcon(item, holder);

        SaveExpandedList(_moneyCategorySettings.get_showExpandButton());
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

    @Override
    public boolean canActionRightImage()
    {
        return _currentItem.get_object().canDelete(_context);
    }

    @Override
    public boolean canActionLeftImage() { return true; }

    @Override
    public boolean LeftButtonClick()
    {
        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        if (!MoneyCategory.canAdd(_context, globalVariable))
        {
            SubscriptionClick(true);
            return false;
        }

        notifyItemChanged(_dataSet.indexOf(_currentItem));
        MoneyCategory moneyCategory = _currentItem.get_object().Clone();

        _fragmentPopupNew = new FragmentMoneyCategoryPopupNew((AppCompatActivity) _context,
                moneyCategory, EnumTypeAction.Insert, _fragmentViewCustom,
                moneyCategory.getTypeCategory());
        _fragmentPopupNew.PopupShow();

        _currentItem = null;
        return true;
    }

    //region Subscription
    protected void SubscriptionClick(boolean cat)
    {
        if (GlobalClass.SubscrGoogle())
            SubscriptGoogle(cat);

        if (GlobalClass.SubscrRustore())
            SubscriptRustore(cat);
    }

    private void SubscriptRustore(boolean cat)
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

            if (cat)
                CustomTrack.PaidFormOpenFromCategory();
            else
                CustomTrack.PaidFormOpenFromPNL();

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

    private void SubscriptGoogle(boolean cat)
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

                    if (cat)
                        CustomTrack.PaidFormOpenFromCategory();
                    else
                        CustomTrack.PaidFormOpenFromPNL();

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

    @Override
    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }

    public Long AddItem(MoneyCategory moneyCategory)
    {
        if (!gVar.get_hasUnpaidSubscription())
            if (!_moneyCategorySettings.get_showArchive() && moneyCategory.get_archive())
                return 0L;

        if (!gVar.get_hasUnpaidSubscription())
            if (!_moneyCategorySettings.get_categoryType().equals(EnumCategoryType.All))
            {
                ArrayList<EnumCategoryType> statusList = _moneyCategorySettings.get_categoryList();
                if (!statusList.contains(moneyCategory.get_type()))
                    return 0L;
            }

        int insertPosition = 0;

        if (moneyCategory.get_parentId() == 0)
        {
            TreeItem<MoneyCategory> folderCategory = new TreeItem<>(moneyCategory,
                    moneyCategory.getTypeCategory() != EnumTypeCategory.Wallet);
            insertPosition = addLevel2(folderCategory);
        }
        else
        {
            for (int i = 0; i < _dataSet.size(); i++)
                if ((_dataSet.get(i).get_object()).get_id().equals(moneyCategory.get_parentId()))
                {
                    _dataSet.get(i).get_object().set_hasChild(true);

                    if (!_dataSet.get(i).isExpanded())
                    {
                        _dataSet.get(i).setExpanded(true);
                        insertAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                        notifyItemRangeInserted(i + 1, _dataSet.get(i).getChildren().size());
                    }

                    TreeItem<MoneyCategory> singleCategory = new TreeItem<>(moneyCategory,false);
                    insertPosition = addLevel3(singleCategory, moneyCategory.get_parentId());

                    _dataSet.get(i).get_object().changeSumLong(moneyCategory.get_sumLong());
                    notifyItemChanged(i);
                    break;
                }
        }

        return (long) insertPosition;
    }

    public Long UpdateItem(MoneyCategory moneyCategoryNew)
    {
        if (!gVar.get_hasUnpaidSubscription())
            if (!_moneyCategorySettings.get_showArchive() && moneyCategoryNew.get_archive())
                DeleteFromDataset(moneyCategoryNew);

        if (!gVar.get_hasUnpaidSubscription())
            if (!_moneyCategorySettings.get_categoryType().equals(EnumCategoryType.All))
            {
                ArrayList<EnumCategoryType> statusList = _moneyCategorySettings.get_categoryList();
                if (!statusList.contains(moneyCategoryNew.get_type()))
                    DeleteFromDataset(moneyCategoryNew);
            }

        for (int i = 0; i < _dataSet.size(); i++)
        {
            MoneyCategory moneyCategoryOld = _dataSet.get(i).get_object();
            if (Objects.equals(moneyCategoryNew.get_id(), moneyCategoryOld.get_id()))
            {
                if (Objects.equals(moneyCategoryOld.get_parentId(), moneyCategoryNew.get_parentId()))
                {
                    _dataSet.get(i).set_object(moneyCategoryNew);
                    notifyItemChanged(i);

                    for (int k = i - 1; k >=0; k--)
                        if (Objects.equals(_dataSet.get(k).get_object().get_id(), moneyCategoryNew.get_parentId()))
                        {
                            _dataSet.get(k).get_object().changeSumLong(
                                    moneyCategoryNew.get_sumLong() - moneyCategoryOld.get_sumLong());
                            notifyItemChanged(k);
                            break;
                        }

                    if (moneyCategoryNew.get_sumLong() == 0L && !_moneyCategorySettings.get_showBlankData())
                        DeleteFromDataset(moneyCategoryNew);
                }
                else
                {
                    try { DeleteFromDataset(moneyCategoryOld); }
                    catch (Exception e) {}
                    AddItem(moneyCategoryNew);
                }
                break;
            }
        }

        return moneyCategoryNew.get_id();
    }

    public boolean DeleteFromDataset(TreeItem<MoneyCategory> moneyCategoryTreeItem) throws Exception
    {
        return DeleteFromDataset(moneyCategoryTreeItem.get_object());
    }

    public boolean DeleteFromDataset(MoneyCategory mcDeleted)
    {
        for (int i = 0; i < _dataSet.size(); i++)
        {
            MoneyCategory moneyCategory = _dataSet.get(i).get_object();
            if (Objects.equals(moneyCategory.get_id(), mcDeleted.get_id()))
            {
                _dataSet.remove(i);
                if (mcDeleted.hasParent())
                {
                    for (int k = i - 1; k >= 0; k--)
                    {
                        MoneyCategory mc2 = _dataSet.get(k).get_object();
                        if (Objects.equals(mc2.get_id(), mcDeleted.get_parentId()))
                        {
                            _dataSet.get(k).getChildren().remove(i - k - 1);
                            _dataSet.get(k).get_object().changeSumLong(-mcDeleted.get_sumLong());
                            notifyItemChanged(k);
                            mc2.set_hasChild(_dataSet.get(k).getChildren().size() > 0);
                            break;
                        }
                    }
                }
                notifyItemRemoved(i);
                break;
            }
        }
        return true;
    }

    public boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (dbHelper.DeleteMoneyCategory(_currentItem.get_object().get_id()))
                return DeleteFromDataset(_currentItem.get_object());
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyCategoryNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyCategory) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void ChangeByInc4Cost(MoneyInc4Cost moneyInc4Cost)
    {
        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).get_object().get_id(), moneyInc4Cost.get_inc_subCategory_id()))
            {
                _dataSet.get(i).get_object().changeSumLong(moneyInc4Cost.get_sumLong());
                notifyItemChanged(i);

                for (int k = 0; k < _dataSet.size(); k++)
                    if (Objects.equals(_dataSet.get(k).get_object().get_id(), _dataSet.get(i).get_object().get_parentId()))
                    {
                        _dataSet.get(k).get_object().changeSumLong(moneyInc4Cost.get_sumLong());
                        notifyItemChanged(k);
                        break;
                    }

                if (_dataSet.get(i).get_object().get_sumLong() == 0d && !_moneyCategorySettings.get_showBlankData())
                    DeleteFromDataset(_dataSet.get(i).get_object());
                break;
            }

        boolean isFind = false;
        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).get_object().get_id(), moneyInc4Cost.get_cost_subCategory_id()))
            {
                _dataSet.get(i).get_object().changeSumLong(-moneyInc4Cost.get_sumLong());
                notifyItemChanged(i);

                for (int k = 0; k < _dataSet.size(); k++)
                    if (Objects.equals(_dataSet.get(k).get_object().get_id(), _dataSet.get(i).get_object().get_parentId()))
                    {
                        _dataSet.get(k).get_object().changeSumLong(-moneyInc4Cost.get_sumLong());
                        notifyItemChanged(k);
                        break;
                    }

                if (_dataSet.get(i).get_object().get_sumLong() == 0d && !_moneyCategorySettings.get_showBlankData())
                    DeleteFromDataset(_dataSet.get(i).get_object());
                isFind = true;
                break;
            }

        if (!isFind)
            for (int i = 0; i < _dataSet.size(); i++)
                if (Objects.equals(_dataSet.get(i).get_object().get_id(), moneyInc4Cost.getCostCategoryId()))
                {
                    boolean findChild = false;
                    for (TreeItem<MoneyCategory> item: _dataSet.get(i).getChildren())
                        if (Objects.equals(item.get_object().get_id(), moneyInc4Cost.get_cost_subCategory_id()))
                        {
                            item.get_object().changeSumLong(-moneyInc4Cost.get_sumLong());
                            findChild = true;
                            break;
                        }

                    if (!findChild)
                        AddItem(MoneyCategoryDataSource.LoadOne(_context, moneyInc4Cost.get_cost_subCategory_id()));

                    _dataSet.get(i).get_object().changeSumLong(-moneyInc4Cost.get_sumLong());
                    notifyItemChanged(i);
                    break;
                }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        Long updateId = _dataSet.get(fromPosition).get_object().get_id();

        if (_dataSet.get(fromPosition).get_object().isChild())
        //Изменения для 3-его уровня
        {
            int oldParentIndex = 0;
            TreeItem<MoneyCategory> oldParent = null;
            int start = toPosition;
            int end = fromPosition;
            int endForUpdate = fromPosition;
            if (fromPosition < toPosition)
            {
                start = fromPosition;
                end = toPosition;
                endForUpdate = toPosition;
            }

            for (int i = fromPosition; i >= 0; i--)
                if (_dataSet.get(i).get_object().isParent())
                {
                    oldParent = _dataSet.get(i);
                    oldParentIndex = i;
                    break;
                }

            if (fromPosition < toPosition)
                for (int i = start; i < end; i++)
                {
                    if (_dataSet.get(i + 1).get_object().isChild())
                    {
                        int pos = _dataSet.get(i).get_object().get_position();
                        _dataSet.get(i).get_object().set_position(_dataSet.get(i + 1).get_object().get_position());
                        _dataSet.get(i + 1).get_object().set_position(pos);
                        Collections.swap(oldParent.getChildren(), i - oldParentIndex - 1,i - oldParentIndex);
                    }
                    else
                    {
                        Long parentId = _dataSet.get(i + 1).get_object().get_id();
                        _dataSet.get(i).get_object().set_position(1);
                        _dataSet.get(i).get_object().set_parentId(parentId);

                        if (!_dataSet.get(i + 1).isExpanded())
                            Expand(_dataSet.get(i + 1).get_object().get_id());

                        for (int k = i + 2; k < _dataSet.size(); k++)
                            if (Objects.equals(_dataSet.get(k).get_object().get_parentId(), parentId))
                            {
                                _dataSet.get(k).get_object().set_position(_dataSet.get(k).get_object().get_position() + 1);
                                endForUpdate = k;
                            }

                        int childIndex = 0;
                        if (oldParent.isExpanded())
                            childIndex = oldParent.getChildren().size() - 1;

                        _dataSet.get(i + 1).getChildren().add(0, oldParent.getChildren().get(childIndex));
                        oldParent.getChildren().remove(childIndex);
                        oldParent = _dataSet.get(i + 1);
                    }

                    Collections.swap(_dataSet, i, i + 1);
                }
            else
                for (int i = end; i > start; i--)
                {
                    if (_dataSet.get(i - 1).get_object().isChild())
                    {
                        int pos = _dataSet.get(i).get_object().get_position();
                        _dataSet.get(i).get_object().set_position(_dataSet.get(i - 1).get_object().get_position());
                        _dataSet.get(i - 1).get_object().set_position(pos);
                        Collections.swap(oldParent.getChildren(), i - oldParentIndex - 1,i - oldParentIndex - 2);
                    }
                    else
                    {
                        if (i - 1 == 0)
                        {
                            _dataSet.get(i).get_object().set_position(1);
                            _dataSet.get(i).get_object().set_parentId(0L);
                            int parentId = 0;
                            for (int k = 0; k < _dataSet.size(); k++)
                                if (_dataSet.get(k).get_object().get_parentId() == parentId &&
                                        !Objects.equals(_dataSet.get(k).get_object().get_id(), updateId))
                                {
                                    _dataSet.get(k).get_object().set_position(_dataSet.get(k).get_object().get_position() + 1);
                                    endForUpdate = k;
                                }

                            oldParent.getChildren().remove(0);
                            oldParent = null;
                        }
                        else
                        {
                            TreeItem<MoneyCategory> newParent = null;
                            for (int k = i - 2; k >= 0; k--)
                                if (_dataSet.get(k).get_object().isParent())
                                {
                                    newParent = _dataSet.get(k);
                                    break;
                                }

                            _dataSet.get(i).get_object().set_parentId(newParent.get_object().get_id());
                            if (!newParent.isExpanded())
                            {
                                Expand(newParent.get_object().get_id());
                                i = i + newParent.getChildren().size();
                                end = end + newParent.getChildren().size();
                                endForUpdate = endForUpdate + newParent.getChildren().size();
                                start = start + newParent.getChildren().size();
                            }
                            _dataSet.get(i).get_object().set_position(_dataSet.get(i - 2).get_object().get_position() + 1);

                            int childIndex = 0;
                            if (!oldParent.isExpanded())
                                childIndex = oldParent.getChildren().size() - 1;

                            newParent.getChildren().add(oldParent.getChildren().get(childIndex));
                            oldParent.getChildren().remove(childIndex);
                            oldParent = newParent;
                        }
                    }

                    Collections.swap(_dataSet, i, i - 1);
                }

            try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
            {
                for (int i = start; i <= endForUpdate; i++)
                    if (_dataSet.get(i).get_object().isChild() ||
                            (_dataSet.get(i).get_object().isParent() && (i == 0)))
                    {
                        if (Objects.equals(_dataSet.get(i).get_object().get_id(), updateId))
                        {
                            if (!dbHelper.UpdateMoneyCategory(_dataSet.get(i).get_object()))
                                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyCategory),
                                        Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (!dbHelper.UpdateMoneyCategoryPosition(_dataSet.get(i).get_object().get_id(),
                                    _dataSet.get(i).get_object().get_position()))
                                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyCategoryPosition),
                                        Toast.LENGTH_SHORT).show();
                        }
                    }
            }
            catch (Exception e)
            {
                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyCategoryPosition) +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            notifyItemMoved(fromPosition, toPosition);
            for (int i = start; i <= endForUpdate; i++)
                notifyItemChanged(i);
        }
        else
        //Изменение для 2-ого уровня
        {
            int oldPosition = _dataSet.get(fromPosition).get_object().get_position();
            int start = toPosition;
            int end = fromPosition;
            if (fromPosition < toPosition)
            {
                start = fromPosition;
                end = toPosition;
            }

            for (int i = 0; i < _dataSet.size(); i++)
                if (_dataSet.get(i).get_object().isParent())
                {
                    int removeCnt = Collapse(_dataSet.get(i).get_object().get_id());
                    if (_dataSet.get(i).get_object().get_position() < oldPosition)
                    {
                        start -= removeCnt;
                        end -= removeCnt;
                    }
                }

            if (fromPosition < toPosition)
                for (int i = start; i < end; i++)
                {
                    int pos = _dataSet.get(i).get_object().get_position();
                    _dataSet.get(i).get_object().set_position(_dataSet.get(i + 1).get_object().get_position());
                    _dataSet.get(i + 1).get_object().set_position(pos);
                    Collections.swap(_dataSet, i, i + 1);
                }
            else
                for (int i = end; i > start; i--)
                {
                    int pos = _dataSet.get(i).get_object().get_position();
                    _dataSet.get(i).get_object().set_position(_dataSet.get(i - 1).get_object().get_position());
                    _dataSet.get(i - 1).get_object().set_position(pos);
                    Collections.swap(_dataSet, i, i - 1);
                }

            try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
            {
                for (int i = start; i <= end; i++)
                    if (!dbHelper.UpdateMoneyCategoryPosition(_dataSet.get(i).get_object().get_id(),
                            _dataSet.get(i).get_object().get_position()))
                        Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyCategoryPosition),
                                Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyCategoryPosition) +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            notifyItemMoved(fromPosition, toPosition);
            for (int i = start; i <= end; i++)
                notifyItemChanged(i);
        }
    }

    public static class ItemViewHolder extends TreeViewAdapter.ItemViewHolder<TreeItem<MoneyCategory>>
    {
        private Hashtable<Long, Icon> _icons = new Hashtable<>();
        private final TextView _textViewName;
        private final TextView _textViewSum;
        private final ImageView imageViewIcon;
        public final ImageView imageViewTransferBudget;
        private GlobalClass gVar;

        public void setIcons(Hashtable<Long, Icon> icons) { _icons = icons; }

        public ItemViewHolder(@androidx.annotation.NonNull View itemView, Context context)
        {
            super(itemView, context);
            _textViewName = itemView.findViewById(R.id.name);
            _textViewSum = itemView.findViewById(R.id.sum);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            imageViewTransferBudget = itemView.findViewById(R.id.imageViewTransferBudget);
            gVar = (GlobalClass) context.getApplicationContext();
        }

        private void setName(boolean isParent, String name)
        {
            _textViewName.setText(name);
            _textViewName.setTypeface(null, isParent ? Typeface.BOLD : Typeface.NORMAL);
        }

        private void set_isArchive(boolean isArchive)
        {
            _textViewName.setBackgroundResource(isArchive ? R.color.lightLightGrey : 0);
            _textViewSum.setBackgroundResource(isArchive ? R.color.lightLightGrey : 0);
        }

        private void setSumLong(Long sum)
        {
            if (gVar.get_hasUnpaidSubscription())
            {
                _textViewSum.setText("");
                return;
            }

            _textViewSum.setText(sum == 0L ? "" : CustomStaticClass.getRubleSum(sum / 100d));

            if (sum > 0L)
                _textViewSum.setBackgroundResource(R.color.lightLightGreen);
            else if (sum < 0L)
                _textViewSum.setBackgroundResource(R.color.lightlightRed);
        }

        private void setType(EnumCategoryType type)
        {
            switch (type)
            {
                case Project:
                    imageViewIcon.setBackgroundResource(R.color.lightGreen);
                    break;
                case Advanced:
                case WalletAccumulation:
                    imageViewIcon.setBackgroundResource(R.color.lightOrange);
                    break;
                default:
                    imageViewIcon.setBackgroundResource(R.color.lightLightGrey);
            }
        }

        @Override
        public void setObject(TreeItem<MoneyCategory> object)
        {
            super.setObject(object);

            MoneyCategory moneyCategory = object.get_object();

            setName(moneyCategory.isParent(), moneyCategory.getSValue());
            set_isArchive(moneyCategory.get_archive());
            setSumLong(moneyCategory.get_sumLong());
            setType(moneyCategory.get_type());
            if (object.get_object().get_icon() != 0)
            {
                try
                {
                    imageViewIcon.setImageResource(_icons.get(object.get_object().get_icon()).get_icon_id());
                }
                catch (Exception e) { }
            }
            else
                imageViewIcon.setImageResource(0);

            if (gVar.get_hasUnpaidSubscription())
            {
                imageViewTransferBudget.setVisibility(View.INVISIBLE);
                imageViewTransferBudget.getLayoutParams().width = 0;
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _textViewSum.getLayoutParams();
                lp.setMarginEnd((int) _context.getResources().getDimension(R.dimen.BasePadding));
                _textViewSum.setLayoutParams(lp);
            }
            if (moneyCategory.getTypeCategory().equals(EnumTypeCategory.Cost))
            {
                if (moneyCategory.isChild())
                    imageViewTransferBudget.setVisibility(View.VISIBLE);
                else
                    imageViewTransferBudget.setVisibility(View.INVISIBLE);
            }
            else
            {
                imageViewTransferBudget.setVisibility(View.INVISIBLE);
                imageViewTransferBudget.getLayoutParams().width = 0;
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) _textViewSum.getLayoutParams();
                lp.setMarginEnd((int) _context.getResources().getDimension(R.dimen.BasePadding));
                _textViewSum.setLayoutParams(lp);
            }
        }
    }
}
