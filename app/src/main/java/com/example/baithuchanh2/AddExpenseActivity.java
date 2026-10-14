package com.example.baithuchanh2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText edtName, edtAmount, edtNote;
    private Spinner spnCategory;
    private DatePicker datePicker;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = Locale.forLanguageTag("vi-VN");
        Locale.setDefault(locale);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_add_expense);

        View btnHeaderBack = findViewById(R.id.btnHeaderBack);
        if (btnHeaderBack != null) {
            btnHeaderBack.setOnClickListener(v -> finish());
        }

        edtName = findViewById(R.id.edtName);
        edtAmount = findViewById(R.id.edtAmount);
        spnCategory = findViewById(R.id.spnCategory);
        datePicker = findViewById(R.id.datePicker);
        edtNote = findViewById(R.id.edtNote);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String amountStr = edtAmount.getText().toString().trim();

            if (name.isEmpty()) {
                edtName.setError(getString(R.string.err_empty_name));
                edtName.requestFocus();
                Toast.makeText(this, getString(R.string.err_empty_name), Toast.LENGTH_SHORT).show();
                return;
            }

            if (amountStr.isEmpty()) {
                edtAmount.setError(getString(R.string.err_empty_amount));
                edtAmount.requestFocus();
                Toast.makeText(this, getString(R.string.err_empty_amount), Toast.LENGTH_SHORT).show();
                return;
            }

            long amount;
            try {
                amount = Long.parseLong(amountStr);
            } catch (NumberFormatException e) {
                edtAmount.setError(getString(R.string.err_invalid_amount));
                edtAmount.requestFocus();
                Toast.makeText(this, getString(R.string.err_invalid_amount), Toast.LENGTH_SHORT).show();
                return;
            }

            if (amount <= 0) {
                edtAmount.setError(getString(R.string.err_amount_gt_zero));
                edtAmount.requestFocus();
                Toast.makeText(this, getString(R.string.err_amount_gt_zero), Toast.LENGTH_SHORT).show();
                return;
            }

            String category = spnCategory.getSelectedItem() != null ? spnCategory.getSelectedItem().toString() : "Khác";
            String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
            String note = edtNote.getText().toString();

            Expense newExpense = new Expense(name, amount, category, date, note);

            Intent intent = new Intent();
            intent.putExtra("NEW_EXPENSE", newExpense);
            setResult(RESULT_OK, intent);
            Toast.makeText(this, getString(R.string.msg_save_success), Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
