package id.co.cimb.connector.domain.prov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.co.cimb.util.action.AppConstant;
import id.co.cimb.util.action.AppEmailAction;
import id.co.cimb.util.action.AppLogger;
import id.co.cimb.util.action.AppServiceDBConnection;
import id.co.cimb.util.action.AppUtilAction;
import id.co.cimb.util.model.AppEmailModel;
import id.co.cimbniaga.pwd.PasswordRandom;

public class DomainScanDBMethod
{
  public static String Driver = "com.mysql.jdbc.Driver";
  //public static String insertTotAppProv = " INSERT INTO tapp_prov_status ( app, nik_req, nik_ben, is_provisioned,req_no, created_at) VALUES (?,?,?,?,?,?) ";
  //public static String deletetAppProv = " delete from t_domain where sAMAccountName = ? ";
  //public static String updatedTDomain = " update t_domain set physicalDeliveryOfficeName = ? where sAMAccountName = ? ";
  public static SimpleDateFormat sdf02 = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
  public static String s_AppType = "1";
  
  public static String checkTable(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String s_ADRootDN, String HostMysql, String UserMySql, String PassMySql, String passwordPolicy)
  {
    AppServiceDBConnection dbCon = new AppServiceDBConnection();
    AppLogger log = new AppLogger();
    AppConstant constant = new AppConstant();
    AppServiceDBConnection sdb = new AppServiceDBConnection();
    AppUtilAction util = new AppUtilAction();
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    Date now = new Date();
    
    System.out.println(" Start scan : " + sdf.format(now));
    String result = "";
    String s_DomainName = doGetDomain(s_BaseDN);
    try
    {
      result = doProvCreate(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_ADRootDN, HostMysql, UserMySql, PassMySql, passwordPolicy);
//      result = result + doProvModify(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_ADRootDN, HostMysql, UserMySql, PassMySql, s_DomainName);
//      result = result + doProvResetPassword(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_ADRootDN, HostMysql, UserMySql, PassMySql, s_DomainName, passwordPolicy);
//      result = result + doProvDelete(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_ADRootDN, HostMysql, UserMySql, PassMySql, s_DomainName);
    }
    catch (Exception e)
    {
      result = "Failed" + " " + e.getMessage();
      e.printStackTrace();
    }
    Date endDate = new Date();
    System.out.println("end scan " + sdf.format(endDate));
    
    return result;
  }
  
