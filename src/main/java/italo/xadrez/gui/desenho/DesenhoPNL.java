package italo.xadrez.gui.desenho;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class DesenhoPNL extends JPanel implements Tela, MouseListener, KeyListener {
    
    private Desenho desenho = null;
    private BufferedImage buffer = null;
    private Graphics2D bufferGraphics = null;
    
    private DesenhoGUIListener listener = null;
    private TecladoGUIListener tecladoListener = null;
    
    public DesenhoPNL() {
        super.setBackground( Color.BLACK ); 
        
        super.addMouseListener( this );
        super.addKeyListener( this ); 
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
        
        int w = super.getWidth();
        int h = super.getHeight();
                
        if ( buffer == null ) {
            buffer = new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );        
            bufferGraphics = buffer.createGraphics();
            
            bufferGraphics.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY ); 
        }
        
        if ( desenho != null ) {                                    
            desenho.desenha( bufferGraphics, this );                                 
            g.drawImage( buffer, 0, 0, w, h, this );
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if ( listener != null )
            listener.mouseClicado( e.getX(), e.getY() ); 
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if ( tecladoListener == null )
            return;
        
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_ENTER:
                tecladoListener.teclaPressionada( TecladoGUIListener.ENTER ); 
                break;
            case KeyEvent.VK_ESCAPE:
                tecladoListener.teclaPressionada( TecladoGUIListener.ESC ); 
                break;
        }
        
        tecladoListener.qualquerTeclaPressionada();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public int getLargura() {
        if ( buffer == null )
            return 0;
        return buffer.getWidth();
    }

    @Override
    public int getAltura() {
        if ( buffer == null )
            return 0;
        return buffer.getHeight();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    public void setDesenhoGUIListener( DesenhoGUIListener listener ) {
        this.listener = listener;
    }
    
    public void setTecladoGUIListener( TecladoGUIListener listener ) {
        this.tecladoListener = listener;
    }
    
    public void setDesenho( Desenho desenho ) {
        this.desenho = desenho;
    }
    
}
