package jp.mikunika.SpringBootInsurance.controller;

import jp.mikunika.SpringBootInsurance.dto.InsuranceClientDto;
import jp.mikunika.SpringBootInsurance.dto.InsuranceObjectDto;
import jp.mikunika.SpringBootInsurance.dto.InsurancePolicyDto;
import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;
import jp.mikunika.SpringBootInsurance.service.InsurancePolicyService;
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
@RequestMapping("policies")
public class InsurancePolicyController {
    private final InsurancePolicyService policyService;

    @Autowired
    public InsurancePolicyController(InsurancePolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public ResponseEntity<List<InsurancePolicyDto>> getPolicies() {
        List<InsurancePolicy> policyList = policyService.findAll();
        List<InsurancePolicyDto> policyDtoList = policyList.stream().map(InsurancePolicyDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(policyDtoList);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<InsurancePolicyDto> getOnePolicy(@PathVariable final Long id) {
        InsurancePolicy policy = policyService.findOne(id);
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }

    @PostMapping
    public ResponseEntity<InsurancePolicyDto> create(@RequestBody InsurancePolicyDto policyDto) {
        InsurancePolicy policy = policyService.save(InsurancePolicy.from(policyDto));
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<InsurancePolicyDto> update(@PathVariable final Long id, @RequestBody InsurancePolicyDto policyNewDto) {
        InsurancePolicy policy = policyService.update(id, InsurancePolicy.from(policyNewDto));
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable final Long id) {
        policyService.delete(id);
    }

    @GetMapping(value = "{policyId}/client")
    public ResponseEntity<InsuranceClientDto> getPolicyClient(@PathVariable final Long policyId) {
        InsuranceClient client = policyService.getPolicyClient(policyId);
        return ResponseEntity.ok(InsuranceClientDto.from(client));
    }

    @GetMapping(value = "{policyId}/objects")
    public ResponseEntity<List<InsuranceObjectDto>> getPolicyObjectList(@PathVariable final Long policyId) {
        List<InsuranceObject> objectList = policyService.getPolicyObjectList(policyId);
        return ResponseEntity.ok(InsuranceObjectDto.fromList(objectList));
    }

    @PostMapping(value = "{policyId}/objects")
    public ResponseEntity<InsurancePolicyDto> createObjectForPolicy(@PathVariable final Long policyId,
                                                                @RequestBody InsuranceObjectDto objectDto) {
        InsurancePolicy policy = policyService.createObjectForPolicy(policyId, InsuranceObject.from(objectDto));
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }

    @PostMapping(value = "{policyId}/client")
    public ResponseEntity<InsurancePolicyDto> createClientForPolicy(@PathVariable final Long policyId,
                                                                @RequestBody InsuranceClientDto clientDto) {
        InsurancePolicy policy = policyService.createClientForPolicy(policyId, InsuranceClient.from(clientDto));
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }

    @PostMapping(value = "{policyId}/objects/{objectId}")
    public ResponseEntity<InsurancePolicyDto> setObjectToPolicy(@PathVariable final Long policyId,
                                                                @PathVariable final Long objectId) {
        InsurancePolicy policy = policyService.setObjectToPolicy(policyId, objectId);
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }

    @PostMapping(value = "{policyId}/clients/{clientId}")
    public ResponseEntity<InsurancePolicyDto> setClientToPolicy(@PathVariable final Long policyId,
                                                                @PathVariable final Long clientId) {
        InsurancePolicy policy = policyService.setClientToPolicy(policyId, clientId);
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }

    @PutMapping(value = "{policyId}/clients/{clientId}")
    public ResponseEntity<InsurancePolicyDto> changeClientInPolicy(@PathVariable final Long policyId, @PathVariable final Long clientId) {
        InsurancePolicy policy = policyService.changeClientInPolicy(policyId, clientId);
        return ResponseEntity.ok(InsurancePolicyDto.from(policy));
    }
}
