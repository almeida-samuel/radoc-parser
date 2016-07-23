package formatters;

import parsers.ResolucaoParser;
import utils.MatcherUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public abstract class FormatadorPadrao {

    private ResolucaoParser resolucaoParser;

    public abstract String obtenhaTipoAtividadeResolucao();

    public int obtenhaNumeroGrupoTabela() {
        return 2;
    }

    public FormatadorPadrao(String pathResolucao) {
        this.resolucaoParser = new ResolucaoParser(new File(pathResolucao));
    }

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

    public String substituiOcorrencias(String conteudo, HashMap<String, String> substituicoes) {
        for(String key : substituicoes.keySet()) {
            conteudo = conteudo.replaceAll(key, substituicoes.get(key));
        }
        return conteudo;
    }

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

    private static String trataDatas(Matcher matcher) {

        String dtInicio = matcher.group(4).replaceAll("/", "").trim();
        String dtFim = matcher.group(5).replaceAll("/", "").trim();

        if ("".equals(dtInicio)) dtInicio = dtFim;
        if ("".equals(dtFim)) dtFim = dtInicio;

        return (dtInicio + ", " + dtFim);
    }

    private String obtenhaCodGrupoPontuacao(Matcher matcher) {
        String codGrupoPontuacao = obtenhaCampo(matcher, "codGrupoPontuacao");
        if (codGrupoPontuacao.equals("")) {
            return "000000000000";
        }
        return codGrupoPontuacao;
    }

    public String obtenhaPontuacao(Matcher matcher) {
        String codPontuacao = obtenhaCampo(matcher, "pontuacao");
        if (codPontuacao.equals("")) {
            return "0000";
        }
        return String.format("%04d", Integer.valueOf(codPontuacao));
    }

    public ResolucaoParser getResolucaoParser() {
        return resolucaoParser;
    }

    public String obtenhaCampo(Matcher matcher, String campo) {
        HashMap<String, Map> anexoIIResolucao = getResolucaoParser().obtenhaAtividadesResolucao();
        String key = matcher.group(obtenhaNumeroGrupoTabela()).toLowerCase().trim();

        String codGrupoPontuacao = "";
        HashMap<String, Map> mapaAtividade = (HashMap<String, Map>) anexoIIResolucao.get(obtenhaTipoAtividadeResolucao());

        for(String k : mapaAtividade.keySet()) {
            if((k.equals(key) || k.contains(key)) && !key.equals("")) {
                codGrupoPontuacao = String.valueOf(mapaAtividade.get(k).get(campo));
                break;
            }
        }

        return codGrupoPontuacao;
    }

    public static int obtenhaDiferencaEmMeses(Date comeco, Date fim) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(comeco);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(fim);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return diffMonth;
    }

    public String obtenhaPontuacaoBaseadaEmAnos(Matcher matcher) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String pontuacaoString = obtenhaCampo(matcher, "pontuacao");

        if(pontuacaoString.equals("")) return "0000";

        Integer pontuacao = Integer.valueOf(pontuacaoString);

        try {
            Date dataInicio = simpleDateFormat.parse(matcher.group(4));
            Date dataFim = simpleDateFormat.parse(matcher.group(5));
            Integer anos = obtenhaDiferencaEmMeses(dataInicio, dataFim) / 12;
            if(anos == 0){
                anos = 1;
            }
            return String.format("%04d", anos * pontuacao );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "0000";
    }

}
