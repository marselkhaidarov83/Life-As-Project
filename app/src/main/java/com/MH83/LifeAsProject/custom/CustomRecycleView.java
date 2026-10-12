package com.MH83.LifeAsProject.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;

public class CustomRecycleView extends RecyclerView
{
    private int     m_gridMinSpans;
    private int     m_gridItemLayoutId;
    private final LayoutRequester m_layoutRequester = new LayoutRequester();
    private int _spanCount = 15;

    public CustomRecycleView(@NonNull Context context) { super(context); }

    public CustomRecycleView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomRecycleView(@NonNull Context context, @Nullable AttributeSet attrs,
                             int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public interface OnRViewLayoutListener { void onRViewLayouted(RecyclerView recyclerView); }
    CustomRecycleView.OnRViewLayoutListener mOnRViewLayoutListener;
    public void setOnRViewLayoutListener(CustomRecycleView.OnRViewLayoutListener listener)
    {
        mOnRViewLayoutListener = listener;
    }

    public void setGridLayoutManager( int orientation, int itemLayoutId, int minSpans )
    {
        GridLayoutManager layoutManager = new GridLayoutManager( getContext(), _spanCount,
                orientation, false );
        m_gridItemLayoutId = itemLayoutId;
        m_gridMinSpans = minSpans;

        setLayoutManager( layoutManager );
    }

    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom )
    {
        super.onLayout( changed, left, top, right, bottom );

        if( changed && m_gridItemLayoutId != 0)
        {
            LayoutManager layoutManager = getLayoutManager();
            if( layoutManager instanceof GridLayoutManager )
            {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
//                LayoutInflater inflater = LayoutInflater.from( getContext() );
//                View item = inflater.inflate( m_gridItemLayoutId, this, false );
//                int measureSpec = View.MeasureSpec.makeMeasureSpec( 0, View.MeasureSpec.UNSPECIFIED );
//                item.measure( measureSpec, measureSpec );
//                int itemWidth = item.getMeasuredWidth();
                int itemWidth =
                        (int) getResources().getDimension(R.dimen.CustomRViewItemWidth);
                int recyclerViewWidth = getMeasuredWidth();
                _spanCount = Math.max( m_gridMinSpans, recyclerViewWidth / itemWidth );

                gridLayoutManager.setSpanCount( _spanCount );
                this.setPadding(0, 0, recyclerViewWidth - _spanCount * itemWidth, 0);

                // if you call requestLayout() right here, you'll get ArrayIndexOutOfBoundsException when scrolling
                post( m_layoutRequester );
            }
        }
    }

    private class LayoutRequester implements Runnable {
        @Override
        public void run() {
            requestLayout();
        }
    }
}
