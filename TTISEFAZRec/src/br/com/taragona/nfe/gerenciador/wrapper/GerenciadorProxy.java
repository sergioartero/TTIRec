package br.com.taragona.nfe.gerenciador.wrapper;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import nfeimpressao.TaragonaInt;
import br.com.taragona.nfe.controle.ControleInterface;
import br.com.taragona.nfe.erpinterface.InterfaceERP;
import br.com.taragona.nfe.gerenciador.GerenciadorInterface;
import br.com.taragona.nfe.mensageiro.MensageiroRemote;
import br.com.taragona.nfe.nfecockpitdesktop.data.Perfil;
import br.com.taragona.nfe.nfecockpitdesktop.data.Usuario;
import br.com.taragona.nfe.nfecockpitdesktop.gerenciadorlistener.GerenciadorListener;
import br.com.taragona.nfe.persistence.CodigoNFe;
import br.com.taragona.nfe.persistence.EstadoLote;
import br.com.taragona.nfe.persistence.EstadoNFe;
import br.com.taragona.nfe.persistence.EstadoRecibo;
import br.com.taragona.nfe.persistence.LogNFe;
import br.com.taragona.nfe.sender.SenderRemote;
import br.com.taragona.nfe.util.data.BaseDadosConfig;
import br.com.taragona.nfe.util.data.MensageiroConfig;
import br.com.taragona.nfe.util.data.SenderConf;
import br.com.taragona.nfe.util.data.ServicosConfig;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.util.Locator;

public class GerenciadorProxy implements GerenciadorInterface {

	private static String GERENCIADOR_NAME = "gerenciador";

	private ManagerInterface manager;

	public GerenciadorProxy() {
		this.manager = Locator.getManagerReference();
	}

	public void registerProxy() {
		try {
			Registry reg = LocateRegistry.getRegistry();
			Remote stub = UnicastRemoteObject.exportObject(this, 0);
			reg.rebind(GERENCIADOR_NAME, stub);
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void registrarImpressao(TaragonaInt imp) throws RemoteException {
		this.manager.registrarImpressao(imp);
	}

	public void initGerenciadorWrapper() {
		this.registerProxy();
	}

	@Override
	public void addBoletoXML(String id, String xml) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajustarMensageiroConfig(String cnpj, MensageiroConfig conf)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajustarSenhas(Hashtable<String, String> senhas)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajustarServicosConfig(ServicosConfig conf)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajustarTipoServico(String cnpj, String tipo)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajustarTodosServicos(Hashtable[] servico)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String assinar(String cnpj, String xml, String tag)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarInut(String serie, String cnpj, String numero,
			String codigo, String something, String modo)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizarLote(EstadoLote lote) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizarNota(EstadoNFe nota) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelarNota(String cnpj, String idNota, String justificativa)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelarNota(String cnpj, String mod, String serie,
			String numero, String just) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeOp(String cnpj, String tpEmis, String tpAmb)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checarConsistencia(Vector<String> chaves)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checarSenha(String arquivo, String senha)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String consultarNota(String idNota) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String consultarNotaXml(String idNota) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void criarDpec(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] downloadPDF(String pdf) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enviarEmailNota(String id) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String enviarMensagemXML(String idServico, String cnpj,
			String cabecalho, String xml, Hashtable prop)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enviarNota(String CNPJEmitente, String CNPJDestino,
			String idNota, String nota, Date data) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enviarNotaErro(String cnpj, String idNota, String xml,
			String erro) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enviarNotasDpec(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int estadoOperacao(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector<EstadoNFe> findNota(String cnpj, String serie, String ini,
			String fim, Date data) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<String> findPrinters() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void findProtocol(String cnpj, String serie, String numero)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void imprimir(String chaveNota, String estado, String xml,
			Hashtable<String, String> prop) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String inutilizarNota(String UF, String ano, String cnpj,
			String mod, String serie, String ini, String fim, String just)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inutilizarNumero(String idNota, String justificativa)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean login(String login, String senha) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mudarEstadoContingencia(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mudarEstadoDpec(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mudarEstadoNormalEnvioContingencia(String cnpj)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mudarEstadoNormalSemEnvio(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mudarEstadoScan(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarArquivoNaoTratado(String nomeArquivo, String motivo,
			String chave) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarControle(String cnpj, String idLote, String recibo,
			Set<String> notas) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarNotaERP(String chave, String modo, String protocolo)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarNotasCancelamentoERP(String[] idNotas)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarProtocoloCancelamentoContingecia(String chaveNota,
			String protocolo) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarProtocoloCancelamentoNormal(String chaveNota,
			String protocolo) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarProtocoloInutilizcaoContingecia(String chaveNota,
			String protocolo) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarProtocoloInutilizcaoNormal(String chaveNota,
			String protocolo) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BaseDadosConfig obterBDConfig() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<String> obterCNPJRegistrados() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CodigoNFe obterCodigo(int codigo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ControleInterface obterControlador(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<String> obterControles() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<String> obterEmails(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogNFe> obterHistorico(String idNota) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, String> obterImpressorasDefault()
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, String> obterImpressorasPermitidas()
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EstadoLote obterLote(String idNota) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<EstadoLote> obterLotes(Date d1) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MensageiroRemote obterMensageiro(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MensageiroConfig obterMensageiroConfig(String cnpj)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<String> obterMensageiros() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, String> obterNomesFantasia()
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<EstadoNFe> obterNotas(String idLote) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<EstadoNFe> obterNotas(String CNPJDestinatario, String serie,
			String n1, String n2, Date d1, Date d2) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<EstadoNFe> obterNotas(Vector<String> cnpjs, String serie,
			String n1, String n2, Date d1, Date d2) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector obterNotas(Date d1, Date d2) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EstadoRecibo obterRecibo(String idLote) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SenderConf obterSenderConfig() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServicosConfig obterServicosConfig() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obterTipoServico(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, String> obterTipoServicos() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable[] obterTodosServicos() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario obterUsuario(String login, String senha)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obterXml(String idNota) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persistPrintKey(String id, String key) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reImprimirNota(String idNota) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarControle(String id, ControleInterface controle)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarInterface(String id, InterfaceERP retorno)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarListener(GerenciadorListener listener)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarMensageiro(String id, MensageiroRemote mensageiro)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarSender(String id, SenderRemote sender)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarUsuario(Usuario usuario, Perfil perfil)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerUsuario(Usuario usuario) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retornoExcecao(String chaveNFe, String metodoAraujoInt,
			String mensagemExcecao) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPrinter(String codigo, String printer)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tentarEnvioNotas(String cnpj) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void todoEmContingencia() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trocarSenha(String login, String senha) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
