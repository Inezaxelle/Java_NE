package rw.axelle.ne.java_ne.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import rw.axelle.ne.java_ne.models.Customer;
import rw.axelle.ne.java_ne.models.Transaction;
import rw.axelle.ne.java_ne.repositories.CustomerRepository;
import rw.axelle.ne.java_ne.repositories.TransactionRepository;

import java.time.LocalDateTime;

@Service
public class TransferService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transfer(Long senderId, Long receiverId, double amount) {
        Customer sender = customerRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found with id: " + senderId));

        Customer receiver = customerRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found with id: " + receiverId));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Deduct amount from sender's balance
        sender.setBalance(sender.getBalance() - amount);
        customerRepository.save(sender);

        // Add amount to receiver's balance
        receiver.setBalance(receiver.getBalance() + amount);
        customerRepository.save(receiver);

        // Create sender transaction
        Transaction senderTransaction = new Transaction();
        senderTransaction.setCustomerId(senderId);
        senderTransaction.setAccount(sender.getAccount());
        senderTransaction.setAmount(amount);
        senderTransaction.setType("withdraw");
        senderTransaction.setBankingDateTime(LocalDateTime.now());
        transactionRepository.save(senderTransaction);

        // Create receiver transaction
        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setCustomerId(receiverId);
        receiverTransaction.setAccount(receiver.getAccount());
        receiverTransaction.setAmount(amount);
        receiverTransaction.setType("saving");
        receiverTransaction.setBankingDateTime(LocalDateTime.now());
        transactionRepository.save(receiverTransaction);
    }
}