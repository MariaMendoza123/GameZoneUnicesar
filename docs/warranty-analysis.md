# Warranty Module Analysis

## 5. Location of the expiring-soon warranty query

The listWarrantiesExpiringSoon(int daysAhead) method belongs in WarrantyService. It needs the full in-memory list of warranties (loaded from WarrantyRepository through the constructor) and Java's LocalDate to compute today's date and the upper bound of the window (today.plusDays(daysAhead)), then filters the warranties whose endDate falls within that range.

This location is coherent with the layered architecture because deciding what counts as "expiring soon" and how many days ahead to look is a business rule, not raw persisted data (which belongs to WarrantyRepository) nor something the console menu should compute itself. The service layer is where such filtering and interpretation of domain data happens on behalf of a specific use case, keeping WarrantyRepository focused purely on reading and writing the CSV file, and keeping ConsoleMenu focused purely on collecting the daysAhead value from the user and displaying the result.
