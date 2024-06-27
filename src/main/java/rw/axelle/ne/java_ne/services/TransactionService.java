package rw.axelle.ne.java_ne.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.axelle.ne.java_ne.models.Customer;
import rw.axelle.ne.java_ne.models.Transaction;
import rw.axelle.ne.java_ne.repositories.CustomerRepository;
import rw.axelle.ne.java_ne.repositories.TransactionRepository;
import rw.axelle.ne.java_ne.utils.MailtrapEmailSender;

import java.io.IOException;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Transaction saveTransaction(Transaction transaction) {
        Customer customer = customerRepository.findById(transaction.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + transaction.getCustomerId()));

        if (transaction.getType().equals("withdraw") && customer.getBalance() < transaction.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update customer balance
        if (transaction.getType().equals("withdraw")) {
            customer.setBalance(customer.getBalance() - transaction.getAmount());
        } else if (transaction.getType().equals("saving")) {
            customer.setBalance(customer.getBalance() + transaction.getAmount());
        }

        customerRepository.save(customer);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Send email notification
        sendEmailNotification(customer.getEmail(), "Transaction Completed", createTransactionMessage(customer.getFirstName(), transaction.getType(), transaction.getAmount(), customer.getAccount()));

        return savedTransaction;
    }

    private void sendEmailNotification(String recipientEmail, String subject, String message) {
        MailtrapEmailSender.sendEmail(recipientEmail, subject, message);
    }

    private String createTransactionMessage(String customerName, String transactionType, double amount, String account) {
        return String.format("Dear %s, your %s of %.2f on your account %s has been completed successfully.",
                customerName, transactionType, amount, account);
    }
}
