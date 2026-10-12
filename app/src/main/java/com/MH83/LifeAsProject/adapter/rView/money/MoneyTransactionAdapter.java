package com.MH83.LifeAsProject.adapter.rView.money;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.IconDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCreditDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumCreditStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumIndependent;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Custom.Tagged;
import com.MH83.LifeAsProject.model.Money.MoneyCredit;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyInc4CostIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyPNL;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSettings;
import com.MH83.LifeAsProject.model.Money.MoneyTransactionSum;
import com.MH83.LifeAsProject.model.Money.MoneyTransaction;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyCostPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyIncomingPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyMovingPopupNew;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Money.MoneyAccountingDataSource;
import com.MH83.LifeAsProject.data.Money.MoneyCategoryDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyCategory;
import com.MH83.LifeAsProject.view.Money.MoneyInc4Cost.FragmentMoneyInc4CostPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.material.card.MaterialCardView;

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

public class MoneyTransactionAdapter
        extends CustomMoneyAdapter<MoneyTransaction, MoneyTransactionAdapter.ItemViewHolder,
        CustomPopupBottomFragment>
{
    private final Calendar _dateStart;
    private final Calendar _dateEnd;
    private final MoneyAccountingSum _moneyAccountingSumCost;
    private final MoneyAccountingSum _moneyAccountingSumIncrease;
    private final Hashtable<Long, Icon> _iconsIncrease;
    private final Hashtable<Long, Icon> _iconsWallet;
    private final Hashtable<Long, Icon> _iconsCost;
    private final MoneyTransactionSettings _moneyTransactionSettings;
    boolean _showDate = true;
    public MoneyTransactionAdapter(Context context, ArrayList<MoneyTransaction> dataSet,
                                   IFragmentViewCustom fragmentViewCustom,
                                   Calendar dateStart, Calendar dateEnd,
                                   MoneyAccountingSum moneyAccountingSumCost,
                                   MoneyAccountingSum moneyAccountingSumIncrease,
                                   MoneyTransactionSettings moneyTransactionSettings)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_money_transaction);
        _dateStart = dateStart;
        _dateEnd = dateEnd;
        _moneyAccountingSumCost = moneyAccountingSumCost;
        _moneyAccountingSumIncrease = moneyAccountingSumIncrease;
        _moneyTransactionSettings = moneyTransactionSettings;

        GlobalClass gVar = (GlobalClass) context.getApplicationContext();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
            gVar.get_typeDevice() == EnumTypeDevice.Phone)
            _showDate = false;

        MoneyTransaction moneyPNLPrev = null;
        MoneyTransactionSum moneyPNLSum = null;
        if (dataSet != null)
            for (MoneyTransaction moneyTransaction: dataSet)
            {
                if (moneyPNLPrev == null || !moneyTransaction.CompareDate(moneyPNLPrev))
                    moneyPNLSum = new MoneyTransactionSum(null, moneyAccountingSumIncrease);

                if (!moneyTransaction.get_isPlan())
                    moneyPNLSum.change_sum(moneyTransaction, moneyAccountingSumIncrease,
                            EnumTypeCategory.Increase);
                moneyTransaction.set_moneyTransactionSum(moneyPNLSum);

                moneyPNLPrev = moneyTransaction;
            }

        _iconsIncrease = IconDataSource.GetHashtable(EnumTypeCategory.Increase);
        _iconsWallet = IconDataSource.GetHashtable(EnumTypeCategory.Wallet);
        _iconsCost = IconDataSource.GetHashtable(EnumTypeCategory.Cost);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(_layoutId, parent, false), _context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        holder.ShowDate = _showDate;

        int pos = position;
        if (_dataSet.get(position) instanceof MoneyInc4CostIndependent)
            holder.setIcons(_iconsCost);
        else
            switch (_dataSet.get(position).get_typeCategory())
            {
                case Increase:
                    holder.setIcons(_iconsIncrease);
                    break;
                case Cost:
                    holder.setIcons(_iconsCost);
                    break;
                case Wallet:
                    holder.setIcons(_iconsWallet);
                    break;
                default:
            }

        super.onBindViewHolder(holder, pos);
        MoneyTransaction moneyTransaction = _dataSet.get(pos);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = moneyTransaction;
            _fragmentPopupNew = null;

            if (_currentItem instanceof MoneyPNLIndependent)
            {
                MoneyAccountingSum moneyAccountingSumCost =
                        MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                                moneyTransaction.getCostCategoryId());
                MoneyAccountingSum moneyAccountingSumIncrease =
                        MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                                moneyTransaction.getIncreaseCategoryId());

                MoneyCredit moneyCredit = null;
                if (((MoneyPNLIndependent) moneyTransaction).get_creditId() != 0)
                    moneyCredit = MoneyCreditDataSource.GetOneMoneyCredit(_context,
                            ((MoneyPNLIndependent) moneyTransaction).get_creditId());

                if (_currentItem.get_typeCategory() == EnumTypeCategory.Increase)
                {
                    _fragmentPopupNew = new FragmentMoneyIncomingPopupNew(
                            (AppCompatActivity) _context, (MoneyPNLIndependent) moneyTransaction, EnumTypeAction.Update,
                            _fragmentViewCustom, moneyAccountingSumCost, moneyAccountingSumIncrease,
                            null, null,
                            ((MoneyPNLIndependent) moneyTransaction).get_creditId() == 0,
                            ((MoneyPNLIndependent) moneyTransaction).get_creditId() == 0);
                }
                else if (_currentItem.get_typeCategory() == EnumTypeCategory.Cost)
                {
                    _fragmentPopupNew = new FragmentMoneyCostPopupNew(
                            (AppCompatActivity) _context, (MoneyPNLIndependent) moneyTransaction, EnumTypeAction.Update,
                            _fragmentViewCustom, moneyAccountingSumCost, moneyAccountingSumIncrease,
                            null, null,
                            ((MoneyPNLIndependent) moneyTransaction).get_creditId() == 0,
                            ((MoneyPNLIndependent) moneyTransaction).get_creditId() == 0,
                            ((MoneyPNLIndependent) moneyTransaction).get_creditId() == 0 ||
                                    (((MoneyPNLIndependent) moneyTransaction).get_creditId() != 0 &&
                                            moneyCredit != null && moneyCredit.get_status().equals(EnumCreditStatus.Active)));
                }
                else
                {
                    _fragmentPopupNew = new FragmentMoneyMovingPopupNew(
                            (AppCompatActivity) _context, (MoneyPNLIndependent) moneyTransaction, EnumTypeAction.Update,
                            _fragmentViewCustom, moneyAccountingSumCost, moneyAccountingSumIncrease,
                            null, null,
                            ((MoneyPNLIndependent) moneyTransaction).get_accumulationId() == 0,
                            ((MoneyPNLIndependent) moneyTransaction).get_accumulationId() == 0);
                }
            }
            else if (_currentItem instanceof MoneyInc4CostIndependent)
            {
                MoneyAccountingSum moneyAccountingSumCost =
                        MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                                _currentItem.getCostCategoryId());
                MoneyAccountingSum moneyAccountingSumIncrease =
                        MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                                _currentItem.getIncreaseCategoryId());

                final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
                globalVariable.set_MAccTypeCategory(_currentItem.get_typeCategory());

                _fragmentPopupNew = new FragmentMoneyInc4CostPopupNew(
                        (AppCompatActivity) _context,
                        (MoneyInc4Cost) moneyTransaction,
                        null,
                        EnumTypeAction.Update,
                        null,
                        _fragmentViewCustom,
                        moneyAccountingSumCost,
                        moneyAccountingSumIncrease,
                        _dateStart,
                        _dateEnd,
                        EnumIndependent.Independent);
            }

            if (_fragmentPopupNew != null)
                _fragmentPopupNew.PopupShow();
        });
    }

    @Override
    public boolean canActionLeftImage()
    {
        if (_currentItem instanceof MoneyInc4Cost)
            return _currentItem.get_typeCategory() == EnumTypeCategory.Wallet;
        else if (_currentItem instanceof MoneyPNLIndependent)
            return ((MoneyPNLIndependent) _currentItem).get_creditId() == 0;
        else
            return false;
    }

    @Override
    public boolean canActionRightImage()
    {
        if (_currentItem == null)
            return false;

        return _currentItem.canDelete(_context);
    }

    public boolean LeftButtonClick()
    {
        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        if (_currentItem instanceof MoneyPNLIndependent)
        {
//            if (!MoneyPNLIndependent.canAdd(_context, globalVariable.get_hasSubscription()))
//            {
//                SubscriptionClick(globalVariable);
//                return false;
//            }
        }
        else if (_currentItem instanceof MoneyInc4CostIndependent)
        {
            if (!MoneyInc4CostIndependent.canAdd(_context, globalVariable))
            {
                SubscriptionClick();
                return false;
            }
        }

        if (_currentItem == null)
            return false;

        notifyItemChanged(_dataSet.indexOf(_currentItem));
        MoneyTransaction moneyTransaction = _currentItem.Clone();
        _fragmentPopupNew = null;

        if (_currentItem instanceof MoneyPNLIndependent)
        {
            MoneyCategory costCategory = MoneyCategoryDataSource.LoadOne(_context,
                    moneyTransaction.getCostCategoryId());
            MoneyCategory incrementCategory = MoneyCategoryDataSource.LoadOne(_context,
                    moneyTransaction.getIncreaseCategoryId());

            if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Increase)
                //создается только для некредитных операций
                _fragmentPopupNew = new FragmentMoneyIncomingPopupNew(
                        (AppCompatActivity) _context, (MoneyPNLIndependent) moneyTransaction, EnumTypeAction.Insert,
                        _fragmentViewCustom, costCategory, incrementCategory,
                        _dateStart, _dateEnd,
                        true, true);
            else if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Cost)
                _fragmentPopupNew = new FragmentMoneyCostPopupNew(
                        (AppCompatActivity) _context, (MoneyPNLIndependent) moneyTransaction, EnumTypeAction.Insert,
                        _fragmentViewCustom, costCategory, incrementCategory,
                        _dateStart, _dateEnd,
                        true, true, true);
            else if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Wallet)
                _fragmentPopupNew = new FragmentMoneyMovingPopupNew(
                        (AppCompatActivity) _context, (MoneyPNLIndependent) moneyTransaction, EnumTypeAction.Insert,
                        _fragmentViewCustom, costCategory, incrementCategory,
                        _dateStart, _dateEnd,
                        ((MoneyPNLIndependent) moneyTransaction).get_accumulationId() == 0,
                        true);
        }
        else if (_currentItem instanceof MoneyInc4CostIndependent)
        {
            MoneyAccountingSum moneyAccountingSumCost =
                    MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                            _currentItem.getCostCategoryId());
            MoneyAccountingSum moneyAccountingSumIncrease =
                    MoneyAccountingDataSource.LoadDataWithoutPNL(_context,
                            _currentItem.getIncreaseCategoryId());

            globalVariable.set_MAccTypeCategory(_currentItem.get_typeCategory());
            _fragmentPopupNew = new FragmentMoneyInc4CostPopupNew(
                    (AppCompatActivity) _context,
                    (MoneyInc4Cost) moneyTransaction,
                    null,
                    EnumTypeAction.Insert,
                    null,
                    _fragmentViewCustom,
                    moneyAccountingSumCost,
                    moneyAccountingSumIncrease,
                    _dateStart,
                    _dateEnd,
                    EnumIndependent.Independent);
        }

        if (_fragmentPopupNew != null)
            _fragmentPopupNew.PopupShow();

        _currentItem = null;
        return true;
    }

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

    private void SubscriptGoogle()
    {
        BillingClient.Builder builder = BillingClient.newBuilder(_context);
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

    public boolean RightButtonClick()
    {
        if (_currentItem == null)
            return false;

        boolean b = DeleteCurrent();
//        ((FragmentMoneyTransaction) _fragmentViewCustom).FillTextViewSum();
        return b;
    }

    public Long AddItem(MoneyTransaction moneyTransaction)
    {
        if (_dateStart.getTime().getTime() > moneyTransaction.getDate().getTime())
            return -1L;

        if (_dateEnd.getTime().getTime() < moneyTransaction.getDate().getTime())
            return -1L;

        if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Cost &&
                _moneyAccountingSumCost != null &&
                !Objects.equals(moneyTransaction.getCostCategoryId(), _moneyAccountingSumCost.getCategoryId()))
            return -1L;

        if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Increase &&
                _moneyAccountingSumIncrease != null &&
                !Objects.equals(moneyTransaction.getIncreaseCategoryId(), _moneyAccountingSumIncrease.getCategoryId()))
            return -1L;

        int insertPosition = 0;

        for (int i = _dataSet.size() - 1; i >= 0; i--)
            if (moneyTransaction.getDate().getTime() < _dataSet.get(i).getDate().getTime())
            {
                insertPosition = i + 1;
                break;
            }

        for (MoneyTransaction moneyTransactionItem: _dataSet)
            if (moneyTransactionItem.CompareDate(moneyTransaction))
            {
                MoneyTransactionSum moneyTransactionSum = moneyTransactionItem.get_moneyTransactionSum();
                moneyTransactionSum.change_sum(moneyTransaction, _moneyAccountingSumIncrease,
                        EnumTypeCategory.Increase);
                moneyTransaction.set_moneyTransactionSum(moneyTransactionSum);

                break;
            }
        if (moneyTransaction.get_moneyTransactionSum() == null)
            moneyTransaction.set_moneyTransactionSum(new MoneyTransactionSum(moneyTransaction, _moneyAccountingSumIncrease));

        _dataSet.add(insertPosition, moneyTransaction);
        notifyItemInserted(insertPosition);

        return (long) insertPosition;
    }

    public Long UpdateItem(MoneyTransaction moneyTransaction)
    {
        Long id = moneyTransaction.get_id();

        if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Cost &&
                _moneyAccountingSumCost != null &&
                !Objects.equals(moneyTransaction.getCostCategoryId(), _moneyAccountingSumCost.getCategoryId()))
        {
            DeleteFromDataset(moneyTransaction);
            return -1L;
        }

        if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Increase &&
                _moneyAccountingSumIncrease != null &&
                !Objects.equals(moneyTransaction.getIncreaseCategoryId(), _moneyAccountingSumIncrease.getCategoryId()))
        {
            DeleteFromDataset(moneyTransaction);
            return -1L;
        }

        if (_moneyTransactionSettings.get_isPlan() != null &&
            _moneyTransactionSettings.get_isPlan() != moneyTransaction.get_isPlan())
        {
            DeleteFromDataset(moneyTransaction);
            return -1L;
        }

        if (moneyTransaction.get_typeCategory().equals(EnumTypeCategory.Increase))
        {
            if (_moneyTransactionSettings.get_categoryId() != null &&
                    _moneyTransactionSettings.get_categoryId() != 0L &&
                    !Objects.equals(_moneyTransactionSettings.get_categoryId(), moneyTransaction.getCostCategoryId()) &&
                    !Objects.equals(_moneyTransactionSettings.get_categoryId(), moneyTransaction.getIncreaseCategoryId()))
            {
                DeleteFromDataset(moneyTransaction);
                return -1L;
            }

            if (moneyTransaction instanceof MoneyPNL && _moneyTransactionSettings.get_subCategoryId() != null &&
                    _moneyTransactionSettings.get_subCategoryId() != 0L &&
                    !Objects.equals(_moneyTransactionSettings.get_subCategoryId(),
                            ((MoneyPNL) moneyTransaction).getSubCategoryId()))
            {
                DeleteFromDataset(moneyTransaction);
                return -1L;
            }
        }
        else if (moneyTransaction.get_typeCategory().equals(EnumTypeCategory.Cost))
        {
            if (_moneyTransactionSettings.get_categoryId() != null &&
                    _moneyTransactionSettings.get_categoryId() != 0L &&
                    !Objects.equals(_moneyTransactionSettings.get_categoryId(), moneyTransaction.getIncreaseCategoryId()) &&
                    !Objects.equals(_moneyTransactionSettings.get_categoryId(), moneyTransaction.getCostCategoryId()))
            {
                DeleteFromDataset(moneyTransaction);
                return -1L;
            }

            if (moneyTransaction instanceof MoneyPNL && _moneyTransactionSettings.get_subCategoryId() != null &&
                    _moneyTransactionSettings.get_subCategoryId() != 0L &&
                    !Objects.equals(_moneyTransactionSettings.get_subCategoryId(),
                            ((MoneyPNL) moneyTransaction).getSubCategoryId()))
            {
                DeleteFromDataset(moneyTransaction);
                return -1L;
            }
        }
        else if (moneyTransaction.get_typeCategory().equals(EnumTypeCategory.Wallet))
        {
            if (_moneyTransactionSettings.get_categoryId() != null &&
                    _moneyTransactionSettings.get_categoryId() != 0L &&
                    !Objects.equals(_moneyTransactionSettings.get_categoryId(), moneyTransaction.getIncreaseCategoryId()) &&
                    !Objects.equals(_moneyTransactionSettings.get_categoryId(), moneyTransaction.getCostCategoryId()))
            {
                DeleteFromDataset(moneyTransaction);
                return -1L;
            }
        }

        if (moneyTransaction instanceof MoneyPNLIndependent &&
                _moneyTransactionSettings.get_tagId() != null &&
                _moneyTransactionSettings.get_tagId() != 0L)
        {
            boolean contains = false;
            for (Tagged item : ((MoneyPNLIndependent) moneyTransaction).get_Tagged())
                if (Objects.equals(item.get_id(), _moneyTransactionSettings.get_tagId()))
                {
                    contains = true;
                    break;
                }
            if (!contains)
            {
                DeleteFromDataset(moneyTransaction);
                return -1L;
            }
        }

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()) &&
                    ((moneyTransaction instanceof MoneyPNLIndependent && _dataSet.get(i) instanceof MoneyPNLIndependent) ||
                     (moneyTransaction instanceof MoneyInc4Cost && _dataSet.get(i) instanceof MoneyInc4Cost)))
            {
                if (moneyTransaction.getDate().getTime() == _dataSet.get(i).getDate().getTime())
                {
                    _dataSet.get(i).get_moneyTransactionSum().change_sum(
                            _dataSet.get(i), _moneyAccountingSumIncrease,
                            EnumTypeCategory.Cost);

                    moneyTransaction.set_moneyTransactionSum(_dataSet.get(i).get_moneyTransactionSum());
                    _dataSet.set(i, moneyTransaction);
                    _dataSet.get(i).get_moneyTransactionSum().change_sum(
                            _dataSet.get(i), _moneyAccountingSumIncrease,
                            EnumTypeCategory.Increase);

                    notifyItemChanged(i);
                    return (long) i;
                }
                else
                {
                    _dataSet.get(i).get_moneyTransactionSum().change_sum(
                            _dataSet.get(i), _moneyAccountingSumIncrease,
                            EnumTypeCategory.Cost);
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyTransaction);
                }
            }

        _fragmentPopupNew = null;
        return 0L;
    }

    private boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (_currentItem.DeleteFromDatabase(dbHelper, _context))
            {
                _fragmentViewCustom.refreshViewOnDelete(_currentItem);
                return true;
            }
