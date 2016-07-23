package formatters;

public class FormatadorProduto extends FormatadorPadrao {

    public FormatadorProduto(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "producaoIntelectual";
    }
}
