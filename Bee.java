package xyz.fcidd.debug;

import java.awt.image.BufferedImage;

/*小蜜蜂*/
public class Bee extends FlyingObject implements Award{
    public int getAwardType(){
        return awardType;
    }

    private int xspeed;//x移动速度
    private int yspeed;//y移动速度
    private int awardType;//奖励

    public Bee(){
        super(60,51);
        xspeed=1;
        yspeed=2;
        awardType=(int)(Math.random()*2);
    }

    public void step(){
        x+=xspeed;
        y+=yspeed;
        if (x<=0||x>=World.WIDTH-width){
            xspeed*=-1;
        }
    }

    private int index=1;
    public BufferedImage getimage(){
        if (isLift()){
            return Images.bees[0];
        }else if (isDead()){
            BufferedImage img=Images.bees[index++];
            if (index==Images.bees.length){//若到最后一张图
                state =REMOVE;//则状态修改为REMOVE删除的
            }
            return img;
        }
        return null;
    }
}