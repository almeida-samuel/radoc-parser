package formatters;

import parsers.ResolucaoParser;
import utils.MatcherUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public abstract class FormatadorPadrao {

    private ResolucaoParser resolucaoParser;

    public FormatadorPadrao(String pathResolucao) {
        this.resolucaoParser = new ResolucaoParser(new File(pathResolucao));
    }

    /**
     *
     * @param matcherGeral
     * @param regexRegistrosIndividuais
     * @param regexRegistroUnico
     * @param substituicoes
     * @return
     */
    public ArrayList<String> obtenhaRegistros(Matcher matcherGeral, String regexRegistrosIndividuais, String regexRegistroUnico, HashMap substituicoes){
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
    public String substituiOcorrencias(String conteudo, HashMap<String, String> substituicoes) {
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
    public String obtenhaLinhaDeRegistroPadronizado(int sequencial, Matcher matcher) {
        String linhaDeRegistroPadrao = "";

        linhaDeRegistroPadrao += obtenhaCodGrupoPontuacao(matcher) + ", ";
        linhaDeRegistroPadrao += obtenhaPontuacao(matcher) + ", ";
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

    /**
     * Recupera o codGrupoPontuacao de uma dada atividade.
     * @param matcher Matcher para a atividade.
     * @return O codGrupoPontuacao.
     */
    private String obtenhaCodGrupoPontuacao(Matcher matcher) {
        String codGrupoPontuacao = obtenhaCampo(matcher, "codGrupoPontuacao");
        if (codGrupoPontuacao.equals("")) {
            return "000000000000";
        }
        return codGrupoPontuacao;
    }

    /**
     * Método que será implementado pelas classes que herdarem de FormatadorPadrao.
     * <p>Recupera o tipo de atividade da resolução tratada.</p>
     * @return O tipo de atividade da resolução tratada.
     */
    public abstract String obtenhaTipoAtividadeResolucao();

    /**
     * Calcula a pontuação do registro baseado em suas datas.
     * @param matcher O matcher para a atividade.
     * @return A pontuação.
     */
    public String obtenhaPontuacao(Matcher matcher) {
        return obtenhaCampo(matcher, "pontuacao");
    }

    /**
     * Calcula a pontuação do registro baseado em suas datas.
     * @param matcher O matcher para a atividade.
     * @return A pontuação.
     */
    public String obtenhaCampo(Matcher matcher, String campo) {
        HashMap<String, Map> anexoIIResolucao = this.resolucaoParser.obtenhaAtividadesResolucao();
        String key = matcher.group(2).toLowerCase().trim();

        String codGrupoPontuacao = "";
        HashMap<String, Map> mapaAtividade = (HashMap<String, Map>) anexoIIResolucao.get(obtenhaTipoAtividadeResolucao());

        for(String k : mapaAtividade.keySet()) {
            if(k.equals(key) || k.contains(key)) {
                codGrupoPontuacao = String.valueOf(mapaAtividade.get(k).get(campo));
                break;
            }
        }

        return codGrupoPontuacao;
    }

}
