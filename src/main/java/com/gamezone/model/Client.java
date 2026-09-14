package com.gamezone.model;

/**
 * Represents a client who purchases products at the store.
 */
public class Client extends Person {

    private String email;

    /**
     * Constructs a new Client instance.
     *
     * @param id    Unique identifier for the client.
     * @param name  Full name of the client.
     * @param phone Contact phone number.
     * @param email Client's email address.
     */
    public Client(String id, String name, String phone, String email) {
        super(id, name, phone);
        this.email = email;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getRoleDescription() {
        return "Cliente | Email: " + email;
    }
}