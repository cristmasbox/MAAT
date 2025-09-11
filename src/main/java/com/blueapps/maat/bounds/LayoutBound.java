package com.blueapps.maat.bounds;

import static com.blueapps.maat.BoundCalculation.XML_H_TAG;
import static com.blueapps.maat.BoundCalculation.XML_SIGN_TAG;
import static com.blueapps.maat.BoundCalculation.XML_V_TAG;

import android.graphics.Rect;
import android.util.Log;

import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class LayoutBound extends Bound{
    private static final String TAG = "LayoutBound";

    protected ArrayList<Bound> boundCalculations = new ArrayList<>();
    protected Rect myBound = new Rect();
    protected ArrayList<Rect> subBounds = new ArrayList<>();

    protected int counter;
    protected Element element;
    protected boolean rootChild;

    public LayoutBound(int counter, Element element, boolean rootChild) {
        super(element);

        this.counter = counter;
        this.element = element;
        this.rootChild = rootChild;

        NodeList childNodes = element.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (childNode instanceof Element) {

                Element childElement = (Element) childNode;

                if (Objects.equals(childElement.getTagName(), XML_SIGN_TAG)) {

                    boundCalculations.add(new SimpleBound(childElement));
                    this.counter++;

                } else if (Objects.equals(childElement.getTagName(), XML_V_TAG)) {

                    VertBound vertBound = new VertBound(counter, childElement, false);
                    this.counter += vertBound.getCounter();
                    boundCalculations.add(vertBound);

                } else if (Objects.equals(childElement.getTagName(), XML_H_TAG)) {

                    HorizontalBound horizontalBound = new HorizontalBound(counter, childElement, false);
                    this.counter += horizontalBound.getCounter();
                    boundCalculations.add(horizontalBound);

                }

            } else if (childNode instanceof Comment) {
                Log.i(TAG, "ChildNode is a Comment");
            } else if (childNode instanceof Text) {
                Log.i(TAG, "ChildNode is a Text");
            } else {
                Log.i(TAG, "ChildNode Type unknown");
            }
        }
    }

    @Override
    public ArrayList<Rect> getBounds(Rect newBound) {
        ArrayList<Rect> returnBounds = new ArrayList<>();

        float scaleX = (float) newBound.width() / myBound.width();
        float scaleY = (float) newBound.height() / myBound.height();
        float transitionX = (newBound.left - myBound.left);
        float transitionY = (newBound.top - myBound.top);

        int counter = 0;
        for (Rect bound: subBounds){
            float x = bound.left;
            float y = bound.top;
            float r = bound.right;
            float b = bound.bottom;

            x = (x * scaleX) + transitionX;
            y = (y * scaleY) + transitionY;
            r = (r * scaleX) + transitionX;
            b = (b * scaleY) + transitionY;

            Rect newBound2 = new Rect((int) x, (int) y, (int) r, (int) b);
            Bound bound2 = boundCalculations.get(counter);
            returnBounds.addAll(bound2.getBounds(newBound2));
            counter++;
        }
        return returnBounds;
    }

    @Override
    public ArrayList<String> getIds() {
        ArrayList<String> returnArray = new ArrayList<>();
        for (Bound bound: boundCalculations){
            returnArray.addAll(bound.getIds());
        }
        return returnArray;
    }

    @Override
    public int getSignCount() {
        int signCount = 0;
        for (Bound bound: boundCalculations) {
            signCount += bound.getSignCount();
        }
        return signCount;
    }

    public int getCounter(){
        return this.counter;
    }
}
