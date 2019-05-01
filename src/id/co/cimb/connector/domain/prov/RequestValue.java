/**
 * 
 */
package id.co.cimb.connector.domain.prov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import id.co.cimb.util.action.AppUtilAction;

/**
 * @author sihendr4
 * 28 Jul 2017 - 09.58.56
 * id.co.cimb.connector.domain.prov.RequestValue.java
 */
public class RequestValue {
	 public static RequestModel req(String sHostname, String sUsername, String sPassword, String reqNo, String reqObj)
	  {
	    AppUtilAction util = new AppUtilAction();
	    String HostMysql = sHostname;
	    String UserMySql = sUsername;
	    String PassMySql = sPassword;
	    RequestModel reqModel = new RequestModel();
	    
	    Connection conMySql = null;
	    ResultSet rsGet = null;
	    String email = "";
	    String sc_key = "";
	    String requestor = "";
	    String beneficiary = "";
	    String headBeneficiary = "";
	    Date createdDate = null;
	    String fullName = "";
	    
	    PreparedStatement preGet = null;
	    
	    String tableNameTappDomain = "tapp_domain";
	    if (reqObj.equals("2")) {
	      tableNameTappDomain = "tapp_domain_edit";
	    }
	    if (reqObj.equals("3")) {
	      tableNameTappDomain = "tapp_domain_delete";
	    }
	    if (reqObj.equals("4")) {
	      tableNameTappDomain = "tapp_domain_reset";
	    }
	    String queryGet = "SELECT req.*,reqchild.*,reqdomain.*  from t_rrequest as req   join t_rrequest_detail as reqchild on reqchild.req_header_id = req.id  join " + 
	    

	      tableNameTappDomain + " as reqdomain on reqchild.req_no = reqdomain.req_no  " + 
	      " where reqchild.req_no = ? " + 
	      " and req.req_status =  ? ";
	    

	    String getResult = "";
	    try
	    {
	      conMySql = DriverManager.getConnection(HostMysql, UserMySql, PassMySql);
	      preGet = conMySql.prepareStatement(queryGet);
	      preGet.setString(1, reqNo);
	      preGet.setString(2, "3");
	      

	      rsGet = preGet.executeQuery();
	      while (rsGet.next())
	      {
	        getResult = rsGet.getString("sc_number");
	        sc_key = rsGet.getString("sc_number");
	        requestor = rsGet.getString("req_requestor");
	        beneficiary = rsGet.getString("req_beneficiary");
	        headBeneficiary = rsGet.getString("req_nik_email");
	        createdDate = rsGet.getTimestamp("created_at");
	        fullName = rsGet.getString("name");
	      }
	      reqModel.setBeneficiary(beneficiary);
	      reqModel.setRequestor(requestor);
	      reqModel.setReq_no(reqNo);
	      reqModel.setPassword(sc_key);
	      reqModel.setReq_last_action_by_nik(headBeneficiary);
	      reqModel.setCreatedDate(createdDate);
	      reqModel.setFullName(fullName);
	      rsGet.close();
	    }
	    catch (SQLException e)
	    {
	      System.out.println("error " + e);
	      if (preGet != null) {
	        try
	        {
	          preGet.close();
	        }
	        catch (Exception localException) {}
	      }
	      if (conMySql != null) {
	        try
	        {
	          conMySql.close();
	        }
	        catch (Exception localException1) {}
	      }
	    }
	    catch (Exception localException2)
	    {
	      if (preGet != null) {
	        try
	        {
	          preGet.close();
	        }
	        catch (Exception localException3) {}
	      }
	      if (conMySql != null) {
	        try
	        {
	          conMySql.close();
	        }
	        catch (Exception localException4) {}
	      }
	    }
	    finally
	    {
	      if (preGet != null) {
	        try
	        {
	          preGet.close();
	        }
	        catch (Exception localException5) {}
	      }
	      if (conMySql != null) {
	        try
	        {
	          conMySql.close();
	        }
	        catch (Exception localException6) {}
	      }
	    }
	    return reqModel;
	  }
}
