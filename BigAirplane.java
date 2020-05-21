package xyz.fcidd.debug;

import java.awt.image.BufferedImage;

/*大敌机*/
public class BigAirplane extends FlyingObject implements Enemy{
    public int getScore() {
        return 3;//大敌机得3分
    }

    private int speed;//移动速度
    /*坐标*/
    public BigAirplane(){
        super(66,89);
        speed=2;
    }
    public void step(){
        y+=speed;//y+(向下)
    }
    private int index=1;
    public BufferedImage getimage(){
        if (isLift()){
            return Images.bairs[0];
        }else if (isDead()){
            BufferedImage img=Images.bairs[index++];
            if (index==Images.bairs.length){//若到最后一张图
                state =REMOVE;//则状态修改为REMOVE删除的
            }
            return img;
        }
        return null;
    }
}