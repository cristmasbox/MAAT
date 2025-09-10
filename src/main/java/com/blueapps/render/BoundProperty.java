package com.blueapps.render;

public class BoundProperty {

    private float x = 0f;
    private float y = 0f;

    private float textSize = 0f;
    private int verticalOrientation;
    private int writingDirection;
    private int writingLayout;

    // Constants
    public static final int VERTICAL_ORIENTATION_UP = 0;
    public static final int VERTICAL_ORIENTATION_MIDDLE = 1;
    public static final int VERTICAL_ORIENTATION_DOWN = 2;

    public static final int WRITING_DIRECTION_LTR = 0;
    public static final int WRITING_DIRECTION_RTL = 1;

    public static final int WRITING_LAYOUT_LINES = 0;
    public static final int WRITING_LAYOUT_COLUMNS = 1;



    public BoundProperty(float x, float y, float textSize, int verticalOrientation, int writingDirection, int writingLayout){
        this.x = x;
        this.y = y;
        this.textSize = textSize;
        this.verticalOrientation = verticalOrientation;
        this.writingDirection = writingDirection;
        this.writingLayout = writingLayout;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getVerticalOrientation() {
        return verticalOrientation;
    }

    public void setVerticalOrientation(int verticalOrientation) {
        this.verticalOrientation = verticalOrientation;
    }

    public int getWritingDirection(){
        return writingDirection;
    }

    public void setWritingDirection(int writingDirection){
        this.writingDirection = writingDirection;
    }

    public int getWritingLayout(){
        return writingLayout;
    }

    public void setWritingLayout(int writingLayout){
        this.writingLayout = writingLayout;
    }

}
