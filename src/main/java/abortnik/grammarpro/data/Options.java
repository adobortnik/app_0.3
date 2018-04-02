package abortnik.grammarpro.data;

/**
 * Created by abortnik on 21.3.18.
 */

public class Options {
    public String option1;
    public String option2;
    public String option3;
    public String option4;

    public Options() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Options(String option1, String option2, String option3, String option4) {
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }
}
