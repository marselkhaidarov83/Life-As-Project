package com.MH83.LifeAsProject.adapter.rView.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.custom.CustomItemViewHolder;
import com.MH83.LifeAsProject.adapter.custom.CustomMoneyAdapter;
import com.MH83.LifeAsProject.custom.CustomInsertPosition;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumRepeatType;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeTask;
import com.MH83.LifeAsProject.model.Tasks.TasksTask;
import com.MH83.LifeAsProject.model.Tasks.TasksTaskSettings;
import com.MH83.LifeAsProject.view.Tasks.TasksTasks.FragmentTasksTaskPopupNew;
import com.MH83.LifeAsProject.view.Tasks.TasksTasks.FragmentTasksTaskPopupTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class TasksTaskAdapter
        extends CustomMoneyAdapter<TasksTask, TasksTaskAdapter.ItemViewHolder,
        FragmentTasksTaskPopupNew>
{
    private final Calendar _dateStart;
    private final Calendar _dateEnd;
    private final TasksTaskSettings _tasksTaskSettings;
    private final EnumTypeAction _typeAction;
    private final IFragmentViewCustom _fragmentViewCustomParent;

    public TasksTaskAdapter(Context context, ArrayList<TasksTask> dataSet,
                            EnumTypeAction typeAction, IFragmentViewCustom fragmentViewCustomParent,
                            IFragmentViewCustom fragmentViewCustom,
                            Calendar dateStart, Calendar dateEnd, TasksTaskSettings tasksTaskSettings)
    {
        super(context, dataSet, fragmentViewCustom, R.layout.item_tasks_task);

        _typeAction = typeAction;
        _fragmentViewCustomParent = fragmentViewCustomParent;

        _dateStart = dateStart;
        _dateEnd = dateEnd;
        _tasksTaskSettings = tasksTaskSettings;
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
        TasksTask tasksTask = _dataSet.get(position);

        holder.imageViewConfirmed.setOnClickListener(view ->
        {
            TasksTask tasksTaskClone = tasksTask.CloneWithInverseConfirmed();
            UpdateItem(tasksTaskClone);
            _fragmentViewCustom.refreshViewOnUpdate(tasksTaskClone);
        });

        holder.itemView.setOnClickListener(view ->
        {
            _currentItem = tasksTask;

            new FragmentTasksTaskPopupNew(
                    (AppCompatActivity) _context,
                    tasksTask.get_type(),
                    tasksTask,
                    _typeAction,
                    EnumTypeAction.Update,
                    _fragmentViewCustomParent,
                    _fragmentViewCustom,
                    false,
                    true,
                    null).PopupShow();
        });
    }

    @Override
    public boolean canActionLeftImage()
    {
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
        FragmentTasksTaskPopupTime fragmentTasksTaskPopupTime = new FragmentTasksTaskPopupTime(
                (AppCompatActivity) _context,
                _currentItem,
                _fragmentViewCustom);

        fragmentTasksTaskPopupTime.PopupShow();

        return true;
    }

    public boolean RightButtonClick()
    {
        if (_currentItem == null)
            return false;

        return DeleteCurrent();
    }

    public int AddItem(TasksTask tasksTask)
    {
        CustomInsertPosition customInsertPosition = tasksTask.GetPositionInDataset(_dataSet,
                _tasksTaskSettings);

//        int insertPosition = _dataSet.size();
//
//        for (int i = 0; i < _dataSet.size(); i++)
//            if (tasksTask.getPlanTime() < _dataSet.get(i).getPlanTime())
//            {
//                insertPosition = i;
//                break;
//            }

        int insertPosition = customInsertPosition.get_insertPosition();
        _dataSet.add(insertPosition, tasksTask);
        notifyItemInserted(insertPosition);

//        if (tasksTask.get_nextTask() != null)
//        {
//            AddItem(tasksTask.get_nextTask());
//            tasksTask.clear_nextTask();
//        }

        return insertPosition;
    }

    public int UpdateItem(TasksTask tasksTask)
    {
        Long id = tasksTask.get_id();

        for (int i = 0; i < _dataSet.size(); i++)
            if (Objects.equals(id, _dataSet.get(i).get_id()))
            {
                if ((tasksTask.getPlanTime() == _dataSet.get(i).getPlanTime()) &&
                        (tasksTask.get_name().equals(_dataSet.get(i).get_name())) &&
                        (tasksTask.get_priority().equals(_dataSet.get(i).get_priority())) &&
                        (tasksTask.get_confirmed() == _dataSet.get(i).get_confirmed()))
                {
                    _dataSet.set(i, tasksTask);

                    notifyItemChanged(i);

//                    if (tasksTask.get_nextTask() != null)
//                    {
//                        AddItem(tasksTask.get_nextTask());
//                        tasksTask.clear_nextTask();
//                    }

                    return i;
                }
                else
                {
                    _dataSet.remove(i);
                    notifyItemRemoved(i);
                    return AddItem(tasksTask);
                }
            }

        return 0;
    }

    private boolean DeleteCurrent()
    {
        try (DBHelper dbHelper = DBHelper.GetDBHelper(_context))
        {
            if (_currentItem.DeleteFromDatabase(dbHelper, _context))
                return DeleteFromDataset(_currentItem);
            else
                Toast.makeText(_context,
                    _context.getString(R.string.errorDeletedTasksTaskNotFound),
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(_context,
                    _context.getString(R.string.errorDeleteTasksTask) + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean DeleteFromDataset(TasksTask tasksTask)
    {
        int index = -1;
        for (int i = 0; i < _dataSet.size(); i++)
            if (_dataSet.get(i).get_id() == tasksTask.get_id())
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

    @Override
    public void onItemMove(int fromPosition, int toPosition) { }

    public static class ItemViewHolder extends CustomItemViewHolder<TasksTask>
    {
        private final ImageView imageViewConfirmed;
        private final TextView textViewDate;
        private final TextView textViewProjectName;
        private final TextView textViewName;
        public final TextView textViewComment;
        private final ImageView imageViewType;
        private final ImageView imageViewRepeat;
        private final TextView textViewPeriod;
        private final TextView textViewType;

        public ItemViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView, context);
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

        private void setDate(Integer day, Integer month, Integer year, Integer hour, Integer minute,
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

        private void setType(EnumTypeTask type, Long priority)
        {
            if (priority == 1L)
                imageViewType.setImageResource(R.drawable.icons_priority_high);
            else if (priority == 2L)
                imageViewType.setImageResource(R.drawable.icons_priority_middle);
            else if (priority == 3L)
                imageViewType.setImageResource(R.drawable.icons_priority_low);
            else
                imageViewType.setImageResource(R.drawable.icons_priority_none);
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
        public void setObject(TasksTask tasksTask)
        {
            super.setObject(tasksTask);

            setConfirmed(tasksTask.get_confirmed());
            setProjectName(tasksTask.get_projectName());
            setDate(tasksTask.get_day_plan(), tasksTask.get_month_plan(), tasksTask.get_year_plan(),
                    tasksTask.get_hour_plan(), tasksTask.get_minute_plan(), tasksTask.get_projectName());
            setName(tasksTask.get_name());
            setComment(tasksTask.get_comment());
            setType(tasksTask.get_type(), tasksTask.get_priority());
            setRepeat(tasksTask.getPlanTime(), tasksTask.get_repeat());
            set_period(tasksTask.get_repeat(), tasksTask.get_repeatPer(), tasksTask.get_repeatType());
            set_countTask(tasksTask.get_childs().size());
        }
    }
}
