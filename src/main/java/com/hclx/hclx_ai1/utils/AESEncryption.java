package com.hclx.hclx_ai1.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
public class AESEncryption {
 private static final String ALGORITHM = "AES";
 private static final int KEY_SIZE = 128;
 /**
  * 加密方法
  * @param key 随机密钥
  * @param value 要加密的字符串
  * @return
  * @throws Exception
  */
 public static String encrypt(String key, String value) throws Exception
 {
  KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
  keyGenerator.init(KEY_SIZE);
  SecretKey secretKey = new
          SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
  Cipher cipher = Cipher.getInstance(ALGORITHM);
  cipher.init(Cipher.ENCRYPT_MODE, secretKey);
  byte[] encryptedBytes =
          cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
  return Base64.getEncoder().encodeToString(encryptedBytes);
 }
 /**
  * 解密
  * @param key
  * @param encryptedValue
  * @return
  * @throws Exception
  */
 public static String decrypt(String key, String encryptedValue) throws
         Exception {
  KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
  keyGenerator.init(KEY_SIZE);
  SecretKey secretKey = new
          SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
  Cipher cipher = Cipher.getInstance(ALGORITHM);
  cipher.init(Cipher.DECRYPT_MODE, secretKey);
  byte[] decryptedBytes =
          cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
  return new String(decryptedBytes, StandardCharsets.UTF_8);
 }
 /**
  * 传入32位id获取16位的字符串
  * @param id
  * @return
  */
 public static String get16bitUUID(String id) {
  String fullID = id.toString().replace("-", "");
  return fullID.substring(0, 16);
 }
 public static void main(String[] args) {

  try {
   String key = get16bitUUID(UUID.randomUUID().toString());
   String originalText = "mySecretPassword"; // 原始文本
   String encryptedText = encrypt(key, originalText);
   System.out.println("Encrypted Text: " + encryptedText);
   String decryptedText = decrypt(key, encryptedText);
   System.out.println("Decrypted Text: " + decryptedText);
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
}
