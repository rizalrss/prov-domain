package id.co.cimb.connector.domain.prov;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import id.co.cimb.util.action.AppConstant;
import id.co.cimb.util.action.AppLogger;
import id.co.cimb.util.action.AppServiceDBConnection;
import id.co.cimb.util.action.AppUtilAction;

public class DomainProvisioningMethod {

	
// 	public String s_Host;
//	public String s_Port;
//	public String s_BaseDN;
//	public String s_AdminUser;
//	public String s_AdminPassword; 
//	public String s_UseSSL;
	
	String s_Host = "192.168.86.185";
	String s_Port = "389";
	String s_BaseDN = "DC=ArabITPro,DC=local";
	String s_AdminUser = "CN=rizal sobar. rosa,CN=Users,DC=ArabITPro,DC=local";//"CN=Administrator,CN=Users,DC=company,DC=com";
	String s_AdminPassword = "Bintaro1!";
	String s_UseSSL = "NO";
	String s_ADRootDN="CN=Users,DC=company,DC=com";
	String s_DomainName = "ArabITPro";
	String s_UserDN = "CN=Users";
	 
	//Class for create connection in Active Directory
	public LdapContext Connect(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword
	        , String s_UseSSL)
	{
	        LdapContext ctx = null;
	        try
	        {
	                Hashtable < String, String > env = new Hashtable < String, String > ();
	                env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
	                env.put("java.naming.security.authentication", "simple");
	                env.put("java.naming.security.principal", s_AdminUser);
	                env.put("java.naming.security.credentials", s_AdminPassword);
	                s_UseSSL = s_UseSSL.toUpperCase();
	                if (s_UseSSL.toUpperCase()
	                        .equals("YES"))
	                {
	                        env.put(LdapContext.SECURITY_PROTOCOL, "ssl");
	                }
	                
	                env.put("java.naming.provider.url", "ldap://" + s_Host + ":" + s_Port);
	                System.setProperty("javax.net.ssl.trustStore", "C:/Program Files/Java/jre7/lib/security/cacerts");
	    	        System.setProperty("javax.net.ssl.trustStorePassword","changeit");
	    	      
	                ctx = new InitialLdapContext(env, null);
	                //System.out.println("Connected");
	        }
	        catch (Exception e)
	        {
	                e.printStackTrace();
	        }
	        return ctx;
	}

