package model;

public class ArtigoEvento extends Producao{
	private String tituloProceedings;
	
	public ArtigoEvento(String idCurriculo, String titulo, String veiculo, String ano, String tituloProceedings) {
		super(idCurriculo, titulo, veiculo, ano);
		this.tituloProceedings = tituloProceedings;
	}
	
	public String getTituloProceedings() {
		return tituloProceedings;
	}

	public void setTituloProceedings(String tituloProceedings) {
		this.tituloProceedings = tituloProceedings;
	}

}
