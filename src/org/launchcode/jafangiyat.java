package org.launchcode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class jafangiyat {
    public static void main(String[] args) {
        myclass m1=new myclass();
        m1.func1();
    }
}
class myclass{
    myclass(){

    }
    public void func1(){
        Integer a=0;
        func2(a);
        System.out.println(a);
    }
    private void func2(Integer a){
        a++;
    }
}