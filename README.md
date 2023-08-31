# Automaton
Lemur form from JSON

#Sample **JSON**
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
