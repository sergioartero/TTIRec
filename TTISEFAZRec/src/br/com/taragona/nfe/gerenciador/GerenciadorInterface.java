package br.com.taragona.nfe.gerenciador;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import nfeimpressao.TaragonaInt;
import br.com.taragona.nfe.controle.ControleInterface;
import br.com.taragona.nfe.erpinterface.InterfaceERP;
import br.com.taragona.nfe.mensageiro.MensageiroRemote;
import br.com.taragona.nfe.nfecockpitdesktop.data.Perfil;
import br.com.taragona.nfe.nfecockpitdesktop.data.Usuario;
import br.com.taragona.nfe.nfecockpitdesktop.gerenciadorlistener.GerenciadorListener;
import br.com.taragona.nfe.persistence.CodigoNFe;
import br.com.taragona.nfe.persistence.EstadoLote;
import br.com.taragona.nfe.persistence.EstadoNFe;
import br.com.taragona.nfe.persistence.EstadoRecibo;
import br.com.taragona.nfe.persistence.LogNFe; // import br.com.taragona.nfe.persistence.Notificacao;
import br.com.taragona.nfe.sender.SenderRemote;
import br.com.taragona.nfe.util.data.BaseDadosConfig;
import br.com.taragona.nfe.util.data.MensageiroConfig;
import br.com.taragona.nfe.util.data.SenderConf;
import br.com.taragona.nfe.util.data.ServicosConfig;

public interface GerenciadorInterface extends Remote {
	public MensageiroRemote obterMensageiro(String id) throws RemoteException;

	public ControleInterface obterControlador(String id) throws RemoteException;

	public String consultarNota(String idNota) throws RemoteException;

	public String consultarNotaXml(String idNota) throws RemoteException;

	public void enviarNota(String CNPJEmitente, String CNPJDestino,
			String idNota, String nota, Date data) throws RemoteException;

	public void enviarNotaErro(String cnpj, String idNota, String xml,
			String erro) throws RemoteException;

	public void cancelarNota(String cnpj, String idNota, String justificativa)
			throws RemoteException;

	public String inutilizarNota(String UF, String ano, String cnpj,
			String mod, String serie, String ini, String fim, String just)
			throws RemoteException;

	public void inutilizarNumero(String idNota, String justificativa)
			throws RemoteException;

	public Vector<String> obterMensageiros() throws RemoteException;

	public Vector<String> obterControles() throws RemoteException;

	public void mudarEstadoContingencia(String cnpj) throws RemoteException;

	public void mudarEstadoNormalEnvioContingencia(String cnpj)
			throws RemoteException;

	public void mudarEstadoNormalSemEnvio(String cnpj) throws RemoteException;

	public Hashtable[] obterTodosServicos() throws RemoteException;

	public void ajustarTodosServicos(Hashtable[] servico)
			throws RemoteException;

	public int estadoOperacao(String cnpj) throws RemoteException;

	public Vector<String> obterCNPJRegistrados() throws RemoteException;

	public MensageiroConfig obterMensageiroConfig(String cnpj)
			throws RemoteException;

	public void ajustarMensageiroConfig(String cnpj, MensageiroConfig conf)
			throws RemoteException;

	// registro

	public void registrarMensageiro(String id, MensageiroRemote mensageiro)
			throws RemoteException;

	public void registrarControle(String id, ControleInterface controle)
			throws RemoteException;

	public void registrarListener(GerenciadorListener listener)
			throws RemoteException;

	public void registrarImpressao(TaragonaInt imp) throws RemoteException;

	public void registrarSender(String id, SenderRemote sender)
			throws RemoteException;

	public void registrarInterface(String id, InterfaceERP retorno)
			throws RemoteException;

	// impressao
	public void imprimir(String chaveNota, String estado, String xml,
			Hashtable<String, String> prop) throws RemoteException;

	public void persistPrintKey(String id, String key) throws RemoteException;

	// assinador
	public String assinar(String cnpj, String xml, String tag)
			throws RemoteException;

	// listener
	public void atualizarNota(EstadoNFe nota) throws RemoteException;

	public void atualizarLote(EstadoLote lote) throws RemoteException;

	// sender
	public String enviarMensagemXML(String idServico, String cnpj,
			String cabecalho, String xml, Hashtable prop)
			throws RemoteException;

	// config servicos

	public ServicosConfig obterServicosConfig() throws RemoteException;

	public void ajustarServicosConfig(ServicosConfig conf)
			throws RemoteException;

	// notificacao
	public void tentarEnvioNotas(String cnpj) throws RemoteException;

