package jp.mikunika.SpringBootInsurance.repository;

import jp.mikunika.SpringBootInsurance.model.InsuranceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceOptionRepository extends JpaRepository<InsuranceOption, Long> {
}
