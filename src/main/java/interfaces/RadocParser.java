package interfaces;

import java.util.List;

public interface RadocParser {
	List<String> obtenhaAtividadesDeEnsino();
	List<String> obtenhaAtividadesDeOrientacao();
	List<String> obtenhaAtividadesEmProjetos();
	List<String> obtenhaAtividadesDeExtensao();
	List<String> obtenhaAtividadesDeQualificacao();
	List<String> obtenhaAtividadesAcademicasEspeciais();
	List<String> obtenhaAtividadesAdministrativas();
	List<String> obtenhaProdutos();
	List<String> obtenhaAfastamentos();
}
