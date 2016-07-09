package services;

import utils.FileUtils;
import utils.MatcherUtils;
import utils.RegistrosUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class Atividades {
    private final String REGEX_ATIVIDADES_DE_ENSINO = "Atividades de ensino([\\p{L}\\s-\\d]+)Atividades de orientação";
    private final String REGEX_ATIVIDADES_DE_ORIENTACAO = "Atividades de orientação([\\p{L}\\s-\\d\\W]+)Atividades em projetos";
    private final String REGEX_ATIVIDADES_EM_PROJETOS = "Atividades em projetos([\\p{L}\\s-\\d\\W]+)Atividades de extensão";
    private final String REGEX_ATIVIDADES_DE_EXTENSAO = "Atividades de extensão([\\p{L}\\s-\\d\\W]+)Atividades de qualificação";
    private final String REGEX_ATIVIDADES_DE_QUALIFICACAO = "Atividades de qualificação[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Atividades acadêmicas especiais";
    private final String REGEX_ATIVIDADES_ACADEMICAS_ESPECIAIS = "Atividades acadêmicas especiais[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Atividades administrativas";
    private final String REGEX_ATIVIDADES_ADMINISTRATIVAS = "Atividades administrativas[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Produtos";

    private String conteudoArquivo;
    private File arquivoFonte;

    public Atividades(String conteudoArquivo, File arquivoFonte) {
        this.conteudoArquivo = conteudoArquivo;
        this.arquivoFonte = arquivoFonte;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesDeEnsino() {
        ArrayList<String> atividadesDeEnsino;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_DE_ENSINO, conteudoDoArquivo);
        String regexAtividadesIndividuais = "(.+?)()(\\d+).+?(\\d{4}).+?()(SIM|NÃO)";
        String regexAtividadeUnica = "(.+?)()(\\d{2,3}).+(\\d{4}).+()(SIM|NÃO)";
        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades de ensino", "");
        substituicoes.put("Atividades de orientação", "");
        substituicoes.put("\n+", "");
        substituicoes.put("\r+", "");

        atividadesDeEnsino = RegistrosUtils.obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesDeEnsino;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesDeOrientacao() {
        ArrayList<String> atividadesDeOrientacao;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_DE_ORIENTACAO, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades de orientação", "");
        substituicoes.put("Atividades em projetos", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Título do trabalho:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+\\d+\\/\\d+\\/\\d+";
        String regexAtividadeUnica = "Título do trabalho:([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Tabela:([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Orientando:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data início:\\s+(\\d+\\/\\d+\\/\\d+)[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+(\\d+\\/\\d+\\/\\d+)";

        atividadesDeOrientacao = RegistrosUtils.obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesDeOrientacao;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesEmProjetos() {
        ArrayList<String> atividadesEmProjetos;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_EM_PROJETOS, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades em projetos", "");
        substituicoes.put("Atividades de extensão", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Título do Projeto:.*?Data Término:\\s*[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}";
        String regexAtividadeUnica = "Título do Projeto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)\\s+Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Unidade Responsável:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data Início:\\s+(\\d+\\/\\d+\\/\\d+)\\s+Data Término:\\s+(\\d+\\/\\d+\\/\\d+)";

        atividadesEmProjetos = RegistrosUtils.obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesEmProjetos;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesDeExtensao() {
        ArrayList<String> atividadesDeOrientacao = new ArrayList<String>();
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_DE_EXTENSAO, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades de extensão", "");
        substituicoes.put("Atividades de qualificação", "Tabela");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        if(matcher.find()) {
            conteudoDoArquivo = matcher.group();
            conteudoDoArquivo = RegistrosUtils.substituiOcorrencias(conteudoDoArquivo, substituicoes);

            String regexAtividadesIndividuais = "Tabela:.+?(?=Tabela)";
            Matcher matcherAtividadesIndividuais = MatcherUtils.obtenhaMatcher(regexAtividadesIndividuais, conteudoDoArquivo);
            int contadorAtividadesIndividuais = 0;

            while(matcherAtividadesIndividuais.find()) {
                Matcher matcherAtividade;
                String regexAtividadeUnica = "Tabela:.+?CHA:.+?(\\d+).+?Data início:.+?(\\d+\\/\\d+\\/\\d+).+?Data término:.+?(\\d+\\/\\d+\\/\\d+).+?Descrição da atividade:.+?(.+).+?Descrição da clientela:.+?(.+)";
                matcherAtividade = MatcherUtils.obtenhaMatcher(regexAtividadeUnica, matcherAtividadesIndividuais.group());
                while(matcherAtividade.find()) {
                    String atividadeTratada = trateAtividadeDeExtensao(matcherAtividade);
                    Matcher matcherAtividadeTratada = MatcherUtils.obtenhaMatcher("(.+?)\\n(.+?)\\n(.+?)\\n(.+)\\n(.+)",atividadeTratada);
                    matcherAtividadeTratada.find();
                    atividadesDeOrientacao.add(RegistrosUtils.obtenhaLinhaDeRegistroPadronizado(contadorAtividadesIndividuais, matcherAtividadeTratada));
                }

                contadorAtividadesIndividuais++;
            }
        }

        return atividadesDeOrientacao;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesDeQualificacao() {
        ArrayList<String> atividadesDeQualificacao;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_DE_QUALIFICACAO, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades de qualificação", "");
        substituicoes.put("Atividades acadêmicas especiais", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesQualificacaoIndividuais = "Tabela:\\s+[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data de término:\\s+\\d{2}\\/\\d{2}\\/\\d{4}";
        String regexAtividadesQualificacaoUnica = "Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Descrição:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)CHA:\\s+(\\d+)\\s+Data de início:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+Data de término:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})";

        atividadesDeQualificacao = RegistrosUtils.obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesQualificacaoIndividuais, regexAtividadesQualificacaoUnica, substituicoes);

        return atividadesDeQualificacao;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesAcademicasEspeciais() {
        ArrayList<String> atividadesAcademicasEspeciais = new ArrayList<String>();
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_ACADEMICAS_ESPECIAIS, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades acadêmicas especiais", "");
        substituicoes.put("Atividades administrativas", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        if(matcher.find()) {
            conteudoDoArquivo = matcher.group();
            conteudoDoArquivo = RegistrosUtils.substituiOcorrencias(conteudoDoArquivo, substituicoes);

            String regexAtividadesIndividuais = "Tabela:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?(?=\\bTabela\\b|$)";
            Matcher matcherAtividadesIndividuais = MatcherUtils.obtenhaMatcher(regexAtividadesIndividuais, conteudoDoArquivo);
            int contadorAtividadesIndividuais = 0;

            while(matcherAtividadesIndividuais.find()) {
                Matcher matcherAtividade;
                String regexAtividadeUnica = "Tabela:(.+?)CHA:.+?(\\d+).+?Data início:.+?(\\d{2}\\/\\d{2}\\/\\d{4}).+?Data término:.+?(\\d{2}\\/\\d{2}\\/\\d{4}).+?Descrição Complementar:.+?(.+?)Descrição da Clientela:.+";
                matcherAtividade = MatcherUtils.obtenhaMatcher(regexAtividadeUnica, matcherAtividadesIndividuais.group());
                while(matcherAtividade.find()) {
                    String atividadeTratada = trateAtividadeAcademicaEspecial(matcherAtividade);
                    Matcher matcherAtividadeTratada = MatcherUtils.obtenhaMatcher("(.+?)\\n(.+?)\\n(.+?)\\n(.+)\\n(.+)",atividadeTratada);
                    matcherAtividadeTratada.find();
                    atividadesAcademicasEspeciais.add(RegistrosUtils.obtenhaLinhaDeRegistroPadronizado(contadorAtividadesIndividuais, matcherAtividadeTratada));
                }

                contadorAtividadesIndividuais++;
            }
        }

        return atividadesAcademicasEspeciais;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesAdministrativas() {
        ArrayList<String> atividadesAdministrativas;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_ADMINISTRATIVAS, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades administrativas", "");
        substituicoes.put("Produtos", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "\\s+Tabela:\\s+[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+\\d{2}\\/\\d{2}\\/\\d{4}";
        String regexAtividadeUnica = "\\s+Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Descrição:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Órgão emissor[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data início:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+Data término:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})";

        atividadesAdministrativas = RegistrosUtils.obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesAdministrativas;
    }

    /**
     *Trata as atividades de extensão ao padrão de grupos
     * @param matcher Matcher para a atividade.
     * @return Atividade tratada
     */
    private String trateAtividadeDeExtensao(Matcher matcher){
        return matcher.group(4) + "\n" + matcher.group(5) + "\n" + matcher.group(1) + "\n" + matcher.group(2) + "\n" + matcher.group(3);
    }

    /**
     * Rearranjo das atividadades acadêmicas especiais para a adequação ao formato
     * definido pelo método obtenhaLinhaDeRegistroPadronizado.
     * @param matcher Matcher para a atividade.
     * @return Rearranjo das atividades;
     */
    private String trateAtividadeAcademicaEspecial(Matcher matcher) {
        return matcher.group(1) + "\n" + matcher.group(5) + "\n" + matcher.group(2) + "\n" + matcher.group(3) + "\n" + matcher.group(4);
    }
}
