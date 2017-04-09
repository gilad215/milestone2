package view.gui;

import java.io.*;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.application.HostServices;
import javafx.stage.Stage;
import model.data.Command;
import model.data.Level;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import view.View;

public class SokobanGUIController extends Observable implements Initializable,View {
	private Level lvl;
	private HostServices services;
	private int timercount;
	private int steps=0;

	private HashMap<String,Integer> levelIDs;
    private int lvlid=0;
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
        levelIDs.put("LEVEL4",4);



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
                int finishtime=getTimer();
                TextInputDialog dialog=new TextInputDialog("Name");
                dialog.setTitle("Level Finished!");
                dialog.setHeaderText("Time: "+finishtime+" Steps: "+steps);

                dialog.setContentText("Please enter your Full name:");
                Optional<String> result=dialog.showAndWait();
                fullname=result.get();
                while(fullname.split(" ").length!=2)
                {
                    result=dialog.showAndWait();
                    System.out.println(result.get()+"length: "+fullname.length());
                    fullname=result.get();
                }
                String[] name = fullname.split(" ");

                addUser(new User(lvlid,name[0],name[1],steps,finishtime));
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

    public void showLeaderBoards() {

        TableView<User> usertable;

        TableColumn<User, Integer> gameidColumn = new TableColumn<>("Game ID");
        gameidColumn.setMinWidth(100);
        gameidColumn.setCellValueFactory(new PropertyValueFactory<>("GameID"));

        TableColumn<User, Integer> LevelidColumn = new TableColumn<>("LevelID");
        LevelidColumn.setMinWidth(100);
        LevelidColumn.setCellValueFactory(new PropertyValueFactory<>("levelID"));

        TableColumn<User, String> FirstNameColumn = new TableColumn<>("First Name");
        FirstNameColumn.setMinWidth(150);
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("First_name"));

