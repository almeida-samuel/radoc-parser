package formatters;

public class FormatadorAtividadeDeOrientacao extends FormatadorPadrao {

    public FormatadorAtividadeDeOrientacao(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividade() {
        return "atividadeDeOrientacao";
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "outras";
    }
}
