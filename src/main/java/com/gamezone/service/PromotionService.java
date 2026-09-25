package com.gamezone.service;

import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.persistence.PromotionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business rules for registering, listing, and selecting promotions.
 */
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final List<Promotion> promotions;

    /**
     * Constructs a PromotionService with the specified PromotionRepository.
     *
     * @param promotionRepository the repository for managing promotions
     */
    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
        this.promotions = promotionRepository.loadAll();
    }

    /**
     * Registers a new percentage-based promotion.
     *
     * @param name       the name of the promotion
     * @param startDate  the start date of the promotion
     * @param endDate    the end date of the promotion
     * @param percentage the discount percentage to apply to the sale total
     * @return the registered PercentageDiscount object
     */
    public PercentageDiscount registerPercentageDiscount(String name, LocalDate startDate,
                                                         LocalDate endDate, double percentage) {
        validateCommonAttributes(name, startDate, endDate);
        validatePercentage(percentage);

        String id = generateNextId();
        PercentageDiscount promotion = new PercentageDiscount(id, name, startDate, endDate, percentage);
        promotions.add(promotion);
        promotionRepository.saveAll(promotions);
        return promotion;
    }

    /**
     * Registers a new category-based promotion.
     *
     * @param name               the name of the promotion
     * @param startDate          the start date of the promotion
     * @param endDate            the end date of the promotion
     * @param discountPercentage the discount percentage to apply to the target category
     * @param targetCategory     the target category ("VIDEOGAME", "CONSOLE" o "ACCESSORY")
     * @return the registered CategoryDiscount object
     */
    public CategoryDiscount registerCategoryDiscount(String name, LocalDate startDate, LocalDate endDate,
                                                     double discountPercentage, String targetCategory) {
        validateCommonAttributes(name, startDate, endDate);
        validatePercentage(discountPercentage);
        if (!"VIDEOGAME".equals(targetCategory) && !"CONSOLE".equals(targetCategory) && !"ACCESSORY".equals(targetCategory)) {
            throw new IllegalArgumentException("La categoría objetivo debe ser VIDEOGAME, CONSOLE o ACCESSORY.");
        }

        String id = generateNextId();
        CategoryDiscount promotion = new CategoryDiscount(id, name, startDate, endDate, discountPercentage, targetCategory);
        promotions.add(promotion);
        promotionRepository.saveAll(promotions);
        return promotion;
    }

    /**
     * Registers a new bulk-purchase promotion.
     *
     * @param name               the name of the promotion
     * @param startDate          the start date of the promotion
     * @param endDate            the end date of the promotion
     * @param minimumQuantity    the minimum quantity of products required
     * @param discountPercentage the discount percentage to apply to the sale total
     * @return the registered BulkPurchaseDiscount object
     */
    public BulkPurchaseDiscount registerBulkPurchaseDiscount(String name, LocalDate startDate, LocalDate endDate,
                                                             int minimumQuantity, double discountPercentage) {
        validateCommonAttributes(name, startDate, endDate);
        validatePercentage(discountPercentage);
        if (minimumQuantity <= 0) {
            throw new IllegalArgumentException("La cantidad mínima de productos debe ser mayor que cero.");
        }

        String id = generateNextId();
        BulkPurchaseDiscount promotion = new BulkPurchaseDiscount(id, name, startDate, endDate, minimumQuantity, discountPercentage);
        promotions.add(promotion);
        promotionRepository.saveAll(promotions);
        return promotion;
    }

    /**
     * Retrieves all registered promotions.
     *
     * @return a list of all promotions
     */
    public List<Promotion> listAllPromotions() {
        return promotions;
    }

    /**
     * Retrieves all promotions currently active on today's date.
     *
     * @return a list of active promotions
     */
    public List<Promotion> listActivePromotions() {
        List<Promotion> active = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Promotion promotion : promotions) {
            if (promotion.isActive(today)) {
                active.add(promotion);
            }
        }
        return active;
    }

    /**
     * Finds, among the currently active promotions, the one that grants the
     * greatest discount for the given sale.
     *
     * @param sale the sale to evaluate
     * @return the best applicable Promotion, or null if none applies or the best discount is zero
     */
    public Promotion findBestPromotionFor(Sale sale) {
        Promotion best = null;
        double bestDiscount = 0;

        for (Promotion promotion : listActivePromotions()) {
            double discount = promotion.calculateDiscount(sale);
            if (discount > bestDiscount) {
                bestDiscount = discount;
                best = promotion;
            }
        }
        return best;
    }

    /**
     * Finds a promotion by its ID.
     *
     * @param id the ID of the promotion to find
     * @return the found Promotion object, or null if not found
     */
    public Promotion findById(String id) {
        for (Promotion promotion : promotions) {
            if (promotion.getId().equals(id)) {
                return promotion;
            }
        }
        return null;
    }

    private void validateCommonAttributes(String name, LocalDate startDate, LocalDate endDate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre de la promoción es obligatorio.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
    }

    private void validatePercentage(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
    }

    private String generateNextId() {
        int maxId = 0;
        for (Promotion promotion : promotions) {
            if (promotion.getId() != null && promotion.getId().startsWith("PROMO-")) {
                try {
                    int numericPart = Integer.parseInt(promotion.getId().replace("PROMO-", ""));
                    if (numericPart > maxId) {
                        maxId = numericPart;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return "PROMO-" + (maxId + 1);
    }
}