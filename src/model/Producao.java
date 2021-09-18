package model;

public abstract class Producao {
	private String idCurriculo;
	private String titulo;
	private String veiculo;
	private String avaliacao;
	private String ano;	

	public Producao(String idCurriculo, String titulo, String veiculo, String ano) {
		this.idCurriculo = idCurriculo;
		this.titulo = titulo;
		this.veiculo = veiculo;
		this.ano = ano;
		this.avaliacao = "-";
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getIdCurriculo() {
		return idCurriculo;
	}

	public void setIdCurriculo(String idCurriculo) {
		this.idCurriculo = idCurriculo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	public int compareTo(Producao o) {
		return this.ano.compareTo(o.getAno());
	}

}
