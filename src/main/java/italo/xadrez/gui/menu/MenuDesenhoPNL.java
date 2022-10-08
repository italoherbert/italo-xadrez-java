package italo.xadrez.gui.menu;

import italo.xadrez.gui.desenho.Desenho;
import italo.xadrez.gui.desenho.Tela;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class MenuDesenhoPNL extends JPanel implements Tela {
   
    private final MenuPNL menuPNL = new MenuPNL();
    private Desenho fundoDesenho = null;
    
    private final int borda = 10;
    
    public MenuDesenhoPNL() {
        super.setLayout( new FlowLayout() ); 
        super.add( menuPNL );
        
        super.setBackground( Color.BLACK );
    }

    @Override
    public void paintComponent( Graphics g ) {                
        super.paintComponent( g );        
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY ); 
        
        if ( fundoDesenho != null )
            fundoDesenho.desenha( g2d, this );
        
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
    
    public void setFundoDesenho( Desenho fundoDesenho ) {
        this.fundoDesenho = fundoDesenho;
    }

    @Override
    public int getLargura() {
        return super.getWidth();
    }

    @Override
    public int getAltura() {
        return super.getHeight();
    }
    
}
