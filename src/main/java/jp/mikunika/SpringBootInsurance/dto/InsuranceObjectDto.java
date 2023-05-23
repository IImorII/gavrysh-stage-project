package jp.mikunika.SpringBootInsurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;
import jp.mikunika.SpringBootInsurance.model.InsuranceOption;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class InsuranceObjectDto {
    private Long id;
    private String name;
    private Double price;

    @JsonIncludeProperties(value = {"id", "name"})
    private List<InsuranceOption> insuranceOptionList = new ArrayList<>();

    @JsonIncludeProperties(value = {"id", "name"})
    private InsuranceObjectType insuranceObjectType;

    @JsonIncludeProperties(value = {"id", "name"})
    private InsurancePolicy insurancePolicy;

    public static InsuranceObjectDto from(InsuranceObject object) {
        InsuranceObjectDto insuranceObjectDto = new InsuranceObjectDto();
        insuranceObjectDto.setId(object.getId());
        insuranceObjectDto.setName(object.getName());
        if (object.getInsuranceOptionList() != null) {
            insuranceObjectDto.setInsuranceOptionList(List.copyOf(object.getInsuranceOptionList()));
        }
        if (object.getInsuranceObjectType() != null) {
            insuranceObjectDto.setInsuranceObjectType(object.getInsuranceObjectType());
        }
        if (object.getInsurancePolicy() != null) {
            insuranceObjectDto.setInsurancePolicy(object.getInsurancePolicy());
        }
        return insuranceObjectDto;
    }

    public static List<InsuranceObjectDto> fromList(List<InsuranceObject> objectList) {
        return objectList.stream().map(InsuranceObjectDto::from).collect(Collectors.toList());
    }
}
