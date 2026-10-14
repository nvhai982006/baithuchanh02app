package com.example.baithuchanh2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ExpenseDetailActivity extends AppCompatActivity {
    private TextView tvName, tvAmount, tvCategory, tvDate, tvNote;
    private Button btnBack, btnDelete;
    private int expenseIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        View btnDetailHeaderBack = findViewById(R.id.btnDetailHeaderBack);
        if (btnDetailHeaderBack != null) {
            btnDetailHeaderBack.setOnClickListener(v -> finish());
        }

        tvName = findViewById(R.id.tvDetailName);
        tvAmount = findViewById(R.id.tvDetailAmount);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvDate = findViewById(R.id.tvDetailDate);
        tvNote = findViewById(R.id.tvDetailNote);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("EXPENSE_DATA")) {
            Expense expense = (Expense) intent.getSerializableExtra("EXPENSE_DATA");
            expenseIndex = intent.getIntExtra("EXPENSE_INDEX", -1);

            if (expense != null) {
                tvName.setText(expense.getTenKhoanChi());

                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("vi-VN"));
                symbols.setGroupingSeparator('.');
                DecimalFormat formatter = new DecimalFormat("#,###", symbols);
                tvAmount.setText(formatter.format(expense.getSoTien()) + " đ");

                tvCategory.setText(expense.getDanhMuc());
                tvDate.setText(expense.getNgayChiTieu());
                tvNote.setText(expense.getGhiChu());
            }
        }

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("DELETE_INDEX", expenseIndex);
            setResult(RESULT_OK, returnIntent);
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
