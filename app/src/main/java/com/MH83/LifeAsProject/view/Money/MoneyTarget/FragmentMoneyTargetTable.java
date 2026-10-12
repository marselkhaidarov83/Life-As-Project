package com.MH83.LifeAsProject.view.Money.MoneyTarget;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IGetTitle;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentMinus;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentParam;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentPlus;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetFinished;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetInJob;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Settings.NotifMoneySettings;
import com.MH83.LifeAsProject.model.Settings.NotifProjectSettings;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Money.MoneyTargetDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.model.Money.MoneyDream;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.rView.money.MoneyTargetAdapter;
import com.MH83.LifeAsProject.data.Money.MoneyDreamDataSource;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentBack;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSetPopupImage;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Money.MoneyDream.FragmentMoneyDreamPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class FragmentMoneyTargetTable extends CustomFragment implements IFragmentViewCustom,
        IFragmentBack, IFragmentAddItem, IFragmentSetPopupImage, IFragmentLegend,
        IFragmentParam, IFragmentSettings,
        IFragmentShowTargetAll, IFragmentShowTargetInJob, IFragmentShowTargetFinished,
        IGetTitle, IFragmentMinus, IFragmentPlus
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_money_target;
    //endregion

    //region Fragment parameters
    private final FragmentMoneyTargetTable _this;
    private MoneyDream _moneyDream;
    private ArrayList<MoneyTarget> _dataSet = null;
    //endregion

    //region Fragment Components
    private RecyclerView rView;
    private FragmentMoneyTargetPopupNew _fragmentMoneyTargetPopup;
    MaterialCardView materialCardViewDream;
    private TextView textViewDreamName;
    private ImageView imageViewDream;
    private ImageView imageViewStatus;
    private TextView textViewStatus;
    //endregion

    //region Fragment settings
