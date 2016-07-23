package formatters;

public class FormatadorAfastamento extends FormatadorPadrao {

    public FormatadorAfastamento(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividade() {
        return "afastamento";
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "outras";
    }
}
