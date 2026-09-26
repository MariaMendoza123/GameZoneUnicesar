package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;
/**
 * Represents a return transaction in the store.
 */
public class Return {

    private String id;
    private LocalDate returnDate;
    private com.gamezone.model.Sale originalSale;
    private List<Product> returnedProducts;
    private String returnReason;
    private double refundAmount;
    /**
     * Constructs a new Return instance.
     *
     * @param id               Unique identifier for the return.
     * @param returnDate       Date of the return.
     * @param originalSale     The original sale associated with the return.
     * @param returnedProducts List of products being returned.
     * @param returnReason     Reason for the return.
     * @param refundAmount     Amount to be refunded to the customer.
     */
    public Return(String id, LocalDate returnDate, Sale originalSale, List<Product> returnedProducts, String returnReason, double refundAmount) {
        this.id = id;
        this.returnDate = returnDate;
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.returnReason = returnReason;
        this.refundAmount = refundAmount;
    }
    /**
     * Returns the unique identifier of the return transaction.
     *
     * @return the return ID
     */
    public String getId() {
        return id;
    }
    /**
     * Returns the date of the return transaction.
     *
     * @return the return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }
    /**
     * Returns the original sale associated with the return transaction.
     *
     * @return the original sale
     */
    public Sale getOriginalSale() {
        return originalSale;
    }
    /**
     * Returns the list of products being returned in the transaction.
     *
     * @return the list of returned products
     */
    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }
    /**
     * Returns the reason for the return transaction.
     *
     * @return the return reason
     */
    public String getReturnReason() {
        return returnReason;
    }
    /**
     * Returns the amount to be refunded to the customer for the return transaction.
     *
     * @return the refund amount
     */
    public double getRefundAmount() {
        return refundAmount;
    }
    /**
     * Calculates the refund amount based on the original sale and the returned products.
     *
     * @return the calculated refund amount
     */
    public double calculateRefundAmount() {
        double totalRefund = 0;
        double subtotal = originalSale.calculateTotal();
        double paidProportion = 1 - (originalSale.getDiscountAmount()/subtotal);
        for (Product product : returnedProducts) {
            totalRefund =  totalRefund + (product.getPrice() * paidProportion);
        }
        this.refundAmount = totalRefund;
        return totalRefund;
    }
    /**
     * Generates a return receipt for the return transaction.
     *
     * @return the return receipt as a string
     */
    public String generateReturnReceipt(){
        double subtotal = originalSale.calculateTotal();
        double paidProportion = 1 - (originalSale.getDiscountAmount()/subtotal);
        String receipt = "Recibo de devolución\n";
        receipt += "ID de devolución: " + id + "\n";
        receipt += "Fecha de devolución: " + returnDate + "\n";
        receipt += "Venta original: " + originalSale.getId() + "\n";
        receipt += "Productos devueltos:\n";
        for (Product product : returnedProducts) {
            double listPrice = product.getPrice();
            double proportionalDiscount = listPrice * (1 - paidProportion);
            double refundedAmount = listPrice * paidProportion;
            receipt += "- " + product.getTitle() + ": precio $" + listPrice + ", descuento $" + proportionalDiscount + ", reembolso $" + refundedAmount + "\n";        }
        receipt += "Motivo de la devolución: " + returnReason + "\n";
        receipt += "Monto del reembolso: $" + refundAmount + "\n";

        return receipt;
    }

}
