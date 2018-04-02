package abortnik.grammarpro;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import abortnik.grammarpro.data.User;

public class HomeActivity extends AppCompatActivity implements iHomeActivity {
    private int lastFrag = 0;
int pocitadlo = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        switchFrag(FRAG_HOME);
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
            case FRAG_HOME:
                newFrag = actFrag instanceof Home ? null
                        : new Home();
                break;
            case FRAG_TEST:
                newFrag = actFrag instanceof Test_Screen ? null
                        : new Test_Screen();
                break;
            case FRAG_MULTIPLAYER_MENU:
                newFrag = actFrag instanceof MultiplayerMenu ? null
                        : new MultiplayerMenu();
                break;
            case FRAG_EXPLAIN:
                newFrag = actFrag instanceof Explainator ? null
                        : new Explainator();
                break;
            case FRAG_EXPLAIN2:
                newFrag = actFrag instanceof Explainator2 ? null
                        : new Explainator2();
                break;
            case FRAG_PROFILE:
                newFrag = actFrag instanceof Profile ? null
                        : new Profile();
                break;
        }
//PRVY ODSTRANIT REPLACE NA ADD
        if (newFrag != null) {
            if(pocitadlo == 0) {
                pocitadlo = 1;
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, newFrag)
                    //    .addToBackStack(null)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, newFrag)
                        .addToBackStack(null)
                        .commit();
            }

        }
    }

    private Fragment getActFrag() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    public User getInfoAboutUser(DataSnapshot dataSnapshot) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return dataSnapshot.child("users").child(uId).getValue(User.class);
    }
}
