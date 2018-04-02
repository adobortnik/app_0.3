package abortnik.grammarpro;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import abortnik.grammarpro.data.UIHelper;
import abortnik.grammarpro.data.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class login extends Fragment {


    private DatabaseReference mDatabase;

    public login() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.login_progress);
        final EditText email = (EditText) view.findViewById(R.id.email);
        final EditText password = (EditText) view.findViewById(R.id.password);
        final TextInputLayout email_view = (TextInputLayout) view.findViewById(R.id.email_view);
        TextView grammar = (TextView) view.findViewById(R.id.grammar);
        TextView pro = (TextView) view.findViewById(R.id.pro);
        TextView sign_up = (TextView) view.findViewById(R.id.sign_up);
        final Button login = (Button) view.findViewById(R.id.sign_in);
        sign_up.setTypeface(UIHelper.getBoldFont(getActivity()));
        grammar.setTypeface(UIHelper.getUltraLight(getActivity()));
        pro.setTypeface(UIHelper.getRegularFont(getActivity()));
        login.setTypeface(UIHelper.getBoldFont(getActivity()));
        String first = "Don't have an account?";
        String next = "<font color='#E65151'> Sign up!</font>";
        sign_up.setText(Html.fromHtml(first + next));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(password.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Login", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail());
                                    startActivity(new Intent(getActivity(), HomeActivity.class));
                                } else {

                                    // If sign in fails, display a message to the user.
                                    Log.w("Login", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity) getActivity()).switchFrag(iLoginActivity.FRAG_REGISTER);
            }
        });

    }
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}
