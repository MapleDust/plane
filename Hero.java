package xyz.fcidd.debug;

import java.awt.image.BufferedImage;

/*英雄机*/
public class Hero extends FlyingObject {
    private int life;//命
    private int fire;//火力值

    public Hero(){
        super(97,139,140,400);
        life=3;
        fire=0;
    }

    public void step(){
    }

    private int index=0;

    public BufferedImage getimage(){
        if (isLift()){
            return Images.heros[index++%Images.heros.length];
        }
        return null;
    }

    /*英雄机发射子弹*/
    public Bullet[] shoot(){
        int xStep=this.width/4;
        int yStep=20;
        if (fire>0){
            Bullet[] bs=new Bullet[2];
            bs[0]=new Bullet(x+1*xStep,y-yStep);
            bs[1]=new Bullet(x+3*xStep,y-yStep);
            fire-=2;//发射一次双倍火力，则火力-2
            return bs;
        }else {
            Bullet[] bs=new Bullet[1];
            bs[0]=new Bullet(x+2*xStep,y-yStep);
            return bs;
        }
    }

    /*随着鼠标移动*/
    public void moveTo(int x,int y){
        this.x = x-this.width/2;
        this.y = y-this.height/2;
    }

    /*英雄机增命*/
    public void addLife(){
        life++;//命数增加1
    }

    /*英雄机减命*/
    public void subtractLift(){
        life--;
    }

    /*获取英雄机的生命值*/
    public int getLife(){
        return life;
    }

    /*英雄机增活力*/
    public void addFire(){
        fire+=40;//活力值曾40
    }

    /*清空火力值*/
    public void clearFire(){
        fire=0;
    }
}