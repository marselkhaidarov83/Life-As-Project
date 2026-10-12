package com.MH83.LifeAsProject.adapter.rView.money;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.content.ClipData;
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
import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;
import com.MH83.LifeAsProject.model.Money.MoneyPNLIndependent;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccounting;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyAccountingPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyIncomingPopupNew;
import com.MH83.LifeAsProject.view.Money.MoneyAccounting.FragmentMoneyMovingPopupNew;
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

public class MoneyWalletSumAdapter
        extends CustomMoneyAdapter<MoneyAccountingSum,
                        MoneyWalletSumAdapter.ItemViewHolder, FragmentMoneyAccountingPopupNew>
{
    private final Calendar _dateStart;
    private final Calendar _dateEnd;
    private final MoneyIncomingSumTrio _moneyIncomingSumTrio;
    private final Hashtable<Long, Icon> _icons;

    public MoneyWalletSumAdapter(Context context, ArrayList<MoneyAccountingSum> dataSet,
                                 IFragmentViewCustom fragmentMoneyIncreaseCustom,
                                 MoneyIncomingSumTrio moneyIncomingSumTrio,
                                 Calendar dateStart, Calendar dateEnd)
    {
        super(context, dataSet, fragmentMoneyIncreaseCustom, R.layout.item_money_accounting);
        _moneyIncomingSumTrio = moneyIncomingSumTrio;
        _dateStart = dateStart;
        _dateEnd = dateEnd;
        _icons = IconDataSource.GetHashtable(EnumTypeCategory.Wallet);
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
        holder.setIcons(_icons);
        int pos = position;
        super.onBindViewHolder(holder, position);

        holder.itemView.setOnClickListener(view ->
        {
            GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();
            if (_dataSet.get(pos).get_dataOrSetting().equals(EnumDataOrSetting.Data))
            {
                globalVar.set_pnl_transactionType(EnumTransactionType.PNL);
                globalVar.get_moneyTransactionSettings().set_typeCategory(EnumTypeCategory.All);
                ((FragmentMoneyAccounting) _fragmentViewCustom).GoToFragmentMoneyPNL(
                        _dataSet.get(pos).getCategoryId(), null,
                        _dataSet.get(pos));
            }
            else
            {
                globalVar.set_moneyCategorySettings_fromItem(R.id.navigation_money_accounting);
                globalVar.set_moneyCategorySettingEnumTypeCategory(EnumTypeCategory.Wallet);
                ((MainActivity) _context).NavigationItemSelected(R.id.navigation_money_category,
                        R.id.navigation_money_pnl);
            }
        });

        holder.itemView.setOnLongClickListener(view ->
        {
            if (_dataSet.get(pos).get_dataOrSetting().equals(EnumDataOrSetting.Setting))
                return false;

            GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
            if (globalVariable.get_hasUnpaidSubscription())
            {
                Calendar calendarThisMonthBegin = Calendar.getInstance();
                calendarThisMonthBegin.set(Calendar.DATE, 1);
                calendarThisMonthBegin.set(Calendar.HOUR, 0);
                calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
                calendarThisMonthBegin.set(Calendar.MINUTE, 0);
                calendarThisMonthBegin.set(Calendar.SECOND, 0);
                calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);

                Calendar calendarThisMonthEnd = Calendar.getInstance();
                calendarThisMonthBegin.set(Calendar.DATE, 1);
                calendarThisMonthBegin.set(Calendar.HOUR, 0);
                calendarThisMonthBegin.set(Calendar.HOUR_OF_DAY, 0);
                calendarThisMonthBegin.set(Calendar.MINUTE, 0);
                calendarThisMonthBegin.set(Calendar.SECOND, 0);
                calendarThisMonthBegin.set(Calendar.MILLISECOND, 0);
                calendarThisMonthEnd.add(Calendar.MONTH, 1);
                calendarThisMonthEnd.add(Calendar.SECOND, -1);

                if (!(_dateStart.getTime().getTime() >= calendarThisMonthBegin.getTime().getTime() &&
                        _dateStart.getTime().getTime() <= calendarThisMonthEnd.getTime().getTime()) ||
                        !(_dateEnd.getTime().getTime() >= calendarThisMonthBegin.getTime().getTime() &&
                                _dateEnd.getTime().getTime() <= calendarThisMonthEnd.getTime().getTime()))
                    return false;
            }

            _moneyIncomingSumTrio.SetWalletMoneyAccountingSum(_dataSet.get(pos));
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(data, shadowBuilder, view, 0);
            return true;
        });

        holder.linearLayoutItem.setOnDragListener((view, dragEvent) ->
        {
            if (_dataSet.get(pos).get_dataOrSetting().equals(EnumDataOrSetting.Setting))
                return false;

            int action = dragEvent.getAction();
            if (action == DragEvent.ACTION_DROP)
            {
//                final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
//                if (!MoneyPNLIndependent.canAdd(_context, globalVariable.get_hasSubscription()))
//                {
//                    SubscriptionClick(globalVariable);
//                    return false;
//                }

                MoneyAccountingSum costMAS = _moneyIncomingSumTrio.GetIncomMoneyAccountingSum();
                MoneyAccountingSum incrMAS = _dataSet.get(pos);

                if (incrMAS != null && costMAS != null &&
                        costMAS.getTypeCategory() == EnumTypeCategory.Increase)
                {
                    _fragmentPopupNew = new FragmentMoneyIncomingPopupNew(
                            (AppCompatActivity) _context, null, EnumTypeAction.Insert,
                            _fragmentViewCustom, costMAS, incrMAS,
                            _dateStart, _dateEnd, true, true);
                    _fragmentPopupNew.PopupShow();
                }
                else
                {
                    incrMAS = _dataSet.get(pos);
                    costMAS = _moneyIncomingSumTrio.GetWalletMoneyAccountingSum();

                    if (incrMAS != null && costMAS != null && incrMAS != costMAS &&
                            costMAS.getTypeCategory() == EnumTypeCategory.Wallet)
                    {
                        _fragmentPopupNew = new FragmentMoneyMovingPopupNew(
                                (AppCompatActivity) _context, null, EnumTypeAction.Insert,
                                _fragmentViewCustom, costMAS, incrMAS,
                                _dateStart, _dateEnd, true, true);
                        _fragmentPopupNew.PopupShow();
                    }
                }
            }
            return true;
        });

    }

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
        if (moneyPNL.get_typeCategory() == EnumTypeCategory.Increase ||
            moneyPNL.get_typeCategory() == EnumTypeCategory.Wallet)
        {
            Long walletCategoryId = moneyPNL.getIncreaseCategoryId();
//            Double sum = moneyPNL.get_totalSum();
            Long sumLong = moneyPNL.get_totalSumLong();

            for (int i = 0; i < _dataSet.size(); i++)
                if (Objects.equals(_dataSet.get(i).getCategoryId(), walletCategoryId))
                {
//                    _dataSet.get(i).setSum(_dataSet.get(i).getSum() + sum);
                    _dataSet.get(i).set_sumLong(_dataSet.get(i).get_sumLong() + sumLong);
                    notifyItemChanged(i);
                    break;
                }
        }

        if (moneyPNL.get_typeCategory() == EnumTypeCategory.Cost ||
                moneyPNL.get_typeCategory() == EnumTypeCategory.Wallet)
        {
            Long walletCategoryId = moneyPNL.getCostCategoryId();
//            Double sum = -moneyPNL.get_totalSum();
            Long sumLong = -moneyPNL.get_totalSumLong();

            for (int i = 0; i < _dataSet.size(); i++)
                if (Objects.equals(_dataSet.get(i).getCategoryId(), walletCategoryId))
                {
//                    _dataSet.get(i).setSum(_dataSet.get(i).getSum() + sum);
                    _dataSet.get(i).set_sumLong(_dataSet.get(i).get_sumLong() + sumLong);
                    notifyItemChanged(i);
                    break;
                }
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

//    public Double GetSum()
//    {
//        Double sum = 0d;
//        for (MoneyAccountingSum moneyAccountingSum: _dataSet)
//            sum += moneyAccountingSum.getSum();
//        return sum;
//    }

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
//        public void setSum(EnumDataOrSetting dataOrSetting, Double sum)
//        {
//            textViewSum.setText(dataOrSetting.equals(EnumDataOrSetting.Data) ? CustomStaticClass.getRubleSum(sum) : "");
//        }
        public void setSumLong(EnumDataOrSetting dataOrSetting, Long sumLong)
        {
            textViewSum.setText(dataOrSetting.equals(EnumDataOrSetting.Data) ? CustomStaticClass.getRubleSum(sumLong / 100d) : "");
        }
        public void setBudget()
        {
            textViewBudget.getLayoutParams().height = 0;
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
            setName(moneyAccountig.get_dataOrSetting(), moneyAccountig.getCategoryName());
//            setSum(moneyAccountig.get_dataOrSetting(), moneyAccountig.getSum());
            setSumLong(moneyAccountig.get_dataOrSetting(), moneyAccountig.get_sumLong());
            setBudget();
            setImage(moneyAccountig.get_dataOrSetting(), moneyAccountig.get_icon());
        }
    }
}
