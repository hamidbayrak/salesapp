package useutils;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import props.Currency;
import props.User;

public class Util {
    
    public static User us = new User();
    public static List<Currency> cls = new ArrayList<>();
    public static final String apikey = "49lY1z5gyd0yOTl3UwyrmofeGhbsWFuU";
    public static final String url = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/318251";
    
    public String md5Generator( String password ) {
        String st = password;
        for (int i = 0; i < 3; i++) {
            st = MD5(st);
        }
        return st;
    }
        
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public List<Currency> xmlRead(){
        try {
            String url = "https://www.tcmb.gov.tr/kurlar/today.xml";
            String data = Jsoup.connect(url).timeout(30000).ignoreContentType(true).get().toString();
            Document doc = Jsoup.parse(data, "", Parser.xmlParser());
            Elements elmnts = doc.getElementsByTag("Currency");
            Currency cr = new Currency();
            cr.setCurrencyName("TÜRK LİRASI");
            cr.setForexSelling(1.0);
            cls.add(cr);
            for (Element item : elmnts ){
                String Isim = item.getElementsByTag("Isim").text();
                if ( Isim.contains("ABD DOLAR") || Isim.contains("EURO") || Isim.contains("STERLİN")){
                    double BanknoteSelling = Double.parseDouble(item.getElementsByTag("BanknoteSelling").text());
                    Currency crt = new Currency();
                    crt.setCurrencyName(Isim);
                    crt.setForexSelling(BanknoteSelling);
                    cls.add(crt);
                }
            }
        } catch (Exception e) {
            System.err.println("xmlRead Error: "+e);
        }
        return cls;
    }
}
