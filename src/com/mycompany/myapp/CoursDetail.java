/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;
import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;

import Entity.Cours;
import Entity.comment;
import com.codename1.components.MultiButton;
import com.codename1.io.Util;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.animations.BubbleTransition;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 *
 * @author mzark
 */
public class CoursDetail  {

   
    Form f ;
    public static int id;
    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    

    public CoursDetail(Resources theme, int id,int idu) throws IOException {
       
        ConnectionRequest con = new ConnectionRequest();
        ConnectionRequest con1 = new ConnectionRequest();
        con.setUrl("http://localhost/EspritAide/web/app_dev.php/cour/" + id);
        con1.setUrl("http://localhost/EspritAide/web/app_dev.php/comment/" + id);

        System.out.println(Arrays.toString(con.getResponseData()));
        
        UIBuilder ui = new UIBuilder();
        f = ui.createContainer(theme, "CroisiereDetail").getComponentForm();
        Button ajoutcommentaire = new Button("Commenter");
        TextArea Commenter;
        Commenter = new TextArea("Votre commentaire...");
        Commenter.isScrollableX();
        Commenter.setRows(3);
     Commenter.setGrowByContent(true);
     
        System.out.println(Commenter.getText());
         ajoutcommentaire.addActionListener((e) -> {
            String url = "http://localhost/EspritAide/web/app_dev.php/new"
                    + "?com="+Commenter.getText()
                    +"&id_user="+idu
                    +"&id_fichier="+id;
            System.out.println(url);
            Commenter.setText("Votre commentaire...");
            try {
                CoursDetail a = new CoursDetail(theme, id, idu);
                a.getF().show();
            } catch (IOException ex) {
            }
            f.refreshTheme();
                ConnectionRequest con2 = new ConnectionRequest(url);
           
                con2.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    String response = new String(con.getResponseData());
                    if(response.equals("\"ok\"")){  
                          System.out.println("ok"+response.toString());
                          
                           f.refreshTheme();
                           
                        }
                        else 
                            System.out.println("ERROR !"+response.toString());
 
                }
            });
                 NetworkManager.getInstance().addToQueue(con2);
                

            

      
        });
        //f.add(ship_detail);
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Cours croz = new Cours();
                    System.out.println(idu+"iduser");
                    croz = getDetailCroisiere(new String(con.getResponseData()));
                    Label d = new Label();

                    Container contY = new Container(BoxLayout.y());
                    Label nom = new Label(croz.getNom());
                    Label nb_cabie = new Label("Type : " + croz.getType().toString());
                    Label service_inclu = new Label("Nom : " + croz.getNom());
                    Label num_croz = new Label("Module:" + croz.getModule().toString());
                    Label Destination = new Label("Date de Creation :" + croz.getDatecreation());
                    /*
                    BrowserComponent browser = new BrowserComponent();
                    
                    System.out.println(a);
                    browser.setURL("http://localhost/EspritAide/web/images/products/"+croz.getImage_name());

                    */
                    String a = "http://localhost/EspritAide/web/images/products/"+croz.getImage_name();
                    Button devGuide = new Button("Show PDF");
devGuide.addActionListener(e -> {
    FileSystemStorage fs = FileSystemStorage.getInstance();
    String fileName = fs.getAppHomePath() + "pdf-sample.pdf";
    if(!fs.exists(fileName)) {
        Util.downloadUrlToFile(a, fileName, true);
    }
    Display.getInstance().execute(fileName);
});
                    
                       
            con1.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                    ArrayList<comment> lv = getListComment(new String(con1.getResponseData()));
           
                    for (comment lv1 : lv) {
                        
                  Button delete = new Button("supprimer");
                   Container contXB = new Container(BoxLayout.y());
                  
                   
                   contXB.add(delete);
                     delete.addActionListener((e) -> {
            String url = "http://localhost/EspritAide/web/app_dev.php/delete"
                    + "?id="+lv1.getId();
           
            try {
                CoursDetail a = new CoursDetail(theme, id, idu);
                a.getF().show();
            } catch (IOException ex) {
            }
            f.refreshTheme();
            
                ConnectionRequest con3 = new ConnectionRequest(url);
           
                con3.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    String response = new String(con.getResponseData());
                    if(response.equals("\"ok\"")){  
                          System.out.println("ok"+response.toString());
                          
                           f.refreshTheme();
                           
                        }
                        else 
                            System.out.println("ERROR !"+response.toString());
 
                }
            });
                 NetworkManager.getInstance().addToQueue(con3);
                     
                 });
                    Container contX = new Container(BoxLayout.x());
                    Label d = new Label();

                    Container contY = new Container(BoxLayout.y());
                    Label name = new Label(lv1.getUsername());
                        System.out.println(lv1.getImage());

                    try {
                        enc = EncodedImage.create("/load.png");
                    } catch (IOException ex) {
                    }
