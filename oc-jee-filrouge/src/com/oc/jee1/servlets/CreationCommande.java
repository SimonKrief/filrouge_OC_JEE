package com.oc.jee1.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oc.jee1.beans.Client;
import com.oc.jee1.beans.Commande;

public class CreationCommande extends HttpServlet {
	private static final Logger LOG = Logger.getLogger("");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * R�cup�ration des donn�es saisies, envoy�es en tant que param�tres de la
		 * requ�te GET g�n�r�e � la validation du formulaire
		 */
		String nom = request.getParameter("nomClient");
		String prenom = request.getParameter("prenomClient");
		String adresse = request.getParameter("adresseClient");
		String telephone = request.getParameter("telephoneClient");
		String email = request.getParameter("emailClient");
		
		String dateLivraison = request.getParameter("dateLivraison");
		LOG.info(dateLivraison);
		
		
		
		

		/* R�cup�ration de la date courante */
//        DateTime dt = new DateTime();
		LocalDateTime dt = LocalDateTime.now();// = new LocaDateTime(now);
		/* Conversion de la date en String selon le format d�fini */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        String date = dt.toString( formatter );
		String date = dt.format(formatter);

		double montant;
		try {
			/* R�cup�ration du montant */
			montant = Double.parseDouble(request.getParameter("montantCommande"));
		} catch (NumberFormatException e) {
			/* Initialisation � -1 si le montant n'est pas un nombre correct */
			montant = -1;
		}
		String modePaiement = request.getParameter("modePaiementCommande");
		String statutPaiement = request.getParameter("statutPaiementCommande");
		String modeLivraison = request.getParameter("modeLivraisonCommande");
		String statutLivraison = request.getParameter("statutLivraisonCommande");

		String message;
		/*
		 * Initialisation du message � afficher : si un des champs obligatoires du
		 * formulaire n'est pas renseign�, alors on affiche un message d'erreur, sinon
		 * on affiche un message de succ�s
		 */
		if (nom.trim().isEmpty() || adresse.trim().isEmpty() || telephone.trim().isEmpty() || montant == -1
				|| modePaiement.isEmpty() || modeLivraison.isEmpty()) {
			message = "Erreur - Vous n'avez pas rempli tous les champs obligatoires. <br> <a href=\"creerCommande.jsp\">Cliquez ici</a> pour acc�der au formulaire de cr�ation d'une commande.";
		} else {
			message = "Commande cr��e avec succ�s !";
		}
		/*
		 * Cr�ation des beans Client et Commande et initialisation avec les donn�es
		 * r�cup�r�es
		 */
		Client client = new Client();
		client.setNom(nom);
		client.setPrenom(prenom);
		client.setAdresse(adresse);
		client.setTelephone(telephone);
		client.setEmail(email);

		Commande commande = new Commande();
		commande.setClient(client);
		commande.setDate(date);
		commande.setMontant(montant);
		commande.setModePaiement(modePaiement);
		commande.setStatutPaiement(statutPaiement);
		commande.setModeLivraison(modeLivraison);
		commande.setStatutLivraison(statutLivraison);
		
		commande.setDateLivraison(dateLivraison);

		/* Ajout du bean et du message � l'objet requ�te */
		request.setAttribute("commande", commande);
		request.setAttribute("message", message);

		/* Transmission � la page JSP en charge de l'affichage des donn�es */
		this.getServletContext().getRequestDispatcher("/afficherCommande.jsp").forward(request, response);
	}
}