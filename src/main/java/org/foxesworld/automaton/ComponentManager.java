package org.foxesworld.automaton;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.ProgressBar;
import com.simsilica.lemur.style.ElementId;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

    private Map<String, Label> labelMap = new HashMap<>();
    private Map<String, IdentifiableProgressBar> progressBarMap = new HashMap<>();
    public ComponentManager() {}

    protected IdentifiableLabel addLabel(String text, String elementIdText, Container parent) {
        ElementId elementId = new ElementId(elementIdText);
        Label label = new Label(text, elementId);
        parent.addChild(label);

        IdentifiableLabel identifiableLabel = new IdentifiableLabel(elementIdText, label);
        labelMap.put(elementIdText, label);

        return identifiableLabel;
    }

    protected IdentifiableProgressBar addProgressBar(String id, float initialValue, Container parent) {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgressValue(initialValue);
        parent.addChild(progressBar);

        IdentifiableProgressBar identifiableProgressBar = new IdentifiableProgressBar(id, progressBar);
        progressBarMap.put(id, identifiableProgressBar);

        return identifiableProgressBar;
    }

    public void updateProgressBarValue(String id, float newValue) {
        IdentifiableProgressBar identifiableProgressBar = progressBarMap.get(id);
        if (identifiableProgressBar != null) {
            ProgressBar progressBar = identifiableProgressBar.getProgressBar();
            progressBar.setProgressValue(newValue);
        }
    }

    public Label getLabelByElementId(String elementIdText) {
        return labelMap.get(elementIdText);
    }

    public void updateLabelText(String elementIdText, String newText) {
        Label label = getLabelByElementId(elementIdText);
        if (label != null) {
            label.setText(newText);
        }
    }

    public void updateLabelTexts(String[] elementIds, String[] newValues) {
        if (elementIds.length != newValues.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }

        for (int i = 0; i < elementIds.length; i++) {
            updateLabelText(elementIds[i], newValues[i]);
        }
    }
}
