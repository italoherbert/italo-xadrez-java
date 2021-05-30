package italo.xadrez.nucleo;

import italo.xadrez.Const;
import italo.xadrez.media.ImagemManager;
import italo.xadrez.nucleo.peca.Bispo;
import italo.xadrez.nucleo.peca.Cavalo;
import italo.xadrez.nucleo.peca.Peao;
import italo.xadrez.nucleo.peca.Peca;
import italo.xadrez.nucleo.peca.PecaIDUtil;
import italo.xadrez.nucleo.peca.PecaManager;
import italo.xadrez.nucleo.peca.Rainha;
import italo.xadrez.nucleo.peca.Rei;
import italo.xadrez.nucleo.peca.Torre;
import java.util.List;
import italo.xadrez.nucleo.mat.Matriz;

public class JogoManager implements PecaManager {
    
    private final Peao peao = new Peao();
    private final Torre torre = new Torre();
    private final Cavalo cavalo = new Cavalo();
    private final Bispo bispo = new Bispo();
    private final Rainha rainha = new Rainha();
    private final Rei rei = new Rei();
                
    public boolean ehMovePosic( int[][] movs, int i, int j ) {
        for( int[] mov : movs )
            if ( mov[ 0 ] == i && mov[ 1 ] == j )
                return true;        
        return false;
    }
    
    public int[][] movimentosValidos( Jogo jogo, PecaIDUtil util, Matriz mat, int matI, int matJ ) {
        int pecaID = mat.getValor( matI, matJ );
        int tipo = util.getPecaTipo( pecaID );
        int dir = util.getPecaDirecaoPorPID( pecaID );
        
        Peca peca = this.getPeca( tipo );                 
        if ( peca != null )
            return peca.movimentosValidos( jogo, this, util, mat, matI, matJ, dir );        
        
        return new int[][] {};
    }        
             
    public boolean sofreuXequeMate( Jogo jogo, PecaIDUtil pecaIDUtil, Matriz mat, int c ) {        
        int[] reiP = this.buscaRei( pecaIDUtil, mat, c );                
        if ( reiP == null )
            return true;             
        
        int reiI = reiP[0];
        int reiJ = reiP[1];        
                            
        if ( this.reiEmXeque( jogo, pecaIDUtil, mat, reiI, reiJ, c ) ) {                    
            int pid = mat.getValor( reiI, reiJ );
            int dir = pecaIDUtil.getPecaDirecaoPorPID( pid );
            
            int[][] lista = rei.movimentosValidos( jogo, this, pecaIDUtil, mat, reiI, reiJ, dir );
            if ( lista.length == 0 ) {
                for( int i = 0; i < 8; i++ ) {
                    for( int j = 0; j < 8; j++ ) {
                        pid = mat.getValor( i, j );
                        if ( pid == Const.INT_NULO )
                            continue;
                        
                        int cor = pecaIDUtil.getPecaCor( pid );
                        if ( cor != c )
                            continue;
                        
                        int tipo = pecaIDUtil.getPecaTipo( pid );
                        dir = pecaIDUtil.getPecaDirecaoPorPID( pid );
                        
                        Peca peca = this.getPeca( tipo );
                        lista = peca.movimentosValidos( jogo, this, pecaIDUtil, mat, i, j, dir );
                        if ( lista.length > 0 )
                            return false;
                    }
                }          
                return true;
            } 
        }
        
        return false;        
    }
    
    public boolean verificaSeEmpate( Jogo jogo, PecaIDUtil pecaIDUtil, Matriz mat, int c ) {        
        for( int i = 0; i < 8; i++ ) {
            for( int j = 0; j < 8; j++ ) {
                int pid = mat.getValor( i, j );
                if ( pid == Const.INT_NULO )
                    continue;
                
                int cor = pecaIDUtil.getPecaCor( pid );
                if ( cor != c )
                    continue;

                int tipo = pecaIDUtil.getPecaTipo( pid );
                int dir = pecaIDUtil.getPecaDirecaoPorPID( pid );
                Peca peca = this.getPeca( tipo );
                
                int[][] movs = peca.movimentosValidos( jogo, this, pecaIDUtil, mat, i, j, dir );
                if ( movs.length > 0 )
                    return false;
            }            
        }
        return true;        
    }
                    
    @Override
    public boolean reiEmXeque( Jogo jogo, PecaIDUtil util, Matriz mat, int reiCor ) {
        int[] reiP = this.buscaRei( util, mat, reiCor );                
        if ( reiP == null )
            return true;
        return this.reiEmXeque( jogo, util, mat, reiP[0], reiP[1], reiCor );
    }
        
