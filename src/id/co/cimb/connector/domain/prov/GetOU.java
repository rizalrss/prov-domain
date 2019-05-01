/**
 * 
 */
package id.co.cimb.connector.domain.prov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import id.co.cimb.util.action.AppUtilAction;

/**
 * @author sihendr4
 * 7 Jun 2017 - 16.35.09
 * id.co.cimb.connector.domain.prov.GetOU.java
 */
public class GetOU {

	
	public static String GetOU(String s_Host, String s_Port, String s_BaseDN,
			String s_AdminUser, String s_AdminPassword,String s_UseSSL,
			String s_ADRootDN,String sHostname,String sUsername,
			String sPassword,String lokasiKerja)
	{
		
		AppUtilAction util = new AppUtilAction();
		String HostMysql = sHostname;
		String UserMySql = sUsername;
		String PassMySql  = sPassword;
		
		String ResultOU = "OU=User Accounts,OU=Jabotabek";
 		
		if(util.isNullOrEmpty(lokasiKerja)){ 
			lokasiKerja = "ERROR"; 
		  }
		  
		  if(lokasiKerja.contains("Kantor Pusat")){ 
			  lokasiKerja = "Kantor Pusat"; 
		  }
		  
		  if(!util.isNullOrEmpty(lokasiKerja)){
			 if(!lokasiKerja.equals("ERROR") && !lokasiKerja.contains("Kantor Pusat")){
				 lokasiKerja =  lokasiKerja.substring(lokasiKerja.length() - 3); 
			 } 
			}
	  
		Connection conMySqlOU =  null;
 		ResultSet rsGetOu = null;
		ResultSet rsGetOuErr = null;
 		PreparedStatement preGetOU = null;
		int countBranchOu = 0;
		String queryGetOu = "select * from t_branch_ou where kode_area like ? "; 

		try {
			conMySqlOU = DriverManager.getConnection(HostMysql, UserMySql, PassMySql);

			preGetOU =  conMySqlOU.prepareStatement(queryGetOu);
			preGetOU.setString(1, lokasiKerja);
			rsGetOu = preGetOU.executeQuery();
			while(rsGetOu.next()){
				countBranchOu =+1;
				ResultOU = rsGetOu.getString("ou_name");
				countBranchOu++;

			}
			rsGetOu.close();
			if(countBranchOu < 1){
 				lokasiKerja = "ERROR";
				preGetOU.setString(1, lokasiKerja);
				rsGetOuErr = preGetOU.executeQuery();
				while(rsGetOuErr.next()){
					ResultOU = rsGetOuErr.getString("ou_name");
				}
				rsGetOuErr.close();	
			}
			
			
			
			} catch (SQLException e) {
			 System.out.println("error "+e);
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(preGetOU != null){
				try {
					preGetOU.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
			if(conMySqlOU != null){
				try {
					conMySqlOU.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}	
		}
		
		if(!util.isNullOrEmpty(ResultOU) ){
			ADCommonMethod commonMethod = new ADCommonMethod();
			String checkOu = commonMethod.checkOUExist(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, ResultOU);
			
				String[] getSimpleOU = ResultOU.split(","); 
				if(util.isNullOrEmpty(checkOu)){
					String ouUserAccount =  "OU=User Accounts,";
					String finalOU = "";
					int lengthOU = getSimpleOU.length-1;
					for(int i=1; i<lengthOU;i++){
						finalOU = finalOU+getSimpleOU[i+1];

						if(i!=lengthOU+2){
							finalOU+=",";
						}else{
						}
						
					}
					
					ResultOU =  ouUserAccount+finalOU;
					ResultOU = ResultOU.replaceFirst(".$", "");
					System.out.println(":::: ini ::"+ResultOU);
					checkOu = commonMethod.checkOUExist(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, ResultOU);
						if(util.isNullOrEmpty(checkOu)){
							lokasiKerja = "ERROR";
							String resultCheck = GetOU(s_Host,   s_Port,   s_BaseDN,
									  s_AdminUser,   s_AdminPassword,  s_UseSSL,
									  s_ADRootDN,  sHostname,  sUsername,
									  sPassword,  lokasiKerja);
							System.out.println("::::::::"+resultCheck);
						}
					 
				}
			
			
		}
		
		return ResultOU;
	}

  
	public static void main (String arg[]){
		/*String lokasiKerja = " ";
		String sDriver = "com.mysql.jdbc.Driver";
		String sUrl    = "jdbc:mysql://localhost:3306/ucsdb";
		String UserID ="root"; 
		String Passwd="123456";
		String resultIs  = GetOU(sUrl,UserID,Passwd,lokasiKerja);
		System.out.println(resultIs);*/
	}
}
