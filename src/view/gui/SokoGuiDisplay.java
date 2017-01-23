package view.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.data.Level;
import model.data.MySokobanDisplay;

public class SokoGuiDisplay extends Canvas {
private Level lvl;
private GraphicsContext gc=getGraphicsContext2D();


public SokoGuiDisplay(){
	setWidth(300);
	setHeight(300);
}
public void setLevel(Level l)
{
	this.lvl=l;
}

public void getWH(){
	System.out.println(getWidth()+","+getHeight());
}

public void reDraw()
{

	ArrayList<ArrayList<Character>> board=this.lvl.getBoard();
	//MySokobanDisplay dos= new MySokobanDisplay(lvl);
	//dos.display();
	if(board!=null) {
		int longestRow = 0;
		for (int g = 0; g < board.size(); g++)
		{
			if(board.get(g).size()>longestRow) longestRow=board.get(g).size();
		}

		double W=getWidth();
		double H=getHeight();
		double w=W/longestRow;
		double h=H/board.size();
		System.out.println(w+" "+h+" "+W+" "+H);


		Image wall=null;
		Image floor=null;
		Image target=null;
		Image box=null;
		Image hero=null;


		try {
			wall=new Image(new FileInputStream("./Extras/images/wall.jpg"));
		    floor=new Image(new FileInputStream("./Extras/images/floor.jpg"));
			target=new Image(new FileInputStream("./Extras/images/target.jpg"));
			box=new Image(new FileInputStream("./Extras/images/box.png"));
			hero=new Image(new FileInputStream("./Extras/images/hero.jpg"));


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		for (int i=0;i<board.size();i++)
		{
			for(int j=0;j<board.get(i).size();j++)
			{
				if(board.get(i).get(j)=='o') gc.drawImage(target, j*w, i*h, w, h);
				if(board.get(i).get(j)=='A') gc.drawImage(hero, j*w, i*h, w, h);
				if(board.get(i).get(j)=='@') gc.drawImage(box, j*w, i*h, w, h);
				if(board.get(i).get(j)=='#') gc.drawImage(wall, j*w, i*h, w, h);
				if(board.get(i).get(j)==' ') gc.drawImage(floor, j*w, i*h, w, h);

			}
		}
	}
}
public void clearCanvas()
{
	gc.clearRect(0, 0, getWidth(), getHeight());
}
}


