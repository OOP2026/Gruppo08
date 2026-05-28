package model;

public abstract class Utente {
	private String nome;
	private String cognome;
	private String login;
	private String email;
	private String pswd;

	public abstract boolean puoRichiedereSpostamento();

	public abstract boolean puoModificareOrario();

	public abstract Integer getMatricola();

	Utente(String nome, String cognome, String login, String email, String pswd) {
		this.nome = nome;
		this.cognome = cognome;
		this.login = login;
		this.email = email;
		this.pswd = pswd;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getLogin() {
		return login;
	}

	public String getEmail() {
		return email;
	}

	public boolean checkPswd(String pswd) {
		return pswd.equals(this.pswd);
	}

}
