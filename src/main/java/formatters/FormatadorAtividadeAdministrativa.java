package formatters;

import java.util.regex.Matcher;

public class FormatadorAtividadeAdministrativa extends FormatadorPadrao {

    public FormatadorAtividadeAdministrativa(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "administrativas";
    }

    @Override
    public String obtenhaPontuacao(Matcher matcher) {
        return super.obtenhaPontuacaoBaseadaEmAnos(matcher);
    }
}
