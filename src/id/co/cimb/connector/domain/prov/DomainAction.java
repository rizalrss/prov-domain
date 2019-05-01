/**
 * 
 */
package id.co.cimb.connector.domain.prov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import id.co.cimb.util.action.AppConstant;
import id.co.cimb.util.action.AppEmailAction;
import id.co.cimb.util.action.AppLogger;
import id.co.cimb.util.action.AppPayloadAction;
import id.co.cimb.util.action.AppServiceDBConnection;
import id.co.cimb.util.action.AppUtilAction;
import id.co.cimb.util.model.AppEmailModel;

/**
 * @author sihendr4
 * 21 Feb 2017 - 10.40.06
 * id.co.cimb.connector.sync.MainTask.java
 */
public class DomainAction implements Job {
	
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
 	
			AppPayloadAction  respone = new AppPayloadAction();
			AppConstant constant = new AppConstant();
			AppServiceDBConnection sDB = new AppServiceDBConnection();
			AppUtilAction util = new AppUtilAction();
			
			Date dateNow = new Date();
			SimpleDateFormat sdfDate = new SimpleDateFormat(constant.defaultFormatDateTime);
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
			String sdateNow = sdfDate.format(dateNow);
			String className = this.getClass().getName();
			String sTimeNow = sdfTime.format(dateNow);
			
			JobDetail jobDetail = jobContext.getJobDetail();
			//GET JOB DETAIL
			
			Trigger trigger = jobContext.getTrigger();
			String jobName = jobDetail.getKey().toString();
		    String sID = jobName.replaceAll("UCSJOB.job", "");
			
		    String sDbUrl = "";
			String sDbUsername= "";
			String sDbPassword = "";
			String sHostname = "";
			String sDbPort = "";
			String sDBName = "";
			
		
			String sUrlWeb = "";

			String sIp =""; //192.168.5.176
			String sPort =""; //389
			String sBaseDN=""; //DC=company,DC=com
			String sAdminFQDN=""; //CN=Administrator,CN=Users,DC=company,DC=com
			String sAdminPass=""; //Admin123!
			String sUsingSSL=""; //NO
			String sADRootDN=""; //CN=Users,DC=company,DC=com
			String sResponse = "";
			String error = "";
			String errorResponse = "";
			String sStatus = "0";
			String sResponseUrl = "";
			
			String sPasswordType = "";
			
			
			// query for search value resource
			
			String sqlConfig = " SELECT * from t_config ";
			String queryItemResource = " SELECT a.value_id,a.resource_id,a.server_param_id,a.value_name,"
					+ " b.server_id,b.param_field_name "
					+ " FROM t_resource_value AS a "
					+ " LEFT JOIN t_server_param as b on b.param_id=a.server_param_id "
					+ " LEFT JOIN t_server_type as c on c.server_id = b.server_id "
					+ " LEFT JOIN t_resource as d on d.resource_id = a.resource_id "
					+ " LEFT JOIN t_scheduler  as e ON  e.resource_id =  d.resource_id"
					+ " WHERE "
					+ " e.scheduler_id = ? "
					+ " and d.`status` = '1' "
					+ " and c.server_status = '1' ";
			 
			String idResource = "";
			
			Connection conUCSDB = null;
 			ResultSet rsItem = null;
 			ResultSet rsConfig = null;
			
 			DomainProvisioningMethod domainProvMethod = new DomainProvisioningMethod();
			String provDomain = "";
			
