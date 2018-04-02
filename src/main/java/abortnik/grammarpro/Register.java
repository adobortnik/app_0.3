package abortnik.grammarpro;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import abortnik.grammarpro.data.UIHelper;
import abortnik.grammarpro.data.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {


    private DatabaseReference mDatabase;

    public Register() {
        // Required empty public constructor
    }
private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.login_progress);
        TextView grammar = (TextView) view.findViewById(R.id.grammar);
        TextView pro = (TextView) view.findViewById(R.id.pro);
        Button sign_up = (Button) view.findViewById(R.id.sign_up);
        final EditText email = (EditText) view.findViewById(R.id.email);
        final EditText username = (EditText) view.findViewById(R.id.username);
        final EditText password = (EditText) view.findViewById(R.id.password);
        sign_up.setTypeface(UIHelper.getBoldFont(getActivity()));
        pro.setTypeface(UIHelper.getRegularFont(getActivity()));
        grammar.setTypeface(UIHelper.getUltraLight(getActivity()));
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(password.getText().toString()) && TextUtils.isEmpty(username.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Register", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username.getText().toString())
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("REGISTER", "User profile updated.");
                                                    }
                                                }
                                            });
                                    writeNewUser(user.getUid(), username.getText().toString(), user.getEmail());
                                    startActivity(new Intent(getActivity(), HomeActivity.class));
                                   // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Register", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                  //  updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email, 1, 0, 0);

        mDatabase.child("users").child(userId).setValue(user);
    }
}
