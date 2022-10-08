package italo.xadrez.desenho;

import italo.xadrez.gui.carregando.BarraProgressoDesenho;
import italo.xadrez.gui.desenho.Tela;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class BarraProgDesenho implements BarraProgressoDesenho {

    private final Color barraColor = Color.GREEN;
    private final Color porcentTxtColor1 = Color.GREEN;
    private final Color porcentTxtColor2 = Color.BLACK;
    private final Font porcentTxtFont = new Font(Font.SANS_SERIF, Font.BOLD, 24 );
    
    private double porcentagem = 0;
    
    private final FundoInicialDesenho fundoInicialDesenho;

    public BarraProgDesenho(FundoInicialDesenho fundoInicialDesenho) {
        this.fundoInicialDesenho = fundoInicialDesenho;
    }
            
    @Override
    public void desenha(Graphics2D g2d, Tela tela) {                
        if ( fundoInicialDesenho != null )
            fundoInicialDesenho.desenha( g2d, tela );
        
        int w = tela.getLargura();
        int h = tela.getAltura();
        int barraW = w - 200;
        int barraH = 50;
        
        int barraX = ( w - barraW ) / 2;
        int barraY = ( h - barraH ) / 2;
        
        int barraPreenchidaW = (int)( barraW * ( porcentagem * 0.01d ) );
        
        g2d.setColor( barraColor );
        g2d.fillRect( barraX, barraY, barraPreenchidaW, barraH );        
        g2d.drawRect( barraX, barraY, barraW, barraH ); 
        
        int porcent = (int)Math.round( porcentagem );
        String porcentTxt = "" + porcent + "%";
        
        g2d.setFont( porcentTxtFont ); 
        Rectangle2D ret = g2d.getFontMetrics().getStringBounds( porcentTxt, g2d );
        int txtX = barraX + ( ( barraW - (int)ret.getWidth() ) / 2 );
        int txtY = barraY + (int)ret.getHeight() + ( ( barraH - (int)ret.getHeight() ) / 4 );
        
        g2d.setColor( porcent < 45 ? porcentTxtColor1 : porcentTxtColor2 ); 
        g2d.drawString( porcentTxt, txtX, txtY );
    }
 
    
    @Override
    public double getPorcentagem() {
        return porcentagem;
    }

    @Override
    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }                
    
}
