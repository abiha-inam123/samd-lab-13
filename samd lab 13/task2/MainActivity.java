package com.example.myapplication. 
import androidx.appcompat.app.AppCompatActivity; import android.accessibilityservice.GestureDescription; import android.app.Activity; import android.app.AlertDialog; import android.content.Context; import android.database.Cursor; 
import android.database.sqlite.SQLiteDatabase; import android.os.Bundle; import android.view.View; import android.widget.Button; import android.widget.EditText; public class MainActivity extends Activity implements View.OnClickListener 
{ 
 
    EditText editRollno, editName, editMarks; 
    Button btnAdd, btnDelete, btnModify, btnView, btnViewAll, btnShowInfo; 
    SQLiteDatabase db;     @Override 
    protected void onCreate(Bundle savedInstanceState) {         super.onCreate(savedInstanceState);         setContentView(R.layout.activity_main);         editRollno = (EditText) findViewById(R.id.enroll);         editName = (EditText) findViewById(R.id.name);         btnAdd = (Button) findViewById(R.id.add);         btnDelete = (Button) findViewById(R.id.delete);         btnModify = (Button) findViewById(R.id.modify);         btnView = (Button) findViewById(R.id.view);         btnAdd.setOnClickListener((View.OnClickListener) this);         btnDelete.setOnClickListener((View.OnClickListener) this);         btnModify.setOnClickListener((View.OnClickListener) this);         btnView.setOnClickListener((View.OnClickListener) this);         db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);         db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name 
                VARCHAR,marks VARCHAR);"); 
    } 
    public void onClick(View view) {         if (view == btnAdd) { 
            if (editRollno.getText().toString().trim().length() == 0 ||                     editName.getText().toString().trim().length() == 0 ||                     editMarks.getText().toString().trim().length() == 0) {                 showMessage("Error", "Please enter all values");                 return; 
            } 
            db.execSQL("INSERT INTO student VALUES('" + editRollno.getText() + "','" 
                    + editName.getText() + "','" + editMarks.getText() + 
"');"); 
            showMessage("Success", "Contact added");             clearText(); 
        } 
        if (view == btnDelete) { 
            if (editRollno.getText().toString().trim().length() == 0) {                 showMessage("Error", "Please enter Rollno");                 return; 
            } 
            Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" +                     editRollno.getText() + "'", null);             if (c.moveToFirst()) { 
                db.execSQL("DELETE FROM student WHERE rollno='" +                         editRollno.getText() + "'");                 showMessage("Success", "Contact Deleted"); 
            } else { 
                showMessage("Error", "Invalid Rollno"); 
            } 
            clearText(); 
        } 
        if (view == btnModify) { 
 
            if (editRollno.getText().toString().trim().length() == 0) {                 showMessage("Error", "Please enter Contact ID");                 return; 
            } 
            Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" +                     editRollno.getText() + "'", null);             if (c.moveToFirst()) { 
                db.execSQL("UPDATE student SET name='" + editName.getText() + 
                        "',marks='" + editMarks.getText() + "' WHERE rollno='" + editRollno.getText() + "'"); 
                showMessage("Success", "Contact Modified"); 
            } else { 
                showMessage("Error", "Invalid Contact"); 
            } 
            clearText(); 
        } 
        if (view == btnView) { 
            if (editRollno.getText().toString().trim().length() == 0) {                 showMessage("Error", "Please enter ContactID");                 return; 
            } 
            Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" +                     editRollno.getText() + "'", null);             if (c.moveToFirst()) { 
                editName.setText(c.getString(1));                 editMarks.setText(c.getString(2)); 
            } else { 
                showMessage("Error", "Invalid Rollno");                 clearText(); 
            } 
        }     } 
    public void showMessage(String title, String message) {         AlertDialog.Builder builder = new AlertDialog.Builder(this);         builder.setCancelable(true);         builder.setTitle(title);         builder.setMessage(message);         builder.show(); 
    } 
    public void clearText() {         editRollno.setText("");         editName.setText("");         editMarks.setText("");         editRollno.requestFocus(); 
    } 
} 
