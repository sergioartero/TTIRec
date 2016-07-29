/*
 * and open the template in the editor.
 * To change this template, choose Tools | Templates
 */

package br.com.tti.sefaz.signer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.com.taragona.nfe.util.xml.XMLFactory;
import br.com.tti.sefaz.xmlgenerate.cte.XMLMessageFactoryCTe;

/**
 * 
 * @author taragona
 */
public class Main implements AssinadorInt {

	public Main() {
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	protected Certificate certificate = null;
	protected PrivateKey privateKey = null;

	public static void main(String[] args) {
		// TODO code application logic here
		HashMap parametros = null;
		Opcao[] permitidos = {
				new Opcao("-h", false, false, true, "-h: mostra esta descricao"),
				new Opcao("-c", true, true, false,
						"-c certificacao: arquivo pfx de certificacao"),
		// new Opcao("-x", true, true, false, "-x xml: arquivo xml da nota"),
		};
		String certificado;

		PegaOpcoes pegaOpcoes = new PegaOpcoes(permitidos);
		try {
			parametros = pegaOpcoes.opcoes(args);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}
		String help = (String) parametros.get("-h");
		if (help != null) {
			System.out.print(pegaOpcoes.help());
			System.exit(0);
		}

		certificado = (String) parametros.get("-c");

		String senha = null;
		Console cons;
		char[] passwd;
		cons = System.console();
		do {
			passwd = cons.readPassword("Informe Senha do Certificado:");
		} while (passwd == null);
		senha = new String(passwd);

		Main obj;

		obj = new Main(certificado, senha);

		AssinadorInt stub = null;
		try {
			stub = (AssinadorInt) UnicastRemoteObject.exportObject(obj, 0);
		} catch (RemoteException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(70);
		}
		Registry registry = null;
		try {
			registry = LocateRegistry.getRegistry(obj.getConfigIpLocal());
		} catch (RemoteException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(72);
		}
		try {
			registry.rebind("AssinadorInt", stub);
		} catch (RemoteException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(71);
		}
		System.out.println("Pronto para assinar...");
	}

	public Main(String senha) {

		char[] pin = senha.toCharArray();

		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance("PKCS11");
		} catch (KeyStoreException ex) {
			System.out.println("Nao conseguiu criar keystore com pkcs12");
			System.exit(1);
		}

		try {
			ks.load(null, pin);
		} catch (IOException ex) {
			System.out.println("Senha digitada invalida");
			System.exit(2);
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Algoritmo do certificado invalido");
			System.exit(3);
		} catch (CertificateException ex) {
			System.out.println("Certificado ou senha invalidos");
			System.exit(4);
		}
		Enumeration<String> aliases = null;
		try {
			aliases = ks.aliases();
		} catch (KeyStoreException ex) {
			System.out.println("Nenhuma entrada no keystore");
			System.exit(5);
		}
		String alias = null;
		KeyStore.PrivateKeyEntry entry = null;
		try {
			while (aliases.hasMoreElements()) {
				String alias1 = (String) aliases.nextElement();
				if (ks.isKeyEntry(alias1)) {
					entry = (KeyStore.PrivateKeyEntry) ks
							.getEntry(alias1, new KeyStore.PasswordProtection(
									senha.toCharArray()));
					privateKey = entry.getPrivateKey();
					certificate = entry.getCertificate();
					break;
				}
			}
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnrecoverableEntryException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (KeyStoreException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Main(String certificado, String senha) {
		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance("pkcs12");
		} catch (KeyStoreException ex) {
			System.out.println("Nao conseguiu criar keystore com pkcs12");
			System.exit(1);
		}

		try {
			new FileInputStream(certificado);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nao encontrado: " + certificado);
			System.exit(222);
		}

		try {
			ks.load(new FileInputStream(certificado), senha.toCharArray());
		} catch (IOException ex) {
			System.out.println("Senha digitada invalida");
			System.exit(2);
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Algoritmo do certificado invalido");
			System.exit(3);
		} catch (CertificateException ex) {
			System.out.println("Certificado ou senha invalidos");
			System.exit(4);
		}
		Enumeration<String> aliases = null;
		try {
			aliases = ks.aliases();
		} catch (KeyStoreException ex) {
			System.out.println("Nenhuma entrada no keystore");
			System.exit(5);
		}
		String alias = null;
		KeyStore.PrivateKeyEntry entry = null;
		try {
			while (aliases.hasMoreElements()) {
				String alias1 = (String) aliases.nextElement();
				if (ks.isKeyEntry(alias1)) {
					entry = (KeyStore.PrivateKeyEntry) ks
							.getEntry(alias1, new KeyStore.PasswordProtection(
									senha.toCharArray()));
					privateKey = entry.getPrivateKey();
					certificate = entry.getCertificate();
					break;
				}
			}
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnrecoverableEntryException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (KeyStoreException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String myAssinaCanc(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(xml.getBytes()));

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList transformList = new ArrayList();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,
				tps);
		Transform c14NTransform = fac.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();

		x509Content.add(certificate);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

		try {

			NodeList elements = doc.getElementsByTagName("infCanc");
			Element el = (Element) elements.item(0);
			String id = el.getAttribute("Id");

			Reference ref = fac.newReference("#" + id,
					fac.newDigestMethod(DigestMethod.SHA1, null),
					transformList, null, null);

			// Create the SignedInfo.
			SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
					CanonicalizationMethod.INCLUSIVE,
					(C14NMethodParameterSpec) null), fac.newSignatureMethod(
					SignatureMethod.RSA_SHA1, null), Collections
					.singletonList(ref));

			XMLSignature signature = fac.newXMLSignature(si, ki);
			DOMSignContext dsc = new DOMSignContext(privateKey,
					doc.getDocumentElement());
			signature.sign(dsc);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		return os.toString();

	}

	public String myAssinaDpec(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(xml.getBytes()));

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList transformList = new ArrayList();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,
				tps);
		Transform c14NTransform = fac.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();

		x509Content.add(certificate);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

		try {

			NodeList elements = doc.getElementsByTagName("infDPEC");
			Element el = (Element) elements.item(0);
			String id = el.getAttribute("Id");

			Reference ref = fac.newReference("#" + id,
					fac.newDigestMethod(DigestMethod.SHA1, null),
					transformList, null, null);

			// Create the SignedInfo.
			SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
					CanonicalizationMethod.INCLUSIVE,
					(C14NMethodParameterSpec) null), fac.newSignatureMethod(
					SignatureMethod.RSA_SHA1, null), Collections
					.singletonList(ref));

