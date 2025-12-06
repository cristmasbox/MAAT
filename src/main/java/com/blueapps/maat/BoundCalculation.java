package com.blueapps.maat;

import android.graphics.Rect;
import android.util.Log;

import com.blueapps.maat.bounds.Bound;
import com.blueapps.maat.bounds.BreakBound;
import com.blueapps.maat.bounds.HorizontalBound;
import com.blueapps.maat.bounds.SimpleBound;
import com.blueapps.maat.bounds.VertBound;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class BoundCalculation {

    private static final String TAG = "BoundCalculation";

    private float xCursor = 0f;
    private float yCursor = 0f;

    private float width = 0f;
    private float height = 0f;

    private Document XMLContent;
    private ArrayList<ValuePair<Float, Float>> Dimensions;
    private BoundProperty property;

    private ArrayList<Bound> boundCalculations = new ArrayList<>();

    // XML Data
    public static final String XML_ROOT_TAG = "ancientText";
    public static final String XML_SIGN_TAG = "sign";
    public static final String XML_V_TAG = "v";
    public static final String XML_H_TAG = "h";
    public static final String XML_BREAK_TAG = "br";
    public static final String XML_PAGE_BREAK_TAG = "pbr";
    public static final String XML_ID_ATTRIBUTE = "id";

    public static final int STANDARD_HEIGHT_VALUE = 1000;
    public static final int STANDARD_WIDTH_VALUE = 1000;
    public static final float MAX_INDIVIDUAL_SCALE = 1.0f;

    public BoundCalculation(Document XMLContent){
        this.XMLContent = XMLContent;

        int counter = 0;

        if (this.XMLContent != null) {
            // Loop through all tags
            Element rootElement = XMLContent.getDocumentElement();
            if (rootElement != null) {
                if (Objects.equals(rootElement.getTagName(), XML_ROOT_TAG)) {
                    NodeList nodeList = rootElement.getChildNodes();
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if (node instanceof Element) {
                            Element element = (Element) node;
                            if (Objects.equals(element.getTagName(), XML_SIGN_TAG)) {

                                boundCalculations.add(new SimpleBound(element));
                                counter++;

                            } else if (Objects.equals(element.getTagName(), XML_H_TAG)) {

                                HorizontalBound horizontalBound = new HorizontalBound(counter, element, true);
                                counter += horizontalBound.getCounter();
                                boundCalculations.add(horizontalBound);

                            } else if (Objects.equals(element.getTagName(), XML_V_TAG)) {

                                VertBound vertBound = new VertBound(counter, element, true);
                                counter += vertBound.getCounter();
                                boundCalculations.add(vertBound);

                            } else if (Objects.equals(element.getTagName(), XML_BREAK_TAG) ||
                                        Objects.equals(element.getTagName(), XML_PAGE_BREAK_TAG)) {

                                BreakBound breakBound = new BreakBound(element);
                                boundCalculations.add(breakBound);

                            }
                        } else if (node instanceof Comment) {
                            Log.i(TAG, "Node is a Comment");
                        } else if (node instanceof Text) {
                            Log.i(TAG, "Node is a Text");
                        } else {
                            Log.i(TAG, "Node Type unknown");
                        }
                    }
                } else {
                    Log.e(TAG, "Wrong Root Element. Root Element should be '<" + XML_ROOT_TAG + ">' in stead of '<" + rootElement.getTagName() + ">'.");
                }
            }
        }
    }

    public ArrayList<Rect> getBounds(ArrayList<ValuePair<Float, Float>> dimensions, BoundProperty property){
        ArrayList<Rect> bounds = new ArrayList<>();
        ArrayList<Rect> returnBounds = new ArrayList<>();
        this.Dimensions = dimensions;
        this.property = property;

        // Calculate linePadding
        float linePadding;
        if (property.areLinesDrawn()){
            linePadding = property.getInterLinePadding() + property.getLineThickness();
        } else {
            linePadding = property.getInterLinePadding();
        }

        // Set starting positions
        xCursor = property.getX() + property.getPagePaddingLeft();
        yCursor = property.getY() + property.getPagePaddingTop();

        // reverse order for RTL layout
        if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES &&
                property.getWritingDirection() == BoundProperty.WRITING_DIRECTION_RTL) {

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

            if (bound instanceof BreakBound){

                if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES){
                    width = Math.max(xCursor, width);
                    xCursor = property.getX() + property.getPagePaddingLeft();
                    yCursor += property.getTextSize() + linePadding;
                } else {
                    height = Math.max(yCursor, height);
                    xCursor += property.getTextSize() + linePadding;
                    yCursor = property.getY() + property.getPagePaddingTop();
                }

            } else {

                ArrayList<ValuePair<Float, Float>> dimension = new ArrayList<>();
                int signCount = bound.getSignCount();
                for (int i = 0; i < signCount; i++) {
                    dimension.add(dimensions.get(count));
                    count++;
                }

                Rect boundBound = new Rect(bound.getBound(property, dimension));

                boundBound.left = (int) (boundBound.left + xCursor);
                boundBound.right = (int) (boundBound.right + xCursor);
                boundBound.top = (int) (boundBound.top + yCursor);
                boundBound.bottom = (int) (boundBound.bottom + yCursor);

                bounds.add(boundBound);

                if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES) {
                    xCursor += bound.getWidth() + property.getSignPadding();
                } else {
                    yCursor += bound.getHeight() + property.getSignPadding();
                }

            }
        }

        if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES){
            width = Math.max(xCursor, width);
            yCursor += property.getTextSize();
            height = yCursor;
        } else {
            height = Math.max(yCursor, height);
            xCursor += property.getTextSize();
            width = xCursor;
        }

        int counter = 0;
        for (Bound bound: boundCalculations){

            if (!(bound instanceof BreakBound)){
                Rect boundBound = bounds.get(counter);
                ArrayList<Rect> bounds2 = bound.getBounds(boundBound);
                returnBounds.addAll(bounds2);

                counter++;
            }
        }

        width += property.getPagePaddingRight() - property.getX();
        height += property.getPagePaddingBottom() - property.getY();

        return returnBounds;
    }

    public ArrayList<String> getIds(boolean lines, boolean RTL){
        ArrayList<String> returnArray = new ArrayList<>();

        // reverse order for RTL layout
        if (RTL && lines) {

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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
