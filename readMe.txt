1) Script for creating "Games" Table (Sokoban Database should be created before):


use Sokoban

create table Games (
GameID INT IDENTITY(1,1),
LevelID INT default 0,
FirstName NVARCHAR(20),
LastName NVARCHAR(20),
FinishTime INT default 0,
Steps INT default 0,
PRIMARY KEY (GameID)
);

2) Script for Testing Leaderboards:

use Sokoban

insert into Games
values ('4','Gilad','Lekner','17','92'), ('2','May','Frish','2','5'), ('1','Gilad','Lekner','5','5'),('3','Zub','Dub','67','92'),('3','Sharon','Gueta','68','93');


3) Search Options:
	 1.Search for a Player(First Name+Last Name, example:"Gilad Lekner")
	 2.Search for a level (example: "level1")