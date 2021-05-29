package italo.xadrez.nucleo.mat;

public class MatrizPecas implements Matriz {
        
    private final int[][] matriz;

    public MatrizPecas( int ilen, int jlen ) {
        this.matriz = new int[ ilen ][ jlen ];
    }
    
    @Override
    public void inicializa( MatrizInicializador ini ) {
        ini.inicializa( matriz );
    }
    
    @Override
    public int getJLen() {
        return matriz[ 0 ].length;
    }

    @Override
    public int getILen() {
        return matriz.length;
    }

    @Override
    public int getValor( int i, int j ) {
        return matriz[ i ][ j ];
    }

    @Override
    public void setValor( int i, int j, int valor ) {
        matriz[ i ][ j ] = valor;       
    }

    @Override
    public void copia( Matriz matCopia ) {
        for( int i = 0; i < matriz.length; i++ )
            for( int j = 0; j < matriz[i].length; j++ )
                matCopia.setValor( i, j, matriz[ i ][ j ] );
    }
    
}
