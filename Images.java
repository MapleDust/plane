package xyz.fcidd.debug;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/*图片工具类*/
public class Images {
    public static BufferedImage sky;//天空（图片只有一张,但是会画两次）
    public static BufferedImage bullet;//子弹
    public static BufferedImage[] heros;//英雄机图片数组
    public static BufferedImage[] airs;//小敌机图片数组
    public static BufferedImage[] bairs;//大敌机图片数组
    public static BufferedImage[] bees;//小蜜蜂图片数组
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameover;

    static{
        sky=readImage("background.png");
        bullet=readImage("bullet.png");

        heros =new BufferedImage[2];
        heros[0]=readImage("hero0.png");
        heros[1]=readImage("hero1.png");

        airs=new BufferedImage[5];
        airs[0]=readImage("airplane.png");

        bairs=new BufferedImage[5];
        bairs[0]=readImage("bigairplane.png");

        bees=new BufferedImage[5];
        bees[0]=readImage("bee.png");

        start=readImage("start.png");
        pause=readImage("pause.png");
        gameover=readImage("gameover.png");

        for (int i=1;i<bees.length;i++){
            airs[i]=readImage("bom"+i+".png");
            bairs[i]=readImage("bom"+i+".png");
            bees[i]=readImage("bom"+i+".png");
        }
    }
    public static BufferedImage readImage(String fileName){
        try{
            BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
            return img;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}