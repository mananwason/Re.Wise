package re.wise.mananwason.rewise;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.Random;


public class QuestionActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView question_text;
    private EditText editText;
    private Button submit;
    private Integer index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        question_text = (TextView) findViewById(R.id.question_text);
        editText = (EditText) findViewById(R.id.ans_text);
        submit = (Button) findViewById(R.id.next);
        Query myTopPostsQuery = mDatabase.child("questions");
        final ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Female sharks have ______ skins than males", "thicker"));
        questions.add(new Question("The adult human skeleton has _____ bones?", "206"));
        questions.add(new Question("The first electronic computer _____ weighed more than 27 tons and took up 1800 square feet", "ENIAC"));
        questions.add(new Question("Only about __% of the world’s currency is physical money, the rest only exists on computers", "10"));
        questions.add(new Question("Doug Engelbart invented the first computer _____ in around 1964 which was made of wood.", "mouse"));
        questions.add(new Question("The password for the computer controls of nuclear tipped missiles of the U.S was _______ for eight years", "00000000"));


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().toLowerCase().equals(questions.get(index).ans) && !editText.getText().toString().isEmpty()) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.ll), "Awesome! you are a great learner!", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                }
            }
        });

        myTopPostsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println("ASDASD" + " " + dataSnapshot.getChildrenCount());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String que = ds.child("que").getValue(String.class);
                    String ans = ds.child("ans").getValue(String.class);
                    System.out.println(que + " " + ans);

                    questions.add(new Question(que, ans));

                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            // TODO: implement the ChildEventListener methods as documented above
            // ...
        });
        Random r = new Random();
        int Low = 0;
        int High = 5;
        int Result = r.nextInt(High - Low) + Low;
        index = Result;
        Question question = questions.get(Result);
        question_text.setText(question.getQue());


    }
}
