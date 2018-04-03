package abortnik.grammarpro;

import android.widget.TextView;

public class ResultConstructor {
    public String question;
    public String time;
    public boolean correct;

    public ResultConstructor() {

    }

    public ResultConstructor(String question, String time, boolean correct) {
        this.question = question;
        this.time = time;
        this.correct = correct;
    }
}
