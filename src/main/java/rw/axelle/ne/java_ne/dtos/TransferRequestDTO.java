package rw.axelle.ne.java_ne.dtos;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private Long senderId;
    private Long receiverId;
    private double amount;
}