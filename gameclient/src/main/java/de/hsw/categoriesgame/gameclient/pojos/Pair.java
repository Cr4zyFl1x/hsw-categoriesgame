package de.hsw.categoriesgame.gameclient.pojos;

import java.util.List;

public class Pair<A, B> {

    private A firstElement;
    private B secondElement;

    public Pair(A firstElement, B secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public A getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(A firstElement) {
        this.firstElement = firstElement;
    }

    public B getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(B secondElement) {
        this.secondElement = secondElement;
    }
}
