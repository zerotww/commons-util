package com.nriet.email;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/** 
 * 类说明 
 * @author taocheng
 * 2014-1-23 下午3:32:41 
 * @version V1.0
 * Copyright (C) 2014 NRIET
 */
public class MyAuthenticator extends Authenticator{
    String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }  
}
