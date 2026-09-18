# Return Module Analysis

## 3. Return deadline validation

The 30-day deadline validation is placed partly in the model and partly in the service layer, each with a distinct responsibility. Sale.canBeReturned() contains the actual date comparison, using java.time.LocalDate.parse to convert the stored sale date into a LocalDate, and java.time.temporal.ChronoUnit.DAYS.between(saleDate, currentDate) to calculate the number of days elapsed since the sale.

ReturnService.registerReturn(...) is the class that actually enforces the business rule: before creating a Return, it calls sale.canBeReturned() and rejects the operation with an IllegalArgumentException if the sale is no longer within the 30-day window. This belongs in the service layer because deciding whether an operation is allowed to proceed is a business rule, not something the model itself should refuse or the persistence layer should check. Sale simply exposes the fact (can this sale still be returned), while ReturnService decides what to do with that fact.

## 5. Location of the monthly balance report

The monthly balance report is implemented in ReturnService, through the generateMonthlyBalance(int month, int year) method. This location is coherent with the layered architecture because generating this report requires combining business data from two different modules (sales and returns) and applying a business rule (net balance = sales total - returns total), which is exactly what the service layer is responsible for: coordinating operations across the domain model on behalf of a use case, without embedding that coordination in the model or the UI.

To generate the report, ReturnService needs two dependencies: SaleService, to retrieve all registered sales and filter them by month and year using their date; and its own list of returns (loaded through ReturnRepository), filtered by the return date using the same month/year criteria. No direct dependency on the persistence layer is needed beyond what ReturnRepository already provides, keeping the ui -> service -> persistence -> model dependency direction intact.
