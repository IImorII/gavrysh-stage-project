package jp.mikunika.SpringBootInsurance.controller;

import jp.mikunika.SpringBootInsurance.dto.InsuranceObjectDto;
import jp.mikunika.SpringBootInsurance.dto.InsuranceObjectTypeDto;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;
import jp.mikunika.SpringBootInsurance.service.InsuranceObjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("types")
public class InsuranceObjectTypeController {
    private final InsuranceObjectTypeService typeService;

    @Autowired
    public InsuranceObjectTypeController(InsuranceObjectTypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<InsuranceObjectTypeDto>> getTypes() {
        List<InsuranceObjectType> objectTypes = typeService.findAll();
        return ResponseEntity.ok(InsuranceObjectTypeDto.fromList(objectTypes));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<InsuranceObjectTypeDto> getOneType(@PathVariable final Long id) {
        InsuranceObjectType objectType = typeService.findOne(id);
        return ResponseEntity.ok(InsuranceObjectTypeDto.from(objectType));
    }

    @PostMapping
    public ResponseEntity<InsuranceObjectTypeDto> create(@RequestBody InsuranceObjectTypeDto typeDto) {
        InsuranceObjectType objectType = typeService.save(InsuranceObjectType.from(typeDto));
        return ResponseEntity.ok(InsuranceObjectTypeDto.from(typeService.save(objectType)));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<InsuranceObjectTypeDto> update(@PathVariable final Long id,
                                                         @RequestBody InsuranceObjectTypeDto typeDtoNew) {
        InsuranceObjectType objectType = typeService.update(id, InsuranceObjectType.from(typeDtoNew));
        return ResponseEntity.ok(InsuranceObjectTypeDto.from(objectType));
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable final Long id) {
        typeService.delete(id);
    }

    @GetMapping(value = "{typeId}/objects")
    public ResponseEntity<List<InsuranceObjectDto>> getTypeObjectList(@PathVariable final Long typeId) {
        List<InsuranceObject> objectList = typeService.getTypeObjectList(typeId);
        return ResponseEntity.ok(InsuranceObjectDto.fromList(objectList));
    }

    @PostMapping(value = "{typeId}/object")
    public ResponseEntity<InsuranceObjectTypeDto> createObjectForType(@PathVariable final Long typeId,
                                                                  @RequestBody InsuranceObjectDto objectDto) {
        InsuranceObjectType objectType = typeService.createObjectForType(typeId, InsuranceObject.from(objectDto));
        return ResponseEntity.ok(InsuranceObjectTypeDto.from(objectType));
    }

    @PostMapping(value = "{typeId}/objects/{objectId}")
    public ResponseEntity<InsuranceObjectTypeDto> setObjectToType(@PathVariable final Long typeId,
                                                                  @PathVariable final Long objectId) {
        InsuranceObjectType objectType = typeService.setObjectToType(typeId, objectId);
        return ResponseEntity.ok(InsuranceObjectTypeDto.from(objectType));
    }
}

