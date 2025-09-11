package com.blueapps.maat.bounds;

import static com.blueapps.maat.BoundCalculation.STANDARD_HEIGHT_VALUE;
import static com.blueapps.maat.BoundCalculation.STANDARD_WIDTH_VALUE;
import static com.blueapps.maat.BoundCalculation.XML_ID_ATTRIBUTE;
import static com.blueapps.maat.BoundProperty.VERTICAL_ORIENTATION_DOWN;
import static com.blueapps.maat.BoundProperty.VERTICAL_ORIENTATION_MIDDLE;
import static com.blueapps.maat.BoundProperty.VERTICAL_ORIENTATION_UP;
import static com.blueapps.maat.BoundProperty.WRITING_LAYOUT_COLUMNS;
import static com.blueapps.maat.BoundProperty.WRITING_LAYOUT_LINES;

import android.graphics.Rect;
import android.util.Log;

import com.blueapps.maat.BoundProperty;
import com.blueapps.maat.ValuePair;

import org.w3c.dom.Element;

import java.util.ArrayList;

public class SimpleBound extends Bound{

    private static final String TAG = "SimpleBound";

    private String id;

    public SimpleBound(Element element) {
        super(element);
        id = element.getAttribute(XML_ID_ATTRIBUTE);
    }

    @Override
    public Rect getBound(BoundProperty property, ArrayList<ValuePair<Float, Float>> dimensions) {
        this.property = property;
        this.dimensions = dimensions;

        Rect bound;

        float boundWidth = dimensions.get(0).getKey();
        float boundHeight = dimensions.get(0).getValue();
        float y = 0;
        float x = 0;

        float tempHeight;
        float tempWidth;

        float newHeight;
        float newWidth;

        int returnHeight;
        int returnWidth;

        if (boundHeight >= STANDARD_HEIGHT_VALUE) {
            tempHeight = STANDARD_HEIGHT_VALUE;
            tempWidth = (boundWidth / boundHeight) * tempHeight;
        } else {
            tempHeight = boundHeight;
            tempWidth = boundWidth;
        }
        if (tempWidth >= STANDARD_WIDTH_VALUE){
            newWidth = STANDARD_WIDTH_VALUE;
            newHeight = (tempHeight / tempWidth) * newWidth;
        } else {
            newHeight = tempHeight;
            newWidth = tempWidth;
        }

        if (property.getWritingLayout() == WRITING_LAYOUT_LINES) {

            returnHeight = (int) ((newHeight / STANDARD_HEIGHT_VALUE) * property.getTextSize());
            returnWidth = (int) ((newWidth / newHeight) * returnHeight);

            float bottomBorder = property.getTextSize() - returnHeight;

            if (boundHeight < STANDARD_HEIGHT_VALUE) {
                if (property.getVerticalOrientation() == VERTICAL_ORIENTATION_MIDDLE) {
                    y = (bottomBorder / 2) + y;
                } else if (property.getVerticalOrientation() == VERTICAL_ORIENTATION_DOWN) {
                    y = bottomBorder + y;
                }
            }
        } else {

            returnWidth = (int) ((newWidth / STANDARD_WIDTH_VALUE) * property.getTextSize());
            returnHeight = (int) ((newHeight / newWidth) * returnWidth);

            float bottomBorder = property.getTextSize() - returnWidth;

            if (boundWidth < STANDARD_WIDTH_VALUE) {
                x = (bottomBorder / 2) + x;
            }
        }

        Log.i(TAG, "New values: \nNew Width = " + returnWidth + "\nNew Height = " + returnHeight);

        bound = new Rect(
                (int) x,
                (int) y,
                (int) (returnWidth + x),
                (int) (returnHeight + y));

        width = returnWidth;
        height = returnHeight;

        return bound;
    }

    @Override
    public ArrayList<String> getIds() {
        ArrayList<String> returnArray = new ArrayList<>();
        returnArray.add(id);
        return returnArray;
    }

    @Override
    public float getWidth() {
        if (property.getWritingLayout() == WRITING_LAYOUT_COLUMNS) {
            return property.getTextSize();
        } else {
            return width;
        }
    }

    @Override
    public float getHeight() {
        if (property.getWritingLayout() == WRITING_LAYOUT_LINES) {
            return property.getTextSize();
        } else {
            return height;
        }
    }
}
