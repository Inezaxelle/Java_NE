package rw.axelle.ne.java_ne.utils;

public class EmailUtil {
    public static String createTransactionMessage(String customerName, String transactionType, double amount, String account) {
        return String.format("Dear %s, your %s of %f on your account %s has been completed successfully.",
                customerName, transactionType, amount, account);
    }
}