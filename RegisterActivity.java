package nl.hhs.apep2122group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import nl.hhs.apep2122group1.R;
import nl.hhs.apep2122group1.database.FileDatabase;
import nl.hhs.apep2122group1.models.User;
import nl.hhs.apep2122group1.utils.ValidationResult;
import nl.hhs.apep2122group1.utils.Validators;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onNavigateToLogin(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // set focus to name field for convenience
        TextInputEditText nameField = findViewById(R.id.name_et);
        nameField.requestFocus();
    }

    public void onRegister(View view) {
        // get fields
        TextInputEditText nameField = findViewById(R.id.name_et);
        TextInputEditText usernameField = findViewById(R.id.username_et);
        TextInputEditText passwordField = findViewById(R.id.password_et);
        TextInputEditText retypePasswordField = findViewById(R.id.retype_password_et);

        // get values from fields
        String name = nameField.getText() != null ? nameField.getText().toString() : null;
        String username = usernameField.getText() != null ? usernameField.getText().toString() : null;
        String password = passwordField.getText() != null ? passwordField.getText().toString() : null;
        String retypePassword = retypePasswordField.getText() != null ? retypePasswordField.getText().toString() : null;

        // validate values
        boolean error = false;
        if (!Validators.validateStringNotNullOrEmpty(name)) {
            nameField.setError(getResources().getString(R.string.validation_field_empty));
            error = true;
        }
        if (!Validators.validateStringNotNullOrEmpty(username)) {
            usernameField.setError(getResources().getString(R.string.validation_field_empty));
            error = true;
        } else if (!Validators.validateStringDoesNotContainWhitespace(username)) {
            usernameField.setError(getResources().getString(R.string.register_validation_username_cannot_contain_whitespace));
            error = true;
        }
        if (!Validators.validateStringNotNullOrEmpty(password)) {
            passwordField.setError(getResources().getString(R.string.validation_field_empty));
            error = true;
        } else if (Validators.validatePasswordComplexity(password) != ValidationResult.OK) {
            ValidationResult validatorResult = Validators.validatePasswordComplexity(password);
            passwordField.setError(getPasswordValidationError(validatorResult));
            error = true;
        } else if (!password.equals(retypePassword)) {
            passwordField.setError(getResources().getString(R.string.register_validation_passwords_not_equal));
            retypePasswordField.setError(getResources().getString(R.string.register_validation_passwords_not_equal));
            error = true;
        }
        if (error) {
            return;
        }

        // values ok, save to DB
        User newUser = new User(name.trim(), username, password);
        if (!FileDatabase.getDatabase(this).insertUser(newUser)) {
            usernameField.setError(getResources().getString(R.string.register_validation_username_in_use));
        } else {
            Toast.makeText(this, R.string.register_registered_toast, Toast.LENGTH_SHORT).show();
            finish();

            Intent intent = new Intent(this, TaskListActivity.class);
            intent.putExtra("USERNAME", username);
            this.startActivity(intent);
        }
    }

    private String getPasswordValidationError(ValidationResult validatorResult) {
        switch (validatorResult) {
            case TOO_SHORT:
                return getResources().getString(R.string.register_validation_password_too_short);
            case SAME_CHARACTERS:
                return getResources().getString(R.string.register_validation_password_too_simple);
            case SPECIFIC_INPUT_NOT_ALLOWED:
                return getResources().getString(R.string.register_validation_password_is_known_string);
        }
        return null;
    }
}