package jp.mikunika.SpringBootInsurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceOption;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class InsuranceOptionDto {
    private Long id;
    private String name;

    @JsonIncludeProperties(value = {"id", "name"})
    List<InsuranceObject> insuranceObjectList = new ArrayList<>();

    public static InsuranceOptionDto from(InsuranceOption option) {
        InsuranceOptionDto insuranceOptionDto = new InsuranceOptionDto();
        insuranceOptionDto.setId(option.getId());
        insuranceOptionDto.setName(option.getName());
        if (option.getInsuranceObjectList() != null) {
            insuranceOptionDto.setInsuranceObjectList(List.copyOf(option.getInsuranceObjectList()));
        }
        return insuranceOptionDto;
    }

    public static List<InsuranceOptionDto> fromList(List<InsuranceOption> optionList) {
        return optionList.stream().map(InsuranceOptionDto::from).collect(Collectors.toList());
    }
}
