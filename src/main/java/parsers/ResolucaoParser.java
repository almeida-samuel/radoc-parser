package parsers;
import helpers.RomanHelper;
import utils.MatcherUtils;
import utils.RegistrosUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import static utils.FileUtils.extraiaTextoTXT;

public class ResolucaoParser {

    private static final String REGEX_ATIVIDADE = "(.+);(.+);(.+);(.+)";
    private static final String ATIVIDADES_DE_ENSINO = "ATIVIDADES DE ENSINO[\\s\\S]+PRODUÇÃO INTELECTUAL";
    private static final String PRODUCAO_INTELECTUAL = "PRODUÇÃO INTELECTUAL[\\s\\S]+ATIVIDADES DE PESQUISA E EXTENSÃO";
    private static final String ATIVIDADES_DE_PESQUISA_E_EXTENSÃO = "ATIVIDADES DE PESQUISA E EXTENSÃO[\\s\\S]+ATIVIDADES ADMINISTRATIVAS E DE REPRESENTAÇÃO";
    private static final String ATIVIDADES_ADMINISTRATIVAS_REPRESENTAÇÃO = "ATIVIDADES ADMINISTRATIVAS E DE REPRESENTAÇÃO[\\s\\S]+OUTRAS ATIVIDADES";
    private static final String OUTRAS_ATIVIDADES = "OUTRAS ATIVIDADES[\\s\\S]+";

    private File arquivoFonte;
    private String conteudoResolucao;

    public ResolucaoParser(File arquivoFonte) {
        this.arquivoFonte = arquivoFonte;
        this.conteudoResolucao = "";
    }

    /**
     * Recupera somente o Anexo II da Resolução.
     * @return O texto equivalente ao Anexo II da Resolução.
     */
    public HashMap<String, Map> obtenhaAtividadesResolucao() {
        HashMap<String, Map> atividades = new HashMap<String, Map>();

        atividades.put("ensino", obtenhaAtividades(ATIVIDADES_DE_ENSINO));
        atividades.put("producaointelectual", obtenhaAtividades(PRODUCAO_INTELECTUAL));
        atividades.put("pesquisaextensao", obtenhaAtividades(ATIVIDADES_DE_PESQUISA_E_EXTENSÃO));
        atividades.put("administrativas", obtenhaAtividades(ATIVIDADES_ADMINISTRATIVAS_REPRESENTAÇÃO));
        atividades.put("outras", obtenhaAtividades(OUTRAS_ATIVIDADES));

        return atividades;
    }


    private Map obtenhaAtividades(String regexGrupoAtividades){
        HashMap<String, Map> atividades = new HashMap<String, Map>();
        String conteudoResolucao = obtenhaConteudoResolucao();
        Matcher matcherGrupoAtividades = MatcherUtils.obtenhaMatcher(regexGrupoAtividades, conteudoResolucao);

        if(matcherGrupoAtividades.find()){
            String conteudoAtividades = matcherGrupoAtividades.group();
            Matcher matcherAtividades = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADE, conteudoAtividades);

            while(matcherAtividades.find()){
                atividades.put(matcherAtividades.group(3), monteMapaAtividade(matcherAtividades));
            }
        }

        return atividades;
    }

    private Map monteMapaAtividade(Matcher matcher){
        HashMap<String, String> atividade = new HashMap<String, String>();

        String k = matcher.group();

        atividade.put("codigo", matcher.group(1));
        atividade.put("indice", matcher.group(2));
        atividade.put("descricao", matcher.group(3));
        atividade.put("pontuacao", matcher.group(4));
        atividade.put("codgrupopontuacao", monteCodGrupoPontuacao(matcher));

        return atividade;
    }

    private String monteCodGrupoPontuacao(Matcher matcher){

        String naturezaAtividade = trateZeroEsquerda(String.valueOf(RomanHelper.valueOf(matcher.group(1).split("-")[0])));
        String subNaturezaAtividade = trateZeroEsquerda(matcher.group(1).split("-")[1]);
        String categoriaAtividade = "";
        String subCategoriaAtividade = "";
        String[] result = matcher.group(2).split("\\.");
        if(result.length == 1){
            categoriaAtividade = trateZeroEsquerda(result[0]);
            subCategoriaAtividade = "000";
        }else{
            categoriaAtividade = trateZeroEsquerda(result[0]);
            subCategoriaAtividade = trateZeroEsquerda(result[1]);
        }

        return naturezaAtividade + subNaturezaAtividade + categoriaAtividade + subCategoriaAtividade;
    }

    private String trateZeroEsquerda(String valor){
        if(Integer.valueOf(valor) < 10) return "00" + valor;
        if(Integer.valueOf(valor) <= 99) return "0" + valor;
        return valor;
    }

    /**
     * Recupera o conteúdo da resolução (Somente anexo II).
     * @return A Resolução parseada (Somente anexo II).
     */
    private String obtenhaConteudoResolucao(){
        if("".equals(conteudoResolucao)){
            conteudoResolucao = extraiaTextoTXT(arquivoFonte);
        }

        return conteudoResolucao;
    }

}
