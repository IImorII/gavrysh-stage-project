package jp.mikunika.SpringBootInsurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class InsuranceClientDto {
    private Long id;
    private String name;
    private Integer birth;

    @JsonIncludeProperties(value = {"id", "name"})
    private List<InsurancePolicy> insurancePolicyList = new ArrayList<>();

    public static InsuranceClientDto from(InsuranceClient client) {
        InsuranceClientDto clientDto = new InsuranceClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setBirth(client.getBirth());
        if (client.getInsurancePolicyList() != null) {
            clientDto.setInsurancePolicyList(List.copyOf(client.getInsurancePolicyList()));
        }
        return clientDto;
    }

    public static List<InsuranceClientDto> fromList(List<InsuranceClient> clientList) {
        return clientList.stream().map(InsuranceClientDto::from).collect(Collectors.toList());
    }
}
