/*
  TRABALHO PRÁTICO - DCC146 ASPECTOS TEÓRICOS DA COMPUTAÇÃO 2022/1
  Professor: GLEIPH GHIOTTO LIMA DE MENEZES
  Autores: Leonardo Silva da Cunha: 201676019
           Juarez de Paula Campos Júnior: 201676022
           Luiz Guilherme Almas Araujo: 201676050
 */


package componentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Menu {

    private static String warningImplementacao = "[WARNING] - Função não implementada";
    
    //private String definicao;
    private static Scanner leitura = new Scanner(System.in);
    private ArrayList<String> tagsMemoria = new ArrayList<>();
    private String cmd = "";
    private String caminho = "";
    private String entradaInformada = "";
    private ArrayList<String> divisoesMemoria = new ArrayList<>();
    ClassificaString cs = new ClassificaString();
    
    /**
     * Função menu, que dará todo o inicio do programa (logica)
     * onde o comando :d realiza a divisão em tags da string do arquivo informado ex: ":d input.txt"
     * onde o comando :c carrega um arquivo com definições de tags
     * onde o comando :o especifica o caminho do arquivo de saída para a divisão em tags ex: ":o output.txt" 
     * onde o comando :p realiza a divisão em tags da entrada informada
     * onde o comando :a Lista as definições formais dos autômatos em memória
     * onde o comando :l Lista as definições de tag válidas 
     * onde o comando :q sair do programa
     * onde o comando :s salva as tags em um arquivo
     */
    public void menu(){

        boolean flag = true;
        while (flag) {
            entrada();

            switch (this.cmd) {
                
            case ":q":
                System.out.println("[INFO] - Encerrando execucao.");
                flag = false;
                break;
            case ":d": 
//                realiza a divisao em tags da string do arquivo informado
//                entradaParaDividirArq(this.caminho);
                System.out.println(warningImplementacao);
                break;
            case ":c":
                definicoesDeTag(this.caminho);
                break;                
            case ":o":
//                salvaDivisaoTags(this.caminho);
                System.out.println(warningImplementacao);
                break;                
            case ":p":
//                realiza a divisão em tags da entrada informada
//                entradaParaDividir();
                System.out.println(warningImplementacao);
                break;
            case ":a":
//                tagsParaAutomato();
//                geraAutomato();
                System.out.println(warningImplementacao);
                break;
            case ":l":
                for (String s : this.tagsMemoria) {
                    System.out.println(s);
                }
                break;
            case ":s":
                salvarTags(this.caminho);
                break;
            
            default:
                adicionaTagMemoria(cmd);
                break;
            }
        }
    }
    
    /**
     * Pega a String informada pelo usuário, manda para a classe Automato e lá e feita a divisão.
     * Recebe de volta a divisão em TAGs, apresenta para o usuário e também as prepara para gravação em arquivo.
     */
    private void entradaParaDividir(){
        //automato.divideEntrada(this.entradaInformada);
        ArrayList<String> divisoes = cs.divideString(this.entradaInformada);
        String divs = "";
        for (String divisoe : divisoes) {
            divs = divs + divisoe + " ";

        }
        System.out.println(divs);
        divisoesMemoria.add(divs);
    }
    
    /**
     * Recebe Strings para divisão de um arquivo, cujo caminho foi informado pelo usuário.
     * Salva as expressões em um array, envia cada uma para divisão na classe Automato.
     * Recebe de volta as divisões em TAG, mostra para o usuário e as prepara para gravação em arquivo.
     * @param caminho Caminho do arquivo
     */
    private void entradaParaDividirArq(String caminho){
        Arquivo arq = new Arquivo();
        ArrayList<String> expressoes = arq.getExpressao(caminho);

        for (String expressoe : expressoes) {
            if (expressoe != null) {
                ArrayList<String> divisoes = cs.divideString(expressoe);
                String divs = "";
                for (String divisoe : divisoes) {
                    divs = divs + divisoe + " ";

                }
                System.out.println(divs);
                divisoesMemoria.add(divs);
            }
        }
        
        
    }
    
    public void tagsParaAutomato(){
//        for(int i=0; i<tagsMemoria.size();i++){
//            automato.novoEstado(tagsMemoria.get(i));
//        }
    }
    
    public void geraAutomato(){
        for (String s : tagsMemoria) {
            cs.adAFN(GeraAutomato.geraAFN(s));
        }
    }
    
    /**
     * Função com objetivo de validar o comando passado ou se não for comando, será
     * considerado como uma tag.
     * @return true ou false para caso válido ou não.
     */
    private boolean entrada(){ // lê e entrada
        String linha = leitura.nextLine();
        String[] comando = linha.split(" ");
        if(linha.isEmpty()){
            System.out.println("[INFO] - Nenhuma instrução enviada. Por favor digite alguma instrução!");
            return entrada();
        }
        if(linha.charAt(0) == ' ' ){
            System.out.println("[ERRO] - Comando começou com espaço");
            return false;
        }else if(comando.length > 2){
            System.out.println("[Erro] - Comando inválido");
        }else if(comando[0].charAt(0) == ':'){
            if(!validadeComando(comando))
                return entrada();
            else
                return true;
        }else{
            this.cmd = linha;
        }
        return true;
    }
    
    /**
     * Função que tem o objetivo de reconhecer o comando e atribuir na variavel
     * 'cmd' e se caso, o comando for algum que é necessário de um caminho, o mesmo
     * atribui o caminho na variavel 'caminho'.
     * @param comando Comando de entrada
     * @return  true ou false para caso válido ou não
     */
    private boolean validadeComando(String[] comando) {
        if(comando.length == 1) { // verifica os comandos sem parametro
            if(comando[0].equals(":a") || comando[0].equals(":l") || comando[0].equals(":q")) {
                this.cmd = comando[0];
                return true;
            }
            else { // casos de comando sem paramêtro que não existem
                System.out.println("[ERRO] COMANDO INVALIDO");
                return false;
            }
        }
        else { // separa o comando do parametro
            if (comando[0].equals(":p")){
                this.cmd = comando[0];
                this.entradaInformada = comando[1];
                return true;
            } else
                if (comando[0].equals(":d") || comando[0].equals(":c") || comando[0].equals(":o")
                        || comando[0].equals(":s")) {
                    this.cmd = comando[0];
                    this.caminho = comando[1];
                    return true;
                } else { // casos de comando com paramêtro que não existem
                    System.out.println("[ERRO] COMANDO INVALIDO");
                    return false;
                }
        }
    }
    
    /**
     * Função que tem como objetivo, ler um arquivo passado pelo usuario e verificar
     * se as tags contidas nele, são tags válidas ou não e se caso for, adicionar 
     * essas tags na memoria de execução do programa (caso alguma tag desse arquivo
     * tiver o mesmo nome de alguma tag já salva na memoria de execução do programa,
     * a mesma será desconsiderada).
     * @param caminhoTag 
     */
    private void definicoesDeTag(String caminhoTag) {
        Arquivo arq = new Arquivo();

        ArrayList<String> tagsArq;
        if ((tagsArq = arq.getExpressao(caminhoTag)) != null) {
            ReconheceTag[] er = new ReconheceTag[tagsArq.size()];

            // A ULTIMA VEZ QUE A TAG FOI INSERIDA NO
            // ARQUIVO EH A QUE SERA VALIDA.
            Collections.reverse(tagsArq);

            for (int i = 0; i < tagsArq.size(); i++) {
                er[i] = new ReconheceTag(tagsArq.get(i));
                if (er[i].validaExpressao())
                    this.tagsMemoria.add(tagsArq.get(i));
            }
        }
    }
    
    /**
     * Salva a divisão da entrada em tags em um arquivo.
     * @param caminhoDiv - Caminho do arquivo.
     */
    private void salvaDivisaoTags(String caminhoDiv){
        Arquivo salvaDiv = new Arquivo();
        salvaDiv.gravaDivisao(divisoesMemoria, caminho);
    }
    
    /**
     * Função que tem como objetivo, salvar as tags válidas que estão na memoria
     * de execução do programa em um arquivo na memoria do computador.
     * @param caminhoTag 
     */
    private void salvarTags(String caminhoTag){
        Arquivo arq = new Arquivo();

        if (!this.tagsMemoria.isEmpty()) {
            if (!arq.existe(caminhoTag)) {
                for (String s : this.tagsMemoria) arq.setExpressao(s, caminhoTag);
                System.out.println("[INFO] ARQUIVO CRIADO - TAGS ADICIONADAS.");
            } else {
                for (String s : this.tagsMemoria) arq.setExpressao(s, caminhoTag);
                System.out.println("[INFO] ARQUIVO SOBRESCRITO COM NOVAS TAGS.");
            }
        } else {
            System.out.println("[WARNING] NAO HA TAGS A SEREM ADICIONADAS.");
        }
    }
    
    /**
     * Função que tem com objetivo, verificar a tag que foi passada pelo usuario
     * é uma tag válida ou não. Se caso for válido, adiciona na memoria de execução 
     * do programa e se caso não, retorna uma mensagem de erro.
     * @param tag Tag que será adicionada a memória
     */
    private void adicionaTagMemoria(String tag) {
            ReconheceTag re = new ReconheceTag(tag);
            if (re.validaExpressao())
                    this.tagsMemoria.add(tag);

    }
    
}
