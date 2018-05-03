/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

/**
 *
 * @author mzark
 */

import Entity.Cours;
import Entity.User;
import com.codename1.components.ImageViewer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;





public class Login {
    Form f ;
    TextField username ;
    TextField password ;
  
 public Login(Resources theme)
 {
     ConnectionRequest con = new ConnectionRequest();
     UIBuilder ui = new UIBuilder();
      ui.registerCustomComponent("ImageViewer", ImageViewer.class);
         f = ui.createContainer(theme, "GUI 1").getComponentForm();
  

         username = (TextField) ui.findByName("username", f);
         password = (TextField) ui.findByName("password", f);
         password.setConstraint(TextField.PASSWORD);

        Button login = (Button) ui.findByName("login", f);

        ImageViewer img = (ImageViewer) ui.findByName("ImageViewer", f);
        f.setTransitionOutAnimator(CommonTransitions.createFade(1000));
        
        login.addActionListener((ActionListener) new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
         con.setUrl("http://localhost/EspritAide/web/app_dev.php/user/"+ username.getText()+"?p="+password.getText());
             System.out.println(con.getUrl());

             con.addResponseListener(new ActionListener<NetworkEvent>() {
                 @Override
                 public void actionPerformed(NetworkEvent evt) {
                     
                     try {
                         
                         
                         
                         System.out.println(username.getText());
                         System.out.println(con.getUrl());
                         System.out.println(con.getResponseData());
                         System.out.println(con.getContentLength());
                         
                         
                         
                         User a = new User();
                       
                         a = getUser(new String(con.getResponseData()));
                         System.out.println(a);
                         if (username.getText().equals(a.getUsername()) ) {
                             
                             CoursList ac = new CoursList(theme,a.getId());
                             ac.getF().show();
                             
                             
                         }
                         else
                         {
                             
                             Dialog.show("Authentification", "login ou mot de passe incorrect", "Ok", null);
                         }
                     } catch (IOException ex) {
                         
                     }
                     
                 }
                 
             });
             
             NetworkManager.getInstance().addToQueue(con);
             
             
             
             
             
             
             //  +"?password="+hashPassword(password.getText())
         }
     });


 }



 
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
    
 public void show(){
     
      f.show(); 
 }
 
 
  public User getUser(String json) throws IOException {

        User e = new User();

        JSONParser j = new JSONParser();
        Map<String, Object> u = j.parseJSON(new CharArrayReader(json.toCharArray()));
       
        if(u.isEmpty())
        {
        Dialog.show("Authentification", "login ou mot de passe incorrect", "Ok", null);

        }
        else 
        {
        System.out.println(u+"kk");
       e.setUsername(u.get("username").toString());
       e.setPassword(u.get("password").toString());
               // System.out.println(obj.get("nom").toString());
               //e.setId(Integer.parseInt(obj.get("id").toString()));
                
             // double a = (double)double.valueOf((int) (double) obj.get("id"));
               
         //  e.setId( Integer.parseInt( u.get("id^^").toString()));
       
                 double a = (double) u.get("id");
           
                e.setId((int) a);
             
             
        
        System.out.println(e);
        //System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
}
        return e;
        
    }
}