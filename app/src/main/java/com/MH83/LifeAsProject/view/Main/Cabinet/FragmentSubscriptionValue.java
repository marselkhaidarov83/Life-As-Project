package com.MH83.LifeAsProject.view.Main.Cabinet;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.MH83.LifeAsProject.R;

public class FragmentSubscriptionValue extends Fragment
{
    private static final String ARG_PARAM1 = "paramText";

    private int mParam1;

    public FragmentSubscriptionValue() { }

    public static FragmentSubscriptionValue newInstance(Integer param1)
    {
        FragmentSubscriptionValue fragment = new FragmentSubscriptionValue();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_subscription_value, container, false);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText(mParam1);

        return view;
    }
}