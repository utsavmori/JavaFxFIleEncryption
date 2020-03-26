package com.example.fileencryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

public class Aes {
    static void fileProcessor(int cipherMode, String key, File inputFile, File outputFile) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean Encrypt(String hashedPassword, List<File> listOfFiles) {
        if (hashedPassword.equals("") || hashedPassword == null) {
            return false;
        }
        for (int i = 0; i < listOfFiles.size(); i++) {
            if (listOfFiles.get(i).isFile()) {
                String fname = listOfFiles.get(i).getName();
                if (fname.equals("FileEncrypter.jar")) {
                    continue;
                }
                if (fname.toCharArray()[0] == '.') {
                    continue;
                }
                String newFname = "";
                String[] filePathArray = listOfFiles.get(i).getAbsolutePath().split(Pattern.quote(System.getProperty("file.separator")));
                for (int j = 0; j < filePathArray.length - 1; j++) {
                    newFname += filePathArray[j] + File.separator;
                }
                newFname += fname + ".enc";
                File encryptedFile = new File(newFname);
                Aes.fileProcessor(Cipher.ENCRYPT_MODE, hashedPassword.substring(0, 32), listOfFiles.get(i), encryptedFile);
//                inputFile.delete();
            }
        }

        File hashFile = new File("Hash.enc");
        try {
            FileWriter fw = new FileWriter(hashFile);
            fw.write(hashedPassword);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean Decrypt(String hashedPassword, List<File> listOfFiles) throws IOException {
        if (hashedPassword.equals("") || hashedPassword == null) {
            return false;
        }
        File hshfile = new File("Hash.enc");
        if (hshfile.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(hshfile));
            String hashToCompare = br.readLine();
            br.close();
            if (hashToCompare.equals(hashedPassword)) {
                for (int i = 0; i < listOfFiles.size(); i++) {
                    if (listOfFiles.get(i).isFile()) {
                        String fname = listOfFiles.get(i).getName();
                        if (fname.equals("FileEncrypter.jar")) {
                            continue;
                        }

                        String newFilePath = "";
                        String[] filePathArray = listOfFiles.get(i).getAbsolutePath().split(Pattern.quote(System.getProperty("file.separator")));
                        for (int j = 0; j < filePathArray.length - 1; j++) {
                            newFilePath += filePathArray[j] + File.separator;
                        }
                        newFilePath += getOriginalFileName(fname);
                        File decryptedFile = new File(newFilePath);
                        Aes.fileProcessor(Cipher.DECRYPT_MODE, hashedPassword.substring(0, 32), listOfFiles.get(i), decryptedFile);
//                        inputFile.delete();
                    }
                }


            } else {
                return false;
            }
            hshfile.delete();
        } else {
            return false;
        }
        return true;
    }

    private static String getOriginalFileName(String fname) {
        String[] temp = fname.split("\\.");
        String newfname = "";
        for (int j = 0; j < temp.length - 1; j++) {
            if (j == 0) {
                newfname += (temp[j]);
            } else {
                newfname += "." + temp[j];
            }
        }
        return newfname;
    }

}
