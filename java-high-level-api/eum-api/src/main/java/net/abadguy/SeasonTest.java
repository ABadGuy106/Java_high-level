package net.abadguy;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SeasonTest {
    public static void main(String[] args) {
        System.out.println("=====================");
        CertInfo certInfo = new CertInfo();
        certInfo.setC("China");
        certInfo.setST("商户名称");
        certInfo.setL("商户地址");
        certInfo.setO("SCCBA");
        certInfo.setOU("Bank");
        certInfo.setCN("SHDM0001");
        certInfo.setStartTime("2019-10-21");
        certInfo.setEndTime("2020-01-22");
        certInfo.setSerialNumber("jkfhsdk");
        gennenJKS(certInfo);
    }

    public static void gennenJKS(CertInfo info){
        try{
            char[] chars = new char[0];
            KeyStore store = KeyStore.getInstance("jks");
            store.load(null,chars);
            store.store(new FileOutputStream("D:\\zwj"+info.getCN()+".jks"),"123456".toCharArray());
            System.out.println(store.toString());
        }catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e){
            e.printStackTrace();
       }
    }

}
