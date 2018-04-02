package abortnik.grammarpro.data;

/**
 * Created by abortnik on 21.3.18.
 */

public class Question {

    public String question;

    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Question(String question) {
        this.question = question;
    }



}
