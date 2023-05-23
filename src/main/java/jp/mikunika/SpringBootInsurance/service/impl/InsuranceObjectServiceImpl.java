package jp.mikunika.SpringBootInsurance.service.impl;

import jp.mikunika.SpringBootInsurance.exception.EntityRelationsException;
import jp.mikunika.SpringBootInsurance.model.*;
import jp.mikunika.SpringBootInsurance.repository.InsuranceObjectRepository;
import jp.mikunika.SpringBootInsurance.service.InsuranceObjectService;
import jp.mikunika.SpringBootInsurance.service.InsuranceObjectTypeService;
import jp.mikunika.SpringBootInsurance.service.InsuranceOptionService;
import jp.mikunika.SpringBootInsurance.service.InsurancePolicyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InsuranceObjectServiceImpl implements InsuranceObjectService {

    private final static String ERROR_OBJECT_HAS_POLICY = "This object already has policy";
    private final static String ERROR_OBJECT_HAS_TYPE = "This object already has type";
    private final static String ERROR_OBJECT_HASNT_OPTION = "This object does not have this option";

    private final InsuranceObjectRepository objectRepository;
    private final InsurancePolicyService policyService;
    private final InsuranceOptionService optionService;
    private final InsuranceObjectTypeService objectTypeService;

    @Autowired
    InsuranceObjectServiceImpl(InsuranceObjectRepository objectRepository,
                               @Lazy InsurancePolicyService policyService,
                               @Lazy InsuranceOptionService optionService,
                               @Lazy InsuranceObjectTypeService objectTypeService) {
        this.objectRepository = objectRepository;
        this.policyService = policyService;
        this.optionService = optionService;
        this.objectTypeService = objectTypeService;
    }

    @Override
    public List<InsuranceObject> findAll() {
        return objectRepository.findAll();
    }

    @Override
    public InsuranceObject findOne(Long id) {
        return objectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public InsuranceObject save(InsuranceObject entity) {
        return objectRepository.save(entity);
    }

    @Override
    public InsuranceObject update(Long id, InsuranceObject entityNew) {
        InsuranceObject object = findOne(id);
        BeanUtils.copyProperties(entityNew, object, "id");
        return objectRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        objectRepository.deleteById(id);
    }

    @Override
    public List<InsuranceOption> getObjectOptionList(Long objectId) {
        InsuranceObject object = findOne(objectId);
        return List.copyOf(object.getInsuranceOptionList());
    }

    @Override
    public InsuranceObjectType getObjectType(Long objectId) {
        InsuranceObject object = findOne(objectId);
        return object.getInsuranceObjectType();
    }

    @Override
    public InsurancePolicy getObjectPolicy(Long objectId) {
        InsuranceObject object = findOne(objectId);
        return object.getInsurancePolicy();
    }

    @Override
    public InsuranceObject createOptionForObject(Long objectId, InsuranceOption option) {
        InsuranceObject object = findOne(objectId);
        optionService.save(option);
        object.getInsuranceOptionList().add(option);
        return save(object);
    }

    @Override
    public InsuranceObject createPolicyForObject(Long objectId, InsurancePolicy policy) {
        InsuranceObject object = findOne(objectId);
        if (object.getInsurancePolicy() != null) {
            throw new EntityRelationsException(object.getName() + ". " +
                    ERROR_OBJECT_HAS_POLICY + ": " +
                    object.getInsurancePolicy().getName());
        }
        policyService.save(policy);
        object.setInsurancePolicy(policy);
        return save(object);
    }

    @Override
    public InsuranceObject createTypeForObject(Long objectId, InsuranceObjectType objectType) {
        InsuranceObject object = findOne(objectId);
        if (object.getInsuranceObjectType() != null) {
            throw new EntityRelationsException(object.getName() + ". " +
                    ERROR_OBJECT_HAS_TYPE + ": " +
                    object.getInsuranceObjectType().getName());
        }
        objectTypeService.save(objectType);
        object.setInsuranceObjectType(objectType);
        return save(object);
    }

    @Override
    public InsuranceObject setOptionToObject(Long objectId, Long optionId) {
        InsuranceObject object = findOne(objectId);
        InsuranceOption option = optionService.findOne(optionId);
        object.getInsuranceOptionList().add(option);
        return save(object);
    }

    @Override
    public InsuranceObject setPolicyToObject(Long objectId, Long policyId) {
        InsuranceObject object = findOne(objectId);
        if (object.getInsurancePolicy() != null) {
            throw new EntityRelationsException(object.getName() + ". " +
                    ERROR_OBJECT_HAS_POLICY + ": " +
                    object.getInsurancePolicy().getName());
        }
        InsurancePolicy policy = policyService.findOne(policyId);
        object.setInsurancePolicy(policy);
        return save(object);
    }

    @Override
    public InsuranceObject setTypeToObject(Long objectId, Long objectTypeId) {
        InsuranceObject object = findOne(objectId);
        if (object.getInsuranceObjectType() != null) {
            throw new EntityRelationsException(object.getName() + ". " +
                    ERROR_OBJECT_HAS_TYPE + ": " +
                    object.getInsuranceObjectType().getName());
        }
        InsuranceObjectType objectType = objectTypeService.findOne(objectTypeId);
        object.setInsuranceObjectType(objectType);
        return save(object);
    }

    @Override
    public InsuranceObject deleteOptionFromObject(Long objectId, Long optionId) {
        InsuranceObject object = findOne(objectId);
        InsuranceOption option = optionService.findOne(optionId);
        if (object.getInsuranceOptionList().contains(option)) {
            object.getInsuranceOptionList().remove(option);
            return save(object);
        } else {
            throw new EntityRelationsException(object.getName() + ". " +
                    ERROR_OBJECT_HASNT_OPTION + " " +
                    option.getName());
        }
    }

    @Override
    public InsuranceObject changeTypeInObject(Long objectId, Long objectTypeId) {
        InsuranceObject object = findOne(objectId);
        InsuranceObjectType objectType = objectTypeService.findOne(objectTypeId);
        object.setInsuranceObjectType(objectType);
        return save(object);
    }
}
