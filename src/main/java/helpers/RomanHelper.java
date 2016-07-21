package helpers;

/**
 * Auxilia na conversão de algarismos romanos para numerais inteiros.
 */
public class RomanHelper {

    final static char simbolos[] = {'M','D','C','L','X','V','I'};
    final static int   valores[] = {1000,500,100,50,10,5,1};

    /**
     * Recupera o valor inteiro de um número escrito em algarismos romanos.
     * @param romano Número escrito em algarismos romanos.
     * @return O valor inteiro que representa o os algarismos romanos do input.
     */
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
