package jp.mikunika.SpringBootInsurance.controller;

import jp.mikunika.SpringBootInsurance.dto.InsuranceObjectDto;
import jp.mikunika.SpringBootInsurance.dto.InsuranceObjectTypeDto;
import jp.mikunika.SpringBootInsurance.dto.InsuranceOptionDto;
import jp.mikunika.SpringBootInsurance.dto.InsurancePolicyDto;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;
import jp.mikunika.SpringBootInsurance.model.InsuranceOption;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;
import jp.mikunika.SpringBootInsurance.service.InsuranceObjectService;
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
@RequestMapping("objects")
public class InsuranceObjectController {

    private final InsuranceObjectService objectService;

    @Autowired
    public InsuranceObjectController(InsuranceObjectService objectService) {
        this.objectService = objectService;
    }

    @GetMapping
    public ResponseEntity<List<InsuranceObjectDto>> getObjects() {
        List<InsuranceObject> objects = objectService.findAll();
        return ResponseEntity.ok(InsuranceObjectDto.fromList(objects));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<InsuranceObjectDto> getOneObject(@PathVariable final Long id) {
        InsuranceObject object = objectService.findOne(id);
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @PostMapping
    public ResponseEntity<InsuranceObjectDto> create(@RequestBody InsuranceObjectDto objectDto) {
        InsuranceObject object = objectService.save(InsuranceObject.from(objectDto));
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<InsuranceObjectDto> update(@PathVariable final Long id, @RequestBody InsuranceObjectDto objectNewDto) {
        InsuranceObject object = objectService.update(id, InsuranceObject.from(objectNewDto));
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable final Long id) {
        objectService.delete(id);
    }

    @GetMapping(value = "{objectId}/options")
    public ResponseEntity<List<InsuranceOptionDto>> getObjectOptionList(@PathVariable final Long objectId) {
        List<InsuranceOption> optionList = objectService.getObjectOptionList(objectId);
        return ResponseEntity.ok(InsuranceOptionDto.fromList(optionList));
    }

    @GetMapping(value = "{objectId}/type")
    public ResponseEntity<InsuranceObjectTypeDto> getObjectType(@PathVariable final Long objectId) {
        InsuranceObjectType objectType = objectService.getObjectType(objectId);
        return ResponseEntity.ok(InsuranceObjectTypeDto.from(objectType));
    }

    @GetMapping(value = "{objectId}/policy")
    public ResponseEntity<InsurancePolicyDto> getObjectPolicy(@PathVariable final Long objectId) {
        InsurancePolicy policy = objectService.getObjectPolicy(objectId);
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }

    @PostMapping(value = "{objectId}/options")
    public ResponseEntity<InsuranceObjectDto> createOptionForObject(@PathVariable final Long objectId, @RequestBody InsuranceOptionDto optionDto) {
        InsuranceObject object = objectService.createOptionForObject(objectId, InsuranceOption.from(optionDto));
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @PostMapping(value = "{objectId}/type")
    public ResponseEntity<InsuranceObjectDto> createTypeForObject(@PathVariable final Long objectId, @RequestBody InsuranceObjectTypeDto typeDto) {
        InsuranceObject object = objectService.createTypeForObject(objectId, InsuranceObjectType.from(typeDto));
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @PostMapping(value = "{objectId}/policy")
    public ResponseEntity<InsuranceObjectDto> createPolicyForObject(@PathVariable final Long objectId, @RequestBody InsurancePolicyDto policyDto) {
        InsuranceObject object = objectService.createPolicyForObject(objectId, InsurancePolicy.from(policyDto));
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @PostMapping(value = "{objectId}/options/{optionId}")
    public ResponseEntity<InsuranceObjectDto> setOptionToObject(@PathVariable final Long objectId, @PathVariable final Long optionId) {
        InsuranceObject object = objectService.setOptionToObject(objectId, optionId);
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @PostMapping(value = "{objectId}/types/{typeId}")
    public ResponseEntity<InsuranceObjectDto> setTypeToObject(@PathVariable final Long objectId, @PathVariable final Long typeId) {
        InsuranceObject object = objectService.setTypeToObject(objectId, typeId);
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @PostMapping(value = "{objectId}/policies/{policyId}")
    public ResponseEntity<InsuranceObjectDto> setPolicyToObject(@PathVariable final Long objectId, @PathVariable final Long policyId) {
        InsuranceObject object = objectService.setPolicyToObject(objectId, policyId);
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @DeleteMapping(value = "{objectId}/options/{optionId}")
    public ResponseEntity<InsuranceObjectDto> deleteOptionFromObject(@PathVariable final Long objectId, @PathVariable final Long optionId) {
        InsuranceObject object = objectService.deleteOptionFromObject(objectId, optionId);
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }

    @PutMapping(value = "{objectId}/types/{typeId}")
    public ResponseEntity<InsuranceObjectDto> changeTypeInObject(@PathVariable final Long objectId, @PathVariable final Long typeId) {
        InsuranceObject object = objectService.changeTypeInObject(objectId, typeId);
        return ResponseEntity.ok(InsuranceObjectDto.from(object));
    }
}
