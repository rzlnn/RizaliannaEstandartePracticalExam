package estandartee.rizalianna.exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference root;
    EditText Firstname,Surname,FirstGrade,SecondGrade;
    TextView Average;
    ArrayList<String> keyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        root = db.getReference("grade");
        Firstname = findViewById(R.id.firstname);
        Surname = findViewById(R.id.surname);
        FirstGrade = findViewById(R.id.firstgrade);
        SecondGrade = findViewById(R.id.secondgrade);
        Average = findViewById(R.id.average);
        keyList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ss: dataSnapshot.getChildren()) {
                    keyList.add(ss.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addRecord(View v) {
        String fname = Firstname.getText().toString().trim();
        String lname = Surname.getText().toString().trim();
        Long exam1 = Long.parseLong(FirstGrade.getText().toString().trim());
        Long exam2 = Long.parseLong(SecondGrade.getText().toString().trim());
        Long ave = (exam1 + exam2) / 2;

        Student sgrade = new Student(fname,lname,ave);
        String key = root.push().getKey();
        root.child(key).setValue(sgrade);
        keyList.add(key);

        Average.setText(ave.toString());
        Toast.makeText(this,"The Grade of the student was added to Firebase",Toast.LENGTH_LONG).show();
    }
}