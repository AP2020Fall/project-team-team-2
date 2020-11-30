package model;


import java.util.*;

public class Country {
    private String name;
    private Player Owner;
    private String continent;
    private int soldiers = 0;
    
    public static void main(String[] args) {
    }
    public Country(String name , String continent){
        this.name = name;
        this.continent = continent;
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

    public String getContinent() {
        return continent;
    }
}

enum Countries{
    COUNTRY_1("Isreofrosla" , new Country("Isreofrosla" , "Africa") , "Africa"),
    COUNTRY_2("Finmenvis" , new Country("Finmenvis", "Africa"), "Africa"),
    COUNTRY_3("Tanauli Lands" , new Country("Tanauli Lands", "Africa"), "Africa"),
    COUNTRY_4("Rai Rea" , new Country("Rai Rea", "Africa"), "Africa"),
    COUNTRY_5("Iradjihamy" , new Country("Iradjihamy", "Africa"), "Africa"),
    COUNTRY_6("Ntserne" , new Country("Ntserne", "Africa"), "Africa"),
    COUNTRY_7("Stanrea Ntarcmoblic" , new Country("Stanrea Ntarcmoblic", "Africa")
            , "Africa"),
    COUNTRY_8("Isle of Hongemi" , new Country("Isle of Hongemi", "Africa"), "Africa"),


    COUNTRY_9("Cona Ribhutic" , new Country("Cona Ribhutic","Europe"),"Europe"),
    COUNTRY_10("Guiliewe" , new Country("Guiliewe","Europe"),"Europe"),
    COUNTRY_11("Togha" , new Country("Togha","Europe"),"Europe"),
    COUNTRY_12("Decangankraine" , new Country("Decangankraine","Europe"),"Europe"),
    COUNTRY_13("Ciabra" , new Country("Ciabra","Europe"),"Europe"),
    COUNTRY_14("Vea" , new Country("Vea","Europe"),"Europe"),
    COUNTRY_15("Virstria Greti" , new Country("Virstria Greti","Europe"),"Europe"),
    COUNTRY_16("Tuniatu" , new Country("Tuniatu","Europe"),"Europe"),
    COUNTRY_17("Republic of Steini Brial" , new Country("Republic of Steini Brial","Europe")
            ,"Europe"),
    COUNTRY_18("Liane Panavir" , new Country("Liane Panavir","Europe"),"Europe"),
    COUNTRY_19("Iland Moara" , new Country("Iland Moara","Europe"),"Europe"),
    COUNTRY_20("Danmau" , new Country("Danmau","Europe"),"Europe"),


    COUNTRY_21("Southern Jathea" , new Country("Southern Jathea","Asia"),"Asia"),
    COUNTRY_22("Myanniji Territories" , new Country("Myanniji Territories","Asia"),"Asia"),
    COUNTRY_23("Sriu Verdenistern Island" , new Country("Sriu Verdenistern Island","Asia")
            ,"Asia"),
    COUNTRY_24("Cochrist Niailand" , new Country("Cochrist Niailand","Asia"),"Asia"),
    COUNTRY_25("Andhu" , new Country("Andhu","Asia"),"Asia"),
    COUNTRY_26("Frenchandcazim" , new Country("Frenchandcazim","Asia"),"Asia"),
    COUNTRY_27("Nitedamegre" , new Country("Nitedamegre","Asia"),"Asia"),
    COUNTRY_28("Guyarkeypu" , new Country("Guyarkeypu","Asia"),"Asia"),
    COUNTRY_29("Kingemo" , new Country("Kingemo","Asia"),"Asia"),
    COUNTRY_30("Republic of Bulta" , new Country("Republic of Bulta","Asia"),"Asia"),
    COUNTRY_31("Costa Nasy" , new Country("Costa Nasy","Asia"),"Asia"),
    COUNTRY_32("Gileonedi" , new Country("Gileonedi","Asia"),"Asia"),



    COUNTRY_33("Coi Ginu" , new Country("Coi Ginu","Australia"),"Australia"),
    COUNTRY_34("Theti" , new Country("Theti","Australia"),"Australia"),
    COUNTRY_35("Liland" , new Country("Liland","Australia"),"Australia"),
    COUNTRY_36("Manque Landroc" , new Country("Manque Landroc","Australia"),"Australia"),
    COUNTRY_37("Chaddia" , new Country("Chaddia","Australia"),"Australia"),
    COUNTRY_38("Mithio" , new Country("Mithio","Australia"),"Australia"),
    COUNTRY_39("Ofmoami" , new Country("Ofmoami","Australia"),"Australia"),


    COUNTRY_40("Corsey" , new Country("Corsey","America"),"America"),
    COUNTRY_41("Nibutu" , new Country("Nibutu","America"),"America"),
    COUNTRY_42("Tosaint" , new Country("Tosaint","America"),"America"),
    COUNTRY_43("Ife" , new Country("Ife","America"),"America"),
    COUNTRY_44("Deli" , new Country("Deli","America"),"America"),
    COUNTRY_45("Northand" , new Country("Northand","America"),"America");

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

    public String getContinent() {
        return continent;
    }

    public static int countryNumberContinent(List<Country> countries , String continentName){
        int number = 0;
        for(Country country : countries){
            if(continentName.equals(country.getContinent())){
                number++;
            }
        }
        return number;
    }
}
