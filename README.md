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
