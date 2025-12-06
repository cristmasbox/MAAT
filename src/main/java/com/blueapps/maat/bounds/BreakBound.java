package com.blueapps.maat.bounds;

import org.w3c.dom.Element;

public class BreakBound extends Bound{

    public BreakBound(Element element) {
        super(element);
    }

    @Override
    public int getSignCount() {
        return 0;
    }
}
