/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package point;

import java.util.Scanner;

/**
 * @author otsuka@kinsoku
 */
public class AngleData extends Thread {

    private final PointData pdDesign;
    private double horizontalDMS, verticalDMS;  // DMS形式
    private double distance;                    // 

    public AngleData(PointData design) {
        this.pdDesign = design;
        this.horizontalDMS = 0.;
        this.verticalDMS = 0.;
        this.distance = 0.;
    }

    public AngleData(PointData design, double h, double v) {
        this.pdDesign = design;
        this.horizontalDMS = h;
        this.verticalDMS = v;
        this.distance = 0.;
    }

    public AngleData(PointData design, double h, double v, double l) {
        this.pdDesign = design;
        this.horizontalDMS = h;
        this.verticalDMS = v;
        this.distance = l;
    }

    public static String DMS2Str(double dms) {
        String dmsStr = String.format("%8.4f", dms);
        String deg = dmsStr.substring(0, 3);
        String min = dmsStr.substring(4, 6);
        String sec = dmsStr.substring(6, 8);
        return deg + "°" + min + "'" + sec + "''";
    }

    public static String DMS2FIX7(double dms) {
        // 2018.01.21 n_otsuka
        // ここは本来 "%08.4f" と指定したいが 359.59596 が四捨五入により 359.5960 となる不具合が
        // 解決できないので、"%09.5f" と指定して 0.1秒単位を切り捨てる。
        //String w = String.format("%08.4f", dms);
        String w = String.format("%09.5f", dms);
        return String.format("%s%s", w.substring(0, 3), w.substring(4, 8));
    }

    public static double RAD2DMS(double rad) {
        double seconds;
        while (rad >= 2. * Math.PI) {
            rad -= 2. * Math.PI;
        }
        while (rad < 0.) {
            rad += 2. * Math.PI;
        }
        seconds = Math.round(10 * rad * 180 * 60 * 60 / Math.PI);
        seconds /= 10.;
        return SEC2DMS(seconds);
    }

    public static double DMS2RAD(double dms) {
        double deg, min, sec, subsec;
        double rad;
        while (dms >= 360.) {
            dms -= 360.;
        }
        while (dms < 0.) {
            dms += 360.;
        }
        String dmsStr = String.format("%9.5f", dms);
        deg = Double.parseDouble(dmsStr.substring(0, 3));
        min = Double.parseDouble(dmsStr.substring(4, 6));
        sec = Double.parseDouble(dmsStr.substring(6, 9)) / 10;
        rad = Math.PI / 180. * (deg + min / 60 + sec / 3600);
        return rad;
    }

    /*
    * @description
    * D.MS形式の角度データを符号付 sec 単位に変換。
    * 0.1秒単位まで変換。
     */
    public static double DMS2SEC(double a) {
        int sign;
        String strA = String.format("%011.6f", a);
        String ww;
        ww = strA.substring(0, 4);
        int degA = Integer.parseInt(ww);
        if (a >= 0) {
            sign = 1;
        } else {
            sign = -1;
            degA *= -1;
        }
        ww = strA.substring(5, 7);
        int minA = degA * 60 + Integer.parseInt(ww);
        // 小数点以下第5位まで切り出して
        ww = strA.substring(7, 10);
        double secA = minA * 60 + Integer.parseInt(ww) / 10.;
        secA *= sign;
        return secA;
    }

    // unsigned DMS.
    public static double SEC2DMS(double a) {
        while (a < 0) {
            a += 60 * 60 * 360;
        }
        while (a >= 60 * 60 * 360) {
            a -= 60 * 60 * 360;
        }
        int deg = (int) a / 3600;
        a -= (deg * 3600);
        int min = (int) a / 60;
        a -= (min * 60);
        // 0.01秒の位で四捨五入し、0.1秒単位で出力しよう
        int sec = (int) Math.round(a * 10);
        return Double.parseDouble(String.format("%4d.%02d%03d", deg, min, sec));
    }

