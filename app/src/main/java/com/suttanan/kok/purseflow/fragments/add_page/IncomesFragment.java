package com.suttanan.kok.purseflow.fragments.add_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.suttanan.kok.purseflow.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KOKKOK on 5/8/2016.
 */
public class IncomesFragment extends Fragment {


    @BindView(R.id.adding_incomes_value)  TextView valueTextView;

    @BindView(R.id.adding_incomes_saralyBtn)  Button saralyBtn;
    @BindView(R.id.adding_incomes_bussinessBtn) Button bussinessBtn;
    @BindView(R.id.adding_incomes_giftBtn) Button giftBtn;
    @BindView(R.id.adding_incomes_extraBtn) Button extraBtn;

    @BindView(R.id.adding_incomes_0Btn) Button num0Btn;
    @BindView(R.id.adding_incomes_1Btn) Button num1Btn;
    @BindView(R.id.adding_incomes_2Btn) Button num2Btn;
    @BindView(R.id.adding_incomes_3Btn) Button num3Btn;
    @BindView(R.id.adding_incomes_4Btn) Button num4Btn;
    @BindView(R.id.adding_incomes_5Btn) Button num5Btn;
    @BindView(R.id.adding_incomes_6Btn) Button num6Btn;
    @BindView(R.id.adding_incomes_7Btn) Button num7Btn;
    @BindView(R.id.adding_incomes_8Btn) Button num8Btn;
    @BindView(R.id.adding_incomes_9Btn) Button num9Btn;

    @BindView(R.id.adding_incomes_dotBtn) Button dotBtn;
    @BindView(R.id.adding_incomes_enterBtn) Button enterBtn;
    @BindView(R.id.adding_incomes_delBtn) Button delBtn;

    private String value;
    private String category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adding_income_layout, container, false);
        ButterKnife.bind(this, v);
        value = "";
        valueTextView.setText("0.0");

        return v;
    }

    @OnClick({ R.id.adding_incomes_saralyBtn, R.id.adding_incomes_bussinessBtn,
            R.id.adding_incomes_giftBtn, R.id.adding_incomes_extraBtn })
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
    public void InputNumber(Button button){
        value += button.getText().toString();
        valueTextView.setText(value);
        Toast.makeText(this.getContext(), value, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.adding_incomes_delBtn)
    public void DeleteNumber(Button button){
        if(value.length() > 0){
            value = value.substring(0, value.length()-1);
            valueTextView.setText(value);
        } else{
            valueTextView.setText("0.0");
        }
    }
}
