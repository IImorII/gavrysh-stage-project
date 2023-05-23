package jp.mikunika.SpringBootInsurance.service.impl;

import jp.mikunika.SpringBootInsurance.exception.EntityRelationsException;
import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;
import jp.mikunika.SpringBootInsurance.repository.InsurancePolicyRepository;
import jp.mikunika.SpringBootInsurance.service.InsuranceClientService;
import jp.mikunika.SpringBootInsurance.service.InsuranceObjectService;
import jp.mikunika.SpringBootInsurance.service.InsurancePolicyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService {

    private final static String ERROR_POLICY_HAS_CLIENT = "This policy is already has client.";
    private final static String ERROR_OBJECT_HAS_POLICY = "This object is already has policy.";

    private final InsurancePolicyRepository policyRepository;
    private final InsuranceObjectService objectService;
    private final InsuranceClientService clientService;

    @Autowired
    InsurancePolicyServiceImpl(InsurancePolicyRepository policyRepository,
                               InsuranceObjectService objectService,
                               @Lazy InsuranceClientService clientService) {
        this.policyRepository = policyRepository;
        this.objectService = objectService;
        this.clientService = clientService;
    }

    @Override
    public List<InsurancePolicy> findAll() {
        return policyRepository.findAll();
    }

    @Override
    public InsurancePolicy findOne(Long id) {
        return policyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public InsurancePolicy save(InsurancePolicy entity) {
        return policyRepository.save(entity);
    }

    @Override
    public InsurancePolicy update(Long id, InsurancePolicy entityNew) {
        InsurancePolicy policy = findOne(id);
        BeanUtils.copyProperties(entityNew, policy, "id");
        return policyRepository.save(policy);
    }

    @Override
    public void delete(Long id) {
        policyRepository.deleteById(id);
    }


    @Override
    public InsuranceClient getPolicyClient(Long policyId) {
        InsurancePolicy policy = findOne(policyId);
        return policy.getClient();
    }

    @Override
    public List<InsuranceObject> getPolicyObjectList(Long policyId) {
        InsurancePolicy policy = findOne(policyId);
        return List.copyOf(policy.getInsuranceObjectList());
    }

    @Override
    public InsurancePolicy createObjectForPolicy(Long policyId, InsuranceObject object) {
        InsurancePolicy policy = findOne(policyId);
        objectService.save(object);
        object.setInsurancePolicy(policy);
        return save(policy);
    }

    @Override
    public InsurancePolicy createClientForPolicy(Long policyId, InsuranceClient client) {
        InsurancePolicy policy = findOne(policyId);
        if (policy.getClient() != null) {
            throw new EntityRelationsException(policy.getName() + ". " + ERROR_POLICY_HAS_CLIENT);
        }
        clientService.save(client);
        policy.setClient(client);
        return save(policy);
    }

    @Override
    public InsurancePolicy setObjectToPolicy(Long policyId, Long objectId) {
        InsurancePolicy policy = findOne(policyId);
        InsuranceObject object = objectService.findOne(objectId);
        if (object.getInsurancePolicy() != null) {
            throw new EntityRelationsException(object.getName() + ". " +
                    ERROR_OBJECT_HAS_POLICY + " " +
                    object.getInsurancePolicy().getName());
        }
        object.setInsurancePolicy(policy);
        return save(policy);
    }

    @Override
    public InsurancePolicy setClientToPolicy(Long policyId, Long clientId) {
        InsurancePolicy policy = findOne(policyId);
        if (policy.getClient() != null) {
            throw new EntityRelationsException(policy.getName() + ". " +
                    ERROR_POLICY_HAS_CLIENT + " " +
                    policy.getClient().getName());
        }
        InsuranceClient client = clientService.findOne(clientId);
        policy.setClient(client);
        return save(policy);
    }

    @Override
    public InsurancePolicy changeClientInPolicy(Long policyId, Long clientId) {
        InsurancePolicy policy = findOne(policyId);
        InsuranceClient client = clientService.findOne(clientId);
        policy.setClient(client);
        return save(policy);
    }
}