			try {
				
				conUCSDB = sDB.getDatabaseConnection();
				
				PreparedStatement preConfig = conUCSDB.prepareStatement(sqlConfig);
				rsConfig = preConfig.executeQuery();
				while(rsConfig.next()){
					sUrlWeb = rsConfig.getString("sURL");
				}
				rsConfig.close();
				preConfig.close();
				
				sResponseUrl = sUrlWeb+"job/updaterest";
				
				PreparedStatement preItem = conUCSDB.prepareStatement(queryItemResource);
				preItem.setString(1, sID);
				
				rsItem = preItem.executeQuery();
				int countItem = 0;
				while(rsItem.next()){
					
					String paramFieldName = rsItem.getString("param_field_name").toLowerCase();
		        	String valueName = rsItem.getString("value_name");
		        	
		        	if(paramFieldName.equals("idm db username")){
		        		sDbUsername =  valueName;
		        		if(util.isNullOrEmpty(sDbUsername)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " idm db username  is empty !<br/>";
		        		}
		        	}
		        	
		        	
		        	if(paramFieldName.equals("idm db password")){
		        		sDbPassword =  valueName;
		        		if(util.isNullOrEmpty(sDbPassword)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " idm db password is empty !<br/>";
		        		}
		        	}
		        	
		        	if(paramFieldName.equals("idm db port")){
		        		sDbPort =  valueName;
		        		if(util.isNullOrEmpty(sDbPort)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " idm db port is empty !<br/>";
		        		}
		        	}
		        	
		        	if(paramFieldName.equals("idm db hostname")){
		        		sHostname =  valueName;
		        		if(util.isNullOrEmpty(sHostname)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " idm db hostname/url is empty !<br/>";
		        		}
		        	}

		        	if(paramFieldName.equals("idm db database")){
		        		sDBName =  valueName;
		        		if(util.isNullOrEmpty(sDBName)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " idm db database name is empty !<br/>";
		        		}
		        	}
		       
		        	
 		        	//--------------
		        	
		        	if(paramFieldName.equals("ip")){
		        		sIp =  valueName;
		        		if(util.isNullOrEmpty(sIp)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " ip is empty !<br/>";
		        		}
		        	}
		        	
		        	if(paramFieldName.equals("base dn")){
		        		sBaseDN =  valueName;
		        		if(util.isNullOrEmpty(sBaseDN)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " Base DN is empty !<br/>";
		        		}
		        	}

		        	if(paramFieldName.equals("port")){
		        		sPort =  valueName;
		        		if(util.isNullOrEmpty(sPort)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + "Port is empty !<br/>";
		        		}
		        	}
		        	
		        	if(paramFieldName.equals("username")){
		        		sAdminFQDN = valueName;
		        		if(util.isNullOrEmpty(sAdminFQDN)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + "Username / Admin FQDN is empty !<br/>";
		        		}
		        	}
		        	
		        	if(paramFieldName.equals("password")){
		        		sAdminPass =  valueName;
		        		if(util.isNullOrEmpty(sAdminPass)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " Password is empty !<br/>";
		        		}
		        	}
		        	
		        	if(paramFieldName.equals("ssl")){
		        		sUsingSSL =  valueName;
		        		if(util.isNullOrEmpty(sUsingSSL)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " `Use SSL value` is not selected !<br/>";
		        		}
		        	}
		        	
		        	if(paramFieldName.equals("user root dn")){
		        		sADRootDN =  valueName;
		        		if(util.isNullOrEmpty(sADRootDN)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " User Root DN is empty !<br/>";
		        		}
		        	}
		        	
		        	if(paramFieldName.equals("password policy type")){
		        		sPasswordType =  valueName;
		        		if(util.isNullOrEmpty(sPasswordType)){
		        			error = "1";
		        			errorResponse = errorResponse + constant.failed + " Password Policy Type is empty !<br/>";
		        		}
		        	}
		        	
		        	countItem++;
				}
				
				sDbUrl = "jdbc:mysql://";
	        	sDbUrl = sDbUrl+sHostname+":"+sDbPort+"/"+sDBName;
  
				if (countItem < 1) {
		        	sResponse =  AppConstant.failed +" "+className + " Resources Target Server not found,"
		        			+ " <br/> Please Check The Resource is already exist before run this class.";
		        	error = "1";
		        }
				
				if(AppUtilAction.isNullOrEmpty(error)){
					
					DomainScanDBMethod domainMethod = new DomainScanDBMethod();
					provDomain = domainMethod.checkTable(sIp, sPort,
 							sBaseDN, sAdminFQDN,
 							sAdminPass, sUsingSSL,
 							sADRootDN, sDbUrl,
 							sDbUsername, sDbPassword,sPasswordType);
					sResponse = provDomain;
					
				}else{
					sResponse = errorResponse;
				}

				rsItem.close();
				preItem.close();

			} catch (SQLException e) {
				sResponse =  constant.failed + " "+e;
				e.printStackTrace();
				// TODO: handle exception
			}catch (Exception e) {
				sResponse =  constant.failed + " "+e;
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				if(conUCSDB != null){
					try {
						conUCSDB.close();
					} catch (Exception e2) {
						System.out.println(e2);
						// TODO: handle exception
					}
				}
				
			}

		 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			String sDateNow = sdf.format(dateNow);
				  
