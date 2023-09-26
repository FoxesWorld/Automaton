package org.foxesworld.automaton.elements.components;

import com.simsilica.lemur.ProgressBar;

public class IdentifiableProgressBar extends  ComponentManager implements ProgressBarInterface {
    private String id;
    private ProgressBar progressBar;

    public IdentifiableProgressBar(String id, ProgressBar progressBar) {
        this.id = id;
        this.progressBar = progressBar;
    }

    @Override
    public String getId() {
        return id;
    }
    @Override
    public double getValue(){  return progressBar.getProgressValue(); }
    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }
}