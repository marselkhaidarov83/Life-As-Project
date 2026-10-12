package com.MH83.LifeAsProject.adapter.rView.task;

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
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Tasks.TasksProjectDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Tasks.TaskProjectSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Tasks.TasksProject.FragmentTasksProjectPopupNew;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.material.card.MaterialCardView;

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

public class TasksProjectAdapter
        extends TreeViewAdapter<TasksProjectAdapter.ItemViewHolder, TasksProject,
                                FragmentTasksProjectPopupNew>
        implements IAUDAdapter<TasksProject>
{
    public TasksProjectAdapter(Context context, IFragmentViewCustom<TasksProject> fragmentViewCustom)
    {
        super(context, null, fragmentViewCustom, R.layout.item_tree_tasks_project_new);

        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        TaskProjectSettings taskProjectSettings = globalVariable.get_taskProjectSettings();

        List<TasksProject> dataSetParent = TasksProjectDataSource.LoadDataParent(context,
                false, taskProjectSettings);

        ArrayList<Long> expandedId = null;
        if (taskProjectSettings != null)
            expandedId = taskProjectSettings.get_expandedList();

        TreeItem<TasksProject> root = new TreeItem<>(null, true);
        if (dataSetParent != null)
            for (int i = 0; i < dataSetParent.size(); i++)
            {
                TreeItem<TasksProject> folderCategory = new TreeItem<>(
                        dataSetParent.get(i), true);
                if (expandedId != null && expandedId.size() > 0)
                    folderCategory.setExpanded(expandedId.contains(dataSetParent.get(i).get_id()));
                root.getChildren().add(folderCategory);

                List<TasksProject> dataSet2 = TasksProjectDataSource.LoadDataChildren(context,
                        dataSetParent.get(i).get_id(), taskProjectSettings);

                dataSetParent.get(i).set_taskCountHigh(0);
                dataSetParent.get(i).set_taskCountMiddle(0);
                dataSetParent.get(i).set_taskCountLow(0);
                dataSetParent.get(i).set_taskCountNone(0);
                dataSetParent.get(i).set_projectCount(dataSet2.size());
                for (int k = 0; k < dataSet2.size(); k++)
                {
                    dataSetParent.get(i).inc_taskCountHigh(dataSet2.get(k).get_taskCountHigh());
                    dataSetParent.get(i).inc_taskCountMiddle(dataSet2.get(k).get_taskCountMiddle());
                    dataSetParent.get(i).inc_taskCountLow(dataSet2.get(k).get_taskCountLow());
                    dataSetParent.get(i).inc_taskCountNone(dataSet2.get(k).get_taskCountNone());

                    TreeItem<TasksProject> singleCategory2 = new TreeItem<>(
                            dataSet2.get(k), false);
                    folderCategory.getChildren().add(singleCategory2);
                    (folderCategory.get_object()).set_hasChild(true);

                    List<TasksProject> dataSet3 = TasksProjectDataSource.LoadDataChildren(context,
                            dataSet2.get(k).get_id(), taskProjectSettings);
                    for (int j = 0; j < dataSet3.size(); j++)
                    {
                        singleCategory2.setExpandable(true);

                        TreeItem<TasksProject> singleCategory3 = new TreeItem<>(
                                dataSet3.get(j), false);
                        singleCategory2.getChildren().add(singleCategory3);
                        (singleCategory2.get_object()).set_hasChild(true);
                    }
                }
            }

        _setPadding = true;
        setRoot(root);
    }

    public void SaveExpandedList()
    {
        ArrayList<Long> list = new ArrayList<>();

        for (TreeItem<TasksProject> tasksProjectTreeItem : _dataSet)
            if (tasksProjectTreeItem.get_object().isParent() && tasksProjectTreeItem.isExpanded())
                list.add(tasksProjectTreeItem.get_object().get_id());

        GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        globalVariable.get_taskProjectSettings().set_expandedList(list);
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
        TasksProject tasksProject = _dataSet.get(position).get_object();

        holder.itemView.setOnClickListener(view ->
        {
            tasksProject.set_hasChild(_dataSet.get(position).getChildren().size() > 0);
            _currentItem = _dataSet.get(position);

            new FragmentTasksProjectPopupNew((AppCompatActivity) _context,
                    tasksProject, EnumTypeAction.Update, _fragmentViewCustom).PopupShow();
        });

        holder.MaterialCardViewProjectCount.setOnClickListener(view ->
        {
            _currentItem = _dataSet.get(position);
            ClickToLeadingIcon(_currentItem, holder);
        });

        holder.MaterialCardViewTaskCountAll.setOnClickListener(view ->
                NavigateToTasks(_dataSet.get(position), 0L));

        holder.MaterialCardViewTaskCountHigh.setOnClickListener(view ->
                NavigateToTasks(_dataSet.get(position), 1L));

        holder.MaterialCardViewTaskCountMiddle.setOnClickListener(view ->
                NavigateToTasks(_dataSet.get(position), 2L));

        holder.MaterialCardViewTaskCountLow.setOnClickListener(view ->
                NavigateToTasks(_dataSet.get(position), 3L));

        holder.MaterialCardViewTaskCountNone.setOnClickListener(view ->
                NavigateToTasks(_dataSet.get(position), 4L));
    }

    private void NavigateToTasks(TreeItem<TasksProject> item, Long priority)
    {
        _currentItem = item;

        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        globalVariable.set_enumTaskSettings(EnumTaskSettings.FromProject);
        globalVariable.set_tasksTaskSettingsHT(new TasksTaskSettings(
                new CustomPeriodClass(6, ""),
                false,
                _currentItem.get_object().get_parentId() == 0 ?
                        _currentItem.get_object().get_id() : null,
                _currentItem.get_object().get_parentId() != 0 ?
                        _currentItem.get_object().get_id() : null,
                priority, EnumTaskSorting.Date_Priority_Name, true));

        ((MainActivity) _fragmentViewCustom.getActivity()).NavigationItemSelected(R.id.navigation_tasks_tasks_fromproject);
    }

    @Override
    protected void ClickToLeadingIcon(TreeItem<TasksProject> item, @androidx.annotation.NonNull TasksProjectAdapter.ItemViewHolder holder)
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
    public boolean LeftButtonClick()
    {
        NavigateToTasks(_currentItem, 0L);

//        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
//        globalVariable.set_tasksTaskSettingsFrom(new TasksTaskSettings(
//                new CustomPeriodClass(6, ""),
//                false,
//                _currentItem.get_object().get_parentId() == 0 ?
//                        _currentItem.get_object().get_id() : null,
//                _currentItem.get_object().get_parentId() != 0 ?
//                        _currentItem.get_object().get_id() : null,
//                0, EnumTaskSorting.Date_Priority_Name));
//
//        ((MainActivity) _fragmentViewCustom.getActivity()).NavigationItemSelected(R.id.navigation_tasks_tasks);

//        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
//        if (!TasksProject.canAdd(_context, globalVariable.get_hasSubscription()))
//        {
//            SubscriptionClick(globalVariable);
//            return false;
//        }
//
//        notifyItemChanged(_dataSet.indexOf(_currentItem));
//        TasksProject tasksProject = _currentItem.get_object().Clone();
//
//        _fragmentPopupNew = new FragmentTasksProjectPopupNew((AppCompatActivity) _context,
//                tasksProject, EnumTypeAction.Insert, _fragmentViewCustom);
//        _fragmentPopupNew.PopupShow();
//
//        _currentItem = null;
        return true;
    }

    //region Subscript
    protected void SubscriptionClick(GlobalClass globalVariable)
    {
        if (globalVariable.SubscrGoogle())
            SubscriptGoogle(globalVariable);

        if (globalVariable.SubscrRustore())
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
                    if (globalVariable.TestPaid() || product.getProductType() == ProductType.SUBSCRIPTION)
                        cBillingProducts.add(new CBillingProduct(product.getProductId(),
                                product.getTitle(), product.getDescription(), product.getPriceLabel()));

            CustomTrack.PaidFormOpenFromTasksProject();

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
                        if (globalVariable.TestPaid() ||
                                productDetail.getProductType().equals(BillingClient.ProductType.SUBS))
                            cBillingProducts.add(new CBillingProduct(productDetail.getProductId(),
                                    productDetail.getName(), "",
                                    productDetail.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice(),
                                    productDetail));
                    }

                    CustomTrack.PaidFormOpenFromTasksProject();

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

    public Long AddItem(TasksProject tasksProject)
    {
        int insertPosition = 0;

        if (tasksProject.get_parentId() == 0)
        {
            TreeItem<TasksProject> folderCategory = new TreeItem<>(tasksProject, true);
            insertPosition = addLevel2(folderCategory);
        }
        else
        {
            for (int i = 0; i < _dataSet.size(); i++)
                if ((_dataSet.get(i).get_object()).get_id() == tasksProject.get_parentId())
                {
                    _dataSet.get(i).get_object().set_hasChild(true);

                    if (!_dataSet.get(i).isExpanded())
                    {
                        _dataSet.get(i).setExpanded(true);
                        notifyItemChanged(i);
                        insertAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                        notifyItemRangeInserted(i + 1, _dataSet.get(i).getChildren().size());
                    }

                    TreeItem<TasksProject> singleCategory = new TreeItem<>(tasksProject,false);
                    insertPosition = addLevel3(singleCategory, tasksProject.get_parentId());
                    break;
                }
        }

        return (long) insertPosition;
    }

    public Long UpdateItem(TasksProject tasksProjectNew)
    {
        for (int i = 0; i < _dataSet.size(); i++)
        {
            TasksProject tasksProjectOld = _dataSet.get(i).get_object();
            if (tasksProjectNew.get_id() == tasksProjectOld.get_id())
            {
                if (tasksProjectOld.get_parentId() == tasksProjectNew.get_parentId())
                {
                    _dataSet.get(i).set_object(tasksProjectNew);
                    notifyItemChanged(i);
                }
                else
                {
                    try { DeleteFromDataset(tasksProjectOld); }
                    catch (Exception e) {}
                    AddItem(tasksProjectNew);
                }
                break;
            }
        }

        return tasksProjectNew.get_id();
    }

    public boolean DeleteFromDataset(TreeItem<TasksProject> tasksProjectTreeItem) throws Exception
    {
        return DeleteFromDataset(tasksProjectTreeItem.get_object());
    }

    public boolean DeleteFromDataset(TasksProject tasksProjectDeleted)
    {
        for (int i = 0; i < _dataSet.size(); i++)
        {
            TasksProject tasksProject = _dataSet.get(i).get_object();
            if (tasksProject.get_id() == tasksProjectDeleted.get_id())
            {
                _dataSet.remove(i);
                if (tasksProjectDeleted.hasParent())
                {
                    for (int k = i - 1; k >= 0; k--)
                    {
                        TasksProject tp2 = _dataSet.get(k).get_object();
                        if (tp2.get_id() == tasksProjectDeleted.get_parentId())
                        {
                            _dataSet.get(k).getChildren().remove(i - k - 1);
                            tp2.set_hasChild(_dataSet.get(k).getChildren().size() > 0);
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
            if (dbHelper.DeleteTasksProject(_currentItem.get_object().get_id()))
                return DeleteFromDataset(_currentItem.get_object());
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedTasksProjectNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorDeleteTasksProject) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        Long updateId = _dataSet.get(fromPosition).get_object().get_id();

        if (_dataSet.get(fromPosition).get_object().isChild())
        //Изменения для 3-его уровня
        {
            int oldParentIndex = 0;
            TreeItem<TasksProject> oldParent = null;
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
                                    _dataSet.get(k).get_object().get_id() != updateId)
                                {
                                    _dataSet.get(k).get_object().set_position(_dataSet.get(k).get_object().get_position() + 1);
                                    endForUpdate = k;
                                }

                            oldParent.getChildren().remove(0);
                            oldParent = null;
                        }
                        else
                        {
                            TreeItem<TasksProject> newParent = null;
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
                        if (_dataSet.get(i).get_object().get_id() == updateId)
                        {
                            if (!dbHelper.UpdateTasksProject(_dataSet.get(i).get_object()))
                                Toast.makeText(_context, _context.getString(R.string.errorUpdateTasksProject),
                                        Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (!dbHelper.UpdateTasksProjectPosition(_dataSet.get(i).get_object().get_id(),
                                    _dataSet.get(i).get_object().get_position()))
                                Toast.makeText(_context, _context.getString(R.string.errorUpdateTasksProjectPosition),
                                        Toast.LENGTH_SHORT).show();
                        }
                    }
            }
            catch (Exception e)
            {
                Toast.makeText(_context, _context.getString(R.string.errorUpdateTasksProjectPosition) +
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
                    if (!dbHelper.UpdateTasksProjectPosition(_dataSet.get(i).get_object().get_id(),
                            _dataSet.get(i).get_object().get_position()))
                        Toast.makeText(_context, _context.getString(R.string.errorUpdateTasksProjectPosition),
                                Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(_context, _context.getString(R.string.errorUpdateTasksProjectPosition) +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            notifyItemMoved(fromPosition, toPosition);
            for (int i = start; i <= end; i++)
                notifyItemChanged(i);
        }
    }

    @Override
    protected int getLeadingIcon4Child(@androidx.annotation.NonNull ItemViewHolder holder)
    {
        switch (holder.getObject().get_object().get_status())
        {
            case Blank:
                return holder.getObject().get_object().isTarget() ?
                        R.drawable.icons_circle_target_white_24 : R.drawable.icons_circle_white_24;
            case Started:
                return holder.getObject().get_object().isTarget() ?
                        R.drawable.icons_circle_target_orange_24 : R.drawable.icons_circle_orange_24;
            case Finished:
                return holder.getObject().get_object().isTarget() ?
                        R.drawable.icons_circle_target_green_24 : R.drawable.icons_circle_green_24;
            case Irrelevant:
                return holder.getObject().get_object().isTarget() ?
                        R.drawable.icons_circle_target_lightgrey_24 : R.drawable.icons_circle_lightgrey24;
            default:
                return R.drawable.icons_circle_24;
        }
    }

    public static class ItemViewHolder extends TreeViewAdapter.ItemViewHolder<TreeItem<TasksProject>>
    {
        private final TextView _textViewName;
        private final TextView _textViewProjectCount;
        private final TextView _textViewTaskCountAll;
        private final TextView _textViewTaskCountHigh;
        private final TextView _textViewTaskCountMiddle;
        private final TextView _textViewTaskCountLow;
        private final TextView _textViewTaskCountNone;
        public final MaterialCardView MaterialCardViewProjectCount;
        public final MaterialCardView MaterialCardViewTaskCountAll;
        public final MaterialCardView MaterialCardViewTaskCountHigh;
        public final MaterialCardView MaterialCardViewTaskCountMiddle;
        public final MaterialCardView MaterialCardViewTaskCountLow;
        public final MaterialCardView MaterialCardViewTaskCountNone;

        private void setName(String name, boolean isParent)
        {
            _textViewName.setText(name);
            if (isParent)
                _textViewName.setTypeface(null, Typeface.BOLD);
            else
                _textViewName.setTypeface(null, Typeface.NORMAL);
        }
        private void setProjectCount(int count, boolean isParent)
        {
            if (isParent)
                _textViewProjectCount.setText(String.valueOf(count));
            else
                MaterialCardViewProjectCount.setVisibility(View.INVISIBLE);
        }

        private void setTaskCountAll(int count)
        {
            _textViewTaskCountAll.setText(String.valueOf(count));
        }
        private void setTaskCountHigh(int count)
        {
            _textViewTaskCountHigh.setText(String.valueOf(count));
        }
        private void setTaskCountMiddle(int count)
        {
            _textViewTaskCountMiddle.setText(String.valueOf(count));
        }
        private void setTaskCountLow(int count)
        {
            _textViewTaskCountLow.setText(String.valueOf(count));
        }
        private void setTaskCountNone(int count)
        {
            _textViewTaskCountNone.setText(String.valueOf(count));
        }

        public ItemViewHolder(@androidx.annotation.NonNull View itemView, Context context)
        {
            super(itemView, context);
            _textViewName = itemView.findViewById(R.id.textViewName);
            _textViewProjectCount = itemView.findViewById(R.id.textViewProjectCount);
            _textViewTaskCountAll = itemView.findViewById(R.id.textViewTaskCountAll);
            _textViewTaskCountHigh = itemView.findViewById(R.id.textViewTaskCountHigh);
            _textViewTaskCountMiddle = itemView.findViewById(R.id.textViewTaskCountMiddle);
            _textViewTaskCountLow = itemView.findViewById(R.id.textViewTaskCountLow);
            _textViewTaskCountNone = itemView.findViewById(R.id.textViewTaskCountNone);
            MaterialCardViewProjectCount = itemView.findViewById(R.id.materialCardViewProjectCount);
            MaterialCardViewTaskCountAll = itemView.findViewById(R.id.materialCardViewTaskCountAll);
            MaterialCardViewTaskCountHigh = itemView.findViewById(R.id.materialCardViewTaskCountHigh);
            MaterialCardViewTaskCountMiddle = itemView.findViewById(R.id.materialCardViewTaskCountMiddle);
            MaterialCardViewTaskCountLow = itemView.findViewById(R.id.materialCardViewTaskCountLow);
            MaterialCardViewTaskCountNone = itemView.findViewById(R.id.materialCardViewTaskCountNone);
        }

        @Override
        public void setObject(TreeItem<TasksProject> tasksProjectTreeItem)
        {
            super.setObject(tasksProjectTreeItem);

            TasksProject tasksProject = tasksProjectTreeItem.get_object();
            setName(tasksProject.getSValue(), tasksProject.isParent());
            setProjectCount(tasksProject.get_projectCount(), tasksProject.isParent());
            setTaskCountAll(tasksProject.get_taskCountAll());
            setTaskCountHigh(tasksProject.get_taskCountHigh());
            setTaskCountMiddle(tasksProject.get_taskCountMiddle());
            setTaskCountLow(tasksProject.get_taskCountLow());
            setTaskCountNone(tasksProject.get_taskCountNone());
        }
    }
}
