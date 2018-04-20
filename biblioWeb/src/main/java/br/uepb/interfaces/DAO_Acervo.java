package br.uepb.interfaces;

import java.util.ArrayList;

/**
 * Essa � a interface que cont�m as assinaturas dos m�todos para criar, remover, atualizar e buscar objetos de um tipo generico. Utilizado para implementar inser��o, remo��o, atualiza��o e busca de objetos do acervo.
 * @author EquipeACL
 *
 * @param <T>, tipo generico de dado.
 */
public interface DAO_Acervo<T> {
	
	public boolean createItemAcervo(T objeto);
	public boolean removeItemAcervo(T objeto);
	public boolean updateItemAcervo(T objeto);
	public ArrayList<T> searchItemAcervo(String titulo);
	
}
