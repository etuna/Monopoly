package gui;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import domain.Bobby;
import domain.Main;
import events.Evnt;

public class Splite implements Drawable, DisF{

	static final int CLOCKWISE = 0;
	static final int COUNTERCLOCKWISE = 1;
	
	private int posX, posY, velX, velY, accelX, accelY;
	private Point mousePos, cPos, cVel, cAccel;
	private Vecd2d dPos, dVel, dAccel;
	private Vecd2d bPos, bVel, bAccel;
	private int smileyState;
	private int midX, midY;
	private int direction;
	private Path2 spPath;
	private double rotation;
	private final double maxAccel = 10.0;
	private final double maxVel = 8.0;
	private final double maxAccelb = 5.0;
	private final double maxVelb = 4.0;
	private int minDex;
	private String scoreText="Score: ";
	private int score;
	private boolean playing = false;
	private final double velvel = 3.5;
	private int brickCount=100;
	
	private KeyboardPos keyboardPos;
	
	private List<Brick> bricks;
	private Bar bar;
	private WorldField worldField;
	
	private final String[] paths = {
			"smileys/happy.png",
			"smileys/sad.png",
			"smileys/mad.png",
			"smileys/confused.png"
	};
	
	private BufferedImage[] images;
	private Animator animator;
	private JFrame frame;
	private DField field;
	
	public DField getField() {
		return field;
	}