    // signed DMS.
    public static double SEC2DMS2(double seconds) {
        char sign;
        double xxx = seconds;
        if (xxx >= 0) {
            sign = '+';
        } else {
            sign = '-';
            xxx *= -1;
        }
        int deg = (int) xxx / 3600;
        xxx -= (deg * 3600);
        int min = (int) xxx / 60;
        xxx -= (min * 60);
        // 0.01秒の位で四捨五入し、0.1秒単位で出力しよう
        int sec = (int) Math.round(xxx * 10);
        //deg *= sign;
        String strDMS = String.format("%c%d.%02d%03d", sign, deg, min, sec);
        double ret = Double.parseDouble(strDMS);
//        System.out.println(String.format("SEC2DMS2(%8.4f) -> %8.4f", seconds, ret));
        return ret;
    }

    public static double addDMS(double a, double b) {
        double secSum = DMS2SEC(a) + DMS2SEC(b);
        return SEC2DMS(secSum);
    }

    public static double diffDMS(double a, double b) {
        double secDiff = DMS2SEC(a) - DMS2SEC(b);
        return SEC2DMS(secDiff);
    }

    public static void main(String[] args) {
        boolean quit = false;
        String str, input;
        double dms, rad, sec;
        double a, b;
        Scanner scan = new Scanner(System.in);

        a = 0.0000;
        b = 0.00001;
        System.out.println("addDMS(" + a + "," + b + ")=" + addDMS(a, b));
        System.out.println("diffDMS(" + a + "," + b + ")=" + diffDMS(a, b));

        a = 0.0000;
        b = -0.00001;
        System.out.println("addDMS(" + a + "," + b + ")=" + addDMS(a, b));
        System.out.println("diffDMS(" + a + "," + b + ")=" + diffDMS(a, b));

        a = 360.;
        b = 0.00001;
        System.out.println("addDMS(" + a + "," + b + ")=" + addDMS(a, b));
        System.out.println("diffDMS(" + a + "," + b + ")=" + diffDMS(a, b));

        a = 360.;
        b = -0.00001;
        System.out.println("addDMS(" + a + "," + b + ")=" + addDMS(a, b));
        System.out.println("diffDMS(" + a + "," + b + ")=" + diffDMS(a, b));

        System.out.println("Enter command or 'q':");
        str = scan.next();
        while (!str.equals("q")) {
            switch (str) {
                case "DMS2RAD":
                    System.out.println("Enter D.MS>");
                    input = scan.next();
                    dms = Double.parseDouble(input);
                    System.out.println("DMS2RAD(" + input + ")=" + DMS2RAD(dms));
                    break;
                case "DMS2SEC":
                    System.out.println("Enter D.MS>");
                    input = scan.next();
                    dms = Double.parseDouble(input);
                    System.out.println("DMS2SEC(" + input + ")=" + DMS2SEC(dms));
                    break;
                case "RAD2DMS":
                    System.out.println("Enter RAD>");
                    input = scan.next();
                    rad = Double.parseDouble(input);
                    System.out.println("RAD2DMS(" + input + ")=" + RAD2DMS(rad));
                    break;
                case "SEC2DMS":
                    System.out.println("Enter sec>");
                    input = scan.next();
                    sec = Double.parseDouble(input);
                    System.out.println("SEC2DMS(" + input + ")=" + SEC2DMS(sec));
                    break;
                case "addDMS":
                    System.out.println("Enter a>");
                    input = scan.next();
                    a = Double.parseDouble(input);
                    System.out.println("Enter b>");
                    input = scan.next();
                    b = Double.parseDouble(input);
                    System.out.println("addDMS(" + a + "," + b + ")=" + addDMS(a, b));
                    break;
                case "diffDMS":
                    System.out.println("Enter a>");
                    input = scan.next();
                    a = Double.parseDouble(input);
                    System.out.println("Enter b>");
                    input = scan.next();
                    b = Double.parseDouble(input);
                    System.out.println("diffDMS(" + a + "," + b + ")=" + diffDMS(a, b));
                    break;
                default:
            }

            System.out.println("Enter command or 'q':");
            str = scan.next();
        }

    }

    public PointData getPdDesign() {
        return this.pdDesign;
    }

    public double getHorizontalDMS() {
        return this.horizontalDMS;
    }

    public void setHorizontalDMS(double h) {
        this.horizontalDMS = h;
    }

    public double getVerticalDMS() {
        return this.verticalDMS;
    }

    public void setVerticalDMS(double v) {
        this.verticalDMS = v;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double l) {
        this.distance = l;
    }

}
