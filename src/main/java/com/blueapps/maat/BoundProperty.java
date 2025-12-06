package com.blueapps.maat;

public class BoundProperty {

    private float x = 0f;
    private float y = 0f;

    private float textSize = 100f;
    private int verticalOrientation = VERTICAL_ORIENTATION_MIDDLE;
    private int writingDirection = WRITING_DIRECTION_LTR;
    private int writingLayout = WRITING_LAYOUT_LINES;

    private float pagePaddingLeft = 0f;
    private float pagePaddingTop = 0f;
    private float pagePaddingRight = 0f;
    private float pagePaddingBottom = 0f;
    private float signPadding = 10f;
    private float layoutSignPadding = 5f;
    private float interLinePadding = 25f;

    private boolean drawLines = false;
    private float lineThickness = 2f;


    // Constants
    public static final int VERTICAL_ORIENTATION_UP = 0;
    public static final int VERTICAL_ORIENTATION_MIDDLE = 1;
    public static final int VERTICAL_ORIENTATION_DOWN = 2;

    public static final int WRITING_DIRECTION_LTR = 0;
    public static final int WRITING_DIRECTION_RTL = 1;

    public static final int WRITING_LAYOUT_LINES = 0;
    public static final int WRITING_LAYOUT_COLUMNS = 1;

    public BoundProperty(){}

    public BoundProperty(float x, float y, float textSize,
                         int verticalOrientation, int writingDirection,
                         int writingLayout, boolean drawLines,
                         float lineThickness, float pagePaddingLeft,
                         float pagePaddingTop, float pagePaddingRight,
                         float pagePaddingBottom, float signPadding,
                         float layoutSignPadding, float interLinePadding){
        this.x = x;
        this.y = y;
        this.textSize = textSize;
        this.verticalOrientation = verticalOrientation;
        this.writingDirection = writingDirection;
        this.writingLayout = writingLayout;

        this.drawLines = drawLines;
        this.lineThickness = lineThickness;

        this.pagePaddingLeft = pagePaddingLeft;
        this.pagePaddingTop = pagePaddingTop;
        this.pagePaddingRight = pagePaddingRight;
        this.pagePaddingBottom = pagePaddingBottom;
        this.signPadding = signPadding;
        this.layoutSignPadding = layoutSignPadding;
        this.interLinePadding = interLinePadding;
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

    public float getPagePaddingBottom() {
        return pagePaddingBottom;
    }

    public void setPagePaddingBottom(float pagePaddingBottom) {
        this.pagePaddingBottom = pagePaddingBottom;
    }

    public float getPagePaddingRight() {
        return pagePaddingRight;
    }

    public void setPagePaddingRight(float pagePaddingRight) {
        this.pagePaddingRight = pagePaddingRight;
    }

    public float getPagePaddingTop() {
        return pagePaddingTop;
    }

    public void setPagePaddingTop(float pagePaddingTop) {
        this.pagePaddingTop = pagePaddingTop;
    }

    public float getPagePaddingLeft() {
        return pagePaddingLeft;
    }

    public void setPagePaddingLeft(float pagePaddingLeft) {
        this.pagePaddingLeft = pagePaddingLeft;
    }

    public float getSignPadding() {
        return signPadding;
    }

    public void setSignPadding(float signPadding) {
        this.signPadding = signPadding;
    }

    public float getLayoutSignPadding() {
        return layoutSignPadding;
    }

    public void setLayoutSignPadding(float layoutSignPadding) {
        this.layoutSignPadding = layoutSignPadding;
    }

    public float getInterLinePadding() {
        return interLinePadding;
    }

    public void setInterLinePadding(float interLinePadding) {
        this.interLinePadding = interLinePadding;
    }

    public boolean areLinesDrawn() {
        return drawLines;
    }

    public void setDrawLines(boolean drawLines) {
        this.drawLines = drawLines;
    }

    public float getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
    }
}
