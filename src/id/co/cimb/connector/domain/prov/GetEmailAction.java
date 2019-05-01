package id.co.cimb.connector.domain.prov;

import id.co.cimb.util.action.AppConstant;
import id.co.cimb.util.action.AppEmailAction;
import id.co.cimb.util.action.AppUtilAction;
import id.co.cimb.util.model.AppEmailModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetEmailAction
{
  public static AppEmailModel doSendEmail(String sHostname, String sUsername, String sPassword)
  {
    AppUtilAction util = new AppUtilAction();
    AppConstant constant = new AppConstant();
    AppEmailAction emailAction = new AppEmailAction();
    AppEmailModel emailModel = new AppEmailModel();
    String result = "Failed";
    
    String HostMysql = sHostname;
    String UserMySql = sUsername;
    String PassMySql = sPassword;
    
    String error = "";
    String errorResponse = "";
    String resourceName = "SMTP SERVER";
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre = null;
    String query = " SELECT a.value_id,a.resource_id,a.server_param_id,a.value_name, b.server_id,b.param_field_name  FROM t_resource_value AS a LEFT JOIN t_server_param as b on b.param_id=a.server_param_id LEFT JOIN t_server_type as c on c.server_id = b.server_id LEFT JOIN t_resource as d on d.resource_id = a.resource_id WHERE d.`status` = ?  and d.resource_name = ? and c.server_status = ?";
    
    String server = "";
    String adminSMTP = "";
    String adminSMTPPwd = "";
    String adminSMTPPort = "";
    String adminSMTPAuth = "";
    String adminSMTPSTTL = "";
    

    String emailAddressTo = "";
    String nameAddressTo = "";
    String emailAddressFrom = "";
    String nameAddressFrom = "";
    String emailSubject = "";
    String emailContent = "";
    String sendStatus = "";
    try
    {
      conn = DriverManager.getConnection(HostMysql, UserMySql, PassMySql);
      pre = conn.prepareStatement(query);
      pre.setString(1, "1");
      pre.setString(2, resourceName);
      pre.setString(3, "1");
      rs = pre.executeQuery();
      int countItem = 0;
      while (rs.next())
      {
        String paramFieldName = rs.getString("param_field_name").toLowerCase();
        String valueName = rs.getString("value_name");
        if (paramFieldName.equals("smtp server"))
        {
          server = valueName;
          if (AppUtilAction.isNullOrEmpty(server))
          {
            error = "1";
            errorResponse = errorResponse + "Failed" + " SMTP Server  is empty !<br/>";
          }
        }
        if (paramFieldName.equals("smtp user"))
        {
          adminSMTP = valueName;
          if (AppUtilAction.isNullOrEmpty(adminSMTP))
          {
            error = "1";
            errorResponse = errorResponse + "Failed" + " SMTP User  is empty !<br/>";
          }
        }
        if (paramFieldName.equals("smtp password"))
        {
          adminSMTPPwd = valueName;
          if (AppUtilAction.isNullOrEmpty(adminSMTPPwd))
          {
            error = "1";
            errorResponse = errorResponse + "Failed" + " SMTP Password  is empty !<br/>";
          }
        }
        if (paramFieldName.equals("smtp port")) {
          adminSMTPPort = valueName;
        }
        if (paramFieldName.equals("smtp auth"))
        {
          adminSMTPAuth = valueName;
          if (AppUtilAction.isNullOrEmpty(adminSMTPAuth))
          {
            error = "1";
            errorResponse = errorResponse + "Failed" + " SMTP Auth  is empty !<br/>";
          }
        }
        if (paramFieldName.equals("name address from"))
        {
          nameAddressFrom = valueName;
          if (AppUtilAction.isNullOrEmpty(nameAddressFrom))
          {
            error = "1";
            errorResponse = errorResponse + "Failed" + " Name Address From  is empty !<br/>";
          }
        }
        if (paramFieldName.equals("email address from"))
        {
          emailAddressFrom = valueName;
          if (AppUtilAction.isNullOrEmpty(emailAddressFrom))
          {
            error = "1";
            errorResponse = errorResponse + "Failed" + " Email Address From  is empty !<br/>";
          }
        }
        countItem++;
      }
      if (countItem < 1)
      {
        errorResponse = 
          "Failed  Resources " + resourceName + " not found," + " <br/> Please Check The Resource is already exist before run this class.";
        error = "1";
      }
      rs.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
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
      if (rs != null) {
        try
        {
          rs.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conn != null) {
        try
        {
          conn.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    finally
    {
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
      if (rs != null) {
        try
        {
          rs.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conn != null) {
        try
        {
          conn.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    emailModel.setServer(server);
    emailModel.setAdminSMTP(adminSMTP);
    emailModel.setAdminSMTPPwd(adminSMTPPwd);
    emailModel.setAdminSMTPPort(adminSMTPPort);
    emailModel.setAdminSMTPAuth(adminSMTPAuth);
    emailModel.setAdminSMTPSTTL("true");
    
    emailModel.setNameAddressFrom(nameAddressFrom);
    emailModel.setEmailAddressFrom(emailAddressFrom);
    if (!AppUtilAction.isNullOrEmpty(error)) {
      emailModel = null;
    }
    return emailModel;
  }
  
  public static String getMail(String sHostname, String sUsername, String sPassword, String employeeID)
  {
    String result = "";
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre = null;
    AppUtilAction util = new AppUtilAction();
    try
    {
      String password = "";
      String noReq = "";
      String reqRequestor = "";
      String reqBeneficiery = "";
      if (!AppUtilAction.isNullOrEmpty(employeeID))
      {
        String resultEmail = "";
        conn = DriverManager.getConnection(sHostname, sUsername, sPassword);
        String query = " select *  from t_domain  where 1=1  and employeeID = ? ";
        



        pre = conn.prepareStatement(query);
        pre.setString(1, employeeID);
        rs = pre.executeQuery();
        while (rs.next()) {
          resultEmail = rs.getString("mail");
        }
        int loopMax = 7;
        if (AppUtilAction.isNullOrEmpty(resultEmail))
        {
          int i = 1;
          int count = 0;
          while (i == 1)
          {
            String resultManager = getManagerEmailByEpass(conn, employeeID);
            resultEmail = resultManager;
            if (!AppUtilAction.isNullOrEmpty(resultManager)) {
              i = 0;
            }
            count++;
            if (count == loopMax)
            {
              i = 0;
              break;
            }
          }
        }
        result = resultEmail;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
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
      if (rs != null) {
        try
        {
          rs.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conn != null) {
        try
        {
          conn.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    finally
    {
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
      if (rs != null) {
        try
        {
          rs.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conn != null) {
        try
        {
          conn.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    return result;
  }
  
  public static GetEmailModel getMailModel(String sHostname, String sUsername, String sPassword, String employeeID)
  {
    String result = "";
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre = null;
    AppUtilAction util = new AppUtilAction();
    GetEmailModel emailModel = new GetEmailModel();
    
    String sEmail = "";
    String sFullname = "";
    try
    {
      String password = "";
      String noReq = "";
      String reqRequestor = "";
      String reqBeneficiery = "";
      if (!AppUtilAction.isNullOrEmpty(employeeID))
      {
        String resultEmail = "";
        conn = DriverManager.getConnection(sHostname, sUsername, sPassword);
        String query = " select *  from t_domain  where 1=1  and employeeID = ? and mail != '' ";
        



        pre = conn.prepareStatement(query);
        pre.setString(1, employeeID);
        rs = pre.executeQuery();
        while (rs.next())
        {
          sEmail = rs.getString("mail");
          sFullname = rs.getString("name");
          resultEmail = rs.getString("mail");
        }
        emailModel.setEmail(sEmail);
        emailModel.setFullname(sFullname);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
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
      if (rs != null) {
        try
        {
          rs.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conn != null) {
        try
        {
          conn.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    finally
    {
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
      if (rs != null) {
        try
        {
          rs.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if (conn != null) {
        try
        {
          conn.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    return emailModel;
  }
  
  public static String getManagerEmailByEpass(Connection conn, String employeeID)
  {
    AppUtilAction util = new AppUtilAction();
    String query = "";
    String resultEmail = "";
    
    PreparedStatement preGetManager = null;
    
    String manager = "";
    boolean isManager = false;
    try
    {
      String queryGetManager = "select * from t_epass_trusted where NIK = ?";
      preGetManager = conn.prepareStatement(queryGetManager);
      preGetManager.setString(1, employeeID);
      ResultSet getManager = preGetManager.executeQuery();
      while (getManager.next()) {
        manager = getManager.getString("Supervisor_ID");
      }
      if (!AppUtilAction.isNullOrEmpty(manager))
      {
        isManager = true;
        query = " select a.*   from t_domain as a  where 1=1  and a.employeeID = ? ";
        


        PreparedStatement pre = conn.prepareStatement(query);
        pre.setString(1, manager);
        ResultSet rs02 = pre.executeQuery();
        while (rs02.next()) {
          resultEmail = rs02.getString("mail");
        }
        rs02.close();
        if ((AppUtilAction.isNullOrEmpty(resultEmail)) && (isManager)) {
          resultEmail = getManagerEmailByEpass(conn, manager);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return resultEmail;
  }
  
  public static String getManagerEmailByEpassWithEmailModel(Connection conn, String employeeID, GetEmailModel emailModel)
  {
    AppUtilAction util = new AppUtilAction();
    String query = "";
    String resultEmail = "";
    
    PreparedStatement preGetManager = null;
    
    String manager = "";
    String sEmail = "";
    String sFullname = "";
    boolean isManager = false;
    try
    {
      String queryGetManager = "select * from t_epass_trusted where NIK = ?";
      preGetManager = conn.prepareStatement(queryGetManager);
      preGetManager.setString(1, employeeID);
      ResultSet getManager = preGetManager.executeQuery();
      while (getManager.next()) {
        manager = getManager.getString("Supervisor_ID");
      }
      if (!AppUtilAction.isNullOrEmpty(manager))
      {
        isManager = true;
        query = " select a.*   from t_domain as a  where 1=1  and a.employeeID = ? ";
        


        PreparedStatement pre = conn.prepareStatement(query);
        pre.setString(1, manager);
        ResultSet rs02 = pre.executeQuery();
        while (rs02.next())
        {
          resultEmail = rs02.getString("mail");
          sEmail = rs02.getString("mail");
          sFullname = rs02.getString("name");
          emailModel.setEmail(sEmail);
          emailModel.setFullname(sFullname);
        }
        rs02.close();
        if ((AppUtilAction.isNullOrEmpty(resultEmail)) && (isManager)) {
          resultEmail = getManagerEmailByEpass(conn, manager);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return resultEmail;
  }
  
  public static void main(String[] args) {}
}
