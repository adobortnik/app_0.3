package abortnik.grammarpro;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import abortnik.grammarpro.data.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRoom extends Fragment {


    public CreateRoom() {
        // Required empty public constructor
    }

    Random rnd = new Random();
    int numLetters = 6;

    String randomLetters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String[] codeArray;
    private FirebaseAuth mAuth;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        final FirebaseUser me = mAuth.getCurrentUser();
        final TextView name = (TextView) view.findViewById(R.id.name);
        final CardView friend_layout = (CardView) view.findViewById(R.id.user_tab);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        Log.i("CODE", generateCode(6));
        final String unique_code = generateCode(6);
        TextView code = (TextView) view.findViewById(R.id.code);
        final TextView status = (TextView) view.findViewById(R.id.searching);
        code.setText(unique_code);




        myRef.child(me.getUid()).child("code").setValue(unique_code);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    User user = dsp.getValue(User.class);
                    if (user.code != null) {
                        if (user.code.equals(unique_code)) {
                            if (dsp.getKey() != me.getUid()) {
                                progressBar.setVisibility(View.GONE);
                                friend_layout.setVisibility(View.VISIBLE);
                                name.setText(user.username);
                                status.setText("Found");
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String generateCode(int sizeOfRandomString) {
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);

        for (int i = 0; i < sizeOfRandomString; i++)
            sb.append(randomLetters.charAt(rnd.nextInt(randomLetters.length())));
        return sb.toString();
    }
}
