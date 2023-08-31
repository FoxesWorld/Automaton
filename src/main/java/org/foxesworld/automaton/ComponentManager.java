package org.foxesworld.automaton;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.ElementId;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

    private Map<String, Label> labelMap = new HashMap<>();
    public ComponentManager() {}

    public Label addLabel(String text, String elementIdText, Container parent) {
        ElementId elementId = new ElementId(elementIdText);
        Label label = new Label(text, elementId);
        labelMap.put(elementIdText, label);
        parent.addChild(label);
        return label;
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
