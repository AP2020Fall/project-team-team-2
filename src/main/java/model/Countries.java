package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Countries {
    COUNTRY_1("Isreofrosla", "Africa"),
    COUNTRY_2("Finmenvis", "Africa"),
    COUNTRY_3("Tanauli Lands", "Africa"),
    COUNTRY_4("Rai Rea", "Africa"),
    COUNTRY_5("Iradjihamy", "Africa"),
    COUNTRY_6("Ntserne", "Africa"),
    COUNTRY_7("Stanrea Ntarcmoblic", "Africa"),
    COUNTRY_8("Isle of Hongemi", "Africa"),


    COUNTRY_9("Cona Ribhutic", "Europe"),
    COUNTRY_10("Guiliewe", "Europe"),
    COUNTRY_11("Togha", "Europe"),
    COUNTRY_12("Decangankraine", "Europe"),
    COUNTRY_13("Ciabra", "Europe"),
    COUNTRY_14("Vea", "Europe"),
    COUNTRY_15("Virstria Greti", "Europe"),
    COUNTRY_16("Tuniatu", "Europe"),
    COUNTRY_17("Republic of Steini Brial", "Europe"),
    COUNTRY_18("Liane Panavir", "Europe"),
    COUNTRY_19("Iland Moara", "Europe"),
    COUNTRY_20("Danmau", "Europe"),


    COUNTRY_21("Southern Jathea", "Asia"),
    COUNTRY_22("Myanniji Territories", "Asia"),
    COUNTRY_23("Sriu Verdenistern Island", "Asia"),
    COUNTRY_24("Cochrist Niailand", "Asia"),
    COUNTRY_25("Andhu", "Asia"),
    COUNTRY_26("Frenchandcazim", "Asia"),
    COUNTRY_27("Nitedamegre", "Asia"),
    COUNTRY_28("Guyarkeypu", "Asia"),
    COUNTRY_29("Kingemo", "Asia"),
    COUNTRY_30("Republic of Bulta", "Asia"),
    COUNTRY_31("Costa Nasy", "Asia"),
    COUNTRY_32("Gileonedi", "Asia"),


    COUNTRY_33("Coi Ginu", "Australia"),
    COUNTRY_34("Theti", "Australia"),
    COUNTRY_35("Liland", "Australia"),
    COUNTRY_36("Manque Landroc", "Australia"),
    COUNTRY_37("Chaddia", "Australia"),
    COUNTRY_38("Mithio", "Australia"),
    COUNTRY_39("Ofmoami", "Australia"),


    COUNTRY_40("Corsey", "America"),
    COUNTRY_41("Nibutu", "America"),
    COUNTRY_42("Tosaint", "America"),
    COUNTRY_43("Ife", "America"),
    COUNTRY_44("Deli", "America"),
    COUNTRY_45("Northand", "America");

    private final String name;
    private final String continent;

    Countries(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    public String getName() {
        return this.name;
    }

    public String getContinent() {
        return continent;
    }

    public static int countryNumberContinent(List<Country> countries, String continentName) {
        int number = 0;
        for (Country country : countries) {
            if (continentName.equals(country.getContinent())) {
                number++;
            }
        }
        return number;
    }
}
