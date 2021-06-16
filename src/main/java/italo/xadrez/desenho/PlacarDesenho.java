package italo.xadrez.desenho;

import italo.xadrez.Sistema;
import italo.xadrez.gui.desenho.Desenho;
import italo.xadrez.gui.desenho.Tela;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class PlacarDesenho implements Desenho {

    private final int placarBorda = 10;               
    private final Font placarFont = new Font( Font.SERIF, Font.PLAIN, 20 );
    
    private final Color placarRotuloColor = Color.WHITE;
    private final Color placarValorColor = Color.GREEN;
    
    private int altura = 0;
    
    private final JogoDesenhoDriver drv;

    public PlacarDesenho( JogoDesenhoDriver drv ) {
        this.drv = drv;
    }
    
    @Override
    public void desenha( Graphics2D g2d, Tela tela ) {        
        Sistema sistema = drv.getSistema();
        
        String nivelBrancasRotulo = "Nível brancas=";
        
        String nivelPretasRotulo = "Nível pretas=";
        String vitoriasRotulo = "Vitórias=";
        String derrotasRotulo = "Derrotas=";

        String nivelBrancasValor = String.valueOf( sistema.getComputador1().getNivel() );
        String nivelPretasValor = String.valueOf( sistema.getComputador2().getNivel() );
        String vitoriasValor = String.valueOf( sistema.getJogo().getVitorias() );
        String derrotasValor = String.valueOf( sistema.getJogo().getDerrotas() );

        if ( sistema.getJogo().isUsuarioVersusComputador() ) {
            nivelBrancasRotulo = "";
            nivelBrancasValor = "";
        }

        g2d.setFont( placarFont );
        
        String[] placarTextos = {
            nivelBrancasRotulo,
            nivelBrancasValor,
            
            nivelPretasRotulo,
            nivelPretasValor,
            
            vitoriasRotulo,
            vitoriasValor,
            
            derrotasRotulo,
            derrotasValor
        };
        
        int[] larguras = new int[ placarTextos.length ];
                
        int txtX = 0;
        int ph = 0;
        for( int i = 0; i < placarTextos.length; i++ ) {
            int len = placarTextos[ i ].length();
            if ( len > 0 ) {
                String texto = placarTextos[ i ];
                if ( i % 2 != 0 ) {                    
                    texto += ",  ";
                }
                Rectangle2D placarRet = g2d.getFontMetrics().getStringBounds( texto, g2d );
                int larg = (int)placarRet.getWidth();
                int alt = (int)placarRet.getHeight();
                
                larguras[ i ] = txtX;                
                txtX += larg;
                
                if ( alt > ph )
                    ph = alt;
            }
        }
        
        int x = ( tela.getLargura() - txtX ) / 2;
        int y = ( placarBorda + ph );
        
        for( int i = 0; i < placarTextos.length / 2; i++ ) {
            String textoL = placarTextos[ (2*i) ];
            int lenL = textoL.length();
            if ( lenL > 0 ) {         
                String textoV = placarTextos[ (2*i)+1 ];

                txtX = larguras[ 2*i ];                                
                g2d.setColor( placarRotuloColor );
                g2d.drawString( textoL, x + txtX, y );
                                
                txtX = larguras[ (2*i) + 1 ];
                
                g2d.setColor( placarValorColor );
                g2d.drawString( textoV, x + txtX, y );                                
            }
        }        
        
        altura = ( y + 4 * placarBorda );
    }

    public int getAltura() {
        return altura;
    }
    
}
