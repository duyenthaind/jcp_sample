package com.thaind.test;

public class NoneVisility {
    private int number = 0;

    public int upNum(){
        return ++number;
    }

    public int getNum(){
        return this.number;
    }

    // Novisibility
    public void incr() {
        new Thread(() -> {
            for (int index = -1; ++index < 100000;) {
                ++number;
            }
        }).start();
        System.out.println(this.number);
    }

    public static void incr(NoneVisility novisiblity){
        System.out.println(novisiblity.getNum());
        for(int index = -1; ++index < 10000;){
            novisiblity.upNum();
        }
        System.out.println(novisiblity.getNum());
    }

    public static void main(String[] args) {
        NoneVisility no = new NoneVisility();
        no.incr();
        new Thread(()->{
            NoneVisility.incr(no);
        }).start();
    }

}
