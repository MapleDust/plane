package xyz.fcidd.debug;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
    public static final int LIFE=0;
    public static final int DEAD= 1;
    public static final int REMOVE=2;
    protected int state=LIFE;

    protected int width;//宽
    protected int height;//高
    /*坐标*/
    protected int x;
    protected int y;

    /*小敌机、大敌机、小蜜蜂的属性*/
    public FlyingObject(int width,int height){
        this.width=width;
        this.height=height;
        this.x=(int)(Math.random()*World.WIDTH-width);
        this.y=-height;
    }
    /*英雄机、子弹、天空的属性*/
    public FlyingObject(int width,int height,int x,int y){
        this.width=width;
        this.height=height;
        this.x=x;
        this.y=y;
    }
    public abstract void step();

    public abstract BufferedImage getimage();

    /*判断对象是否是活的*/
    public boolean isLift(){
        return state==LIFE;
    }

    public boolean isDead(){
        return state==DEAD;
    }

    public boolean isRemove(){
        return state==REMOVE;
    }

    public boolean OutOfBounds(){
        return this.y>= World.HEIGHT;
    }

    public boolean hit(FlyingObject other){
        int x1=this.x-other.width;//x1:敌人的x-英雄机/子弹的宽
        int x2=this.x+this.width;//x2:敌人的x+敌人的宽
        int y1=this.y-other.height;//y1:敌人的y-英雄机/子弹的高
        int y2=this.y+this.height;//y2:敌人的y+敌人的高
        int x=other.x; //英雄机/子弹的x
        int y=other.y; //英雄记/子弹的y
        return x>=x1 && x<=x2 && y<=y1 && y<=y2;
    }

    public void goDead(){
        state=DEAD;//将对象状态设置成为Dead
    }
}