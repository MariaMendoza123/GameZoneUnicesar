# GameZoneUnicesar

Video game store management system developed in Java using object-oriented programming and layered architecture.

## New Features

The system now supports accessory management, including:

- Controllers with connection type and console compatibility.
- Cables with length and connector type.
- Memories with storage capacity and memory type.
- Accessory inventory and filtering by type.
- Compatibility queries between accessories and consoles.
- Integration of accessories into sales.

## Promotion Features

The system now supports promotion management, including:

- Percentage discounts applied to the total sale.
- Category discounts applied only to products of a target category.
- Bulk purchase discounts based on a minimum quantity of products.
- Promotion validity based on start and end dates.
- Automatic selection of the promotion that provides the highest monetary discount.
- Promotion persistence in data/promotions.csv.
- Discount information included in the sale receipt.

## Return Management Features

The system now supports return management, including:

- Registration of partial returns linked to an original sale.
- Validation of the 30-day return deadline.
- Validation that returned products belong to the referenced sale.
- Automatic restoration of product stock after a successful return.
- Automatic calculation of the refunded amount.
- Return queries by customer and by original sale.
- Monthly balance reporting with total sales, total returns, and net balance.
- Return persistence in data/returns.csv.
- Return receipts with return details and refund information.

## Warranty Management Features

The system now supports warranty management, including:

- Automatic basic warranties for consoles sold in the system.
- Basic warranty coverage for 6 months from the sale date.
- Optional extended warranties for consoles.
- Extended warranty coverage for 12 months from the sale date.
- Extended warranty cost of 10% of the associated console price.
- Warranty validity checks based on start and end dates.
- Consultation of the warranty associated with a product in a specific sale.
- Listing of all registered warranties.
- Listing of currently active warranties.
- Listing of warranties expiring within a user-defined number of days.
- Warranty persistence in data/warranties.csv.
- Warranty certificate generation with warranty details.
