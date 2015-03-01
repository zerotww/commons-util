package com.nriet.email;

public class MailTask {
	public static void  main(String[] str){
		sendMail("455683142@qq.com","aaa","bbb");
	}
	public static boolean sendMail(String mailAddr, String mailTitle, String mailCont){
		//这个类主要是设置邮件   
	     MailSenderInfo mailInfo = new MailSenderInfo();    
	     mailInfo.setMailServerHost("smtp.163.com");    //服务器
	     mailInfo.setMailServerPort("25");    			
	     mailInfo.setValidate(true);    
	     mailInfo.setUserName("nrietwarning@163.com");  //发送邮箱的账号   
	     mailInfo.setPassword("nriet123");				//发送邮箱的密码
	     mailInfo.setFromAddress("nrietwarning@163.com"); //发送邮箱的地址   
	     mailInfo.setToAddress(mailAddr);    
	     mailInfo.setSubject(mailTitle);    
	     mailInfo.setContent(mailCont); 

		 
	     //发送邮件   
	     MailSender sms = new MailSender();   
	    boolean bool= sms.sendHtmlMail(mailInfo);//发送文体格式    
	    if(!bool){
	    	 System.out.println(" 邮件发送失败"); 
	    }else{
	    	System.out.println(" 邮件发送成功！"); 
			
	    }
	   return bool;
	}
}
