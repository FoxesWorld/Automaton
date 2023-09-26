package org.foxesworld.automaton;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.component.IconComponent;
import org.foxesworld.automaton.elements.ComponentID;
import org.foxesworld.automaton.elements.components.IdentifiableLabel;
import org.foxesworld.automaton.elements.components.IdentifiableProgressBar;
import org.foxesworld.automaton.elements.components.ComponentManager;

import java.util.HashMap;
import java.util.Map;

public class Automaton extends ComponentManager {

    private float screenWidth = 256;
    private float screenHeight = 128;
    private ComponentManager componentManager;

    public Automaton(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public Container createContainerFromJson(JsonObject json) {
        float floatContainer = json.has("floatContainer") ? json.get("floatContainer").getAsFloat() : 0.5f;

        int minWidth = 200;
        if (json.has("width")) {
            String widthValue = json.get("width").getAsString();
            if (widthValue.endsWith("%")) {
                float percent = Float.parseFloat(widthValue.substring(0, widthValue.length() - 1)) / 100f;
                minWidth = (int) (screenWidth * percent);
            } else {
                minWidth = json.get("width").getAsInt();
            }
        }

        int minHeight = 150;
        if (json.has("height")) {
            String heightValue = json.get("height").getAsString();
            if (heightValue.endsWith("%")) {
                float percent = Float.parseFloat(heightValue.substring(0, heightValue.length() - 1)) / 100f;
                minHeight = (int) (screenHeight * percent);
            } else {
                minHeight = json.get("height").getAsInt();
            }
        }

        String verticalAlignment = json.has("verticalAlignment") ? json.get("verticalAlignment").getAsString() : "center";
        String horizontalAlignment = json.has("horizontalAlignment") ? json.get("horizontalAlignment").getAsString() : "center";

        Container container = new Container();
        container.setPreferredSize(new Vector3f(minWidth, minHeight, 1));

        if (json.has("children")) {
            JsonArray children = json.getAsJsonArray("children");
            Map<String, Object> typeValues;
            for (JsonElement childElement : children) {
                JsonObject childJson = childElement.getAsJsonObject();
                if (childJson.has("type")) {
                    ComponentID type = ComponentID.valueOf(childJson.get("type").getAsString().toUpperCase());
                    switch (type) {
                        case CONTAINER:
                            Container nestedContainer = createContainerFromJson(childJson);
                            container.addChild(nestedContainer);
                            break;
                        case BUTTON:
                            String[][] buttonValues = new String[][]{
                                    {"text", "String"},
                                    {"id", "String"},
                                    {"icon", "String"}
                            };
                            typeValues = parseTypeValues(buttonValues, childJson);
                            Button button = container.addChild(new Button((String) typeValues.get("text")));
                            break;
                        case LABEL:
                            String[][] labelValues = new String[][]{
                                    {"text", "String"},
                                    {"id", "String"},
                                    {"fontSize", "Float"},
                                    {"icon", "String"},
                                    {"iconSize", "String"}
                            };
                            typeValues = parseTypeValues(labelValues, childJson);
                            IdentifiableLabel label = componentManager.addLabel((String) typeValues.get("text"), (String) typeValues.get("id"), container);
                            if (childJson.has("icon")) {
                                IconComponent iconPic = new IconComponent((String) typeValues.get("icon"));
                                String[] iconSize = ((String) typeValues.get("iconSize")).split(",");
                                iconPic.setIconSize(new Vector2f(Float.valueOf(iconSize[0]), Float.valueOf(iconSize[1])));
                                label.getLabel().setIcon(iconPic);
                            }
                            label.getLabel().setFontSize((Float) typeValues.get("fontSize"));
                            break;
                        case PROGRESSBAR:
                            String[][] progressBarValues = new String[][]{
                                    {"id", "String"},
                                    {"text", "String"},
                                    {"value", "Float"}
                            };
                            typeValues = parseTypeValues(progressBarValues, childJson);
                            IdentifiableProgressBar progressBar = componentManager.addProgressBar((String) typeValues.get("id"), (Float) typeValues.get("value"), container);
                            progressBar.getProgressBar().setMessage((String) typeValues.get("text"));
                            break;
                        case ICON:
                            String iconPath = childJson.get("path").getAsString();
                            IconComponent icon = new IconComponent(iconPath);
                            break;
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

    private Map<String, Object> parseTypeValues(String[][] elArray, JsonObject childJson) {
        Map<String, Object> typeValues = new HashMap<>();
        for (String[] val : elArray) {
            String field = val[0];
            Class<?> dataType = getType(val[1]);
            Object value = childJson.has(field) ? new Gson().fromJson(childJson.get(field), dataType) : getDefault(dataType);
            typeValues.put(field, value);
        }
        return typeValues;
    }

    private Class<?> getType(String typeName) {
        switch (typeName) {
            case "String":
                return String.class;
            case "Float":
                return Float.class;
            case "Boolean":
                return Boolean.class;
            default:
                throw new IllegalArgumentException("Unsupported data type: " + typeName);
        }
    }

    private Object getDefault(Class<?> dataType) {
        if (dataType.equals(String.class)) {
            return "";
        } else if (dataType.equals(Float.class)) {
            return 0f;
        } else if (dataType.equals(Boolean.class)) {
            return true;
        }
        throw new IllegalArgumentException("Unsupported data type: " + dataType.getName());
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }
}
