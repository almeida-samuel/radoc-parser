package utils;

import parsers.ResolucaoParser;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class RegistrosUtils {

    private static final String PATH_RESOLUCAO_TXT = "src/main/resources//atividadesconsuni32-2013.txt";
    private static ResolucaoParser resolucaoParser = new ResolucaoParser(new File(PATH_RESOLUCAO_TXT));

    public static ArrayList<String> obtenhaRegistros(Matcher matcherGeral, String regexRegistrosIndividuais, String regexRegistroUnico, HashMap substituicoes){
        ArrayList<String> registros = new ArrayList<String>();

        if(matcherGeral.find()) {
            String conteudoDoArquivo = matcherGeral.group();
            conteudoDoArquivo = substituiOcorrencias(conteudoDoArquivo, substituicoes);

            Matcher matcherProdutosIndividuais = MatcherUtils.obtenhaMatcher(regexRegistrosIndividuais, conteudoDoArquivo);
            int contadorProdutosIndividuais = 0;

            while(matcherProdutosIndividuais.find()) {
                Matcher matcherAtividade;
                matcherAtividade = MatcherUtils.obtenhaMatcher(regexRegistroUnico, matcherProdutosIndividuais.group());

                while(matcherAtividade.find()) {
                    registros.add(obtenhaLinhaDeRegistroPadronizado(contadorProdutosIndividuais, matcherAtividade));
                }

                contadorProdutosIndividuais++;
            }
        }

        return registros;
    }


    /**
     * Substitui as ocorrências de determinada regex (key) por um dado valor (value)
     * em um determinado conteúdo alvo.
     * @param conteudo Conteúdo alvo no qual se deseja substituir as ocorrências.
     * @param substituicoes Map, em que: key= regex que se deseja substituir, value= valor da substituição.
     * @return O conteúdo alterado, já com as substituições feitas.
     */
    public static String substituiOcorrencias(String conteudo, HashMap<String, String> substituicoes) {
        for(String key : substituicoes.keySet()) {
            conteudo = conteudo.replaceAll(key, substituicoes.get(key));
        }
        return conteudo;
    }


    /**
     * Obtém a linha padronizada de um registro.
     * @param sequencial Número sequencial que identifica o registro na atividade de um determinado tipo.
     * @param matcher Matcher para a atividade.
     * @return Linha padronizada de um registro.
     */
    public static String obtenhaLinhaDeRegistroPadronizado(int sequencial, Matcher matcher) {
        HashMap<String, Map> anexoIIResolucao = resolucaoParser.obtenhaAtividadesResolucao();
        String linhaDeRegistroPadrao = "";

        String codGrupoPontuacao = "";
        for(String atividade : anexoIIResolucao.keySet()) {
            String key = matcher.group(2).toLowerCase().trim();
            HashMap<String, Map> mapaAtividade = (HashMap<String, Map>) anexoIIResolucao.get(atividade).get(key);

            if(mapaAtividade != null) {
                codGrupoPontuacao = String.valueOf(mapaAtividade.get("codGrupoPontuacao"));
                break;
            }

            codGrupoPontuacao = "000000000000";
        }

        linhaDeRegistroPadrao += codGrupoPontuacao + ", ";
        linhaDeRegistroPadrao += (sequencial+1) + ", ";
        linhaDeRegistroPadrao += matcher.group(1) + " " + matcher.group(2) + ", ";
        linhaDeRegistroPadrao += trataCargaHoraria(matcher) + ", ";
        linhaDeRegistroPadrao += trataDatas(matcher);

        return linhaDeRegistroPadrao;
    }

    /**
     * Recebe o matcher da atividade qualquer e trata a carga horária anual.
     * @param matcher Matcher para a atividade.
     * @return A carga horária anual da atividade.
     */
    private static String trataCargaHoraria(Matcher matcher) {
        if (!"".equals(matcher.group(3)) && !" ".equals(matcher.group(3))) {
            return matcher.group(3);
        }

        String dtInicio = matcher.group(4).trim();
        String dtFim = matcher.group(5).trim();

        if ("".equals(dtInicio) || " ".equals(dtInicio)) dtInicio = dtFim;
        if ("".equals(dtFim) || " ".equals(dtFim)) dtFim = dtInicio;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dtInicioDate = simpleDateFormat.parse(dtInicio);
            Date dtFimDate = simpleDateFormat.parse(dtFim);

            long diferenca = TimeUnit.DAYS.convert(dtFimDate.getTime() - dtInicioDate.getTime(), TimeUnit.MILLISECONDS);
            if(diferenca == 0) return String.valueOf(8);
            return String.valueOf(diferenca * 8);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Recebe o matcher da atividade qualquer e trata suas datas de início e fim.
     * @param matcher Matcher para a atividade.
     * @return Concatenação das datas de início e fim.
     */
    private static String trataDatas(Matcher matcher) {

        String dtInicio = matcher.group(4).replaceAll("/", "").trim();
        String dtFim = matcher.group(5).replaceAll("/", "").trim();

        if ("".equals(dtInicio)) dtInicio = dtFim;
        if ("".equals(dtFim)) dtFim = dtInicio;

        return (dtInicio + ", " + dtFim);
    }
}
