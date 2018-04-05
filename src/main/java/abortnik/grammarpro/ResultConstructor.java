package abortnik.grammarpro;

import android.widget.TextView;

public class ResultConstructor {
    public String question;
    public String time;
    public boolean correct;
    public String correct_sentence;

    public ResultConstructor() {

    }

    public ResultConstructor(String question, String time, boolean correct, String correct_sentence) {
        this.question = question;
        this.time = time;
        this.correct = correct;
        this.correct_sentence = correct_sentence;
    }
}
