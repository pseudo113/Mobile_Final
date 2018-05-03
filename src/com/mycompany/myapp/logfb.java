/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ScaleImageLabel;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 * @author acer
 */
public class logfb extends com.codename1.ui.Form{
    
     static int usernaid ;
     private Resources theme;
    
    public logfb() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }
    
    public logfb(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        showFormElements();
    }
    
     private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.LayeredLayout());
        setInlineStylesTheme(resourceObjectInstance);
        setInlineStylesTheme(resourceObjectInstance);
        setTitle("Authentification");
        setName("UserForm");
    }// </editor-fold>
     
      private void showFormElements() {
        this.setScrollable(false);
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        showData(this);
    }

    private void showData(logfb form) {
        String token = (String) Storage.getInstance().readObject("token");
        if(token == null || token.equals("")){
            showIfNotLoggedIn(form);
        } else {
            showIfLoggedIn(form);
        }
    }

    private void showIfNotLoggedIn(logfb form) {
        form.getContentPane().removeAll(); //Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
        Storage.getInstance().writeObject("token", "");
        ScaleImageLabel myPic = new ScaleImageLabel();
        //            Image img = Image.createImage("/anonimo.jpg");
        //   myPic.setIcon(img);
        com.codename1.ui.geom.Dimension d = new com.codename1.ui.geom.Dimension(50, 50);
        myPic.setPreferredSize(d);
        form.add(myPic);
        form.add(new Label("User not connected"));
        Button buttonLogin = new Button("Connecter");
        buttonLogin.addActionListener((e) -> {
            facebookLogin(form);
        });
        form.add(buttonLogin);
        form.revalidate();
        //form.show();
    }

    private void showIfLoggedIn(logfb form) {
        String token = (String) Storage.getInstance().readObject("token");
        FaceBookAccess.setToken(token);
            final User me = new User();
            try {
                
                FaceBookAccess.getInstance().getUser("me", me, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String miNombre = me.getName();
                        
                        form.getContentPane().removeAll();
                        
                        form.add(new Label(miNombre));
                        
                        Button buttonLogout = new Button("Logout");
                        buttonLogout.addActionListener((e) -> {
                            facebookLogout(form);
                            showIfNotLoggedIn(form);
                        });

                        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
                        URLImage background = URLImage.createToStorage(placeholder, "fbuser.jpg",
                                "https://graph.facebook.com/v2.11/me/picture?access_token=" + token);
                        background.fetch();
                        ScaleImageLabel myPic = new ScaleImageLabel();
                        myPic.setIcon(background);
                        
                        form.add(myPic);
                        form.add(buttonLogout);
                        
                        form.revalidate();
                        //form.show();
                    }

                   

                    
                });
            } catch (IOException ex) {
                ex.printStackTrace();
                showIfNotLoggedIn(form);
            }
    }

    private void facebookLogout(logfb form) {
        String clientId = "162027171125640";
        String redirectURI = "https://www.google.com/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
        String clientSecret = "768a335eb70a1c84789d7a7e82659986";
        FacebookConnect fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);

        //trigger the login if not already logged in
        fb.doLogout();
        Storage.getInstance().writeObject("token", "EAACEdEose0cBAGUXAOL7d7b6gcZALpzFbHeDPq4TUvsjJ9JzI5Q9Ev24KGq8WVWR97IlQdxkZBkeCKTkI6zk3aAab4Qz6M5jJLTbIoj8bAWE9yACDPZBNfqZA0uKZB8hhDdu4ZB7wNWjNILBFepxUkvCGXDJPJQHcjGzVZCNkTZBbmeWL6BmgbZBve2Jh9KzyEncZD");
        showIfNotLoggedIn(form);
    }
    
    private void facebookLogin(logfb form) {
        //use your own facebook app identifiers here   
        //These are used for the Oauth2 web login process on the Simulator.
         String clientId = "162027171125640";
        String redirectURI = "https://www.google.com/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
        String clientSecret = "768a335eb70a1c84789d7a7e82659986";
        FacebookConnect fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);
        //Sets a LoginCallback listener
        fb.setCallback(new LoginCallback() {
            @Override
            public void loginFailed(String errorMessage) {
                System.out.println("Falló el login");
                Storage.getInstance().writeObject("token", "EAACEdEose0cBAGUXAOL7d7b6gcZALpzFbHeDPq4TUvsjJ9JzI5Q9Ev24KGq8WVWR97IlQdxkZBkeCKTkI6zk3aAab4Qz6M5jJLTbIoj8bAWE9yACDPZBNfqZA0uKZB8hhDdu4ZB7wNWjNILBFepxUkvCGXDJPJQHcjGzVZCNkTZBbmeWL6BmgbZBve2Jh9KzyEncZD");
                showIfNotLoggedIn(form);
            }

            @Override
            public void loginSuccessful() {
                System.out.println("Funcionó el login");//                    Logger.getLogger(logfb.class.getName()).log(Level.SEVERE, null, ex);
                //  Logger.getLogger(logfb.class.getName()).log(Level.SEVERE, null, ex);
                String token = fb.getAccessToken().getToken();
                Storage.getInstance().writeObject("token", token);
                // showIfLoggedIn(form);
                //////////////////////////////////////////////////////////////////////
                String tokenme = (String) Storage.getInstance().readObject("token");
                FaceBookAccess.setToken(tokenme);
                final User me = new User();
                try {
                    FaceBookAccess.getInstance().getUser("me", me, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            System.out.println(me.getName());
                            
                            if(me.getName().equals("Oussema Gharbi")){
                                usernaid=43;
                            }
                            
                            if(me.getName().equals("Sou Maya")){
                                usernaid=42;
                            } 
                            
                            if(me.getName().equals("Ismail Mlayeh")){
                                usernaid=20;
                            } 
                            if(me.getName().equals("Уны Усама")){
                                usernaid=49;
                            } 
                            
                            
                            
                        }});
                } catch (IOException ex) {
                    //  Logger.getLogger(logfb.class.getName()).log(Level.SEVERE, null, ex);
                }
                //////////////////////////////////////////////////
                
                   
//                ColoAffichage a = new ColoAffichage();
//                a.getF().show();
              
            }
            
        });
        //trigger the login if not already logged in
        if(!fb.isUserLoggedIn()){
            fb.doLogin();
        }else{
            //get the token and now you can query the facebook API
            String token = fb.getAccessToken().getToken();
            Storage.getInstance().writeObject("token", token);
            showIfLoggedIn(form);
        }
    }
    
   
    
}
