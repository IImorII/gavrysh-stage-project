package jp.mikunika.SpringBootInsurance.service.impl;

import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceOption;
import jp.mikunika.SpringBootInsurance.repository.InsuranceOptionRepository;
import jp.mikunika.SpringBootInsurance.service.InsuranceObjectService;
import jp.mikunika.SpringBootInsurance.service.InsuranceOptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InsuranceOptionServiceImpl implements InsuranceOptionService {

    private final InsuranceOptionRepository optionRepository;
    private final InsuranceObjectService objectService;

    InsuranceOptionServiceImpl(InsuranceOptionRepository optionRepository,
                               InsuranceObjectService objectService) {
        this.optionRepository = optionRepository;
        this.objectService = objectService;
    }

    @Override
    public List<InsuranceOption> findAll() {
        return optionRepository.findAll();
    }

    @Override
    public InsuranceOption findOne(Long id) {
        return optionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public InsuranceOption save(InsuranceOption entity) {
        return optionRepository.save(entity);
    }

    @Override
    public InsuranceOption update(Long id, InsuranceOption entityNew) {
        InsuranceOption option = findOne(id);
        BeanUtils.copyProperties(entityNew, option, "id");
        return optionRepository.save(option);
    }

    @Override
    public void delete(Long id) {
        optionRepository.deleteById(id);
    }

    @Override
    public List<InsuranceObject> getOptionObjectList(Long optionId) {
        InsuranceOption option = findOne(optionId);
        return List.copyOf(option.getInsuranceObjectList());
    }

    @Override
    public InsuranceOption createObjectForOption(Long optionId, InsuranceObject object) {
        InsuranceOption option = findOne(optionId);
        objectService.save(object);
        object.getInsuranceOptionList().add(option);
        return save(option);
    }

    @Override
    public InsuranceOption setObjectToOption(Long optionId, Long objectId) {
        InsuranceOption option = findOne(optionId);
        InsuranceObject object = objectService.findOne(objectId);
        object.getInsuranceOptionList().add(option);
        return save(option);
    }
}