    public boolean reiEmXeque( Jogo jogo, PecaIDUtil util, Matriz mat, int reiI, int reiJ, int reiCor ) {
        int pid = mat.getValor( reiI, reiJ );
        int dir = util.getPecaDirecaoPorPID( pid );
        int opostaCor = util.getPecaCorOposta( pid );
                
        List<int[]> lista = rainha.movimentosValidos2( jogo, util, mat, reiI, reiJ, dir );
        
        for( int[] mov : lista ) {
            int movI = mov[ 0 ];
            int movJ = mov[ 1 ];
            int pid2 = mat.getValor( movI, movJ );
            int tipo2 = util.getPecaTipo( pid2 );
            
            if ( pid2 == Const.INT_NULO )
                continue;
            
            if ( movI == reiI || movJ == reiJ ) {                    
                if ( tipo2 == ImagemManager.RAINHA || tipo2 == ImagemManager.TORRE )
                    return true;
            } else {
                if ( tipo2 == ImagemManager.RAINHA || tipo2 == ImagemManager.BISPO )
                    return true;

                if ( movI == ( reiI + dir ) && Math.abs( movJ - reiJ ) == 1 )
                    if ( tipo2 == ImagemManager.PEAO )
                        return true;                                
            }
                                    
            if ( Math.abs( movI - reiI ) <= 1 && Math.abs( movJ - reiJ ) <= 1 )
                if ( tipo2 == ImagemManager.REI )
                    return true;            
        }
        
        lista = cavalo.movimentosValidos2( jogo, util, mat, reiI, reiJ, dir );
        for( int[] mov : lista ) {
            int movI = mov[ 0 ];
            int movJ = mov[ 1 ];
            int pid2 = mat.getValor( movI, movJ );
            if ( pid2 == Const.INT_NULO )
                continue;
            
            int cor2 = util.getPecaCor( pid2 );
            int tipo2 = util.getPecaTipo( pid2 );
            if ( tipo2 == ImagemManager.CAVALO && cor2 == opostaCor )
                return true;
        }
        
        return false;
    }
        
    public int[] buscaRei( PecaIDUtil pecaIDUtil, Matriz mat, int c ) {        
        for( int i = 0; i < 8; i++ ) {
            for( int j = 0; j < 8; j++ ) {
                int pid = mat.getValor( i, j );
                if ( pid == Const.INT_NULO )
                    continue;                                
                
                int tipo = pecaIDUtil.getPecaTipo( pid );
                int cor = pecaIDUtil.getPecaCor( pid );
                
                if ( tipo == ImagemManager.REI && c == cor )
                    return new int[] { i, j };                                                
            }
        }        
        return null;
    }
    
    public boolean apenasOReiPodeMover( Jogo jogo, PecaIDUtil pecaIDUtil, Matriz mat, int c ) {
        for( int i = 0; i < 8; i++ ) {
            for( int j = 0; j < 8; j++ ) {
                int pid = mat.getValor( i, j );
                if ( pid == Const.INT_NULO )
                    continue;                                
                
                int tipo = pecaIDUtil.getPecaTipo( pid );
                int cor = pecaIDUtil.getPecaCor( pid );
                int dir = pecaIDUtil.getPecaDirecaoPorPID( pid );
                
                if ( tipo != ImagemManager.REI && c == cor ) {
                    Peca peca = this.getPeca( tipo );
                    int[][] movs = peca.movimentosValidos( jogo, this, pecaIDUtil, mat, i, j, dir );
                    if ( movs.length > 0 )
                        return false;
                }                                
            }
        }        
        return true;
    }
    
    public boolean verificaSeRoqueJogada( Jogo jogo, PecaIDUtil pecaIDUtil, Matriz mat, int i1, int j1, int i2, int j2 ) {                
        int pid = jogo.getMatrizPecas().getValor( i1, j1 );
        int tipo = pecaIDUtil.getPecaTipo( pid );
        if ( tipo != ImagemManager.REI )
            return false;
        
        int cor = pecaIDUtil.getPecaCor( pid );
        RoqueJogo roque = jogo.getRoqueJogo( cor );
        
        if ( roque.isRoqueDir( pecaIDUtil, mat, i2, j2 ) || roque.isRoqueDir( pecaIDUtil, mat, i2, j2 ) )
            return true;                     
        
        return false;
    }
        
    public int getX( Tabuleiro tabuleiro, int matJ ) {
        int tx = tabuleiro.getTX();        
        int cw = tabuleiro.getCelulaW();        
        return tx + ( matJ*cw );
    }
    
    public int getY( Tabuleiro tabuleiro, int matI ) {
        int ty = tabuleiro.getTY();        
        int ch = tabuleiro.getCelulaH();        
        return ty + ( matI*ch );
    }
    
    public int getMatrizI( Tabuleiro tabuleiro, int mouseY ) {
        int ty = tabuleiro.getTY();
        int th = tabuleiro.getTH();
        
        int ch = tabuleiro.getCelulaH();
        
        if ( mouseY >= ty && mouseY < ty+th )
            return ( mouseY - ty ) / ch;
            
        return Const.INT_NULO;
    }
            
    public int getMatrizJ( Tabuleiro tabuleiro, int mouseX ) {
        int tx = tabuleiro.getTX();
        int tw = tabuleiro.getTW();
        
        int cw = tabuleiro.getCelulaW();
        
        if ( mouseX >= tx && mouseX < tx+tw )
            return ( mouseX - tx ) / cw;
            
        return Const.INT_NULO;
    }            
    
    @Override
    public Peca getPeca( int tipo ) {
        switch( tipo ) {
            case ImagemManager.PEAO: return peao;
            case ImagemManager.TORRE: return torre;
            case ImagemManager.BISPO: return bispo;
            case ImagemManager.CAVALO: return cavalo;
            case ImagemManager.RAINHA: return rainha;
            case ImagemManager.REI: return rei;
        }       
        return null;
    }        
    
}