/**
 * 
 */
package id.co.cimb.connector.domain.prov;

/**
 * @author sihendr4
 * 28 Jul 2017 - 09.49.59
 * id.co.cimb.connector.domain.prov.GetEmailModel.java
 */
public class GetEmailModel
{
  public String email;
  public String fullname;
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getFullname()
  {
    return this.fullname;
  }
  
  public void setFullname(String fullname)
  {
    this.fullname = fullname;
  }
}
