package com.blueapps.maat.bounds;

import android.graphics.Rect;

import com.blueapps.maat.BoundProperty;
import com.blueapps.maat.ValuePair;

import org.w3c.dom.Element;

import java.util.ArrayList;

public abstract class Bound {

    protected BoundProperty property;
    protected ArrayList<ValuePair<Float, Float>> dimensions;

    protected float width = 0;
    protected float height = 0;

    protected Element element;

    public Bound(Element element){
        this.element = element;
    }

    public Rect getBound(BoundProperty property, ArrayList<ValuePair<Float, Float>> dimensions){
        this.property = property;
        this.dimensions = dimensions;
        return new Rect();
    }

    public ArrayList<Rect> getBounds(Rect newBound){
        ArrayList<Rect> bounds = new ArrayList<>();
        bounds.add(newBound);
        return bounds;
    }

    public ArrayList<String> getIds(){
        return new ArrayList<>();
    }

    public int getSignCount(){
        return 1;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

}
