package test;

import org.springframework.security.crypto.bcrypt.BCrypt;
/**
 *
 * @author Achmad Baihaqi
 */
public class Encryption {
    
    public static void main(String[] args) {
        String pw_hash = BCrypt.hashpw("haqi12345", BCrypt.gensalt(12));
        System.out.println(pw_hash);

        if (BCrypt.checkpw("ilham12345", pw_hash)) {
            System.out.println("It matches");
        } else {
            System.out.println("It does not match");
        }
    }
}