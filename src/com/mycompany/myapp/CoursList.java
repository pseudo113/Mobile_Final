/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import Entity.Cours;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.codename1.ui.Form;
import java.util.Arrays;


/**
 *
 * @author mzark
 */
public class CoursList {
    Form f;
    public static int idd;
    public CoursList(Resources theme,int id)
    {
        
       
    UIBuilder ui = new UIBuilder();
        f = ui.createContainer(theme, "CroisiereListe").getComponentForm();

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/EspritAide/web/app_dev.php/cours");
     
       //System.out.println(Arrays.toString(con.getResponseData()));
      //   System.out.println(con.getResponseData().length);
        Toolbar.setGlobalToolbar(true);
      Style s = UIManager.getInstance().getComponentStyle("Title");
        TextField searchField = new TextField("", "Chercher un cours"); 
searchField.getHintLabel().setUIID("Title");
searchField.setUIID("Title");
searchField.getAllStyles().setAlignment(Component.LEFT);
f.getToolbar().setTitleComponent(searchField);
FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
searchField.addDataChangeListener((i1, i2) -> { 
    String t = searchField.getText();
    if(t.length() < 1) {
        for(Component cmp : f.getContentPane()) {
            cmp.setHidden(false);
            cmp.setVisible(true);
        }
    } else {
        t = t.toLowerCase();
        for(Component cmp : f.getContentPane()) {
            String val = null;
            if(cmp instanceof Label) {
                val = ((Label)cmp).getText();
            } else {
                if(cmp instanceof TextArea) {
                    val = ((TextArea)cmp).getText();
                } else {
                    val = (String)cmp.getPropertyValue("text");
                }
            }
            boolean show = val != null && val.toLowerCase().indexOf(t) > -1;
            cmp.setHidden(!show); 
            cmp.setVisible(show);
        }
    }
    f.getContentPane().animateLayout(250);
});
f.getToolbar().addCommandToRightBar("", searchIcon, (e) -> {
    searchField.startEditingAsync(); 
});
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                ArrayList<Cours> lv = getListCours(new String(con.getResponseData()));
                  
                for (Cours lv1 : lv) {
                    Container contX = new Container(BoxLayout.x());
                    Label d = new Label();
                     
                    Container contY = new Container(BoxLayout.y());
                    Label nom = new Label(lv1.getNom());
                    contY.add(nom);
                    contX.add(contY);
                           Button btnom = new Button(lv1.getNom());

                    //f.add(contX);
                    f.add(btnom);
                    btnom.addActionListener(new ActionListener() {

                        @Override
                       public void actionPerformed(ActionEvent evt) {

                         
                              //  Dialog.show("Authentification", lv1.getNom(), "Ok", null);
                              CoursDetail crois;
                            try {
                                crois = new CoursDetail(theme,lv1.getId(),id);
                                                              crois.getF().show();

                            } catch (IOException ex) {
                            }
                             
                      
                                      
                              
                            
                        }
                    });
   contX.setLeadComponent(btnom);
                }

                f.refreshTheme();

            }
        });
        NetworkManager.getInstance().addToQueue(con);

        
      f.getToolbar().addCommandToSideMenu("", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                CoursList a = new CoursList(theme, id);
                 a.getF().show();
                 
            }
        });
      
       f.getToolbar().addCommandToSideMenu("", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                CoursList a = new CoursList(theme, id);
                 a.getF().show();
                 
            }
        });
       
       
         
           
           
           
        f.getToolbar().addCommandToSideMenu("       Page d'acceuil", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                CoursList a = new CoursList(theme, id);
                 a.getF().show();
                 
            }
        });
        
         f.getToolbar().addCommandToSideMenu("----------------------------------------", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                CoursList a = new CoursList(theme, id);
                 a.getF().show();
                 
            }
        });
        
         f.getToolbar().addCommandToSideMenu("   Mes cours", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                CoursList a = new CoursList(theme, id);
                 a.getF().show();
                 
            }
        });
         
            f.getToolbar().addCommandToSideMenu("   Statistiques des Cours", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                BudgetPieChart about = new BudgetPieChart();
                 about.execute().show();
                 
            }
        });
         
         
     
               
                  f.getToolbar().addCommandToSideMenu("   Logout", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                 logfb lolo = new logfb();
                 lolo.show();
            }
        });

    }

    public Form getF() {
        return f;
    }
    
          public ArrayList<Cours> getListCours(String json) {
        ArrayList<Cours> listCours = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();
            Map<String, Object> croiz = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) croiz.get("root");
            for (Map<String, Object> obj : list) {
                Cours e = new Cours();
                e.setNom(obj.get("nom").toString());
               // System.out.println(obj.get("nom").toString());
               //e.setId(Integer.parseInt(obj.get("id").toString()));
                
             // double a = (double)double.valueOf((int) (double) obj.get("id"));
               double a = (double) obj.get("id");
           
                e.setId((int) a);
                System.out.println(a);
                listCours.add(e);

            }

        } catch (IOException ex) {
        }

        return listCours;

    }   
    
}
