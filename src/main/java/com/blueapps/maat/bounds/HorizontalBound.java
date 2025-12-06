package com.blueapps.maat.bounds;

import static com.blueapps.maat.BoundCalculation.MAX_INDIVIDUAL_SCALE;

import android.graphics.Rect;

import com.blueapps.maat.BoundProperty;
import com.blueapps.maat.ValuePair;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;

public class HorizontalBound extends LayoutBound {

    public HorizontalBound(int counter, Element element, boolean rootChild) {
        super(counter, element, rootChild);
    }

    @Override
    public Rect getBound(BoundProperty property, ArrayList<ValuePair<Float, Float>> dimensions) {
        ArrayList<Rect> firstBounds = new ArrayList<>();
        ArrayList<Rect> secondBounds = new ArrayList<>();
        ArrayList<Rect> thirdBounds = new ArrayList<>();
        float xCursor = 0f;
        float firstOverallWidth = 0f;
        float secondOverallWidth = 0f;

        // reverse order for RTL layout
        if (property.getWritingDirection() == BoundProperty.WRITING_DIRECTION_RTL) {

            ArrayList<Bound> reversed = new ArrayList<>();
            ArrayList<Bound> line = new ArrayList<>();
            for (Bound bound : boundCalculations) {
                if (bound instanceof BreakBound) {
                    Collections.reverse(line);
                    reversed.addAll(line);
                    reversed.add(bound);
                    line.clear();
                } else {
                    line.add(bound);
                }
            }

            Collections.reverse(line);
            reversed.addAll(line);
            boundCalculations = reversed;

        }

        int count = 0;
        for (Bound bound: boundCalculations){

            ArrayList<ValuePair<Float, Float>> dimension = new ArrayList<>();
            int signCount = bound.getSignCount();
            for (int i = 0; i < signCount; i++){
                dimension.add(dimensions.get(count));
                count++;
            }
            Rect myBound = bound.getBound(property, dimension);

            int width = myBound.width();
            int height = myBound.height();
            myBound.left = 0;
            myBound.right = width;
            myBound.top = 0;
            myBound.bottom = height;

            firstBounds.add(myBound);

            firstOverallWidth += width + property.getLayoutSignPadding();
        }

        if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_COLUMNS || (!rootChild)) {
            double scale = property.getTextSize() / firstOverallWidth;

            if (scale > MAX_INDIVIDUAL_SCALE) {
                scale = MAX_INDIVIDUAL_SCALE;
            }

            for (Rect bound : firstBounds) {
                bound.right = (int) (bound.width() * scale);
                bound.bottom = (int) (bound.height() * scale);
                secondOverallWidth += bound.width();
                secondBounds.add(bound);
            }

            for (Rect bound : secondBounds) {
                int boundHeight = bound.height();

                if (boundHeight > height) {
                    this.height = boundHeight;
                }
            }
            this.width = property.getTextSize();

            float border = property.getTextSize() - secondOverallWidth;
            int spaces = firstBounds.size() - 1;
            float individualBorder = border / spaces;

            for (Rect bound : secondBounds) {
                float boundHeight = bound.height();
                bound.left = (int) xCursor;
                bound.right = (int) (bound.right + xCursor);
                bound.top = (int) ((height - boundHeight) / 2);
                bound.bottom = (int) (bound.bottom + ((height - boundHeight) / 2));

                thirdBounds.add(bound);
                xCursor += bound.width() + individualBorder;
            }
        } else {
            for (Rect bound : firstBounds) {
                int boundWidth = bound.width();

                this.width += boundWidth;
            }
            this.height = property.getTextSize();

            for (Rect bound : firstBounds) {
                float boundHeight = bound.height();
                bound.top = (int) ((height - boundHeight) / 2);
                bound.bottom = (int) (bound.bottom + ((height - boundHeight) / 2));
                bound.left = (int) xCursor;
                bound.right = (int) (bound.right + xCursor);

                thirdBounds.add(bound);
                xCursor += bound.width();
                counter++;
            }
        }

        this.subBounds = thirdBounds;

        int minLeft = Integer.MAX_VALUE;
        int minTop = Integer.MAX_VALUE;
        int maxRight = Integer.MIN_VALUE;
        int maxBottom = Integer.MIN_VALUE;
        for (Rect bound: subBounds){
            if (bound.left < minLeft){ minLeft = bound.left;}
            if (bound.top < minTop){ minTop = bound.top;}
            if (bound.right > maxRight){ maxRight = bound.right;}
            if (bound.bottom > maxBottom){ maxBottom = bound.bottom;}
        }

        myBound = new Rect(minLeft, minTop, maxRight, maxBottom);

        return new Rect(myBound);
    }

    @Override
    public ArrayList<String> getIds(boolean RTL) {
        ArrayList<String> returnArray = new ArrayList<>();
        // reverse order for RTL layout
        if (RTL) {

            ArrayList<Bound> reversed = new ArrayList<>();
            ArrayList<Bound> line = new ArrayList<>();
            for (Bound bound : boundCalculations) {
                if (bound instanceof BreakBound) {
                    Collections.reverse(line);
                    reversed.addAll(line);
                    reversed.add(bound);
                    line.clear();
                } else {
                    line.add(bound);
                }
            }

            Collections.reverse(line);
            reversed.addAll(line);
            boundCalculations = reversed;

        }

        for (Bound bound: boundCalculations){
            returnArray.addAll(bound.getIds(RTL));
        }
        return returnArray;
    }
}
