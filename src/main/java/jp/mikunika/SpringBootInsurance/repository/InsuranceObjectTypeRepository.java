package jp.mikunika.SpringBootInsurance.repository;

import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceObjectTypeRepository extends JpaRepository<InsuranceObjectType, Long> {
}
