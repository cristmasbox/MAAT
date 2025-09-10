package com.blueapps.render.bounds;

import static com.blueapps.render.BoundCalculation.MAX_INDIVIDUAL_SCALE;

import android.graphics.Rect;

import com.blueapps.render.BoundProperty;
import com.blueapps.render.ValuePair;

import org.w3c.dom.Element;

import java.util.ArrayList;

public class VertBound extends LayoutBound {
    private static final String TAG = "VertBound";

    public VertBound(int counter, Element element, boolean rootChild) {
        super(counter, element, rootChild);
    }

    @Override
    public Rect getBound(BoundProperty property, ArrayList<ValuePair<Float, Float>> dimensions) {
        ArrayList<Rect> firstBounds = new ArrayList<>();
        ArrayList<Rect> secondBounds = new ArrayList<>();
        ArrayList<Rect> thirdBounds = new ArrayList<>();
        float yCursor = 0f;
        float firstOverallHeight = 0f;
        float secondOverallHeight = 0f;

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

            firstOverallHeight += height;

        }

        if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES || (!rootChild)) {
            double scale = property.getTextSize() / firstOverallHeight;

            if (scale > MAX_INDIVIDUAL_SCALE) {
                scale = MAX_INDIVIDUAL_SCALE;
            }

            for (Rect bound : firstBounds) {
                bound.right = (int) (bound.width() * scale);
                bound.bottom = (int) (bound.height() * scale);
                secondOverallHeight += bound.height();
                secondBounds.add(bound);
            }

            for (Rect bound : secondBounds) {
                int boundWidth = bound.width();

                if (boundWidth > width) {
                    this.width = boundWidth;
                }
            }
            this.height = property.getTextSize();

            float border = property.getTextSize() - secondOverallHeight;
            int spaces = firstBounds.size() - 1;
            float individualBorder = border / spaces;

            for (Rect bound : secondBounds) {
                float boundWidth = bound.width();
                bound.top = (int) yCursor;
                bound.bottom = (int) (bound.bottom + yCursor);
                if (property.getWritingDirection() == BoundProperty.WRITING_DIRECTION_RTL) {
                    bound.left = (int) -((width - boundWidth) / 2);
                    bound.right = (int) (bound.right - ((width - boundWidth) / 2));
                } else {
                    bound.left = (int) ((width - boundWidth) / 2);
                    bound.right = (int) (bound.right + ((width - boundWidth) / 2));
                }

                thirdBounds.add(bound);
                yCursor += bound.height() + individualBorder;
            }
        } else {
            for (Rect bound : firstBounds) {
                int boundHeight = bound.height();

                this.height += boundHeight;
            }
            this.width = property.getTextSize();

            for (Rect bound : firstBounds) {
                float boundWidth = bound.width();
                bound.top = (int) yCursor;
                bound.bottom = (int) (bound.bottom + yCursor);
                bound.left = (int) ((width - boundWidth) / 2);
                bound.right = (int) (bound.right + ((width - boundWidth) / 2));

                thirdBounds.add(bound);
                yCursor += bound.height();
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

}
