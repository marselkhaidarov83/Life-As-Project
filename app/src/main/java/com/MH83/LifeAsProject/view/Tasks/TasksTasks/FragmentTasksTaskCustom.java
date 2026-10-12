package com.MH83.LifeAsProject.view.Tasks.TasksTasks;

import static android.content.Context.NOTIFICATION_SERVICE;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.task.TasksTaskAdapterTreeView;
import com.MH83.LifeAsProject.adapter.spinner.SpinnerPeriodAdapter;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.data.Tasks.TasksTaskDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.ICollapseAllAdapter;
import com.MH83.LifeAsProject.interfac.adapter.IExpandAllAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeTask;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCollapseAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentExpandAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoCabinet;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoSocial;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Settings.NotifProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksTask;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskIndependent;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.Tasks.TasksInbox.FragmentTasksInbox;
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

public class FragmentTasksTaskCustom
        extends CustomFragment
        implements IFragmentViewCustom, IFragmentAddItem, IFragmentLegend,
        IFragmentExpandAll, IFragmentCollapseAll, IFragmentGotoCabinet, IFragmentGotoSocial
{
    //region Fragment parameters
    private final int _layoutId = R.layout.fragment_tasks_task_list;
    private final FragmentTasksTaskCustom _this;
    //endregion

    //region Fragment settings
    private CustomPeriodClass _customPeriodClass;
    private boolean _showSpinnerDate;
    //endregion

    //region Fragment Components
    RecyclerView recyclerView;
    private Spinner _spinnerDate;
    private ImageView _imageViewDateBack;
    private ImageView _imageViewDateForward;
    private LinearLayout _linearLayoutDate;
    //endregion

    //region Constructors
    public FragmentTasksTaskCustom() { _this = this; }

    private void StopNotif(Long projectId)
    {
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel((int) (NotifProjectSettings.NotifIdNeedTaskInProjectShift + projectId));
    }
    //endregion

    //region Start Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (globalVariable.get_hasUnpaidSubscription())
        {
            TasksTaskSettings tasksTaskSettings = globalVariable.get_tasksTaskSettingsHT();

            TasksTaskSettings tasksTaskSettingsNew = new TasksTaskSettings(
                    tasksTaskSettings.get_customPeriodClass(),
                    null,
                    0L,
                    0L,
                    0L,
                    EnumTaskSorting.Date_Priority_Name,
                    tasksTaskSettings.get_showSpinnerDate());

            globalVariable.set_tasksTaskSettingsHT(tasksTaskSettingsNew);
        }

        _customPeriodClass = globalVariable.get_tasksTaskSettingsHT().get_customPeriodClass();
        _showSpinnerDate = globalVariable.get_tasksTaskSettingsHT().get_showSpinnerDate();

        View view = inflater.inflate(_layoutId, container, false);

        FindComponents(view);
        SetVisibility();
        SetListeners();

        if (TasksTaskDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentTasksTask"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentTasksTask");
        }

        return view;
    }

    private void FindComponents(View view)
    {
        _linearLayoutDate = view.findViewById(R.id.linearLayoutDate);
        _spinnerDate = view.findViewById(R.id.spinnerDate);
        _imageViewDateBack = view.findViewById(R.id.imageViewDateBack);
        _imageViewDateForward = view.findViewById(R.id.imageViewDateForward);
        recyclerView = view.findViewById(R.id.recycleViewTasks);
    }

    private void SetVisibility()
    {
        if (!_showSpinnerDate)
            _linearLayoutDate.getLayoutParams().height = 0;
    }

    private void SetListeners()
    {
        _imageViewDateBack.setOnClickListener(view ->
        {
            if (_customPeriodClass.get_id() == 6)
                return;

            ((CustomPeriodClass) _spinnerDate.getSelectedItem()).DecShift();
            SetSettings((CustomPeriodClass) _spinnerDate.getSelectedItem());
            ((SpinnerPeriodAdapter) _spinnerDate.getAdapter()).notifyDataSetChanged();
            FillFormAll();
        });

        _imageViewDateForward.setOnClickListener(view ->
        {
            if (_customPeriodClass.get_id() == 6)
                return;

            ((CustomPeriodClass) _spinnerDate.getSelectedItem()).IncShift();
            SetSettings((CustomPeriodClass) _spinnerDate.getSelectedItem());
            ((SpinnerPeriodAdapter) _spinnerDate.getAdapter()).notifyDataSetChanged();
            FillFormAll();
        });

        FillSpinner();
    }

    private void FillFormAll()
    {
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        FillRecyclerView(globalVariable.get_tasksTaskSettingsHT(), true);
    }

    private void FillSpinner()
    {
        if (_customPeriodClass == null)
            return;

        ArrayList<CustomPeriodClass> list;
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (globalVariable.get_enumTaskSettings().equals(EnumTaskSettings.Today))
            list = CustomPeriodClass.GetListPlus0(getContext());
        else
            list = CustomPeriodClass.GetList(getContext());
        SpinnerPeriodAdapter adapter = new SpinnerPeriodAdapter(getContext(), list);
        _spinnerDate.setAdapter(adapter);
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).get_id() == _customPeriodClass.get_id())
            {
                _spinnerDate.setSelection(i);
                ((CustomPeriodClass) _spinnerDate.getSelectedItem())
                        .SetShift(_customPeriodClass.GetShift());
                break;
            }

        _spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                CustomPeriodClass customPeriodClass = ((CustomPeriodClass) _spinnerDate.getSelectedItem());
                for (int ii = 0; ii < _spinnerDate.getAdapter().getCount(); ii++)
                    if (customPeriodClass.get_id() !=
                            ((CustomPeriodClass) _spinnerDate.getAdapter().getItem(ii)).get_id())
                        ((CustomPeriodClass) _spinnerDate.getAdapter().getItem(ii)).ResetShift();

                ((CustomPeriodClass) _spinnerDate.getSelectedItem()).ResetShift();
                SetSettings((CustomPeriodClass) _spinnerDate.getSelectedItem());
                FillFormAll();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void FillRecyclerView(TasksTaskSettings tasksTaskSettings, boolean firstUse)
    {
        TasksTaskAdapterTreeView adapter = new TasksTaskAdapterTreeView(getContext(),
                this,
                get_customPeriodClass().get_start_dateC(),
                get_customPeriodClass().get_end_dateC(),
                tasksTaskSettings, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < recyclerView.getItemDecorationCount(); i++)
            recyclerView.removeItemDecorationAt(0);
        RecyclerView.ItemDecoration itemViewDecoration = null;
        if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Date_Priority_Name))
            itemViewDecoration = new TasksTaskItemViewDecoration1(getContext());
        else if (tasksTaskSettings.get_taskSorting().equals(EnumTaskSorting.Priority_Date_Name))
            itemViewDecoration = new TasksTaskItemViewDecoration2(getContext());
        if (itemViewDecoration != null)
            recyclerView.addItemDecoration(itemViewDecoration);

        if (firstUse)
        {
            int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.Tasks_Task_BasedBorderLineDp);
            TasksTaskSimpleCallback misc = new TasksTaskSimpleCallback(recyclerView, basedBorderLineLeftDp);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }
    //endregion

    //region GetData
    private CustomPeriodClass get_customPeriodClass()
    {
        return (CustomPeriodClass) _spinnerDate.getSelectedItem();
    }
    //endregion

    //region SaveSettings
    private void SetSettings(CustomPeriodClass customPeriodClass)
    {
        _customPeriodClass = new CustomPeriodClass(customPeriodClass.get_id(), customPeriodClass.get_name());
        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        TasksTaskSettings tasksTaskSettings = globalVariable.get_tasksTaskSettingsHT();
        tasksTaskSettings.set_customPeriodClass(_customPeriodClass);
        globalVariable.set_tasksTaskSettingsHT(tasksTaskSettings);
    }
    //endregion

    //region IFragmentExpandAll
    public boolean needExpandButton(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();
        return !global.get_hasUnpaidSubscription();
    }
    //endregion

    //region IFragmentCollapseAll
    public boolean needCollapseButton(Context context)
    {
        GlobalClass global = (GlobalClass) context.getApplicationContext();
        return !global.get_hasUnpaidSubscription();
    }
    //endregion

    //region Click to button
    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }

    private void ClickToLegend()
    {
        new FragmentTasksTaskLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }

    @Override
    public View.OnClickListener onClickCollapseAll()
    {
        return view ->
        {
            ICollapseAllAdapter adapter = ((ICollapseAllAdapter) recyclerView.getAdapter());
            if (adapter != null)
                adapter.CollapseAll(true);
        };
    }

    @Override
    public View.OnClickListener onClickExpandAll()
    {
        return view ->
        {
            IExpandAllAdapter adapter = ((IExpandAllAdapter) recyclerView.getAdapter());
            if (adapter != null)
                adapter.ExpandAll(true);
        };
    }
    //endregion

    //region IFragmentAddItem
    @Override
    public boolean ShowAddItemButton() { return true; }

    @Override
    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (!TasksTask.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick();
                return;
            }

            new FragmentTasksTaskPopupNew(
                    (AppCompatActivity) getActivity(),
                    EnumTypeTask.Task,
                    null,
                    EnumTypeAction.Insert,
                    null,
                    _this,
                    null,
                    _this instanceof FragmentTasksInbox,
                    false,
                    null).PopupShow();
        };
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof TasksTaskIndependent)
        {
            TasksTaskAdapterTreeView adapter = (TasksTaskAdapterTreeView) recyclerView.getAdapter();
            if (adapter != null)
                recyclerView.scrollToPosition(Math.toIntExact(adapter.AddItem((TasksTaskIndependent) object)));

            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_typeAction4Popup2(null);
            globalVariable.set_IFragmentViewCustom2(null);

            StopNotif(((TasksTaskIndependent) object).get_project_id());

            return true;
        }
        else if (object instanceof TasksTaskSettings)
        {
            FillRecyclerView((TasksTaskSettings) object, false);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof TasksTaskIndependent)
        {
            TasksTaskAdapterTreeView adapter = (TasksTaskAdapterTreeView) recyclerView.getAdapter();
            if (adapter != null)
                recyclerView.scrollToPosition(Math.toIntExact(adapter.UpdateItem((TasksTaskIndependent) object)));

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof TasksTaskIndependent)
        {
//            TasksTaskAdapter adapter = (TasksTaskAdapter) recyclerView.getAdapter();
            TasksTaskAdapterTreeView adapter = (TasksTaskAdapterTreeView) recyclerView.getAdapter();
            if (adapter != null)
                adapter.DeleteFromDataset((TasksTaskIndependent) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) { return false; }
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

            CustomTrack.PaidFormOpenFromTasksTask();

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

                    CustomTrack.PaidFormOpenFromTasksTask();

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
    //endregion
}