package jp.mikunika.SpringBootInsurance.service.impl;

import jp.mikunika.SpringBootInsurance.exception.EntityRelationsException;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;
import jp.mikunika.SpringBootInsurance.repository.InsuranceObjectTypeRepository;
import jp.mikunika.SpringBootInsurance.service.InsuranceObjectService;
import jp.mikunika.SpringBootInsurance.service.InsuranceObjectTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InsuranceObjectTypeServiceImpl implements InsuranceObjectTypeService {

    private final static String ERROR_OBJECT_HAS_TYPE = "This object already has type";

    private final InsuranceObjectTypeRepository objectTypeRepository;
    private final InsuranceObjectService objectService;

    @Autowired
    InsuranceObjectTypeServiceImpl(InsuranceObjectTypeRepository objectTypeRepository,
                                   InsuranceObjectService objectService) {
        this.objectTypeRepository = objectTypeRepository;
        this.objectService = objectService;
    }

    @Override
    public List<InsuranceObjectType> findAll() {
        return objectTypeRepository.findAll();
    }

    @Override
    public InsuranceObjectType findOne(Long id) {
        return objectTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public InsuranceObjectType save(InsuranceObjectType entity) {
        return objectTypeRepository.save(entity);
    }

    @Override
    public InsuranceObjectType update(Long id, InsuranceObjectType entityNew) {
        InsuranceObjectType objectType = findOne(id);
        BeanUtils.copyProperties(entityNew, objectType, "id");
        return objectTypeRepository.save(objectType);
    }

    @Override
    public void delete(Long id) {
        objectTypeRepository.deleteById(id);
    }

    @Override
    public List<InsuranceObject> getTypeObjectList(Long typeId) {
        InsuranceObjectType objectType = findOne(typeId);
        return List.copyOf(objectType.getInsuranceObjectList());
    }

    @Override
    public InsuranceObjectType createObjectForType(Long typeId, InsuranceObject object) {
        InsuranceObjectType objectType = findOne(typeId);
        objectService.save(object);
        object.setInsuranceObjectType(objectType);
        return save(objectType);
    }

    @Override
    public InsuranceObjectType setObjectToType(Long typeId, Long objectId) {
        InsuranceObjectType objectType = findOne(typeId);
        InsuranceObject object = objectService.findOne(objectId);
        if (object.getInsuranceObjectType() != null) {
            throw new EntityRelationsException(object.getName() + ". " +
                    ERROR_OBJECT_HAS_TYPE + ": " +
                    object.getInsuranceObjectType().getName());
        }
        object.setInsuranceObjectType(objectType);
        objectType.getInsuranceObjectList().add(object);
        return save(objectType);
    }
}
