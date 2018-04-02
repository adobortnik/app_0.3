package abortnik.grammarpro;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import abortnik.grammarpro.data.UIHelper;

import static abortnik.grammarpro.iHomeActivity.FRAG_PROFILE;
import static abortnik.grammarpro.iHomeActivity.FRAG_TEST;
import static abortnik.grammarpro.iHomeActivity.MY_PREFERENCES;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    int pocitadlo = 0;

    public Home() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        int pocitadlo = sharedPreferences.getInt("pocitadlo", 0);
        if (pocitadlo == 0) {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE).edit();
            editor.putInt("pocitadlo", 1);

        }
        Typeface light = UIHelper.getLightFont(getActivity());
        Typeface bold = UIHelper.getBoldFont(getActivity());
        TextView going = (TextView) view.findViewById(R.id.going);
        TextView name = (TextView) view.findViewById(R.id.name);
        going.setTypeface(light);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            name.setText(user.getDisplayName());
        }

        CardView start_tab = (CardView) view.findViewById(R.id.start_tab);
        start_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).switchFrag(FRAG_TEST);
            }
        });
        FrameLayout acc = (FrameLayout) view.findViewById(R.id.account);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).switchFrag(FRAG_PROFILE);
            }
        });
    }
}