			Date end = new Date();
			String sEndTime = sdf.format(end);	 
			
			respone.loadParameterPayLoad(jobContext, sResponse, sStatus, sDateNow, sEndTime, sResponseUrl);
 }

	//for test connection
	/*	public String testConnection(String sDbUrl,String sDbUsername,String sDbPassword){
			String result =  AppConstant.failed;
			try {
				
				AppLogger log = new AppLogger();
				AppConstant constant = new AppConstant();
				AppUtilAction util = new AppUtilAction();
				AppServiceDBConnection dbCon = new AppServiceDBConnection();
				Connection conMySql = null;
				try {
					conMySql = dbCon.dBConnection(sDbUrl,sDbUsername,sDbPassword);
					if(conMySql !=null){
						result =  AppConstant.success;
					}
					
				} catch (Exception e) {
					result =  AppConstant.failed + " " +e.getMessage();
					e.printStackTrace();
					// TODO: handle exception
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			
			return result;
		}
	
 */
	 

  public static void main(String[] args)
  {
		AppPayloadAction  respone = new AppPayloadAction();
		AppConstant constant = new AppConstant();
		AppServiceDBConnection sDB = new AppServiceDBConnection();
		AppUtilAction util = new AppUtilAction();
		
		Date dateNow = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat(constant.defaultFormatDateTime);
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		String sdateNow = sdfDate.format(dateNow);
 		String sTimeNow = sdfTime.format(dateNow);
		
 		//GET JOB DETAIL
		
 		
	    String sDbUrl = "";
		String sDbUsername= "";
		String sDbPassword = "";
		String sHostname = "";
		String sDbPort = "";
		String sDBName = "";
		
	
		String sUrlWeb = "";

		String sIp =""; //192.168.5.176
		String sPort =""; //389
		String sBaseDN=""; //DC=company,DC=com
		String sAdminFQDN=""; //CN=Administrator,CN=Users,DC=company,DC=com
		String sAdminPass=""; //Admin123!
		String sUsingSSL=""; //NO
		String sADRootDN=""; //CN=Users,DC=company,DC=com
		String sResponse = "";
		String error = "";
		String errorResponse = "";
		String sStatus = "0";
		String sResponseUrl = "";
		String sID = "3fb5613f-1f28-4818-be17-3820d39eb915";
		String sPasswordType = "";
		
		
		// query for search value resource
		
		String sqlConfig = " SELECT * from t_config ";
		String queryItemResource = " SELECT a.value_id,a.resource_id,a.server_param_id,a.value_name,"
				+ " b.server_id,b.param_field_name "
				+ " FROM t_resource_value AS a "
				+ " LEFT JOIN t_server_param as b on b.param_id=a.server_param_id "
				+ " LEFT JOIN t_server_type as c on c.server_id = b.server_id "
				+ " LEFT JOIN t_resource as d on d.resource_id = a.resource_id "
				+ " LEFT JOIN t_scheduler  as e ON  e.resource_id =  d.resource_id"
				+ " WHERE "
				+ " e.scheduler_id = ? "
				+ " and d.`status` = '1' "
				+ " and c.server_status = '1' ";
		 
		String idResource = "";
		
		Connection conUCSDB = null;
		ResultSet rsItem = null;
		ResultSet rsConfig = null;
		
		DomainProvisioningMethod domainProvMethod = new DomainProvisioningMethod();
		String provDomain = "";
		
		try {
			
			conUCSDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/ucsdb_001", "root", "Admin123");
			
			PreparedStatement preConfig = conUCSDB.prepareStatement(sqlConfig);
			rsConfig = preConfig.executeQuery();
			while(rsConfig.next()){
				sUrlWeb = rsConfig.getString("sURL");
			}
			rsConfig.close();
			preConfig.close();
			
			sResponseUrl = sUrlWeb+"job/updaterest";
			
			PreparedStatement preItem = conUCSDB.prepareStatement(queryItemResource);
			preItem.setString(1, sID);
			
			rsItem = preItem.executeQuery();
			int countItem = 0;
			while(rsItem.next()){
				
				String paramFieldName = rsItem.getString("param_field_name").toLowerCase();
	        	String valueName = rsItem.getString("value_name");
	        	
	        	if(paramFieldName.equals("idm db username")){
	        		sDbUsername =  valueName;
	        		if(util.isNullOrEmpty(sDbUsername)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " idm db username  is empty !<br/>";
	        		}
	        	}
	        	
	        	
	        	if(paramFieldName.equals("idm db password")){
	        		sDbPassword =  valueName;
	        		if(util.isNullOrEmpty(sDbPassword)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " idm db password is empty !<br/>";
	        		}
	        	}
	        	
	        	if(paramFieldName.equals("idm db port")){
	        		sDbPort =  valueName;
	        		if(util.isNullOrEmpty(sDbPort)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " idm db port is empty !<br/>";
	        		}
	        	}
	        	
	        	if(paramFieldName.equals("idm db hostname")){
	        		sHostname =  valueName;
	        		if(util.isNullOrEmpty(sHostname)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " idm db hostname/url is empty !<br/>";
	        		}
	        	}

	        	if(paramFieldName.equals("idm db database")){
	        		sDBName =  valueName;
	        		if(util.isNullOrEmpty(sDBName)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " idm db database name is empty !<br/>";
	        		}
	        	}
	       
	        	
		        	//--------------
	        	
	        	if(paramFieldName.equals("ip")){
	        		sIp =  valueName;
	        		if(util.isNullOrEmpty(sIp)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " ip is empty !<br/>";
	        		}
	        	}
	        	
	        	if(paramFieldName.equals("base dn")){
	        		sBaseDN =  valueName;
	        		if(util.isNullOrEmpty(sBaseDN)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " Base DN is empty !<br/>";
	        		}
	        	}

	        	if(paramFieldName.equals("port")){
	        		sPort =  valueName;
	        		if(util.isNullOrEmpty(sPort)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + "Port is empty !<br/>";
	        		}
	        	}
	        	
	        	if(paramFieldName.equals("username")){
	        		sAdminFQDN = valueName;
	        		if(util.isNullOrEmpty(sAdminFQDN)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + "Username / Admin FQDN is empty !<br/>";
	        		}
	        	}
	        	
	        	if(paramFieldName.equals("password")){
	        		sAdminPass =  valueName;
	        		if(util.isNullOrEmpty(sAdminPass)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " Password is empty !<br/>";
	        		}
	        	}
	        	
	        	if(paramFieldName.equals("ssl")){
	        		sUsingSSL =  valueName;
	        		if(util.isNullOrEmpty(sUsingSSL)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " `Use SSL value` is not selected !<br/>";
	        		}
	        	}
	        	
	        	if(paramFieldName.equals("user root dn")){
	        		sADRootDN =  valueName;
	        		if(util.isNullOrEmpty(sADRootDN)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " User Root DN is empty !<br/>";
	        		}
	        	}
	        	
	        	if(paramFieldName.equals("password policy type")){
	        		sPasswordType =  valueName;
	        		if(util.isNullOrEmpty(sPasswordType)){
	        			error = "1";
	        			errorResponse = errorResponse + constant.failed + " Password Policy Type is empty !<br/>";
	        		}
	        	}
	        	
	        	countItem++;
			}
			
			System.out.println("::: sPasswordType"+sPasswordType);
			sDbUrl = "jdbc:mysql://";
        	sDbUrl = sDbUrl+sHostname+":"+sDbPort+"/"+sDBName;

			 
			
			if(AppUtilAction.isNullOrEmpty(error)){
				
				DomainScanDBMethod domainMethod = new DomainScanDBMethod();
				provDomain = domainMethod.checkTable(sIp, sPort,
							sBaseDN, sAdminFQDN,
							sAdminPass, sUsingSSL,
							sADRootDN, sDbUrl,
							sDbUsername, sDbPassword,sPasswordType);
				sResponse = provDomain;
				
			}else{
				sResponse = errorResponse;
			}

			rsItem.close();
			preItem.close();

		} catch (SQLException e) {
			sResponse =  constant.failed + " "+e;
			e.printStackTrace();
			// TODO: handle exception
		}catch (Exception e) {
			sResponse =  constant.failed + " "+e;
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			if(conUCSDB != null){
				try {
					conUCSDB.close();
				} catch (Exception e2) {
					System.out.println(e2);
					// TODO: handle exception
				}
			}
			
		}

	 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDateNow = sdf.format(dateNow);
			  
		Date end = new Date();
		String sEndTime = sdf.format(end);	 
		

    
  }
}
