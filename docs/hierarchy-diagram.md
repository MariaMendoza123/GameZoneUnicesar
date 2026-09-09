---
config:
  theme: redux
  layout: fixed
---
flowchart TB
n1["GameZone"] --> n2["Abstract Person"] & n5["Abstract Product"] & n13["Sale"]
n2 --> n6["Customer"] & n7["Seller"]
n5 --> n8["VideoGame"] & n9["Console"]
n11["Hierarchy diagram for GameZoneUnicesar"]
n12[" "]
n14[" "]
n15[" "]
n16[" "]
n17[" "]

    n1@{ shape: rect}
    n11@{ shape: text}
    n12@{ icon: "azure:cost-alerts", pos: "b"}
    n14@{ icon: "azure:customer-lockbox-for-microsoft-azure", pos: "b"}
    n15@{ icon: "gcp:producer-portal", pos: "b"}
    n16@{ icon: "azure:video-analyzers", pos: "b"}
    n17@{ icon: "aws:arch-amazon-gamesparks", pos: "b"}
    style n1 fill:#FFF9C4,stroke:#FFF9C4,stroke-width:4px,stroke-dasharray: 0
    style n2 fill:#BBDEFB,stroke:#BBDEFB
    style n5 fill:#BBDEFB,stroke:#BBDEFB
    style n13 fill:#BBDEFB,stroke:#BBDEFB
    style n6 fill:#C8E6C9,stroke:#C8E6C9
    style n7 fill:#C8E6C9,stroke:#C8E6C9
    style n8 fill:#C8E6C9,stroke:#C8E6C9
    style n9 fill:#C8E6C9,stroke:#C8E6C9