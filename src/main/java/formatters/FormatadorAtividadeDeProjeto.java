package formatters;

import java.util.regex.Matcher;

public class FormatadorAtividadeDeProjeto extends FormatadorPadrao {

    public FormatadorAtividadeDeProjeto(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "pesquisaExtensao";
    }

    @Override
    public String obtenhaPontuacao(Matcher matcher) {
        return super.obtenhaPontuacaoBaseadaEmAnos(matcher);
    }
}
