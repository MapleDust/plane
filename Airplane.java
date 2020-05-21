package xyz.fcidd.debug;

import java.awt.image.BufferedImage;

/*小敌机*/
public class Airplane extends FlyingObject implements Enemy{
    public int getScore() {
        return 1;//打掉小敌机得1分
    }

    private int speed;//移动速度
    public Airplane(){
        super(48,50);
        speed=2;
    }
    public void step(){
        y+=speed;//y加向下
    }
    private int index=1;
    public BufferedImage getimage(){
        if (isLift()){
            return Images.airs[0];
        }else if (isDead()){
            BufferedImage img=Images.airs[index++];
            if (index==Images.airs.length){//若到最后一张图
                state =REMOVE;//则状态修改为REMOVE删除的
            }
            return img;
        }
        return null;
    }
}