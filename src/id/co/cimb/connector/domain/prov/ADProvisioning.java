package id.co.cimb.connector.domain.prov;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class ADProvisioning {

	
	//Definisi Variable
//	public String s_Host;
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
//	String s_ADRootDN="CN=Users,DC=company,DC=com";
//	String s_DomainName = "ArabITPro";
//	String s_UserDN = "CN=Users";
	
	//Bikin Connection
	//Fill in Variable & Validasi
	//Create User
	//Close Connection
	
	//Class for create connection in Active Directory
	public static LdapContext Connect(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
			String s_UseSSL) {
		LdapContext ctx = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put("java.naming.security.authentication", "simple");
	        env.put("java.naming.security.principal", s_AdminUser);
	        env.put("java.naming.security.credentials", s_AdminPassword);
	        s_UseSSL = s_UseSSL.toUpperCase();
	        if(s_UseSSL.toUpperCase().equals("YES")) { env.put(LdapContext.SECURITY_PROTOCOL, "ssl"); }			
	        env.put("java.naming.provider.url", "ldap://" + s_Host + ":" + s_Port);
	        ctx = new InitialLdapContext(env, null);
	        
	        System.setProperty("javax.net.ssl.trustStore", "C:/Program Files/Java/jre7/lib/security/cacerts");
	        System.setProperty("javax.net.ssl.trustStorePassword","changeit");
	        
	        //System.out.println("Connected");
	        
		}
		catch(Exception e) { e.printStackTrace(); }		
		return ctx;
	}

	//Connector Create User
	public static String CreateUser(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
			String s_UseSSL, String s_ADRootDN, String s_UserLogon, String s_FirstName, String s_Initial, 
			String s_LastName, String s_FullName, String s_Password, String s_CountryCode,String s_ObjectGuid,
			String s_UserPrincipalName, String s_Manager, String s_MustChangePassword) 
	{
		try {
			//Open connection
			LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
			
			//Fill in Variable & Validasi 
			String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
			
			BasicAttributes attrs = new BasicAttributes();
			BasicAttribute ocs = new BasicAttribute("objectClass");
			ocs.add("top");
			ocs.add("person");
			ocs.add("user");
			ocs.add("organizationalPerson");
			attrs.put(ocs);
			
			if(!s_UserLogon.equals("") && !(s_UserLogon==null))
				attrs.put("sAMAccountName",s_UserLogon);
			
			if(!s_FirstName.equals("") && !(s_FirstName==null))
				attrs.put("givenName",s_FirstName);
			
			if(!s_LastName.equals("") && !(s_LastName==null))
				attrs.put("sn",s_LastName);
			
			if(!s_FullName.equals("") && !(s_FullName==null))
				attrs.put("displayName",s_FullName);
			
			if(!s_UserLogon.equals("") && !(s_UserLogon==null))
				attrs.put("cn",s_UserLogon);
			
			if(!s_UserLogon.equals("") && !(s_CountryCode==null))
				attrs.put("countryCode",s_CountryCode);
			
			if(!s_ObjectGuid.equals("") && !(s_ObjectGuid==null))
				attrs.put("objectGUID",s_ObjectGuid);
			
			if(!s_UserPrincipalName.equals("") && !(s_UserPrincipalName==null))
				attrs.put("userPrincipalName",s_UserPrincipalName);	
			
			if(!s_Manager.equals("") && !(s_Manager==null))
				attrs.put("manager",s_Manager);
			
			/* Some useful Constant */
			int UF_ACCOUNTDISABLE = 0x0002;
			int UF_PASSWD_NOTREQD = 0x0020;
			int UF_ACCOUNTLOCKEDOUT = 0x0010;
			int UF_PASSWD_CANT_CHANGE = 0x0040;
			int UF_NORMAL_ACCOUNT = 0x0200; 
			int UF_DONT_EXPIRE_PASSWD = 0x10000;
			int UF_PASSWORD_EXPIRED = 0x800000;
			
			
			/* Set Normal Account with No Password */
			attrs.put("userAccountControl",Integer.toString(UF_NORMAL_ACCOUNT + UF_ACCOUNTDISABLE));
			
			//Create User
			//System.out.println(attrs.toString());
			ctx.createSubcontext(s_UserName, attrs);
			//System.out.println("Created account for: " + s_UserName);
			//String result = "CE"; // User created but Error while modify attribute
			
			ModificationItem[] mods = new ModificationItem[1];
			
			//mofication for user password
			String newQuotedPassword = s_Password;
			byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userpassword", newUnicodePassword));
			ctx.modifyAttributes(s_UserName, mods);
			//System.out.println("Set User Password Done");
			
			if(s_MustChangePassword.equals("1"))
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("pwdLastSet","0"));
			ctx.modifyAttributes(s_UserName, mods);
			//System.out.println("Set User Must Change Password Done");
			//result = "C";
			
			
		} catch (Exception e) {e.printStackTrace();}
			
		//user created success
		System.out.println("User created");
		return "OK";
	}
	
	//Connector Delete User	
	public static String DeleteUser(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
			String s_UseSSL, String s_ADRootDN, String s_UserLogon)
	{
		try {
			//open connection
			LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
			
			//validate data entry
			String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
			System.out.println(s_UserName);
			
			//delete user

			/* Some useful Constant */
			int UF_ACCOUNTDISABLE = 0x0002;
			int UF_PASSWD_NOTREQD = 0x0020;
			int UF_PASSWD_CANT_CHANGE = 0x0040;
			int UF_NORMAL_ACCOUNT = 0x0200;
			int UF_DONT_EXPIRE_PASSWD = 0x10000;
			int UF_PASSWORD_EXPIRED = 0x800000;
				
			ctx.unbind(s_UserName);
			//System.out.println("User deleted.");
			//String result = "C";
			
			//close connection
			ctx.close();
		
		} catch (NamingException e) {e.printStackTrace();
		}
	
		return "OK";
	}
	
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
			//System.out.println("Password updated.");
			//result = "C";
			
			//close connection
			ctx.close();
	
		}
		catch (NamingException e) { System.err.println("Problem updating user's password : " + e); }		
		catch(Exception e) { e.printStackTrace(); }
		
		return "OK";
	}
	
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
			//System.out.println(s_UserName);
					
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
			//System.out.println(s_UserName);
						
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
	}
		
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
			/* Some useful Constant */
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
			//System.out.println("User Locked.");
			//String result = "C";
			
			//close connection
			ctx.close();
		}
		catch (NamingException e) { System.err.println("Problem when try to lock user : " + e); }
		catch(Exception e) { e.printStackTrace(); }
	
		return "OK";
	}
	
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
			//System.out.println(s_UserName);
				
			//Locked Account        	
			/* Some useful Constant */
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
			//System.out.println("User Unlocked.");
			//String result = "C";
				
			//close connection
			ctx.close();
		}
		catch (NamingException e) { System.err.println("Problem when try to unlock user : " + e); }
		catch(Exception e) { e.printStackTrace(); }
			
		return "OK";
	}

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
			//System.out.println(s_UserName);
				
			//Locked Account        	
			/* Some useful Constant */
			int UF_ACCOUNTDISABLE = 0x0002;
			int UF_PASSWD_NOTREQD = 0x0020;
			int UF_PASSWD_CANT_CHANGE = 0x0040;
			int UF_NORMAL_ACCOUNT = 0x0200;
			int UF_DONT_EXPIRE_PASSWD = 0x10000;
			int UF_PASSWORD_EXPIRED = 0x800000;
			
			ModificationItem[] mods = new ModificationItem[1];					
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl",Integer.toString(UF_ACCOUNTDISABLE)));
			ctx.modifyAttributes(s_UserName, mods);
			//System.out.println("User disabled.");
			//String result = "C";
				
			//close connection
			ctx.close();
		}
		catch (NamingException e) { System.err.println("Problem disable user : " + e); }
		catch(Exception e) { e.printStackTrace(); }
		
		return "OK";
	}
	
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
			//System.out.println(s_UserName);
					
			//Locked Account        	
			/* Some useful Constant */
			int UF_ACCOUNTDISABLE = 0x0002;
			int UF_PASSWD_NOTREQD = 0x0020;
			int UF_PASSWD_CANT_CHANGE = 0x0040;
			int UF_NORMAL_ACCOUNT = 0x0200;
			int UF_DONT_EXPIRE_PASSWD = 0x10000;
			int UF_PASSWORD_EXPIRED = 0x800000;
			
			ModificationItem[] mods = new ModificationItem[1];					
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl",Integer.toString(UF_NORMAL_ACCOUNT)));
			ctx.modifyAttributes(s_UserName, mods);
			//System.out.println("User enabled.");
			//String result = "C";
					
			//close connection
			ctx.close();
		}
		catch (NamingException e) { System.err.println("Problem enable user : " + e); }
		catch(Exception e) { e.printStackTrace(); }
			
		return "OK";
	}
		
	//Connector Update Attribute Account
	public static String UpdateAttribute(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword,
			String s_UseSSL, String s_DomainName, String s_UserLogon, String s_AttributeName, String s_AttributeNameValue,
			String s_ADRootDN) 
	{
		try
		{
			//open connection
			LdapContext ctx = Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
						
			//validate data entry
			String s_UserName = "CN="+ s_UserLogon +","+ s_ADRootDN;
			//System.out.println(s_UserName);
						
			//Locked Account        	
			ModificationItem attr[] = new ModificationItem[1];
			attr[0]= new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(s_AttributeName, s_AttributeNameValue)); 
			
			ctx.modifyAttributes(s_UserName, attr);
			//System.out.println(s_AttributeName +" Updated To : " + s_AttributeNameValue);
			//String result = "C";
						
			//close connection
			ctx.close();
		}
		catch (NamingException e) { System.err.println("Problem updating user's attribute : " + e); }		
		catch(Exception e) { e.printStackTrace(); }
			
		return "OK";
	}
		
/*public static void main(String[] args) {
	String s_Host = "192.168.5.193";
	String s_Port = "389";
	String s_BaseDN = "DC=company,DC=com";
	String s_AdminUser = "idM@company.com";
	String s_AdminPassword = "Jakarta7";
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
