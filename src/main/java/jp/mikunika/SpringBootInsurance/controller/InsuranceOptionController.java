package jp.mikunika.SpringBootInsurance.controller;

import jp.mikunika.SpringBootInsurance.dto.InsuranceObjectDto;
import jp.mikunika.SpringBootInsurance.dto.InsuranceOptionDto;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceOption;
import jp.mikunika.SpringBootInsurance.service.InsuranceOptionService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("options")
public class InsuranceOptionController {
    private final InsuranceOptionService optionService;

    @Autowired
    public InsuranceOptionController(InsuranceOptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<InsuranceOptionDto>> getObjects() {
        List<InsuranceOption> options = optionService.findAll();
        List<InsuranceOptionDto> optionsDtos = options.stream().map(InsuranceOptionDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(optionsDtos);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<InsuranceOptionDto> getOneObject(@PathVariable final Long id) {
        InsuranceOption option = optionService.findOne(id);
        return ResponseEntity.ok(InsuranceOptionDto.from(option));
    }

    @PostMapping
    public ResponseEntity<InsuranceOptionDto> create(@RequestBody InsuranceOptionDto optionDto) {
        InsuranceOption option = optionService.save(InsuranceOption.from(optionDto));
        return ResponseEntity.ok(InsuranceOptionDto.from(option));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<InsuranceOptionDto> update(@PathVariable final Long id, @RequestBody InsuranceOptionDto optionNewDto) {
        InsuranceOption option = optionService.update(id, InsuranceOption.from(optionNewDto));
        return ResponseEntity.ok(InsuranceOptionDto.from(option));
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable final Long id) {
        optionService.delete(id);
    }

    @GetMapping(value = "{optionId}/objects")
    public ResponseEntity<List<InsuranceObjectDto>> getOptionObjectList(@PathVariable final Long optionId) {
        List<InsuranceObject> objectList = optionService.getOptionObjectList(optionId);
        return ResponseEntity.ok(InsuranceObjectDto.fromList(objectList));
    }

    @PostMapping(value = "{optionId}/object")
    public ResponseEntity<InsuranceOptionDto> createObjectForOption(@PathVariable final Long optionId,
                                                                  @RequestBody InsuranceObjectDto objectDto) {
        InsuranceOption option = optionService.createObjectForOption(optionId, InsuranceObject.from(objectDto));
        return ResponseEntity.ok(InsuranceOptionDto.from(option));
    }

    @PostMapping(value = "{optionId}/objects/{objectId}")
    public ResponseEntity<InsuranceOptionDto> setObjectToOption(@PathVariable final Long optionId,
                                                                  @PathVariable final Long objectId) {
        InsuranceOption option = optionService.setObjectToOption(optionId, objectId);
        return ResponseEntity.ok(InsuranceOptionDto.from(option));
    }
}
