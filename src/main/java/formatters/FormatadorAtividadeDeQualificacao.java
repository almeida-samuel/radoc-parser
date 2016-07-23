package formatters;

import java.util.regex.Matcher;

public class FormatadorAtividadeDeQualificacao extends FormatadorPadrao {

    public FormatadorAtividadeDeQualificacao(String pathResolucao) {
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

    @Override
    public int obtenhaNumeroGrupoTabela() {
        return 1;
    }
}
