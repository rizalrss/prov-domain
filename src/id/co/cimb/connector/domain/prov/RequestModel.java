/**
 * 
 */
package id.co.cimb.connector.domain.prov;

import java.util.Date;

/**
 * @author sihendr4
 * 28 Jul 2017 - 09.51.15
 * id.co.cimb.connector.domain.prov.ReqModel.java
 */
public class RequestModel {

	  public String req_no;
	  public String password;
	  public String requestor;
	  public String beneficiary;
	  public String req_last_action_by_nik;
	  public String fullName;
	  public Date createdDate;
	  
	  public String getReq_no()
	  {
	    return this.req_no;
	  }
	  
	  public void setReq_no(String req_no)
	  {
	    this.req_no = req_no;
	  }
	  
	  public String getPassword()
	  {
	    return this.password;
	  }
	  
	  public void setPassword(String password)
	  {
	    this.password = password;
	  }
	  
	  public String getRequestor()
	  {
	    return this.requestor;
	  }
	  
	  public void setRequestor(String requestor)
	  {
	    this.requestor = requestor;
	  }
	  
	  public String getBeneficiary()
	  {
	    return this.beneficiary;
	  }
	  
	  public void setBeneficiary(String beneficiary)
	  {
	    this.beneficiary = beneficiary;
	  }
	  
	  public String getReq_last_action_by_nik()
	  {
	    return this.req_last_action_by_nik;
	  }
	  
	  public void setReq_last_action_by_nik(String req_last_action_by_nik)
	  {
	    this.req_last_action_by_nik = req_last_action_by_nik;
	  }
	  
	  public Date getCreatedDate()
	  {
	    return this.createdDate;
	  }
	  
	  public void setCreatedDate(Date createdDate)
	  {
	    this.createdDate = createdDate;
	  }
	  
	  public String getFullName()
	  {
	    return this.fullName;
	  }
	  
	  public void setFullName(String fullName)
	  {
	    this.fullName = fullName;
	  }
}
