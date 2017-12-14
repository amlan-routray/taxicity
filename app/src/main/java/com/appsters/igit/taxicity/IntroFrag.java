package com.appsters.igit.taxicity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFrag extends Fragment  {

    RelativeLayout intro1,intro2,intro3,intro4;
    Button get_started_button;

    public IntroFrag() {
        // Required empty public constructor
    }

    static IntroFrag init(int value) {
        IntroFrag fragment = new IntroFrag();
        Bundle args = new Bundle();
        args.putInt("value", value);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_intro_frag, container, false);
        get_started_button=(Button)view.findViewById(R.id.get_started_button);
        get_started_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),SignInActivity.class));
            }
        });
        intro1=(RelativeLayout)view.findViewById(R.id.intro1);
        intro2=(RelativeLayout)view.findViewById(R.id.intro2);
        intro3=(RelativeLayout)view.findViewById(R.id.intro3);
        int introNumber=getArguments().getInt("value");
        switch (introNumber)
        {
            case 0:
              intro1.setVisibility(View.VISIBLE);
                intro2.setVisibility(View.INVISIBLE);
                intro3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                intro2.setVisibility(View.VISIBLE);
                intro1.setVisibility(View.INVISIBLE);
                intro3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                intro2.setVisibility(View.INVISIBLE);
                intro3.setVisibility(View.VISIBLE);
                intro1.setVisibility(View.INVISIBLE);
                break;

        }
        return view;
    }

}
