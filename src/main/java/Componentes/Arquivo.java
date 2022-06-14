/**
 * TRABALHO PRÁTICO - DCC146 ASPECTOS TEÓRICOS DA COMPUTAÇÃO 2022/1
 * Professor: GLEIPH GHIOTTO LIMA DE MENEZES
 * Autores: Leonardo Silva da Cunha: 201676019
 *          Juarez de Paula Campos Júnior: 201676022
 *          Luiz Guilherme Almas Araujo: 201676050
 */

package Componentes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *Classe com os métodos que irá manipular os objetos (arquivos) que serão lidos 
 *pelo programa e que serão salvos na memória do computador.
 */
public class Arquivo {

	/**
	 * Método que tem como finalidade criar o arquivo na memória do computador.
	 * @param expressao  - Expressao que sera salva no arquivo.
	 * @param caminhoArq - Caminho do arquivo.
	 */
	public void setExpressao(String expressao, String caminhoArq) {
		try {
			boolean validador = true;
			File arqCaminho = new File(caminhoArq);
			if (!arqCaminho.exists()) {
				arqCaminho.createNewFile();
				validador = false;
			}
			FileWriter arq = new FileWriter(arqCaminho.getAbsoluteFile(), validador);
			BufferedWriter arqGravar = new BufferedWriter(arq);

			arqGravar.write(expressao + "\r");
			arqGravar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
        
       /**
	 * Método que tem como finalidade gravar a divisão da entrada em tags em um arquivo..
	 * @param divisoes  - As divisoes em tags.
	 * @param caminho - Caminho do arquivo.
	 */
        public void gravaDivisao(ArrayList<String> divisoes, String caminho){
            try {
			boolean validador = true;
			File arqCaminho = new File(caminho);
			if (!arqCaminho.exists()) {
				arqCaminho.createNewFile();
				validador = false;
			}
			FileWriter arq = new FileWriter(arqCaminho.getAbsoluteFile(), validador);
			BufferedWriter arqGravar = new BufferedWriter(arq);
                        for (int i=0; i<divisoes.size();i++){
                            arqGravar.write(divisoes.get(i) + "\n");
                        }
			
			arqGravar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        }

	/**
	 * Método que tem como finalidade ler o arquivo que está salvo no computador
	 * @param caminhoArq - Caminho do arquivo que sera lido.
	 * @return - Retorna o conteudo do arquivo.
	 */
	public ArrayList<String> getExpressao(String caminhoArq) {

		ArrayList<String> expressoes = new ArrayList<String>();
		try {
			FileReader entrada = new FileReader(caminhoArq);
			BufferedReader lerEntrada = new BufferedReader(entrada);
			String linha = lerEntrada.readLine();
			while (linha != null) {
				expressoes.add(linha);
				linha = lerEntrada.readLine();

			}
			entrada.close();
		} catch (IOException e) {
			System.out.println("[WARNING] ARQUIVO NAO EXISTE.");
			return null;
		}

		return expressoes;
	}
	
	
	/**
	 * Método para ver se um arquivo existe.
	 * @param caminhoArq Caminho do arquivo no sistema.
	 * @return true se existe, false se nao existe.
	 */
	public boolean existe(String caminhoArq) {
		File arq = new File(caminhoArq);
		if(arq.exists())
			return true;
		else 
			return false;
	}
	
}