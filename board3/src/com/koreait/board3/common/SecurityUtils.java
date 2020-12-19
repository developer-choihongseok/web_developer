package com.koreait.board3.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

import com.koreait.board3.model.UserModel;
/*
  Salt -> 원본 데이터의 앞 혹은 뒤에 다른 임의의 데이터를 끼워놓는 것을 말한다.
  		    그러므로, 완전히 다른 결과가 나오게 된다!
*/
public class SecurityUtils {
	// 보안과 관련 있는 것이기 때문에 Utils에 있던 isLogout(), getLoginUser()를  옮겼다.
	// true : 로그아웃 상태,	false : 로그인 상태
	public static boolean isLogout(HttpServletRequest request) {
		return getLoginUser(request) == null;
	}
		
	public static UserModel getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
			
		return (UserModel)session.getAttribute("loginUser");
	}
	
	// 실제로 수정, 삭제 할 때 자주 쓰인다!! -> 만들어 놓으면 편하다.
	// 글 작성자
	public static int getLoginUserPK(HttpServletRequest request) {
		UserModel loginUser = getLoginUser(request);
		
		return loginUser.getI_user();
	}
	
	public static String getSecurePassword(String password, String salt) {

        String generatedPassword = null;
        try {
        	// MessageDigest : Java에서 MD5, SHA를 이용한 해쉬 알고리즘을 사용.
        	// 참고) 해쉬 함수 -> 단방향 함수이므로, 해시값에서 원본 복원이 불가능.
        	// SHA-512 형식으로 암호화 하는 방법!!
        	// SHA- 512 : 64byte -> 영문/숫자 합쳐서 64글자
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // Base64 Decoding
            // Decoding : -> 코드를 문자로 변환(= 암호화 해제)
            byte[] byteSalt = Base64.decodeBase64(salt);
            // 객체 내에 저장된 digest 값(= 해쉬 값)이 계속해서 갱신된다.
            md.update(byteSalt);
            // 해쉬 값 얻는 방법
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder sb = new StringBuilder();
            // 출력
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
	
    public static String getSalt() {
    	
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        
        random.nextBytes(salt);
        
        // Base64 Encoding
        // Encoding : 문자를 코드로 변환(즉, 암호화),		목적: 정보의 호환성
        return Base64.encodeBase64String(salt);
    }
}
