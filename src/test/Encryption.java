package test;

import org.springframework.security.crypto.bcrypt.BCrypt;
/**
 *
 * @author Achmad Baihaqi
 */
public class Encryption {
    
    public static void main(String[] args) {
//        String pw_hash = BCrypt.hashpw("haqi12345", BCrypt.gensalt(12));
        String pw_hash = "$2a$12$G9rldUjX0SjYYn169mMO3uLoRJOnlYKggCG5nojWOeOYSFxRnckNm";
        System.out.println(pw_hash);

        if (BCrypt.checkpw("ilham12345", pw_hash)) {
            System.out.println("It matches");
        } else {
            System.out.println("It does not match");
        }
    }
}