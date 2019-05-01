package id.co.cimb.connector.domain.prov;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public class ADCommonMethod {
	
	public String userDN = null;
	
	public boolean FindUser(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String s_UserCN) {
		//this function for find user in active directory using userlogin
		boolean found = false;
		try
		{
			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);
	        //find user below under subtree and tree
	        String searchFilter = "(&(cn="+ s_UserCN +"))";
			String searchBase = s_BaseDN;
			String returnedAtts[]={""};
	        
	        SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(searchBase, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult searchResult = (SearchResult) answer.next();
          		this.userDN = searchResult.getName()+","+s_BaseDN;
          		found = true;
          		break;
           	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }		
		return found;
	}
	
	
	
	public String GetDN(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String s_UserCN) {
		
		String dn = "";
		try
		{
			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);		
			
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         	String searchFilter = "(cn="+ s_UserCN +")";
			String returnedAtts[]={""};
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(s_BaseDN, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult sr = (SearchResult)answer.next();
          		dn = sr.getName()+","+s_BaseDN;		          		
           	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return dn;
	}
	
	public String findBysAMAccountName(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL,String s_UserCN) {
		
		String dn = "";
		try
		{
			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);		
			
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         	String searchFilter = "(sAMAccountName="+ s_UserCN +")";
			String returnedAtts[]={""};
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(s_BaseDN, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult sr = (SearchResult)answer.next();
          		dn = sr.getName()+","+s_BaseDN;		          		
           	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return dn;
	}
	
	public String findByOU(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL,String ouName) {
		
		String dn = "";
		try
		{
			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);		
			
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         	String searchFilter = "(&(objectClass=organizationalUnit)(name=" + ouName + "))";
			String returnedAtts[]={""};
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(s_BaseDN, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult sr = (SearchResult)answer.next();
          		dn = sr.getName()+","+s_BaseDN;	
            	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return dn;
	}
	
	
	public String getManager(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL,String s_UserCN) {
		
		String dn = "";
		try
		{
			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);		
			
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         	String searchFilter = "(sAMAccountName="+ s_UserCN +")";
			String returnedAtts[]={"manager","mail"};
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(s_BaseDN, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult sr = (SearchResult)answer.next();
          		dn = sr.getName()+","+s_BaseDN;	
          		Attribute getAttribute = sr.getAttributes().get("manager");
          			dn = (String) getAttribute.get();
            	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return dn;
	}
	
	public String getManagerByCN(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String s_UserCN) {
		
		String dn = "";
		try
		{
			System.out.println("hahah"+s_UserCN);
			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);		
			
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         	String searchFilter = "(distinguishedName= "+ s_UserCN +")";
			String returnedAtts[]={"manager"};
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(s_BaseDN, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult sr = (SearchResult)answer.next();
          		Attribute getAttribute = sr.getAttributes().get("manager");
      			dn = (String) getAttribute.get();       		
           	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return dn;
	}
	
	public String getEmployeeIDByCN(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String s_UserCN) {
		
		String dn = "";
		try
		{
 			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);		
			
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         	String searchFilter = "(distinguishedName= "+ s_UserCN +")";
			String returnedAtts[]={"employeeID"};
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(s_BaseDN, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult sr = (SearchResult)answer.next();
          		Attribute getAttribute = sr.getAttributes().get("employeeID");
      			dn = (String) getAttribute.get();       		
           	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return dn;
	}
	
	public String getsAMAAccountNameByCN(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String s_UserCN) {
		
		String dn = "";
		try
		{
 			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);		
			
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         	String searchFilter = "(distinguishedName= "+ s_UserCN +")";
			String returnedAtts[]={"sAMAccountName"};
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(s_BaseDN, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult sr = (SearchResult)answer.next();
          		Attribute getAttribute = sr.getAttributes().get("sAMAccountName");
      			dn = (String) getAttribute.get();       		
           	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return dn;
	}
	
	public String checkOUExist(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String sParameter) {
		
		String dn = "";
		try
		{
 			ADProvisioning adProv = new ADProvisioning();
			LdapContext ctx = adProv.Connect(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL);		
			
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         	String searchFilter = "(objectClass=organizationalUnit)";
			String returnedAtts[]={"objectCategory"};
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<?> answer = ctx.search(sParameter, searchFilter, searchCtls);
          	while (answer.hasMoreElements()) {
          		SearchResult sr = (SearchResult)answer.next();
          		Attribute getAttribute = sr.getAttributes().get("objectCategory");
       			dn = (String) getAttribute.get(); 
           	}	
          	ctx.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return dn;
	}
}
