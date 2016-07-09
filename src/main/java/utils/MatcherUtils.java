package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtils {
    /**
     * Retorna o Matcher equivalente para um data regex e um dado conteudo.
     * @param regexConteudo Regex utilizada na configuração do matcher.
     * @param conteudo Conteudo no qual será feito a análise da regex.
     * @return O matcher configurado para a regex e o conteúdo em análise.
     */
    public static Matcher obtenhaMatcher (String regexConteudo, String conteudo){
        Pattern regex = Pattern.compile(regexConteudo);
        return regex.matcher(conteudo);
    }

}
