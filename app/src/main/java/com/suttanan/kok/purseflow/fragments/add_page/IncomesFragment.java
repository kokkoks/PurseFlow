package com.suttanan.kok.purseflow.fragments.add_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.activities.AddingDescriptionActivity;
import com.suttanan.kok.purseflow.others.TransactionType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KOKKOK on 5/8/2016.
 */
public class IncomesFragment extends Fragment {


    @BindView(R.id.adding_incomes_value)
    TextView valueTextView;

    @BindView(R.id.adding_incomes_salaryBtn)
    Button salaryBtn;
    @BindView(R.id.adding_incomes_bussinessBtn)
    Button bussinessBtn;
    @BindView(R.id.adding_incomes_giftBtn)
    Button giftBtn;
    @BindView(R.id.adding_incomes_extraBtn)
    Button extraBtn;

    @BindView(R.id.adding_incomes_0Btn)
    Button num0Btn;
    @BindView(R.id.adding_incomes_1Btn)
    Button num1Btn;
    @BindView(R.id.adding_incomes_2Btn)
    Button num2Btn;
    @BindView(R.id.adding_incomes_3Btn)
    Button num3Btn;
    @BindView(R.id.adding_incomes_4Btn)
    Button num4Btn;
    @BindView(R.id.adding_incomes_5Btn)
    Button num5Btn;
    @BindView(R.id.adding_incomes_6Btn)
    Button num6Btn;
    @BindView(R.id.adding_incomes_7Btn)
    Button num7Btn;
    @BindView(R.id.adding_incomes_8Btn)
    Button num8Btn;
    @BindView(R.id.adding_incomes_9Btn)
    Button num9Btn;

    @BindView(R.id.adding_incomes_dotBtn)
    Button dotBtn;
    @BindView(R.id.adding_incomes_enterBtn)
    Button enterBtn;
    @BindView(R.id.adding_incomes_delBtn)
    ImageButton delBtn;

    private String value;
    private String category;
    private String[] transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adding_income_layout, container, false);
        ButterKnife.bind(this, v);

        initComponents();

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.length() > 0) {
                    value = value.substring(0, value.length() - 1);
                    valueTextView.setText(value);
                } else {
                    valueTextView.setText("0.0");
                }
            }
        });
        return v;
    }

    private void initComponents() {
        transaction = new String[5];
        value = "";
        valueTextView.setText("0.0");
    }

    @OnClick({R.id.adding_incomes_salaryBtn, R.id.adding_incomes_bussinessBtn,
            R.id.adding_incomes_giftBtn, R.id.adding_incomes_extraBtn})
    public void SelectCategory(Button button) {
        String text = button.getText().toString();
        category = text;
        Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.adding_incomes_0Btn, R.id.adding_incomes_1Btn,
            R.id.adding_incomes_2Btn, R.id.adding_incomes_3Btn,
            R.id.adding_incomes_4Btn, R.id.adding_incomes_5Btn,
            R.id.adding_incomes_6Btn, R.id.adding_incomes_7Btn,
            R.id.adding_incomes_8Btn, R.id.adding_incomes_9Btn})
    public void inputNumber(Button button) {
        String[] text = value.split("\\.");
        if(text.length == 1 ) {
            if (text[0].length() < 10) {
                value += button.getText().toString();
            }
        } else {
            if(text[1].length() < 2){
                if(button.getText().equals("0")){
                    if(text[1].length() == 1){
                        return;
                    }
                }
                value += button.getText().toString();
            }
        }

        valueTextView.setText(value);
    }

    @OnClick(R.id.adding_incomes_dotBtn)
    public void inputDot(Button button){
        String[] text = value.split("\\.");
        if(text.length == 1){
            if(!value.contains(".")) {
                value += ".";
            }
        } else if(text[1].length() == 0){
            valueTextView.setText(value + "0");
        }
    }

//    @OnClick(R.id.adding_incomes_delBtn)
//    public void deleteNumber(Button button) {
//        if (value.length() > 0) {
//            value = value.substring(0, value.length() - 1);
//            valueTextView.setText(value);
//        } else {
//            valueTextView.setText("0.0");
//        }
//    }

    @OnClick(R.id.adding_incomes_enterBtn)
    public void enterNextPage(Button button) {
        if (category != null) {
            if(value.equals("")) {
                value = "0";
            }
            transaction[1] = String.valueOf(TransactionType.INCOMES);
            transaction[2] = value;
            transaction[3] = category;
            Intent intent = new Intent(this.getContext(), AddingDescriptionActivity.class);
            intent.putExtra("transaction", transaction);
            startActivity(intent);
        } else {
            Toast.makeText(this.getContext(), "Please select category", Toast.LENGTH_LONG).show();
        }
    }
}
