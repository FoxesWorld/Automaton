package org.foxesworld.automaton;

import com.simsilica.lemur.Label;

public class IdentifiableLabel extends ComponentManager implements LabelInterface {

    private String id;

    private Boolean enabled;
    private Label label;

    public IdentifiableLabel(String id, Label label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public String getId() {
        return id;
    }
    @Override
    public Label getLabel() {
        return label;
    }
    @Override
    public String getValue() {
        return this.label.getText();
    }
}
