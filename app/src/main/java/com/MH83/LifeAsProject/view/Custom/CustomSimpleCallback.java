package com.MH83.LifeAsProject.view.Custom;

import static java.lang.Math.abs;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.adapter.interfac.LifesItemViewHolder;
import com.MH83.LifeAsProject.adapter.interfac.LifesProjectAdapter;
import com.MH83.LifeAsProject.interfac.enums.EnumRecycleViewParentLayout;

public class CustomSimpleCallback extends SimpleCallback
{
    protected RecyclerView _rView;
    protected LifesProjectAdapter<Object> _adapter;
    private final int _basedBorderLineRight; // точка принятия решения
    private final int _basedBorderLineLeft; // точка принятия решения
    private final boolean _showRightImage;
    private final boolean _showLeftImage;
    private final int _leftImage;
    private final int _rightImage;
    private final EnumRecycleViewParentLayout _recycleViewParentLayout;

    public CustomSimpleCallback(int dragDirs, int swipeDirs, RecyclerView rView,
                                @NonNull Integer basedBorderLineLeftDp,
                                EnumRecycleViewParentLayout recycleViewParentLayout,
                                boolean showLeftImage, boolean showRightImage,
                                int leftImage, int rightImage)
    {
        super(dragDirs, swipeDirs);
        _rView = rView;
        _adapter = (LifesProjectAdapter<Object>) _rView.getAdapter();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) rView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        _basedBorderLineRight = (int) (-basedBorderLineLeftDp * displaymetrics.scaledDensity);
        _basedBorderLineLeft = (int) (basedBorderLineLeftDp * displaymetrics.scaledDensity);

