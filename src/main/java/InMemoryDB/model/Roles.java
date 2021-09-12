package InMemoryDB.model;

public enum Roles {
    EMPLOYEE("EMPLOYEE"), ADMIN("ADMIN");

    private String role;

    Roles(String role) {
        this.role=role;
    }

    public String getRole() {
        return role;
    }


}
