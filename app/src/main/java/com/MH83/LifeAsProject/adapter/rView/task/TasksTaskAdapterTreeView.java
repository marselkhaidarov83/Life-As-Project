package com.MH83.LifeAsProject.adapter.rView.task;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.TreeViewAdapter;
import com.MH83.LifeAsProject.custom.CustomInsertPosition;
import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.CustomSubscription;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.custom.track.CustomTrack;
import com.MH83.LifeAsProject.custom.treeview.TreeItem;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.data.Tasks.TasksTaskDataSource;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.adapter.IAUDAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatType;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Custom.CBillingProduct;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskIndependent;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Main.Cabinet.FragmentSubscriptionPopupNew;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Tasks.TasksTasks.FragmentTasksTaskPopupNew;
import com.MH83.LifeAsProject.view.Tasks.TasksTasks.FragmentTasksTaskPopupTime;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.rustore.sdk.billingclient.RuStoreBillingClient;
import ru.rustore.sdk.billingclient.model.product.Product;
import ru.rustore.sdk.billingclient.model.product.ProductStatus;
import ru.rustore.sdk.billingclient.model.product.ProductType;
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase;
import ru.rustore.sdk.core.tasks.Task;

public class TasksTaskAdapterTreeView
        extends TreeViewAdapter<TasksTaskAdapterTreeView.ItemViewHolder, TasksTaskIndependent,
        FragmentTasksTaskPopupNew>
        implements IAUDAdapter<TasksTaskIndependent>
{
    private final Calendar _dateStart;
    private final Calendar _dateEnd;
    private final TasksTaskSettings _tasksTaskSettings;

    public TasksTaskAdapterTreeView(Context context,
                                    IFragmentViewCustom<TasksTaskIndependent> fragmentViewCustom,
                                    Calendar dateStart, Calendar dateEnd,
                                    TasksTaskSettings tasksTaskSettings,
                                    List<Integer> expandedId)
    {
        super(context, null, fragmentViewCustom, R.layout.item_tasks_task);

        _dateStart = dateStart;
        _dateEnd = dateEnd;
        _tasksTaskSettings = tasksTaskSettings;

        List<TasksTaskIndependent> dataSetParent = TasksTaskDataSource.LoadDataParent(context,
                dateStart, dateEnd, tasksTaskSettings, false);

        TreeItem<TasksTaskIndependent> root = new TreeItem<>(null, true);
        for (int i = 0; i < dataSetParent.size(); i++)
        {
            TreeItem<TasksTaskIndependent> folderCategory = new TreeItem<>(dataSetParent.get(i),
                    true);
            if (expandedId != null && expandedId.size() > 0)
                folderCategory.setExpanded(expandedId.contains(dataSetParent.get(i).get_id()));
            root.getChildren().add(folderCategory);

            List<TasksTaskIndependent> dataSet = dataSetParent.get(i).get_childs();

            for (int k = 0; k < dataSet.size(); k++)
                if (tasksTaskSettings.get_confirmedType() == null ||
                    tasksTaskSettings.get_confirmedType() == dataSet.get(k).get_confirmed())
            {
                TreeItem<TasksTaskIndependent> singleCategory = new TreeItem<>(dataSet.get(k), false);
                folderCategory.getChildren().add(singleCategory);
                (folderCategory.get_object()).set_hasChild(true);
            }
        }

        setRoot(root);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(View view)
    {
        return new ItemViewHolder(view, _context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.set_tasksTaskSettings(_tasksTaskSettings);
        super.onBindViewHolder(holder, position);
        TreeItem<TasksTaskIndependent> treeItem = _dataSet.get(position);
        TasksTaskIndependent tasksTask = treeItem.get_object();

        holder.imageViewConfirmed.setOnClickListener(view ->
        {
            if (treeItem.isExpanded())
            {
                int removedCount = removeAllExpandedItemRecursive(treeItem, holder.getLayoutPosition() + 1);
                notifyItemRangeRemoved(holder.getLayoutPosition() + 1, removedCount);
                treeItem.setExpanded(false);
            }
            TasksTaskIndependent tasksTaskClone = tasksTask.CloneWithInverseConfirmed();

            try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
            {
                if (tasksTaskClone.UpdateInDatabase(dbHelper, _context))
                    UpdateItem(tasksTaskClone);
                else
                    Toast.makeText(_context,
                            _context.getString(R.string.errorUpdateTasksTask),
                            Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(_context,
                        _context.getString(R.string.errorUpdateTasksTask) + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = treeItem;
            tasksTask.set_hasChild(_currentItem.getChildren().size() > 0);

            _fragmentPopupNew = new FragmentTasksTaskPopupNew((AppCompatActivity) _context,
                    tasksTask.get_type(), tasksTask,
                    EnumTypeAction.Update,
                    null,
                    _fragmentViewCustom,
                    null,
                    false, false, null);
            _fragmentPopupNew.PopupShow();
        });

        holder.textViewProjectName.setOnClickListener(view ->
                NavigateToTasks(tasksTask.get_project_id(), 6));
    }

    private void NavigateToTasks(Long tasksProjectId, int period)
    {
        final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
        globalVariable.set_enumTaskSettings(EnumTaskSettings.FromProject);
        globalVariable.set_tasksTaskSettingsHT(new TasksTaskSettings(
                new CustomPeriodClass(period, ""),
                false,
                null,
                tasksProjectId,
                0L,
                EnumTaskSorting.Date_Priority_Name,
                true));

        ((MainActivity) _fragmentViewCustom.getActivity()).NavigationItemSelected(R.id.navigation_tasks_tasks_fromproject);
    }

    @Override
    public boolean canActionRightImage()
    {
        return !_currentItem.get_object().isParent() && _currentItem.get_object().canDelete(_context);
    }

    @Override
    public boolean canActionLeftImage() { return true; }

    @Override
    public boolean LeftButtonClick()
    {
        FragmentTasksTaskPopupTime fragmentTasksTaskPopupTime = new FragmentTasksTaskPopupTime(
                (AppCompatActivity) _context,
                _currentItem.get_object(),
                _fragmentViewCustom);

        fragmentTasksTaskPopupTime.PopupShow();

        return true;
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

            CustomTrack.PaidFormOpenFromTasksTask();

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

                    CustomTrack.PaidFormOpenFromTasksTask();

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

    public Long AddItem(TasksTaskIndependent tasksTask)
    {
        return AddItem(tasksTask, tasksTask.isParent());
    }

    public Long AddItem(TasksTaskIndependent tasksTask, boolean childsVisible)
    {
        if (tasksTask.isParent())
        {
            if (_dateStart != null && _dateStart.getTime().getTime() > tasksTask.getPlanTime())
                return -1L;

            if (_dateEnd != null && _dateEnd.getTime().getTime() < tasksTask.getPlanTime())
                return -1L;

            if (_tasksTaskSettings.get_projectId() != null &&
                    !Objects.equals(_tasksTaskSettings.get_projectId(), tasksTask.get_project_id()))
                return -1L;

            if (_tasksTaskSettings.get_priority() != null && _tasksTaskSettings.get_priority() != 0 &&
                    !_tasksTaskSettings.get_priority().equals(tasksTask.get_priority()))
                return -1L;
        }

        if (_tasksTaskSettings.get_confirmedType() != null &&
                _tasksTaskSettings.get_confirmedType() != tasksTask.get_confirmed())
            return -1L;

        int insertPosition;

        if (tasksTask.isParent())
        {
            TreeItem<TasksTaskIndependent> folderCategory = new TreeItem<>(tasksTask, true);

            CustomInsertPosition customInsertPosition = tasksTask.GetPositionInDataset(_dataSet,
                    _tasksTaskSettings);
            insertPosition = addLevel2(folderCategory, customInsertPosition.get_insertPosition());

            for (TasksTaskIndependent taskChild : tasksTask.get_childs())
                AddItem(taskChild);

            if (tasksTask.get_nextTask() != null)
            {
                AddItem(tasksTask.get_nextTask());
                tasksTask.clear_nextTask();
            }
        }
        else
        {
            insertPosition = 0;

            for (int i = 0; i < _dataSet.size(); i++)
                if (Objects.equals((_dataSet.get(i).get_object()).get_id(), tasksTask.get_parentId()))
                {
                    _dataSet.get(i).get_object().set_hasChild(true);

                    if (childsVisible || _dataSet.get(i).isExpanded())
                    {
                        if (!_dataSet.get(i).isExpanded())
                        {
                            _dataSet.get(i).setExpanded(true);
                            notifyItemChanged(i);
                            insertAllExpandedItemRecursive(_dataSet.get(i), i + 1);
                            notifyItemRangeInserted(i + 1, _dataSet.get(i).getChildren().size());
                        }

                        int end = _dataSet.size();
                        for (int k = i + 1; k < _dataSet.size(); k++)
                            if (_dataSet.get(k).get_object().isParent())
                            {
                                end = k;
                                break;
                            }

                        CustomInsertPosition customInsertPosition = tasksTask.GetPositionInDataset(
                                _dataSet, _tasksTaskSettings, new CustomInsertPosition(end, i + 1, end),
                                true);

                        TreeItem<TasksTaskIndependent> singleCategory = new TreeItem<>(tasksTask, false);
                        _dataSet.get(i).get_object().get_childs().add(tasksTask);
                        notifyItemChanged(i);

                        int index = customInsertPosition.get_insertPosition() - i - 1;
                        insertPosition = addLevel3(singleCategory, tasksTask.get_parentId(), index,
                                childsVisible || _dataSet.get(i).isExpanded());
                    }
                    else
                    {
                        TreeItem<TasksTaskIndependent> singleCategory = new TreeItem<>(tasksTask, false);

                        CustomInsertPosition customInsertPosition = tasksTask.GetPositionInDataset(
                                _dataSet.get(i).getChildren(), _tasksTaskSettings,
                                new CustomInsertPosition(_dataSet.get(i).getChildren().size(),
                                        0, _dataSet.get(i).getChildren().size()), true);

                        _dataSet.get(i).get_object().get_childs().add(customInsertPosition.get_insertPosition(), tasksTask);

                        addLevel3(singleCategory, tasksTask.get_parentId(),
                                customInsertPosition.get_insertPosition(), false);

                        notifyItemChanged(i);
                        insertPosition = -1;
                    }

                    break;
                }
        }

        return (long) insertPosition;
    }

    public Long UpdateItem(TasksTaskIndependent tasksTaskNew)
    {
        for (int i = 0; i < _dataSet.size(); i++)
        {
            TasksTaskIndependent tasksTaskOld = _dataSet.get(i).get_object();
            if (Objects.equals(tasksTaskNew.get_id(), tasksTaskOld.get_id()))
            {
                if (Objects.equals(tasksTaskOld.get_parentId(), tasksTaskNew.get_parentId()) &&
                        (tasksTaskNew.getPlanTime() == tasksTaskOld.getPlanTime()) &&
                        (tasksTaskNew.get_name().equals(tasksTaskOld.get_name())) &&
                        (tasksTaskNew.get_priority().equals(tasksTaskOld.get_priority())) &&
                        (_tasksTaskSettings.get_projectId() == null ||
                                Objects.equals(tasksTaskNew.get_project_id(), _tasksTaskSettings.get_projectId())) &&
                        (_tasksTaskSettings.get_confirmedType() == null ||
                                tasksTaskNew.get_confirmed() == _tasksTaskSettings.get_confirmedType()) &&
                        (_tasksTaskSettings.get_priority() == null ||
                                _tasksTaskSettings.get_priority() == 0 ||
                                tasksTaskNew.get_priority().equals(_tasksTaskSettings.get_priority())))
                {
                    TasksTaskIndependent tasksTaskNewClone = tasksTaskNew.Clone();
                    tasksTaskNewClone.set_childs(tasksTaskOld.get_childs());
                    _dataSet.get(i).set_object(tasksTaskNewClone);
                    notifyItemChanged(i);

                    if (tasksTaskNew.hasParent())
                        for (int k = 0; k < _dataSet.size(); k++)
                            if (Objects.equals(tasksTaskNew.get_parentId(), _dataSet.get(k).get_object().get_id()))
                                _dataSet.get(k).get_object().update_child(tasksTaskNew);

                    ArrayList<TasksTaskIndependent> list = new ArrayList<>();
                    for (TasksTaskIndependent item : tasksTaskNew.get_childs())
                        list.add(item.getIndependentClone());

                    for (TasksTaskIndependent tasksTaskIndependentChild : list)
                    {
                        Long pos = UpdateItem(tasksTaskIndependentChild);
                        if (pos == -1)
                        {
                            for (TreeItem<TasksTaskIndependent> treeItem : _dataSet.get(i).getChildren())
                                if (Objects.equals(tasksTaskIndependentChild.get_id(), treeItem.get_object().get_id()))
                                {
                                    _dataSet.get(i).getChildren().remove(treeItem);
                                    break;
                                }

                            for (TasksTaskIndependent ttI: tasksTaskNewClone.get_childs())
                                if (Objects.equals(ttI.get_id(), tasksTaskIndependentChild.get_id()))
                                {
                                    tasksTaskNewClone.get_childs().remove(ttI);
                                    break;
                                }

                            AddItem(tasksTaskIndependentChild, false);
                        }
                    }

                    for (TasksTaskIndependent tasksTaskIndependent : tasksTaskNew.get_deletedChilds4View())
                        DeleteFromDataset(tasksTaskIndependent);
                    tasksTaskNew.clear_deletedChilds4View();

                    if (tasksTaskNew.get_nextTask() != null)
                    {
                        AddItem(tasksTaskNew.get_nextTask());
                        tasksTaskNew.clear_nextTask();
                    }

                    return (long) i;
                }
                else
                {
                    DeleteFromDataset(tasksTaskOld);

                    if (tasksTaskNew.get_nextTask() != null)
                    {
                        AddItem(tasksTaskNew.get_nextTask());
                        tasksTaskNew.clear_nextTask();
                    }

                    return AddItem(tasksTaskNew);
                }
            }
        }

        return -1L;
    }

    public boolean DeleteFromDataset(TreeItem<TasksTaskIndependent> tasksTaskTreeItem) throws Exception
    {
        return DeleteFromDataset(tasksTaskTreeItem.get_object());
    }

    public boolean DeleteFromDataset(TasksTaskIndependent tasksTaskDeleted)
    {
        for (int i = 0; i < _dataSet.size(); i++)
        {
            TasksTaskIndependent tasksTask = _dataSet.get(i).get_object();
            if (Objects.equals(tasksTask.get_id(), tasksTaskDeleted.get_id()))
            {
                _dataSet.remove(i);
                if (tasksTaskDeleted.hasParent())
                {
                    for (int k = i - 1; k >= 0; k--)
                    {
                        TasksTaskIndependent tasksTask2 = _dataSet.get(k).get_object();
                        if (Objects.equals(tasksTask2.get_id(), tasksTaskDeleted.get_parentId()))
                        {
                            _dataSet.get(k).get_object().delete_child(tasksTaskDeleted.get_id());
                            _dataSet.get(k).getChildren().remove(i - k - 1);
                            tasksTask2.set_hasChild(_dataSet.get(k).getChildren().size() > 0);
                            notifyItemChanged(k);
                            break;
                        }
                    }
                }
                notifyItemRemoved(i);

                for (TasksTaskIndependent tasksTaskIndependentChild : tasksTaskDeleted.get_childs())
                    DeleteFromDataset(tasksTaskIndependentChild);

                for (TasksTaskIndependent tasksTaskIndependentChild : tasksTaskDeleted.get_deletedChilds())
                    DeleteFromDataset(tasksTaskIndependentChild);

                return true;
            }
        }

        for (int i = 0; i < _dataSet.size(); i++)
        {
            TasksTaskIndependent tasksTask = _dataSet.get(i).get_object();
            if (Objects.equals(tasksTask.get_id(), tasksTaskDeleted.get_parentId()))
            {
                for (TreeItem<TasksTaskIndependent> treeItem: _dataSet.get(i).getChildren())
                    if (Objects.equals(treeItem.get_object().get_id(), tasksTaskDeleted.get_id()))
                    {
                        _dataSet.get(i).getChildren().remove(treeItem);
                        _dataSet.get(i).get_object().get_childs().removeIf(ttI -> Objects.equals(ttI.get_id(), tasksTaskDeleted.get_id()));
                        notifyItemChanged(i);
                        return true;
                    }
            }
        }

        return false;
    }

    public boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (dbHelper.DeleteTasksTask(_currentItem.get_object()))
                return DeleteFromDataset(_currentItem.get_object());
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedTasksTaskNotFound), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorDeleteTasksTask) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    public static class ItemViewHolder extends TreeViewAdapter.ItemViewHolder<TreeItem<TasksTaskIndependent>>
    {
        private TasksTaskSettings _tasksTaskSettings;
        private final ConstraintLayout constraintLayoutTask;
        private final ImageView imageViewConfirmed;
        private final TextView textViewDate;
        private final TextView textViewProjectName;
        private final TextView textViewName;
        public final TextView textViewComment;
        private final ImageView imageViewType;
        private final ImageView imageViewRepeat;
        private final TextView textViewPeriod;
        private final TextView textViewType;
        public void set_tasksTaskSettings(TasksTaskSettings tts) { _tasksTaskSettings = tts; }

        public ItemViewHolder(@androidx.annotation.NonNull View itemView, Context context)
        {
            super(itemView, context);
            constraintLayoutTask = itemView.findViewById(R.id.constraintLayoutTask);
            imageViewConfirmed = itemView.findViewById(R.id.imageViewConfirmed);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewProjectName = itemView.findViewById(R.id.textViewProjectName);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            imageViewType = itemView.findViewById(R.id.imageViewType);
            imageViewRepeat = itemView.findViewById(R.id.imageViewRepeat);
            textViewPeriod = itemView.findViewById(R.id.textViewPeriod);
            textViewType = itemView.findViewById(R.id.textViewType);
        }

        private void setConfirmed(boolean confirmed)
        {
            imageViewConfirmed.setImageResource(confirmed ? R.drawable.icons_is_fact_100 :
                    R.drawable.icons_is_plan_100);
        }

        private void setProjectName(String projectName)
        {
            textViewProjectName.setText(projectName);
        }

        private void setDate(long planTime,
                             Integer day, Integer month, Integer year, Integer hour, Integer minute,
                             String projectName)
        {
            if (textViewDate != null)
            {
                CustomStaticClass.setDateView(_context, day,
                        month, year, hour, minute, textViewDate);

                if (day == 0 && month == 0 && year == 0 && hour == 0 && minute == 0 &&
                        (projectName == null || projectName.equals("")))
                {
                    textViewDate.getLayoutParams().height = 0;
                    imageViewRepeat.getLayoutParams().height = 0;
                    textViewProjectName.getLayoutParams().height = 0;
                }
                else
                {
                    textViewDate.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageViewRepeat.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                    textViewProjectName.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }

                Calendar calendarToday = Calendar.getInstance();
                calendarToday.set(Calendar.HOUR, 0);
                calendarToday.set(Calendar.HOUR_OF_DAY, 0);
                calendarToday.set(Calendar.MINUTE, 0);
                calendarToday.set(Calendar.SECOND, 0);
                calendarToday.set(Calendar.MILLISECOND, 0);
                if (calendarToday.getTime().getTime() > planTime)
                {
                    textViewDate.setTextColor(_context.getColor(R.color.red));
                    textViewPeriod.setTextColor(_context.getColor(R.color.red));
                }
                else
                {
                    textViewDate.setTextColor(_context.getColor(R.color.black));
                    textViewPeriod.setTextColor(_context.getColor(R.color.black));
                }
            }
        }

        private void setName(String name)
        {
            if (textViewName != null)
                textViewName.setText(name);
        }

        private void setComment(String comment)
        {
            if (textViewComment != null)
            {
                String oldS = textViewComment.getText().toString();
                textViewComment.setText(comment);
                if (comment.equals("") && !oldS.equals(""))
                    textViewComment.getLayoutParams().height = 0;
                if (!comment.equals("") && oldS.equals(""))
                    textViewComment.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        }

        private void setType(Long priority)
        {
            switch (priority.intValue())
            {
                case 1:
                    imageViewType.setImageResource(R.drawable.icons_priority_high);
                    break;
                case 2:
                    imageViewType.setImageResource(R.drawable.icons_priority_middle);
                    break;
                case 3:
                    imageViewType.setImageResource(R.drawable.icons_priority_low);
                    break;
                default:
                    imageViewType.setImageResource(R.drawable.icons_priority_none);
            }
        }

        private void setRepeat(long planTime, boolean repeat)
        {
            Calendar calendarToday = Calendar.getInstance();
            calendarToday.set(Calendar.HOUR, 0);
            calendarToday.set(Calendar.HOUR_OF_DAY, 0);
            calendarToday.set(Calendar.MINUTE, 0);
            calendarToday.set(Calendar.SECOND, 0);
            calendarToday.set(Calendar.MILLISECOND, 0);

            if (repeat)
            {
                if (calendarToday.getTime().getTime() > planTime)
                    imageViewRepeat.setImageResource(R.drawable.icons_repeat_100_red);
                else
                    imageViewRepeat.setImageResource(R.drawable.icons_repeat_100_orange);

                imageViewRepeat.setVisibility(View.VISIBLE);
            }
            else
                imageViewRepeat.setVisibility(View.INVISIBLE);
        }

        private void set_isParent(boolean isParent)
        {
            if (isParent)
                constraintLayoutTask.setBackgroundResource(R.color.white);
            else
                constraintLayoutTask.setBackgroundResource(R.color.lightLightGrey);
        }

        private void set_period(boolean repeat, int repeatPer, EnumRepeatType repeatType)
        {
            if (!repeat || repeatType == null)
            {
                textViewPeriod.setText("");
                return;
            }

            String s = String.valueOf(repeatPer);
            switch (repeatType)
            {
                case Day:
                    s += _context.getResources().getString(R.string.RepeatTypeDayShort);
                    break;
                case Week:
                    s += _context.getResources().getString(R.string.RepeatTypeWeekShort);
                    break;
                case Month:
                case MonthEnd:
                    s += _context.getResources().getString(R.string.RepeatTypeMonthShort);
                    break;
                case Year:
                case YearEnd:
                    s += _context.getResources().getString(R.string.RepeatTypeYearShort);
                    break;
                default:
            }

            textViewPeriod.setText(s);
        }

        private void set_countTask(int countTask)
        {
            if (countTask == 0)
                textViewType.setText("");
            else
                textViewType.setText(String.valueOf(countTask));
        }

        @Override
        public void setObject(TreeItem<TasksTaskIndependent> treeItem)
        {
            super.setObject(treeItem);

            TasksTaskIndependent tasksTask = treeItem.get_object();
            setConfirmed(tasksTask.get_confirmed());
            setProjectName(tasksTask.get_projectName());
            setDate(tasksTask.getPlanTime(),
                    tasksTask.get_day_plan(), tasksTask.get_month_plan(), tasksTask.get_year_plan(),
                    tasksTask.get_hour_plan(), tasksTask.get_minute_plan(), tasksTask.get_projectName());
            setName(tasksTask.get_name());
            setComment(tasksTask.get_comment());
            setType(tasksTask.get_priority());
            setRepeat(tasksTask.getPlanTime(), tasksTask.get_repeat());
            set_isParent(tasksTask.isParent());
            set_period(tasksTask.get_repeat(), tasksTask.get_repeatPer(), tasksTask.get_repeatType());
            set_countTask(tasksTask.getCountChild(_tasksTaskSettings));
        }
    }
}
