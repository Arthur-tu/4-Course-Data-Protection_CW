package sample.logic;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MD4AlgoExecutor {

    private String userText;
    private File userFile;
    private String secretKey;

    public MD4AlgoExecutor(String userText) {
        this.userText = userText;
    }

    public MD4AlgoExecutor(File userFile) {
        this.userFile = userFile;
    }

    public MD4AlgoExecutor(File userFile, String secretKey) {
        this.userFile = userFile;
        this.secretKey = secretKey;
    }

    public String generateHashText() {
        try {
            byte[] bytesOfMessage = userText.getBytes(StandardCharsets.UTF_8);
            MD4 md4 = new MD4();
            md4.update(bytesOfMessage,0,bytesOfMessage.length);
            byte[] thedigest = md4.engineDigest();
            StringBuilder hexString = new StringBuilder();
            for(byte bi : thedigest) {
                String hex = Integer.toHexString(0xFF & bi);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Ошибка хеширования";
        }
    }

    public String generateHashFile() {
        try {
            byte[] bytesOfFile = Files.readAllBytes(userFile.toPath());
            MD4 md4 = new MD4();
            md4.update(bytesOfFile,0,bytesOfFile.length);
            byte[] thedigest = md4.engineDigest();
            StringBuilder hexString = new StringBuilder();
            for(byte bi : thedigest) {
                String hex = Integer.toHexString(0xFF & bi);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e) {
            return "Ошибка хеширования";
        }
    }

    public String generateHashFileWithKey() {
        try {
            byte[] bytesOfFile = Files.readAllBytes(userFile.toPath());
            byte[] bytesOfKey = secretKey.getBytes(StandardCharsets.UTF_8);
            byte[] bytes = new byte[bytesOfKey.length + bytesOfFile.length];
            for (int i = 0; i < bytes.length; i++)
                bytes[i] = i < bytesOfFile.length ? bytesOfFile[i] : bytesOfKey[i - bytesOfFile.length];
            MD4 md4 = new MD4();
            md4.update(bytes,0, bytes.length);
            byte[] thedigest = md4.engineDigest();
            StringBuilder hexString = new StringBuilder();
            for(byte bi : thedigest) {
                String hex = Integer.toHexString(0xFF & bi);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e) {
            return "Ошибка хеширования";
        }
    }
}
