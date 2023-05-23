package jp.mikunika.SpringBootInsurance.repository;

import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<InsuranceClient, Long> {
}
