/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author n_otsuka
 */
public class RotationSpeedMap {

    //rotation speed: key = deg/sec
    public static final Map<String, Double> PARA2Speed;

    static {
        Map<String, Double> map = new HashMap<>();
        map.put("000", 0.00);
        map.put("001", 0.05);
        map.put("002", 0.10);
        map.put("003", 0.15);
        map.put("004", 0.18);
        map.put("005", 0.20);
        map.put("006", 0.22);
        map.put("007", 0.24);
        map.put("008", 0.26);
        map.put("009", 0.28);
        map.put("010", 0.30);
        map.put("011", 0.35);
        map.put("012", 0.40);
        map.put("013", 0.50);
        map.put("014", 0.70);
        map.put("015", 0.90);
        map.put("016", 1.10);
        map.put("017", 1.26);
        map.put("018", 1.34);
        map.put("019", 1.42);
        map.put("020", 1.50);
        map.put("021", 1.58);
        map.put("022", 1.66);
        map.put("023", 1.74);
        map.put("024", 1.82);
        map.put("025", 1.90);
        map.put("026", 1.98);
        map.put("027", 2.06);
        map.put("028", 2.14);
        map.put("029", 2.22);
        map.put("030", 2.30);
        map.put("031", 2.38);
        map.put("032", 2.46);
        map.put("033", 2.54);
        map.put("034", 2.62);
        map.put("035", 2.70);
        map.put("036", 2.78);
        map.put("037", 2.86);
        map.put("038", 2.94);
        map.put("039", 3.02);
        map.put("040", 3.10);
        map.put("041", 3.18);
        map.put("042", 3.26);
        map.put("043", 3.34);
        map.put("044", 3.42);
        map.put("045", 3.50);
        map.put("046", 3.58);
        map.put("047", 3.66);
        map.put("048", 3.74);
        map.put("049", 3.82);
        map.put("050", 3.90);
        map.put("051", 3.98);
        map.put("052", 4.05);
        map.put("053", 4.11);
        map.put("054", 4.18);
        map.put("055", 4.25);
        map.put("056", 4.31);
        map.put("057", 4.39);
        map.put("058", 4.46);
        map.put("059", 4.54);
        map.put("060", 4.61);
        map.put("061", 4.65);
        map.put("062", 4.74);
        map.put("063", 4.82);
        map.put("064", 4.91);
        map.put("065", 5.01);
        map.put("066", 5.10);
        map.put("067", 5.20);
        map.put("068", 5.25);
        map.put("069", 5.36);
        map.put("070", 5.47);
        map.put("071", 5.59);
        map.put("072", 5.71);
        map.put("073", 5.83);
        map.put("074", 5.96);
        map.put("075", 6.10);
        map.put("076", 6.24);
        map.put("077", 6.39);
        map.put("078", 6.55);
        map.put("079", 6.72);
        map.put("080", 6.89);
        map.put("081", 7.08);
        map.put("082", 7.27);
        map.put("083", 7.47);
        map.put("084", 7.69);
        map.put("085", 7.92);
        map.put("086", 8.16);
        map.put("087", 8.42);
        map.put("088", 8.70);
        map.put("089", 8.99);
        map.put("090", 9.31);
        map.put("091", 9.65);
        map.put("092", 10.01);
        map.put("093", 10.41);
        map.put("094", 10.83);
        map.put("095", 11.29);
        map.put("096", 11.79);
        map.put("097", 12.34);
        map.put("098", 12.94);
        map.put("099", 13.61);
        map.put("100", 14.34);
        map.put("101", 15.16);
        map.put("102", 15.61);
        map.put("103", 16.08);
        map.put("104", 16.58);
        map.put("105", 17.12);
        map.put("106", 17.69);
        map.put("107", 18.30);
        map.put("108", 18.95);
        map.put("109", 19.66);
        map.put("110", 20.41);
        map.put("111", 21.23);
        map.put("112", 22.11);
        map.put("113", 23.07);
        map.put("114", 24.12);
        map.put("115", 25.27);
        map.put("116", 26.54);
        map.put("117", 27.93);
        map.put("118", 29.49);
        map.put("119", 31.22);
        map.put("120", 33.17);
        map.put("121", 35.38);
        map.put("122", 37.91);
        map.put("123", 40.83);
        map.put("124", 44.23);
        map.put("125", 48.26);
        map.put("126", 53.08);
        map.put("127", 60.00);
        PARA2Speed = Collections.unmodifiableMap(map);
    }

}
