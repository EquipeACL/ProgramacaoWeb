package br.uepb.interfaces;

import java.util.ArrayList;

/**
 * Essa � a interface que cont�m as assinaturas dos m�todos para criar, remover, atualizar e buscar objetos que implementem a interface Item_Acervo
 * @author EquipeACL
 *
 */
public interface Interface_manterAcervo {
	
	public boolean createItemAcervo(DAO_Acervo itemDao,IFAcervo item);
	public boolean removeItemAcervo(DAO_Acervo itemDao,IFAcervo item);
	public boolean updateItemAcervo(DAO_Acervo itemDao,IFAcervo item);
	public ArrayList<IFAcervo> searchItemAcervo(DAO_Acervo itemDao,String titulo);
	
}
