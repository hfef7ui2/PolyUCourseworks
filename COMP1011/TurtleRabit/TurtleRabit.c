// ************************************************
// Name : Zheng Hongyi
// Student ID: 13104036D
// Date : 09/03/2015
// Program Name: Minor in Software Technology
// Description of the program:
// It is a C program to simulate the rabbit and turtle race game.
// The team with highe total meters will be the winner.
//*************************************************


#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include <time.h>

void welcome(struct Rabbit* rabbit_player, struct Turtle* turtle_player);//show the greeting
void intro_rabbit(struct Rabbit* player);//self-introductino of each rabbit
void intro_turtle(struct Turtle* player);//self-introduction of each turtle
void sleep_or_not(struct Rabbit* player);//decide whether each running rabbit will fall sleep or keep running
void wake_or_not(struct Rabbit* player);//decide whether each slepping rabbit will wake or keep running
void run_rabbit(struct Rabbit* player);//make rabbits run
void crawl_turtle(struct Turtle* player);//make turtles crawl
void result_rabbit(struct Rabbit* player);//show running distance of rabbits
void result_turtle(struct Turtle* player);//show crawling distance of turtles
void declare_team_winner(struct Rabbit* team_rabbit, struct Turtle* team_turtle);//declare the team winner

enum condition{ WAKE = 0, SLEEP = 1 };//to record wether the rabbit is awake or asleep

//rabbit player
struct Rabbit
{
	char name[10];//player's name
	char sex[10];//player's sex
	int speed;//current speed
	int total_meters;//total meters run
	int running_time;//the time of running after waking
	int sleeping_time;//the time of sleeping after running
	enum condition sleep_or_not;//awake or asleep
};

//turtle player
struct Turtle
{
	char name[10];//player's name
	char sex[10];//player's sex
	int speed; //player's speed
	int total_meters;//total meters run
};

int main()
{
	int i = 0;//temporary variable for loop function
	int game_time = 0;//to record the time past
	char stop_or_not = 'n';//to control the stop time of the race
	srand((unsigned)time(NULL));
	struct Rabbit rabbit_player[2] = { { "Troy", "Male", 40, 0, 0, 0, WAKE }, { "Sally", "Female", 40, 0, 0, 0, WAKE } };//rabbit team
	struct Turtle turtle_player[2] = { { "Nate", "Male", 25, 0 }, { "Sharon", "Female", 25, 0 } };//turtle team
	welcome(rabbit_player, turtle_player);
	intro_rabbit(rabbit_player);
	intro_turtle(turtle_player);
	printf("\n\n*****Click ENTER to start the race*****");
	getchar();
	while (stop_or_not != 'y'&&stop_or_not != 'Y')
	{
		printf("\n\n********Minute %d begins********", game_time + 1);
		wake_or_not(rabbit_player);
		run_rabbit(rabbit_player);
		crawl_turtle(turtle_player);
		sleep_or_not(rabbit_player);
		++game_time;
		printf("\n*****Click Enter to continue*****");
		while ((stop_or_not = getchar()) != '\n'&& stop_or_not != EOF);
		printf("It is now %d minutes past.\nStop the Race (Type y or Y to stop; Click ENTER to continue)? ", game_time);
		stop_or_not = getchar();
	}
	printf("\n\n*** TIME IS UP and THE GAME IS OVER ***");
	printf("\nThe total time of the race is %d minutes", game_time);
	result_rabbit(rabbit_player);
	result_turtle(turtle_player);
	declare_team_winner(rabbit_player, turtle_player);
	printf("\n\n*****Let's sing up for the champions!*****");
	return 0;
}

//show the greeting
void welcome(struct Rabbit* rabbit_player, struct Turtle* turtle_player)
{
	printf("****** Welcome to the Turtle and Rabbit Race Game ******");
	printf("\nMembers in Rabbit Team: %s and %s", rabbit_player[0].name, rabbit_player[1].name);
	printf("\nMembers in Turtle Team: %s and %s", turtle_player[0].name, turtle_player[1].name);
	printf("\n\nThe Race starts at 1:00pm!");
	printf("\n%s, %s, %s, %s: Go!", rabbit_player[0].name, rabbit_player[1].name, turtle_player[0].name, turtle_player[1].name);
}


