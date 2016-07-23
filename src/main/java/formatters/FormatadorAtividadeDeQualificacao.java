package formatters;

public class FormatadorAtividadeDeQualificacao extends FormatadorPadrao {

    public FormatadorAtividadeDeQualificacao(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividade() {
        return "atividadeDeQualificacao";
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "outras";
    }
}
