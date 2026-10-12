package com.MH83.LifeAsProject.adapter.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;
import com.MH83.LifeAsProject.view.Custom.CustomFragmentPopup;
import com.MH83.LifeAsProject.adapter.interfac.LifesProjectAdapter;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;

public abstract class CustomMoneyAdapter<TDataSet extends CustomMoneyObject, VH extends RecyclerView.ViewHolder,
        TPopupNew extends CustomPopupBottomFragment> extends RecyclerView.Adapter<VH>
        implements LifesProjectAdapter<TDataSet>
{
    protected Context _context;
    protected ArrayList<TDataSet> _dataSet;
    public ArrayList<TDataSet> get_dataSet() { return _dataSet; }
    public TDataSet getItem(int pos) { return _dataSet.get(pos); }
    protected final IFragmentViewCustom<TDataSet> _fragmentViewCustom;
    protected TDataSet _currentItem = null;
    protected Long _currentId1 = -1L;
    protected TPopupNew _fragmentPopupNew;
    private boolean _undoOnRight = false;
    private boolean _undoOnLeft = false;
    private float _startMoveRightX = 0; //откуда считаем X
    private float _startMoveLeftX = 0; //откуда считаем X
    private float _startMoveRightX2 = 0; //откуда считаем X2
    private float _startMoveLeftX2 = 0; //откуда считаем X2
    private boolean _fromRightUndoOn = false;
    private boolean _fromLeftUndoOn = false;
    private ImageView _imageViewRight = null;
    private ImageView _imageViewLeft = null;
    protected int _layoutId, _layoutRes = 0;

    public CustomMoneyAdapter(Context context, ArrayList<TDataSet> dataSet,
                              IFragmentViewCustom<TDataSet> fragmentViewCustom,
                              int layoutId)
    {
        _context = context;
        _fragmentViewCustom = fragmentViewCustom;
        _layoutId = layoutId;

        _dataSet = dataSet;
        if (_dataSet == null)
            _dataSet = new ArrayList<>();
    }

    public CustomMoneyAdapter(Context context, ArrayList<TDataSet> dataSet,
                              IFragmentViewCustom<TDataSet> fragmentViewCustom,
                              int layoutId, int layoutRes)
    {
        _context = context;
        _fragmentViewCustom = fragmentViewCustom;
        _layoutId = layoutId;
        _layoutRes = layoutRes;

        _dataSet = dataSet;
        if (_dataSet == null)
            _dataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { return null; }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position)
    {
        TDataSet item = _dataSet.get(position);
        ((CustomItemViewHolder<TDataSet>) holder).setObject(item);

        holder.itemView.setOnTouchListener((view, motionEvent) ->
                TouchListenerCustom(item, motionEvent));
    }

    protected boolean TouchListenerCustom(TDataSet item, MotionEvent motionEvent)
    {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            _fromRightUndoOn = false;
            _fromLeftUndoOn = false;
        }

        if (item != _currentItem && _undoOnRight)
        {
            _undoOnRight = false;
            _startMoveRightX = 0;
            _startMoveRightX2 = 0;
            notifyItemChanged(_dataSet.indexOf(_currentItem));
            if (_imageViewRight != null)
            {
                ViewGroup.LayoutParams lp = _imageViewRight.getLayoutParams();
                lp.width = 0;
                lp.height = 0;
                _imageViewRight.setLayoutParams(lp);
                _imageViewRight = null;
            }

            _currentItem = item;
        }

        if (item != _currentItem && _undoOnLeft)
        {
            _undoOnLeft = false;
            _startMoveLeftX = 0;
            _startMoveLeftX2 = 0;
            notifyItemChanged(_dataSet.indexOf(_currentItem));
            if (_imageViewLeft != null)
            {
                ViewGroup.LayoutParams lp = _imageViewLeft.getLayoutParams();
                lp.width = 0;
                lp.height = 0;
                _imageViewLeft.setLayoutParams(lp);
                _imageViewLeft = null;
            }

            _currentItem = item;
        }

        return false;
    }

    /*public abstract int AddItem(TDataSet dataSet);
    public abstract int UpdateItem(TDataSet dataSet);
    public abstract boolean DeleteFromDataset(TDataSet dataSet);*/
    @Override
    public int getItemCount() { return _dataSet.size(); }
    public void setCurrentItem(TDataSet object) { _currentItem = object; }
    public TDataSet getCurrentItem() { return _currentItem; }
    @Override
    public void onItemMove(int fromPosition, int toPosition) { }
    @Override
    public boolean canActionRightImage() { return false;}
    @Override
    public boolean canActionLeftImage() { return false;}
    public void setImageViewRight(ImageView imageViewRight) { _imageViewRight = imageViewRight; }
    public void setImageViewLeft(ImageView imageViewLeft) { _imageViewLeft = imageViewLeft; }
    @Override
    public boolean LeftButtonClick() { return false; }
    @Override
    public boolean RightButtonClick() { return false;}
    public ImageView getImageViewRight() { return _imageViewRight; }
    public ImageView getImageViewLeft() { return _imageViewLeft; }
    public void setUndoOnRight(boolean undoOnRight) { this._undoOnRight = undoOnRight; }
    public void setUndoOnLeft(boolean undoOnLeft) { this._undoOnLeft = undoOnLeft; }
    public boolean isUndoOnRight() { return _undoOnRight; }
    public boolean isUndoOnLeft() { return _undoOnLeft; }
    public boolean isFromRightUndoOn() { return _fromRightUndoOn; }
    public boolean isFromLeftUndoOn() { return _fromLeftUndoOn; }
    public void setFromRightUndoOn(boolean fromRightUndoOn) { _fromRightUndoOn = fromRightUndoOn; }
    public void setFromLeftUndoOn(boolean fromLeftUndoOn) { _fromLeftUndoOn = fromLeftUndoOn; }
    public void setStartMoveRightX(Float f) { _startMoveRightX = f; }
    public void setStartMoveLeftX(Float f) { _startMoveLeftX = f; }
    public void setStartMoveRightX2(Float f) { _startMoveRightX2 = f; }
    public void setStartMoveLeftX2(Float f) { _startMoveLeftX2 = f; }
    public float getStartMoveRightX() { return _startMoveRightX; }
    public float getStartMoveLeftX() { return _startMoveLeftX; }
    public float getStartMoveRightX2() { return _startMoveRightX2; }
    public float getStartMoveLeftX2() { return _startMoveLeftX2; }
}
