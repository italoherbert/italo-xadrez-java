package italo.xadrez.gui.carregando;

import italo.xadrez.gui.desenho.DesenhoPNL;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class CarregandoDesenhoPNL extends DesenhoPNL {
   
    private final Color barraColor = Color.GREEN;
    private final Color porcentTxtColor1 = Color.GREEN;
    private final Color porcentTxtColor2 = Color.BLACK;
    private final Font porcentTxtFont = new Font(Font.SANS_SERIF, Font.BOLD, 24 );
    
    private double porcentagem = 0;
    
    @Override
    public void paintComponent( Graphics g ) {                
        super.paintComponent( g );        
        
        int w = this.getWidth();
        int h = this.getHeight();
        int barraW = w - 200;
        int barraH = 50;
        
        int barraX = ( w - barraW ) / 2;
        int barraY = ( h - barraH ) / 2;
        
        int barraPreenchidaW = (int)( barraW * ( porcentagem * 0.01d ) );
        
        g.setColor( barraColor );
        g.fillRect( barraX, barraY, barraPreenchidaW, barraH );        
        g.drawRect( barraX, barraY, barraW, barraH ); 
        
        int porcent = (int)Math.round( porcentagem );
        String porcentTxt = "" + porcent + "%";
        
        g.setFont( porcentTxtFont ); 
        Rectangle2D ret = g.getFontMetrics().getStringBounds( porcentTxt, g );
        int txtX = barraX + ( ( barraW - (int)ret.getWidth() ) / 2 );
        int txtY = barraY + (int)ret.getHeight() + ( ( barraH - (int)ret.getHeight() ) / 4 );
        
        g.setColor( porcent < 45 ? porcentTxtColor1 : porcentTxtColor2 ); 
        g.drawString( porcentTxt, txtX, txtY );
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }        
    
}
