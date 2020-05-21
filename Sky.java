package xyz.fcidd.debug;

import java.awt.image.BufferedImage;

/*天空*/
public class Sky extends FlyingObject {//继承Person
    private int speed;//移动速度
    /*坐标*/
    private int y1;//第二张图片的y坐标
    /*构造方法*/
    public Sky(){
        super(World.WIDTH,World.HEIGHT,0,0);
        speed=1;
        y1=-World.HEIGHT;
    }
    public void step(){
        y+=speed;//y+(向下)
        y1+=speed;//y1+(向下)
        if(y>=World.HEIGHT){//若y>=窗口的高，表示到最下面了
            y=-World.HEIGHT;//则设置y为负的窗口高(把他挪到最上面)
        }
        if (y1>=World.HEIGHT){
            y1=-World.HEIGHT;
        }
    }
    public BufferedImage getimage(){
        return Images.sky;
    }
    public int gety1(){
        return y1;
    }
}
