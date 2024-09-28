package de.hsw.categoriesgame.gameclient.interfaces;

/**
 * The interface delivers a method which initializes all necessary components for a view
 */

public interface InitializableView {

    /**
     * Initializes components for a view
     */
    public void initializeComponents();

    /**
     * Builds the view after initializing the components
     */
    public void buildView();

}
