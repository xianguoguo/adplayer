package com.adshow.player.activitys.fullscreen;

import java.util.Map;

public class ADMaterial {

    private String clazz;

    private int index;

    private float percentageTop;

    private float percentageBottom;

    private float percentageLeft;

    private float percentageRight;

    private Map<String, Object> attrs;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getPercentageTop() {
        return percentageTop;
    }

    public void setPercentageTop(float percentageTop) {
        this.percentageTop = percentageTop;
    }

    public float getPercentageBottom() {
        return percentageBottom;
    }

    public void setPercentageBottom(float percentageBottom) {
        this.percentageBottom = percentageBottom;
    }

    public float getPercentageLeft() {
        return percentageLeft;
    }

    public void setPercentageLeft(float percentageLeft) {
        this.percentageLeft = percentageLeft;
    }

    public float getPercentageRight() {
        return percentageRight;
    }

    public void setPercentageRight(float percentageRight) {
        this.percentageRight = percentageRight;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }
}
