package abortnik.grammarpro;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import abortnik.grammarpro.data.UIHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class Explainator extends Fragment {


    public Explainator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explainator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        title.setTypeface(UIHelper.getMediumFont(getActivity()));
        description.setTypeface(UIHelper.getLightFont(getActivity()));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).switchFrag(iHomeActivity.FRAG_EXPLAIN2);
            }
        });
    }
}
