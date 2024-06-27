package rw.axelle.ne.java_ne.dtos;

import lombok.Data;

@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String dob;
    private String account;
    private double balance;
}