	//Connector Create User
	public String CreateUser(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String s_ADRootDN
	        , String s_UserLogon
	        , String s_FirstName, String s_Initial, String s_LastName
	        , String s_FullName, String s_Password, String s_CountryCode
	        , String s_ObjectGuid, String s_UserPrincipalName, String s_MustChangePassword
	        , String s_EmployeeID, String s_PhysicalDeliveryOfficeName, String s_Mail
	        , String s_Mobile, String s_Department, String s_Manager
	        , String s_telephoneNumber, String s_wWWHomePage
	        , String s_streetAddress, String s_postOfficeBox, String s_l
	        , String s_st, String s_postalCode, String s_tittle
	        , String s_department, String s_company, String s_homePhone
	        , String s_pager, String s_mobile, String s_facsimileTelephoneNumber
	        , String s_ipPhone, String s_info, String s_profilePath
	        , String s_scriptPath, String s_initials, String s_sAMAccountName, String s_description, String lokasiKerja)
	{

	        //Fill in Variable & Validasi 
	        AppLogger log = new AppLogger();
	        AppConstant constant = new AppConstant();
	        AppServiceDBConnection sdb = new AppServiceDBConnection();
	        AppUtilAction util = new AppUtilAction();
	        String result = constant.failed;
	        SimpleDateFormat sdf02 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	        try
	        {
	                Date now = new Date();
 	                //Open connection
	                LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
 	                ADCommonMethod commonMehod = new ADCommonMethod();
	                String findUser = commonMehod.findBysAMAccountName(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_sAMAccountName);
	                if (util.isNullOrEmpty(findUser))
	                {


	                        String s_UserName = "CN=" + s_UserLogon + "," + s_ADRootDN;

	                        BasicAttributes attrs = new BasicAttributes();
	                        BasicAttribute ocs = new BasicAttribute("objectClass");
	                        ocs.add("top");
	                        ocs.add("person");
	                        ocs.add("user");
	                        ocs.add("organizationalPerson");
	                        attrs.put(ocs);

	                        if (!s_sAMAccountName.equals("") && !(s_sAMAccountName == null))
	                                attrs.put("sAMAccountName", s_sAMAccountName);

	                        if (!s_FirstName.equals("") && !(s_FirstName == null))
	                                attrs.put("givenName", s_FirstName);

	                        if (!s_LastName.equals("") && !(s_LastName == null))
	                                attrs.put("sn", s_LastName);

	                        if (!s_FullName.equals("") && !(s_FullName == null))
	                                attrs.put("displayName", s_FullName);

	                        if (!s_UserLogon.equals("") && !(s_UserLogon == null))
	                                attrs.put("cn", s_UserLogon);

	                        if (!s_CountryCode.equals("") && !(s_CountryCode == null))
	                        {
	                                attrs.put("countryCode", s_CountryCode);
	                        }

	                        if (!s_ObjectGuid.equals("") && !(s_ObjectGuid == null))
	                        {
	                                attrs.put("objectGUID", s_ObjectGuid);
	                        }

	                        if (!s_UserPrincipalName.equals("") && !(s_UserPrincipalName == null))
	                        {
	                                attrs.put("userPrincipalName", s_UserPrincipalName);
	                        }

	                        if (!s_EmployeeID.equals("") && !(s_EmployeeID == null))
	                        {
	                                attrs.put("employeeID", s_EmployeeID);
	                        }

	                        if (!util.isNullOrEmpty(s_PhysicalDeliveryOfficeName))
	                        {
	                                attrs.put("physicalDeliveryOfficeName", lokasiKerja);
	                        }

	                        /*if (!util.isNullOrEmpty(s_Mail))
	                        {
	                                attrs.put("mail", s_Mail);
	                        }*/

	                        if (!util.isNullOrEmpty(s_Mobile))
	                        {
	                                attrs.put("mobile", s_Mobile);
	                        }

	                        if (!util.isNullOrEmpty(s_Department))
	                        {
	                                attrs.put("department", s_Department);
	                        }

	                        if (!util.isNullOrEmpty(s_Manager))
	                        {
	                                ADCommonMethod commMethod = new ADCommonMethod();
	                                String sSupervisorName = s_Manager;
	                                //boolean findManager = commMethod.FindUser(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, sSupervisorName);
	                                String findManager = commMethod.findBysAMAccountName(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, sSupervisorName);
	                                if (!util.isNullOrEmpty(findManager))
	                                {
	                                        String s_managerAcount = findManager;
	                                        attrs.put("manager", s_managerAcount);
	                                }

	                        }

	                        if (!util.isNullOrEmpty(s_telephoneNumber))
	                        {
	                                attrs.put("telephoneNumber", s_telephoneNumber);
	                        }
	                        if (!util.isNullOrEmpty(s_wWWHomePage))
	                                attrs.put("wWWHomePage", s_wWWHomePage);

	                        if (!util.isNullOrEmpty(s_streetAddress))
	                                attrs.put("streetAddress", s_streetAddress);

	                        if (!util.isNullOrEmpty(s_postOfficeBox))
	                                attrs.put("postOfficeBox", s_postOfficeBox);

	                        if (!util.isNullOrEmpty(s_l))
	                                attrs.put("l", s_l);

	                        if (!util.isNullOrEmpty(s_st))
	                                attrs.put("st", s_st);

	                        if (!util.isNullOrEmpty(s_postalCode))
	                                attrs.put("postalCode", s_postalCode);

	                        if (!util.isNullOrEmpty(s_tittle))
	                                attrs.put("title", s_tittle);

	                        if (!util.isNullOrEmpty(s_department))
	                                attrs.put("department", s_department);

	                        if (!util.isNullOrEmpty(s_company))
	                                attrs.put("company", s_company);

	                        if (!util.isNullOrEmpty(s_homePhone))
	                                attrs.put("homePhone", s_homePhone);

	                        if (!util.isNullOrEmpty(s_pager))
	                                attrs.put("pager", s_pager);

	                        if (!util.isNullOrEmpty(s_mobile))
	                                attrs.put("mobile", s_mobile);

	                        if (!util.isNullOrEmpty(s_facsimileTelephoneNumber))
	                                attrs.put("facsimileTelephoneNumber", s_facsimileTelephoneNumber);

	                        if (!util.isNullOrEmpty(s_ipPhone))
	                                attrs.put("ipPhone", s_ipPhone);

	                        if (!util.isNullOrEmpty(s_info))
	                                attrs.put("info", s_info);

	                        if (!util.isNullOrEmpty(s_profilePath))
	                                attrs.put("profilePath", s_profilePath);

	                        if (!util.isNullOrEmpty(s_scriptPath))
	                                attrs.put("scriptPath", s_scriptPath);

	                        /*if(!s_co.equals("") && !(s_co))
	                        	attrs.put("co",s_co);*/

	                        if (!util.isNullOrEmpty(s_initials))
	                                attrs.put("initials", s_initials);

	                        if (!util.isNullOrEmpty(s_description))
	                                attrs.put("description", s_description);


	                        /* Some useful Constant */
	                        int UF_ACCOUNTDISABLE = 0x0002;
	                        int UF_PASSWD_NOTREQD = 0x0020;
	                        int UF_ACCOUNTLOCKEDOUT = 0x0010;
	                        int UF_PASSWD_CANT_CHANGE = 0x0040;
	                        int UF_NORMAL_ACCOUNT = 0x0200;
	                        int UF_DONT_EXPIRE_PASSWD = 0x10000;
	                        int UF_PASSWORD_EXPIRED = 0x800000;

	                        s_MustChangePassword = "1";
	                        /* Set Normal Account with No Password */
	                        attrs.put("userAccountControl", Integer.toString(UF_NORMAL_ACCOUNT + UF_ACCOUNTDISABLE));

	                        //Create User
	                        ctx.createSubcontext(s_UserName, attrs);

	                        ModificationItem[] mods = new ModificationItem[1];

	                        //mofication for user password
	                        String newQuotedPassword = "\"" + s_Password + "\"";

	                        char unicodePwd[] = newQuotedPassword.toCharArray();
	                        byte pwdArray[] = new byte[unicodePwd.length * 2];
	                        for (int i = 0; i < unicodePwd.length; i++)
	                        {
	                                pwdArray[i * 2 + 1] = (byte)(unicodePwd[i] >>> 8);
	                                pwdArray[i * 2 + 0] = (byte)(unicodePwd[i] & 0xff);
	                        }
	                        //System.out.println("encoded Password :");
	                        for (int i = 0; i < pwdArray.length; i++)
	                        {
	                                //System.out.print(pwdArray[i] + " ");
	                        }

	                        //System.out.println("newQuotedPasswordL::" + newQuotedPassword);
	                        byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
	                        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", newUnicodePassword));
	                        ctx.modifyAttributes(s_UserName, mods);

	                        if (s_MustChangePassword.equals("1"))
	                                mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("pwdLastSet", "0"));
	                        ctx.modifyAttributes(s_UserName, mods);

	                        result = constant.success + " created user. user : " + s_UserLogon;
	                }
	                else
	                {
	                        result = constant.failed + " user already exist";
	                }


	        }catch (NameNotFoundException ex) {
	        	ex.printStackTrace();
	        	result = constant.failed + " " + ex.getCause();
				// TODO: handle exception
			}
	        catch (NameAlreadyBoundException ex2) {
	        	ex2.printStackTrace();
	        	result = constant.failed + " " + ex2.getCause();
				// TODO: handle exception
			}catch (NamingException ex) {
	        	ex.printStackTrace();
	        	result = constant.failed + " " + ex.getCause();
				// TODO: handle exception
			}
	        catch (Exception e){
	                e.printStackTrace();
	                result = constant.failed + " " + e;
	                //System.out.println("result "+result);
	        }