        TableColumn<User, String> LastNameColumn = new TableColumn<>("Last Name");
        LastNameColumn.setMinWidth(150);
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        TableColumn<User, Integer> FinishTimeColumn = new TableColumn<>("Finish Time");
        FinishTimeColumn.setMinWidth(100);
        FinishTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));

        TableColumn<User, Integer> StepsColumn = new TableColumn<>("Steps");
        StepsColumn.setMinWidth(100);
        StepsColumn.setCellValueFactory(new PropertyValueFactory<>("steps"));

        usertable = new TableView<>();
        usertable.setItems(getUsers());
        usertable.getColumns().addAll(gameidColumn, LevelidColumn, FirstNameColumn, LastNameColumn, FinishTimeColumn, StepsColumn);
        usertable.getSortOrder().add(LevelidColumn);
        usertable.getSortOrder().add(FinishTimeColumn);
        usertable.getSortOrder().add(StepsColumn);

        Label search=new Label("Search:");
        TextField searchfield=new TextField();
        searchfield.setText("Press ENTER to Search");
        HBox hb=new HBox();
        hb.getChildren().addAll(search,searchfield);
        hb.setSpacing(10);
        searchfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER))
                {
                    String[] split=searchfield.getText().split(" ");
                    if(split.length>1)
                    {
                        showPlayerTable(new User(10,split[0],split[1],1,1));
                    }
                    System.out.println(split[0].toUpperCase());
                    if(levelIDs.containsKey(split[0].toUpperCase())) showLevelTable(levelIDs.get(split[0].toUpperCase()));
                }
            }
        });


        Stage table = new Stage();
        table.setTitle("Leaderboards");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hb,usertable);

        Scene scene = new Scene(vBox);
        table.setScene(scene);
        table.show();

        usertable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount()==2)
                    showPlayerTable(usertable.getSelectionModel().getSelectedItem());
                    //usertable.getSelectionModel().getSelectedItem().printUser();
            }
        });

    }

    public void showPlayerTable(User selectedUser)
    {

        TableView<User> usertable;

        TableColumn<User, Integer> gameidColumn = new TableColumn<>("Game ID");
        gameidColumn.setMinWidth(100);
        gameidColumn.setCellValueFactory(new PropertyValueFactory<>("GameID"));

        TableColumn<User, Integer> LevelidColumn = new TableColumn<>("LevelID");
        LevelidColumn.setMinWidth(100);
        LevelidColumn.setCellValueFactory(new PropertyValueFactory<>("levelID"));

        TableColumn<User, String> FirstNameColumn = new TableColumn<>("First Name");
        FirstNameColumn.setMinWidth(150);
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("First_name"));

        TableColumn<User, String> LastNameColumn = new TableColumn<>("Last Name");
        LastNameColumn.setMinWidth(150);
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        TableColumn<User, Integer> FinishTimeColumn = new TableColumn<>("Finish Time");
        FinishTimeColumn.setMinWidth(100);
        FinishTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));

        TableColumn<User, Integer> StepsColumn = new TableColumn<>("Steps");
        StepsColumn.setMinWidth(100);
        StepsColumn.setCellValueFactory(new PropertyValueFactory<>("steps"));

        usertable = new TableView<>();
        usertable.setItems(getUser(selectedUser));
        usertable.getColumns().addAll(gameidColumn, LevelidColumn, FirstNameColumn, LastNameColumn, FinishTimeColumn, StepsColumn);
        usertable.getSortOrder().add(LevelidColumn);
        usertable.getSortOrder().add(FinishTimeColumn);
        usertable.getSortOrder().add(StepsColumn);


        Stage table = new Stage();
        table.setTitle("Player Stats");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(usertable);

        Scene scene = new Scene(vBox);
        table.setScene(scene);
        table.show();

    }

    public void showLevelTable(int lvlid)
    {

        TableView<User> usertable;

        TableColumn<User, Integer> gameidColumn = new TableColumn<>("Game ID");
        gameidColumn.setMinWidth(100);
        gameidColumn.setCellValueFactory(new PropertyValueFactory<>("GameID"));

        TableColumn<User, Integer> LevelidColumn = new TableColumn<>("LevelID");
        LevelidColumn.setMinWidth(100);
        LevelidColumn.setCellValueFactory(new PropertyValueFactory<>("levelID"));

        TableColumn<User, String> FirstNameColumn = new TableColumn<>("First Name");
        FirstNameColumn.setMinWidth(150);
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("First_name"));

        TableColumn<User, String> LastNameColumn = new TableColumn<>("Last Name");
        LastNameColumn.setMinWidth(150);
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        TableColumn<User, Integer> FinishTimeColumn = new TableColumn<>("Finish Time");
        FinishTimeColumn.setMinWidth(100);
        FinishTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));

        TableColumn<User, Integer> StepsColumn = new TableColumn<>("Steps");
        StepsColumn.setMinWidth(100);
        StepsColumn.setCellValueFactory(new PropertyValueFactory<>("steps"));

        usertable = new TableView<>();
        usertable.setItems(getLevel(lvlid));
        usertable.getColumns().addAll(gameidColumn, LevelidColumn, FirstNameColumn, LastNameColumn, FinishTimeColumn, StepsColumn);
        usertable.getSortOrder().add(LevelidColumn);
        usertable.getSortOrder().add(FinishTimeColumn);
        usertable.getSortOrder().add(StepsColumn);


        Stage table = new Stage();
        table.setTitle("Player Stats");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(usertable);

        Scene scene = new Scene(vBox);
        table.setScene(scene);
        table.show();

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
    public ObservableList<User> getUsers()
    {
        ObservableList<User> users= FXCollections.observableArrayList();
        Query<User> query= HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
        query.setMaxResults(20);
        List<User> list=query.list();
        for (User u:list) {
            if(lvlid==0) users.add(u);
            else {
                if (u.getLevelID() == lvlid) users.add(u);
            }

        }
        for(User user:users) user.printUser();
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        return users;
    }
    public ObservableList<User> getUser(User selectedUser)
    {
        ObservableList<User> users= FXCollections.observableArrayList();
        Query<User> query= HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
        List<User> list=query.list();
        for (User u:list) {
                if(selectedUser.getFirst_name().compareTo(u.getFirst_name())==0 && selectedUser.getLast_name().compareTo(u.getLast_name())==0)
                    users.add(u);
        }
        for(User user:users) user.printUser();
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        return users;
    }
    public ObservableList<User> getLevel(int lvlid)
    {
        ObservableList<User> users= FXCollections.observableArrayList();
        Query<User> query= HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
        List<User> list=query.list();
        for (User u:list) {
            if(lvlid==u.getLevelID())
                users.add(u);
        }
        for(User user:users) user.printUser();
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        return users;
    }
}

