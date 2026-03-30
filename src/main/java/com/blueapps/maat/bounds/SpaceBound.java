package com.blueapps.maat.bounds;

import static com.blueapps.maat.BoundCalculation.STANDARD_HEIGHT_VALUE;
import static com.blueapps.maat.BoundCalculation.STANDARD_WIDTH_VALUE;
import static com.blueapps.maat.BoundCalculation.XML_GAP_TAG;

import android.graphics.Rect;

import com.blueapps.maat.BoundProperty;
import com.blueapps.maat.ValuePair;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Objects;

public class SpaceBound extends Bound{

    private boolean large;

    public SpaceBound(Element element) {
        super(element);
        large = Objects.equals(element.getTagName(), XML_GAP_TAG);
    }

    @Override
    public Rect getBound(BoundProperty property, ArrayList<ValuePair<Float, Float>> dimensions) {
        super.getBound(property, dimensions);

        Rect bound = new Rect(0, 0, 0, 0);
        if (large){
            bound.right = (int) property.getTextSize();
            bound.bottom = (int) property.getTextSize();
        } else {
            bound.right = (int) property.getTextSize() / 2;
            bound.bottom = (int) property.getTextSize() / 2;
        }
        return bound;
    }

    @Override
    public int getSignCount() {
        return 0;
    }

    @Override
    public float getWidth() {
        if (large){
            return (int) property.getTextSize();
        } else {
            return (int) property.getTextSize() / 2;
        }
    }

    @Override
    public float getHeight() {
        if (large){
            return (int) property.getTextSize();
        } else {
            return (int) property.getTextSize() / 2;
        }
    }
}
