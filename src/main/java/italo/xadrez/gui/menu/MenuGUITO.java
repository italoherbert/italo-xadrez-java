package italo.xadrez.gui.menu;

public interface MenuGUITO {
    
    public final static String FACIL =  "Facil";
    public final static String MEDIANO =  "Mediano";
    public final static String DIFICIL =  "Dificil";

    public final static int HUMANO =  1;
    public final static int COMPUTADOR = 2;
    
    public boolean isJogador1Humano();
    
    public Object getNivelJogadorComp1();
    
    public Object getNivelJogadorComp2();
    
}
