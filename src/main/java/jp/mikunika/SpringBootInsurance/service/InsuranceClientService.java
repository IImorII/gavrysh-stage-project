package jp.mikunika.SpringBootInsurance.service;

import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import jp.mikunika.SpringBootInsurance.model.InsurancePolicy;

import java.util.List;


public interface InsuranceClientService extends BaseService<InsuranceClient> {

    /** GET - retrieve related objects */
    List<InsurancePolicy> getClientPolicyList(Long clientId);

    /** POST without id - create a new object and connect it with current object */
    InsuranceClient createPolicyForClient(Long clientId, InsurancePolicy policy);

    /** POST with id - create relationship between the two existing objects */
    InsuranceClient setPolicyToClient(Long clientId, Long policyId);
}
