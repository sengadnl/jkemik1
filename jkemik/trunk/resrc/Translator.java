package resrc;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import utilities.Tools;
/**
 * The language code used in this source code is based on ISO 639-1 Code
 * http://www.loc.gov/standards/iso639-2/php/code_list.php
 * 
 * */

public class Translator implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String language, country;
	private Locale locale;
	private ResourceBundle messages;
	public Translator(String language, String country, String bundle){
		this.language = language;
		this.country = country;
		this.locale = new Locale(language, country);
		this.messages = ResourceBundle.getBundle(bundle,locale);
	} 
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundle getMessages() {
		return messages;
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	public String translate(String key){    
	      return this.messages.getString(key);
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String toString(){
		return "" + language + " " + country;
	}
	public static void main(String[] args){
		String language = new String("en");
		String country = new String("US");
		Translator tr = new Translator(language,country, "resrc.MessagesBundle_en_US");//"en","US"
		System.out.println("The key is ----- " + tr.translate("greetings") + "" +
				"The language string was " + Tools.languageValue("fr"));
	}

}
