package locadora;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edlon
 */
import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private String[] dependentes;
    private int qtdDependentes;
    private ArrayList<Locacao> locacoes = new ArrayList<>();
    
    public Cliente(){
        dependentes = new String[6];
        for(int i=1; i<=5; i++){
            dependentes[i] = "";
        }
        qtdDependentes = 0;
    }
    public Cliente(String nome, String cpf, String telefone){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        dependentes = new String[6];
        for(int i=1; i<=5; i++){
            dependentes[i] = "";
        }
        qtdDependentes = 0;
    }
    
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public String getTelefone(){
        return telefone;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public String getDependenteI(int i){
        try{
            if(dependentes[i].equals("")) throw new ArrayIndexOutOfBoundsException();
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Não é possível achar o dependente.");
            return "";
        }
        return dependentes[i];
    }
    public void setDependente(String dependente){
        try{
            dependentes[++qtdDependentes] = dependente;
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Não foi possível adicionar o depedente " + dependente
                    + ". Já excedeu a quantidade de dependentes.");
            qtdDependentes--;
        }
    }
    public String getDependentes(){
        String aux = "";
        if(qtdDependentes == 0){
            System.out.println("Não há dependentes.");
            aux = "0";
        }
        for(int i=1; i<=qtdDependentes; i++){
            if(qtdDependentes==i) aux += dependentes[i] + ".";
            else aux += dependentes[i] + ", ";
        }
        return aux;
    }
    public void imprimeDependentes(){
        for(int i=1; i<=qtdDependentes; i++){
            System.out.println("["+i+"]- " + dependentes[i]);
        }
    }
    public void delDependente(int id){
        dependentes[id] = "";
        for(int i=1; i<5; i++){
            if(dependentes[i]=="" && dependentes[i+1]!=""){
                dependentes[i] = dependentes[i+1];
                dependentes[i+1] = "";
            }
        }
        qtdDependentes--;
    }
    
    public boolean equals(Cliente cliente){
        if(cliente.getCpf().contains(this.cpf)) return true;
        else return false;
    }
    
    public void gravaLocacao(Locacao locacao){
        locacoes.add(locacao);
    }
    
    public void imprimeLocacoes(){
        System.out.println("Locações feitas pelo cliente " + nome + " e seus dependentes:");
        for(Locacao locacao : locacoes){
            locacao.imprimeLocacao();
        }
    }

    public int getQtdDependentes() {
        return qtdDependentes;
    }
    
}
