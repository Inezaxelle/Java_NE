package rw.axelle.ne.java_ne.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Account is required")
    private String account;

    @Positive(message = "Amount must be positive")
    private double amount;

    @NotNull(message = "Transaction type is required")
    @Pattern(regexp = "saving|withdraw", message = "Type must be 'saving' or 'withdraw'")
    private String type;

    @NotNull(message = "Banking time can not be null")
    private LocalDateTime bankingDateTime;
}