package com.gamezone.model;

/**
 * Represents a seller who attends clients and registers sales.
 */
public class Seller extends Person {

    private String employeeCode;
    private String shift;

    /**
     * Constructs a new Seller instance.
     *
     * @param id           Unique identifier for the seller.
     * @param name         Full name of the seller.
     * @param phone        Contact phone number.
     * @param employeeCode Internal employee code.
     * @param shift        Assigned work shift.
     */
    public Seller(String id, String name, String phone, String employeeCode, String shift) {
        super(id, name, phone);
        this.employeeCode = employeeCode;
        this.shift = shift;
    }

    public String getEmployeeCode() { return employeeCode; }
    public String getShift() { return shift; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    public void setShift(String shift) { this.shift = shift; }

    @Override
    public String getRoleDescription() {
        return "Vendedor | Código: " + employeeCode + " | Turno: " + shift;
    }
}