        _showLeftImage = showLeftImage;
        _showRightImage = showRightImage;
        _leftImage = leftImage;
        _rightImage = rightImage;
        _recycleViewParentLayout = recycleViewParentLayout;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target)
    {
        _adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
    {
        if (direction == 4)
        {
            if (_adapter.canActionRightImage())
                onRightImageClick(viewHolder.itemView);
        }
        else if (direction == 8)
        {
            if (_adapter.canActionLeftImage())
                onLeftImageClick();
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive)
    {
        if (actionState == 1)
        {
            View itemView = viewHolder.itemView;
            _adapter.setCurrentItem(((LifesItemViewHolder<Object>) viewHolder).getObject());
            Integer ddX = (int) dX;

            if ((_adapter.isUndoOnRight() && !_adapter.isUndoOnLeft() && !isCurrentlyActive) ||
                    (dX <= -_basedBorderLineRight && _adapter.isUndoOnRight() && !_adapter.isUndoOnLeft() && isCurrentlyActive) ||
                    ((dX < 0) && !_adapter.isUndoOnRight() && !_adapter.isUndoOnLeft() & !_adapter.isFromLeftUndoOn()) ||
                    (dX >= 0 && !_adapter.isUndoOnRight() && !isCurrentlyActive && _adapter.isFromRightUndoOn()))
            {
                if (!_adapter.canActionRightImage()) return;
                if (dX == -c.getWidth()) return;

                ddX = getddXRight(dX, isCurrentlyActive);
                //noinspection StatementWithEmptyBody
                if (ddX <= -itemView.getWidth()) { }
                else if (ddX <= 0)
                    setImageForRight(itemView, ddX);
            }

            if ((_adapter.isUndoOnLeft() && !_adapter.isUndoOnRight() && !isCurrentlyActive) ||
                    (dX >= -_basedBorderLineLeft && _adapter.isUndoOnLeft() && !_adapter.isUndoOnRight() && isCurrentlyActive) ||
                    ((dX > 0) && !_adapter.isUndoOnLeft() && !_adapter.isUndoOnRight() && !_adapter.isFromRightUndoOn()) ||
                    (dX <= 0 && !_adapter.isUndoOnLeft() && !isCurrentlyActive && _adapter.isFromLeftUndoOn()))
            {
                if (!_adapter.canActionLeftImage()) return;
                if (dX == c.getWidth()) return;

                ddX = getddXLeft(dX, isCurrentlyActive);
                //noinspection StatementWithEmptyBody
                if (ddX >= itemView.getWidth()) { }
                else if (ddX >= 0)
                    setImageForLeft(itemView, ddX);
            }

            super.onChildDraw(c, recyclerView, viewHolder, ddX, dY, actionState, isCurrentlyActive);
        }
        else
        {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    private Integer getddXLeft(float dX, boolean isCurrentlyActive)
    {
        if (!_showLeftImage) return (int) dX;

        int ddX = (int) dX;

        if ((!_adapter.isUndoOnLeft()) && isCurrentlyActive)
        {
            _adapter.setStartMoveLeftX2(0f);
        }
        else if ((!_adapter.isUndoOnLeft()) && (!isCurrentlyActive))
        {
            //noinspection StatementWithEmptyBody
            if (abs(dX) < abs(_basedBorderLineLeft)) { }
            else
            {
                _adapter.setStartMoveLeftX(dX);
                _adapter.setUndoOnLeft(true);
            }
        }
        else if (_adapter.isUndoOnLeft() && isCurrentlyActive) { }
        else if (_adapter.isUndoOnLeft() && !isCurrentlyActive)
        {
            if (abs(dX + _basedBorderLineLeft) < abs(_basedBorderLineLeft))
            {
                _adapter.setUndoOnLeft(false);
                _adapter.setFromLeftUndoOn(true);
                _adapter.setStartMoveLeftX(0f);
                _adapter.setStartMoveLeftX2(dX);
            }
        }

        if ( _adapter.getStartMoveLeftX() == 0 && isCurrentlyActive)
            ddX = (int) dX;
        if (_adapter.getStartMoveLeftX() != 0 && !isCurrentlyActive)
            ddX = (int) (dX + _basedBorderLineLeft * (_adapter.getStartMoveLeftX() - dX) / _adapter.getStartMoveLeftX());
        if (_adapter.getStartMoveLeftX() != 0 && isCurrentlyActive)
            ddX = (int )(dX + _basedBorderLineLeft);
        if (_adapter.getStartMoveLeftX() == 0 && !isCurrentlyActive)
        {
            if (dX > 0)
                ddX = (int) dX;
            else
            {
                ddX = (int) (dX * (_basedBorderLineLeft + _adapter.getStartMoveLeftX2()) / _adapter.getStartMoveLeftX2());
            }
        }

        return ddX;
    }

    private Integer getddXRight(float dX, boolean isCurrentlyActive)
    {
        if (!_showRightImage) return (int) dX;
        if (!_adapter.canActionRightImage()) return (int) dX;

        int ddX = (int) dX;

        if ((!_adapter.isUndoOnRight()) && isCurrentlyActive)
        {
            _adapter.setStartMoveRightX2(0f);
        }
        else if ((!_adapter.isUndoOnRight()) && (!isCurrentlyActive))
        {
            //noinspection StatementWithEmptyBody
            if (abs(dX) < abs(_basedBorderLineRight)) { }
            else
            {
                _adapter.setStartMoveRightX(dX);
                _adapter.setUndoOnRight(true);
            }
        }
        else if (_adapter.isUndoOnRight() && isCurrentlyActive) { }
        else if (_adapter.isUndoOnRight() && !isCurrentlyActive)
        {
            if (abs(dX + _basedBorderLineRight) < abs(_basedBorderLineRight))
            {
                _adapter.setUndoOnRight(false);
                _adapter.setFromRightUndoOn(true);
                _adapter.setStartMoveRightX(0f);
                _adapter.setStartMoveRightX2(dX);
            }
        }

        if ( _adapter.getStartMoveRightX() == 0 && isCurrentlyActive)
            ddX = (int) dX;
        if (_adapter.getStartMoveRightX() != 0 && !isCurrentlyActive)
            ddX = (int) (dX + _basedBorderLineRight * (_adapter.getStartMoveRightX() - dX) / _adapter.getStartMoveRightX());
        if (_adapter.getStartMoveRightX() != 0 && isCurrentlyActive)
            ddX = (int )(dX + _basedBorderLineRight);
        if (_adapter.getStartMoveRightX() == 0 && !isCurrentlyActive)
        {
            if (dX < 0)
                ddX = (int) dX;
            else
            {
                ddX = (int) (dX * (_basedBorderLineRight + _adapter.getStartMoveRightX2()) / _adapter.getStartMoveRightX2());
            }
        }

        return ddX;
    }

    private void setImageForLeft(View itemView, Integer ddX)
    {
        if (!_showLeftImage) return;

        ImageView imageView = _adapter.getImageViewLeft();
        Drawable background = new ColorDrawable(Color.WHITE);
        background.setBounds(itemView.getLeft(), itemView.getTop() + _rView.getTop(),
                itemView.getLeft() + ddX, itemView.getBottom() + _rView.getTop());

//        Log.d("Bounds", "" + itemView.getLeft() + "_" +
//                String.valueOf(itemView.getTop() + _rView.getTop()) + "_" +
//                String.valueOf(itemView.getLeft() + ddX) + "_" +
//                String.valueOf(itemView.getBottom() + _rView.getTop()));

        if (imageView != null)
            ((ViewGroup)_rView.getParent()).removeView(imageView);
        else
        {
            imageView = new ImageView(_rView.getContext());
            _adapter.setImageViewLeft(imageView);

            imageView.setImageDrawable(AppCompatResources.getDrawable(_rView.getContext(),
                    _leftImage));
            imageView.setClickable(true);
            imageView.setOnClickListener(view -> onLeftImageClick());
        }
        imageView.setBackground(background);

        int[] parentLocationOnScreen = new int[2];
        ((View)itemView.getParent()).getLocationOnScreen(parentLocationOnScreen);
        imageView.setX(parentLocationOnScreen[0] + itemView.getLeft());
        imageView.setY(itemView.getTop() + _rView.getTop());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);


        if (_recycleViewParentLayout.equals(EnumRecycleViewParentLayout.FrameLayout))
            imageView.setLayoutParams(new FrameLayout.LayoutParams(abs(ddX), itemView.getHeight()));
        else if (_recycleViewParentLayout.equals(EnumRecycleViewParentLayout.RelativeLayout))
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(abs(ddX), itemView.getHeight()));
        else if (_recycleViewParentLayout.equals(EnumRecycleViewParentLayout.LinearLayout))
            imageView.setLayoutParams(new LinearLayout.LayoutParams(abs(ddX), itemView.getHeight()));
        else if (_recycleViewParentLayout.equals(EnumRecycleViewParentLayout.ConstraintLayout))
            imageView.setLayoutParams(new ConstraintLayout.LayoutParams(abs(ddX), itemView.getHeight()));
        if (imageView.getWidth() < imageView.getHeight())
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        else
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ((ViewGroup)_rView.getParent()).addView(imageView);
    }

    private void setImageForRight(View itemView, Integer ddX)
    {
        if (!_showRightImage) return;
        if (!_adapter.canActionRightImage()) return;

        ImageView imageView = _adapter.getImageViewRight();
        Drawable background = new ColorDrawable(Color.WHITE);
        background.setBounds(itemView.getRight() + ddX, itemView.getTop() + _rView.getTop(),
                itemView.getRight(), itemView.getBottom() + _rView.getTop());

        if (imageView != null)
            ((ViewGroup)_rView.getParent()).removeView(imageView);
        else
        {
            imageView = new ImageView(_rView.getContext());
            _adapter.setImageViewRight(imageView);

            imageView.setImageDrawable(AppCompatResources.getDrawable(_rView.getContext(),
                    _rightImage));
            imageView.setClickable(true);
            imageView.setOnClickListener(view -> onRightImageClick(itemView));
        }
        imageView.setBackground(background);

        int[] parentLocationOnScreen = new int[2];
        ((View)itemView.getParent()).getLocationOnScreen(parentLocationOnScreen);

        imageView.setX(parentLocationOnScreen[0] + itemView.getRight() - abs(ddX));
        imageView.setY(itemView.getTop() + _rView.getTop());

        if (_recycleViewParentLayout.equals(EnumRecycleViewParentLayout.FrameLayout))
            imageView.setLayoutParams(new FrameLayout.LayoutParams(abs(ddX), itemView.getHeight()));
        else if (_recycleViewParentLayout.equals(EnumRecycleViewParentLayout.RelativeLayout))
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(abs(ddX), itemView.getHeight()));
        else if (_recycleViewParentLayout.equals(EnumRecycleViewParentLayout.LinearLayout))
            imageView.setLayoutParams(new LinearLayout.LayoutParams(abs(ddX), itemView.getHeight()));
        else if (_recycleViewParentLayout.equals(EnumRecycleViewParentLayout.ConstraintLayout))
            imageView.setLayoutParams(new ConstraintLayout.LayoutParams(abs(ddX), itemView.getHeight()));

        if (imageView.getWidth() < imageView.getHeight())
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        else
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ((ViewGroup)_rView.getParent()).addView(imageView);
    }

    private void onRightImageClick(View itemView)
    {
        if (_adapter.RightButtonClick())
        {
            _adapter.getImageViewRight().getLayoutParams().width = 0;
            _adapter.getImageViewRight().getLayoutParams().height = 0;

            _adapter.setImageViewRight(null);
            _rView.removeView(itemView);

            _adapter.setUndoOnRight(false);
            _adapter.setStartMoveRightX(0f);
            _adapter.setStartMoveRightX2(0f);
        }
    }

    private void onLeftImageClick()
    {
        if (_adapter.LeftButtonClick())
        {
            _adapter.getImageViewLeft().getLayoutParams().width = 0;
            _adapter.getImageViewLeft().getLayoutParams().height = 0;

            _adapter.setImageViewLeft(null);

            _adapter.setUndoOnLeft(false);
            _adapter.setStartMoveLeftX(0f);
            _adapter.setStartMoveLeftX2(0f);
        }
    }
}
