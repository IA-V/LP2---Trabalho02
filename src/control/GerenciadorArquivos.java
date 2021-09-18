package control;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileInputStream;

import model.*;
import jxl.Workbook;
//import jxl.write.DateFormat;
import jxl.write.Number;

import jxl.write.*;

public abstract class GerenciadorArquivos {
	//private static Hashtable<String, Curriculo> curriculos;
	
	private static final String EXCEL_FILE1_LOCATION = "produção_prof.xlsx";
	private static final String EXCEL_FILE2_LOCATION = "produção_prof_trabalhos_separados.xlsx";
	private static final String EXCEL_FILE3_LOCATION = "sintese_prof.xlsx";

    public static void gerarRelatorioComum(Hashtable<String, Curriculo> curriculos, Hashtable<Integer, Producao> producoes, int inicioIntervalo, int fimIntervalo) {
    	int linhaAtual = 1;
        //1. Create an Excel file
        WritableWorkbook relatorio = null;
        try {

        	relatorio = Workbook.createWorkbook(new File(EXCEL_FILE1_LOCATION));

            // create an Excel sheet
            WritableSheet excelSheet = relatorio.createSheet("Sheet 1", 0);

            // add something into the Excel sheet
            Label label = new Label(0, 0, "Professor");
            excelSheet.addCell(label);
            
            label = new Label(1, 0, "Setor");
            excelSheet.addCell(label);
            
            label = new Label(2, 0, "Ano");
            excelSheet.addCell(label);
            
            label = new Label(3, 0, "Título");
            excelSheet.addCell(label);
            
            label = new Label(4, 0, "Local");
            excelSheet.addCell(label);
            
            label = new Label(5, 0, "Avaliação");
            excelSheet.addCell(label);
            
            ArrayList<Map.Entry<Integer, Producao>> arrayProducoes = new ArrayList<Map.Entry<Integer, Producao>>(producoes.entrySet());
            Collections.sort(arrayProducoes, new Comparator<Map.Entry<Integer, Producao>>(){

              public int compare(Map.Entry<Integer, Producao> o1, Map.Entry<Integer, Producao> o2) {
                 return o2.getValue().compareTo(o1.getValue());
             }

			});
            
            ArrayList<Map.Entry<String, Curriculo>> arrayCurriculos = new ArrayList<Map.Entry<String, Curriculo>>(curriculos.entrySet());
            Collections.sort(arrayCurriculos, new Comparator<Map.Entry<String, Curriculo>>(){

              public int compare(Map.Entry<String, Curriculo> o1, Map.Entry<String, Curriculo> o2) {
                 return o1.getValue().compareTo(o2.getValue());
             }});
            
            String professor = "";
            String sigla = "";

            //Ordena, PARA CADA PROF, as producoes pelo ano
            for(Entry<String, Curriculo> entradaCurriculo: arrayCurriculos) {
            	Curriculo curriculo = entradaCurriculo.getValue();
				professor = curriculo.getNomeCompleto();
				String ano = "";
        		String titulo = "";
        		String veiculo = "";
        		String avaliacao = "";
				
				for(Entry<Integer, Producao> entradaProducao: arrayProducoes) {
					Producao producao = entradaProducao.getValue();
					sigla = "";
					
					if(curriculo.getProducoes().contains(producao)) {
						for(String key1: curriculo.getSetores().keySet()) {
							Setor setor = curriculo.getSetores().get(key1);
							sigla += setor.getSigla();
							if(curriculo.getSetores().size() > 1) {
								sigla += ", ";
							}
						}
						ano = producao.getAno();
		        		titulo = producao.getTitulo();
		        		veiculo = producao.getVeiculo();
		        		avaliacao = producao.getAvaliacao();
		        		
		        		if(Integer.parseInt(ano) >= fimIntervalo && Integer.parseInt(ano) <= inicioIntervalo) {
		        			excelSheet.addCell(new Label(0, linhaAtual, professor));
			        		excelSheet.addCell(new Label(1, linhaAtual, sigla));
			        		excelSheet.addCell(new Label(2, linhaAtual, ano));
			        		excelSheet.addCell(new Label(3, linhaAtual, titulo));
			        		excelSheet.addCell(new Label(4, linhaAtual, veiculo));
			        		excelSheet.addCell(new Label(5, linhaAtual, avaliacao));
			        		
			        		linhaAtual++;
		        		}
					}
				}
			}

            relatorio.write();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (relatorio != null) {
                try {
                	relatorio.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    public static void gerarRelatorioTiposProducoesSeparados(Hashtable<String, Curriculo> curriculos, Hashtable<Integer, Producao> producoes, int inicioIntervalo, int fimIntervalo) {
    	int linhaAtual = 1;
        //1. Create an Excel file
        WritableWorkbook relatorio = null;
        try {

        	relatorio = Workbook.createWorkbook(new File(EXCEL_FILE2_LOCATION));

            // create an Excel sheet
            WritableSheet excelSheet = relatorio.createSheet("Sheet 1", 0);

            // add something into the Excel sheet
            Label label = new Label(0, 0, "Professor");
            excelSheet.addCell(label);
            
            label = new Label(1, 0, "Setor");
            excelSheet.addCell(label);
            
            label = new Label(2, 0, "Ano");
            excelSheet.addCell(label);
            
            label = new Label(3, 0, "Artigo Publicado");
            excelSheet.addCell(label);
            
            label = new Label(4, 0, "Local");
            excelSheet.addCell(label);
            
            label = new Label(5, 0, "Avaliação");
            excelSheet.addCell(label);
            
            ArrayList<Map.Entry<Integer, Producao>> arrayProducoes = new ArrayList<Map.Entry<Integer, Producao>>(producoes.entrySet());
            Collections.sort(arrayProducoes, new Comparator<Map.Entry<Integer, Producao>>(){

              public int compare(Map.Entry<Integer, Producao> o1, Map.Entry<Integer, Producao> o2) {
                 return o2.getValue().compareTo(o1.getValue());
             }

			});
            
            ArrayList<Map.Entry<String, Curriculo>> arrayCurriculos = new ArrayList<Map.Entry<String, Curriculo>>(curriculos.entrySet());
            Collections.sort(arrayCurriculos, new Comparator<Map.Entry<String, Curriculo>>(){

              public int compare(Map.Entry<String, Curriculo> o1, Map.Entry<String, Curriculo> o2) {
                 return o1.getValue().compareTo(o2.getValue());
             }});
            
            String professor = "";
            String sigla = "";

            //Ordena, PARA CADA PROF, as producoes pelo ano
            //Artigos em Periodicos
            for(Entry<String, Curriculo> entradaCurriculo: arrayCurriculos) {
            	Curriculo curriculo = entradaCurriculo.getValue();
				professor = curriculo.getNomeCompleto();
				String ano = "";
        		String titulo = "";
        		String veiculo = "";
        		String avaliacao = "";
				
				for(Entry<Integer, Producao> entradaProducao: arrayProducoes) {
					Producao producao = entradaProducao.getValue();
					sigla = "";
					
					if(curriculo.getProducoes().contains(producao) && producao instanceof ArtigoPeriodico) {
						for(String key1: curriculo.getSetores().keySet()) {
							Setor setor = curriculo.getSetores().get(key1);
							sigla += setor.getSigla();
							if(curriculo.getSetores().size() > 1) {
								sigla += ", ";
							}
						}
						ano = producao.getAno();
		        		titulo = producao.getTitulo();
		        		veiculo = producao.getVeiculo();
		        		avaliacao = producao.getAvaliacao();
		        		
		        		if(Integer.parseInt(ano) >= fimIntervalo && Integer.parseInt(ano) <= inicioIntervalo) {
		        			excelSheet.addCell(new Label(0, linhaAtual, professor));
			        		excelSheet.addCell(new Label(1, linhaAtual, sigla));
			        		excelSheet.addCell(new Label(2, linhaAtual, ano));
			        		excelSheet.addCell(new Label(3, linhaAtual, titulo));
			        		excelSheet.addCell(new Label(4, linhaAtual, veiculo));
			        		excelSheet.addCell(new Label(5, linhaAtual, avaliacao));
		        		
			        		linhaAtual++;
		        		}
					}
				}
			}
            
            linhaAtual++;
			label = new Label(0, linhaAtual, "Professor");
            excelSheet.addCell(label);
            
            label = new Label(1, linhaAtual, "Setor");
            excelSheet.addCell(label);
            
            label = new Label(2, linhaAtual, "Ano");
            excelSheet.addCell(label);
            
            label = new Label(3, linhaAtual, "Trabalhos em Anais de Eventos");
            excelSheet.addCell(label);
            
            label = new Label(4, linhaAtual, "Local");
            excelSheet.addCell(label);
            
            label = new Label(5, linhaAtual, "Avaliação");
            excelSheet.addCell(label);
			linhaAtual++;
			
			//Artigos em Eventos
			for(Entry<String, Curriculo> entradaCurriculo: arrayCurriculos) {
				Curriculo curriculo = entradaCurriculo.getValue();
				professor = curriculo.getNomeCompleto();
				String ano = "";
        		String titulo = "";
        		String veiculo = "";
        		String avaliacao = "";
				
    			for(Entry<Integer, Producao> entradaProducao: arrayProducoes) {
    				Producao producao = entradaProducao.getValue();
    				sigla = "";
    				
    				if(curriculo.getProducoes().contains(producao) && producao instanceof ArtigoEvento) {
    					for(String key1: curriculo.getSetores().keySet()) {
    						Setor setor = curriculo.getSetores().get(key1);
    						sigla += setor.getSigla();
    						if(curriculo.getSetores().size() > 1) {
    							sigla += ", ";
    						}
    					}
    					ano = producao.getAno();
    	        		titulo = producao.getTitulo();
    	        		veiculo = producao.getVeiculo();
    	        		avaliacao = producao.getAvaliacao();
    	        		
    	        		if(Integer.parseInt(ano) >= fimIntervalo && Integer.parseInt(ano) <= inicioIntervalo) {
    	        			excelSheet.addCell(new Label(0, linhaAtual, professor));
        	        		excelSheet.addCell(new Label(1, linhaAtual, sigla));
        	        		excelSheet.addCell(new Label(2, linhaAtual, ano));
        	        		excelSheet.addCell(new Label(3, linhaAtual, titulo));
        	        		excelSheet.addCell(new Label(4, linhaAtual, veiculo));
        	        		excelSheet.addCell(new Label(5, linhaAtual, avaliacao));
    	        		
        	        		linhaAtual++;
    	        		}
    				}
    			}
			}

            relatorio.write();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (relatorio != null) {
                try {
                	relatorio.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void gerarRelatorioSintese(Hashtable<String, Curriculo> curriculos, int inicioIntervalo, int fimIntervalo) {
    	int linhaAtual = 1;
        //1. Create an Excel file
        WritableWorkbook relatorio = null;
        try {

        	relatorio = Workbook.createWorkbook(new File(EXCEL_FILE3_LOCATION));
        	
            // create an Excel sheet
            WritableSheet excelSheet = relatorio.createSheet("Sheet 1", 0);

            // add something into the Excel sheet
            Label label = new Label(0, 0, "Professor");
            excelSheet.addCell(label);
            
            label = new Label(1, 0, "A1");
            excelSheet.addCell(label);
            
            label = new Label(2, 0, "A2");
            excelSheet.addCell(label);
            
            label = new Label(3, 0, "A3");
            excelSheet.addCell(label);
            
            label = new Label(4, 0, "A4");
            excelSheet.addCell(label);
            
            label = new Label(5, 0, "B1");
            excelSheet.addCell(label);
            
            label = new Label(6, 0, "B2");
            excelSheet.addCell(label);
            
            label = new Label(7, 0, "B3");
            excelSheet.addCell(label);
            
            label = new Label(8, 0, "B4");
            excelSheet.addCell(label);
            
            label = new Label(9, 0, "SQ");
            excelSheet.addCell(label);
            
            //A1 -> 0, A2 -> 1, A3 -> 2, ..., SQ -> 8
            int[] qtdAvaliacoes = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            
            ArrayList<Map.Entry<String, Curriculo>> arrayCurriculos = new ArrayList(curriculos.entrySet());
            Collections.sort(arrayCurriculos, new Comparator<Map.Entry<String, Curriculo>>(){

              public int compare(Map.Entry<String, Curriculo> o1, Map.Entry<String, Curriculo> o2) {
                 return o1.getValue().compareTo(o2.getValue());
             }});
            
            for(Entry<String, Curriculo> entradaCurriculo: arrayCurriculos) {
            	Curriculo curriculo = entradaCurriculo.getValue();
				String professor = curriculo.getNomeCompleto();
				
				for(int key1: curriculo.getProducoes().keySet()) {
					
					Producao producao = curriculo.getProducoes().get(key1);
					if(Integer.parseInt(producao.getAno()) >= fimIntervalo && Integer.parseInt(producao.getAno()) <= inicioIntervalo) {
						try {
							switch(producao.getAvaliacao()) {
								case "A1":
									qtdAvaliacoes[0]++;
									break;
								case "A2":
									qtdAvaliacoes[1]++;
									break;
								case "A3":
									qtdAvaliacoes[2]++;
									break;
								case "A4":
									qtdAvaliacoes[3]++;
									break;
								case "B1":
									qtdAvaliacoes[4]++;
									break;
								case "B2":
									qtdAvaliacoes[5]++;
									break;
								case "B3":
									qtdAvaliacoes[6]++;
									break;
								case "B4":
									qtdAvaliacoes[7]++;
									break;
								case "SQ":
									qtdAvaliacoes[8]++;
									break;
								default:
									break;
							}
						} catch(NullPointerException e) {
							System.out.println(e.getMessage());
						}
					}
				}
				excelSheet.addCell(new Label(0, linhaAtual, professor));
        		excelSheet.addCell(new Label(1, linhaAtual, Integer.toString(qtdAvaliacoes[0])));
        		qtdAvaliacoes[0] = 0;
        		excelSheet.addCell(new Label(2, linhaAtual, Integer.toString(qtdAvaliacoes[1])));
        		qtdAvaliacoes[1] = 0;
        		excelSheet.addCell(new Label(3, linhaAtual, Integer.toString(qtdAvaliacoes[2])));
        		qtdAvaliacoes[2] = 0;
        		excelSheet.addCell(new Label(4, linhaAtual, Integer.toString(qtdAvaliacoes[3])));
        		qtdAvaliacoes[3] = 0;
        		excelSheet.addCell(new Label(5, linhaAtual, Integer.toString(qtdAvaliacoes[4])));
        		qtdAvaliacoes[4] = 0;
        		excelSheet.addCell(new Label(6, linhaAtual, Integer.toString(qtdAvaliacoes[5])));
        		qtdAvaliacoes[5] = 0;
        		excelSheet.addCell(new Label(7, linhaAtual, Integer.toString(qtdAvaliacoes[6])));
        		qtdAvaliacoes[6] = 0;
        		excelSheet.addCell(new Label(8, linhaAtual, Integer.toString(qtdAvaliacoes[7])));
        		qtdAvaliacoes[7] = 0;
        		excelSheet.addCell(new Label(9, linhaAtual, Integer.toString(qtdAvaliacoes[8])));
        		qtdAvaliacoes[8] = 0;
        		
        		linhaAtual++;
			}

            relatorio.write();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (relatorio != null) {
                try {
                	relatorio.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void relacionarProducoesCurriculos(Hashtable<Integer, Producao> producoes, Hashtable<String, Curriculo> curriculos) throws IOException{		
    	for(int key: producoes.keySet()) {
    		Producao producao = producoes.get(key);
    		Curriculo curriculo = curriculos.get(producao.getIdCurriculo());
    		curriculo.adicionarProducao(producao, key);
    	}
	}
    
    public static void relacionarProducoesEventosEstratos(Hashtable<Integer, Producao> producoes) throws IOException{
		BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("estrato_artigo_em_evento.csv"), "UTF-8"));
		String avaliacao;
		
		String linha1 = leitor.readLine();
		while((linha1 = leitor.readLine()) != null) {
			String[] tmp = linha1.split(";");
			avaliacao = tmp[2];
			
			for(int key : producoes.keySet()) {
				if(producoes.get(key) instanceof ArtigoEvento) {
					ArtigoEvento producao = (ArtigoEvento)producoes.get(key);
					
					String siglaEvento = tmp[0];
					String nomeEvento = tmp[1];
					String prodNomeEvento = producao.getVeiculo();
					String prodTituloProceedings = producao.getTituloProceedings();
					
					String novoSiglaEvento = siglaEvento.replaceAll("[()]", "");
					String novoProdNomeEvento = prodNomeEvento.replaceAll("[()]", "");
					String novoProdTituloProceedings = prodTituloProceedings.replaceAll("[()]", "");
					StringTokenizer st1 = new StringTokenizer(novoProdNomeEvento);
					StringTokenizer st2 = new StringTokenizer(novoProdTituloProceedings);
					
					while (st1.hasMoreTokens()) {  
						if(st1.nextToken().equals(novoSiglaEvento)) {
							producao.setAvaliacao(avaliacao);
							break;
						}
				    }
					while (st2.hasMoreTokens()) {  
						if(st2.nextToken().equals(novoSiglaEvento)) {
							producao.setAvaliacao(avaliacao);
							break;
						}
				    }
				}
				
				
				/*if( tmp[0].equals(producao.getIssn()) ) {
					producoes.get(key).setAvaliacao(avaliacao);
				}*/
			}
		}
		
		leitor.close();
	}
	
	public static void relacionarProducoesPeriodicosEstratos(Hashtable<Integer, Producao> producoes) throws IOException{
		BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("estrato_artigo_periodico.csv"), "UTF-8"));
		String avaliacao;
		
		String linha1 = leitor.readLine();
		while((linha1 = leitor.readLine()) != null) {
			String[] tmp = linha1.split(";");
			avaliacao = tmp[2];
			
			for(int key : producoes.keySet()) {
				if(producoes.get(key) instanceof ArtigoPeriodico) {
					ArtigoPeriodico producao = (ArtigoPeriodico)producoes.get(key);
					if( tmp[0].equals(producao.getIssn()) ) {
						producao.setAvaliacao(avaliacao);
					}
				}
			}
		}
		
		leitor.close();
	}
	
	public static void relacionarSetoresCurriculos(Hashtable<String, Curriculo> curriculos, Hashtable<String, Setor> setores) throws IOException{
		BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("curriculo_setor.csv"), "UTF-8"));
		
		String linha = leitor.readLine();
		while((linha = leitor.readLine()) != null) {
			String[] tmp = linha.split(";");
			
			for(String key : curriculos.keySet()) {
				if( tmp[0].equals(curriculos.get(key).getId()) ) {
					curriculos.get(key).adicionarSetor(setores.get(tmp[1]), tmp[1]);
				}
			}
			
		}
		leitor.close();
		
	}
	
	public static void importarProducoesEvento(Hashtable<Integer, Producao> producoes) throws IOException{
		BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("artigos_em_evento.csv"), "UTF-8"));
		//Hashtable<Integer, ArtigoEvento> producoes = new Hashtable<Integer, ArtigoEvento>();
		String idCurriculo;
		String tituloProducao;
		String tituloProceedings;
		String veiculo;
		String ano;
		//String sigla
		
		String linha = leitor.readLine();
		while((linha = leitor.readLine()) != null) {
			String[] tmp = linha.split(";");
			idCurriculo = tmp[0];
			tituloProducao = tmp[1];
			veiculo = tmp[2];
			tituloProceedings = tmp[3];
			ano = tmp[4];
			ArtigoEvento producao = new ArtigoEvento(idCurriculo, tituloProducao, veiculo, ano, tituloProceedings);
			producoes.put(producao.hashCode(), producao);
		}
		leitor.close();
	}
	
	public static void importarProducoesPeriodico(Hashtable<Integer, Producao> producoes) throws IOException{
		BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("artigos_em_periodico.csv"), "UTF-8"));
		//Hashtable<Integer, ArtigoPeriodico> producoes = new Hashtable<Integer, ArtigoPeriodico>();
		String idCurriculo;
		String tituloProducao;
		String issn;
		String veiculo;
		String ano;
		
		String linha = leitor.readLine();
		while((linha = leitor.readLine()) != null) {
			String[] tmp = linha.split(";");
			idCurriculo = tmp[0];
			tituloProducao = tmp[1];
			issn = tmp[2];
			veiculo = tmp[3];
			ano = tmp[4];
			ArtigoPeriodico producao = new ArtigoPeriodico(idCurriculo, tituloProducao, veiculo, ano, issn);
			producoes.put(producao.hashCode(), producao);
		}
		leitor.close();
	}
	
	public static Hashtable<String, Setor> importarSetores() throws IOException{
		BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("setor.csv"), "UTF-8"));
		Hashtable<String, Setor> setores = new Hashtable<String, Setor>();
		
		String linha = leitor.readLine();
		while((linha = leitor.readLine()) != null) {
			String[] tmp = linha.split(";");
			setores.put(tmp[0], new Setor(tmp[0], tmp[1], tmp[2]));
		}
		leitor.close();
		
		return setores;
	}
	
