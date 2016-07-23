package formatters;

public class FormatadorAtividadeDeEnsino extends FormatadorPadrao {

    public FormatadorAtividadeDeEnsino(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "ensino";
    }
}
