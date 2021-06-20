package flappyBird;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import javax.swing.JFrame;

public class FlappyBird implements ActionListener, KeyListener
{
	public static FlappyBird flappyBird;
	
	public final int WIDTH = 800, HEIGHT = 800;
	
	public Renderer renderer;
	
	public Rectangle bird;
	
	public int ritz, vmotion, score;
	
	public ArrayList<Rectangle> columns;
	
	public Random random;
	
	public boolean endGame, begin;

	
	public FlappyBird()
	{
		JFrame jframe = new JFrame();
		
		Timer timer = new Timer(20,this);
		
		renderer = new Renderer();
		random = new Random();
		
		jframe.add(renderer);
		
		jframe.setTitle("Flying Shape");
		
		jframe.setSize(WIDTH, HEIGHT);
		
		jframe.setResizable(false);
		
		jframe.addKeyListener(this);
		
		jframe.setVisible(true);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		bird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
		
		
	}
	
	public void addColumn(boolean begin)
	{
		int space = 300;
		int width = 100;
		//randomly generated height
		int height = 50 + random.nextInt(300);
		
		if(begin)
		{
		
		
		columns.add(new Rectangle(WIDTH + width + columns.size()*300,
				HEIGHT - height - 120,width, height));
		columns.add(new Rectangle(WIDTH + width + (columns.size() - 1)*300,0, width, HEIGHT - height - space));
		}
		
		else
		{
			columns.add(new Rectangle(columns.get(columns.size()-1).x +600,
					HEIGHT - height - 120,width, height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x,0, width, HEIGHT - height - space));
		}
		
	}
	
	public void paintColumn(Graphics g, Rectangle column)
	{
		g.setColor(Color.cyan);
		g.fillRect(column.x, column.y,column.width , column.height);
	
	}
	
	public void actionPerformed(ActionEvent e)
	{
		renderer.repaint();
		 
		int speed = 10;
		
		ritz++;
		
		for(int i = 0; i < columns.size(); i++)
		{
			Rectangle col = columns.get(i);
			col.x -= 10;
			
		}
		
		if(ritz %2 ==0 && vmotion < 20)
		{
			vmotion +=2;
		}
		
		for(int i = 0; i < columns.size(); i++)
		{
			Rectangle col = columns.get(i);
			if(col.x + col.width < 0)
			{
				columns.remove(col);
				
				if(col.y == 0)
				{
				addColumn(false);
				}
				
			}
			
		}
		//adding y motion
		bird.y += vmotion; 
		//collision detection
		for(Rectangle col : columns)
		{
			if(bird.intersects(col))
			{
				endGame = true;
				
				bird.x = col.x - bird.width;
			}
		}
		
		if(bird.y > HEIGHT -120 || bird.y < 0)
		{
			
			endGame = true;
		}
		
		if(endGame)
		{
			//stops the bird where it will fall
			bird.y = HEIGHT -120 - bird.height;
		}

		
	}
	

	public void repaint(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.gray.darker());
		g.fillRect(0, HEIGHT - 150, WIDTH, 150);
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 150, WIDTH, 20);
		
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		// TODO Auto-generated method stub
		
		for(Rectangle col : columns)
		{
			paintColumn(g, col);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		
		//if(!endGame) {
			//g.drawString("Click to begin!",75, HEIGHT/2 - 50);
		

		//}
		
		
		if(endGame)
		{
			g.drawString("Game Over!", 100, HEIGHT/2 - 50);
		}
		
	}
	public static void main(String [] args)
	{
		flappyBird = new FlappyBird();
		
	}
	//the jump method -- why the object goes up
	public void jump()
	{
		if(endGame)
		{
			endGame = false;
			

			bird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
			columns.clear();
			vmotion =0;
			//score = 0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
		}
		
		if (!begin)
		{
			begin = true;
		}
		
		else if(!endGame)
		{
			if(vmotion > 0)
			{
				//if the 
				vmotion =0;
			}
			vmotion -= 10;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		jump();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	}
	