//    private int _scrollPosition = 0;
//    public int GetScrollPosition()
//    {
//        _scrollPosition = ((LinearLayoutManager) rView.getLayoutManager()).findFirstVisibleItemPosition();
//        return _scrollPosition;
//    }
    //endregion

    //region IGetTitle
    @Override
    public String GetTitle(Context context)
    {
        Integer count = null;
        if (rView != null && rView.getAdapter() != null)
            count = rView.getAdapter().getItemCount();

        final GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        if (globalVar.get_moneyTargetDreamId() != null)
            return context.getString(R.string.money_your_target);
        else
        {
            if (globalVar.get_moneyTargetEnumTargetStatus4Settings() == EnumTargetStatus4Settings.Finished)
                return context.getString(R.string.AchievedTargets) +
                        (count != null ? " (" + count + ")" : "");
            else if (globalVar.get_moneyTargetYear() != null)
                return context.getString(R.string.money_your_target) + " (" +
                        globalVar.get_moneyTargetYear() + ")" +
                        (count != null ? " (" + count + ")" : "");
        }

        return context.getString(R.string.money_your_target);
    }
    //endregion

    //region Constructors
    public FragmentMoneyTargetTable()
    {
        _this = this;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        StopNotifAccumulationDone();
    }

    private void StopNotifAccumulationDone()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (globalVariable.get_moneyTargetDreamId() != null)
        {
            NotificationManager notificationManager =
                    (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel((int) (NotifMoneySettings.NotifAccumulationDone +
                                globalVariable.get_moneyTargetDreamId()));
        }
    }

    private void StopNotifNeedTarget()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel((int) (NotifProjectSettings.NotifIdNeedTargetShift +
                        globalVariable.get_moneyTargetDreamId()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container, false);
        FindComponents(view);
        SetListeners();

        SetComponentsSize();
        SetAdapters();
        SetAdapterPosition();
        SetMainData();

//        if (_scrollPosition != 0)
//            rView.scrollToPosition(_scrollPosition);

        return view;
    }

    private void SetAdapters()
    {
        LoadData();
        MoneyTargetAdapter adapter = new MoneyTargetAdapter(getContext(), _dataSet,
                this, GetHeightTarget());
        rView.setAdapter(adapter);
    }

    private void SetAdapterPosition()
    {
        GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
        Long id = globalVar.get_moneyTargetTargetId();
        if (id != 0)
        {
            int pos = 0;
            for (MoneyTarget item: ((MoneyTargetAdapter) rView.getAdapter()).get_dataSet())
            {
                if (Objects.equals(item.get_id(), id))
                {
                    rView.scrollToPosition(pos);
                    break;
                }
                pos++;
            }
        }
    }

    private void SetComponentsSize()
    {
        GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if (globalVar.get_moneyTargetDreamId() != null)
            {
                materialCardViewDream.getLayoutParams().width = GetMaterialCardWidth();
                rView.getLayoutParams().width = GetRViewWidth();
            }
            else
            {
                materialCardViewDream.getLayoutParams().width = 0;
                ((LinearLayout.LayoutParams) materialCardViewDream.getLayoutParams()).setMarginStart(0);
                rView.getLayoutParams().width = GetRViewWidthFull();
            }
        }
        else
        {
            if (globalVar.get_moneyTargetDreamId() != null)
            {
                materialCardViewDream.getLayoutParams().height = GetMaterialCardHeight();
                imageViewDream.getLayoutParams().height = GetMaterialCardHeight();
                rView.getLayoutParams().height = GetRViewHeight();
            }
            else
            {
                materialCardViewDream.getLayoutParams().height = 0;
                ((LinearLayout.LayoutParams) rView.getLayoutParams()).setMargins(0, 0, 0, 0);
                imageViewDream.getLayoutParams().height = 0;
                rView.getLayoutParams().height = GetRViewHeightFull();
            }

        }
    }

    private int GetMaterialCardHeight()
    {
        return (int) (GetHeightTarget() + getResources().getDimension(R.dimen.BasePadding));
    }

    private int GetRViewHeightFull()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            return (int) (6 * (GetHeightTarget() + getResources().getDimension(R.dimen.BasePadding)));
        else
            return (int) (3 * (GetHeightTarget() + getResources().getDimension(R.dimen.BasePadding)));
    }

    private int GetRViewHeight()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
            return (int) (5 * (GetHeightTarget() + getResources().getDimension(R.dimen.BasePadding)));
        else
            return (int) (2 * (GetHeightTarget() + getResources().getDimension(R.dimen.BasePadding)));
    }

    private int GetWidthDp(DisplayMetrics displaymetrics)
    {
        int baseWidthTargetDp = (int) (displaymetrics.widthPixels / displaymetrics.scaledDensity);
        return baseWidthTargetDp - 5;
    }

    private int GetMaterialCardWidth()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return (int) (displaymetrics.scaledDensity * GetWidthDp(displaymetrics) * 3 / 10);
    }

    private int GetRViewWidthFull()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return (int) (displaymetrics.scaledDensity * GetWidthDp(displaymetrics) +
                getResources().getDimension(R.dimen.BasePadding));
    }

    private int GetRViewWidth()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return (int) (displaymetrics.scaledDensity * GetWidthDp(displaymetrics) * 7 / 10);
    }

    private void LoadData()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (_moneyDream == null)
            _moneyDream = MoneyDreamDataSource.GetOneMoneyDream(getContext(),
                    globalVariable.get_moneyTargetDreamId());

        _dataSet = MoneyTargetDataSource.LoadData(getContext().getApplicationContext());

        if (MoneyTargetDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentMoneyTarget"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentMoneyTarget");
        }
    }

    private void SetListeners()
    {
        materialCardViewDream.setOnClickListener(view1 ->
        {
            new FragmentMoneyDreamPopupNew((AppCompatActivity) getContext(), _moneyDream,
                    EnumTypeAction.Update, _this).PopupShow();
//            FragmentMoneyDreamPopup fragmentPopup = new FragmentMoneyDreamPopup(getContext(),
//                    _this, EnumTypeAction.Update);
//            fragmentPopup.PopupShow(LayoutInflater.from(getContext()), _moneyDream);
        });
    }

    private void FindComponents(View view)
    {
        materialCardViewDream = view.findViewById(R.id.materialCardViewDream);
        textViewDreamName = view.findViewById(R.id.textViewDreamName);
        imageViewDream = view.findViewById(R.id.imageViewDreamImage);
        imageViewStatus = view.findViewById(R.id.imageViewStatus);
        textViewStatus = view.findViewById(R.id.textViewStatus);
        rView = view.findViewById(R.id.listViewR);
    }

    private int GetHeightTarget()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int heightTarget = displaymetrics.heightPixels;
        heightTarget += getResources().getDimension(R.dimen.UnknownToolbarHeight);
        heightTarget -= getResources().getDimension(R.dimen.Fragment_MainActivity_Toolbar_Height) +
                getResources().getDimension(R.dimen.MoneyDreamBottomToolbarHeight);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            if (globalVariable.get_moneyTargetDreamId() != null)
                heightTarget -= getResources().getDimension(R.dimen.BasePadding);
            else
            {
                if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
                    heightTarget -= getResources().getDimension(R.dimen.HalfPadding);
                else
                    heightTarget += getResources().getDimension(R.dimen.HalfPadding);
            }
            heightTarget -= getResources().getDimension(R.dimen.UnknownPortraitHeight);
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
                heightTarget /= 6;
            else
                heightTarget /= 3;
        }
        else
        {
            heightTarget -= getResources().getDimension(R.dimen.UnknownLandscapeHeight);
            if (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet)
                heightTarget /= 3;
            else
            {
                heightTarget += getResources().getDimension(R.dimen.BasePadding);
                heightTarget /= 1;
            }
        }

        return heightTarget;
    }
    //endregion

    //region Set Params
    private void SetMainData()
    {
        if (_moneyDream != null)
        {
            setDreamName(_moneyDream.get_name());
            setImage();
            setStatus(_moneyDream.getStatusColor());
            setTextStatus(_moneyDream.getTextStatus());
        }

        ((MainActivity) getActivity()).SetTitle();
    }

    private void setTextStatus(String textStatus)
    {
        textViewStatus.setText(textStatus);
    }

    private void setStatus(int statusColor)
    {
        imageViewStatus.setImageResource(statusColor);
    }
    private void setDreamName(String dreamName)
    {
        textViewDreamName.setText(dreamName);
    }
    private void setImage()
    {
        Context context = getContext();
        if (context != null)
        {
            File internalStorage = context.getDir(DBHelper.getDbName(getContext()) + "DreamPictures", Context.MODE_PRIVATE);
            File filePath = new File(internalStorage, _moneyDream.get_id() + ".jpeg");

            try
            {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getPath());
                imageViewDream.setImageBitmap(bitmap);
            }
            catch (Exception ex)
            {
                Log.i("DATABASE", "Problem reading picture", ex);
            }
        }
    }
    //endregion

    //region IFragmentBack
    public void ClickToBack()
    {
        if (getActivity() != null)
            ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_money_dream);
    }
    //endregion

    //region Click to button
    @Override
    public View.OnClickListener onClickToParam()
    {
        return view ->
        {
            if (getActivity() != null)
                ((MainActivity)getActivity()).NavigationItemSelected(R.id.navigation_param,
                        R.id.navigation_money_target);
        };
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }

    public void setPopupImage(Uri uri)
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

        if (_fragmentMoneyTargetPopup == null)
            _fragmentMoneyTargetPopup = (FragmentMoneyTargetPopupNew) globalVariable.get_popup();

        if (_fragmentMoneyTargetPopup != null)
            _fragmentMoneyTargetPopup.set_Image(uri);

        if (rView.getAdapter() != null)
            ((MoneyTargetAdapter) rView.getAdapter()).setPopupImage(uri);
    }
    //endregion

    //region IFragmentAddItem
    public boolean ShowAddItemButton()
    {
        return true;
    }

    @Override
    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (!MoneyTarget.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick(globalVariable);
                return;
            }

            globalVariable.get_imagePath(true);
            _fragmentMoneyTargetPopup = new FragmentMoneyTargetPopupNew((AppCompatActivity) getContext(),
                    null, EnumTypeAction.Insert, _this, _moneyDream.get_id());
            _fragmentMoneyTargetPopup.PopupShow();
        };
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
        new FragmentMoneyTargetLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

    //region Subscription
    private void SubscriptionClick(GlobalClass globalVariable)
    {
        if (GlobalClass.SubscrGoogle())
            SubscriptGoogle(globalVariable);
        else if (GlobalClass.SubscrRustore())
            SubscriptRustore(globalVariable);
    }

    private void SubscriptGoogle(GlobalClass globalVariable)
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

                    CustomTrack.PaidFormOpenFromTarget();

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

    private void SubscriptRustore(GlobalClass globalVariable)
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

            CustomTrack.PaidFormOpenFromTarget();

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

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyTarget)
        {
            MoneyTargetAdapter moneyTargetAdapter = ((MoneyTargetAdapter) rView.getAdapter());
            if (moneyTargetAdapter != null)
            {
                int pos = moneyTargetAdapter.AddItem((MoneyTarget) object);
                if (pos >= 0)
                    rView.smoothScrollToPosition(pos);
            }

            _fragmentMoneyTargetPopup = null;

            _moneyDream = MoneyDreamDataSource.GetOneMoneyDream(getContext(), _moneyDream.get_id());
            SetMainData();
            StopNotifNeedTarget();

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyTarget)
        {
            MoneyTargetAdapter moneyTargetAdapter = ((MoneyTargetAdapter) rView.getAdapter());
            if (moneyTargetAdapter != null)
            {
                int pos = moneyTargetAdapter.UpdateItem((MoneyTarget) object);
                if (pos >= 0)
                    rView.smoothScrollToPosition(pos);
            }

            _fragmentMoneyTargetPopup = null;

            if (_moneyDream != null)
                _moneyDream = MoneyDreamDataSource.GetOneMoneyDream(getContext(), _moneyDream.get_id());
            SetMainData();

            return true;
        }
        else if (object instanceof MoneyDream)
        {
            _moneyDream = MoneyDreamDataSource.GetOneMoneyDream(getContext(), _moneyDream.get_id());
            SetMainData();

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyTarget)
        {
            MoneyTargetAdapter moneyTargetAdapter = ((MoneyTargetAdapter) rView.getAdapter());
            if (moneyTargetAdapter != null)
                moneyTargetAdapter.DeleteFromDataset((MoneyTarget) object);

            if (_moneyDream != null)
                _moneyDream = MoneyDreamDataSource.GetOneMoneyDream(getContext(), _moneyDream.get_id());
            SetMainData();

            return true;
        }

        return false;
    }
    //endregion

    //region IFragmentSetting
    @Override
    public View.OnClickListener onClickSettings()
    {
        return view ->
        {

        };
    }

    @Override
    public boolean ShowFormSettingButton() { return false; }
    //endregion

    //region IFragmentShowTargetAll
    @Override
    public boolean ShowTargetAll(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        if (globalVar.get_moneyTargetSettings() == null)
            return false;
        else
            return globalVar.get_moneyTargetSettings().get_targetStatus4Settings().equals(EnumTargetStatus4Settings.Finished);
    }

    @Override
    public View.OnClickListener onClickToShowTargetAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyTargetSettings().set_targetStatus4Settings(EnumTargetStatus4Settings.All);
            SetAdapters();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowTargetFinished
    @Override
    public boolean ShowTargetFinished(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        if (globalVar.get_moneyTargetSettings() == null)
            return false;
        else
            return globalVar.get_moneyTargetSettings().get_targetStatus4Settings().equals(EnumTargetStatus4Settings.InJob);
    }

    @Override
    public View.OnClickListener onClickToShowTargetFinished()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyTargetSettings().set_targetStatus4Settings(EnumTargetStatus4Settings.Finished);
            SetAdapters();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentShowTargetInJob
    @Override
    public boolean ShowTargetInJob(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        if (globalVar.get_moneyTargetSettings() == null)
            return false;
        else
            return globalVar.get_moneyTargetSettings().get_targetStatus4Settings().equals(EnumTargetStatus4Settings.All);
    }

    @Override
    public View.OnClickListener onClickToShowTargetInJob()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_moneyTargetSettings().set_targetStatus4Settings(EnumTargetStatus4Settings.InJob);
            SetAdapters();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion

    //region IFragmentMinus
    @Override
    public boolean ShowMinus(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        return globalVar.get_moneyTargetDreamId() == null
                && globalVar.get_moneyTargetYear() != null;
    }

    @Override
    public View.OnClickListener onClickToMinus()
    {
        return v ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.minus1_moneyTargetYear();
            SetAdapters();
            ((MainActivity) getActivity()).SetTitle();
        };
    }
    //endregion

    //region IFragmentPlus
    @Override
    public boolean ShowPlus(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();
        return globalVar.get_moneyTargetDreamId() == null
                && globalVar.get_moneyTargetYear() != null;
    }

    @Override
    public View.OnClickListener onClickToPlus()
    {
        return v ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.plus1_moneyTargetYear();
            SetAdapters();
            ((MainActivity) getActivity()).SetTitle();
        };
    }
    //endregion
}