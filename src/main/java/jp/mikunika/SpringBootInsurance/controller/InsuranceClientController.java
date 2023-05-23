package jp.mikunika.SpringBootInsurance.controller;

import jp.mikunika.SpringBootInsurance.dto.InsuranceClientDto;
import jp.mikunika.SpringBootInsurance.dto.InsurancePolicyDto;
import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;
import jp.mikunika.SpringBootInsurance.service.InsuranceClientService;
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
@RequestMapping("clients")
public class InsuranceClientController {

    private final InsuranceClientService clientService;

    @Autowired
    public InsuranceClientController(InsuranceClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<InsuranceClientDto>> getClients() {
        List<InsuranceClient> clients = clientService.findAll();
        return ResponseEntity.ok(InsuranceClientDto.fromList(clients));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<InsuranceClientDto> getOneClient(@PathVariable final Long id) {
        InsuranceClient client = clientService.findOne(id);
        return ResponseEntity.ok(InsuranceClientDto.from(client));
    }

    @PostMapping
    public ResponseEntity<InsuranceClientDto> create(@RequestBody InsuranceClientDto clientDto) {
        InsuranceClient client = clientService.save(InsuranceClient.from(clientDto));
        return ResponseEntity.ok(InsuranceClientDto.from(client));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<InsuranceClientDto> update(@PathVariable final Long id, @RequestBody InsuranceClientDto clientNewDto) {
        InsuranceClient client = clientService.update(id, InsuranceClient.from(clientNewDto));
        return ResponseEntity.ok(InsuranceClientDto.from(client));
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable final Long id) {
        clientService.delete(id);
    }

    @GetMapping(value = "{id}/policies")
    public ResponseEntity<List<InsurancePolicyDto>> getClientPolicyList(@PathVariable final Long id) {
        List<InsurancePolicy> policyList = clientService.getClientPolicyList(id);
        return ResponseEntity.ok(InsurancePolicyDto.fromList(policyList));
    }

    @PostMapping(value = "{clientId}/policies")
    public ResponseEntity<InsuranceClientDto> createPolicyForClient(@PathVariable final Long clientId, @RequestBody InsurancePolicyDto policyDto) {
        InsuranceClient client = clientService.createPolicyForClient(clientId, InsurancePolicy.from(policyDto));
        return ResponseEntity.ok(InsuranceClientDto.from(client));
    }

    @PostMapping(value = "{clientId}/policies/{policyId}")
    public ResponseEntity<InsuranceClientDto> setPolicyToClient(@PathVariable final Long clientId, @PathVariable final Long policyId) {
        InsuranceClient client = clientService.setPolicyToClient(clientId, policyId);
        return ResponseEntity.ok(InsuranceClientDto.from(client));
    }

}
