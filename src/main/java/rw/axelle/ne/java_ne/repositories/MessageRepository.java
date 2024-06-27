package rw.axelle.ne.java_ne.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.axelle.ne.java_ne.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}