	        Date endDate = new Date();
 	        return result;
	}

	//Connector Delete User	
	/*public static String DeleteUser(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
				String s_UseSSL, String s_ADRootDN, String s_UserLogon)
		{
			try {
				//open connection
				LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
				
				//validate data entry
				String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
				System.out.println(s_UserName);
				
				//delete user

				 Some useful Constant 
				int UF_ACCOUNTDISABLE = 0x0002;
				int UF_PASSWD_NOTREQD = 0x0020;
				int UF_PASSWD_CANT_CHANGE = 0x0040;
				int UF_NORMAL_ACCOUNT = 0x0200;
				int UF_DONT_EXPIRE_PASSWD = 0x10000;
				int UF_PASSWORD_EXPIRED = 0x800000;
					
				ctx.unbind(s_UserName);
				System.out.println("User deleted.");
				//String result = "C";
				
				//close connection
				ctx.close();
			
			} catch (NamingException e) {e.printStackTrace();
			}
		
			return "OK";
		}*/
		
		/*
		//Connector Change Password
		public static String ChangePassword(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
				String s_UseSSL, String s_DomainName, String s_UserLogon, String s_Password, String s_ADRootDN) 
		{		
			try
			{
				//open connection
				LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
				
				//validate data entry
				String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
				System.out.println(s_UserName);
	        	
	        	
				//Change Password user
				ModificationItem[] mods = new ModificationItem[1];
				String newQuotedPassword = "12345";
				byte[] newUnicodePassword =  newQuotedPassword.getBytes("UTF-16LE");
					
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userpassword", newUnicodePassword));
				ctx.modifyAttributes(s_UserName, mods);
				System.out.println("Password updated.");
				//result = "C";
				
				//close connection
				ctx.close();
		
			}
			catch (NamingException e) { System.err.println("Problem updating user's password : " + e); }		
			catch(Exception e) { e.printStackTrace(); }
			
			return "OK";
		}*/
		
		/*
		//Connector AddUserGroup
		public static String AddUserGroup(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
				String s_UseSSL, String s_DomainName, String s_UserLogon, String s_GroupName,
				String s_ADRootDN) 
		{
			try
			{
				//open connection
				LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
			
				//validate data entry
				String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
				System.out.println(s_UserName);
						
				//AddGroup
				ModificationItem member[] = new ModificationItem[1];
				member[0]= new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("member", s_UserName)); 
						
				ctx.modifyAttributes(s_GroupName, member);
				System.out.println("Added user to group: " + s_GroupName);
				//String result = "C";
							
				//close connection
				ctx.close();
			    		
			} 
			catch (NamingException e) {e.printStackTrace();}
			return "OK";
		}
		*/
		
		/*
		//Connector RemoveUserGroup Group
		public static String RemoveUserGroup(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
				String s_UseSSL, String s_DomainName, String s_UserLogon, String s_GroupName, String s_ADRootDN) 
		{
			try
			{	
				//open connection
				LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
				
				//validate data entry
				String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
				System.out.println(s_UserName);
							
				//RemoveGroup
				ModificationItem member[] = new ModificationItem[1];
				member[0]= new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("member", s_UserName)); 
			
				ctx.modifyAttributes(s_GroupName, member);
				System.out.println("Removed user to group: " + s_GroupName);
				//String result = "C";
								
				//close connection
				ctx.close();
				    		
			}
			catch (NamingException e) { System.err.println("Problem removing user to group: " + e); }		
			catch(Exception e) { e.printStackTrace(); }
				
			return "OK";
		}*/
		
	
		/*
		//Connector Locked Account
		public static String LockAccount(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
				String s_UseSSL, String s_UserLogon, String s_ADRootDN) 
		{
			try
			{
				//open connection
				LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
				
				//validate data entry
				String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
				System.out.println(s_UserName);
				
				//Locked Account        	
				 Some useful Constant 
				int UF_ACCOUNTDISABLE = 0x0002;
				int UF_ACCOUNTLOCKEDOUT = 0x0010;
				int UF_PASSWD_NOTREQD = 0x0020;
				int UF_PASSWD_CANT_CHANGE = 0x0040;
				int UF_NORMAL_ACCOUNT = 0x0200;
				int UF_DONT_EXPIRE_PASSWD = 0x10000;
				int UF_PASSWORD_EXPIRED = 0x800000;
				
				ModificationItem[] mods = new ModificationItem[1];					
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl",Integer.toString(UF_ACCOUNTLOCKEDOUT)));
				ctx.modifyAttributes(s_UserName, mods);
				System.out.println("User Locked.");
				//String result = "C";
				
				//close connection
				ctx.close();
			}
			catch (NamingException e) { System.err.println("Problem when try to lock user : " + e); }
			catch(Exception e) { e.printStackTrace(); }
		
			return "OK";
		}*/
		
	    /*
		//Connector UnLocked Account
		public static String UnLockAccount(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
				String s_UseSSL, String s_UserLogon, String s_ADRootDN) 
		{
			try
			{
				//open connection
				LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
					
				//validate data entry
				String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
				System.out.println(s_UserName);
					
				//Locked Account        	
				 Some useful Constant 
				int UF_ACCOUNTDISABLE = 0x0002;
				int UF_ACCOUNTLOCKEDOUT = 0x0010;
				int UF_PASSWD_NOTREQD = 0x0020;
				int UF_PASSWD_CANT_CHANGE = 0x0040;
				int UF_NORMAL_ACCOUNT = 0x0200;
				int UF_DONT_EXPIRE_PASSWD = 0x10000;
				int UF_PASSWORD_EXPIRED = 0x800000;
					
				ModificationItem[] mods = new ModificationItem[1];					
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl",Integer.toString(UF_NORMAL_ACCOUNT)));
				ctx.modifyAttributes(s_UserName, mods);
				System.out.println("User Unlocked.");
				//String result = "C";
					
				//close connection
				ctx.close();
			}
			catch (NamingException e) { System.err.println("Problem when try to unlock user : " + e); }
			catch(Exception e) { e.printStackTrace(); }
				
			return "OK";
		}*/

		/*
		//Connector Disable Account
		public static String DisableAccount(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
				String s_UseSSL, String s_UserLogon, String s_ADRootDN) 
		{
			try
			{
				//open connection
				LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
					
				//validate data entry
				String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
				System.out.println(s_UserName);
					
				//Locked Account        	
				 Some useful Constant 
				int UF_ACCOUNTDISABLE = 0x0002;
				int UF_PASSWD_NOTREQD = 0x0020;
				int UF_PASSWD_CANT_CHANGE = 0x0040;
				int UF_NORMAL_ACCOUNT = 0x0200;
				int UF_DONT_EXPIRE_PASSWD = 0x10000;
				int UF_PASSWORD_EXPIRED = 0x800000;
				
				ModificationItem[] mods = new ModificationItem[1];					
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl",Integer.toString(UF_ACCOUNTDISABLE)));
				ctx.modifyAttributes(s_UserName, mods);
				System.out.println("User disabled.");
				//String result = "C";
					
				//close connection
				ctx.close();
			}
			catch (NamingException e) { System.err.println("Problem disable user : " + e); }
			catch(Exception e) { e.printStackTrace(); }
			
			return "OK";
		}*/
		
	
		/*
		//Connector Enable Account
		public static String EnableAccount(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
				String s_UseSSL, String s_UserLogon, String s_ADRootDN) 
		{
			try
			{
				//open connection
				LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
						
				//validate data entry
				String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
				System.out.println(s_UserName);
						
				//Locked Account        	
				 Some useful Constant 
				int UF_ACCOUNTDISABLE = 0x0002;
				int UF_PASSWD_NOTREQD = 0x0020;
				int UF_PASSWD_CANT_CHANGE = 0x0040;
				int UF_NORMAL_ACCOUNT = 0x0200;
				int UF_DONT_EXPIRE_PASSWD = 0x10000;
				int UF_PASSWORD_EXPIRED = 0x800000;
				
				ModificationItem[] mods = new ModificationItem[1];					
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl",Integer.toString(UF_NORMAL_ACCOUNT)));
				ctx.modifyAttributes(s_UserName, mods);
				System.out.println("User enabled.");
				//String result = "C";
						
				//close connection
				ctx.close();
			}
			catch (NamingException e) { System.err.println("Problem enable user : " + e); }
			catch(Exception e) { e.printStackTrace(); }
				
			return "OK";
		}
		*/

	//Connector Update Attribute Account
	public String UpdateAttribute(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword
	        , String s_UseSSL, String s_DomainName, String s_UserLogon, String s_sAMAccountName, String s_AttributeName, String s_AttributeNameValue
	        , String s_ADRootDN)
	{

	        AppLogger log = new AppLogger();
	        AppConstant constant = new AppConstant();
	        AppServiceDBConnection sdb = new AppServiceDBConnection();
	        AppUtilAction util = new AppUtilAction();
	        String result = constant.failed;
	        SimpleDateFormat sdf02 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	        try
	        {
	                //open connection
	                LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);

	                ADCommonMethod commonMehod = new ADCommonMethod();
	                String findUser = commonMehod.findBysAMAccountName(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_sAMAccountName);
 	                if (util.isNullOrEmpty(findUser))
	                {
	                        //String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
 	                        result = constant.failed + " account is not found";
	                }
	                else
	                {
 	                        String s_UserName = findUser;
 
	                        //Locked Account        	
	                        ModificationItem attr[] = new ModificationItem[1];
	                        attr[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(s_AttributeName, s_AttributeNameValue));

	                        ctx.modifyAttributes(s_UserName, attr);
	                        result = constant.success + " modify user : " + s_UserLogon+" (update attribute : "+s_AttributeName+") ";
	                        
 	                }

	                //close connection
	                ctx.close();
	        }
	        catch (NamingException e)
	        {
	                System.err.println("Problem updating user's attribute : " + e);
	                result = constant.failed + "" + e;
	        }
	        catch (Exception e)
	        {
	                result = constant.failed + "" + e;
	                e.printStackTrace();
	        }

	        return result;
	}
	
	
	//Connector Update Attribute Account
		public String UpdateAttribute(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword
		        , String s_UseSSL, String s_DomainName, String s_UserLogon, String s_sAMAccountName, List attributeNameAndValues
		        , String s_ADRootDN)
		{

		        AppLogger log = new AppLogger();
		        AppConstant constant = new AppConstant();
		        AppServiceDBConnection sdb = new AppServiceDBConnection();
		        AppUtilAction util = new AppUtilAction();
		        String result = constant.failed;
		        SimpleDateFormat sdf02 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

		        try
		        {
		                //open connection
		                LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);

		                ADCommonMethod commonMehod = new ADCommonMethod();
		                String findUser = commonMehod.findBysAMAccountName(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_sAMAccountName);
	 	                if (util.isNullOrEmpty(findUser))
		                {
		                        //String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
	 	                        result = constant.failed + " account is not found";
		                }
		                else
		                {/*
	 	                        String s_UserName = findUser;
	 	                        int countAttribute = attributeNameAndValues.size();
		                        if(!attributeNameAndValues.isEmpty()){
		                        Iterator itr = attributeNameAndValues.iterator();
		                        while(itr.hasNext()){
		                        	Map map = (Map) itr.next();
		                        	//String attributeName = 
		                        }
		                         
	 	                        
		                        ModificationItem attr[] = new ModificationItem[countAttribute];
		                         
		                        attr[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(s_AttributeName, s_AttributeNameValue));

		                        ctx.modifyAttributes(s_UserName, attr);
		                        result = constant.success + " modify user : " + s_UserLogon+" (update attribute : "+s_AttributeName+") ";
		                        }*/
	 	                }

		                //close connection
		                ctx.close();
		        }
		        catch (NamingException e)
		        {
		                System.err.println("Problem updating user's attribute : " + e);
		                result = constant.failed + "" + e;
		        }
		        catch (Exception e)
		        {
		                result = constant.failed + "" + e;
		                e.printStackTrace();
		        }

		        return result;
		}

	//Connector Change Password
	public String ChangePassword(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword
	        , String s_UseSSL, String s_DomainName, String s_UserLogon, String s_Password, String s_ADRootDN)
	{
	        //Fill in Variable & Validasi 
	        AppLogger log = new AppLogger();
	        AppConstant constant = new AppConstant();
	        AppServiceDBConnection sdb = new AppServiceDBConnection();
	        AppUtilAction util = new AppUtilAction();
	        String result = constant.failed;
	        SimpleDateFormat sdf02 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	        try
	        {

	                Date now = new Date();
	                System.out.println("start provisioning reset user domain : " + sdf02.format(now));
	                //open connection
	                LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);

	                ADCommonMethod commonMehod = new ADCommonMethod();

	                String findUser = commonMehod.findBysAMAccountName(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_UserLogon);
	                if (util.isNullOrEmpty(findUser))
	                {
	                        result = constant.failed + s_UserLogon+" account is not found";
	                }
	                else
	                {
	                        //validate data entry
	                        String s_UserName = findUser; //"CN="+ s_UserLogon +","+ s_ADRootDN;

	                        //Change Password user
	                        ModificationItem[] mods = new ModificationItem[2];
	                        //mofication for user password
	                        String newQuotedPassword = "\"" + s_Password + "\"";

	                        char unicodePwd[] = newQuotedPassword.toCharArray();
	                        byte pwdArray[] = new byte[unicodePwd.length * 2];
	                        for (int i = 0; i < unicodePwd.length; i++)
	                        {
	                                pwdArray[i * 2 + 1] = (byte)(unicodePwd[i] >>> 8);
	                                pwdArray[i * 2 + 0] = (byte)(unicodePwd[i] & 0xff);
	                        }
	                        //System.out.println("encoded Password :");
	                        for (int i = 0; i < pwdArray.length; i++)
	                        {
	                                //System.out.print(pwdArray[i] + " ");
	                        }
	                        //System.out.println("");


	                        System.out.println("newQuotedPassword::" + newQuotedPassword);
	                        byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
	                        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", newUnicodePassword));
	                        mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("pwdLastSet", Integer.toString(0)));
	                        ctx.modifyAttributes(s_UserName, mods);
	                        System.out.println("Password updated.");
	                        result = constant.success + "reset password";
	                }


	                //close connection
	                ctx.close();

	        }
	        catch (NamingException e)
	        {
	                System.err.println("Problem updating user's password : " + e);
	                result = constant.failed + e;
	        }
	        catch (Exception e)
	        {
	                System.out.println("error " + e);
	                result = constant.failed + e;
	                e.printStackTrace();
	        }

	        return result;
	}

	public String EnableAccount(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword
	        , String s_UseSSL, String s_UserLogon, String s_ADRootDN)
	{
	        //Fill in Variable & Validasi 
	        AppLogger log = new AppLogger();
	        AppConstant constant = new AppConstant();
	        AppServiceDBConnection sdb = new AppServiceDBConnection();
	        AppUtilAction util = new AppUtilAction();
	        String result = constant.failed;
	        SimpleDateFormat sdf02 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	        try
	        {

	                Date now = new Date();
	                System.out.println("start provisioning enabled user domain : " + sdf02.format(now));
	                //open connection
	                LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);

	                ADCommonMethod commonMehod = new ADCommonMethod();
	                //System.out.println("s_UserLogon"+s_UserLogon);

	                String findUser = commonMehod.findBysAMAccountName(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_UserLogon);
	                if (util.isNullOrEmpty(findUser))
	                {
	                        result = constant.failed + " account is not found";
	                }
	                else
	                {
	                        String s_UserName = findUser; //"CN="+ s_UserLogon +","+ s_ADRootDN;
	                        //Locked Account        	
	                        /* Some useful Constant */
	                        int UF_ACCOUNTDISABLE = 0x0002;
	                        int UF_PASSWD_NOTREQD = 0x0020;
	                        int UF_PASSWD_CANT_CHANGE = 0x0040;
	                        int UF_NORMAL_ACCOUNT = 0x0200;
	                        int UF_DONT_EXPIRE_PASSWD = 0x10000;
	                        int UF_PASSWORD_EXPIRED = 0x800000;

	                        //System.out.println(Integer.toString(UF_NORMAL_ACCOUNT));
	                        ModificationItem[] mods = new ModificationItem[1];
	                        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl", Integer.toString(UF_NORMAL_ACCOUNT)));
	                        ctx.modifyAttributes(s_UserName, mods);
	                        result = constant.success;
	                        System.out.println("User enabled.");
	                }
	                //close connection
	                ctx.close();
	        }
	        catch (NamingException e)
	        {
	                System.err.println("Problem enable user : " + e);
	                result = constant.failed + "" + e;
	        }
	        catch (Exception e)
	        {
	                System.err.println("Problem enable user : " + e);
	                e.printStackTrace();
	                result = constant.failed + "" + e;
	        }

	        return result;
	}

	//Connector Delete User	
	public String DeleteUser(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword
	        , String s_UseSSL, String s_ADRootDN, String s_UserLogon)
	{

	        //Fill in Variable & Validasi 
	        AppLogger log = new AppLogger();
	        AppConstant constant = new AppConstant();
	        AppServiceDBConnection sdb = new AppServiceDBConnection();
	        AppUtilAction util = new AppUtilAction();
	        String result = constant.failed;
	        SimpleDateFormat sdf02 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	        try
	        {

	                Date now = new Date();
	                System.out.println("start provisioning delete user domain : " + sdf02.format(now));
	                //open connection
	                LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);

	                ADCommonMethod commonMehod = new ADCommonMethod();
	                //System.out.println("s_UserLogon"+s_UserLogon);

	                String findUser = commonMehod.findBysAMAccountName(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_UserLogon);
	                if (util.isNullOrEmpty(findUser))
	                {
	                        result = constant.failed + " account is not found";
	                }
	                else
	                {
	                        String s_UserName = findUser; //"CN="+ s_UserLogon +","+ s_ADRootDN;
 
	                        int UF_ACCOUNTDISABLE = 0x0002;
	                        int UF_PASSWD_NOTREQD = 0x0020;
	                        int UF_PASSWD_CANT_CHANGE = 0x0040;
	                        int UF_NORMAL_ACCOUNT = 0x0200;
	                        int UF_DONT_EXPIRE_PASSWD = 0x10000;
	                        int UF_PASSWORD_EXPIRED = 0x800000;

	                        ctx.unbind(s_UserName);
	                        System.out.println("User deleted.");
	                        result = constant.success;
	                }
	                ctx.close();
	        }
	        catch (NamingException e)
	        {
	                System.err.println("Problem delete user's password : " + e);
	                result = constant.failed + e;
	        }
	        catch (Exception e)
	        {
	                System.out.println("error " + e);
	                result = constant.failed + e;
	                e.printStackTrace();
	        }

	        return result;
	}

	/*
public static void main(String[] args) {
	String s_Host = "192.168.75.138";
	String s_Port = "389";
	String s_BaseDN = "DC=company,DC=com";
	String s_AdminUser = "CN=Administrator,CN=Users,DC=company,DC=com";
	String s_AdminPassword = "Admin123";
	String s_UseSSL = "NO";
	String s_ADRootDN="CN=Users,DC=company,DC=com";
	String s_DomainName = "company.com";
	String s_UserDN = "CN=Users";
	
	String s_UserLogon="User 11. Satu"; //sAMAccountName & accountname
	String s_FirstName="User";
	String s_Initial="11";
	String s_LastName="Satu"; 
	String s_FullName="User Satu"; //display name
	String s_Password="Admin_12345";
	String s_CountryCode = "0";
	String s_GroupName = "CN=Domain Admins,CN=Users,DC=company,DC=com";
	String s_AttributeName = "st";
	String s_AttributeNameValue ="ASDASDA";
	String s_ObjectGuid = "";
	String s_UserPrincipalName = "User.Satu@company.com";
	String s_Manager="CN=Administrator,CN=Users,DC=company,DC=com";
	String s_Locked = "1";
	String s_AllowLogin = "1";
	
	String s_MustChangePassword = "0";
	
	LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL);
	
	CreateUser(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_ADRootDN, s_UserLogon, s_FirstName, s_Initial,
			s_LastName, s_FullName, s_Password,s_CountryCode, s_ObjectGuid,
			s_UserPrincipalName, s_Manager, s_MustChangePassword);
	
	DeleteUser(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_ADRootDN, s_UserLogon);
	
	ChangePassword(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_DomainName, s_UserLogon, s_Password, s_ADRootDN);
	
	AddUserGroup(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_DomainName, s_UserLogon, s_GroupName, s_ADRootDN);
	
	RemoveUserGroup(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_DomainName, s_UserLogon, s_GroupName, s_ADRootDN);
	
	LockAccount(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_UserLogon, s_ADRootDN);
	
	UnLockAccount(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_UserLogon, s_ADRootDN);
	
	DisableAccount(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_UserLogon, s_ADRootDN);
	
	EnableAccount(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_UserLogon, s_ADRootDN);
	
	UpdateAttribute(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword,
			s_UseSSL, s_DomainName, s_UserLogon, s_AttributeName, s_AttributeNameValue,
			s_ADRootDN);
	
	}*/
}
