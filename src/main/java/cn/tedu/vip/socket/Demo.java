/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.tedu.vip.socket;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        int [] allOut ={1,1,121,21,41,4124,124,235,3156,35};
        int pw=4124;
        for (int i=0;i<allOut.length;i++){
            if (allOut[i]==pw){
                allOut[i]=allOut[allOut.length-1];
                allOut=Arrays.copyOf(allOut,allOut.length-1);
                break;
            }
        }
        System.out.println(Arrays.toString(allOut));
    }
}
