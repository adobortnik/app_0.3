package abortnik.grammarpro;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import abortnik.grammarpro.data.UIHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class startScreen extends Fragment {


    public startScreen() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView grammar = (TextView) view.findViewById(R.id.grammar);
        TextView pro = (TextView) view.findViewById(R.id.pro);
        TextView popis = (TextView) view.findViewById(R.id.popis);
        Button sign_up = (Button) view.findViewById(R.id.sign_up);
        Button login = (Button) view.findViewById(R.id.login);
        sign_up.setTypeface(UIHelper.getBoldFont(getActivity()));
        popis.setTypeface(UIHelper.getRegularFont(getActivity()));
        pro.setTypeface(UIHelper.getRegularFont(getActivity()));
        grammar.setTypeface(UIHelper.getUltraLight(getActivity()));
        login.setTypeface(UIHelper.getBoldFont(getActivity()));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity)getActivity()).switchFrag(iLoginActivity.FRAG_LOGIN);
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity)getActivity()).switchFrag(iLoginActivity.FRAG_REGISTER);

            }
        });


    }
}
