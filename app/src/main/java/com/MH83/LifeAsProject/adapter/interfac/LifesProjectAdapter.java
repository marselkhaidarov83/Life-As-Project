package com.MH83.LifeAsProject.adapter.interfac;

import android.widget.ImageView;

public interface LifesProjectAdapter<TDataSet>
{
    boolean LeftButtonClick();
    boolean RightButtonClick();
    ImageView getImageViewRight();
    ImageView getImageViewLeft();
    void setImageViewRight(ImageView imageView);
    void setImageViewLeft(ImageView imageView);
    void setUndoOnRight(boolean undoOnRight);
    void setUndoOnLeft(boolean undoOnLeft);
    boolean isUndoOnRight();
    boolean isUndoOnLeft();
    boolean isFromRightUndoOn();
    boolean isFromLeftUndoOn();
    void setFromRightUndoOn(boolean fromRightUndoOn);
    void setFromLeftUndoOn(boolean fromLeftUndoOn);
    void setStartMoveRightX(Float f);
    void setStartMoveLeftX(Float f);
    void setStartMoveRightX2(Float f);
    void setStartMoveLeftX2(Float f);
    float getStartMoveRightX();
    float getStartMoveLeftX();
    float getStartMoveRightX2();
    float getStartMoveLeftX2();
    void setCurrentItem(TDataSet object);
    TDataSet getCurrentItem();
    void onItemMove(int fromPosition, int toPosition);
    boolean canActionRightImage();
    boolean canActionLeftImage();
}
