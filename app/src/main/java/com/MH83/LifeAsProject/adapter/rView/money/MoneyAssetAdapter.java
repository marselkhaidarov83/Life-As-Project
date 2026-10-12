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
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyAssetDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyTypeAssetDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Money.MoneyAsset;
import com.MH83.LifeAsProject.model.Money.MoneyAssetSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAsset;
import com.MH83.LifeAsProject.model.Money.MoneyTypeAssetSettings;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAsset.FragmentMoneyAssetPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class MoneyAssetAdapter
        extends TreeViewAdapter<MoneyAssetAdapter.ItemViewHolder, MoneyAsset,
        FragmentMoneyAssetPopupNew>
        implements IAUDAdapter<MoneyAsset>
{
    public MoneyAssetAdapter(Context context, IFragmentViewCustom<MoneyAsset> fragmentViewCustom)
    {
        super(context, null, fragmentViewCustom, R.layout.item_tree_money_asset);

        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        MoneyAssetSettings moneyAssetSettings = globalVariable.get_moneyAssetSettings();

        List<MoneyTypeAsset> dataSetParentTA = MoneyTypeAssetDataSource.LoadDataParent(context,
                false, new MoneyTypeAssetSettings(null));

        List<MoneyAsset> dataSetParent = new ArrayList<>();
        if (dataSetParentTA != null)
            for (MoneyTypeAsset moneyTypeAsset: dataSetParentTA)
                dataSetParent.add(moneyTypeAsset.GetMoneyAsset());

        ArrayList<Long> expandedId = null;
        if (moneyAssetSettings != null)
            expandedId = moneyAssetSettings.get_expandedList();

        TreeItem<MoneyAsset> root = new TreeItem<>(null, true);
            for (int i = 0; i < dataSetParent.size(); i++)
            {
                TreeItem<MoneyAsset> folderCategory = new TreeItem<>(dataSetParent.get(i), true);
                if (expandedId != null && expandedId.size() > 0)
                    folderCategory.setExpanded(expandedId.contains(dataSetParent.get(i).get_id()));
                root.getChildren().add(folderCategory);

                MoneyAssetSettings moneyAssetSettings1 = moneyAssetSettings.Clone();
                moneyAssetSettings1.set_moneyTypeAssetId(dataSetParent.get(i).get_id());
                List<MoneyAsset> dataSet2 = MoneyAssetDataSource.LoadDataChildren(context, moneyAssetSettings1);

                if (dataSet2 != null)
                    for (int k = 0; k < dataSet2.size(); k++)
                    {
                        TreeItem<MoneyAsset> singleCategory2 = new TreeItem<>(
                                dataSet2.get(k), false);
                        folderCategory.getChildren().add(singleCategory2);
                        (folderCategory.get_object()).set_hasChild(true);

//                        List<MoneyAsset> dataSet3 = MoneyAssetDataSource.LoadDataChildren(context,
//                                dataSet2.get(k).get_id(), moneyAssetSettings);
//                        for (int j = 0; j < dataSet3.size(); j++)
//                        {
//                            singleCategory2.setExpandable(true);
//
//                            TreeItem<MoneyAsset> singleCategory3 = new TreeItem<>(
//                                    dataSet3.get(j), false);
//                            singleCategory2.getChildren().add(singleCategory3);
//                            (singleCategory2.get_object()).set_hasChild(true);
//                        }
                    }
            }

        _setPadding = true;
        setRoot(root);
    }

    public void SaveExpandedList()
    {
        ArrayList<Long> list = new ArrayList<>();

        for (TreeItem<MoneyAsset> moneyAssetTreeItem : _dataSet)
            if (moneyAssetTreeItem.get_object().get_moneyTypeAssetId() == 0L
                    && moneyAssetTreeItem.isExpanded())
                list.add(moneyAssetTreeItem.get_object().get_id());

        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        globalVariable.get_moneyAssetSettings().set_expandedList(list);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(View view)
    {
        return new ItemViewHolder(view, _context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        super.onBindViewHolder(holder, position);
        MoneyAsset moneyAsset = _dataSet.get(position).get_object();

        holder.itemView.setOnClickListener(view ->
        {
            if (moneyAsset.get_moneyTypeAssetId() == 0)
                return;

            moneyAsset.set_hasChild(_dataSet.get(position).getChildren().size() > 0);
            _currentItem = _dataSet.get(position);

            new FragmentMoneyAssetPopupNew((AppCompatActivity) _context,
                    moneyAsset, EnumTypeAction.Update, _fragmentViewCustom).PopupShow();
        });
    }

    @Override
    protected void ClickToLeadingIcon(TreeItem<MoneyAsset> item,
                                      @androidx.annotation.NonNull MoneyAssetAdapter.ItemViewHolder holder)
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
        super.CollapseAll(showExpandedButton);

        SaveExpandedList();
    }

    @Override
    public boolean canActionRightImage()
    {
        return _currentItem.get_object().canDelete(_context);
    }

    @Override
    public boolean canActionLeftImage() { return true; }

    @Override
    public boolean LeftButtonClick() { return false; }

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

    @Override
    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }

    public Long AddItem(MoneyAsset moneyAsset)
    {
        int insertPosition = 0;

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals((_dataSet.get(i).get_object()).get_id(), moneyAsset.get_moneyTypeAssetId()) &&
                    _dataSet.get(i).get_object().get_moneyTypeAssetId() == 0L)
            {
                _dataSet.get(i).get_object().set_hasChild(true);

                if (!_dataSet.get(i).isExpanded())
                {
                    _dataSet.get(i).setExpanded(true);
                    notifyItemChanged(i);
                    insertAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                    notifyItemRangeInserted(i + 1, _dataSet.get(i).getChildren().size());
                }

                TreeItem<MoneyAsset> singleCategory = new TreeItem<>(moneyAsset, false);
                insertPosition = addLevel3ToParent(singleCategory, moneyAsset.get_moneyTypeAssetId());
                break;
            }

        return (long) insertPosition;
    }

    public Long UpdateItem(MoneyAsset moneyAssetNew)
    {
        for (int i = 0; i < _dataSet.size(); i++)
        {
            MoneyAsset moneyAssetOld = _dataSet.get(i).get_object();
            if (Objects.equals(moneyAssetNew.get_id(), moneyAssetOld.get_id()) &&
                    moneyAssetOld.get_moneyTypeAssetId() != 0L)
            {
                if (Objects.equals(moneyAssetOld.get_moneyTypeAssetId(), moneyAssetNew.get_moneyTypeAssetId()))
                {
                    _dataSet.get(i).set_object(moneyAssetNew);
                    notifyItemChanged(i);
                }
                else
                {
                    try { DeleteFromDataset(moneyAssetOld); }
                    catch (Exception e) {}
                    AddItem(moneyAssetNew);
                }
                break;
            }
        }

        return moneyAssetNew.get_id();
    }

    public boolean DeleteFromDataset(TreeItem<MoneyAsset> moneyAssetTreeItem) throws Exception
    {
        return DeleteFromDataset(moneyAssetTreeItem.get_object());
    }

    public boolean DeleteFromDataset(MoneyAsset moneyAssetDeleted)
    {
        for (int i = 0; i < _dataSet.size(); i++)
        {
            MoneyAsset moneyAsset = _dataSet.get(i).get_object();
            if (Objects.equals(moneyAsset.get_id(), moneyAssetDeleted.get_id()) &&
                _dataSet.get(i).get_object().get_moneyTypeAssetId() != 0L)
            {
                _dataSet.remove(i);
                for (int k = i - 1; k >= 0; k--)
                {
                    MoneyAsset tp2 = _dataSet.get(k).get_object();
                    if (Objects.equals(tp2.get_id(), moneyAssetDeleted.get_moneyTypeAssetId())
                            && tp2.get_moneyTypeAssetId() == 0)
                    {
                        _dataSet.get(k).getChildren().remove(i - k - 1);
                        tp2.set_hasChild(_dataSet.get(k).getChildren().size() > 0);
                        break;
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

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        if (_dataSet.get(fromPosition).get_object().get_moneyTypeAssetId() == 0)
            return;

        Long updateId = _dataSet.get(fromPosition).get_object().get_id();

        //Изменения для 3-его уровня
        {
            int oldParentIndex = 0;
            TreeItem<MoneyAsset> oldParent = null;
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
                if (_dataSet.get(i).get_object().get_moneyTypeAssetId() == 0L)
                {
                    oldParent = _dataSet.get(i);
                    oldParentIndex = i;
                    break;
                }

            if (fromPosition < toPosition)
                for (int i = start; i < end; i++)
                {
                    if (_dataSet.get(i + 1).get_object().get_moneyTypeAssetId() != 0L)
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
                        _dataSet.get(i).get_object().set_moneyTypeAssetId(parentId);

                        if (!_dataSet.get(i + 1).isExpanded())
                            Expand(_dataSet.get(i + 1).get_object().get_id());

                        for (int k = i + 2; k < _dataSet.size(); k++)
                            if (Objects.equals(_dataSet.get(k).get_object().get_moneyTypeAssetId(), parentId))
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
                    if (_dataSet.get(i - 1).get_object().get_moneyTypeAssetId() != 0L)
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
                            _dataSet.get(i).get_object().set_moneyTypeAssetId(0L);
                            int parentId = 0;
                            for (int k = 0; k < _dataSet.size(); k++)
                                if (_dataSet.get(k).get_object().get_moneyTypeAssetId() == parentId &&
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
                            TreeItem<MoneyAsset> newParent = null;
                            for (int k = i - 2; k >= 0; k--)
                                if (_dataSet.get(k).get_object().get_moneyTypeAssetId() == 0L)
                                {
                                    newParent = _dataSet.get(k);
                                    break;
                                }

                            _dataSet.get(i).get_object().set_moneyTypeAssetId(newParent.get_object().get_id());
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
                    if (_dataSet.get(i).get_object().get_moneyTypeAssetId() != 0L ||
                            (_dataSet.get(i).get_object().get_moneyTypeAssetId() == 0L && (i == 0)))
                    {
                        if (Objects.equals(_dataSet.get(i).get_object().get_id(), updateId))
                        {
                            if (!dbHelper.UpdateMoneyAsset(_dataSet.get(i).get_object()))
                                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyAsset),
                                        Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (!dbHelper.UpdateMoneyAssetPosition(_dataSet.get(i).get_object().get_id(),
                                    _dataSet.get(i).get_object().get_position()))
                                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyAssetPosition),
                                        Toast.LENGTH_SHORT).show();
                        }
                    }
            }
            catch (Exception e)
            {
                Toast.makeText(_context, _context.getString(R.string.errorUpdateMoneyAssetPosition) +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            notifyItemMoved(fromPosition, toPosition);
            for (int i = start; i <= endForUpdate; i++)
                notifyItemChanged(i);
        }
    }

    public static class ItemViewHolder extends TreeViewAdapter.ItemViewHolder<TreeItem<MoneyAsset>>
    {
        private final TextView _textViewName;

        private void setName(String name, boolean isParent)
        {
            _textViewName.setText(name);
            if (isParent)
                _textViewName.setTypeface(null, Typeface.BOLD);
            else
                _textViewName.setTypeface(null, Typeface.NORMAL);
        }

        public ItemViewHolder(@androidx.annotation.NonNull View itemView, Context context)
        {
            super(itemView, context);
            _textViewName = itemView.findViewById(R.id.textViewName);
        }

        @Override
        public void setObject(TreeItem<MoneyAsset> moneyAssetTreeItem)
        {
            super.setObject(moneyAssetTreeItem);

            MoneyAsset moneyAsset = moneyAssetTreeItem.get_object();
            setName(moneyAsset.getSValue(), moneyAsset.get_moneyTypeAssetId() == 0L);
        }
    }
}
