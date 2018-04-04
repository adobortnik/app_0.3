package abortnik.grammarpro;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import abortnik.grammarpro.data.Test;
import abortnik.grammarpro.data.User;

import static abortnik.grammarpro.iHomeActivity.MY_PREFERENCES;


/**
 * A simple {@link Fragment} subclass.
 */
public class Test_Screen extends Fragment {

    int question = 0;
    private DatabaseReference mDatabase;
    private TextView counter;
    private ProgressBar progressBar;
    private TextView question_text;
    private int current_question = 1;
    private int current_question_inLoop;
    private Timer t;

    public Test_Screen() {
        // Required empty public constructor
    }

    private int time;

    private int[] BoldTextViews_id = {R.id.title, R.id.question, R.id.next, R.id.counter};
    private TextView[] BoldTextViews = new TextView[BoldTextViews_id.length];
    private int[] LightTextViews_id = {R.id.answer_1, R.id.answer_2, R.id.answer_3, R.id.answer_4};
    private TextView[] LightTextViews = new TextView[LightTextViews_id.length];
    private int[] Radio_id = {R.id.radio_1, R.id.radio_2, R.id.radio_3, R.id.radio_4};
    private int[] Input_id = {R.id.input_1, R.id.input_2, R.id.input_3, R.id.input_4};
    private LinearLayout[] Input = new LinearLayout[Input_id.length];
    private ImageView[] Radio = new ImageView[Radio_id.length];
    private int[] CardView_id = {R.id.row1, R.id.row2, R.id.row3, R.id.row4};
    private CardView[] Row = new CardView[CardView_id.length];
    private List<ResultConstructor> resultList = new ArrayList<>();
    private String correct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        question_text = (TextView) view.findViewById(R.id.question);
        counter = (TextView) view.findViewById(R.id.counter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button next = (Button) view.findViewById(R.id.next);
        for (int i = 0; i < LightTextViews_id.length; i++) {
            LightTextViews[i] = (TextView) view.findViewById(LightTextViews_id[i]);
            Radio[i] = (ImageView) view.findViewById(Radio_id[i]);
            // LightTextViews[i].setTypeface(UIHelper.getLightFont(getActivity()));
            Input[i] = (LinearLayout) view.findViewById(Input_id[i]);
            Row[i] = (CardView) view.findViewById(CardView_id[i]);
            final int finalI = i;
            Row[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDot(finalI);
                }
            });
        }

        startTimer();
        loadData();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WhichIsChoosen() < 0) {
                    Toast.makeText(getActivity(), "Musíš to vyplniť", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (current_question == 11) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    String timik = stopTimer();
                    time = 0;
                    addToList(question_text.getText().toString(), timik, IsCorrect());
                    Gson gson = new Gson();
                    String json = gson.toJson(resultList);
                    prefsEditor.putString("ResultList", json);
                    prefsEditor.commit();
                    ((HomeActivity) getActivity()).switchFrag(iHomeActivity.FRAG_RESULT);
                } else {
                    String timik = stopTimer();
                    time = 0;
                    addToList(question_text.getText().toString(), timik, IsCorrect());
                    loadData();

                }
            }
        });

    }

    private void startTimer() {
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //  myTextView.setText("count="+count);
                time++;

            }
        }, 1000, 1000);
    }

    private String stopTimer() {
        if (t != null) {
            t.cancel();
        }
        return getDateFromMillis(time * 1000);
    }

    public static String getDateFromMillis(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return formatter.format(new Date(millis));
    }

    private void addToList(String question, String time, boolean correct) {
        ResultConstructor resultConstructor = new ResultConstructor(question, time, correct);
        resultList.add(resultConstructor);

    }

    private int WhichIsChoosen() {
        int visible = -1;
        for (int i = 0; i < Radio_id.length; i++) {
            if (Radio[i].getVisibility() == View.VISIBLE) {
                visible = i;
            }
        }
        return visible;
    }

    private Boolean IsCorrect() {
        boolean isCorrect = false;
        String chosen_answer = LightTextViews[WhichIsChoosen()].getText().toString();
        if (chosen_answer.equals(correct)) {
            isCorrect = true;
        }

        return isCorrect;
    }

    private void showDot(int index) {
        for (int i = 0; i < Radio_id.length; i++) {
            if (i != index) {
                Radio[i].setVisibility(View.GONE);
            }
            Radio[index].setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        startTimer();
        progressBar.setVisibility(View.VISIBLE);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                User user = ((HomeActivity) getActivity()).getInfoAboutUser(dataSnapshot);
                int randomNumber = generateNumber(dataSnapshot);
                current_question_inLoop = 0;

                for (DataSnapshot dsp : dataSnapshot.child("Testy").getChildren()) {
                    if (randomNumber == current_question_inLoop) {
                        Test test = dsp.getValue(Test.class);
                        question_text.setText(dsp.getKey());
                        counter.setText(String.valueOf(current_question) + " of " + "10");
                        showAnswers(test);
                        correct = test.correct;
                        current_question++;
                    }
                    current_question_inLoop++;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("POST", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);

    }

    private void showAnswers(Test test) {
        for (int i = 0; i < 4; i++) {
            Radio[i].setVisibility(View.GONE);
            switch (i) {
                case 0:
                    LightTextViews[i].setText(test.option1);
                    break;
                case 1:
                    LightTextViews[i].setText(test.option2);
                    break;
                case 2:
                    LightTextViews[i].setText(test.option3);
                    break;
                case 3:
                    LightTextViews[i].setText(test.option4);
                    break;

            }
        }
    }

    private int generateNumber(DataSnapshot dataSnapshot) {
        int min = 0;
        int max = (int) dataSnapshot.child("Testy").getChildrenCount();
        Random r = new Random();
        int i1 = r.nextInt(max - min) + min;
        return i1;
    }
}