ImageViewer imgv = new ImageViewer(URLImage.createToStorage(enc, lv1.getImage(), "http://localhost/EspritAide/web/images/products/" + lv1.getImage(), URLImage.RESIZE_SCALE)
                            .scaled(30, 30));
                    //  Image imgs;
                    // ImageViewer imgv;
                   

                    contY.add(name);
                    Container k = new Container(new BorderLayout());
                   //k.add(BorderLayout.CENTER, imgv);
                    contY.setLayout(new BorderLayout());

                    contY.add(BorderLayout.CENTER, k);
                    //contY.add(BorderLayout.SOUTH,new Label(lv1.getCountry()));
                    //  Container head = new  Container();
                    //  head.add(new Label("aa"));
                    //  contY.add(BorderLayout.NORTH,new Label(lv1.getName()));

                    MultiButton gui_LA = new MultiButton("");
                    FontImage.setMaterialIcon(gui_LA, FontImage.MATERIAL_HOTEL);
                    gui_LA.setIconPosition(BorderLayout.EAST);
                    Container foot = new Container();
                    foot.setLayout(new BorderLayout());
                    

                    Container descContainer = new Container();
                    contX.setScrollableY(true);
                    /*TextArea desci = new TextArea(lv1.getDescription());
                  desci.setRows(2);
                  
        desci.setColumns(100);
        desci.setGrowByContent(false);
        desci.setEditable(false);
        desci.setSmoothScrolling(true);

        descContainer.setLayout(new BorderLayout());
                    descContainer.add(BorderLayout.WEST,desci);;
                 foot.add(BorderLayout.WEST,descContainer);
                     */
                    //   gui_LA.set
                    //  gui_LA.setVisible(false);
                    contY.add(BorderLayout.SOUTH, foot);
                    contX.setLayout((new BorderLayout()));
                    Container head = new Container();
                    head.setLayout(new BorderLayout());
                    Container headname = new Container();
                    //ahawa
                    headname.setLayout(new BorderLayout());
                    // headname.add(BorderLayout.NORTH,new Label(lv1.getName()));
                    Container hotelstar = new Container(BoxLayout.x());

                    Label type = new Label(lv1.getCommentaire());
                    type.getAllStyles().setMarginBottom(1);
                    Container namehotelc = new Container();
                    namehotelc.add(imgv);
                    Container xnames = new Container(BoxLayout.y());

                    xnames.add(type);
                    Label namepromo = new Label(lv1.getUsername());
                    xnames.add(namepromo);

                   namehotelc.add(xnames);

                    type.getAllStyles().setFgColor(0xffffff);

                    // nom.getStyle().setFgColor();
                    headname.add(BorderLayout.SOUTH, namehotelc);

                    // headname.add(BorderLayout.CENTER,"stars");
                    head.add(BorderLayout.WEST, headname);
                    // head.add(BorderLayout.CENTER,headname);
                   // head.add(BorderLayout.EAST, new Label(lv1.getPrix() + "€"));
                    //  head.setUIID("Container_uiid_name");
                    head.getStyle().setBgColor(0x329b1a);
                    //  Font fnt = Font.createTrueTypeFont("Achilles", );

                    //  contY.getStyle().setFont(ff);
                    // head.getStyle().setFont(ff);
                    head.getStyle().setBgTransparency(122);
                    foot.getStyle().setBgColor(0xefe888);
                    foot.getStyle().setBgTransparency(122);
                    Button showBubble = new Button("+");
                    showBubble.setName("BubbleButton");
                    
                  
