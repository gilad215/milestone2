package view.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import db.HibernateUtil;
import db.User;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.application.HostServices;
import model.data.Command;
import model.data.Level;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import view.View;

public class SokobanGUIController extends Observable implements Initializable,View {
	private Level lvl;
	private HostServices services;
	private int timercount;
	private int steps=0;

	private HashMap<String,Integer> levelIDs;
    private int lvlid;
    private	String fullname;

    @FXML
	Text timer;
	@FXML
    Text stepscount;
	private StringProperty timerCounter;
	private IntegerProperty stepsCounter;


	private String command;
    private String mp3path= "./Extras/music/Kirby.mp3";
    private String finishpath="./Extras/music/finished.mp3";
    private Media mp3=new Media(new File(mp3path).toURI().toString());
    private Media finish=new Media(new File(finishpath).toURI().toString());
    private MediaPlayer player=new MediaPlayer(mp3);
    private MediaPlayer finished=new MediaPlayer(finish);

	@FXML
	private SokoGuiDisplay displayer;

    public SokobanGUIController()
    {


        levelIDs=new HashMap<>();
        levelIDs.put("LEVEL1",1);
        levelIDs.put("LEVEL2",2);
        levelIDs.put("LEVEL3",3);

    }

	public void setCommand(String c)
	{
		this.command=c;
        String[] arr = command.split(" ");

        LinkedList<String> list=new LinkedList<>();
        Collections.addAll(list, arr);
        this.setChanged();
		this.notifyObservers(list);
	}


	public void openFile()
	{
	    setTimer(0);
	    steps=0;
	    this.stepsCounter.set(steps);
	    displayer.clearCanvas();
	    FileChooser fc=new FileChooser();
		fc.setTitle("Open Sokoban Level");
		fc.setInitialDirectory(new File("./Extras"));
		File chosen= fc.showOpenDialog(null);
		if(chosen!=null)
		{
		    String pattern="level\\d+";
            Pattern p=Pattern.compile(pattern);
            Matcher m=p.matcher(chosen.getPath());
            if(m.find())
            {
                if(levelIDs.containsKey(m.group(0).toUpperCase())) lvlid=levelIDs.get(m.group(0).toUpperCase());
            }

            List<String> params = new LinkedList<String>();
            params.add("load");
            params.add(chosen.getPath());
            this.setChanged();
            this.notifyObservers(params);
            finished.stop();
            player.play();
            startTimer();
            displayer.requestFocus();
        }

	}


	public void saveFile() {
        if (lvl != null) {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("./Extras/"));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML doc(*.xml)", "*.xml"));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Object(*.obj)", "*.obj"));

            File chosen = fc.showSaveDialog(null);
            if (chosen != null) {
                displayer.requestFocus();
                List<String> params = new LinkedList<String>();
                params.add("save");
                params.add(chosen.getPath());
                this.setChanged();
                this.notifyObservers(params);
            }
        }

    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    timerCounter=new SimpleStringProperty();
        stepsCounter=new SimpleIntegerProperty();


        setTimer(0);
        timer.textProperty().bind(timerCounter);
        stepscount.textProperty().bind(stepsCounter.asString());

	    displayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->displayer.requestFocus());

		displayer.setOnKeyPressed(new EventHandler<KeyEvent>()
				{

					@Override
					public void handle(KeyEvent event) {
						if(lvl!=null)
						{
							if (event.getCode()==KeyCode.UP)
							{
								setCommand("move up");

							}
							if (event.getCode()==KeyCode.DOWN)
							{
								setCommand("move down");
							}
							if (event.getCode()==KeyCode.LEFT)
							{
								setCommand("move left");
							}
							if (event.getCode()==KeyCode.RIGHT)
							{
								setCommand("move right");
							}
						}

					}

				});






}


	@Override
	public void displayMessage(Command c) {
		// TODO Auto-generated method stub

	}


	@Override
	public void display(Level l) {
		this.lvl=l;
		displayer.setLevel(lvl);
		displayer.reDraw();

	}

    @Override
    public void setMoveMade(String s) {
	    if(s.equalsIgnoreCase("movemade"))
        {
            steps++;
            this.stepsCounter.set(steps);
        }

    }

    @Override
    public void Finished() {
	    player.stop();
	    finished.play();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TextInputDialog dialog=new TextInputDialog("Name");
                dialog.setTitle("Level Finished!");
                dialog.setHeaderText("Time: "+getTimer()+" Steps: "+steps);

                dialog.setContentText("Please enter your Full name:");
                Optional<String> result=dialog.showAndWait();
                if(result.isPresent())
                {
                    System.out.println(result.get());
                    fullname=result.get();
                }
                String[] name = fullname.split(" ");
                addUser(new User(lvlid,name[0],name[1],steps,getTimer()));
            }
        });


    }

    public void stop()
    {
        Platform.exit();
        System.exit(0);
    }
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

    public void playMusic()
    {
        player.play();
    }
    public void stopMusic()
    {
        player.stop();
        finished.stop();
    }
    private  void startTimer()
    {


        Timer t=new  Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                setTimer(getTimer()+1);
            }
        }, 0, 1000);
    }

    private void setTimer(int num) {
        this.timercount = num;
        this.timerCounter.set(""+num);
    }
    private int getTimer(){return this.timercount;}

    private void addUser(User u)
    {

                Session session= HibernateUtil.getSessionFactory().getCurrentSession();
                System.out.println("Session opened");

                try {
                    session.beginTransaction();
                    session.save(u);
                    session.getTransaction().commit();
                } catch (HibernateException e) {
                    if (session.getTransaction() != null)
                        session.getTransaction().rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }


    }
}