  public static String doProvCreate(String s_Host, String s_Port, String s_BaseDN, String s_AdminUser, String s_AdminPassword, String s_UseSSL, String s_ADRootDN, String HostMysql, String UserMySql, String PassMySql, String passwordPolicyName)
  {
	System.out.println("::Masuk::");
	System.out.println(":::"+passwordPolicyName);  
    AppLogger log = new AppLogger();
    AppConstant constant = new AppConstant();
    AppServiceDBConnection sdb = new AppServiceDBConnection();
    AppUtilAction util = new AppUtilAction();
    GetEmailAction emailDbAction = new GetEmailAction();
    AppEmailAction emailAction = new AppEmailAction();
    AppEmailModel emailModel = GetEmailAction.doSendEmail(HostMysql, UserMySql, PassMySql);
    PasswordRandom populatepass = new PasswordRandom();
    
    GetOU getOu = new GetOU();
    RequestValue secretKey = new RequestValue();
    TemplateEmail templateEmail = new TemplateEmail();
    GetEmailAction getEmail = new GetEmailAction();
    
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    SimpleDateFormat sdf02 = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
    Date now = new Date();
    
    String result = "";
    String table_name = "members";
    String actionName = "create account Domain ";
    
    String s_employeeID = "8888888888";
    String s_givenName = "";
    String s_initials = "";
    String s_sn = "";
    String s_userPrincipalName = "";
    String s_sAMAccountName = "";
    String s_displayName = "";
    String s_name = "";
    String s_description = "";
    String s_physicalDeliveryOfficeName = "";
    String s_telephoneNumber = "";
    String s_mail = "";
    String s_wWWHomePage = "";
    String s_password = "";
    String s_streetAddress = "";
    String s_postOfficeBox = "";
    String s_l = "";
    String s_st = "";
    String s_postalCode = "";
    String s_co = "";
    String s_c = "";
    String s_countryCode = "";
    String s_memberOf = "";
    String s_accountExpires = "";
    String s_userAccountControl = "";
    String s_profilePath = "";
    String s_scriptPath = "";
    String s_homeDirectory = "";
    String s_homeDrive = "";
    String s_userWorkstations = "";
    String s_homePhone = "";
    String s_pager = "";
    String s_mobile = "";
    String s_facsimileTelephoneNumber = "";
    String s_ipPhone = "";
    String s_info = "";
    String s_title = "";
    String s_department = "";
    String s_company = "";
    String s_manager = "";
    String s_mailNickName = "";
    String s_displayNamePrintable = "";
    String s_msExchHideFromAddressLists = "";
    String s_submissionContLength = "";
    String s_delivContLength = "";
    String s_msExchRequireAuthToSendTo = "";
    String s_unauthOrig = "";
    String s_authOrig = "";
    String s_publicDelegates = "";
    String s_altRecipient = "";
    
    String s_msExchRecipLimit = "";
    String s_mDBuseDefaults = "";
    String s_mDBStorageQuota = "";
    String s_mDBOverQuotaLimit = "";
    String s_mDBOverHardQuotaLimit = "";
    String s_deletedItemFlags = "";
    String s_garbageCollPeriod = "";
    String s_msExchOmaAdminWirelessEnable = "";
    String s_protocolSettings = "";
    String s_tsAllowLogon = "";
    String s_tsProfilePath = "";
    String s_tsHomeDir = "";
    String s_tsHomeDirDrive = "";
    String s_tsInheritInitialProgram = "";
    String s_tsIntialProgram = "";
    String s_tsWorkingDir = "";
    String s_tsDeviceClientDrives = "";
    String s_tsDeviceClientPrinters = "";
    String s_tsDeviceClientDefaultPrinter = "";
    String s_tsTimeOutSettingsDisConnections = "";
    String s_tsTimeOutSettingsConnections = "";
    String s_tsTimeOutSettingsIdle = "";
    String s_tsBrokenTimeOutSettings = "";
    String s_tsReConnectSettings = "";
    String s_tsShadowSettings = "";
    String s_preventDeletion = "";
    String s_managerCanUpdateMembers = "";
    String lokasiKerja = "";

    String s_UserLogon = "";
    String s_FirstName = "";
    String s_Initial = "";
    String s_LastName = "";
    String s_FullName = "";
    String s_Password = "";
    String s_CountryCode = "";
    String s_GroupName = "";
    String s_AttributeName = "";
    String s_AttributeNameValue = "";
    String s_ObjectGuid = "";
    String s_UserPrincipalName = "";
    String s_Manager = "";
    String s_Locked = "";
    String s_AllowLogin = "";
    String s_MustChangePassword = "1";
    String s_NewPassword = "";
    
    String s_EmployeeID = "";
    String s_PhysicalDeliveryOfficeName = "";
    String s_Mail = "";
    String s_Mobile = "";
    String s_Department = "";
    String s_reqNo = "";
    String s_reqObj = "1";
    String s_PIC = "";
    
    int idExchange = 0;
    int totalSuccess = 0;
    int totalFailed = 0;
    int countItemCreate = 0;
    int isEmpty = 0;
    

    Connection conMySql = null;
    ResultSet rsCreate = null;
    PreparedStatement pre = null;
    try
    {
      //conMySql = DriverManager.getConnection(HostMysql, UserMySql, PassMySql);
      conMySql = DriverManager.getConnection("jdbc:mysql://localhost/ptba_test", UserMySql, PassMySql);

      String passwordPolicy = passwordPolicyName;
      String URL = HostMysql;
      String getRandomPassword = "";
      
      int i_always_run = 1;
      while (i_always_run == 1)
      {
        String QueryMySqlCreate = " select * from " + table_name + " where status_domain = ?";
        String queryUpdateProvCreate = " update " + table_name + " set status_domain = ?," + 
          " updated = ?, remarks = ? where id = ? ";
        
        //String queryGetOu = "select * from t_branch_ou where kode_area like ? ";
        //String querySelectExchange = "select * from tp_exchange where username = ? and status_exchange = ?";
        //String updateExchange = "update tp_exchange set is_ad_provisioned = ? where idu = ?";
        //String selectTot_domain = "select * from t_domain where sAMAccountName = ? ";
        //String insertTot_domain = "insert into t_domain(sAMAccountName,c,cn,co,company,department,description,displayName,distinguishedName,employeeID_real,employeeID,facsimileTelephoneNumber,givenName, sn,physicalDeliveryOfficeName)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         

        int notProv = 0;
        
        pre = conMySql.prepareStatement(QueryMySqlCreate);
        pre.setInt(1, notProv);
        rsCreate = pre.executeQuery();
        
        DomainProvisioningMethod provMethod = new DomainProvisioningMethod();
        boolean doRepeat = false;
        while (rsCreate.next())
        {
          //s_employeeID = rsCreate.getString("employeeID");
          //s_givenName = rsCreate.getString("givenName");
          //s_initials = rsCreate.getString("initials");
          //s_sn = rsCreate.getString("sn");
          //s_userPrincipalName = rsCreate.getString("userPrincipalName");
          s_sAMAccountName = rsCreate.getString("username");
          s_displayName = rsCreate.getString("fullname");
//          s_name = rsCreate.getString("name");
//          s_description = rsCreate.getString("description");
//          s_physicalDeliveryOfficeName = rsCreate.getString("physicalDeliveryOfficeName");
//          s_telephoneNumber = rsCreate.getString("telephoneNumber");
//          s_mail = rsCreate.getString("mail");
//          s_wWWHomePage = rsCreate.getString("wWWHomePage");
//          s_password = rsCreate.getString("password");
//          s_streetAddress = rsCreate.getString("streetAddress");
//          s_postOfficeBox = rsCreate.getString("postOfficeBox");
//          s_l = rsCreate.getString("l");
//          s_st = rsCreate.getString("st");
//          s_postalCode = rsCreate.getString("postalCode");
//          s_co = rsCreate.getString("co");
//          s_c = rsCreate.getString("c");
//          s_countryCode = rsCreate.getString("countryCode");
//          s_memberOf = rsCreate.getString("memberOf");
//          s_accountExpires = rsCreate.getString("accountExpires");
//          s_userAccountControl = rsCreate.getString("userAccountControl");
//          s_profilePath = rsCreate.getString("profilePath");
//          s_scriptPath = rsCreate.getString("scriptPath");
//          s_homeDirectory = rsCreate.getString("homeDirectory");
//          s_homeDrive = rsCreate.getString("homeDrive");
//          s_userWorkstations = rsCreate.getString("userWorkstations");
//          s_homePhone = rsCreate.getString("homePhone");
//          s_pager = rsCreate.getString("pager");
//          s_mobile = rsCreate.getString("mobile");
//          s_facsimileTelephoneNumber = rsCreate.getString("facsimileTelephoneNumber");
//          s_ipPhone = rsCreate.getString("ipPhone");
//          s_info = rsCreate.getString("info");
//          s_title = rsCreate.getString("title");
//          s_department = rsCreate.getString("department");
//          s_company = rsCreate.getString("company");
//          s_manager = rsCreate.getString("manager");
//          s_mailNickName = rsCreate.getString("mailNickName");
//          s_displayNamePrintable = rsCreate.getString("displayNamePrintable");
//          s_msExchHideFromAddressLists = rsCreate.getString("msExchHideFromAddressLists");
//          s_submissionContLength = rsCreate.getString("submissionContLength");
//          s_delivContLength = rsCreate.getString("delivContLength");
//          s_msExchRequireAuthToSendTo = rsCreate.getString("msExchRequireAuthToSendTo");
//          s_unauthOrig = rsCreate.getString("unauthOrig");
//          s_authOrig = rsCreate.getString("authOrig");
//          s_publicDelegates = rsCreate.getString("publicDelegates");
//          s_altRecipient = rsCreate.getString("altRecipient");
//          
//          s_msExchRecipLimit = rsCreate.getString("msExchRecipLimit");
//          s_mDBuseDefaults = rsCreate.getString("mDBuseDefaults");
//          s_mDBStorageQuota = rsCreate.getString("mDBStorageQuota");
//          s_mDBOverQuotaLimit = rsCreate.getString("mDBOverQuotaLimit");
//          s_mDBOverHardQuotaLimit = rsCreate.getString("mDBOverHardQuotaLimit");
//          s_deletedItemFlags = rsCreate.getString("deletedItemFlags");
//          s_garbageCollPeriod = rsCreate.getString("garbageCollPeriod");
//          s_msExchOmaAdminWirelessEnable = rsCreate.getString("msExchOmaAdminWirelessEnable");
//          s_protocolSettings = rsCreate.getString("protocolSettings");
//          s_tsAllowLogon = rsCreate.getString("tsAllowLogon");
//          s_tsProfilePath = rsCreate.getString("tsProfilePath");
//          s_tsHomeDir = rsCreate.getString("tsHomeDir");
//          s_tsHomeDirDrive = rsCreate.getString("tsHomeDirDrive");
//          s_tsInheritInitialProgram = rsCreate.getString("tsInheritInitialProgram");
//          s_tsIntialProgram = rsCreate.getString("tsIntialProgram");
//          s_tsWorkingDir = rsCreate.getString("tsWorkingDir");
//          s_tsDeviceClientDrives = rsCreate.getString("tsDeviceClientDrives");
//          s_tsDeviceClientPrinters = rsCreate.getString("tsDeviceClientPrinters");
//          s_tsDeviceClientDefaultPrinter = rsCreate.getString("tsDeviceClientDefaultPrinter");
//          s_tsTimeOutSettingsDisConnections = rsCreate.getString("tsTimeOutSettingsDisConnections");
//          s_tsTimeOutSettingsConnections = rsCreate.getString("tsTimeOutSettingsConnections");
//          s_tsTimeOutSettingsIdle = rsCreate.getString("tsTimeOutSettingsIdle");
//          s_tsBrokenTimeOutSettings = rsCreate.getString("tsBrokenTimeOutSettings");
//          s_tsReConnectSettings = rsCreate.getString("tsReConnectSettings");
//          s_tsShadowSettings = rsCreate.getString("tsShadowSettings");
//          s_preventDeletion = rsCreate.getString("preventDeletion");
//          s_managerCanUpdateMembers = rsCreate.getString("managerCanUpdateMembers");
//          s_reqNo = rsCreate.getString("req_no");
          int id = rsCreate.getInt("id");
//          lokasiKerja = rsCreate.getString("lokasi_kerja");
//          s_PIC = rsCreate.getString("pic");
          
          System.out.println("property password"+passwordPolicy.trim());
          getRandomPassword = populatepass.prepopulatePassword(Driver, URL, UserMySql, PassMySql, passwordPolicy.trim());
          
//          s_FirstName = s_givenName;
//          s_Initial = s_initials;
//          s_LastName = s_sn;
          s_FullName = s_displayName;
          s_UserLogon = s_sAMAccountName;
          s_Password = getRandomPassword;
//          s_UserPrincipalName = s_userPrincipalName;
//          s_EmployeeID = s_employeeID;
//          s_PhysicalDeliveryOfficeName = s_physicalDeliveryOfficeName;
//          s_Mail = s_mail;
//          s_Mobile = s_mobile;
//          s_Department = s_department;
//          s_Manager = s_manager;
          
//          s_ADRootDN = GetOU.GetOU(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_ADRootDN, 
//        		  HostMysql, UserMySql, PassMySql, lokasiKerja);
          s_ADRootDN = GetOU.GetOU(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_ADRootDN, 
        		  HostMysql, UserMySql, PassMySql, lokasiKerja);
          RequestModel reqModel = RequestValue.req(HostMysql, UserMySql,PassMySql, s_reqNo, s_reqObj);
          if (!AppUtilAction.isNullOrEmpty(reqModel.password)) {
            s_Password = reqModel.password + s_Password;
          }
          
          String resultCreateUser = provMethod.CreateUser(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_ADRootDN, 
            s_UserLogon, 
            s_FirstName, s_Initial, s_LastName, 
            s_FullName, s_Password, s_CountryCode, 
            s_ObjectGuid, s_UserPrincipalName, s_MustChangePassword, 
            s_EmployeeID, s_PhysicalDeliveryOfficeName, s_Mail, 
            s_Mobile, s_Department, s_Manager, 
            s_telephoneNumber, s_wWWHomePage, 
            s_streetAddress, s_postOfficeBox, s_l, 
            s_st, s_postalCode, s_title, 
            s_department, s_company, s_homePhone, 
            s_pager, s_mobile, s_facsimileTelephoneNumber, 
            s_ipPhone, s_info, s_profilePath, 
            s_scriptPath, s_initials, s_sAMAccountName, s_description, lokasiKerja);
          
          result = resultCreateUser;
          int sStatus = 0;
          Date nowTime = new Date();
          String sRemarks = null;
          

          String domainType = "ArabITPro.local";
          if (!s_BaseDN.contains("ArabITPro")) {
            domainType = "ArabITPro.local";
          }
          if (result.contains("Success"))
          {
            String enabledUser = provMethod.EnableAccount(s_Host, s_Port, s_BaseDN, s_AdminUser, s_AdminPassword, s_UseSSL, s_sAMAccountName, s_ADRootDN);
            if (!enabledUser.contains("Success")) {
              result = "Failed" + " enabled user";
            }
            totalSuccess++;
            sStatus = 1;
            sRemarks = "Success" + " create user :" + s_ADRootDN;
          }
          else
          {
            sStatus = 2;
            sRemarks = result;
            totalFailed++;
          }
          PreparedStatement preUpdate = null;
          try
          {
            Timestamp updateTime = new Timestamp(nowTime.getTime());
            preUpdate = conMySql.prepareStatement(queryUpdateProvCreate);
            preUpdate.setInt(1, sStatus);
            preUpdate.setTimestamp(2, updateTime);
            preUpdate.setString(3, sRemarks);
            //preUpdate.setInt(4, idu);
            preUpdate.executeUpdate();
            countItemCreate++;
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
          finally
          {
            preUpdate.close();
          }
          if (sStatus == 1)
          {
            if (emailModel != null)
            {
              String reqHeadBeneficiary = reqModel.req_last_action_by_nik;
              if (!AppUtilAction.isNullOrEmpty(s_PIC)) {
                reqHeadBeneficiary = s_PIC;
              }
              GetEmailModel emailDbModel = GetEmailAction.getMailModel(HostMysql, UserMySql, PassMySql, reqHeadBeneficiary);
              String fullnameHead = emailDbModel.getFullname();
              String emailHead = emailDbModel.getEmail();
              String contentEmailHead = TemplateEmail.templateCreateDomain(s_FullName, s_EmployeeID, s_sAMAccountName, getRandomPassword, fullnameHead);
              
              emailModel.setEmailSubject("[IDM] -  Success Create Account ");
              emailModel.setEmailContent(contentEmailHead);
              emailModel.setEmailAddressTo(emailHead);
              if (!AppUtilAction.isNullOrEmpty(emailHead))
              {
                emailAction.sendEmail(emailModel);
                String cnRequest = reqModel.requestor;
                GetEmailModel emailDbModel02 = GetEmailAction.getMailModel(HostMysql, UserMySql, PassMySql, cnRequest);
                String fullnameReq = emailDbModel02.getFullname();
                String emailReq = emailDbModel02.getEmail();
                String contentEmail = TemplateEmail.templateSuccessRequest(reqModel.getReq_no(), sdf02.format(reqModel.getCreatedDate()), reqModel.getBeneficiary() + " - " + reqModel.getFullName(), reqModel.getRequestor(), "1", fullnameReq);
                emailModel.setEmailSubject("[IDM] - Request " + reqModel.getReq_no() + " is Success.");
                emailModel.setEmailContent(contentEmail);
                emailModel.setEmailAddressTo(emailReq);
                if (!AppUtilAction.isNullOrEmpty(emailReq)) {
                  emailAction.sendEmail(emailModel);
                }
              }
            }
            else
            {
              log.error("failed send email");
            }
            PreparedStatement preSelectt_domain = null;
            ResultSet rsSelecttDomain = null;
            try
            {
//              preSelectt_domain = conMySql.prepareStatement(selectTot_domain);
//              preSelectt_domain.setString(1, s_sAMAccountName);
              rsSelecttDomain = preSelectt_domain.executeQuery();
              if (!rsSelecttDomain.next())
              {
                PreparedStatement prInsertTot_domain = null;
                try
                {
//                  String s_distinghuisname = "CN=" + s_FirstName + " " + s_LastName + "," + s_ADRootDN;
//                  
//                  prInsertTot_domain = conMySql.prepareStatement(insertTot_domain);
//                  prInsertTot_domain.setString(1, s_sAMAccountName);
//                  prInsertTot_domain.setString(2, s_c);
//                  prInsertTot_domain.setString(3, s_FullName);
//                  prInsertTot_domain.setString(4, s_co);
//                  prInsertTot_domain.setString(5, s_company);
//                  prInsertTot_domain.setString(6, s_Department);
//                  prInsertTot_domain.setString(7, s_description);
//                  prInsertTot_domain.setString(8, s_FullName);
//                  prInsertTot_domain.setString(9, s_distinghuisname);
//                  prInsertTot_domain.setString(10, s_EmployeeID);
//                  prInsertTot_domain.setString(11, s_EmployeeID);
//                  prInsertTot_domain.setString(12, s_facsimileTelephoneNumber);
//                  prInsertTot_domain.setString(13, s_FirstName);
//                  prInsertTot_domain.setString(14, s_LastName);
//                  prInsertTot_domain.setString(15, lokasiKerja);
                  prInsertTot_domain.executeUpdate();
                }
                catch (SQLException e)
                {
                  e.printStackTrace();
                }
                finally
                {
                  prInsertTot_domain.close();
                }
              }
            }
            catch (SQLException e)
            {
              e.printStackTrace();
            }
            finally
            {
              preSelectt_domain.close();
            }
            PreparedStatement preSelectExchange = null;
            ResultSet rsSelectExchange = null;
            try
            {
//              preSelectExchange = conMySql.prepareStatement(querySelectExchange);
//              preSelectExchange.setString(1, s_sAMAccountName);
//              preSelectExchange.setInt(2, 0);
              rsSelectExchange = preSelectExchange.executeQuery();
              while (rsSelectExchange.next()) {
                idExchange = rsSelectExchange.getInt("idu");
              }
              if (idExchange != 0)
              {
                PreparedStatement prUpdateExchange = null;
                try
                {
//                  prUpdateExchange = conMySql.prepareStatement(updateExchange);
//                  prUpdateExchange.setInt(1, 1);
//                  prUpdateExchange.setInt(2, idExchange);
                  prUpdateExchange.executeUpdate();
                }
                catch (SQLException e)
                {
                  e.printStackTrace();
                }
                finally
                {
                  prUpdateExchange.close();
                }
              }
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
            finally
            {
              rsSelectExchange.close();
              preSelectExchange.close();
            }
            PreparedStatement prInsertTotapp_prov = null;
            try
            {
              String s_NikReq = reqModel.requestor;
              String s_NikBen = reqModel.beneficiary;
              String s_reqNoApp = reqModel.req_no;
              String s_isProvisioned = "1";
              Date createAt = new Date();
              String createAtString = sdf.format(createAt);
              
//              prInsertTotapp_prov = conMySql.prepareStatement(insertTotAppProv);
//              prInsertTotapp_prov.setString(1, s_AppType);
//              prInsertTotapp_prov.setString(2, s_NikReq);
//              prInsertTotapp_prov.setString(3, s_NikBen);
//              prInsertTotapp_prov.setString(4, s_isProvisioned);
//              prInsertTotapp_prov.setString(5, s_reqNoApp);
//              prInsertTotapp_prov.setString(6, createAtString);
              prInsertTotapp_prov.executeUpdate();
            }
            catch (SQLException e)
            {
              e.printStackTrace();
            }
            finally
            {
              prInsertTotapp_prov.close();
            }
          }
          else if (emailModel != null)
          {
            GetEmailModel emailDbModel = GetEmailAction.getMailModel(HostMysql, UserMySql, PassMySql, reqModel.getRequestor());
            String fullnameReq = emailDbModel.getFullname();
            String emailReq = emailDbModel.getEmail();
            String contentEmailReq = TemplateEmail.templateFailedRequest(reqModel.getReq_no(), sdf02.format(reqModel.getCreatedDate()), reqModel.getBeneficiary() + " - " + reqModel.getFullName(), reqModel.getRequestor(), "1", fullnameReq, sRemarks);
            
            emailModel.setEmailSubject("[IDM] -  Request " + reqModel.getReq_no() + " is Failed.");
            emailModel.setEmailContent(contentEmailReq);
            emailModel.setEmailAddressTo(emailReq);
            if (!AppUtilAction.isNullOrEmpty(emailReq)) {
              emailAction.sendEmail(emailModel);
            }
          }
          Thread.sleep(1000L);
          if (!rsCreate.next()) {
            i_always_run = 0;
          }
        }
        if (countItemCreate < 1)
        {
          i_always_run = 0;
          
          result = "";
          isEmpty = 1;
        }
      }
    }
    catch (SQLException e)
    {
      result = "Failed" + " " + e.getMessage();
      e.printStackTrace();
      if (rsCreate != null) {
        try
        {
          rsCreate.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (pre != null) {
        try
        {
          pre.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conMySql != null) {
        try
        {
          conMySql.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    catch (Exception e)
    {
      result = "Failed" + " " + e.getMessage();
      e.printStackTrace();
      if (rsCreate != null) {
        try
        {
          rsCreate.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (pre != null) {
        try
        {
          pre.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conMySql != null) {
        try
        {
          conMySql.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    finally
    {
      if (rsCreate != null) {
        try
        {
          rsCreate.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (pre != null) {
        try
        {
          pre.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conMySql != null) {
        try
        {
          conMySql.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    Date endDate = new Date();
    String resultActionName = "total success " + actionName + " :" + totalSuccess + ". <br/>total failed " + actionName + "  :" + totalFailed + ".";
    if (isEmpty == 0) {
      result = result + "<br/>" + resultActionName;
    }
    return result;
  }
 
  public static String doGetDomain(String param)
  {
    param = param.toLowerCase();
    param = param.replaceAll("dc=", "");
    String[] getDomainName = param.split(",");
    String resultGetDomain = "";
    for (int i = 0; i < getDomainName.length; i++)
    {
      resultGetDomain = resultGetDomain + getDomainName[i];
      if (i != 0)
      {
        if (getDomainName.length == i + 1) {
          resultGetDomain = resultGetDomain;
        } else {
          resultGetDomain = resultGetDomain + ".";
        }
      }
      else {
        resultGetDomain = resultGetDomain + ".";
      }
    }
    return resultGetDomain;
  }
  
  public static String doUpdateToTableProvisioning(String stableName, int iStatus, String sRemarks, int iIdu, Connection conMySql)
  {
    AppConstant constant = new AppConstant();
    String result = "Failed";
    Connection conn = conMySql;
    PreparedStatement pre = null;
    String queryUpdateProvCreate = " update " + stableName + " set status_domain = ?," + 
      " updated = ?, remarks = ? where idu = ? ";
    try
    {
      Date nowTime = new Date();
      Timestamp tupdateTime = new Timestamp(nowTime.getTime());
      pre = conn.prepareStatement(queryUpdateProvCreate);
      pre.setInt(1, iStatus);
      pre.setTimestamp(2, tupdateTime);
      pre.setString(3, sRemarks);
      pre.setInt(4, iIdu);
      pre.executeUpdate();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        pre.close();
      }
      catch (Exception e2)
      {
        e2.printStackTrace();
      }
      pre = null;
    }
    return result;
  }
  
 public static void main(String[] args)
  {
	 
	 String s_Host = "192.168.86.185";
		String s_Port = "389";
		String s_BaseDN = "DC=ArabITPro,DC=local";
		String s_AdminUser = "CN=rizal sobar. rosa,CN=Users,DC=ArabITPro,DC=local";//"CN=Administrator,CN=Users,DC=company,DC=com";
		String s_AdminPassword = "Bintaro1!";
		String s_UseSSL = "NO";
		String s_ADRootDN="DC=ArabITPro,DC=local";
		String s_DomainName = "ArabITPro";
		String s_UserDN = "CN=Users";
//    String s_Host = "10.25.131.11";
//    String s_Port = "636";
//    String s_BaseDN = "DC=mylab,DC=local";
//    String s_AdminUser = "sahp17707@mylab.local";
//    String s_AdminPassword = "Cimbniaga1!";
//    String s_UseSSL = "YES";
//    String s_ADRootDN = "DC=mylab,DC=local";
    
    String HostMysql = "localhost";
    String UserMySql = "root";
    String PassMySql = "";
    String sDbPort = "3306";
    String sDBName = "ptba_test";
    String passwordPolicyType = "default";
    
    String sDbUrl = "jdbc:mysql://";
    sDbUrl = sDbUrl + HostMysql + ":" + sDbPort + "/" + sDBName;
    
    PasswordRandom populatepass = new PasswordRandom();
    String getRandomPassword = populatepass.prepopulatePassword(Driver, sDbUrl, UserMySql, PassMySql, passwordPolicyType);
    
    System.out.println(":::" + getRandomPassword);
//    checkTable(sDbUrl,sDbUsername, sDbPassword,isProduction);
    checkTable(s_Host,s_Port,s_BaseDN,s_AdminUser,s_AdminPassword,s_UseSSL,
    		s_ADRootDN,HostMysql,UserMySql,PassMySql,passwordPolicyType);
  }
}
