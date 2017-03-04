package com.example.participatorysensing.firstpage;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by ying on 2016/5/22.
 */
public class IntegerValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;

    public IntegerValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0"); //
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here

        return mFormat.format(value); // e.g. append a dollar-sign
    }
}
