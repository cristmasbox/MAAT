package com.blueapps.render;

import android.graphics.Rect;
import android.util.Log;

import com.blueapps.render.bounds.Bound;
import com.blueapps.render.bounds.HorizontalBound;
import com.blueapps.render.bounds.SimpleBound;
import com.blueapps.render.bounds.VertBound;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class BoundCalculation {

    private static final String TAG = "BoundCalculation";

    private float xCursor = 0f;
    private float yCursor = 0f;

    private Document XMLContent;
    private ArrayList<ValuePair<Float, Float>> Dimensions;
    private BoundProperty property;

    private ArrayList<Bound> boundCalculations = new ArrayList<>();

    // XML Data
    public static final String XML_ROOT_TAG = "ancientText";
    public static final String XML_SIGN_TAG = "sign";
    public static final String XML_V_TAG = "v";
    public static final String XML_H_TAG = "h";
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

        int count = 0;
        for (Bound bound: boundCalculations){

            ArrayList<ValuePair<Float, Float>> dimension = new ArrayList<>();
            int signCount = bound.getSignCount();
            for (int i = 0; i < signCount; i++){
                dimension.add(dimensions.get(count));
                count++;
            }

            Rect boundBound = new Rect(bound.getBound(property, dimension));

            if (property.getWritingDirection() == BoundProperty.WRITING_DIRECTION_RTL && property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES) {
                float width = boundBound.width();
                boundBound.left = (int) (boundBound.left + xCursor + property.getX() - width);
                boundBound.right = (int) (boundBound.right + xCursor + property.getX() - width);
            } else {
                boundBound.left = (int) (boundBound.left + xCursor + property.getX());
                boundBound.right = (int) (boundBound.right + xCursor + property.getX());
            }
            boundBound.top = (int) (boundBound.top + yCursor + property.getY());
            boundBound.bottom = (int) (boundBound.bottom + yCursor + property.getY());

            bounds.add(boundBound);

            if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES) {
                if (property.getWritingDirection() == BoundProperty.WRITING_DIRECTION_LTR) {
                    xCursor += bound.getWidth();
                } else {
                    xCursor -= bound.getWidth();
                }
            } else {
                yCursor += bound.getHeight();
            }
        }

        ArrayList<Rect> rtlBounds = new ArrayList<>();

        if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES && property.getWritingDirection() == BoundProperty.WRITING_DIRECTION_RTL){
            for (Rect bound: bounds){

                bound.left -= xCursor;
                bound.right -= xCursor;

                rtlBounds.add(bound);
            }
        } else {
            rtlBounds = bounds;
        }

        if (property.getWritingLayout() == BoundProperty.WRITING_LAYOUT_LINES){
            yCursor += property.getTextSize();
        } else {
            xCursor += property.getTextSize();
        }

        //return rtlBounds;

        int counter = 0;
        for (Bound bound: boundCalculations){

            Rect rtlBound = rtlBounds.get(counter);
            ArrayList<Rect> bounds2 = bound.getBounds(rtlBound);
            returnBounds.addAll(bounds2);

            counter++;
        }

        return returnBounds;
    }

    public ArrayList<String> getIds(){
        ArrayList<String> returnArray = new ArrayList<>();
        for (Bound bound: boundCalculations){
            returnArray.addAll(bound.getIds());
        }
        return returnArray;
    }

    public float getXCursor() {
        return xCursor;
    }

    public float getYCursor() {
        return yCursor;
    }
}
