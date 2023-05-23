package jp.mikunika.SpringBootInsurance.model;

import jp.mikunika.SpringBootInsurance.dto.InsuranceClientDto;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InsuranceClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer birth;

    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.EAGER, mappedBy = "client")
    private Set<InsurancePolicy> insurancePolicyList = new HashSet<>();

    public void addPolicy(InsurancePolicy policy) {
        insurancePolicyList.add(policy);
    }

    public static InsuranceClient from(InsuranceClientDto clientDto) {
        InsuranceClient client = new InsuranceClient();
        client.setName(clientDto.getName());
        client.setBirth(clientDto.getBirth());
        return client;
    }
}
