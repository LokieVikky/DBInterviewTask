package com.test.doodleblue;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class IncDecView extends LinearLayout {
    int MAX_COUNT = 99;
    int COUNT;
    OnValueChangedListener onValueChangedListener;
    LinearLayout layIncDec;
    Button btnAdd;
    TextView txt;


    public IncDecView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public int getMaxCount() {
        return MAX_COUNT;
    }

    public void setMaxCount(int maxCount) {
        MAX_COUNT = maxCount;
    }

    public int getCount() {
        return COUNT;
    }

    public void setCount(int count) {
        COUNT = count;
        txt.setText(String.valueOf(COUNT));
        updateView();
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    void init(AttributeSet attrs) {
        View v = inflate(getContext(), R.layout.view_increment_decrement_button, this);
        ImageView inc = v.findViewById(R.id.imgInc);
        ImageView dec = v.findViewById(R.id.imgDec);
        txt = v.findViewById(R.id.txtValue);
        btnAdd = v.findViewById(R.id.btnAdd);
        layIncDec = v.findViewById(R.id.layIncDec);

        inc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                COUNT++;
                if (COUNT >= MAX_COUNT) {
                    COUNT = MAX_COUNT;
                }
                txt.setText(String.valueOf(COUNT));
                updateCountThroughCallback();
                updateView();

            }
        });
        dec.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                COUNT--;
                if (COUNT < 0) {
                    COUNT = 0;
                }
                txt.setText(String.valueOf(COUNT));
                updateCountThroughCallback();
                updateView();
            }
        });
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd.setVisibility(GONE);
                layIncDec.setVisibility(VISIBLE);
                COUNT++;
                txt.setText(String.valueOf(COUNT));
                updateCountThroughCallback();
            }
        });
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IncDecView);
            try {
                COUNT = typedArray.getInteger(R.styleable.IncDecView_count, 0);
                MAX_COUNT = typedArray.getInteger(R.styleable.IncDecView_maxCount, 99);
            } finally {
                typedArray.recycle();
            }
        }

    }

    void updateCountThroughCallback() {
        if (this.onValueChangedListener != null) {
            this.onValueChangedListener.OnValueChanged(COUNT);
        }
    }
    void updateView(){
        if (COUNT == 0) {
            btnAdd.setVisibility(VISIBLE);
            layIncDec.setVisibility(GONE);
        }else {
            btnAdd.setVisibility(GONE);
            layIncDec.setVisibility(VISIBLE);
        }
    }

    interface OnValueChangedListener {
        void OnValueChanged(int value);
    }

}
