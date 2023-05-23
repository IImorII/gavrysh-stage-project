package jp.mikunika.SpringBootInsurance.service;

import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsuranceObjectType;

import java.util.List;

public interface InsuranceObjectTypeService extends BaseService<InsuranceObjectType> {

    /** GET - retrieve related objects */
    List<InsuranceObject> getTypeObjectList(Long typeId);

    /** POST without id - create a new object and connect it with current object */
    InsuranceObjectType createObjectForType(Long typeId, InsuranceObject object);

    /** POST with id - create relationship between the two existing objects */
    InsuranceObjectType setObjectToType(Long typeId, Long objectId);
}
