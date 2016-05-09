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
public class ExpensesFragment extends Fragment{
    @BindView(R.id.adding_expenses_value) TextView valueTextVIew;

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
    @BindView(R.id.adding_expenses_delBtn) Button delBtn;

    private String value;
    private String category;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adding_expense_layout, container, false);
        ButterKnife.bind(this, v);
        value = "";
        valueTextVIew.setText("0.0");

        return v;
    }

    @OnClick({R.id.adding_expenses_foodBtn, R.id.adding_expenses_shopBtn,
            R.id.adding_expenses_familyBtn, R.id.adding_expenses_travelBtn,
            R.id.adding_expenses_entertainBtn, R.id.adding_expenses_homeBtn,
            R.id.adding_expenses_healthBtn, R.id.adding_expenses_otherBtn})
    public void SelectCategory(Button button){
        String text = button.getText().toString();
        category = text;
        Toast.makeText(this.getContext(), category, Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.adding_expenses_0Btn, R.id.adding_expenses_1Btn,
            R.id.adding_expenses_2Btn, R.id.adding_expenses_3Btn,
            R.id.adding_expenses_4Btn, R.id.adding_expenses_5Btn,
            R.id.adding_expenses_6Btn, R.id.adding_expenses_7Btn,
            R.id.adding_expenses_8Btn, R.id.adding_expenses_9Btn})
    public void InputNumber(Button button){
        value += button.getText().toString();
        valueTextVIew.setText(value);
        Toast.makeText(this.getContext(), value, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.adding_expenses_delBtn)
    public void DeleteNumber(Button button){
        if(value.length() > 0){
            value = value.substring(0, value.length()-1);
            valueTextVIew.setText(value);
        } else{
            valueTextVIew.setText("0.0");
        }
    }
}