String aa ="                                                                                     ";
String bb ="                                                      ";

 if(lv1.getId_user()==idu)
 {
     contXB.add(bb);    
     foot.add(BorderLayout.WEST,contXB);
 }
 else
 {
     foot.add(BorderLayout.WEST, aa);
 }
                    
                    foot.add(BorderLayout.EAST, showBubble);
                    
                    Style buttonStyle = showBubble.getAllStyles();
                    buttonStyle.setBorder(Border.createEmpty());
                    buttonStyle.setFgColor(0xFFFFFF);
                    buttonStyle.setBgPainter((g, rect) -> {
                        g.setColor(0xff);
                        int actualWidth = rect.getWidth();
                        int actualHeight = rect.getHeight();
                        int xPos, yPos;
                        int size;
                        if (actualWidth > actualHeight) {
                            yPos = rect.getY();
                            xPos = rect.getX() + (actualWidth - actualHeight) / 2;
                            size = actualHeight;
                        } else {
                            yPos = rect.getY() + (actualHeight - actualWidth) / 2;
                            xPos = rect.getX();
                            size = actualWidth;
                        }
                        g.setAntiAliased(true);
                        g.fillArc(xPos, yPos, size, size, 0, 360);
                    });
                   // f.add(showBubble);
                   // f.setTintColor(0);
                    showBubble.addActionListener((e) -> {
                        Dialog dlg = new Dialog(""+lv1.getUsername().toString()+"");
                        dlg.setLayout(new BorderLayout());
                        SpanLabel sl = new SpanLabel("Commentaire:" + lv1.getCommentaire() );
                        dlg.add(BorderLayout.CENTER, sl);
                        dlg.setTransitionInAnimator(new BubbleTransition(500, "BubbleButton"));
                        dlg.setTransitionOutAnimator(new BubbleTransition(500, "BubbleButton"));
                        dlg.setDisposeWhenPointerOutOfBounds(true);
                        dlg.getTitleStyle().setFgColor(0x00CCFF);
                        Style dlgStyle = dlg.getDialogStyle();
                        dlgStyle.setBorder(Border.createEmpty());
                        dlgStyle.setBgColor(0xFFFFFF);
                        dlgStyle.setBgTransparency(19);
                        dlg.showPacked(BorderLayout.NORTH, true);
                    });

                    //    head.getStyle().setBgColor(0xFF0000);
                    //   head.getComponentForm().repaint();
                    contY.add(BorderLayout.NORTH, head);
                    contX.add(BorderLayout.CENTER, contY);
                    Button btnom = new Button(lv1.getUsername());
                    int idd = lv1.getId();
                   
                    head.setLeadComponent(btnom);

                    contY.getStyle().setMarginBottom(40);
                    f.add(contX);

                }
                    
                //   System.out.println(lv.get(i).getTitre());
                //  nom_organizateur.setText(lv.get(i).getTitre());

                // }
                f.refreshTheme();

                        }
                    });
                   
        NetworkManager.getInstance().addToQueue(con1);

                    
                     
                   
                     //System.out.println(id);
                    
                     
                    
                    
                     contY.add(nom);
                    
                    
                    BoxLayout y = new BoxLayout(BoxLayout.Y_AXIS);
                   
                    
                    contY.add(num_croz);
                    
                    
                    contY.add(Destination);
                    

                    contY.add(nb_cabie);
                     contY.add(service_inclu);
                     contY.add(devGuide);
                     contY.add(Commenter);
                     contY.add(ajoutcommentaire);
                  //   contY.add(browser);
                   // f.add(BorderLayout.CENTER, browser);
                    f.add(contY);
                    f.show(); 
                    f.refreshTheme();
                    
                    //slider.setSelectedStyle(slider.getStyle());
                    
                   
                    
             



                } catch (IOException ex) {
                    
                }
            }
 });
        NetworkManager.getInstance().addToQueue(con);
        
        
        setBackCommand(f, theme);

              
           
                }
        

    public Form getF() {
        return f;
    }

    public Cours getDetailCroisiere(String json) throws IOException {

        Cours croz = new Cours();

        JSONParser j = new JSONParser();
        Map<String, Object> croiz = j.parseJSON(new CharArrayReader(json.toCharArray()));
      croz.setImage_name((String) croiz.get("imageName"));
        croz.setNom((String) croiz.get("nom"));
        croz.setType((String) croiz.get("type"));
        croz.setModule((String) croiz.get("module"));
        croz.setDatecreation((String) croiz.get("reDatecreation"));
       
             
             
             
        System.out.println(croz.getNom());
        System.out.println(croz);
        //System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());

        return croz;
    }
protected void setBackCommand(Form f, Resources theme) {
        Command back = new Command("") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                CoursList list = new CoursList(theme,id);
                list.getF().show();
            }

        };
        Image img = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand"));
        back.setIcon(img);
        f.getToolbar().addCommandToLeftBar(back);
        f.getToolbar().setTitleCentered(true);
        f.setBackCommand(back);
    }


public ArrayList<comment> getListComment(String json) {
        ArrayList<comment> listcomment = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();
            Map<String, Object> croiz = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) croiz.get("root");
            for (Map<String, Object> obj : list) {
                comment e = new comment();
  
                e.setCommentaire(obj.get("commentaire").toString());
                e.setUsername(obj.get("username").toString());
                e.setImage(obj.get("image_name").toString());
               
               // System.out.println(obj.get("nom").toString());
               //e.setId(Integer.parseInt(obj.get("id").toString()));
                
             // double a = (double)double.valueOf((int) (double) obj.get("id"));
       e.setId( Integer.parseInt( obj.get("id").toString())  );
       e.setId_fichier(Integer.parseInt( obj.get("id_fichier").toString())  );
     e.setId_user(Integer.parseInt( obj.get("id_user").toString())  );

                System.out.println(e);
                
                listcomment.add(e);

            
            }

        } catch (IOException ex) {
        }

        return listcomment;

    }   
}



        
                

   
