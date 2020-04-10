package com.chacko.ben.sslpoke;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSLPokeController {
	@GetMapping("/test/{host}")
	public String getBook(@PathVariable String host) {
		return SSLPokeUtility.connect(host, 443);
	}

	@GetMapping("/certs")
	public String listCerts() {
		StringBuilder sb = null;
		try {
			// Load the JDK's cacerts keystore file
			String filename = System.getProperty("java.home")
					+ "/lib/security/cacerts".replace('/', File.separatorChar);
			FileInputStream is = new FileInputStream(filename);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			String password = "changeit";
			keystore.load(is, password.toCharArray());
			Enumeration<String> aliases = keystore.aliases();
			sb = new StringBuilder();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
		            sb.append(alias);
		            sb.append("\n");
			}
		} catch (CertificateException e) {
		} catch (KeyStoreException e) {
		} catch (NoSuchAlgorithmException e) {
		} catch (IOException e) {
		}
		return sb != null ? sb.toString() : "Error";
	}
	
	@GetMapping("/listCerts")
	public String listCertDetails() {
		StringBuilder sb = null;
		try {
			// Load the JDK's cacerts keystore file
			String filename = System.getProperty("java.home")
					+ "/lib/security/cacerts".replace('/', File.separatorChar);
			FileInputStream is = new FileInputStream(filename);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			String password = "changeit";
			keystore.load(is, password.toCharArray());
			sb = new StringBuilder();
			
			// This class retrieves the most-trusted CAs from the keystore
			PKIXParameters params = new PKIXParameters(keystore);

			// Get the set of trust anchors, which contain the most-trusted CA certificates
			Iterator<TrustAnchor> it = params.getTrustAnchors().iterator();
			while (it.hasNext()) {
				TrustAnchor ta = (TrustAnchor) it.next();
				// Get certificate
				X509Certificate cert = ta.getTrustedCert();
				sb.append(cert);
	            sb.append("\n\n");
	            System.out.println(cert);
			}
		} catch (CertificateException e) {
		} catch (KeyStoreException e) {
		} catch (NoSuchAlgorithmException e) {
		} catch (InvalidAlgorithmParameterException e) {
		} catch (IOException e) {
		}
		return sb != null ? sb.toString() : "Error";
	}
	
	@GetMapping("/certs/{alias}")
	public String getCert(@PathVariable String alias) {
		try {
			// Load the JDK's cacerts keystore file
			String filename = System.getProperty("java.home")
					+ "/lib/security/cacerts".replace('/', File.separatorChar);
			FileInputStream is = new FileInputStream(filename);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			String password = "changeit";
			keystore.load(is, password.toCharArray());
			Certificate cert = keystore.getCertificate(alias);
			return cert.toString();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return "Error";
		}
	}

}
