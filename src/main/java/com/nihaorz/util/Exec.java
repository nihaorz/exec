package com.nihaorz.util;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created by Nihaorz on 2017/4/5.
 */
public class Exec {

    public static void main(String[] args) {
        InputStream inputStream = Exec.class.getResourceAsStream("/main.bat");
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        StringBuffer sb = new StringBuffer();
        while (scanner.hasNextLine()) {
            String cmd = scanner.nextLine();
            cmd = cmd.trim();
            if (cmd != "") {
                sb.append(new StringBuilder().append(cmd).append("\n").toString());
                String result = executeCmd(cmd);
                sb.append(new StringBuilder().append(result).append("\n").toString());
            }
        }
        // JOptionPane.showMessageDialog(null, sb.toString());
        JOptionPane.showMessageDialog(null, new JScrollPane(new TextArea(sb.toString())), "执行结果", 1, null);
    }

    private static String executeCmd(String cmd) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(new StringBuilder().append(line).append("\n").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (p != null) {
                p.destroy();
            }
        }
        return sb.toString();
    }

}
