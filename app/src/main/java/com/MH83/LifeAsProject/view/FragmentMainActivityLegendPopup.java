package com.MH83.LifeAsProject.view;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.view.Custom.CustomLegendPopup;

public class FragmentMainActivityLegendPopup extends CustomLegendPopup
{
    public FragmentMainActivityLegendPopup()
    {
        super(R.layout.popup_legend_main_activity);
    }

    public FragmentMainActivityLegendPopup(AppCompatActivity activity)
    {
        super(activity, R.layout.popup_legend_main_activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        TextView textView = view.findViewById(R.id.textViewLegendLink);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView = view.findViewById(R.id.textViewLegendLinkBot);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
