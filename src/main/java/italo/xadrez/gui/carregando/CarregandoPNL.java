package italo.xadrez.gui.carregando;

import italo.xadrez.gui.desenho.Desenho;
import italo.xadrez.gui.desenho.Tela;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class CarregandoPNL extends JPanel implements Tela {       
    
    private Desenho fundoDesenho;
    private BarraProgressoDesenho barraProgDesenho;
        
    public CarregandoPNL() {
        super.setBackground( Color.BLACK ); 
    }
    
    @Override
    public void paintComponent( Graphics g ) {                
        super.paintComponent( g );               
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY ); 
                
        if ( fundoDesenho != null )
            fundoDesenho.desenha( g2d, this );
        
        if ( barraProgDesenho != null )
            barraProgDesenho.desenha( g2d, this );
    }

    public BarraProgressoDesenho getBarraProgressoDesenho() {
        return barraProgDesenho;
    }
    
    public void setBarraProgressoDesenho( BarraProgressoDesenho desenho ) {
        this.barraProgDesenho = desenho;
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
