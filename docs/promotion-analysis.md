# Promotion Module Analysis

## 1. Promotion Class Hierarchy

The three promotion types share common attributes and behaviors, such as an identifier, a name, a start date, an end date, and the ability to calculate a discount. This common structure is represented through an abstract base class called `Promotion`.

The `Promotion` class contains the attributes and methods shared by all promotion types, while `PercentageDiscount`, `CategoryDiscount`, and `BulkPurchaseDiscount` extend it and implement their own discount calculation rules.

The object-oriented programming mechanism used to allow each promotion to calculate its discount differently is polymorphism. The system can work with objects of type `Promotion` without knowing their concrete classes. When `calculateDiscount(Sale sale)` is invoked, the overridden implementation corresponding to the actual promotion object is executed.

This design reduces coupling because the rest of the system does not need conditional logic based on the concrete promotion type.

---

## 2. Abstract Discount Calculation Method

The `Promotion` class cannot provide a single implementation for `calculateDiscount(Sale sale)` because each promotion type follows a different business rule.

Therefore, the method is declared as an abstract method:

`public abstract double calculateDiscount(Sale sale);`

This declaration requires every concrete subclass of `Promotion` to provide its own implementation of the method.

The `abstract` declaration guarantees that a concrete promotion cannot exist without defining how its discount is calculated. The `@Override` annotation is used in each subclass to explicitly indicate that the inherited abstract method is being implemented.

---

## 3. Selection of the Best Promotion

The logic for selecting the promotion that provides the highest monetary discount belongs in `PromotionService`.

`PromotionService` is part of the service layer, where business rules and application logic are handled. It can obtain the registered promotions from `PromotionRepository`, filter the active promotions, calculate the discount that each promotion would provide for a specific sale, and select the promotion with the highest applicable discount.

This location is consistent with layered architecture because the service layer coordinates business rules between the user interface, persistence layer, and domain model.

This logic should not be placed inside `Sale` because `Sale` represents a sale and its state, while selecting among multiple promotions is a business rule involving a collection of promotions.

It should also not be placed in the console menu because the user interface should only handle interaction with the user. The menu should delegate business operations to the appropriate service instead of implementing business rules.

---

## 4. Changes to Sale and generateReceipt

The `Sale` class must be extended with two additional private attributes:

- `appliedPromotionName`, which stores the name of the promotion applied to the sale.
- `discountAmount`, which stores the monetary value of the discount.

Corresponding getters and setters must also be added.

The `generateReceipt` method must be updated so that the receipt clearly displays:

1. The subtotal, calculated as the sum of the product prices.
2. The applied discount and the name of the promotion.
3. The final total after subtracting the discount from the subtotal.

These are additive modifications to the existing sale behavior. The existing product, client, seller, and total calculation functionality remains available. When no promotion is applicable, the discount amount is zero and the final total remains equal to the subtotal.

Therefore, the change extends the existing behavior without removing the original sales functionality.

---

## 5. Promotion Validity

The validity rule should be implemented in the `Promotion` class through the `isActive(LocalDate date)` method.

The `Promotion` object owns its start and end dates, so it is responsible for determining whether a specific date falls within its validity range.

The method returns `true` when the specified date is greater than or equal to the start date and less than or equal to the end date.

`PromotionService` is responsible for using this rule when obtaining active promotions. For example, `listActivePromotions()` and `findBestPromotionFor(Sale sale)` can obtain the current date and call `promotion.isActive(currentDate)` for each registered promotion.

This separation keeps the date comparison rule inside the domain model while keeping the process of filtering and selecting promotions inside the service layer.

Therefore, the validity rule is implemented in `Promotion`, while `PromotionService` uses that rule as part of the business workflow.