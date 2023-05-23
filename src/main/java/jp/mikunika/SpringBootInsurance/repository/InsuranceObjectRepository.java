package jp.mikunika.SpringBootInsurance.repository;

import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceObjectRepository extends JpaRepository<InsuranceObject, Long> {
}
