package abortnik.grammarpro;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import abortnik.grammarpro.data.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinRoom extends Fragment {


    private FirebaseAuth mAuth;

    public JoinRoom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        final FirebaseUser me = mAuth.getCurrentUser();
        // TextView searching = (TextView) view.findViewById(R.id.searching);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final View line = (View) view.findViewById(R.id.line);
        final EditText code = (EditText) view.findViewById(R.id.code);
        final CardView friend_layout = (CardView) view.findViewById(R.id.user_tab);
        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView status = (TextView) view.findViewById(R.id.searching);
        code.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(getActivity());
                    status.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    friend_layout.setVisibility(View.GONE);
                    status.setText("Searching");
                    progressBar.setVisibility(View.VISIBLE);
                    setCodeToMe(myRef, me, code.getText().toString());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                User user = dsp.getValue(User.class);
                                if (user.code != null) {
                                    if (user.code.equals(code.getText().toString())) {
                                        if (!dsp.getKey().equals(me.getUid())) {
                                            progressBar.setVisibility(View.GONE);
                                            friend_layout.setVisibility(View.VISIBLE);
                                            name.setText(user.username);
                                            status.setText("Found");
                                        }
                                    } else {
                                        progressBar.setVisibility(View.VISIBLE);
                                        friend_layout.setVisibility(View.GONE);
                                        status.setText("Searching");
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    return true; // consume.
                }
                return false; // pass on to other listeners.
            }
        });

    }

    private void setCodeToMe(DatabaseReference myRef, FirebaseUser me, String unique_code) {
        myRef.child(me.getUid()).child("code").setValue(unique_code);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
