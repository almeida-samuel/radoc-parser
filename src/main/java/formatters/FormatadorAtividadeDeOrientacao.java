package formatters;

import java.util.regex.Matcher;

public class FormatadorAtividadeDeOrientacao extends FormatadorPadrao {

    public FormatadorAtividadeDeOrientacao(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "outras";
    }

    @Override
    public String obtenhaPontuacao(Matcher matcher) {
        return super.obtenhaPontuacaoBaseadaEmAnos(matcher);
    }
}