			XMLSignature signature = fac.newXMLSignature(si, ki);
			DOMSignContext dsc = new DOMSignContext(privateKey,
					doc.getDocumentElement());
			signature.sign(dsc);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		return os.toString();

	}

	public String myAssinaInut(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(xml.getBytes()));

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList transformList = new ArrayList();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,
				tps);
		Transform c14NTransform = fac.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();

		x509Content.add(certificate);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

		try {

			NodeList elements = doc.getElementsByTagName("infInut");
			Element el = (Element) elements.item(0);
			String id = el.getAttribute("Id");

			Reference ref = fac.newReference("#" + id,
					fac.newDigestMethod(DigestMethod.SHA1, null),
					transformList, null, null);

			// Create the SignedInfo.
			SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
					CanonicalizationMethod.INCLUSIVE,
					(C14NMethodParameterSpec) null), fac.newSignatureMethod(
					SignatureMethod.RSA_SHA1, null), Collections
					.singletonList(ref));

			XMLSignature signature = fac.newXMLSignature(si, ki);
			DOMSignContext dsc = new DOMSignContext(privateKey,
					doc.getDocumentElement());
			signature.sign(dsc);

		} catch (Exception e) {
			System.out.println("Problemas no processo de assinatura");
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		return os.toString();

	}

	public String myAssinaCCe(String xml) throws Exception {
		Vector<String> notas = new Vector<String>();
		notas.add(xml);

		String lote = XMLFactory.montarLoteTempo("12345", notas);
		lote = myAssina(lote, "infEvento", "evento");
		// System.out.println("lote" + lote);
		return lote.substring(lote.indexOf("<envEvento"),
				lote.indexOf("</enviNFe>"));

	}

	public String myAssinaNota(String xml) throws Exception {
		Vector<String> notas = new Vector<String>();
		notas.add(xml);

		String lote = XMLMessageFactoryCTe.createEmptyMessage(notas);
		lote = myAssina(lote, "infNFe", "NFe");
		// System.out.println("lote" + lote);
		return lote.substring(lote.indexOf("<NFe"), lote.indexOf("</enviNFe>"));
	}

	static private int counter = 1;

	public String myAssinaNFSeSP(String xml, String tag, String root)
			throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(xml.getBytes()));

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList transformList = new ArrayList();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,
				tps);
		Transform c14NTransform = fac.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();

		x509Content.add(certificate);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

		try {
			/*
			 * for (int i = 0; i < doc.getDocumentElement()
			 * .getElementsByTagName(root).getLength(); i++) {
			 */
			// System.out.println("************************** i->" + i);

			Element el = (Element) doc.getElementsByTagName(tag).item(0);
			System.out.println(el.getNodeName());
			String id = el.getAttribute("Id");

			/*
			 * if (id == null) { id = ""; }
			 */

			Reference ref = fac.newReference(id,
					fac.newDigestMethod(DigestMethod.SHA1, null),
					transformList, null, null);

			// Create the SignedInfo.
			SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
					CanonicalizationMethod.INCLUSIVE,
					(C14NMethodParameterSpec) null), fac.newSignatureMethod(
					SignatureMethod.RSA_SHA1, null), Collections
					.singletonList(ref));

			XMLSignature signature = fac.newXMLSignature(si, ki, null, null,
					null);

			// XMLSignature signature = fac.newXMLSignature(si, ki);
			DOMSignContext dsc = new DOMSignContext(privateKey,
					doc.getDocumentElement());
			signature.sign(dsc);
			// }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		return os.toString();
	}

	public String myAssinaCTe(String xml) throws Exception {
		Vector<String> notas = new Vector<String>();
		notas.add(xml);

		String lote = XMLMessageFactoryCTe.createEmptyMessage(notas);
		lote = myAssinaCTe(lote, "infCte", "CTe");
		// System.out.println("lote" + lote);
		return lote.substring(lote.indexOf("<CTe"), lote.indexOf("</enviNFe>"));
	}

	/*
	 * public String myAssinaNota(String xml) throws Exception {
	 * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	 * factory.setNamespaceAware(true); DocumentBuilder builder =
	 * factory.newDocumentBuilder(); Document doc =
	 * factory.newDocumentBuilder().parse( new
	 * ByteArrayInputStream(xml.getBytes()));
	 * 
	 * XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
	 * 
	 * ArrayList transformList = new ArrayList(); TransformParameterSpec tps =
	 * null; Transform envelopedTransform =
	 * fac.newTransform(Transform.ENVELOPED, tps); Transform c14NTransform =
	 * fac.newTransform( "http://www.w3.org/TR/2001/REC-xml-c14n-20010315",
	 * tps);
	 * 
	 * transformList.add(envelopedTransform); transformList.add(c14NTransform);
	 * 
	 * KeyInfoFactory kif = fac.getKeyInfoFactory(); List x509Content = new
	 * ArrayList();
	 * 
	 * x509Content.add(certificate); X509Data xd = kif.newX509Data(x509Content);
	 * KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
	 * 
	 * try {
	 * 
	 * NodeList elements = doc.getElementsByTagName("infNFe"); Element el =
	 * (Element) elements.item(0); String id = el.getAttribute("Id");
	 * 
	 * Reference ref = fac.newReference("#" + id, fac.newDigestMethod(
	 * DigestMethod.SHA1, null), transformList, null, null); // Create the
	 * SignedInfo. SignedInfo si =
	 * fac.newSignedInfo(fac.newCanonicalizationMethod(
	 * CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
	 * fac.newSignatureMethod( SignatureMethod.RSA_SHA1, null), Collections
	 * .singletonList(ref));
	 * 
	 * XMLSignature signature = fac.newXMLSignature(si, ki); DOMSignContext dsc
	 * = new DOMSignContext(privateKey, doc .getDocumentElement());
	 * signature.sign(dsc); } catch (Exception e) { // TODO: handle exception
	 * e.printStackTrace(); }
	 * 
	 * ByteArrayOutputStream os = new ByteArrayOutputStream();
	 * TransformerFactory tf = TransformerFactory.newInstance(); Transformer
	 * trans = tf.newTransformer(); trans.transform(new DOMSource(doc), new
	 * StreamResult(os)); return os.toString(); }
	 */
	public String myAssina(String xml, String tag, String root)
			throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(xml.getBytes()));

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList transformList = new ArrayList();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,
				tps);
		Transform c14NTransform = fac.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();

		x509Content.add(certificate);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

		try {
			for (int i = 0; i < doc.getDocumentElement()
					.getElementsByTagName(root).getLength(); i++) {
				System.out.println("i->" + i);

				NodeList elements = doc.getElementsByTagName(tag);
				Element el = (Element) elements.item(i);
				String id = el.getAttribute("Id");

				Reference ref = fac.newReference("#" + id,
						fac.newDigestMethod(DigestMethod.SHA1, null),
						transformList, null, null);

				// Create the SignedInfo.
				SignedInfo si = fac.newSignedInfo(fac
						.newCanonicalizationMethod(
								CanonicalizationMethod.INCLUSIVE,
								(C14NMethodParameterSpec) null), fac
						.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref));

				XMLSignature signature = fac.newXMLSignature(si, ki);
				DOMSignContext dsc = new DOMSignContext(privateKey, doc
						.getDocumentElement().getElementsByTagName(root)
						.item(i));
				signature.sign(dsc);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		return os.toString();
	}

	public String myAssinaCTe(String xml, String tag, String root)
			throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(xml.getBytes()));

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList transformList = new ArrayList();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,
				tps);
		Transform c14NTransform = fac.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();

		x509Content.add(certificate);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

		try {
			for (int i = 0; i < doc.getDocumentElement()
					.getElementsByTagName(root).getLength(); i++) {
				System.out.println("i->" + i);

				NodeList elements = doc.getElementsByTagName(tag);
				Element el = (Element) elements.item(i);
				String id = el.getAttribute("Id");

				Reference ref = fac.newReference("#" + id,
						fac.newDigestMethod(DigestMethod.SHA1, null),
						transformList, null, null);

				// Create the SignedInfo.
				SignedInfo si = fac.newSignedInfo(fac
						.newCanonicalizationMethod(
								CanonicalizationMethod.INCLUSIVE,
								(C14NMethodParameterSpec) null), fac
						.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref));

				XMLSignature signature = fac.newXMLSignature(si, ki);
				DOMSignContext dsc = new DOMSignContext(privateKey, doc
						.getDocumentElement().getElementsByTagName(root)
						.item(i));
				signature.sign(dsc);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		return os.toString();
	}

	private void assinarNFE(XMLSignatureFactory fac, ArrayList transformList,
			PrivateKey privateKey, KeyInfo ki, Document doc, int i)
			throws Exception {

		NodeList elements = doc.getElementsByTagName("infNFe");
		Element el = (Element) elements.item(i);
		String id = el.getAttribute("Id");

		Reference ref = fac.newReference("#" + id,
				fac.newDigestMethod(DigestMethod.SHA1, null), transformList,
				null, null);

		SignedInfo si = fac
				.newSignedInfo(fac.newCanonicalizationMethod(
						CanonicalizationMethod.INCLUSIVE,
						(C14NMethodParameterSpec) null), fac
						.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref));

		XMLSignature signature = fac.newXMLSignature(si, ki);

		DOMSignContext dsc = new DOMSignContext(privateKey, doc
				.getDocumentElement().getElementsByTagName("NFe").item(i));
		signature.sign(dsc);

	}

	public String assinaCancelamento(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(xml.getBytes()));

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList transformList = new ArrayList();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,
				tps);
		Transform c14NTransform = fac.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();

		x509Content.add(certificate);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

		/*
		 * NodeList elements = doc.getElementsByTagName("infCanc"); Element el =
		 * (Element) elements.item(0); String id = el.getAttribute("Id");
		 */

		NodeList elements = doc.getElementsByTagName("cancNFe");
		Element el = (Element) elements.item(0);
		String id = el.getAttribute("Id");

		Reference ref = fac.newReference("#" + id,
				fac.newDigestMethod(DigestMethod.SHA1, null), transformList,
				null, null);

		// Create the SignedInfo.
		SignedInfo si = fac
				.newSignedInfo(fac.newCanonicalizationMethod(
						CanonicalizationMethod.INCLUSIVE,
						(C14NMethodParameterSpec) null), fac
						.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref));

		XMLSignature signature = fac.newXMLSignature(si, ki);
		DOMSignContext dsc = new DOMSignContext(privateKey,
				doc.getDocumentElement());
		signature.sign(dsc);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		return os.toString();

	}

	public String myAssinaNFSe(String xml) throws Exception {
		xml = xml
				.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
						"");

		Vector<String> notas = new Vector<String>();
		notas.add(xml);

		String lote = XMLMessageFactoryCTe.createEmptyMessage(notas);
		lote = myAssinaNFSeSP(lote, "InfRps", "Rps");
		System.out.println("lote" + lote);
		return lote.substring(lote.indexOf("<Rps"), lote.indexOf("</enviNFe>"));
	}

	public String myAssinaConsultaRecebNFSeSP(String xml) throws Exception {

		Vector<String> notas = new Vector<String>();
		notas.add(xml);

		String xmlsigned = myAssinaNFSeSP(xml, "Cabecalho",
				"PedidoConsultaNFePeriodo");
		System.out.println("lote" + xmlsigned);
		return xmlsigned;
	}

	public String assina(String xml, String tag) throws RemoteException {

		try {
			if (tag.equals("Cabecalho"))
				return this.myAssinaConsultaRecebNFSeSP(xml);

			if (tag.equals("infNFe"))
				return this.myAssinaNota(xml);

			if (tag.equals("infCTe"))
				return this.myAssinaCTe(xml);

			if (tag.equals("infCanc"))
				return this.myAssinaCanc(xml);

			if (tag.equals("infInut"))
				return this.myAssinaInut(xml);

			if (tag.equals("infDPEC"))
				return this.myAssinaDpec(xml);

			if (tag.equals("infEvento"))
				return this.myAssinaCCe(xml);

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		/*
		 * ByteArrayInputStream inXML = new
		 * ByteArrayInputStream(xml.getBytes()); DocumentBuilderFactory factory
		 * = DocumentBuilderFactory.newInstance();
		 * factory.setNamespaceAware(false);
		 * 
		 * DocumentBuilder builder = null; try { builder =
		 * factory.newDocumentBuilder(); } catch (ParserConfigurationException
		 * ex) { Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "erro
		 * preparando leitura do xml de dados", ex); System.exit(31); } Document
		 * doc = null; try { doc = builder.parse(inXML); } catch (SAXException
		 * ex) { Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "erro
		 * preparando leitura do xml de dados", ex); System.exit(32); } catch
		 * (IOException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "erro
		 * preparando leitura do xml de dados", ex); System.exit(33); }
		 * 
		 * doc.getDocumentElement().removeAttribute("xmlns:ns2");
		 * 
		 * NodeList elements = doc.getElementsByTagName("infNFe"); Element el =
		 * (Element) elements.item(0); String id = el.getAttribute("Id");
		 * 
		 * XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		 * ArrayList transformList = new ArrayList(); TransformParameterSpec tps
		 * = null; Transform envelopedTransform = null; try { envelopedTransform
		 * = fac.newTransform(Transform.ENVELOPED, tps); } catch
		 * (NoSuchAlgorithmException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(100); } catch (InvalidAlgorithmParameterException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(101); } Transform c14NTransform = null; try {
		 * c14NTransform = fac.newTransform(
		 * "http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps); } catch
		 * (NoSuchAlgorithmException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(102); } catch (InvalidAlgorithmParameterException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(103); }
		 * 
		 * transformList.add(envelopedTransform);
		 * transformList.add(c14NTransform);
		 * 
		 * KeyInfoFactory kif = fac.getKeyInfoFactory();
		 * 
		 * List x509Content = new ArrayList(); x509Content.add(certificate);
		 * //.getDocumentElement().getElementsByTagName("NFe").item(0); X509Data
		 * xd = kif.newX509Data(x509Content);
		 * 
		 * KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd)); Reference
		 * ref = null; try { ref = fac.newReference("#" + id,
		 * fac.newDigestMethod( DigestMethod.SHA1, null), transformList, null,
		 * null); } catch (NoSuchAlgorithmException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(104); } catch (InvalidAlgorithmParameterException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(105); } SignedInfo si = null; try { si =
		 * fac.newSignedInfo(fac.newCanonicalizationMethod(
		 * CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
		 * fac.newSignatureMethod( SignatureMethod.RSA_SHA1, null), Collections
		 * .singletonList(ref)); } catch (NoSuchAlgorithmException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(106); } catch (InvalidAlgorithmParameterException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(107); }
		 * 
		 * XMLSignature signature = fac.newXMLSignature(si, ki); DOMSignContext
		 * dsc = new DOMSignContext(privateKey, doc.getDocumentElement());
		 * 
		 * try { signature.sign(dsc); } catch (MarshalException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(107); } catch (XMLSignatureException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(107); } ByteArrayOutputStream os = new
		 * ByteArrayOutputStream(); TransformerFactory tf =
		 * TransformerFactory.newInstance(); Transformer trans = null; try {
		 * trans = tf.newTransformer(); } catch
		 * (TransformerConfigurationException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(108);ByteArrayInputStream inXML = new
		 * ByteArrayInputStream(xml.getBytes()); DocumentBuilderFactory factory
		 * = DocumentBuilderFactory.newInstance();
		 * factory.setNamespaceAware(false);
		 * 
		 * DocumentBuilder builder = null; try { builder =
		 * factory.newDocumentBuilder(); } catch (ParserConfigurationException
		 * ex) { Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "erro
		 * preparando leitura do xml de dados", ex); System.exit(31); } Document
		 * doc = null; try { doc = builder.parse(inXML); } catch (SAXException
		 * ex) { Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "erro
		 * preparando leitura do xml de dados", ex); System.exit(32); } catch
		 * (IOException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "erro
		 * preparando leitura do xml de dados", ex); System.exit(33); }
		 * 
		 * doc.getDocumentElement().removeAttribute("xmlns:ns2");
		 * 
		 * NodeList elements = doc.getElementsByTagName("infNFe"); Element el =
		 * (Element) elements.item(0); String id = el.getAttribute("Id");
		 * 
		 * XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		 * ArrayList transformList = new ArrayList(); TransformParameterSpec tps
		 * = null; Transform envelopedTransform = null; try { envelopedTransform
		 * = fac.newTransform(Transform.ENVELOPED, tps); } catch
		 * (NoSuchAlgorithmException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(100); } catch (InvalidAlgorithmParameterException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(101); } Transform c14NTransform = null; try {
		 * c14NTransform = fac.newTransform(
		 * "http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps); } catch
		 * (NoSuchAlgorithmException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(102); } catch (InvalidAlgorithmParameterException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(103); }
		 * 
		 * transformList.add(envelopedTransform);
		 * transformList.add(c14NTransform);
		 * 
		 * KeyInfoFactory kif = fac.getKeyInfoFactory();
		 * 
		 * List x509Content = new ArrayList(); x509Content.add(certificate);
		 * X509Data xd = kif.newX509Data(x509Content);
		 * 
		 * KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd)); Reference
		 * ref = null; try { ref = fac.newReference("#" + id,
		 * fac.newDigestMethod( DigestMethod.SHA1, null), transformList, null,
		 * null); } catch (NoSuchAlgorithmException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(104); } catch (InvalidAlgorithmParameterException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(105); } SignedInfo si = null; try { si =
		 * fac.newSignedInfo(fac.newCanonicalizationMethod(
		 * CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
		 * fac.newSignatureMethod( SignatureMethod.RSA_SHA1, null), Collections
		 * .singletonList(ref)); } catch (NoSuchAlgorithmException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(106); } catch (InvalidAlgorithmParameterException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(107); }
		 * 
		 * XMLSignature signature = fac.newXMLSignature(si, ki); DOMSignContext
		 * dsc = new DOMSignContext(privateKey, doc .getDocumentElement());
		 * 
		 * try { signature.sign(dsc); } catch (MarshalException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(107); } catch (XMLSignatureException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(107); } ByteArrayOutputStream os = new
		 * ByteArrayOutputStream(); TransformerFactory tf =
		 * TransformerFactory.newInstance(); Transformer trans = null; try {
		 * trans = tf.newTransformer(); } catch
		 * (TransformerConfigurationException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(108); } try { trans.transform(new DOMSource(doc), new
		 * StreamResult(os)); } catch (TransformerException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(109); } return os.toString(); } try { trans.transform(new
		 * DOMSource(doc), new StreamResult(os)); } catch (TransformerException
		 * ex) { Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
		 * ex); System.exit(109); } return os.toString();
		 */
	}

	String configIpLocal = "localhost";

	public String getConfigIpLocal() {
		return configIpLocal;
	}

}
