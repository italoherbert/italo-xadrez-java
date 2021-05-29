package italo.xadrez.gui.menu;

import italo.xadrez.gui.desenho.DesenhoPNL;
import italo.xadrez.gui.menu.MenuGUIListener;
import italo.xadrez.gui.menu.MenuPNL;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

public class MenuDesenhoPNL extends DesenhoPNL {
   
    private final MenuPNL menuPNL = new MenuPNL();
    
    private final int borda = 10;
    
    public MenuDesenhoPNL() {
        super.setLayout( new FlowLayout() ); 
        super.add( menuPNL );
    }

    @Override
    public void paintComponent( Graphics g ) {                
        super.paintComponent( g );        
        int w = super.getWidth();
        int h = super.getHeight();
                
        int mw = menuPNL.getWidth();
        int mh = menuPNL.getHeight();        

        int mx = ( w - mw ) / 2;
        int my = ( h - mh ) / 2;
        
        int brx = mx + menuPNL.getJogadoresPNL().getX() - borda;
        int bry = my + menuPNL.getJogadoresPNL().getY() - borda;
        int brw = menuPNL.getJogadoresPNL().getWidth() + ( 2* borda );
        int brh = menuPNL.getJogadoresPNL().getHeight() + ( 2* borda );
                
        g.setColor( Color.DARK_GRAY ); 
        g.fillRect( brx, bry, brw, brh );
        
        menuPNL.setBounds( mx, my, mw, mh ); 
    }
    
    public void setMenuGUIListener( MenuGUIListener listener ) {
        menuPNL.setMenuGUIListener( listener ); 
    }
    
}
