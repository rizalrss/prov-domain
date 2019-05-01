package id.co.cimb.connector.domain.prov;

public class TemplateEmail {
	
	public static String templateCreateDomain(String fullname,String nik,String username,String password,String fullNameHead ){
		String result = "";
		try {
			String emailTemplate = "";
			emailTemplate= emailTemplate + "<table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'>";
			emailTemplate= emailTemplate + "<tr><td><font face='Tahoma' size='2'>Dear<br/>"+fullNameHead+".<br/></font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2'> New <b>Domain Account</b> For : </font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2'>" +
					"<table width='600' align='left' cellpadding='0' cellspacing='0' border='0'>" +
					"<tr><td width='100'>FullName</td><td width='6'>:</td><td><b>"+nik+"-"+fullname+"</b></td></tr>" +
					"<tr><td width='100'>User Domain</td><td width='6'>:</td><td >"+username+"</td></tr>" +
					"<tr><td width='100'>Password</td><td width='6'>:</td><td >"+password+"</td></tr>" +
					"<tr><td width='100' colspan='3'><br/> has been created successfully.</td></td></tr>" +
					"</table></font></br></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/></td></tr>";
			emailTemplate= emailTemplate + "<tr style='background-color:#ccc'><td><table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'><tr><td width='10'></td><td><br/><font face='Tahoma' size='2'  > <b>Note:</b><br>-           Mohon ganti password di atas pada login pertama setelah request reset password atau create userid domain, dengan login ke Domain menggunakan PC yg sudah Join Domain atau menggunakan link <a href='https://mx.cimbniaga.co.id/owa/auth/expiredpassword.aspx?url=/owa/auth.owa'>https://mx.cimbniaga.co.id/owa/auth/expiredpassword.aspx?url=/owa/auth.owa</a><br/>-           Mohon untuk menghubungi <a href='mailto:servicedesk@cimbniaga.co.id'>servicedesk@cimbniaga.co.id</a> untuk melakukan Join Domain Komputer dengan menginformasikan IP address PC yang bersangkutan [ Untuk PC yang belum Joint Domain ]</font><br/><br/></td></tr>"
					+ "<tr><td colspan='3'><hr/></td></tr>"
					+ "<tr><td width='10'></td><td><font face='Tahoma' size='2' >Do not reply to this computer-generated email.<br/>Thank you.</font><br/></td></tr>"
					+ "<tr><td colspan='3'><br/></td></tr></table>"
					+ "<br/></td></tr></table>";
			result  = emailTemplate;
				
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return result;
	}
	
	public static String templateModifyDomain(String fullname,String nik,String username,String fullNameHead ){
		String result = "";
 	 
		try {
			String emailTemplate = "";
			emailTemplate= emailTemplate + "<table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'>";
			emailTemplate= emailTemplate + "<tr><td><font face='Tahoma'>Dear<br/>"+fullNameHead+".<br/></font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2'>Modify Domain Account For :</font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2'>" +
					"<table width='600' align='left' cellpadding='0' cellspacing='0' border='0'>" +
					"<tr><td width='100'>FullName</td><td width='6'>:</td><td><b>"+nik+"-"+fullname+"</b></td></tr>" +
					"<tr><td width='100'>User Domain</td><td width='6'>:</td><td><b>"+username+"</b></td></tr>" +
					"<tr><td width='100' colspan='3'><br/> has been successfully.</td></td></tr>" +
					"</table></font></br></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/></td></tr>";
			emailTemplate= emailTemplate + "<tr style='background-color:#ccc'><td>"
					+ "<table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'>"
					+ "<tr>"
					+ "<td width='10'></td>"
					+ "<td><br/><font face='Tahoma' size='2'  > <b>Note:</b> <br/>-           Mohon untuk menghubungi <a href='mailto:servicedesk@cimbniaga.co.id'>servicedesk@cimbniaga.co.id</a> untuk melakukan Join Domain Komputer dengan menginformasikan IP address PC yang bersangkutan [ Untuk PC yang belum Joint Domain ]</font><br/><br/></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td colspan='3'><hr/></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td width='10'></td>"
					+ "<td><font face='Tahoma' size='2' >Do not reply to this computer-generated email.<br/>Thank you.</font><br/></td>"
					+ "</tr>"
					+ "<tr><td colspan='3'><br/></td></tr>"
					+ "</table><br/></td></tr>";

			result  = emailTemplate;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return result;
	}
	
	
	public static String templateResetDomain(String fullname,String nik,String username,String password,String fullNameHead ){
		String result = "";
 	 
		try {
			String emailTemplate = "";
			emailTemplate= emailTemplate + "<table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'>";
			emailTemplate= emailTemplate + "<tr><td><font face='Tahoma'>Dear "+fullNameHead+".<br/></font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2' >Reset Password Domain Account For :</font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2' >" +
					"<table width='600' align='left' cellpadding='0' cellspacing='0' border='0'>" +
						"<tr><td width='100'>FullName</td><td width='6'>:</td><td><b>"+nik+"-"+fullname+"</b></td></tr>" +
						"<tr><td width='100'>User Domain</td><td width='6'>:</td><td><b>"+username+"</b></td></tr>" +
						"<tr><td width='100'>Password</td><td width='6'>:</td><td><b>"+password+"</b></td></tr>" +
						"<tr><td colspan='3'></td>has been successfully.</td></tr>" +
					"</table></font></br></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/></td></tr>";
			emailTemplate= emailTemplate + "<tr style='background-color:#ccc'><td>"
					+ "<table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'>"
					+ "<tr>"
						+ "<td width='10'></td>"
						+ "<td><br/><font face='Tahoma' size='2'> <b>Note:</b><br>-           Mohon ganti password di atas pada login pertama setelah request reset password atau create userid domain, dengan login ke Domain menggunakan PC yg sudah Join Domain atau menggunakan link <a href='https://mx.cimbniaga.co.id/owa/auth/expiredpassword.aspx?url=/owa/auth.owa'>https://mx.cimbniaga.co.id/owa/auth/expiredpassword.aspx?url=/owa/auth.owa</a><br/>-           Mohon untuk menghubungi <a href='mailto:servicedesk@cimbniaga.co.id'>servicedesk@cimbniaga.co.id</a> untuk melakukan Join Domain Komputer dengan menginformasikan IP address PC yang bersangkutan [ Untuk PC yang belum Joint Domain ]</font><br/><br/></td>"
					+ "</tr>"
					+ "<tr><td colspan='3'><hr/></td></tr>"
					+ "<tr><td width='10'></td>"
						+ "<td><font face='Tahoma' size='2' >Do not reply to this computer-generated email.<br/>Thank you.</font><br/></td>"
					+ "</tr>"
					+ "<tr><td colspan='3'><br/></td></tr>"
					+ "</table><br/></td></tr>";
			result  = emailTemplate;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return result;
	}
	
	
	public static String templateDeleteDomain(String username,String fullNameHead){
		String result = "";
		try {
			String emailTemplate = "";
			emailTemplate= emailTemplate + "<table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'>";
			emailTemplate= emailTemplate + "<tr><td><font face='Tahoma'>Dear "+fullNameHead+".<br/></font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2' > Delete Domain Account for: </font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2' >" +
					"<table width='600' align='left' cellpadding='0' cellspacing='0' border='0'>" +
					"<tr><td width='100'>User Domain</td><td width='6'>:</td><td ><b>"+username+"</b></td></tr>" +
					"<tr><td colspan='3'>has been successfully.</td></tr>" +
					"</table></font></br></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/></td></tr>";
			emailTemplate= emailTemplate + "<tr style='background-color:#ccc'><td><table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'><tr><td width='10'></td><td><br/><font face='Tahoma' size='2'  > <b>Note:</b><br>-           Mohon ganti password di atas pada login pertama setelah request reset password atau create userid domain, dengan login ke Domain menggunakan PC yg sudah Join Domain atau menggunakan link <a href='https://mx.cimbniaga.co.id/owa/auth/expiredpassword.aspx?url=/owa/auth.owa'>https://mx.cimbniaga.co.id/owa/auth/expiredpassword.aspx?url=/owa/auth.owa</a><br/>-           Mohon untuk menghubungi <a href='mailto:servicedesk@cimbniaga.co.id'>servicedesk@cimbniaga.co.id</a> untuk melakukan Join Domain Komputer dengan menginformasikan IP address PC yang bersangkutan [ Untuk PC yang belum Joint Domain ]</font><br/><br/></td></tr><tr><td colspan='3'><hr/></td></tr><tr><td width='10'></td><td><font face='Tahoma' size='2' >Do not reply to this computer-generated email.<br/>Thank you.</font><br/></td></tr><tr><td colspan='3'><br/></td></tr></table><br/></td></tr>";
			result  = emailTemplate;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return result;
	}
	
	public static String templateSuccessRequest(String requestNo,String requestDate,String requestFor,String requestBy,String typeRequest,String fullname ){
		String result = "";
		try {
			String typeRequestDesc = " Create Akun Domain ";
			String typeAction = "di lakukan  Pembuat akun.";
			if(typeRequest.equals("2")){
				typeRequestDesc = "Modify Akun Domain";
				typeAction = "di lakukan  Modify akun.";
			}
			
			if(typeRequest.equals("3")){
				typeRequestDesc = "Reset Password Akun Domain";
				typeAction = "di lakukan Perubahan password.";
			}
			if(typeRequest.equals("4")){
				typeRequestDesc = "Delete Akun Domain";
				typeAction = "di lakukan Penghapusan akun.";
			}
			
			String emailTemplate = "";
			emailTemplate= emailTemplate + "<table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'>";
			emailTemplate= emailTemplate + "<tr><td><font face='Tahoma' size='2' >Dear "+fullname+".<br/><br/></font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><font face='Tahoma' size='2' >Selamat,Permohonan Permintaan "+typeRequestDesc+" dengan detail:   </font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2'>" +
					"<table width='600' align='left' cellpadding='0' cellspacing='0' border='0'>" +
					"<tr><td width='150'>Request No</td><td width='6'>:</td><td><b>"+requestNo+"</b></td></tr>" +
					"<tr><td width='150'>Request untuk ID</td><td width='6'>:</td><td ><b>"+requestFor+"</b></td></tr>" +
					"<tr><td width='150'>Pada Tanggal</td><td width='6'>:</td><td ><b>"+requestDate+"</b></td></tr>" +
					"<tr><td colspan='3'><br/>Telah berhasil "+typeAction+"<br/></br/></td></tr>" +
					"</table></font></br></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/></td></tr>";
			emailTemplate= emailTemplate + "<tr><td width='10'> <hr/> <font face='Tahoma' size='2' >Do not reply to this computer-generated email.<br/>Thank you.</font><br/></td></tr>"
					+ "<tr><td colspan='3'><br/></td></tr></table>"
					+ "<br/></td></tr></table>";
			result  = emailTemplate;
				
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return result;
	}
	
	
	public static String templateFailedRequest(String requestNo,String requestDate,String requestFor,String requestBy,String typeRequest,String fullname,String reasonFailed ){
		String result = "";
		try {
			String typeRequestDesc = " Create Akun Domain ";
			String typeAction = "di lakukan  Pembuat akun.";
			if(typeRequest.equals("2")){
				typeRequestDesc = "Modify Akun Domain";
				typeAction = "di lakukan  Modify akun.";
			}
			
			if(typeRequest.equals("3")){
				typeRequestDesc = "Reset Password Akun Domain";
				typeAction = "di lakukan Perubahan password.";
			}
			if(typeRequest.equals("4")){
				typeRequestDesc = "Delete Akun Domain";
				typeAction = "di lakukan Penghapusan akun.";
			}
			
			String emailTemplate = "";
			emailTemplate= emailTemplate + "<table width='100%' align='left' cellpadding='0' cellspacing='0' border='0'>";
			emailTemplate= emailTemplate + "<tr><td><font face='Tahoma' size='2' >Dear "+fullname+".<br/><br/></font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><font face='Tahoma' size='2' >Permohonan Permintaan "+typeRequestDesc+" dengan detail:   </font></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/><font face='Tahoma' size='2' >" +
					"<table width='600' align='left' cellpadding='0' cellspacing='0' border='0'>" +
					"<tr><td width='150'>Request No</td><td width='6'>:</td><td><b>"+requestNo+"</b></td></tr>" +
					"<tr><td width='150'>Request untuk ID</td><td width='6'>:</td><td ><b>"+requestFor+"</b></td></tr>" +
					"<tr><td width='150'>Pada Tanggal</td><td width='6'>:</td><td ><b>"+requestDate+"</b></td></tr>" +
					"<tr><td colspan='3'><br/>Gagal  "+typeAction+"<br/></br/></td></tr>" +
					"<tr><td colspan='3'>dikarenakan : "+reasonFailed+"<br/></br/></td></tr>" +
					"</table></font></br></td></tr>";
			emailTemplate= emailTemplate + "<tr><td><br/></td></tr>";
			emailTemplate= emailTemplate + "<tr><td width='10'> <hr/> <font face='Tahoma' size='2' >Do not reply to this computer-generated email.<br/>Thank you.</font><br/></td></tr>"
					+ "<tr><td colspan='3'><br/></td></tr></table>"
					+ "<br/></td></tr></table>";
			result  = emailTemplate;
				
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return result;
	}
}
