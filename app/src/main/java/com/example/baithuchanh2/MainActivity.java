package com.example.baithuchanh2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalAmount;
    private ListView lvExpenses;
    private Button btnAdd;
    private ArrayList<Expense> expenseList;
    private ExpenseAdapter adapter;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    if (data.hasExtra("NEW_EXPENSE")) {
                        Expense newExpense = (Expense) data.getSerializableExtra("NEW_EXPENSE");
                        expenseList.add(newExpense);
                    } else if (data.hasExtra("DELETE_INDEX")) {
                        int index = data.getIntExtra("DELETE_INDEX", -1);
                        if (index != -1 && index < expenseList.size()) {
                            expenseList.remove(index);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    calculateTotal();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        lvExpenses = findViewById(R.id.lvExpenses);
        btnAdd = findViewById(R.id.btnAdd);

        expenseList = new ArrayList<>();
        expenseList.add(new Expense("Ăn sáng", 30000, "Ăn uống", "12/10/2026", ""));
        expenseList.add(new Expense("Đổ xăng", 50000, "Đi lại", "12/10/2026", ""));
        expenseList.add(new Expense("Mua sách", 120000, "Học tập", "13/10/2026", "Sách lập trình Android"));

        adapter = new ExpenseAdapter(this, expenseList);
        lvExpenses.setAdapter(adapter);
        calculateTotal();

        btnAdd.setOnClickListener(v -> launcher.launch(new Intent(MainActivity.this, AddExpenseActivity.class)));

        lvExpenses.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            intent.putExtra("EXPENSE_DATA", expenseList.get(position));
            intent.putExtra("EXPENSE_INDEX", position);
            launcher.launch(intent);
        });
    }

    private void calculateTotal() {
        long total = 0;
        for (Expense e : expenseList) total += e.getSoTien();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("vi-VN"));
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        tvTotalAmount.setText(formatter.format(total) + " đ");
    }
}
