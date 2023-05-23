package jp.mikunika.SpringBootInsurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class InsurancePolicyDto {
    private Long id;
    private String name;

    @JsonIncludeProperties(value = {"id", "name", "birth"})
    private InsuranceClient client;

    @JsonIncludeProperties(value = {"id", "name"})
    List<InsuranceObject> insuranceObjectList = new ArrayList<>();

    public static InsurancePolicyDto from(InsurancePolicy policy) {
        InsurancePolicyDto insurancePolicyDto = new InsurancePolicyDto();
        insurancePolicyDto.setId(policy.getId());
        insurancePolicyDto.setName(policy.getName());
        if (policy.getClient() != null) {
            insurancePolicyDto.setClient(policy.getClient());
        }
        if (policy.getInsuranceObjectList() != null) {
            insurancePolicyDto.setInsuranceObjectList(List.copyOf(policy.getInsuranceObjectList()));
        }
        return insurancePolicyDto;
    }

    public static List<InsurancePolicyDto> fromList(List<InsurancePolicy> policyList) {
        return policyList.stream().map(InsurancePolicyDto::from).collect(Collectors.toList());
    }
}
