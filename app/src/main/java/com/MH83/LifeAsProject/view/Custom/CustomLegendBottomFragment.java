package com.MH83.LifeAsProject.view.Custom;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.VerticalImageSpan;
import com.MH83.LifeAsProject.model.Custom.CustomMoneyObject;

public class CustomLegendBottomFragment extends CustomBottomFragment
{
    final int constImgWidth = 55;
    final int constImgHeight = 55;

    public CustomLegendBottomFragment(int layoutId)
    {
        super(layoutId);
    }

    public CustomLegendBottomFragment(AppCompatActivity activity, int layoutId)
    {
        super(activity, layoutId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

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
        View view = super.onCreateView(inflater, container, savedInstanceState);

        SetListeners();

        TextView textViewLegend = view.findViewById(R.id.textViewLegend);
        if (textViewLegend != null)
        {
            addTextToTextView(textViewLegend, "[dream_free_count]",
                    String.valueOf(CustomMoneyObject.moneyDreamFreeCount));

            addTextToTextView(textViewLegend, "[category_free_count]",
                    String.valueOf(CustomMoneyObject.moneyCategoryFreeCount));

            addTextToTextView(textViewLegend, "[tag_free_count]",
                    String.valueOf(CustomMoneyObject.tagFreeCount));

            addImageToTextView(textViewLegend, "[finishedtargets]", R.drawable.icons_ok_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[targetperyear]", R.drawable.icons_year_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[targetlist]", R.drawable.icons_list_100_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[mascot]", R.drawable.mascot,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[plus]", R.drawable.icons_plus_data_100_,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[delete]", R.drawable.icons_delete_item_50,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[copy]", R.drawable.icons_copy_blue_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[delete_purple]", R.drawable.icons8_delete_100_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[target]", R.drawable.icons_target_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[legend]", R.drawable.icons_legend_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[cabinet]", R.drawable.icons_cabinet_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[edit_pic]", R.drawable.icons_foto_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[double_left_arrow]", R.drawable.icons_double_left_70,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[double_right_arrow]", R.drawable.icons_double_right_70,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[circle_half_purple]", R.drawable.icons_half_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[circle_white]", R.drawable.icons_circle_white_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[circle_purple]", R.drawable.icons_circle_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[circle_orange]", R.drawable.icons_circle_orange_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[circle_green]", R.drawable.icons_circle_green_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[circle_grey]", R.drawable.icons_circle_grey_50,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[circle_orange_with_number]", R.drawable.icons_circle_orange_with_number_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[goto_fact]", R.drawable.icons_letter_f_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[goto_plan]", R.drawable.icons_letter_p_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[goto_incoming]", R.drawable.icons_letter_d_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[goto_cost]", R.drawable.icons_letter_r_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[expand_all]", R.drawable.icons8_expand_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[collapse_all]", R.drawable.icons8_collapse_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[show_percent]", R.drawable.icons_percent_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[show_percent_increase]", R.drawable.icons_increase_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[show_tree]", R.drawable.icons_tree_100_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[show_list]", R.drawable.icons_list_100_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[settings]", R.drawable.icons_settings_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[card_settings]", R.drawable.icons_settings_purple_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[add_image]", R.drawable.icons_plus_data_100_,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[add_imageSub]", R.drawable.icons_add_100_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[archive]", R.drawable.icons_nonarchive,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[nonarchive]", R.drawable.icon_archive,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[up]", R.drawable.icons_up_100_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[down]", R.drawable.icons_down_100_purple,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[operation_isPlan]", R.drawable.icons_is_plan_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[operation_isFact]", R.drawable.icons_is_fact_100,
                    constImgWidth, constImgHeight);

            addImageToTextView(textViewLegend, "[next_form]", R.drawable.icons_double_right_black_48,
                    constImgWidth, constImgHeight);
        }

        return view;
    }

    private void addImageToTextView(TextView textView, String atText, int imgSrc,
                                    int imgWidth, int imgHeight)
    {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText());

        Drawable drawable = AppCompatResources.getDrawable(getContext(), imgSrc);
        drawable.setBounds(0, 0, imgWidth, imgHeight);

        int start = textView.getText().toString().indexOf(atText);

        while (start >= 0)
        {
            VerticalImageSpan verticalImageSpan = new VerticalImageSpan(drawable);
            spannableStringBuilder.setSpan(verticalImageSpan, start, start + atText.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.setText(spannableStringBuilder, TextView.BufferType.SPANNABLE);
            start = textView.getText().toString().indexOf(atText, start + 1);
        }
    }

    private void addTextToTextView(TextView textView, String atText, String text)
    {
        textView.setText(textView.getText().toString().replace(atText, text));
    }
}
