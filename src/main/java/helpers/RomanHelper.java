package helpers;

/**
 * Auxilia na conversão de algarismos romanos para numerais inteiros.
 */
public class RomanHelper {

    private final static char simbolos[] = {'M','D','C','L','X','V','I'};
    private final static int   valores[] = {1000,500,100,50,10,5,1};

    public static int valueOf(String romano) {
        romano = romano.toUpperCase();
        if(romano.length() == 0) return 0;
        for(int i = 0; i < simbolos.length; i++) {
            int pos = romano.indexOf(simbolos[i]) ;
            if(pos >= 0)
                return valores[i] - valueOf(romano.substring(0,pos)) + valueOf(romano.substring(pos+1));
        }
        throw new IllegalArgumentException("Algarismo romano inválido.");
    }
}
