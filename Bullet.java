package xyz.fcidd.debug;

import java.awt.image.BufferedImage;

/*子弹*/
public class Bullet extends FlyingObject {
    private int speed;//移动速度
    public Bullet(int x,int y){//子弹需要参数：子弹有很多个，英雄机在哪，子弹就在哪！
        super(8,20,x,y);
        speed=3;
    }
    public void step(){
        y-=speed;//y-(向上)
    }
    public BufferedImage getimage(){
        if (isLift()){
            return Images.bullet;
        }else if (isDead()){
            state=REMOVE;
        }
        return null;
    }

    public boolean OutOfBounds(){
        return this.y<=-this.height;//子弹的y<=负的就是越界
    }
}
