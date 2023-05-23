package jp.mikunika.SpringBootInsurance.model;

import jp.mikunika.SpringBootInsurance.dto.InsuranceOptionDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "insurance_option")
@Getter
@Setter
public class InsuranceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "insuranceOptionList")
    private Set<InsuranceObject> insuranceObjectList = new HashSet<>();

    public static InsuranceOption from(InsuranceOptionDto optionDto) {
        InsuranceOption option = new InsuranceOption();
        option.setName(optionDto.getName());
        return option;
    }
}
