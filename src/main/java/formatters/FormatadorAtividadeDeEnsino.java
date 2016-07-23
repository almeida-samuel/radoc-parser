package formatters;

import java.util.regex.Matcher;

public class FormatadorAtividadeDeEnsino extends FormatadorPadrao {

    public FormatadorAtividadeDeEnsino(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "ensino";
    }

    @Override
    public String obtenhaPontuacao(Matcher matcher) {
        Double cargaHoraria = Double.valueOf(matcher.group(3));
        Double valorHora = cargaHoraria/10;
        return String.format("%04d",Double.valueOf(valorHora * 10).intValue());
    }
}
