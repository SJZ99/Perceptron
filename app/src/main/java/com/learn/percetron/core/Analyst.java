package com.learn.percetron.core;

public class Analyst {
    /**
     * Translate time to -1~1
     * @param time Actually time
     * @return -1~1
     */
    public static double time(double time) {
        return (time - 30) / 15;
    }
    /**
     * Translate distance to -1~1
     * @param stopCount
     * @return -1~1
     */
    public static double distance(double stopCount) {
        return (stopCount - 4) / 10;
    }

    /**
     * @param weather weather
     * @return pointer Which position in data should be one
     */
    public static int weather(String weather){
        int pointer = 0;
        switch (weather) {
            case "晴天":
                pointer = 0;
                break;
            case "陰天":
                pointer = 1;
                break;
            case "雨天":
                pointer = 2;
                break;
        }
        return pointer;
    }

    /**
     * @param temperature temperature
     * @return pointer Which position in data should be one
     */
    public static int temperature(String temperature){
        int pointer = 0;
        switch (temperature) {
            case "熱":
                pointer = 3;
                break;
            case "微熱":
                pointer = 4;
                break;
            case "微涼":
                pointer = 5;
                break;
            case "涼":
                pointer = 6;
                break;
        }
        return pointer;
    }

    /**
     * Translate output to String
     * @param output1
     * @return
     */
    public static String isGetBus(double output1){
        String result;
        if(output1 >= 0){
            result = "搭上車 ";
        }else{
            result = "搭不到 ";
        }
        return result;
    }

    /**
     * output to string
     * @param output2 distance
     * @return String
     */
    public static String distanceReverseToString(double output2){
        output2 = output2 * 10.0 + 4.0;
        output2 = Math.round(output2 * 100.0) / 100.0;
        return  " " + output2 + "站";
    }
}
