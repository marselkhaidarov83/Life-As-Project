package com.MH83.LifeAsProject.adapter.rView.money;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Custom.IconDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumDataOrSetting;
import com.MH83.LifeAsProject.interfac.enums.EnumTransactionType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Custom.Icon;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSettings;
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyInc4Cost;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccounting;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccountingPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyCostPopupNew;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.MoneyIncomingSumTrio;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeCategory;
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

public class MoneyCostSumAdapter
        extends CustomMoneyAdapter<MoneyAccountingSum, MoneyCostSumAdapter.ItemViewHolder,
        FragmentMoneyAccountingPopupNew>
{
    private final MoneyIncomingSumTrio _moneyIncomingSumTrio;
    private final Calendar _dateStart;
    private final Calendar _dateEnd;
    MoneyAccountingSettings _moneyAccountingSettings;
    private final Hashtable<Long, Icon> _icons;
    public MoneyCostSumAdapter(Context context, ArrayList<MoneyAccountingSum> dataSet,
                               IFragmentViewCustom fragmentMoneyIncreaseCustom,
                               MoneyIncomingSumTrio moneyIncomingSumTrio,
                               Calendar dateStart, Calendar dateEnd,
                               MoneyAccountingSettings moneyAccountingSettings)
    {
        super(context, dataSet, fragmentMoneyIncreaseCustom, R.layout.item_money_accounting);
        _moneyIncomingSumTrio = moneyIncomingSumTrio;
        _dateStart = dateStart;
        _dateEnd = dateEnd;
        _moneyAccountingSettings = moneyAccountingSettings;
        _icons = IconDataSource.GetHashtable(EnumTypeCategory.Cost);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(_layoutId, parent, false), _context);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void RefreshData(MoneyAccountingSettings moneyAccountingSettings)
    {
        _moneyAccountingSettings = moneyAccountingSettings;
        notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        holder.setIcons(_icons);
        int pos = position;
        super.onBindViewHolder(holder, position);

        holder.itemView.setOnClickListener(view ->
        {
            GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();
            if (_dataSet.get(pos).get_dataOrSetting().equals(EnumDataOrSetting.Data))
            {
                globalVar.set_pnl_transactionType(EnumTransactionType.PNL);
                globalVar.get_moneyTransactionSettings().set_typeCategory(EnumTypeCategory.Cost);
                ((FragmentMoneyAccounting) _fragmentViewCustom).GoToFragmentMoneyPNL(
                        _dataSet.get(pos).getCategoryId(), null,
                        _dataSet.get(pos));
            }
            else
            {
                globalVar.set_moneyCategorySettings_fromItem(R.id.navigation_money_accounting);
                globalVar.set_moneyCategorySettingEnumTypeCategory(EnumTypeCategory.Cost);
                ((MainActivity) _context).NavigationItemSelected(R.id.navigation_money_category,
                        R.id.navigation_money_pnl);
            }
        });

        holder.linearLayoutItem.setOnDragListener((view, dragEvent) ->
        {
            if (_dataSet.get(pos).get_dataOrSetting().equals(EnumDataOrSetting.Setting))
                return false;

            int action = dragEvent.getAction();
            switch (action)
            {
                case DragEvent.ACTION_DROP:
//                    final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
//                    if (!MoneyPNLIndependent.canAdd(_context, globalVariable.get_hasSubscription()))
//                    {
//                        SubscriptionClick(globalVariable);
//                        return false;
//                    }

                    MoneyAccountingSum costMAS = _moneyIncomingSumTrio.GetWalletMoneyAccountingSum();
                    MoneyAccountingSum incrMAS = _dataSet.get(pos);

                    if (incrMAS != null && costMAS != null)
                    {
                        _fragmentPopupNew = new FragmentMoneyCostPopupNew(
                                (AppCompatActivity) _context, null, EnumTypeAction.Insert,
                                _fragmentViewCustom, costMAS, incrMAS,
                                _dateStart, _dateEnd, true, true, true);
                        _fragmentPopupNew.PopupShow();
                    }
            }
            return true;
        });
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

            CustomTrack.PaidFormOpenFromAccounting();

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

                    CustomTrack.PaidFormOpenFromAccounting();

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

    public boolean LeftButtonClick()
    {
        //((FragmentMoneyIncreaseTable) _fragmentViewCustom).GoToFragmentMoneyIncoming();
        return true;
    }

    public boolean RightButtonClick()
    {
        //return DeleteCurrent();
        return false;
    }

    public void AddSubItem(MoneyPNLIndependent moneyPNL)
    {
        if (moneyPNL.get_typeCategory() != EnumTypeCategory.Cost)
            return;

        if (moneyPNL.get_isPlan())
            return;

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).getCategoryId(), moneyPNL.getIncreaseCategoryId()))
            {
                if (!moneyPNL.get_isPlan())
                {
                    _dataSet.get(i).set_budgetLong(_dataSet.get(i).get_budgetLong() - moneyPNL.get_totalSumLong());
                    notifyItemChanged(i);
                }
                break;
            }

        if (_dateStart.getTime().getTime() > moneyPNL.getDate().getTime())
            return;

        if (_dateEnd.getTime().getTime() < moneyPNL.getDate().getTime())
            return;

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).getCategoryId(), moneyPNL.getIncreaseCategoryId()))
            {
                _dataSet.get(i).set_sumLong(_dataSet.get(i).get_sumLong() + moneyPNL.get_totalSumLong());
                notifyItemChanged(i);
                break;
            }
    }

    public void ChangeBudget(MoneyPNLIndependent moneyPNL)
    {
        if (moneyPNL.get_typeCategory() != EnumTypeCategory.Increase &&
                moneyPNL.get_typeCategory() != EnumTypeCategory.Cost)
            return;

        if (moneyPNL.get_isPlan())
            return;

        if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase)
            for (MoneyInc4Cost item: moneyPNL.get_inc4Cost())
                for (int i = 0; i < _dataSet.size(); i++)
                    if (Objects.equals(_dataSet.get(i).getCategoryId(), item.getIncreaseCategoryId()))
                    {
                        _dataSet.get(i).set_budgetLong(_dataSet.get(i).get_budgetLong() + item.get_totalSumLong());
                        notifyItemChanged(i);
                        break;
                    }

        if (moneyPNL.get_typeCategory() == EnumTypeCategory.Cost)
            for (int i = 0; i < _dataSet.size(); i++)
                if (Objects.equals(_dataSet.get(i).getCategoryId(), moneyPNL.getIncreaseCategoryId()))
                {
                    _dataSet.get(i).set_budgetLong(_dataSet.get(i).get_budgetLong() - moneyPNL.get_totalSumLong());
                    notifyItemChanged(i);
                    break;
                }
    }

    public int AddItem(MoneyAccountingSum moneyAccountingSum)
    {
        /*Integer month = moneyIncrease.getImonth();
        Integer year = moneyIncrease.getIyear();

        int insertPosition = _dataSet.size();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if ((month < _dataSet.get(i).getImonth() && year.equals(_dataSet.get(i).getIyear()))
                || (year < _dataSet.get(i).getIyear()))
            {
                insertPosition = i;
                break;
            }
        }

        if (insertPosition > 0)
            moneyIncrease.set_upOrDown(_dataSet.get(insertPosition - 1).get_value());
        _dataSet.add(insertPosition, moneyIncrease);
        notifyItemInserted(insertPosition);

        if (insertPosition < _dataSet.size() - 1)
        {
            _dataSet.get(insertPosition + 1).set_upOrDown(_dataSet.get(insertPosition).get_value());
            notifyItemChanged(insertPosition + 1);
        }

        return insertPosition;*/

        return 0;
    }

    public int UpdateItem(MoneyAccountingSum moneyAccountingSum)
    {
        /*long id = moneyIncrease.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (id == _dataSet.get(i).get_id())
            {
                if (moneyIncrease.get_month().equals(_dataSet.get(i).get_month()) &&
                    moneyIncrease.get_year().equals(_dataSet.get(i).get_year()))
                {
                    _dataSet.set(i, moneyIncrease);
                    notifyItemChanged(i);
                    if (i < _dataSet.size() - 1)
                    {
                        _dataSet.get(i + 1).set_upOrDown(_dataSet.get(i).get_value());
                        notifyItemChanged(i + 1);
                    }
                    return 0;
                }
                else
                {
                    if (i > 0 && i < _dataSet.size() - 1)
                    {
                        _dataSet.get(i + 1).set_upOrDown(_dataSet.get(i - 1).get_value());
                        notifyItemChanged(i + 1);
                    }
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyIncrease);
                }
            }
        }*/

        return 0;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    public boolean DeleteCurrent()
    {
        /*try (DBHelper dbHelper = new DBHelper(_context))
        {
            if (dbHelper.DeleteMoneyIncrease(_currentItem.get_id()))
            {
                int index = _dataSet.indexOf(_currentItem);

                if (index > 0 && index < _dataSet.size() - 1)
                {
                    _dataSet.get(index + 1).set_upOrDown(_dataSet.get(index - 1).get_value());
                    notifyItemChanged(index + 1);
                }

                _dataSet.remove(_currentItem);
                notifyItemRemoved(index);
                return true;
            }
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyIncreaseNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {

            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyIncrease) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/

        return false;
    }

    public boolean DeleteFromDataset(MoneyAccountingSum moneyAccountingSum) throws Exception
    {
        return false;
    }


    public Long GetSumLong()
    {
        Long sumLong = 0L;
        for (MoneyAccountingSum moneyAccountingSum: _dataSet)
            sumLong += moneyAccountingSum.get_sumLong();
        return sumLong;
    }

    public static class ItemViewHolder extends CustomItemViewHolder<MoneyAccountingSum>
    {
        private final TextView textViewName;
        private final TextView textViewSum;
        private final TextView textViewBudget;
        private final ImageView imageViewIcon;
        final LinearLayout linearLayoutItem;
        private Hashtable<Long, Icon> _icons = new Hashtable<>();
        public void setIcons(Hashtable<Long, Icon> icons) { _icons = icons; }
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSum = itemView.findViewById(R.id.textViewSum);
            textViewBudget = itemView.findViewById(R.id.textViewBudget);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            linearLayoutItem = itemView.findViewById(R.id.linear_layout_item);
        }

        public void setName(EnumDataOrSetting dataOrSetting, String name)
        {
            textViewName.setText(dataOrSetting.equals(EnumDataOrSetting.Data) ? name : "");
        }

        public void setSum(EnumDataOrSetting dataOrSetting, Long sumLong)
        {
            textViewSum.setText(dataOrSetting.equals(EnumDataOrSetting.Data) ? CustomStaticClass.getRubleSum(sumLong / 100d) : "");
        }
        public void setBudget(MoneyAccountingSettings moneyAccountingSettings,
                              EnumDataOrSetting dataOrSetting, Long budgetLong)
        {
            GlobalClass gVar = (GlobalClass) _context.getApplicationContext();

            textViewBudget.setVisibility(moneyAccountingSettings.get_showBudget() && !gVar.get_hasUnpaidSubscription() ?
                    View.VISIBLE : View.INVISIBLE);
            textViewBudget.getLayoutParams().height = moneyAccountingSettings.get_showBudget() && !gVar.get_hasUnpaidSubscription() ?
                    ViewGroup.LayoutParams.WRAP_CONTENT : 0;
            textViewBudget.requestLayout();

            textViewBudget.setText(dataOrSetting.equals(EnumDataOrSetting.Data) ?
                    CustomStaticClass.getRubleSum(budgetLong / 100d) : "");

            if (!moneyAccountingSettings.get_showBudget() ||
                    dataOrSetting.equals(EnumDataOrSetting.Setting) ||
                    gVar.get_hasUnpaidSubscription())
                linearLayoutItem.setBackgroundResource(0);
            else
            {
                if (budgetLong >= 0L)
                    linearLayoutItem.setBackgroundResource(R.color.lightLightGreen);
                else
                    linearLayoutItem.setBackgroundResource(R.color.lightlightRed);
            }
        }

        public void setImage(EnumDataOrSetting dataOrSetting, Long icon)
        {
            if (dataOrSetting.equals(EnumDataOrSetting.Setting))
                imageViewIcon.setImageResource(R.drawable.icons_settings_purple_100);
            else if (icon != 0)
            {
                try { imageViewIcon.setImageResource(_icons.get(icon).get_icon_id()); }
                catch (Exception e) { }
            }
        }

        @Override
        public void setObject(MoneyAccountingSum moneyAccountig)
        {
            super.setObject(moneyAccountig);

            GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();
            setName(moneyAccountig.get_dataOrSetting(), moneyAccountig.getCategoryName());
            setSum(moneyAccountig.get_dataOrSetting(), moneyAccountig.get_sumLong());
            setBudget(globalVar.get_moneyAccountingSettings(),
                    moneyAccountig.get_dataOrSetting(), moneyAccountig.get_budgetLong());
            setImage(moneyAccountig.get_dataOrSetting(), moneyAccountig.get_icon());
        }
    }
}
