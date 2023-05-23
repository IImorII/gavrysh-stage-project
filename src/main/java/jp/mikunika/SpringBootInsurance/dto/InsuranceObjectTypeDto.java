package jp.mikunika.SpringBootInsurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class InsuranceObjectTypeDto {
    private Long id;
    private String name;

    @JsonIncludeProperties(value = {"id", "name"})
    private List<InsuranceObject> insuranceObjectList = new ArrayList<>();

    public static InsuranceObjectTypeDto from(InsuranceObjectType objectType) {
        InsuranceObjectTypeDto objectTypeDto = new InsuranceObjectTypeDto();
        objectTypeDto.setId(objectType.getId());
        objectTypeDto.setName(objectType.getName());
        if (objectType.getInsuranceObjectList() != null) {
            objectTypeDto.setInsuranceObjectList(List.copyOf(objectType.getInsuranceObjectList()));
        }
        return objectTypeDto;
    }

    public static List<InsuranceObjectTypeDto> fromList(List<InsuranceObjectType> objectTypeList) {
        return objectTypeList.stream().map(InsuranceObjectTypeDto::from).collect(Collectors.toList());
    }
}
