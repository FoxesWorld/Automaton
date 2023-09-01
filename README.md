# Automaton ![Java Badge](https://img.shields.io/static/v1?label=Powered%20by&message=Java&color=darkorange&style=for-the-badge) <img src="https://img.shields.io/badge/version-1.2.0--Alpha-orange" alt="version - 1.2.0">
Lemur form from JSON

_Part of FrozenLands_

# Sample **JSON**
```
{
  "floatContainer": 0.5,
  "width": 200,
  "height": 250,
  "verticalAlignment": "top",
  "horizontalAlignment": "right",
  "children": [
    {
      "type": "label",
      "fontSize": 20,
      "text": "Player position",
      "id": "test",
      "icon": "ui/icons/pos.png",
      "iconSize": "64,64",
      "alignment": "right"
    },
    {
      "type": "container",
      "alignment": "right",
      "height": 160,
      "children": [
        {
          "type": "label",
          "fontSize": 30,
          "text": "0",
          "id": "posX",
          "icon": "ui/icons/posX.png",
          "iconSize": "32,32"
        },
        {
          "type": "label",
          "fontSize": 30,
          "text": "0",
          "id": "posY",
          "icon": "ui/icons/posY.png",
          "iconSize": "32,32"
        },
        {
          "type": "label",
          "fontSize": 30,
          "text": "0",
          "id": "posZ",
          "icon": "ui/icons/posZ.png",
          "iconSize": "32,32"
        },
        {
          "type": "progressbar",
          "value": 0,
          "id": "test",
          "text": "TEST"
        }
      ]
    }
  ]
}

```

Supports recursiveness.
To use it you need to initialise Automaton() and supply **ComponentManager** as an argument
Don't forget to set width and height using setters to specify screen size
``` 
        automaton.setScreenHeight(playerInterface.getFpsCam().getHeight());
        automaton.setScreenWidth(playerInterface.getFpsCam().getWidth());
```
