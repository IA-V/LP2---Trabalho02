package model;
import java.util.Hashtable;

public class Curriculo {
	private String id;
	private String nomeCompleto;
	private Hashtable<Integer, Producao> producoes;
	private Hashtable<String, Setor> setores;
	
	public Curriculo(String id, String nomeCompleto) {
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.producoes = new Hashtable<Integer, Producao>();
		this.setores = new Hashtable<String, Setor>();
	}
	
	public void adicionarSetor(Setor setor, String key) {
		this.setores.put(key, setor);
	}
	
	public void adicionarProducao(Producao producao, Integer key) {
		this.producoes.put(key, producao);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Hashtable<Integer, Producao> getProducoes() {
		return producoes;
	}

	public void setProducoes(Hashtable<Integer, Producao> producoes) {
		this.producoes = producoes;
	}

	public Hashtable<String, Setor> getSetores() {
		return setores;
	}

	public void setSetores(Hashtable<String, Setor> setores) {
		this.setores = setores;
	}
	
	public int compareTo(Curriculo o) {
		return this.nomeCompleto.compareTo(o.getNomeCompleto());
	}
}
