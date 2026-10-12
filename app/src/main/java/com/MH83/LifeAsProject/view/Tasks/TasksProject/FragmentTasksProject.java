package com.MH83.LifeAsProject.view.Tasks.TasksProject;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.task.TasksProjectAdapter;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeView;
import com.MH83.LifeAsProject.data.Tasks.TasksProjectDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.ICollapseAllAdapter;
import com.MH83.LifeAsProject.interfac.adapter.IExpandAllAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumTargetStatus4Settings;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentAddItem;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentCollapseAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentExpandAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoCabinet;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentGotoSocial;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentLegend;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentSettings;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetAll;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetFinished;
import com.MH83.LifeAsProject.interfac.fragmentButton.IFragmentShowTargetInJob;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Tasks.TaskProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;
import com.MH83.LifeAsProject.view.Custom.CustomFragment;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
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

public class FragmentTasksProject extends CustomFragment implements IFragmentAddItem,
        IFragmentViewCustom<TasksProject>, IFragmentExpandAll, IFragmentCollapseAll,
        IFragmentShowTargetAll, IFragmentShowTargetInJob, IFragmentShowTargetFinished,
        IFragmentLegend, IFragmentSettings, IFragmentGotoSocial, IFragmentGotoCabinet
{
    //region Constants
    private static final int _layoutId = R.layout.fragment_tasks_project;
    //endregion

    //region Fragment parameters
    private final FragmentTasksProject _this;
    //endregion

    //region Fragment Components
    private TreeView treeViewProjects;
    //endregion

    //region Constructors
    public FragmentTasksProject()
    {
        _this = this;
    }
    //endregion

    //region Start Fragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (globalVariable.get_hasUnpaidSubscription())
        {
            TaskProjectSettings taskProjectSettings =
                    globalVariable.get_taskProjectSettings();

            taskProjectSettings.set_enumTargetStatus4Settings(EnumTargetStatus4Settings.All);

            globalVariable.set_taskProjectSettings(taskProjectSettings);
        }

        View view = inflater.inflate(_layoutId, container, false);

        treeViewProjects = view.findViewById(R.id.tree_view);

        SetAdapters();

        if (TasksProjectDataSource.GetCount(getContext()) == 0 &&
                !globalVariable.get_legendShowed().equals("FragmentTasksProject"))
        {
            ClickToLegend();
            globalVariable.set_legendShowed("FragmentTasksProject");
        }

        int basedBorderLineLeftDp = (int) getResources().getDimension(R.dimen.TasksProjectLineBasedBorderLineDp);

        TasksProjectSimpleCallback misc = new TasksProjectSimpleCallback(treeViewProjects, basedBorderLineLeftDp);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(misc);
        itemTouchHelper.attachToRecyclerView(treeViewProjects);

        return view;
    }

    private void SetAdapters()
    {
        TasksProjectAdapter adapter = new TasksProjectAdapter(getContext(), this);
        treeViewProjects.setAdapter(adapter);
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

    //region IFragmentAddItem
    @Override
    public View.OnClickListener onClickToAdd()
    {
        return view ->
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (!TasksProject.canAdd(getContext(), globalVariable))
            {
                SubscriptionClick(globalVariable);
                return;
            }

            new FragmentTasksProjectPopupNew((AppCompatActivity) getContext(),
                    null, EnumTypeAction.Insert, this).PopupShow();
        };
    }

    @Override
    public boolean refreshViewOnAdd(TasksProject tasksProject)
    {
        TasksProjectAdapter adapter = (TasksProjectAdapter) treeViewProjects.getAdapter();
        if (adapter != null)
        {
            Long pos = adapter.AddItem(tasksProject);
            treeViewProjects.smoothScrollToPosition(Math.toIntExact(pos));
        }

        return true;
    }
    //endregion

    //region Click to button
    public boolean ShowAddItemButton() { return true; }

    @Override
    public boolean refreshViewOnUpdate(TasksProject tasksProject)
    {
        TasksProjectAdapter adapter = (TasksProjectAdapter) treeViewProjects.getAdapter();
        if (adapter != null)
            adapter.UpdateItem(tasksProject);

        return true;
    }

    @Override
    public boolean refreshViewOnDelete(TasksProject tasksProject)
    {
        TasksProjectAdapter adapter = (TasksProjectAdapter) treeViewProjects.getAdapter();
        if (adapter != null)
            adapter.DeleteFromDataset(tasksProject);

        return true;
    }

    @Override
    public boolean refreshViewOnSelect(TasksProject tasksProject) { return false; }

    @Override
    public View.OnClickListener onClickCollapseAll()
    {
        return view ->
        {
            ICollapseAllAdapter adapter = ((ICollapseAllAdapter) treeViewProjects.getAdapter());
            if (adapter != null)
                adapter.CollapseAll(true);
        };
    }

    @Override
    public View.OnClickListener onClickExpandAll()
    {
        return view ->
        {
            IExpandAllAdapter adapter = ((IExpandAllAdapter) treeViewProjects.getAdapter());
            if (adapter != null)
                adapter.ExpandAll(true);
        };
    }

    @Override
    public View.OnClickListener onClickToLegend()
    {
        return view -> ClickToLegend();
    }

    private void ClickToLegend()
    {
        new FragmentTasksProjectLegendPopup((AppCompatActivity) getContext()).PopupShow();
    }
    //endregion

    //region Subscription
    private void SubscriptionClick(GlobalClass globalVariable)
    {
        if (globalVariable.SubscrGoogle())
            SubscriptGoogle(globalVariable);

        if (globalVariable.SubscrRustore())
            SubscriptRustore(globalVariable);
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

            CustomTrack.PaidFormOpenFromTasksProject();

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

                    CustomTrack.PaidFormOpenFromTasksProject();

                    FragmentSubscriptionPopupNew popupNew = new FragmentSubscriptionPopupNew(
                            (AppCompatActivity) getContext(), null, EnumTypeAction.Insert,
                            _this,
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

    //region IFragmentSettings
    @Override
    public boolean ShowFormSettingButton() { return false; }

    @Override
    public View.OnClickListener onClickSettings() {
        return null;
    }
    //endregion

    //region IFragmentShowTargetAll
    @Override
    public boolean ShowTargetAll(Context context)
    {
        GlobalClass globalVar = (GlobalClass) context.getApplicationContext();

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        if (globalVar.get_taskProjectSettings() == null)
            return false;
        else
            return globalVar.get_taskProjectSettings().get_enumTargetStatus4Settings().
                    equals(EnumTargetStatus4Settings.Finished);
    }

    @Override
    public View.OnClickListener onClickToShowTargetAll()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_taskProjectSettings().set_enumTargetStatus4Settings(EnumTargetStatus4Settings.All);
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

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        if (globalVar.get_taskProjectSettings() == null)
            return false;
        else
            return globalVar.get_taskProjectSettings().get_enumTargetStatus4Settings().equals(EnumTargetStatus4Settings.InJob);
    }

    @Override
    public View.OnClickListener onClickToShowTargetFinished()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_taskProjectSettings().set_enumTargetStatus4Settings(EnumTargetStatus4Settings.Finished);
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

        if (globalVar.get_hasUnpaidSubscription())
            return false;

        if (globalVar.get_taskProjectSettings() == null)
            return false;
        else
            return globalVar.get_taskProjectSettings().get_enumTargetStatus4Settings().equals(EnumTargetStatus4Settings.All);
    }

    @Override
    public View.OnClickListener onClickToShowTargetInJob()
    {
        return view ->
        {
            GlobalClass globalVar = (GlobalClass) getContext().getApplicationContext();
            globalVar.get_taskProjectSettings().set_enumTargetStatus4Settings(EnumTargetStatus4Settings.InJob);
            SetAdapters();
            ((MainActivity) getActivity()).RefreshToolbarTopRightBase();
        };
    }
    //endregion
}