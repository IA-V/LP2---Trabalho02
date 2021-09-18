package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.*;
import control.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private static Hashtable<String, Curriculo> curriculos;
	private static Hashtable<String, Setor> setores;
	private static Hashtable<Integer, Producao> producoes = new Hashtable<Integer, Producao>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					curriculos = GerenciadorArquivos.importarCurriculos();
					setores = GerenciadorArquivos.importarSetores();
					GerenciadorArquivos.importarProducoesPeriodico(producoes);
					GerenciadorArquivos.importarProducoesEvento(producoes);
					GerenciadorArquivos.relacionarSetoresCurriculos(curriculos, setores);
					GerenciadorArquivos.relacionarProducoesPeriodicosEstratos(producoes);
					GerenciadorArquivos.relacionarProducoesCurriculos(producoes, curriculos);
					GerenciadorArquivos.relacionarProducoesEventosEstratos(producoes);
					
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
		setTitle("Gerador de Relat\u00F3rios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[28.00,grow][172.00,grow][71.00,grow]", "[][][][][][][86.00,grow][]"));
		
		JLabel lblIntervalo = new JLabel("Intervalo de An\u00E1lise");
		contentPane.add(lblIntervalo, "cell 0 0 3 1,alignx center");
		
		JLabel lblInicioIntervalo = new JLabel("Ano de In\u00EDcio");
		contentPane.add(lblInicioIntervalo, "cell 0 1,alignx left");
		
		JLabel lblAnoDeTermino = new JLabel("Ano de T\u00E9rmino");
		contentPane.add(lblAnoDeTermino, "cell 0 2,alignx left");
		
		JCheckBox chkRelatorioComum = new JCheckBox("Relat\u00F3rio Comum");
		contentPane.add(chkRelatorioComum, "cell 0 3 3 1");
		
		JButton btnGerar = new JButton("Gerar Relat\u00F3rio(s)");
		
		JCheckBox chkRelatorioTrabalhosSeparados = new JCheckBox("Relat\u00F3rio com Trabalhos Separados");
		contentPane.add(chkRelatorioTrabalhosSeparados, "cell 0 4 3 1");
		
		JCheckBox chkRelatorioSintese = new JCheckBox("Relat\u00F3rio de S\u00EDntese do Quantitativo das Produ\u00E7\u00F5es");
		contentPane.add(chkRelatorioSintese, "cell 0 5 3 1");
		
		JTextArea txtrOsRelatriosSero = new JTextArea();
		txtrOsRelatriosSero.setLineWrap(true);
		txtrOsRelatriosSero.setText("*Os relat\u00F3rios ser\u00E3o gerados na pasta do projeto - no mesmo diret\u00F3rio onde se encontram os arquivos lidos!");
		txtrOsRelatriosSero.setEditable(false);
		contentPane.add(txtrOsRelatriosSero, "cell 0 6 3 1,grow");
		contentPane.add(btnGerar, "cell 2 7,alignx right");
		
		JComboBox cmbInicio = new JComboBox();
		cmbInicio.setModel(new DefaultComboBoxModel(new String[] {"2021", "2020", "2019", "2018", "2017", "2016"}));
		cmbInicio.setSelectedIndex(0);
		contentPane.add(cmbInicio, "cell 1 1 2 1,growx");
		
		JComboBox cmbFim = new JComboBox();
		cmbFim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valorInicio = Integer.parseInt(cmbInicio.getSelectedItem().toString());
				int valorFim = Integer.parseInt(cmbFim.getSelectedItem().toString());
				
				if(valorInicio < valorFim) {
					int indiceValorFim = cmbFim.getSelectedIndex();
					cmbInicio.setSelectedIndex(indiceValorFim);
				}
			}
		});
		cmbFim.setModel(new DefaultComboBoxModel(new String[] {"2021", "2020", "2019", "2018", "2017", "2016"}));
		cmbFim.setSelectedIndex(5);
		contentPane.add(cmbFim, "cell 1 2 2 1,growx");
		
		cmbInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valorInicio = Integer.parseInt(cmbInicio.getSelectedItem().toString());
				int valorFim = Integer.parseInt(cmbFim.getSelectedItem().toString());
				
				if(valorInicio < valorFim) {
					int indiceValorInicio = cmbInicio.getSelectedIndex();
					cmbFim.setSelectedIndex(indiceValorInicio);
				}
			}
		});
	
		
		btnGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!chkRelatorioComum.isSelected() && !chkRelatorioTrabalhosSeparados.isSelected() && !chkRelatorioSintese.isSelected()) {
					JOptionPane.showMessageDialog(null, "Marque pelo menos uma das opções de relatório");
				} else {
					if(chkRelatorioComum.isSelected()) {
						GerenciadorArquivos.gerarRelatorioComum(curriculos, producoes, Integer.parseInt(cmbInicio.getSelectedItem().toString()), Integer.parseInt(cmbFim.getSelectedItem().toString()));
					}
					
					if(chkRelatorioTrabalhosSeparados.isSelected()) {
						GerenciadorArquivos.gerarRelatorioTiposProducoesSeparados(curriculos, producoes, Integer.parseInt(cmbInicio.getSelectedItem().toString()), Integer.parseInt(cmbFim.getSelectedItem().toString()));
					}
					
					if(chkRelatorioSintese.isSelected()) {
						GerenciadorArquivos.gerarRelatorioSintese(curriculos, Integer.parseInt(cmbInicio.getSelectedItem().toString()), Integer.parseInt(cmbFim.getSelectedItem().toString()));
					}
					
					JOptionPane.showMessageDialog(null, "Relatório(s) gerado(s) com sucesso!");
				}
			}
		});
	}

}
