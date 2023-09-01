package org.foxesworld.automaton;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.IconComponent;

public class Automaton extends ComponentManager {

    private float screenWidth = 256;
    private float screenHeight = 128;
    private ComponentManager componentManager;

    public Automaton(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public Container createContainerFromJson(JsonObject json) {

        float floatContainer = json.has("floatContainer") ? json.get("floatContainer").getAsFloat() : 0.5f;
        int minWidth = json.has("width") ? json.get("width").getAsInt() : 200;
        int minHeight = json.has("height") ? json.get("height").getAsInt() : 150;
        String verticalAlignment = json.has("verticalAlignment") ? json.get("verticalAlignment").getAsString() : "center";
        String horizontalAlignment = json.has("horizontalAlignment") ? json.get("horizontalAlignment").getAsString() : "center";

        Container container = new Container();
        container.setPreferredSize(new Vector3f(minWidth, minHeight, 1));

        if (json.has("children")) {
            JsonArray children = json.getAsJsonArray("children");
            for (JsonElement childElement : children) {
                JsonObject childJson = childElement.getAsJsonObject();
                if (childJson.has("type")) {
                    String type = childJson.get("type").getAsString();
                    switch (type) {
                        case "container":
                            Container nestedContainer = createContainerFromJson(childJson);
                            container.addChild(nestedContainer);
                            break;
                        case "button":
                            Button button = container.addChild(new Button(childJson.get("text").getAsString()));
                            break;
                        case "label":
                            IdentifiableLabel label = componentManager.addLabel(childJson.get("text").getAsString(), childJson.get("id").getAsString(), container);
                            if (childJson.has("icon")) {
                                label.getLabel().setIcon(new IconComponent(childJson.get("icon").getAsString()));
                            }
                            break;
                        case "progressbar":
                            IdentifiableProgressBar progressBar = componentManager.addProgressBar(childJson.get("id").getAsString(), childJson.get("value").getAsFloat(), container);
                            progressBar.getProgressBar().setMessage(childJson.get("text").getAsString());
                            break;
                        case "icon":
                            String iconPath = childJson.get("path").getAsString();
                            IconComponent icon = new IconComponent(iconPath);
                            break;
                        // Add additional cases for other types
                    }

                    if (childJson.has("verticalAlignment") && childJson.has("horizontalAlignment")) {
                        String childVerticalAlignment = childJson.get("verticalAlignment").getAsString();
                        String childHorizontalAlignment = childJson.get("horizontalAlignment").getAsString();
                        Vector3f childPosition = calculatePosition(minWidth, minHeight, container.getPreferredSize().x, container.getPreferredSize().y, childHorizontalAlignment, childVerticalAlignment);
                        container.setLocalTranslation(childPosition);
                    }
                }
            }
        }

        if (json.has("verticalAlignment") && json.has("horizontalAlignment")) {
            Vector3f containerPosition = calculatePosition(screenWidth, screenHeight, minWidth, minHeight, horizontalAlignment, verticalAlignment);
            container.setLocalTranslation(containerPosition);
        }

        return container;
    }

    private Vector3f calculatePosition(float screenWidth, float screenHeight, float containerWidth, float containerHeight, String horizontalAlignment, String verticalAlignment) {
        float xPosition = calculateAlignment(screenWidth, containerWidth, horizontalAlignment);
        float yPosition = calculateAlignment(screenHeight, containerHeight, verticalAlignment);
        return new Vector3f(xPosition, yPosition, 0);
    }

    private float calculateAlignment(float screenSize, float containerSize, String alignment) {
        float position;
        switch (alignment) {
            case "left":
            case "bottom":
                position = 15;
                break;
            case "center":
                position = (screenSize - containerSize) * 0.5f;
                break;
            case "right":
            case "top":
                position = screenSize - containerSize - 15;
                break;
            default:
                throw new IllegalArgumentException("Invalid alignment value: " + alignment);
        }
        return position;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }
}