//self-introductino of each rabbit
void intro_rabbit(struct Rabbit* player)
{
	int i;//temporary variable for loop function
	for (i = 0; i < 2; ++i)
	{
		printf("\n\n******************");
		printf("\nRabbit %s: starts running at 1:00pm", player[i].name);
		printf("\nI am a %s rabbit.", player[i].sex);
		printf("\nI hate to run. I will feel more and more sleepy after running for 4 minutes.");
		printf("\nMy current speed is 40 Meters per minute. \nI will get slower each minute before I get sleep.");
		printf("\nAfter sleep, I will be refreshed and back to 40 Meters per minute.");
	}
}

//self-introductino of each turtle
void intro_turtle(struct Turtle* player)
{
	int i;//temporary variable for loop function
	for (i = 0; i < 2; ++i)
	{
		printf("\n\n******************");
		printf("\nTurtle %s: starts crawling at 1:00pm", player[i].name);
		printf("\nI am a %s turtle.", player[i].sex);
		printf("\nI know I am slow but I will try hard to beat the rabbits.");
		printf("\nMy current speed is 20 Meters per minute.");
	}
}

//decide whether each slepping rabbit will wake or keep running
void wake_or_not(struct Rabbit* player)
{
	int i;//temporary variable for loop function
	for (i = 0; i < 2; ++i)
	{
		if (player[i].sleep_or_not == WAKE)
			continue;
		else if (player[i].sleeping_time <1)
		{
			++(player[i].sleeping_time);
			continue;
		}
		else if (rand() % 10 <2)
		{
			printf("\nRabbit %s wakes up!", player[i].name);
			player[i].sleep_or_not = WAKE;
			player[i].sleeping_time = 0;
			player[i].speed = 40;
			continue;
		}
		else
			++(player[i].sleeping_time);
	}
}

//decide whether each running rabbit will fall sleep or keep running
void sleep_or_not(struct Rabbit* player)
{
	int i;//temporary variable for loop function
	for (i = 0; i < 2; ++i)
	{
		if ((player[i].running_time) < 4 || (player[i].sleep_or_not == SLEEP))
			continue;
		else if ((rand() % 10) > (13 - 2 * (player[i].running_time)))
		{
			printf("\nAfter running, Rabbit %s falls sleep! No one knows when will %s wake up", player[i].name, player[i].name);
			player[i].sleep_or_not = SLEEP;
			player[i].speed = 0;
			player[i].running_time = 0;
		}
	}
}

//make rabbits run
void run_rabbit(struct Rabbit* player)
{
	int i;//temporary variable for loop function
	for (i = 0; i < 2; ++i)
	{
		if (player[i].sleep_or_not == SLEEP)
		{
			printf("\nRabbit %s is in sweet sleep for %d minutes. It has run %d meters totally", player[i].name,player[i].sleeping_time,player[i].total_meters);
			continue;
		}
		else
		{
			player[i].total_meters += player[i].speed;
			player[i].speed -= 4;
			++player[i].running_time;
			printf("\nRabbit %s runs %d meters. It has run %d meters totally.", player[i].name, player[i].speed, player[i].total_meters);
		}
	}
}

//make turtles crawl
void crawl_turtle(struct Turtle* player)
{
	int i;//temporary variable for loop function
	for (i = 0; i < 2; ++i)
	{
		player[i].total_meters += player[i].speed;
		printf("\nTurtle %s crawls %d meters. It has crawled %d meters totally.", player[i].name, player[i].speed, player[i].total_meters);
	}
}

//show running distance of rabbits
void result_rabbit(struct Rabbit* player)
{
	int i;//temporary variable for loop function
	for (i = 0; i < 2; ++i)
	{
		printf("\n\nRabbit %s: I have run %d meters!", player[i].name, player[i].total_meters);
	}
}

//show crawling distance of turtles
void result_turtle(struct Turtle* player)
{
	int i;//temporary variable for loop function
	for (i = 0; i < 2; ++i)
	{
		printf("\n\nTurtle %s: I have run %d meters!", player[i].name, player[i].total_meters);
	}
}

//declare the team winner
void declare_team_winner(struct Rabbit* team_rabbit, struct Turtle* team_turtle)
{
	int result_r = team_rabbit[0].total_meters + team_rabbit[1].total_meters;
	int result_t = team_turtle[0].total_meters + team_turtle[1].total_meters;
	printf("\n\n*****TEAM WINNER*****");
	if (result_r > result_t)
		printf("\nThe winner team is Rabbit team! They have run %d meters!", result_r);
	else if (result_t>result_r)
		printf("\nThe winner team is Turtle team! They have crawled %d meters!", result_t);
	else
		printf("\nBoth team have the result of %d meters! There is no team winner.", result_r);
}


