package com.MH83.LifeAsProject.view.Custom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.MH83.LifeAsProject.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CustomBottomFragment extends BottomSheetDialogFragment
{
    //region Fragment constants
    final int COLLAPSED_HEIGHT = 228;
    //endregion

    //region Fragment parameters
    private int _layoutId;
    protected AppCompatActivity _activity;
    //endregion

    //region Fragment components
    protected ImageView _imageViewClose;
    //endregion

    //region Constructor
    public CustomBottomFragment(int layoutId)
    {
        super();

        _layoutId = layoutId;
    }
    public CustomBottomFragment(AppCompatActivity activity, int layoutId)
    {
        super();

        _activity = activity;
        _layoutId = layoutId;
    }
    //endregion

    //region Start Fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getTheme()
    {
        return R.style.AppBottomSheetDialogTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(_layoutId, container);

        FindComponents(view);

        return view;
    }

    protected void FindComponents(View view)
    {
        _imageViewClose = view.findViewById(R.id.imageViewClose);
    }

    protected void SetListeners()
    {
        if (_imageViewClose != null)
            _imageViewClose.setOnClickListener(view -> dismiss());
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Плотность понадобится нам в дальнейшем
        float density = getContext().getResources().getDisplayMetrics().density;

        // Находим сам bottomSheet и достаём из него Behaviour
        FrameLayout frameLayout = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(frameLayout);

        // Выставляем высоту для состояния collapsed и выставляем состояние collapsed
        behavior.setPeekHeight((int) (COLLAPSED_HEIGHT * density));
//        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        behavior.setSkipCollapsed(true);

//        behavior.setFitToContents(true);
    }

    public void PopupShow()
    {
        show(_activity.getSupportFragmentManager(), "tag");
    }
    //endregion

    //region NonUsage
    private void DrawCloseButton(float density)
    {
        // Достаём корневые лэйауты
        CoordinatorLayout coordinator = getDialog().findViewById(com.google.android.material.R.id.coordinator);
        FrameLayout containerLayout = getDialog().findViewById(com.google.android.material.R.id.container);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.icons8_close_window_100_purple);

        // Выставляем параметры для нашей кнопки
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.width = (int) (100 * density);
//        layoutParams.height = (int) (100 * density);
        layoutParams.gravity = Gravity.TOP;
        imageView.setLayoutParams(layoutParams);

        // Добавляем кнопку в контейнер
        containerLayout.addView(imageView);

//        // Перерисовываем лэйаут
//            buttons.post {
//                (coordinator?.layoutParams as ViewGroup.MarginLayoutParams).apply {
//                    buttons.measure(
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//                    )
//                    // Устраняем разрыв между кнопкой и скролящейся частью
//                    this.bottomMargin = (buttons.measuredHeight - 8 * density).toInt()
//                    containerLayout?.requestLayout()
//                }
//            }
    }

    private void DrawSaveButton(float density)
    {
//        // Достаём корневые лэйауты
//        CoordinatorLayout coordinator = getDialog().findViewById(com.google.android.material.R.id.coordinator);
//        FrameLayout containerLayout = getDialog().findViewById(com.google.android.material.R.id.container);
//
//        @SuppressLint("ResourceType") ImageView imageView =
//                (ImageView) getDialog().getLayoutInflater().inflate(R.drawable.icons8_close_window_100_black, null);
//
//        // Надуваем наш лэйаут с кнопкой
//        LinearLayout button = (LinearLayout) getDialog().getLayoutInflater().inflate(R.layout.button, null);
//
//        // Выставляем параметры для нашей кнопки
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.height = (int) (40 * density);
//        layoutParams.gravity = Gravity.BOTTOM;
//        button.setLayoutParams(layoutParams);
//
//        // Добавляем кнопку в контейнер
//        containerLayout.addView(button);

//        // Перерисовываем лэйаут
//            buttons.post {
//                (coordinator?.layoutParams as ViewGroup.MarginLayoutParams).apply {
//                    buttons.measure(
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//                    )
//                    // Устраняем разрыв между кнопкой и скролящейся частью
//                    this.bottomMargin = (buttons.measuredHeight - 8 * density).toInt()
//                    containerLayout?.requestLayout()
//                }
//            }
    }
    //endregion
}
