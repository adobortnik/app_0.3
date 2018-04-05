package abortnik.grammarpro.data;

/**
 * Created by abortnik on 21.3.18.
 */

public class User {

    public String username;
    public String email;
    public int level;
    public int wins;
    public int played;
    public String code;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
public User(int level) {
    this.level = level;
}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, int level, int wins, int played) {
        this.username = username;
        this.email = email;
        this.level = level;
        this.wins = wins;
        this.played = played;
    }

}