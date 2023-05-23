package jp.mikunika.SpringBootInsurance.service;

import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;
import jp.mikunika.SpringBootInsurance.model.InsuranceOption;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;

import java.util.List;

public interface InsuranceObjectService extends BaseService<InsuranceObject> {

    /** GET - retrieve related objects */
    List<InsuranceOption> getObjectOptionList(Long objectId);
    InsuranceObjectType getObjectType(Long objectId);
    InsurancePolicy getObjectPolicy(Long objectId);

    /** POST without id - create a new object and connect it with current object */
    InsuranceObject createOptionForObject(Long objectId, InsuranceOption option);
    InsuranceObject createPolicyForObject(Long objectId, InsurancePolicy policy);
    InsuranceObject createTypeForObject(Long objectId, InsuranceObjectType objectType);

    /** POST with id - create relationship between the two existing objects */
    InsuranceObject setOptionToObject(Long objectId, Long optionId);
    InsuranceObject setPolicyToObject(Long objectId, Long policyId);
    InsuranceObject setTypeToObject(Long objectId, Long objectTypeId);

    /** DELETE - delete relation from entity */
    InsuranceObject deleteOptionFromObject(Long objectId, Long optionId);

    /** PUT - change relation */
    InsuranceObject changeTypeInObject(Long objectId, Long objectTypeId);
}
