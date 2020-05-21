package xyz.fcidd.debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/*窗口类*/
public class World extends JPanel {
    /*常量*/
    public static final int WIDTH=400;
    public static final int HEIGHT=700;

    /*游戏运行状态*/
    public static final int START=0;
    public static final int RUNNING=1;
    public static final int PAUSE=2;
    public static final int GAME_OVER=3;
    private int state=START;

    /*声明变量*/
    private Sky sky=new Sky();
    private Hero hero=new Hero();
    private FlyingObject[] enemies={};
    private Bullet[] bullets={};

    /*删除越界的敌人、子弹*/
    public void outOfBoundsAction(){
        /*敌人*/
        int index=0;
        FlyingObject[] enemyLives=new FlyingObject[enemies.length];
        for (int i=0;i<enemies.length;i++){
            FlyingObject f=enemies[i];
            if (!f.OutOfBounds() && !f.isRemove()){
                enemyLives[index]=f;
                index++;
            }
        }
        enemies=Arrays.copyOf(enemyLives,index);

        /*子弹*/
        index=0;
        Bullet[] bulletLives=new Bullet[bullets.length];
        for (int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            if (!b.OutOfBounds() && !b.isRemove()){
                bulletLives[index]=b;
                index++;
            }
        }
        bullets=Arrays.copyOf(bulletLives,index);
    }

    /*生成小敌机、大敌机、小蜜蜂的对象*/
    public FlyingObject nextOne(){
        int type=(int)(Math.random()*50);
        if (type<10){
            return new Bee();
        }else if (type<35){
            return new Airplane();
        }else{
            return new BigAirplane();
        }
    }

    /*飞行物移动*/
    public void stepAction(){
        sky.step();//天空移动
        for (int i=0;i<enemies.length;i++){
            enemies[i].step();
        }
        for (int i=0;i<bullets.length;i++){
            bullets[i].step();
        }
    }

    private int enterIndex=0;
    /*敌人入场*/
    public void enterAction(){
        enterIndex++;
        if (enterIndex%40==0){//每400(40*10)毫秒走一次
            FlyingObject obj=nextOne();
            enemies=Arrays.copyOf(enemies,enemies.length+1);//扩容
            enemies[enemies.length-1] =obj;
        }
    }

    /*发射子弹的速度*/
    private int shootIndex=0;
    public void shootAction(){
        shootIndex++;
        if (shootIndex%30==0){
            Bullet[] bs=hero.shoot();
            bullets=Arrays.copyOf(bullets,bullets.length+bs.length);
            System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
        }
    }

    private int score = 0;//玩家得分

    /*子弹与敌人碰撞*/
    public void bulletBangAction(){
        for (int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            for (int j=0;j<enemies.length;j++){
                FlyingObject f=enemies[j];
                if (f.isLift() && b.isLift() && f.hit(b)){
                    f.goDead();
                    b.goDead();
                if (f instanceof Enemy){
                    Enemy e=(Enemy) f;
                    score +=e.getScore();
                }
                if (f instanceof Award){
                    Award a=(Award)f;
                    int type=a.getAwardType();
                    switch (type){
                        case Award.FIRE:
                            hero.addFire();
                            break;
                        case Award.LIFE:
                            hero.addLife();
                            break;
                    }
                }
                }
            }
        }
    }

    /*英雄机与敌人碰撞*/
    public void heroBangAction(){
        for (int i=0;i<enemies.length;i++){
            FlyingObject f=enemies[i];
            if (f.isLift() && hero.isLift() && f.hit(hero)){
                f.goDead();
                hero.subtractLift();//英雄机减命
                hero.clearFire();//清空火力值
            }
        }
    }

    /*检测游戏结束*/
    public void checkGameOverAction(){
        /*英雄机命数小于等于0,游戏结束*/
        if (hero.getLife()<=0){
            state=GAME_OVER;
        }
    }

    /*启动程序执行*/
    public void action(){
        //侦听器对象
        MouseAdapter m=new MouseAdapter() {
            /*重写mouseMoved()鼠标移动事件*/
            public void mouseMoved(MouseEvent e){
                if (state==RUNNING){
                    int x=e.getX();//获取鼠标的x坐标
                    int y=e.getY();//获取鼠标的y坐标
                    hero.moveTo(x,y);
                }
            }

            /*重写鼠标点击事件*/
            public void mouseClicked(MouseEvent e){
                switch (state){//根据当前状态做不同的处理
                    case START://启动状态时
                        state=RUNNING;//将当前状态修改为运行状态
                        break;
                    case GAME_OVER://游戏结束状态时
                        score=0;
                        sky=new Sky();
                        hero=new Hero();
                        enemies=new FlyingObject[0];
                        bullets=new Bullet[0];
                        state=START;//将当前状态修改为启动状态
                        break;
                }
            }

            public void mouseExited(MouseEvent e){
                if (state==RUNNING){
                    state=PAUSE;
                }
            }

            public void mouseEntered(MouseEvent e){
                if (state==PAUSE){
                    state=RUNNING;
                }
            }
        };
        this.addMouseListener(m);//处理鼠标点击
        this.addMouseMotionListener(m);//处理鼠标滑动

        /*定时器*/
        Timer timer=new Timer();
        int intervel=10;
        timer.schedule(new TimerTask(){
            public void run(){
                if (state==RUNNING){
                    enterAction();//敌人入场
                    shootAction();//子弹发射速度
                    stepAction();//敌人入场
                    outOfBoundsAction();//清除越界
                    bulletBangAction();//子弹与敌人碰撞
                    heroBangAction();//英雄机与敌人碰撞
                    checkGameOverAction();//检测游戏状态
                }
                repaint();
            }
        },intervel,intervel);
    }

    public void paint(Graphics g){
        super.paint(g);

        g.drawImage(sky.getimage(),sky.x,sky.y,null);
        g.drawImage(sky.getimage(),sky.x,sky.gety1(),null);
        g.drawImage(hero.getimage(),hero.x,hero.y,null);

        for (int i=0;i<enemies.length;i++){
            FlyingObject f=enemies[i];
            g.drawImage(f.getimage(),f.x,f.y,null);
        }

        for (int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            g.drawImage(b.getimage(),b.x,b.y,null);
        }

        /*分数和生命值显示*/
        g.drawString("分数"+score,10,15);//画分
        g.drawString("生命值"+hero.getLife(),10,33);//画生命值

        switch (state){
            case START:
                g.drawImage(Images.start,0,0,null);
                break;
            case PAUSE:
                g.drawImage(Images.pause,0,0,null);
                break;
            case GAME_OVER:
                g.drawImage(Images.gameover,0,0,null);
                break;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);//1)设置窗口可见度 2)尽快调用paint()方法
        frame.setTitle("飞机大战 - code by MapleDust");
        frame.setResizable(false);

        world.action();
    }
    /*
    * 1)不能将引用声main中，原因是:
    * --如果写在main中，则其他方法中就无法对引用进行访问了
    * --所以将引用声明在类中，方法外，最终结果是：引用可以在整个类中访问
    * 2) 在main中无法访问引用的，原因是：
    * --main方法为static的
    * --所以单独做一个非static的方法action来做测试
    * --action方法计算机是不认识的，因为计算机只认识main方法；
    * --所以在main中创建当前World类的对象，然后用过引用去打点调用的方法;
    */
}