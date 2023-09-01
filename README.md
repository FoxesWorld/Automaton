# Automaton ![Java Badge](https://img.shields.io/static/v1?label=Powered%20by&message=Java&color=darkorange&style=for-the-badge) <img src="https://img.shields.io/badge/version-1.1.0--Alpha-orange" alt="version - 1.1.0">
Lemur form from JSON

_Part of FrozenLands_

# Sample **JSON**
```
{
  "floatContainer": 0.5,
  "width": 200,
  "height": 180,
  "verticalAlignment": "top",
  "horizontalAlignment": "right",
  "children": [
    {
      "type": "label",
      "text": "Player position",
      "id": "test",
      "icon": "ui/icons/pos.png",
      "alignment": "right"
    },
    {
      "type": "container",
      "alignment": "right",
      "children": [
        {
          "type": "label",
          "text": "0",
          "id": "posX",
          "icon": "ui/icons/posX.png"
        },
        {
          "type": "label",
          "text": "0",
          "id": "posY",
          "icon": "ui/icons/posY.png"
        },
        {
          "type": "label",
          "text": "0",
          "id": "posZ",
          "icon": "ui/icons/posZ.png"
        }
      ]
    }
  ]
}
```

Supports recursiveness.
To use it you need to initialise Automaton() and supply **ComponentManager** as an argument
Don't forget to set width and height using setters to specify screen size
