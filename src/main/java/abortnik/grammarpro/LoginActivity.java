package abortnik.grammarpro;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements iLoginActivity {

    private int lastFrag;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_startscreen);
        mAuth = FirebaseAuth.getInstance();
        switchFrag(FRAG_FIRST);
    }


    public void switchFrag(final int fragId) {
        lastFrag = fragId;
        final Fragment actFrag = getActFrag();
        Fragment newFrag;

        boolean toProcess = false;
        switch (fragId) {
            default:
            case -1:
                // unkwnown
                newFrag = null;
                break;
            case FRAG_FIRST:
                newFrag = actFrag instanceof startScreen ? null
                        : new startScreen();
                break;
            case FRAG_LOGIN:
                newFrag = actFrag instanceof login ? null
                        : new login();
                break;
            case FRAG_REGISTER:
                newFrag = actFrag instanceof Register ? null
                        : new Register();
                break;
        }

        if (newFrag != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, newFrag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private Fragment getActFrag() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    private void attemptLogin() {
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
// UZ PRIHLASENY
            startActivity(new Intent(this, HomeActivity.class));
            this.finish();
        }
    }

}


