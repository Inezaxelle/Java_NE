package rw.axelle.ne.java_ne.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.axelle.ne.java_ne.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}