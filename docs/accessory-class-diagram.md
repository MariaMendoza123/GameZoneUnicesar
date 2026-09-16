# Accessory Module Class Diagram

## Class Hierarchy

```text
                    Product
                       ▲
                       │
                  <<abstract>>
                    Accessory
                       ▲
          ┌────────────┼────────────┐
          │            │            │
     Controller      Cable        Memory

Accessory

Accessory is an abstract class that extends Product.

Attributes
compatibleConsoleIds: List<String>
Methods
getCompatibleConsoleIds()
setCompatibleConsoleIds(...)
isCompatibleWith(consoleId)
addCompatibleConsole(consoleId)
removeCompatibleConsole(consoleId)
getDescription()
Controller

Controller extends Accessory.

Attribute
ConnectionType: String
Methods
getConnectionType()
setConnectionType(...)
getDescription()
Cable

Cable extends Accessory.

Attributes
lengthInMeters: double
connectorType: String
Methods
getLengthInMeters()
setLengthInMeters(...)
getConnectorType()
setConnectorType(...)
toString()
Memory

Memory extends Accessory.

Attributes
capacityInGb: int
memoryType: String
Methods
getCapacityInGb()
setCapacityInGb(...)
getMemoryType()
setMemoryType(...)
getDescription()
Inheritance Relationship

The module uses inheritance and polymorphism:

Accessory inherits the common product information from Product.
Controller, Cable, and Memory inherit the common accessory behavior from Accessory.
Each concrete accessory adds its own specific attributes and behavior.