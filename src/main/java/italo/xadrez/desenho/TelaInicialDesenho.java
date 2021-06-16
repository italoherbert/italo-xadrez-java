package italo.xadrez.desenho;

import italo.xadrez.Sistema;
import italo.xadrez.gui.desenho.Desenho;
import italo.xadrez.gui.desenho.Tela;
import italo.xadrez.media.ImagemManager;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class TelaInicialDesenho implements Desenho {

    private final Sistema sistema;

    public TelaInicialDesenho(Sistema sistema) {
        this.sistema = sistema;
    }
    
    @Override
    public void desenha(Graphics2D g2d, Tela tela) {
        ImageObserver obs = sistema.getGUI().getJanelaGUI().getJogoPNL();
        Image img = sistema.getMediaManager().getImagemManager().getImagem( ImagemManager.ABERTURA );
        
        if ( img == null )
            return;
                
        int w = tela.getLargura();
        int h = tela.getAltura();                        
        int iw = img.getWidth( obs );
        int ih = img.getHeight( obs );
        
        double s = 1.0d;
        if( iw > w )
            s *= (double)w/(double)iw;        
        
        if ( ih > h )
            s *= (double)h/(double)ih;        
        
        iw = (int)( (double)iw * s );
        ih = (int)( (double)ih * s );
        
        int ix = ( w - iw ) / 2;
        int iy = ( h - ih ) / 2;                                
                
        g2d.drawImage( img, ix, iy, iw, ih, obs );
    }
    
}
