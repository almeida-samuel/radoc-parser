package parsers;

import utils.FileUtils;
import utils.MatcherUtils;
import utils.RegistrosUtils;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;

public class ResolucaoParser {

    private final String REGEX_TUDO = "[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)\\*\\÷\\–]+";
    private final String REGEX_ANEXO_II = "ANEXO II DA RESOLUÇÃO.+";

    private final String REGEX_SUPERGRUPO_I = "I – ATIVIDADES DE ENSINO[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)\\*\\÷]+(?=\\bII - PRODUÇÃO INTELECTUAL\\b)";
    private final String REGEX_SUPERGRUPO_II = "II - PRODUÇÃO INTELECTUAL[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)\\*\\÷]+(?=\\bIII – ATIVIDADES DE PESQUISA E EXTENSÃO\\b)";
    private final String REGEX_SUPERGRUPO_III = "III – ATIVIDADES DE PESQUISA E EXTENSÃO[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)\\*\\÷]+(?=\\bIV - ATIVIDADES ADMINISTRATIVAS E DE REPRESENTAÇÃO\\b)";
    private final String REGEX_SUPERGRUPO_IV = "IV - ATIVIDADES ADMINISTRATIVAS E DE REPRESENTAÇÃO[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)\\*\\÷\\–]+(?=\\bV - OUTRAS ATIVIDADES\\b)";
    private final String REGEX_SUPERGRUPO_V = "V - OUTRAS ATIVIDADES[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)\\*\\÷\\–]+(?=$)";

    private final String REGEX_SUBGRUPOS_I = "(I\\s*[\\-\\–]\\s*\\d{1}.+?(?=I\\s*[\\-\\–]\\s*\\d{1} | $))";
    private final String REGEX_SUBGRUPOS_II = "(II\\s*[\\-\\–]\\s*\\d{1}.+?(?=II\\s*[\\-\\–]\\s*\\d{1} | $))";
    private final String REGEX_SUBGRUPOS_III = "(III\\s*[\\-\\–]\\s*\\d{1}.+?(?=III\\s*[\\-\\–]\\s*\\d{1} | $))";
    private final String REGEX_SUBGRUPOS_IV = "(IV\\s*[\\-\\–]\\s*\\d{1}.+?(?=IV\\s*[\\-\\–]\\s*\\d{1} | $))";
    private final String REGEX_SUBGRUPOS_V = "(V\\s*[\\-\\–]\\s*\\d{1}.+?(?=V\\s*[\\-\\–]\\s*\\d{1} | $))";

    private final String REGEX_MICROGRUPOS_I = "";
    private final String REGEX_MICROGRUPOS_II = "";
    private final String REGEX_MICROGRUPOS_III = "";
    private final String REGEX_MICROGRUPOS_IV = "";
    private final String REGEX_MICROGRUPOS_V = "";

    private File arquivoFonte;
    private String conteudoResolucao;

    public ResolucaoParser(File arquivoFonte) {
        this.arquivoFonte = arquivoFonte;
        this.conteudoResolucao = "";
    }

    /**
     * Recupera o conteúdo da resolução (Somente anexo II).
     * @return A Resolução parseada (Somente anexo II).
     */
    public String obtenhaConteudoResolucao(){
        conteudoResolucao = obtenhaAnexoIIResolucao(FileUtils.extraiaTexto(arquivoFonte));
        return conteudoResolucao;
    }

    /**
     * Recupera somente o Anexo II da Resolução.
     * @param conteudoCompletoResolucao Texto completo extraído do .pdf da Resolução.
     * @return O texto equivalente ao Anexo II da Resolução.
     */
    private String obtenhaAnexoIIResolucao(String conteudoCompletoResolucao) {
        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        conteudoCompletoResolucao = RegistrosUtils.substituiOcorrencias(conteudoCompletoResolucao, substituicoes);

        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ANEXO_II, conteudoCompletoResolucao);
        if(matcher.find()) {
            return matcher.group();
        }

        return null;
    }

}