	public static Hashtable<String, Curriculo> importarCurriculos() throws IOException{
		BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("curriculo.csv"), "UTF-8"));
		Hashtable<String, Curriculo> curriculos = new Hashtable<String, Curriculo>();
		
		String linha = leitor.readLine();
		while((linha = leitor.readLine()) != null) {
			String[] tmp = linha.split(";");
			Curriculo curriculo = new Curriculo(tmp[0], tmp[1]);
			curriculos.put(tmp[0], curriculo);
		}
		leitor.close();
		
		return curriculos;
	}
	
	/*public static void main (String []args) {
		try {
			Hashtable<String, Curriculo> curriculos = importarCurriculos();
			Hashtable<String, Setor> setores = importarSetores();
			Hashtable<Integer, Producao> producoes = new Hashtable<Integer, Producao>();
			importarProducoesPeriodico(producoes);
			importarProducoesEvento(producoes);
			relacionarSetoresCurriculos(curriculos, setores);
			relacionarProducoesPeriodicosEstratos(producoes);
			relacionarProducoesPeriodicosCurriculos(producoes, curriculos);
			relacionarProducoesEventosCurriculos(producoes, curriculos);
			relacionarProducoesEventosEstratos(producoes);
			System.out.println(producoes.size());
			int count = 1;
			/*for(int key : producoes.keySet()) {
				Producao producao = producoes.get(key);
				System.out.println(count + " - " + curriculos.get(producao.getIdCurriculo()).getNomeCompleto() + " - Producao: " + producao.getTitulo());
				System.out.println("\n");
				count++;
			}
			
			for(String key: curriculos.keySet()) {
				Curriculo curriculo = curriculos.get(key);
				for(int key1: curriculo.getProducoes().keySet()) {
					Producao producao = curriculo.getProducoes().get(key1);
					System.out.println(count + " - " + curriculo.getNomeCompleto() + " - Producao: " + producao.getTitulo());
					count++;
				}
			}
			
			/*for(int key: producoesEvento.keySet()) {
				ArtigoEvento prodEvento = producoesEvento.get(key);
				for(int key1: curriculo.getProducoes().keySet()) {
					Producao producao = curriculo.getProducoes().get(key1);
					System.out.println(count + " - " + curriculo.getNomeCompleto() + " - Producao: " + producao.getTitulo());
					count++;
				}
			}
			
			//gerarRelatorioComum(curriculos, producoes);
			gerarRelatorioTiposProducoesSeparados(curriculos, producoes);
			//gerarRelatorioSintese(curriculos);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}*/
}
