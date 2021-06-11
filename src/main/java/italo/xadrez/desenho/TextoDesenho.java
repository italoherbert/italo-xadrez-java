package italo.xadrez.desenho;

import italo.xadrez.Sistema;
import italo.xadrez.gui.desenho.Desenho;
import italo.xadrez.gui.desenho.Tela;
import italo.xadrez.nucleo.Jogo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class TextoDesenho implements Desenho {
    
    private final long tempoCliqueOuTeclaMS = 3000;
    
    private final int bordaMSG = 10;        
    
    private final String xequeMateMSG = "Xeque mate!";
    private final String venceuMSG = "Você venceu!";
    private final String perdeuMSG = "Você perdeu!";
    private final String pretaVenceuMSG = "Pretas venceram!";
    private final String brancaVenceuMSG = "Brancas venceram!";
    private final String empateMSG = "Houve empate!";
    private final String pauseMSG = "Pausa!";
    private final String escParaSair = "ESC para sair...";
    private final String saindoMSG = "Saindo...";
    private final String esperandoCliqueOuTecle = "Clique ou tecle algo para continuar!";
    
    private final Color ganhouColor = Color.BLUE;
    private final Color perdeuColor = Color.RED;
    private final Color empatouColor = Color.ORANGE;

    private final Color infoColor = Color.WHITE;
    
    private final Color bgMSGColor = Color.BLACK;
    private final Color bordaMSGColor = Color.WHITE;
    
    private final Font msgFonte = new Font( Font.SANS_SERIF, Font.PLAIN, 24 );
        
    private final JogoDesenhoDriver drv;
   
    public TextoDesenho( JogoDesenhoDriver drv) {
        this.drv = drv;
    }

    @Override
    public void desenha(Graphics2D g2d, Tela tela) {
        Sistema sistema = drv.getSistema();
        Jogo jogo = sistema.getJogo();
    
        int jogoStatus = jogo.getStatus();
        if ( jogoStatus != Jogo.NAO_FIM_DO_JOGO ) {
            g2d.setFont( msgFonte ); 
                        
            Color msgcor;
            String msg;
            switch ( jogoStatus ) {
                case Jogo.COR_JOGADOR1:
                    if ( sistema.getJogo().isUsuarioVersusComputador() ) {
                        msg = venceuMSG;
                    } else {
                        msg = brancaVenceuMSG;
                    }
                    msgcor = ganhouColor;
                    this.desenhaMSG2( g2d, tela, msg, xequeMateMSG, msgcor );
                    break;
                case Jogo.COR_JOGADOR2:
                    if ( sistema.getJogo().isUsuarioVersusComputador() ) {
                        msg = perdeuMSG;
                    } else {
                        msg = pretaVenceuMSG;
                    }
                    msgcor = perdeuColor;
                    this.desenhaMSG2( g2d, tela, msg, xequeMateMSG, msgcor );
                    break;                
                case Jogo.EMPATE:
                    this.desenhaMSG( g2d, tela, empateMSG, empatouColor );
                    break;
                case Jogo.PAUSA:
                    this.desenhaMSG2( g2d, tela, pauseMSG, escParaSair, infoColor ); 
                    break;
                case Jogo.SAIR:
                    this.desenhaMSG( g2d, tela, saindoMSG, infoColor );
                    break;
                case Jogo.CLIQUE_OU_TECLE:
                    this.desenhaMSG(g2d, tela, esperandoCliqueOuTecle, infoColor ); 
                    break;
            }                          
            
            if ( jogo.isFim() )
                new CliqueOuTecle().start();            
        }
    }
    
    private void desenhaMSG( Graphics2D g2d, Tela tela, String msg, Color msgcor ) {
        int w = tela.getLargura();
        int h = tela.getAltura();
        
        Rectangle2D msgret = g2d.getFontMetrics().getStringBounds( msg, g2d );

        int bw = 2*bordaMSG + (int)msgret.getWidth();
        int bh = 2*bordaMSG + (int)msgret.getHeight();

        int bx = ( w - bw ) / 2;
        int by = ( h - bh ) / 2;

        int msgTX = (int)( w - msgret.getWidth() ) / 2;
        int msgTY = by + ( bordaMSG / 2 ) + (int)msgret.getHeight();

        g2d.setColor( bgMSGColor );
        g2d.fillRect( bx, by, bw, bh );

        g2d.setColor( bordaMSGColor );
        g2d.drawRect( bx, by, bw, bh );

        g2d.setColor( msgcor );
        g2d.drawString( msg, msgTX, msgTY );   
    }
    
    private void desenhaMSG2( Graphics2D g2d, Tela tela, String msg, String msg2, Color msgcor ) {
        int w = tela.getLargura();
        int h = tela.getAltura();
        
        Rectangle2D msgret = g2d.getFontMetrics().getStringBounds( msg, g2d );
        Rectangle2D msg2Ret = g2d.getFontMetrics().getStringBounds( msg2, g2d );

        int bw = 2*bordaMSG + (int)Math.max( msg2Ret.getWidth(), msgret.getWidth() );
        int bh = 3*bordaMSG + (int)( msg2Ret.getHeight() + msgret.getHeight() );

        int bx = ( w - bw ) / 2;
        int by = ( h - bh ) / 2;        
                
        int msgTX = (int)( w - msgret.getWidth() ) / 2;
        int msgTY = by + bordaMSG + (int)msg2Ret.getHeight();
        
        int msg2TX = (int)( w - msg2Ret.getWidth() ) / 2;
        int msg2TY = by + bordaMSG + (int)( msg2Ret.getHeight() + msgret.getHeight() );
        
        g2d.setColor( bgMSGColor );
        g2d.fillRect( bx, by, bw, bh );

        g2d.setColor( bordaMSGColor );
        g2d.drawRect( bx, by, bw, bh );

        g2d.setColor( msgcor );
        g2d.drawString( msg, msgTX, msgTY );                    
        g2d.drawString( msg2, msg2TX, msg2TY );
    }
    
    class CliqueOuTecle extends Thread {
        
        @Override
        public void run() {
            Sistema sistema = drv.getSistema();
            
            if ( sistema.getJogo().isFim() ) {
                long ms = 0;
                while( sistema.getJogo().isFim() && ms < tempoCliqueOuTeclaMS ) {
                    try {
                        Thread.sleep( 10 );
                        ms += 10;
                    } catch ( InterruptedException e ) {

                    }
                }
                
                if ( sistema.getJogo().isFim() ) {
                    sistema.getJogo().cliqueOuTecle();
                    sistema.getGUI().getJanelaGUI().getJogoPNL().repaint();
                }
            }
        }
        
    }
    
}
