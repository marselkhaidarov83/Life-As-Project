package com.MH83.LifeAsProject.adapter.rView.money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.MH83.LifeAsProject.custom.CustomPeriodClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.Tasks.TasksProjectDataSource;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSettings;
import com.MH83.LifeAsProject.interfac.enums.EnumTaskSorting;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.model.Money.MoneyTarget;
import com.MH83.LifeAsProject.R;

import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.model.Money.MoneyTargetSettings;
import com.MH83.LifeAsProject.model.Tasks.TasksProject;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.MainActivity;
import com.MH83.LifeAsProject.view.Money.MoneyTarget.FragmentMoneyTargetPopupNew;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MoneyTargetAdapter
        extends CustomMoneyAdapter<MoneyTarget, MoneyTargetAdapter.ItemViewHolder,
        FragmentMoneyTargetPopupNew>
{
    private final int _heightDream;

    public MoneyTargetAdapter(Context context, ArrayList<MoneyTarget> dataSet,
                              IFragmentViewCustom<MoneyTarget> fragmentViewCustom,
                              int heightDream)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_money_target_new);
        _heightDream = heightDream;
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
        super.onBindViewHolder(holder, position);
        MoneyTarget _moneyTarget = _dataSet.get(position);

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = _moneyTarget;

            final GlobalClass globalVariable = (GlobalClass) _context.getApplicationContext();
            globalVariable.get_imagePath(true);
            _fragmentPopupNew = new FragmentMoneyTargetPopupNew((AppCompatActivity) _context,
                    _currentItem.CloneWithId(),
                    EnumTypeAction.Update, _fragmentViewCustom, _moneyTarget.get_dreamId());
            _fragmentPopupNew.PopupShow();
        });

        holder.MaterialCardViewStatus.setOnClickListener(view ->
        {
            TasksProject tasksProject = TasksProjectDataSource.LoadOneByTargetId(_context,
                    _moneyTarget.get_id());
            if (tasksProject != null)
                NavigateToTasks(tasksProject.get_id(), 6);
        });

        holder.MaterialCardViewWeekTasks.setOnClickListener(view ->
        {
            TasksProject tasksProject = TasksProjectDataSource.LoadOneByTargetId(_context,
                    _moneyTarget.get_id());
            if (tasksProject != null)
                NavigateToTasks(tasksProject.get_id(), 2);
        });

        holder.MaterialCardViewAllTasks.setOnClickListener(view ->
        {
            TasksProject tasksProject = TasksProjectDataSource.LoadOneByTargetId(_context,
                    _moneyTarget.get_id());
            if (tasksProject != null)
                NavigateToTasks(tasksProject.get_id(), 6);
        });
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
    public boolean canActionRightImage() { return false; }

    @Override
    public boolean canActionLeftImage() { return false; }

    public boolean LeftButtonClick()
    {
        return true;
    }

    public boolean RightButtonClick()
    {
        return DeleteCurrent();
    }

    public int AddItem(MoneyTarget moneyTarget)
    {
        GlobalClass globalVar = (GlobalClass) _context.getApplicationContext();

        ArrayList<EnumStatus> statusList;

        if (globalVar.get_moneyTargetDreamId() != null)
            statusList = globalVar.get_moneyTargetSettings().get_statusList();
        else
            statusList = MoneyTargetSettings.get_statusList(globalVar.get_moneyTargetEnumTargetStatus4Settings());

        if (statusList.size() > 0 && !statusList.contains(moneyTarget.get_status()))
            return -1;

        if (globalVar.get_moneyTargetYear() != null && !moneyTarget.getIyear().equals(globalVar.get_moneyTargetYear()))
            return -1;

        Integer month = moneyTarget.getImonth();
        Integer year = moneyTarget.getIyear();

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

        _dataSet.add(insertPosition, moneyTarget);
        notifyItemInserted(insertPosition);

        if (insertPosition < _dataSet.size() - 1)
            notifyItemChanged(insertPosition + 1);

        return insertPosition;
    }

    public int UpdateItem(MoneyTarget moneyTarget)
    {
        _fragmentPopupNew = null;
        long id = moneyTarget.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
        {
            if (id == _dataSet.get(i).get_id())
            {
                if (moneyTarget.get_month().equals(_dataSet.get(i).get_month()) &&
                        moneyTarget.get_year().equals(_dataSet.get(i).get_year()) &&
                        moneyTarget.get_status().equals(_dataSet.get(i).get_status()))
                {
                    _dataSet.set(i, moneyTarget);
                    notifyItemChanged(i);
                    if (i < _dataSet.size() - 1)
                        notifyItemChanged(i + 1);

                    return i;
                }
                else
                {
                    if (i > 0 && i < _dataSet.size() - 1)
                        notifyItemChanged(i + 1);

                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(moneyTarget);
                }
            }
        }

        return 0;
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
            else
                Toast.makeText(_context, _context.getString(R.string.errorDeletedMoneyTargetNotFound),
                        Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context, _context.getString(R.string.errorDeleteMoneyTarget) +
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(MoneyTarget moneyTargetDeleted)
    {
        File internalStorage = _context.getDir(_context.getString(R.string.picDirectoryTarget),
                Context.MODE_PRIVATE);
        File filePath = new File(internalStorage, moneyTargetDeleted.get_id() + ".jpeg");
        File file = new File(filePath.getPath());
        if (file.exists())
            if (!file.delete())
                Log.d("FileDeleting", "FileDeletingError");

        int index = -1;

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(_dataSet.get(i).get_id(), moneyTargetDeleted.get_id()))
            {
                index = i;
                break;
            }

        if (index == -1)
            return false;

        _dataSet.remove(index);
        notifyItemRemoved(index);

        return true;
    }

    public void onItemMove(int fromPosition, int toPosition) { }

    public void setPopupImage(Uri uri)
    {
        if (_fragmentPopupNew != null)
            _fragmentPopupNew.set_Image(uri);
    }

    public class ItemViewHolder extends CustomItemViewHolder<MoneyTarget>
    {
        private final TextView textViewDate;
        private final TextView textViewName;
        private final TextView textViewPrice;
        private final TextView textViewAccumulation;
        private final TextView textViewCredit;
        private final ImageView imageViewImage;
        private final ImageView imageViewStatus;
        private final TextView textViewStatus;
        private final MaterialCardView materialCardViewTarget;
        private final ProgressBar progressBarPrice;
        private final ProgressBar progressBarAccumulation;
        private final ProgressBar progressBarCredit;
        public final MaterialCardView MaterialCardViewStatus;
        public final MaterialCardView MaterialCardViewWeekTasks;
        public final MaterialCardView MaterialCardViewAllTasks;

        @SuppressLint("ResourceAsColor")
        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewAccumulation = itemView.findViewById(R.id.textViewAccumulation);
            textViewCredit = itemView.findViewById(R.id.textViewCredit);
            imageViewImage = itemView.findViewById(R.id.imageViewImage);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);
            materialCardViewTarget = itemView.findViewById(R.id.materialCardViewTarget);

            progressBarPrice = itemView.findViewById(R.id.progressBarPrice);
            progressBarAccumulation = itemView.findViewById(R.id.progressBarAccumulation);
            progressBarCredit = itemView.findViewById(R.id.progressBarCredit);

            MaterialCardViewStatus = itemView.findViewById(R.id.materialCardViewStatus);
            MaterialCardViewWeekTasks = itemView.findViewById(R.id.materialCardViewWeekTasks);
            MaterialCardViewAllTasks = itemView.findViewById(R.id.materialCardViewAllTasks);
        }

        public Long getId() { return _object.get_id(); }
        public void setName(String name) { textViewName.setText(name); }
        public void setDate(String month, String year)
        {
            CustomStaticClass.setDateView(_context, month, year, textViewDate);
        }
        @SuppressLint("SetTextI18n")
        public void setPrice(Integer price)
        {
            textViewPrice.setText(CustomStaticClass.getIntRubleSum(price, true));
            if (price == 0)
                progressBarPrice.setProgressBackgroundTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.grey)));
            else
                progressBarPrice.setProgressBackgroundTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.green)));
            progressBarPrice.setProgressTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.green)));
            progressBarPrice.setMax(price);
        }
        @SuppressLint("SetTextI18n")
        public void setAccumulation(Integer accumulation)
        {
            textViewAccumulation.setText(CustomStaticClass.getIntRubleSum(accumulation, true));
            if (accumulation == 0)
                progressBarAccumulation.setProgressBackgroundTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.grey)));
            else
                progressBarAccumulation.setProgressBackgroundTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.blue)));
            progressBarAccumulation.setProgressTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.green)));
            progressBarAccumulation.setMax(accumulation);
        }
        @SuppressLint("SetTextI18n")
        public void setCredit(int creditI, Long credit)
        {
            textViewCredit.setText(CustomStaticClass.getIntRubleSum(creditI, true));
            if (credit == 0L)
                progressBarCredit.setProgressBackgroundTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.grey)));
            else
                progressBarCredit.setProgressBackgroundTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.red)));
            progressBarCredit.setProgressTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(_context, R.color.green)));
            progressBarCredit.setMax(credit.intValue());
        }

        public void setImage(Long id)
        {
            File internalStorage = _context.getDir(
                    DBHelper.getDbName(_context) +
                    _context.getString(R.string.picDirectoryTarget),
                    Context.MODE_PRIVATE);
            File filePath = new File(internalStorage, id + ".jpeg");

            try
            {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getPath());
                imageViewImage.setImageBitmap(bitmap);
            }
            catch (Exception ex)
            {
                Log.i("DATABASE", "Problem reading picture", ex);
            }
        }
        private void setImageStatus(int statusColor)
        {
            imageViewStatus.setImageResource(statusColor);
        }
        @SuppressLint("SetTextI18n")
        private void setTextStatus(Integer allTaskCount, Integer weekTaskCount,
                                   EnumStatus status)
        {
            textViewStatus.setText(allTaskCount.toString());
            if (!status.equals(EnumStatus.Started))
            {
                MaterialCardViewAllTasks.setVisibility(View.INVISIBLE);
                MaterialCardViewWeekTasks.setVisibility(View.INVISIBLE);
                return;
            }

            if (allTaskCount == 0)
            {
                MaterialCardViewAllTasks.setVisibility(View.VISIBLE);
                MaterialCardViewWeekTasks.setVisibility(View.INVISIBLE);
            }
            else
            {
                MaterialCardViewAllTasks.setVisibility(View.INVISIBLE);
                MaterialCardViewWeekTasks.setVisibility(weekTaskCount == 0 ?
                        View.VISIBLE : View.INVISIBLE);
            }
        }
        private void setHeightTarget()
        {
            materialCardViewTarget.getLayoutParams().height = _heightDream;
        }
        private void setProgressBarPrice(Long progress)
        {
            progressBarPrice.setProgress(progress.intValue() / 100);
        }
        private void setProgressBarAccumulation(Long progress)
        {
            progressBarAccumulation.setProgress(progress.intValue() / 100);
        }
        private void setProgressBarCredit(Long progress)
        {
            progressBarCredit.setProgress(progress.intValue() / 100);
        }

        @Override
        public void setObject(MoneyTarget moneyTarget)
        {
            super.setObject(moneyTarget);

            setHeightTarget();
            setName(moneyTarget.get_name());
            setDate(moneyTarget.get_month(), moneyTarget.get_year());
            setPrice(moneyTarget.get_price());
            setAccumulation(moneyTarget.get_accumulation());
            setCredit(moneyTarget.get_credit(), moneyTarget.get_creditPlanSum() / 100);
            setImage(moneyTarget.get_id());
            setImageStatus(moneyTarget.get_statusColor());
            setTextStatus(moneyTarget.get_allTaskCount(),
                    moneyTarget.get_weekTaskCount(),
                    moneyTarget.get_status());

            setProgressBarPrice(moneyTarget.get_accumFactSum() + moneyTarget.get_creditPlanSum());
            setProgressBarAccumulation(moneyTarget.get_accumFactSum());
            setProgressBarCredit(moneyTarget.get_creditFactSum());
        }
    }
}
