package jp.mikunika.SpringBootInsurance.service;

import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import jp.mikunika.SpringBootInsurance.model.InsuranceObject;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;

import java.util.List;

public interface InsurancePolicyService extends BaseService<InsurancePolicy> {

    /** GET - retrieve related objects */
    InsuranceClient getPolicyClient(Long policyId);
    List<InsuranceObject> getPolicyObjectList(Long policyId);

    /** POST without id - create a new object and connect it with current object */
    InsurancePolicy createObjectForPolicy(Long policyId, InsuranceObject object);
    InsurancePolicy createClientForPolicy(Long policyId, InsuranceClient client);

    /** POST with id - create relationship between the two existing objects */
    InsurancePolicy setObjectToPolicy(Long policyId, Long objectId);
    InsurancePolicy setClientToPolicy(Long policyId, Long clientId);

    InsurancePolicy changeClientInPolicy(Long policyId, Long clientId);

}
