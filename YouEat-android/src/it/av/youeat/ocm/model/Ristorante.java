/**
 * Copyright 2009 the original author or authors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package it.av.youeat.ocm.model;

import it.av.youeat.ocm.model.data.City;
import it.av.youeat.ocm.model.data.Country;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 * @author <a href='mailto:a.vincelli@gmail.com'>Alessandro Vincelli</a>
 * 
 */
@JsonAutoDetect
public class Ristorante extends BasicEntity {

    public static final String PATH = "path";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String POSTALCODE = "postalCode";
    public static final String COUNTRY = "country";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";
    public static final String WWW = "www";
    public static final String RATES = "rates";
    public static final String TAGS = "tags";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
    public static final String FAX_NUMBER = "faxNumber";
    public static final String CREATION_TIME = "creationTime";
    public static final String MODIFICATION_TIME = "modificationTime";

    private String name;
    private String address;
    private String postalCode;
    private Country country;
    private String province;
    private City city;
    private String www;
    private Date creationTime;
    private Date modificationTime;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String rating;
    private int revisionNumber;
    private List<Tag> tags;
    private List<RistoranteDescriptionI18n> descriptions;
    private double longitude;
    private double latitude;

    public Ristorante() {
        tags = new ArrayList<Tag>();
        descriptions = new ArrayList<RistoranteDescriptionI18n>();
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getAddress() {
        return address;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public final String getPostalCode() {
        return postalCode;
    }

    public final void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public final Country getCountry() {
        return country;
    }

    public final void setCountry(Country country) {
        this.country = country;
    }

    public final String getProvince() {
        return province;
    }

    public final void setProvince(String province) {
        this.province = province;
    }

    public final City getCity() {
        return city;
    }

    public final void setCity(City city) {
        this.city = city;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return the modificationTime
     */
    public Date getModificationTime() {
        return modificationTime;
    }

    /**
     * @param modificationTime the modificationTime to set
     */
    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    /**
     * @return the versionNumber
     */
    public int getRevisionNumber() {
        return revisionNumber;
    }

    /**
     * @param versionNumber the versionNumber to set
     */
    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    /**
     * @return the www
     */
    public String getWww() {
        return www;
    }

    /**
     * @param www the www to set
     */
    public void setWww(String www) {
        this.www = www;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the descriptions
     */
    public List<RistoranteDescriptionI18n> getDescriptions() {
        return descriptions;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * @param descriptions the descriptions to set
     */
    public void setDescriptions(List<RistoranteDescriptionI18n> descriptions) {
        this.descriptions = descriptions;
    }

    /**
     * add a new description
     * 
     * @param description
     */
    public void addDescriptions(RistoranteDescriptionI18n description) {
        this.descriptions.add(description);
    }

    /**
     * return the description for the current language, empty description otherwise
     * 
     * @param language the language to check
     * @return a description
     */
    public RistoranteDescriptionI18n getDesctiptionByLanguage(Language language) {
        RistoranteDescriptionI18n i18n = new RistoranteDescriptionI18n(language);
        for (RistoranteDescriptionI18n ristoranteDescriptionI18n : this.getDescriptions()) {
            if (ristoranteDescriptionI18n.getLanguage().equals(language)) {
                i18n = ristoranteDescriptionI18n;
            }
        }
        return i18n;
    }

}