package model;

public class ArtigoPeriodico extends Producao{
	private String issn;

	public ArtigoPeriodico(String idCurriculo, String titulo, String veiculo, String ano, String issn) {
		super(idCurriculo, titulo, veiculo, ano);
		this.issn = issn;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}
	
}
