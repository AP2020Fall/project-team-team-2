package main;

import com.google.gson.Gson;
import model.Account;

import java.time.LocalDateTime;

public class Token {
    private Header header;
    private UserInfo payload;
    private String signature;
    private static String privateKey = "We got em boys";

    public Token() {
        header = new Header("fuck hash","bad type");
        payload = new UserInfo("id","username");
        signature = "signature";
    }

    public String encrypt() {
        System.out.println("Token: " + new Gson().toJson(this));
        return "This is supposed to be the encrypted token but it is a just text informing you that i am to lazy" +
                " to code this shit. ;)";
       /* try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            System.err.println("Error in encrypting");
            return "";
            //Invalid Signing configuration / Couldn't convert Claims.
        }*/
    }

    public Boolean validate(ClientInfo clientInfo) {
        return true;
    }

    public static Token decrypt(String token) {
        return new Token();
    }


    public Header getHeader() {
        return header;
    }

    public UserInfo getPayload() {
        return payload;
    }

    public String getSignature() {
        return signature;
    }

    public void setLogin(Account account) {
        payload = new UserInfo(account.getAccountId(), account.getUsername());
    }

    public void setLogout() {
        payload.setValid(false);
    }
}
class Header {
    private String algorithm;
    private String type;

    public Header(String  algorithm, String type) {
        this.algorithm = algorithm;
        this.type = type;
    }
}
class UserInfo {
    private String id;
    private String username;
    private Boolean valid;
    private LocalDateTime expiry;

    public UserInfo(String id, String username) {
        this.id = id;
        this.username = username;
        valid = true;
        expiry = LocalDateTime.now().plusDays(1);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getValid() {
        return valid;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
