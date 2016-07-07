package interfaces;

import java.util.ArrayList;

public interface RadocParser {
	ArrayList<String> obtenhaAtividadesDeEnsino();
	ArrayList<String> obtenhaAtividadesDeOrientacao();
	ArrayList<String> obtenhaAtividadesEmProjetos();
	ArrayList<String> obtenhaAtividadesDeExtensao();
	ArrayList<String> obtenhaAtividadesDeQualificacao();
	ArrayList<String> obtenhaAtividadesAcademicasEspeciais();
	ArrayList<String> obtenhaAtividadesAdministrativas();
	ArrayList<String> obtenhaProdutos();
	ArrayList<String> obtenhaAfastamentos();
}
