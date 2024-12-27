package com.iqama.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("expiryDate")
    private LocalDate expiryDate;

    @ManyToOne // each employee can have only one role, but multiple employees can share the same role.
    @JoinColumn(name = "role_id", referencedColumnName = "id") //FK to EmployeeRole
    private EmployeeRole role;

    @Transient //this field won't be stored in the db
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public String getStatus() {
        //calculate status based on expiryDate
        if(expiryDate != null && expiryDate.isBefore(LocalDate.now())) {
            return "Expired";
        }
        return "Active";
    }

    //not needed since the status is dynamically calculated
    public void setStatus(String status) {
        this.status = status;
    }
}
