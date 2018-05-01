/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.charts.views.PieChart;
import com.codename1.ui.Command;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import static com.mycompany.myapp.CoursDetail.id;

/**
 *
 * @author mzark
 */
public class Stat {
    Form f;
     public Stat(Resources theme)
    {
        
    UIBuilder ui = new UIBuilder();
        f = ui.createContainer(theme, "iti").getComponentForm();
        
       
        
 setBackCommand(f, theme);
}
     
     public Form getF() {
        return f;
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
}

