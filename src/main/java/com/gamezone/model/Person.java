package com.gamezone.model;

/**
 * Represents a generic person interacting with the store.
 * This class cannot be instantiated directly; every person must be
 * either a Client or a Seller.
 */
public abstract class Person {

    private String id;
    private String name;
    private String phone;

    /**
     * Constructs a new Person instance.
     *
     * @param id    Unique identifier for the person.
     * @param name  Full name of the person.
     * @param phone Contact phone number.
     */
    public Person(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Returns role-specific information about this person.
     *
     * @return A description of the person's role and its particular data.
     */
    public abstract String getRoleDescription();
}