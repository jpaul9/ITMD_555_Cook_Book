package com.example.cook_book;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    TextInputLayout first_name, last_name, email_address, pass_w;
    Button register, login;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_registration);

        first_name = findViewById(R.id.fname);
        last_name = findViewById(R.id.lname);
        email_address = findViewById(R.id.email);
        pass_w = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        login.setOnClickListener(v -> {

            Intent intent = new Intent (Registration.this,Login_options.class);

            startActivity(intent);

            finish();

        });
    }

    //input validation for each field
    private Boolean validate_fname(){
        String val = first_name.getEditText().getText().toString();
        String nowhitespaces = "(\\S*)";
        if(val.isEmpty()){
            first_name.setError("Name is required");
            return false;
        }
        if (!val.matches(nowhitespaces)){
            first_name.setError("Please remove any spaces");
            return false;
        }
        else{
            first_name.setError(null);
            first_name.setErrorEnabled(false);
            return true;

        }
    }
    private Boolean validate_lname(){
        String val = last_name.getEditText().getText().toString();
        String nowhitespaces = "(\\S*)";

        if(val.isEmpty()){
            last_name.setError("Last Name is required");
            return false;
        }
        else if (!val.matches(nowhitespaces)){
            last_name.setError("Please remove any spaces");
            return false;
        }
        else{
            last_name.setError(null);
            last_name.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validate_email(){
        String val = email_address.getEditText().getText().toString();
        String nowhitespaces = "(\\S*)";
        String emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if(val.isEmpty()){
            email_address.setError("Email address is required");
            return false;
        }
        else if (!val.matches(nowhitespaces)){
            email_address.setError("Please remove any spaces");
            return false;
        }
        else if (!val.matches(emailPattern)){
            email_address.setError("Invalid email address");
            return false;

        }
        else{
            email_address.setError(null);
            email_address.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validate_password(){
        String val = pass_w.getEditText().getText().toString();
        String passwordspecialchar = "^" //represents starting character of the string.
                                    +"(?=.*[a-zA-Z])"// represents any letter
                                    + "(?=.*[@#$%^&+=])"//represents a special character that must occur at least once.
                                    + "(?=\\S+$)"//white spaces don’t allowed in the entire string.
                                    +"$"; //represents the end of the string.
        String password_Length = "^" //represents starting character of the string.
                                    +"(?=.*[a-zA-Z])"// represents any letter
                                    + "(?=\\S+$)"//white spaces don’t allowed in the entire string.
                                    + ".{8,20}" // represents at least 8 characters and at most 20 characters.
                                    +"$"; //represents the end of the string.
        String passwordValidation = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        if(val.isEmpty()){
            pass_w.setError("Password is required");
            return false;
        }
        else if(!val.matches(passwordValidation)){
               pass_w.setError("Password: 8 to 20 long. Special characters required");
               return false;}
        else{
            pass_w.setError(null);
            pass_w.setErrorEnabled(false);
            return true;
        }
    }




    //save data to firebase
    public void register_user(View view){
        if (!validate_fname() | !validate_lname() | !validate_email() | !validate_password()){
            return;
        }
        //Get values from the registration form and pass it to firebase
        String firstname = first_name.getEditText().getText().toString();
        String lastname =last_name .getEditText().getText().toString();
        String email = email_address.getEditText().getText().toString();
        String password = pass_w.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(firstname,lastname,email,password);

        reference.child(firstname +" "+ lastname).setValue(helperClass);
    }
}