	public void notificarNotasCancelamentoERP(String[] idNotas)
			throws RemoteException;

	public void notificarNotaERP(String chave, String modo, String protocolo)
			throws RemoteException;

	public void notificarProtocoloCancelamentoNormal(String chaveNota,
			String protocolo) throws RemoteException;

	public void notificarProtocoloCancelamentoContingecia(String chaveNota,
			String protocolo) throws RemoteException;

	public void notificarProtocoloInutilizcaoNormal(String chaveNota,
			String protocolo) throws RemoteException;

	public void notificarProtocoloInutilizcaoContingecia(String chaveNota,
			String protocolo) throws RemoteException;

	public void retornoExcecao(String chaveNFe, String metodoAraujoInt,
			String mensagemExcecao) throws RemoteException;

	public void reImprimirNota(String idNota) throws RemoteException;

	public void registrarUsuario(Usuario usuario, Perfil perfil)
			throws RemoteException;

	public void removerUsuario(Usuario usuario) throws RemoteException;

	public Usuario obterUsuario(String login, String senha)
			throws RemoteException;

	public boolean login(String login, String senha) throws RemoteException;

	public List<LogNFe> obterHistorico(String idNota) throws RemoteException;

	public Vector<EstadoNFe> obterNotas(String idLote) throws RemoteException;

	public Vector<EstadoNFe> obterNotas(String CNPJDestinatario, String serie,
			String n1, String n2, Date d1, Date d2) throws RemoteException;

	public Vector<EstadoNFe> obterNotas(Vector<String> cnpjs, String serie,
			String n1, String n2, Date d1, Date d2) throws RemoteException;

	public EstadoRecibo obterRecibo(String idLote) throws RemoteException;

	public String obterXml(String idNota) throws RemoteException;

	public EstadoLote obterLote(String idNota) throws RemoteException;

	public Vector obterNotas(Date d1, Date d2) throws RemoteException;

	public Vector<EstadoLote> obterLotes(Date d1) throws RemoteException;

	public CodigoNFe obterCodigo(int codigo) throws RemoteException;

	public void ajustarSenhas(Hashtable<String, String> senhas)
			throws RemoteException;

	public boolean checarSenha(String arquivo, String senha)
			throws RemoteException;

	public void notificarControle(String cnpj, String idLote, String recibo,
			Set<String> notas) throws RemoteException;

	public void trocarSenha(String login, String senha) throws RemoteException;

	public Hashtable<String, String> obterNomesFantasia()
			throws RemoteException;

	public void ajustarTipoServico(String cnpj, String tipo)
			throws RemoteException;

	public Hashtable<String, String> obterTipoServicos() throws RemoteException;

	public String obterTipoServico(String cnpj) throws RemoteException;

	public BaseDadosConfig obterBDConfig() throws RemoteException;

	public SenderConf obterSenderConfig() throws RemoteException;

	public void todoEmContingencia() throws RemoteException;

	// dpec
	public void criarDpec(String cnpj) throws RemoteException;

	public void enviarNotasDpec(String cnpj) throws RemoteException;

	public void mudarEstadoDpec(String cnpj) throws RemoteException;

	public void mudarEstadoScan(String cnpj) throws RemoteException;

	public void cancelarNota(String cnpj, String mod, String serie,
			String numero, String just) throws RemoteException;

	public void atualizarInut(String serie, String cnpj, String numero,
			String codigo, String something, String modo)
			throws RemoteException;

	public void changeOp(String cnpj, String tpEmis, String tpAmb)
			throws RemoteException;

	public void findProtocol(String cnpj, String serie, String numero)
			throws RemoteException;

	public Vector<String> findPrinters() throws RemoteException;

	public Hashtable<String, String> obterImpressorasPermitidas()
			throws RemoteException;

	public void setPrinter(String codigo, String printer)
			throws RemoteException;

	public Hashtable<String, String> obterImpressorasDefault()
			throws RemoteException;

	public Vector<EstadoNFe> findNota(String cnpj, String serie, String ini,
			String fim, Date data) throws RemoteException;

	public void enviarEmailNota(String id) throws RemoteException;

	public void addBoletoXML(String id, String xml) throws RemoteException;

	public byte[] downloadPDF(String pdf) throws RemoteException;

	public void notificarArquivoNaoTratado(String nomeArquivo, String motivo,
			String chave) throws RemoteException;

	public Vector<String> obterEmails(String cnpj) throws RemoteException;

	public void checarConsistencia(Vector<String> chaves)
			throws RemoteException;

}
