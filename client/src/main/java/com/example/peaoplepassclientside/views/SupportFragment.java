package com.example.peaoplepassclientside.views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment {


    public SupportFragment() {
        // Required empty public constructor
    }
    TextView textView;
    Button callBtn,chatBtn;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_support, container, false);
        chatBtn = v.findViewById(R.id.chat_support_btn);
        callBtn = v.findViewById(R.id.call_support_btn);
        textView = v.findViewById(R.id.txt_support_toolbar);

        Font.montSerrat(getContext(),chatBtn);
        Font.montSerratMedium(getContext(),textView);
        Font.montSerrat(getContext(),callBtn);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String phone = getResources().getString(R.string.support_phone_number);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);
            }
        });
        return v;
    }

}