	public Splite(Animator anim, JFrame frame) {
		this.animator = anim;
		this.frame = frame;
		keyboardPos = new KeyboardPos(this);
		((GameUI)frame).kbPos = keyboardPos;
		//field = new DField(animator.getSize().getWidth(), animator.getSize().getHeight());
		field = new DField(250, 400);
		//field = new DField(anim.getSize().getWidth(), anim.getSize().getHeight());
		//System.out.println("animator size: " + animator.getSize().getWidth() + animator.getSize().getHeight());
		
		images = new BufferedImage[4];
		direction = CLOCKWISE;
		mousePos = new Point(0,0);
		
		posX=90;
		posY=90;
		cPos = new Point(posX, posY);
		dPos = new Vecd2d(posX, posY);
		
		velX=0;
		velY=0;
		cVel = new Point(velX, velY);
		dVel = new Vecd2d(velX, velY);
		
		accelX=0;
		accelY=0;
		cAccel = new Point(accelX, accelY);
		dAccel = new Vecd2d(accelX, accelY);
		
		File file;
		for (int i=0; i<4; i++) {
			try {
				file = new File(paths[i]);
				images[i] = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		midX = images[0].getWidth()/2;
		midY = images[0].getHeight()/2;
		
		worldField = new WorldField(this);
		
		bricks = new ArrayList<Brick>();
		initBricks();
		initGame();
	}
	
	@Override
	public void draw(Graphics g) {
		update();
		g.setColor(Color.GRAY);
		g.fillRect((int)field.offsetX/2, (int)field.offsetY/2, (int)field.sizeX-1, (int)field.sizeY-1);
		
		//sprite
		AffineTransform tr = AffineTransform.getRotateInstance(rotation, midX, midY);
		AffineTransformOp trop = new AffineTransformOp(tr, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(trop.filter(images[smileyState], null), (int)dPos.x-midX, (int)dPos.y-midY, null);
		
		//outline
		g.setColor(Color.RED);
		g.drawRect((int)field.offsetX/2, (int)field.offsetY/2, (int)field.sizeX-1, (int)field.sizeY-1);
		
		
		//score text
		g.setColor(Color.BLACK);
		g.drawString(scoreText, 150, 18);
		
		//bar
		g.setColor(Color.BLUE);
		g.fillRect((int)(bPos.x-(bar.sizeX/2)), (int)(bPos.y-(bar.sizeY/2)), (int)bar.sizeX, (int)bar.sizeY);
		
		//bricks
		g.setColor(Color.CYAN);
		for (Brick br : bricks) {
			if (br != null && br.alive) {
				g.fillRect((int)(br.posX-(Brick.sizeX/2)), (int) ( br.posY-(Brick.sizeY/2)), (int) Brick.sizeX, (int) Brick.sizeY);
			}
		}
		
	}
	
	public void update() {
		update(17);
	}
	
	public void update(int dTime) {
		smileyState = Bobby.getInstance().getState(); //get which smiley to use
		//mousePos = MouseInfo.getPointerInfo().getLocation();
		if(animator.getmPos() != null)
			mousePos = animator.getmPos();
		else
			mousePos = new Point(0,0);
		//System.out.println("x: " + mousePos.x + " y: " + mousePos.y);
		Main.m.game.bobby_bot.incrementTimer();
		if(Main.m.game.bobby_bot.getTimer()>60*30) {
			Main.m.game.eventManager.notify(new Evnt(Evnt.WAIT));
		}
		
		
		updateBarAccel();
		updateAccel();
		
		updateBarVel();
		updateVel();
		
		
		 
		
		handleBarCollision();
		handleCollision();
		
		updateBarPos();
		updatePos();
		
		updatePath();
		
		
		checkBrickCount();
		
	}
	
	private void checkBrickCount() {
		brickCount=0;
		for(Brick br : bricks) {
			if(br.alive) {
				brickCount++;
			}
		}
		if(brickCount==0) {
			scoreText = "WINNER!!!";
			initGame();
		}else if(isPlaying()){
			scoreText = "Score: " + (100 - brickCount);
		}
	}

	private void handleBarCollision() {
		double x, y;
		double dn;
		
		if(field.getDistance(bPos) < (-bar.sizeX/2))
			return;
		//else:
		Vecd2d normal = field.getGradient(bPos);
		normal.normalize();
		normal = new Vecd2d(-normal.x, -normal.y);
		dn = bVel.x * normal.x + bVel.y* normal.y;
		
		x = bVel.x - 2 * dn * normal.x;
		y = bVel.y - 2 * dn * normal.y;
		
		bVel = new Vecd2d(x,y).setToMaxLength(maxVelb*3);
		
	}

	private void updateBarPos() {
		bPos = new Vecd2d(bPos.x+bVel.x, bPos.y);
		
	}

	private void updateBarVel() {
		bVel = new Vecd2d(bVel.x+bAccel.x, bVel.y+bAccel.y).setToMaxLength(maxVelb);
		
	}

	private void updateBarAccel() {
		double x = 0;
		if(keyboardPos.isLeft()) {
			x-=0.3;
			//System.out.println("left");
		} else if(keyboardPos.isRight()) {
			x+=0.3;
			//System.out.println("right");
		} else {
			x = -bVel.x * 0.1;
		}
		
		bAccel = new Vecd2d(x,bAccel.y);
		
	}

	private void handleCollision() {
		
		//checkBalltoWall();
		//checkBalltoPit();
		
		
		double x, y;
		double dn;
		
		if(field.getDistance(dPos) < -12) {
			if(worldField.getDistance(dPos)<12){
				minDex = ((WorldField)worldField).minDex;
				//System.out.println(minDex);
				Vecd2d normal = worldField.getGradient(dPos);
				normal.normalize();
				// normal = new Vecd2d(-normal.x, -normal.y);
				dn = dVel.x * normal.x + dVel.y * normal.y;

				x = dVel.x - 2 * dn * normal.x;
				y = dVel.y - 2 * dn * normal.y;

				dVel = new Vecd2d(x, y).setToMaxLength(maxVel*5);
				
				if(minDex>1000) {
					dVel.changeLength(dVel.getLength()*1.5);
				}
				
				if(minDex < 1000) {
					for(Brick br : bricks) {
						if(br.index == minDex) {
							br.alive = false;
						}
					}
				}

			}
			
			return;
		}
		if(worldField.getDistance(dPos)<12){
			minDex = ((WorldField)worldField).minDex;
			//System.out.println(minDex);
			Vecd2d normal = worldField.getGradient(dPos);
			normal.normalize();
			// normal = new Vecd2d(-normal.x, -normal.y);
			dn = dVel.x * normal.x + dVel.y * normal.y;

			x = dVel.x - 2 * dn * normal.x;
			y = dVel.y - 2 * dn * normal.y;

			dVel = new Vecd2d(x, y).setToMaxLength(maxVel*5);
			
			if(minDex>1000) {
				dVel.changeLength(dVel.getLength()*1.5);
			}
			
			if(minDex < 1000) {
				for(Brick br : bricks) {
					if(br.index == minDex) {
						br.alive = false;
					}
				}
			}

		}
		
		minDex = ((WorldField)worldField).minDex;
			
		//else:
		
		if(dPos.y > field.getSizeY()-12) { //ball is out of the field
			
			initGame();
		}
		
		Vecd2d normal = field.getGradient(dPos);
		normal.normalize();
		normal = new Vecd2d(-normal.x, -normal.y);
		dn = dVel.x * normal.x + dVel.y* normal.y;
		
		x = dVel.x - 2 * dn * normal.x;
		y = dVel.y - 2 * dn * normal.y;
		
		dVel = new Vecd2d(x,y).setToMaxLength(maxVel);
		//updatePos();
	/*	x = dAccel.x - 2 * dn * normal.x;
		y = dAccel.y - 2 * dn * normal.y;
		
		dAccel = new Vecd2d(x,y).setToMaxLength(maxAccel);*/
	}

	private void updatePos() {
		cPos = new Point(cPos.x+cVel.x, cPos.y+cAccel.y);
		dPos = new Vecd2d(dPos.x+dVel.x, dPos.y+dVel.y);
		
	}

	private void updateVel() {
		cVel = new Point(Math.max(1, cVel.x+cAccel.x), Math.max(1, cVel.y+cAccel.y));
		double x,y;
/*		if (dVel.x+dAccel.x>=0) {
			x = Math.max(0.01, dVel.x+dAccel.x);
		}else {
			
		}*/
		dVel = new Vecd2d(dVel.x+dAccel.x, dVel.y+dAccel.y).setToMaxLength(maxVel);
		
		//x = Math.abs(dVel.x+dAccel.x) > maxVel ? dVel.x+dAccel.x : maxVel;
		//y = Math.abs(dVel.y+dAccel.y) > maxVel ? dVel.y+dAccel.y : maxVel;
		
		//dVel = new Vecd2d(Math.max(0.01, dVel.x+dAccel.x), Math.max(0.01, dVel.y+dAccel.y));
	}

	private void updateAccel() {
		Point dif = new Point(cPos.x - mousePos.x, cPos.y - mousePos.y);
		Vecd2d ddif = new Vecd2d(dPos.x - mousePos.x, dPos.y - mousePos.y);
		Vecd2d ddiff = new Vecd2d(mousePos.x-dPos.x, mousePos.y-dPos.y);
		
		
		cAccel = new Point(Math.max(dif.x * dif.x, 5), Math.max(dif.y * dif.y, 5));
		//dAccel = new Vecd2d(Math.max(ddif.x * ddif.x, 5), Math.max(ddif.y * ddif.y, 5));
		
		dAccel = new Vecd2d((dPos.x - mousePos.x)/900, (dPos.y - mousePos.y)/900).setToMaxLength(maxAccel);
		//dAccel = new Vecd2d(20/((dPos.x - mousePos.x) == 0 ? 0.1 : (dPos.x - mousePos.x)), 20/((dPos.y - mousePos.y) == 0 ? 0.1 : (dPos.y - mousePos.y))).setToMaxLength(maxAccel);
		if (ddif.getLength() < 400) {
			//ddif.changeLength((100-ddif.getLength())/600);
			//ddiff.changeLength((ddiff.getLength())/600);
			//dAccel = new Vecd2d((ddif.x)/7000, (ddif.y)/7000).setToMaxLength(maxAccel);
			//dAccel = ddiff;
		} else {
			//dAccel = new Vecd2d(0.0,0.0);
		}
		ddiff.changeLength(ddiff.getLength()/200);
		dAccel = ddiff;
		
		Vecd2d ppd = new Vecd2d();
		
		if (dVel.getLength()>velvel) {
			dAccel = new Vecd2d(-dVel.x, -dVel.y);
			dAccel.changeLength(dAccel.getLength()*0.01);
		}else {
			dAccel = new Vecd2d(0,0);
		}
				
				
	}

	private void updatePath() {
		if (spPath != null && spPath.hasMoreSteps())
            rotation = spPath.nextRotation();
        else {
            // Get a random number of steps to make the balls move
            // at different speeds.  Note there has to be at least
            // 1 step in each path, but for appearances we used at least
            // 10 steps.
            int numberOfSteps = (int) (10.0 + (Math.random() * 100.0));

            if (direction == CLOCKWISE) {
                spPath = new RotationPath(Math.PI, -Math.PI, numberOfSteps);
                rotation = spPath.nextRotation();
                direction = COUNTERCLOCKWISE;
            }
            else {
                spPath = new RotationPath(-Math.PI, Math.PI, numberOfSteps);
                rotation = spPath.nextRotation();
                direction = CLOCKWISE;
            }
        }
		
	}

	public List<Brick> getBricks() {
		return bricks;
	}

	public void initBricks() {
		for(int i=0;i<10; i++) {
			for(int j=0;j<10;j++) {
				//create brick at pos
				bricks.add(new Brick(30+i*(Brick.sizeX+2), 30+j*(Brick.sizeY+1), i*10+j));
			}
		}
	}
	
	public void initGame() {
		//worldField = field;
		bar = new Bar();
		dPos = new Vecd2d(124,200);
		dVel = new Vecd2d(0,0);
		dAccel = new Vecd2d(0,0);
		bPos = new Vecd2d(124, 360);
		bVel = new Vecd2d(0, 0);
		bAccel = new Vecd2d(0, 0);
		resetBricks();
		score = 0;
		setPlaying(false);
	}

	public double getDistance(Vecd2d p) {
		//return boxDistance(new Vecd2d(p.x-(bar.sizeX/2), p.y-(bar.sizeY/2)));
		return boxDistance(new Vecd2d(p.x-bPos.x, p.y-bPos.y));
	}
	
	public double boxDistance(Vecd2d p) {
		double dx = 0, dy = 0;
		double bx,by, dd, bb, cc;
		
		//dx = p.x <= centerX ? p.x : sizeX-p.x;
		//dy = p.y <= centerY ? p.y : sizeY-p.y;
		
		dx = Math.abs(p.x) - (bar.sizeX/2);
		dy = Math.abs(p.y) - (bar.sizeY/2);
		
		dd = new Vecd2d(Math.max(dx, 0.0), Math.max(dy, 0.0)).getLength();
		//dd = new Vecd2d(dx, dy).getLength();
		//dd = new Vecd2d(Math.max(dx, 0.0), Math.max(dy, 0.0));
		
		bb = Math.min(Math.max(dx, dy),0);
		
		cc = bb+dd;
		if(dd!=0) {
			//System.out.println("dd: " + dd + " bb: " + bb + " dd+bb: " + cc);
		}
		
		return cc;
		
		//return Math.min(dx, dy);
	}
	
	void resetBricks() {
		for (Brick br : bricks) {
			br.alive = true;
		}
	}
	
	int calcDField() {
		
		
		return 0;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		if(!isPlaying() && playing) {
			dVel = new Vecd2d(Math.random()-0.5,1).changeLength(velvel);
			//initGame();
		}
		this.playing = playing;
	}

}
