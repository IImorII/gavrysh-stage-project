package jp.mikunika.SpringBootInsurance.model;

import jp.mikunika.SpringBootInsurance.dto.InsurancePolicyDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "insurance_policy")
@Getter
@Setter
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "insurancePolicy")
    Set<InsuranceObject> insuranceObjectList = new HashSet<>();

    @ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_fk")
    InsuranceClient client;

    public static InsurancePolicy from(InsurancePolicyDto policyDto) {
        InsurancePolicy policy = new InsurancePolicy();
        policy.setName(policyDto.getName());
        return policy;
    }
}
