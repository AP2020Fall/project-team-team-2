package model;

import com.sun.source.tree.CompilationUnitTree;

import java.util.*;

public class Country {
    private String name;
    private Player Owner;
    private int soldiers;
    
    public static void main(String[] args) {
    }
    public Country(String name){
        this.name = name;
    }

    public String getName() { return name; }
    public void setOwner(Player owner) {
        Owner = owner;
    }
    public Player getOwner() {
        return Owner;
    }
    public void addSoldiers(int soldiers) {
        this.soldiers += soldiers;
    }
    public int getSoldiers() {
        return soldiers;
    }
}

enum Countries{
    COUNTRY_1("Isreofrosla" , new Country("Isreofrosla") , "Africa"),
    COUNTRY_2("Finmenvis" , new Country("Finmenvis"), "Africa"),
    COUNTRY_3("Tanauli Lands" , new Country("Tanauli Lands"), "Africa"),
    COUNTRY_4("Rai Rea" , new Country("Rai Rea"), "Africa"),
    COUNTRY_5("Iradjihamy" , new Country("Iradjihamy"), "Africa"),
    COUNTRY_6("Ntserne" , new Country("Ntserne"), "Africa"),
    COUNTRY_7("Stanrea Ntarcmoblic" , new Country("Stanrea Ntarcmoblic"), "Africa"),
    COUNTRY_8("Isle of Hongemi" , new Country("Isle of Hongemi"), "Africa"),
    COUNTRY_9("Cona Ribhutic" , new Country("Cona Ribhutic"),"Europe"),
    COUNTRY_10("Guiliewe" , new Country("Guiliewe"),"Europe"),
    COUNTRY_13("Togha" , new Country("Togha"),"Europe"),
    COUNTRY_14("Decangankraine" , new Country("Decangankraine"),"Europe"),
    COUNTRY_15("Ciabra" , new Country("Ciabra"),"Europe"),
    COUNTRY_16("Vea" , new Country("Vea"),"Europe"),
    COUNTRY_17("Virstria Greti" , new Country("Virstria Greti"),"Europe"),
    COUNTRY_18("Tuniatu" , new Country("Tuniatu"),"Europe"),
    COUNTRY_19("Republic of Steini Brial" , new Country("Republic of Steini Brial"),"Europe"),
    COUNTRY_20("Liane Panavir" , new Country("Liane Panavir"),"Europe"),
    COUNTRY_21("Iland Moara" , new Country("Iland Moara"),"Europe"),
    COUNTRY_22("Danmau" , new Country("Danmau"),"Europe"),
    COUNTRY_23("Southern Jathea" , new Country("Southern Jathea"),"Asia"),
    COUNTRY_24("Myanniji Territories" , new Country("Myanniji Territories"),"Asia"),
    COUNTRY_25("Sriu Verdenistern Island" , new Country("Sriu Verdenistern Island"),"Asia"),
    COUNTRY_26("Cochrist Niailand" , new Country("Cochrist Niailand"),"Asia"),
    COUNTRY_27("Andhu" , new Country("Andhu"),"Asia"),
    COUNTRY_28("Frenchandcazim" , new Country("Frenchandcazim"),"Asia"),
    COUNTRY_29("Nitedamegre" , new Country("Nitedamegre"),"Asia"),
    COUNTRY_30("Guyarkeypu" , new Country("Guyarkeypu"),"Asia"),
    COUNTRY_31("Kingemo" , new Country("Kingemo"),"Asia"),
    COUNTRY_32("Republic of Bulta" , new Country("Republic of Bulta"),"Asia"),
    COUNTRY_33("Costa Nasy" , new Country("Costa Nasy"),"Asia"),
    COUNTRY_34("Gileonedi" , new Country("Gileonedi"),"Asia"),
    COUNTRY_35("Coi Ginu" , new Country("Coi Ginu"),"Australia"),
    COUNTRY_36("Theti" , new Country("Theti"),"Australia"),
    COUNTRY_37("Liland" , new Country("Liland"),"Australia"),
    COUNTRY_38("Manque Landroc" , new Country("Manque Landroc"),"Australia"),
    COUNTRY_39("Chaddia" , new Country("Chaddia"),"Australia"),
    COUNTRY_40("Mithio" , new Country("Mithio   "),"Australia"),
    COUNTRY_41("Ofmoami" , new Country("Ofmoami"),"Australia"),
    COUNTRY_42("Corsey" , new Country("Corsey"),"America"),
    COUNTRY_43("Nibutu" , new Country("Nibutu"),"America"),
    COUNTRY_44("Tosaint" , new Country("Tosaint"),"America"),
    COUNTRY_45("Ife" , new Country("Ife"),"America"),
    COUNTRY_46("Deli" , new Country("Deli"),"America"),
    COUNTRY_47("Northand" , new Country("Northand"),"America");

    private final String name;
    private final Country country;
    private final String continent;

    Countries(String name , Country country , String continent) {
        this.name = name;
        this.country = country;
        this.continent = continent;
    }
    public String getName(){
        return this.name;
    }
    public Country getCountry(){
        return this.country;
    }
    public static List<Country> getRandomCountries(int n , int m){
        List<Countries> gameCountries = Arrays.asList(Countries.values());
        Collections.shuffle(gameCountries);
        List<Country> providedCountries = new ArrayList<Country>();
        if(n*m <= gameCountries.size()) {
            for (int i = 0; i < n * m; i++) {
                providedCountries.add(gameCountries.get(i).getCountry());
            }
        }else{
            System.out.println("Your map should be smaller than the world!!!(n*m should be smaller than 47)");
        }
        return providedCountries;

    }
}
