package main;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import model.Account;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Token {
    private UserInfo payload;
    private static final String privateKey = "TheSecretKeyIsSupposedToBeSoLongThatIHadToWriteASentenceForIt";

    public Token() {
        payload = new UserInfo("id","username");
    }
    private Token(String foul)
    {
        payload = new UserInfo(foul,foul);
    }
    private static Token falseToken()
    {
        return new Token("false");
    }
    private  Token(UserInfo payload)
    {
        this.payload = payload;
    }

    public String encrypt() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(privateKey));
        String jws = Jwts.builder()
                .setSubject(payload.getUsername())
                .setExpiration(payload.getExpiry())
                .setIssuedAt(payload.getIssued())
                .setId(payload.getId()).signWith(key).compact();
        return jws;
    }

    public static Token validate(String token, ClientInfo clientInfo) {
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(privateKey)
                    .build()
                    .parseClaimsJws(token);
            if (jws.getBody().getExpiration().after(Date.from(LocalDateTime.now().
                    atZone(ZoneId.systemDefault()).toInstant())) &&
                    ((jws.getBody().getSubject().equals("username") && clientInfo.getLoggedInUsername().equals("")) ||
                            jws.getBody().getSubject().equals(clientInfo.getLoggedInUsername()))
            ) {
                return new Token(new UserInfo(jws.getBody().getId(), jws.getBody().getSubject(), jws.getBody().getExpiration(),
                        jws.getBody().getIssuedAt()));
            } else {
                System.err.println(new Gson().toJson(clientInfo));
                System.err.println(new Gson().toJson(jws.getBody()));
                System.out.println(jws.getBody().getExpiration().before(Date.from(LocalDateTime.now().
                        atZone(ZoneId.systemDefault()).toInstant())));
                System.out.println(jws.getBody().getSubject().equals("username") && clientInfo.getLoggedInUsername().equals(""));
                System.out.println( jws.getBody().getSubject().equals(clientInfo.getLoggedInUsername()));
                System.err.println("invalid expiry data or invalid username");
                return falseToken();
            }
            // we can safely trust the JWT
        } catch (JwtException ex) {       // (5)
            System.err.println(ex.getMessage());
            System.err.println("invalid token");
            return falseToken();
        }
    }
    public Boolean isTokenEmpty()
    {
        return payload.getId().equals("id") && payload.getUsername().equals("username");
    }

    public UserInfo getPayload() {
        return payload;
    }

    public void setLogin(Account account) {
        payload = new UserInfo(account.getAccountId(), account.getUsername());
    }

    public void setLogout() {
        payload.logout();
    }

    public boolean isTokenFalse() {
        return payload.getId().equals("false") && payload.getUsername().equals("false");
    }
}
class UserInfo {
    private final String id;
    private final String username;
    private Date expiry;
    private final Date issued;
    public UserInfo(String id, String username) {
        this.id = id;
        this.username = username;
        ZoneId defaultZoneId = ZoneId.systemDefault();
        issued = Date.from(LocalDateTime.now().atZone(defaultZoneId).toInstant());
        expiry = Date.from(LocalDateTime.now().plusDays(1).atZone(defaultZoneId).toInstant());
    }

    public UserInfo(String id, String username, Date expiry, Date issued) {
        this.id = id;
        this.username = username;
        this.expiry = expiry;
        this.issued = issued;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getExpiry() {
        return expiry;
    }

    public Date getIssued() {
        return issued;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void logout() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        expiry = Date.from(LocalDateTime.now().atZone(defaultZoneId).toInstant());
    }
}
