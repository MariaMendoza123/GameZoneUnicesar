package com.gamezone.util;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;

/**
 * Utility class responsible for formatting return receipts.
 * Encapsulates view and text layout construction away from the Return domain model.
 */
public class ReturnReceiptFormatter {

    /**
     * Formats a complete return receipt string from a Return entity.
     *
     * @param returnTx the return transaction instance
     * @return formatted receipt string
     */
    public static String formatReceipt(Return returnTx) {
        if (returnTx == null || returnTx.getOriginalSale() == null) {
            return "";
        }

        Sale originalSale = returnTx.getOriginalSale();
        double subtotal = originalSale.calculateTotal();
        double paidProportion = 1 - (originalSale.getDiscountAmount() / subtotal);

        StringBuilder receipt = new StringBuilder();
        receipt.append("Recibo de devolución\n");
        receipt.append("ID de devolución: ").append(returnTx.getId()).append("\n");
        receipt.append("Fecha de devolución: ").append(returnTx.getReturnDate()).append("\n");
        receipt.append("Venta original: ").append(originalSale.getId()).append("\n");
        receipt.append("Productos devueltos:\n");

        if (returnTx.getReturnedProducts() != null) {
            for (Product product : returnTx.getReturnedProducts()) {
                double listPrice = product.getPrice();
                double proportionalDiscount = listPrice * (1 - paidProportion);
                double refundedAmount = listPrice * paidProportion;
                receipt.append("- ").append(product.getTitle())
                        .append(": precio $").append(listPrice)
                        .append(", descuento $").append(proportionalDiscount)
                        .append(", reembolso $").append(refundedAmount)
                        .append("\n");
            }
        }

        receipt.append("Motivo de la devolución: ").append(returnTx.getReturnReason()).append("\n");
        if (returnTx.getWarrantyRefundAmount() > 0) {
            receipt.append("Reembolso por cancelación de garantías: $")
                    .append(returnTx.getWarrantyRefundAmount()).append("\n");
        }
        receipt.append("Monto del reembolso: $").append(returnTx.getRefundAmount()).append("\n");

        return receipt.toString();
    }
}