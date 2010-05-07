package it.av.youeat.ocm.model;

/**
 * @author Alessandro Vincelli
 */
public class RistoranteDescriptionI18n extends BasicEntity {

    public static final String LANGUAGE = "language";
    public static final String DESCRIPTION = "description";

    private Language language;

    private String description;

    /**
     * default construct
     */
    public RistoranteDescriptionI18n() {
        super();
    }

    /**
     * Use the value in {@link Locales} to create the {@link Language}
     * 
     * @param language the language to use for this description
     */
    public RistoranteDescriptionI18n(Language language) {
        super();
        this.language = language;
        this.description = "";
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
