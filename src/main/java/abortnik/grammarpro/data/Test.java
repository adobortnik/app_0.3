package abortnik.grammarpro.data;

/**
 * Created by abortnik on 21.3.18.
 */

public class Test {

    public String question;
    public String correct;
    public String option1;
    public String option2;
    public String option3;
    public String option4;

    public Test() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public Test(String question, String correct, String option1, String option2, String option3, String option4) {
        this.question = question;
        this.correct = correct;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;

    }
}
