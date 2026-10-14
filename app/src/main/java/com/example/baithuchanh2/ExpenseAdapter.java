package com.example.baithuchanh2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        }
        Expense expense = getItem(position);

        if (expense != null) {
            TextView tvName = convertView.findViewById(R.id.tvItemName);
            TextView tvCategory = convertView.findViewById(R.id.tvItemCategory);
            TextView tvAmount = convertView.findViewById(R.id.tvItemAmount);
            TextView tvDate = convertView.findViewById(R.id.tvItemDate);

            tvName.setText(expense.getTenKhoanChi());
            tvCategory.setText(expense.getDanhMuc());

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("vi-VN"));
            symbols.setGroupingSeparator('.');
            DecimalFormat formatter = new DecimalFormat("#,###", symbols);
            tvAmount.setText(formatter.format(expense.getSoTien()) + " đ");
            tvDate.setText(expense.getNgayChiTieu());
        }

        return convertView;
    }
}