//                return DeleteFromDataset(_currentItem);
            else
                Toast.makeText(_context,
                    _context.getString(R.string.errorDeletedMoneyPNLNotFound),
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context,
                    _context.getString(R.string.errorDeleteMoneyPNL) + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyTransaction moneyTransaction)
    {
        int index = -1;
        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).get_id(),moneyTransaction.get_id()) &&
                    ((moneyTransaction instanceof MoneyPNLIndependent && _dataSet.get(i) instanceof MoneyPNLIndependent) ||
                            (moneyTransaction instanceof MoneyInc4Cost && _dataSet.get(i) instanceof MoneyInc4Cost)))
            {
                index = i;
                break;
            }

        if (index == -1)
            return false;

        _dataSet.get(index).get_moneyTransactionSum().change_sum(
                _dataSet.get(index), _moneyAccountingSumIncrease,
                EnumTypeCategory.Cost);
        _dataSet.remove(index);
        notifyItemRemoved(index);

        return true;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    public Long getSumLong(Long categoryId, MoneyAccountingSum moneyAccountingSumCost,
                         MoneyAccountingSum moneyAccountingSumIncrease)
    {
        Long sumLong = 0L;

        if (moneyAccountingSumIncrease != null &&
                moneyAccountingSumIncrease.getTypeCategory() == EnumTypeCategory.Cost)
            for (MoneyTransaction moneyTransaction: _dataSet)
                if (!moneyTransaction.get_isPlan())
                    sumLong += moneyTransaction.get_totalSumLong();

        if (moneyAccountingSumCost != null &&
                moneyAccountingSumCost.getTypeCategory() == EnumTypeCategory.Increase)
            for (MoneyTransaction moneyTransaction: _dataSet)
                if (!moneyTransaction.get_isPlan())
                    sumLong += moneyTransaction.get_totalSumLong();

        if (moneyAccountingSumCost != null &&
                moneyAccountingSumCost.getTypeCategory() == EnumTypeCategory.Wallet)
            for (MoneyTransaction moneyTransaction: _dataSet)
                if (Objects.equals(moneyTransaction.getCostCategoryId(), categoryId) && !moneyTransaction.get_isPlan())
                    sumLong -= moneyTransaction.get_totalSumLong();
                else if (!moneyTransaction.get_isPlan())
                    sumLong += moneyTransaction.get_totalSumLong();

        if (moneyAccountingSumCost == null && moneyAccountingSumIncrease == null)
            for (MoneyTransaction moneyTransaction: _dataSet)
                if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Increase &&
                        !moneyTransaction.get_isPlan())
                    sumLong += moneyTransaction.get_totalSumLong();
                else if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Cost &&
                        !moneyTransaction.get_isPlan())
                    sumLong -= moneyTransaction.get_totalSumLong();

        return sumLong;
    }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyTransaction>
    {
        private final TextView textViewDate;
        private final TextView textViewCostName;
        private final TextView textViewIncreaseName;
        private final TextView textViewSum;
        private final ImageView imageViewIcon;
        private final MaterialCardView materialCardViewIcon;
        public boolean ShowDate = true;

        private final ConstraintLayout constraintLayoutItem;
        private Hashtable<Long, Icon> _icons;
        public void setIcons(Hashtable<Long, Icon> icons) { _icons = icons; }
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewCostName = itemView.findViewById(R.id.textViewCostName);
            textViewIncreaseName = itemView.findViewById(R.id.textViewIncreaseName);
            textViewSum = itemView.findViewById(R.id.textViewSum);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            constraintLayoutItem = itemView.findViewById(R.id.constraintLayoutItem);
            materialCardViewIcon = itemView.findViewById(R.id.materialCardViewIcon);
        }

        public void setDate(Integer day, Integer month, Integer year)
        {
            if (textViewDate != null)
            {
                if (ShowDate)
                    CustomStaticClass.setDateView(_context, day, month, year, textViewDate);
                else
                {
                    textViewDate.setText("");
                    ViewGroup.LayoutParams lp = textViewDate.getLayoutParams();
                    lp.width = 0;
                    textViewDate.setLayoutParams(lp);
                }
            }
        }
        public void setCostName(String name) { textViewCostName.setText(name); }
        public void setIncreaseName(String name) { textViewIncreaseName.setText(name); }
        @SuppressLint("ResourceType")
        public void setTotalSumLong(EnumTypeCategory typeCategory, Long sumLong)
        {
            if (typeCategory.equals(EnumTypeCategory.Increase))
            {
                textViewSum.setTextColor(_context.getColor(R.color.green));
                textViewSum.invalidate();
            }
            else
            {
                textViewSum.setTextColor(_context.getColor(R.color.black));
                textViewSum.invalidate();
            }

            textViewSum.setText(CustomStaticClass.getRubleSum(sumLong / 100d));
        }
        @SuppressLint("ResourceAsColor")
        public void setImage(EnumTypeCategory typeCategory, Long icon)
        {
            switch (typeCategory)
            {
                case Increase:
                    materialCardViewIcon.setStrokeColor(ContextCompat.getColor(_context, R.color.green));
                    materialCardViewIcon.invalidate();
                    if (icon != 0)
                        try
                        {
                            imageViewIcon.setImageResource(_icons.get(icon).get_icon_id());
                        }
                        catch (Exception e) {}
                    else
                        imageViewIcon.setImageResource(R.drawable.icons_circle_green_50);
                    break;
                case Cost:
                    materialCardViewIcon.setStrokeColor(ContextCompat.getColor(_context, R.color.orange));
                    materialCardViewIcon.invalidate();
                    if (icon != 0)
                        try
                        {
                            imageViewIcon.setImageResource(_icons.get(icon).get_icon_id());
                        }
                        catch (Exception e) {}
                    else
                        imageViewIcon.setImageResource(R.drawable.icons_circle_yellow_50);
                    break;
                case Wallet:
                    materialCardViewIcon.setStrokeColor(ContextCompat.getColor(_context, R.color.grey));
                    materialCardViewIcon.invalidate();
                    imageViewIcon.setImageResource(R.drawable.icons_icon_card_exchange_100);
                    break;
                default:
            }
        }
        private void set_isPlan(boolean isPlan)
        {
            constraintLayoutItem.setBackgroundResource(isPlan ? R.color.lightGrey : R.color.white);
        }


        @Override
        public void setObject(MoneyTransaction moneyTransaction)
        {
            super.setObject(moneyTransaction);
            setDate(moneyTransaction.getDay(), moneyTransaction.getMonth(), moneyTransaction.getYear());
            if (moneyTransaction.get_typeCategory() == EnumTypeCategory.Cost)
            {
                setCostName(moneyTransaction.getIncreaseCategoryName());
                setIncreaseName(moneyTransaction.getCostCategoryName());
            }
            else
            {
                setCostName(moneyTransaction.getCostCategoryName());
                setIncreaseName(moneyTransaction.getIncreaseCategoryName());
            }
            setTotalSumLong(moneyTransaction.get_typeCategory(), moneyTransaction.get_totalSumLong());
            setImage(moneyTransaction.get_typeCategory(), moneyTransaction.get_subCategoryIcon());
            set_isPlan(moneyTransaction.get_isPlan());
        }
    }
}
