/**
 * TRABALHO PRÁTICO - DCC146 ASPECTOS TEÓRICOS DA COMPUTAÇÃO 2022/1
 * Professor: GLEIPH GHIOTTO LIMA DE MENEZES
 * Autores: Leonardo Silva da Cunha: 201676019
 *          Juarez de Paula Campos Júnior: 201676022
 *          Luiz Guilherme Almas Araujo:201676050
 */

package Componentes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Classe Estado com transições para cada símbolo encontrado na expressão da TAG.
 *
 */
public class Estado {
	private int estadoID;
	private Map<Character, ArrayList<Estado>> proxEstado;
	private Set <Estado> estado;
	private boolean eEstadoAceitacao;
	
	/**
         * Construtor usado pelo AFN.
         * @param ID é o índice do estado.
         */
	public Estado (int ID) {
		this.setIDestado(ID);
		this.setProximoEstado(new HashMap <Character, ArrayList<Estado>> ());
		this.setEestadoAceitacao(false);
	}
	
	// Construtor usado pelo AFD.
	public Estado(Set<Estado> estado, int ID) {
		this.setEstados(estado);
		this.setIDestado(ID);
		this.setProximoEstado(new HashMap <Character, ArrayList<Estado>> ());
		
		// descobre se há estado final no conjunto de estados.
		for (Estado p : estado) {
			if (p.eEstadoAceitacao()) {
				this.setEestadoAceitacao(true);
				break;
			}
		}
	}
	
	// Adiciona a transição entre os estados e insere na ArrayList.
	// ou cria o ArrayList com base na chave.
	public void addTransicao (Estado proximo, char chave) {
		ArrayList <Estado> lista = this.proxEstado.get(chave);
		
		if (lista == null) {
			lista = new ArrayList<Estado> ();
			this.proxEstado.put(chave, lista);
		}
		
		lista.add(proximo);
	}

	// Pega todos os estados de transição com base no símbolo.
	public ArrayList<Estado> getTransicoes(char c) {	
		if (this.proxEstado.get(c) == null)	{	return new ArrayList<Estado> ();	}
		else 								{	return this.proxEstado.get(c);	}
	}
	
	// GETS E SETS
        
	public Map<Character, ArrayList<Estado>> getProximoEstado() {
		return proxEstado;
	}

	public void setProximoEstado(HashMap<Character, ArrayList<Estado>> hashMap) {
		this.proxEstado = hashMap;
	}
	
	public int getIDestado() {
		return estadoID;
	}

	public void setIDestado(int estadoID) {
		this.estadoID = estadoID;
	}

	public boolean eEstadoAceitacao() {
		return eEstadoAceitacao;
	}

	public void setEestadoAceitacao(boolean aceitarEstado) {
		this.eEstadoAceitacao = aceitarEstado;
	}

	public Set <Estado> getEstados() {
		return estado;
	}

	public void setEstados(Set <Estado> estado) {
		this.estado = estado;
	}
}
