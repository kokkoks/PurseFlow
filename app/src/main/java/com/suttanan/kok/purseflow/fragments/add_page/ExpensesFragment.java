package com.suttanan.kok.purseflow.fragments.add_page;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KOKKOK on 5/8/2016.
 */
public class ExpensesFragment extends Fragment{
    @BindView(R.id.adding_expenses_value) TextView valueTextView;

    @BindView(R.id.adding_expenses_foodBtn) Button foodBtn;
    @BindView(R.id.adding_expenses_shopBtn) Button shopBtn;
    @BindView(R.id.adding_expenses_familyBtn) Button familyBtn;
    @BindView(R.id.adding_expenses_travelBtn) Button travelBtn;
    @BindView(R.id.adding_expenses_entertainBtn) Button entertainBtn;
    @BindView(R.id.adding_expenses_homeBtn) Button homeBtn;
    @BindView(R.id.adding_expenses_healthBtn) Button healthBtn;
    @BindView(R.id.adding_expenses_otherBtn) Button otherBtn;

    @BindView(R.id.adding_expenses_0Btn) Button num0Btn;
    @BindView(R.id.adding_expenses_1Btn) Button num1Btn;
    @BindView(R.id.adding_expenses_2Btn) Button num2Btn;
    @BindView(R.id.adding_expenses_3Btn) Button num3Btn;
    @BindView(R.id.adding_expenses_4Btn) Button num4Btn;
    @BindView(R.id.adding_expenses_5Btn) Button num5Btn;
    @BindView(R.id.adding_expenses_6Btn) Button num6Btn;
    @BindView(R.id.adding_expenses_7Btn) Button num7Btn;
    @BindView(R.id.adding_expenses_8Btn) Button num8Btn;
    @BindView(R.id.adding_expenses_9Btn) Button num9Btn;

    @BindView(R.id.adding_expenses_dotBtn) Button dotBtn;
    @BindView(R.id.adding_expenses_enterBtn) Button enterBtn;
    @BindView(R.id.adding_expenses_delBtn) ImageButton delBtn;

    private String value;
    private String category;

    /*
    0 = user
    1 = date
    2 = Type (expenses or incomes)
    3 = value
    4 = category
    5 = description
    */
    private String[] transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adding_expense_layout, container, false);
        ButterKnife.bind(this, v);

        initComponents();

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value.length() > 0){
                    value = value.substring(0, value.length()-1);
                    if(value.length() == 0){
                        value = "0";
                    }
                    valueTextView.setText(value);
                } else{
                    value = "0";
                    valueTextView.setText(value);
                }
            }
        });
        return v;
    }

    private void initComponents(){
        transaction = new String[5];
        value = "0";
        valueTextView.setText(value);

//        addBackgroundResource();
        removeBackgroundResource();
    }

    private void addBackgroundResource() {
        foodBtn.setBackgroundResource(R.drawable.button_category);
        shopBtn.setBackgroundResource(R.drawable.button_category);
        familyBtn.setBackgroundResource(R.drawable.button_category);
        travelBtn.setBackgroundResource(R.drawable.button_category);
        entertainBtn.setBackgroundResource(R.drawable.button_category);
        homeBtn.setBackgroundResource(R.drawable.button_category);
        healthBtn.setBackgroundResource(R.drawable.button_category);
        otherBtn.setBackgroundResource(R.drawable.button_category);
    }

    @OnClick({R.id.adding_expenses_foodBtn, R.id.adding_expenses_shopBtn,
            R.id.adding_expenses_familyBtn, R.id.adding_expenses_travelBtn,
            R.id.adding_expenses_entertainBtn, R.id.adding_expenses_homeBtn,
            R.id.adding_expenses_healthBtn, R.id.adding_expenses_otherBtn})
    public void selectCategory(Button button){
        String text = button.getText().toString();
        category = text;
        removeBackgroundResource();
        button.setBackgroundResource(R.drawable.button_category);
        Toast.makeText(this.getContext(), category, Toast.LENGTH_SHORT).show();
    }

    private void removeBackgroundResource() {
        foodBtn.setBackgroundResource(0);
        shopBtn.setBackgroundResource(0);
        familyBtn.setBackgroundResource(0);
        travelBtn.setBackgroundResource(0);
        entertainBtn.setBackgroundResource(0);
        homeBtn.setBackgroundResource(0);
        healthBtn.setBackgroundResource(0);
        otherBtn.setBackgroundResource(0);
    }

    @OnClick({R.id.adding_expenses_0Btn, R.id.adding_expenses_1Btn,
            R.id.adding_expenses_2Btn, R.id.adding_expenses_3Btn,
            R.id.adding_expenses_4Btn, R.id.adding_expenses_5Btn,
            R.id.adding_expenses_6Btn, R.id.adding_expenses_7Btn,
            R.id.adding_expenses_8Btn, R.id.adding_expenses_9Btn})
    public void inputNumber(Button button){
        String[] text = value.split("\\.");
//        if(text[0].equals("") && button.getText().toString().equals("0")){
//            return;
//        }
//        if(text.length == 1 ) {
//            if (text[0].length() < 10) {
//                value += button.getText().toString();
//            }
//        } else {
//            if(text[1].length() < 2){
//                if(button.getText().equals("0")){
//                    if(text[1].length() == 1){
//                        return;
//                    }
//                }
//                value += button.getText().toString();
//            }
//        }
//        valueTextView.setText(value);

//        Toast.makeText(this.getContext(), value, Toast.LENGTH_SHORT).show();

        if(text[0].length() > 10){
            return;
        }
        if(value.equals("0")){
            if(button.getText().toString().equals("0")){
            }else {
                value = button.getText().toString();
            }
        } else {
            if(text.length ==1) {
                value += button.getText().toString();
            } else {
                if(text[1].length() < 2){
                    value += button.getText().toString();
                }
            }
        }

        valueTextView.setText(value);
        Toast.makeText(this.getContext(), value, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.adding_expenses_dotBtn)
    public void inputDot(Button button){
        String[] text = value.split("\\.");
//        if(text.length == 1){
//            if(!value.contains(".")) {
//                value += ".";
//            }
//        } else if(text[1].length() == 0){
//            valueTextView.setText(value + "0");
//        }
        if(value.contains(".")){
            return;
        }
        if(text.length ==1){
            value +=".";
            valueTextView.setText(value + "0");
        }
    }

//    @OnClick(R.id.adding_expenses_delBtn)
//    public void deleteNumber(Button button){
//        if(value.length() > 0){
//            value = value.substring(0, value.length()-1);
//            valueTextView.setText(value);
//        } else{
//            valueTextView.setText("0.00");
//        }
//    }

    @OnClick(R.id.adding_expenses_enterBtn)
    public void enterNextPage(Button button){
        String[] text = value.split("\\.");
        if(category != null ){
            if(Float.parseFloat(value) != 0) {
                if (value.contains(".") && text.length == 1) {
                    value += "0";
                }
                transaction[1] = String.valueOf(TransactionType.EXPENSES);
                transaction[2] = value;
                transaction[3] = category;
                Intent intent = new Intent(this.getContext(), AddingDescriptionActivity.class);
                intent.putExtra("transaction", transaction);
                startActivity(intent);
            } else {
                Toast.makeText(this.getContext(), "Input invalid number", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this.getContext(), "Please select category", Toast.LENGTH_LONG).show();
        }

    }
}
