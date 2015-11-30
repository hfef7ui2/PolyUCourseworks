// ************************************************
// Name : Zheng Hongyi
// Student ID: 13104036D
// Date : 04/04/2015
// Program Name: Minor in Software Technology
// Description of the program:
// It is a C program to simulate a hotel front desk.
// The front desk provides defferent services including hotel management, residence service and other service
//*************************************************

//IMPORTANT: Please run the programe on Visual Studio

#define _CRT_SECURE_NO_WARNINGS

#include<stdio.h>
#include<malloc.h>
#include<memory.h>
#include<stdlib.h>
#include<string.h>

	typedef enum empty_or_occupied
	{
		EMPTY = 0,
		OCCUPIED = 1
	}empty_or_occupied;//to record the state of the room(empty or occupied)
	typedef enum single_or_twin
	{
		TWIN = 0,
		SINGLE = 1
	}single_or_twin;//to record the type of the 
	typedef enum search
	{
		CUR=0,
		PRE=1
	} search;//use enumeration to help the search function more clear
	typedef enum standard_or_luxury
	{
		STANDARD = 0,
		LUXURY = 1
	}standard_or_luxury;//to record the type of the room
	typedef enum phone_call_type
	{
		LOCAL = 5,
		LONG_DISTANCE = 6
	}phone_call_type;//to record the type if phone call

	//declaration of Room and Customer structure
	struct Room;
	struct Customer;

	typedef struct Room
	{
		enum empty_or_occupied room_state;
		enum standard_or_luxury room_type;
		struct Customer *customer;
		int	room_number;
		struct Room* next;
		single_or_twin bed_type;
		
	}Room;//definiton of Room structure
	typedef struct Customer
	{
		char* name;
		char* address;
		int id;
		int check_in_date;
		struct Room* room;
		int bill_dollar;
		int bill_cent;
		struct Customer* next;
		int drink;
		int club;
		int dine;
		int call_local;
		int call_long;
		int room_rate;
		int service_fee;
	}Customer;//definition of Customer structure

	int NUM_OF_ROOM_T[2] = { 1, 1 }, NUM_OF_CUS_T[2] = { 0, 0 };//to recored the number of room and customer in each type of room
	int NUM_OF_ROOM_B[2][2] = { { 1, 0 }, { 1, 0 } }, NUM_OF_CUS_B[2][2] = { { 0, 0 }, { 0,0}};//to record the number of room and customer in different type of bed according their type of room
	const int MAX_ROOM = 200;//the maximum number for room in the hotel
	Room room_s = { EMPTY, STANDARD, NULL, 101,NULL,TWIN };//the first standard room
	Room room_l = { EMPTY, LUXURY, NULL, 301,NULL,TWIN };//the first luxury room
	Room *first_room[2] = { &room_s, &room_l };//pointer to the first room of both types
	Room *last_room[2] = { &room_s, &room_l };//pointer to the last room of both types
	Customer *first_customer = NULL;//pointer to the first customer

	static Room *search_result_r[2] = { NULL, NULL };//store the room search result
	static Customer *search_result_c[2] = { NULL, NULL };//store the customer search result

	void show_welcome();//show to greeting to the customer
	void show_menu();//show the menu
	void build_new_room();//build new room
	void remove_room();//remove a existing room
	void check_in();//check in the customer
	Room* search_empty(const standard_or_luxury room_type, const single_or_twin bed_type);//search the empty room with certain room type and bed type
	Customer* add_new_customer(const char* name);//record the informaton for a new customer
	void check_out();//check out the customer
	void entertainment();//provide entertainment service
	void dine();//provide dine for the customer
	void drink();//provide drink service for the customer
	void enter_club();//provide club service for the customer
	void phone_call(const phone_call_type phone_type);//provide phone call service for customers
	void find_customer_by_customer(const char* name);//find a customer by a certain name
	void find_room_by_customer(const char* name);//find a room by the name of certain customer
	void find_room_by_room(const int room_number);//find a room by its room number
	void find_customer_by_room(const int room_number);//find a customer by a certain room number 
	void customers();//show the list of customer
	void lower_case(char*);//change the name of customer into lower case in order for the sorting
	void free_memory();//free dynamic memory

	int main()
	{
		int choice = 1;//temp variable to record the customer input and control the loop
		
		show_welcome();//show the welcoming information
		getchar();

		while (choice != 0)//loop the service until customer choose to stop
		{
			fflush(stdin);
			show_menu();//show the menu
			scanf("%d", &choice);
			
			switch (choice)//provide service according the customer's choice
			{
			case 1://build new room
				build_new_room();
				break;
			case 2://remove room
				remove_room();
				break;
			case 3://check in
				check_in();
				break;
			case 4://check out
				check_out();
				break;
			case 5://entertianment service
				entertainment();
				break;
			case 6://show customer list
				customers();
				break;
			default:
				printf("\nThere is no such sevice.");
				break;
			}
			printf("\n\n********SERVICE FINISHED, THANK YOU FOR CHOOSING OUR HOTEL********");
			fflush(stdin);
			printf("\nDo you want to quit the program?\nInput 0 to QUIT.  Input ANY OTHER CHARACTERS to order another service.\nInput:");
			scanf("%d", &choice);
		}
		printf("\n\n******THANK YOU FOR CHOOSING GBY HOTEL. MAY GOD BLESS YYQ******");
		free_memory();//free dynamic memory
		return 0;
	}

	//to show the welcome information
	void show_welcome()
	{
		printf("***************************************\n***               GOD               ***\n***              BLESS              ***\n***               YYQ               ***\n***              HOTEL              ***\n***************************************");
		printf("\nAddress: 89 Songyu Road, Haicang, Xiamen, Fujian, China.");
		printf("\n\nWelcome to GOD BLESS YYQ HOTEL, dear customer!");
		printf("\nHere is the front desk. What can I do for you?");
		printf("\nWe provide accomondation and other services.\nCurrently, we have %d standard room(s) and %d luxury room(s).", NUM_OF_ROOM_T[STANDARD], NUM_OF_ROOM_T[LUXURY]);
		printf("\n\n*****Please click ENTER to see the service menu*****");
		return;
	}

	//show the service menu
	void show_menu()
	{
		printf("\n***********SERVICE MENU***********");
		printf("\n***   1. Build new room        ***\n***   2. Remove room           ***\n***   3. Check-in              ***\n***   4. Check-out             ***\n***   5. Entertainment         ***\n***         meal               ***\n***         drink              ***\n***         club               ***\n***         phone call         ***\n***   6. Check customer list   ***\n**********************************\nChoice:");
		return;
	}

	//build new room
	void build_new_room()
	{
		int num,i;//num:record the number of rooms built; i:to control the loop
		standard_or_luxury room_type;//to record the room type
		single_or_twin bed_type;//to record the bed type
		Room *new_room;//to record the new built room

		printf("\n\n******BUILDING NEW ROOM SERVICE******");

		fflush(stdin);
		printf("\nPlease choose the room type.\n0.Standard room     1.Luxury room\nRoom type:");
		scanf("%d", &room_type);

		fflush(stdin);
		printf("\nPlease choose the bed type.\n0.Twin room     1.Single room\nBed type:");
		scanf("%d", &bed_type);

		if ((room_type != STANDARD&&room_type != LUXURY) || (bed_type != SINGLE&&bed_type != TWIN))
		{
			printf("\nSorry, there is no such type room.\n");
			return;
		}

		fflush(stdin);
		printf("\nHow many rooms do you want to build?\nNumber:");
		scanf("%d", &num);
		
		if ((NUM_OF_ROOM_T[STANDARD] + NUM_OF_ROOM_T[LUXURY] + num) > MAX_ROOM)
		{
			printf("\nThere is no space for this construction plan. \nThe maximum room number is %d",MAX_ROOM);
			return;
		}

		for (i = 0; i < num; ++i)
		{
			new_room = (Room*)malloc(sizeof(Room));

			if (NULL == new_room)
			{
				printf("Out of memory!\n");
				exit(1);
			}

			memset(new_room, 0, sizeof(Room));

			new_room->room_state = EMPTY;
			new_room->room_type = room_type;
			new_room->next = NULL;
			new_room->customer = NULL;
			new_room->bed_type = bed_type;
			new_room->room_number = last_room[room_type]->room_number + 1;

			last_room[room_type]->next = new_room;
			last_room[room_type] = new_room;
			++NUM_OF_ROOM_T[room_type];
			++NUM_OF_ROOM_B[room_type][bed_type];

			printf("\n\nA %s room was built. The ROOM NUMBER is %d.", (room_type == STANDARD ? "STANDARD" : "LUXURY"), new_room->room_number);
		}
		return;
	}

	//remove a room with certian room number
	void remove_room()
	{
		int room_number;//to record the room number

		fflush(stdin);
		printf("\nPlease enter the room number\nRoom number:");
		scanf("%d", &room_number);

		if (room_number == 101 || room_number == 301)
		{
			printf("\nThe foundamantal room can not be removed.");
			return;
		}

		find_room_by_room(room_number);

		if (search_result_r[CUR] == NULL)
		{
			printf("\nThere is no such room");
			return;
		}

		if (search_result_r[CUR]->room_state == EMPTY)
		{
			--NUM_OF_ROOM_T[search_result_r[CUR]->room_type];
			--NUM_OF_ROOM_B[search_result_r[CUR]->room_type][search_result_r[CUR]->bed_type];
			printf("\nThe room %d is removed.", search_result_r[CUR]->room_number);

			search_result_r[PRE]->next = search_result_r[CUR]->next;
			search_result_r[CUR]->next = NULL;
			free(search_result_r[CUR]);
			search_result_r[CUR] = NULL;
		}
		else
			printf("This room is occupied");
		return;
	}

	//check in service
	void check_in()
	{
		char name[20];//to record the customer name
		int control;//to control the loop
		standard_or_luxury room_type;//to record the type of room
		single_or_twin bed_type;//to record the type of bed
		Room* new_room;//to storage the search result of the empty room

		printf("\n\n******CHECK IN SERVICE******");
		fflush(stdin);
		printf("\nPlease enter you name\nName:");
		gets(name);
		lower_case(name);
				
		find_customer_by_customer(name);

		if (NULL!=search_result_c[CUR] )
		{
			printf("\nYou have already checked in.");
			return;
		}

		while (1)//this loop ends when break or return
		{
			fflush(stdin);
			printf("\n\nDo you want Standard or Luxury room?\n0.Standard     1.Luxury\nChoice:");
			scanf("\n%d", &room_type);

			fflush(stdin);
			printf("\n\nDo you want Single or Twin room?\n0.Twin     1.Single\nChoice:");
			scanf("\n%d", &bed_type);

			if ((room_type != STANDARD&&room_type != LUXURY) || (bed_type != SINGLE&&bed_type != TWIN))
			{
				printf("\nSorry. There is no such type room.");
				return;
			}

			if ((NUM_OF_CUS_T[room_type] < NUM_OF_ROOM_T[room_type]) && NUM_OF_CUS_B[room_type][bed_type] < NUM_OF_ROOM_B[room_type][bed_type])
			{
				new_room = search_empty(room_type, bed_type);

				new_room->customer = add_new_customer(name);
				new_room->customer->room = new_room;
				new_room->room_state = OCCUPIED;

				++NUM_OF_CUS_T[room_type];
				++NUM_OF_CUS_B[room_type][bed_type];

				printf("\n\nNow you have been checked-in. Your room number is: %d.", new_room->room_number);
				printf("\nHere is your room card");
				printf("\n******************************");
				printf("\n***        GBY HOTEL       ***");
				printf("\n***                        ***");
				printf("\n***      ROOM NO. %d      ***",new_room->room_number);
				printf("\n***                        ***");
				printf("\n******************************");

				break;
			}
			else
			{
				printf("\n\nThere is no vacancy for your choice. Do you want to choose another type?\n0.Yes   1.No\nChoice:");
				scanf("%d", &control);
				if (control == 0)
					continue;
				else
					break;
			}
		}
		return;
	}

	//search an empty room with certain room type and bed type
	Room* search_empty(const standard_or_luxury room_type, const single_or_twin bed_type)
	{
		Room* temp;//temp variable to travel the list of room

		temp = first_room[room_type];
		while ((temp->room_state != EMPTY || temp->bed_type != bed_type) && temp->next != NULL)
			temp = temp->next;
		if (temp->room_state == EMPTY)
			return temp;
		else
			return NULL;
	}

	//record the data of a new customer
	Customer* add_new_customer(const char *name)
	{
		Customer *cur = first_customer, *pre = NULL;//to travel the list of customers
		Customer *new_customer = (Customer*)malloc(sizeof(Customer));//to store the new customer

		if (NULL == new_customer)
		{
			printf("Out of memory!\n");
			exit(1);
		}
		
		memset(new_customer, 0, sizeof(Customer));

		new_customer->name = (char*)malloc(sizeof(char[20]));

		if (NULL == new_customer->name)
		{
			printf("Out of memory!\n");
			exit(1);
		}
		
		strcpy(new_customer->name,name);

		fflush(stdin);
		printf("\nPlease enter your address: ");
		new_customer->address = (char*)malloc(sizeof(char[50]));

		if (NULL == new_customer->address)
		{
			printf("Out of memory!\n");
			exit(1);
		}

		gets(new_customer->address);

		fflush(stdin);
		printf("\nPlease enter your ID or Passport number:");
		scanf("%d", &new_customer->id);

		while (1)//this loop ends when break or return
		{
			fflush(stdin);
			printf("\nPlease enter your check-in date(from 1 to 31): Day__\b\b");
			scanf("%d", &new_customer->check_in_date);

			if (new_customer->check_in_date < 1 || new_customer->check_in_date>31)
			{
				printf("\nThe check-in date should be between 1 and 31.\nPlease select another date.");
				continue;
			}
			break;
		}

		new_customer->bill_dollar = 0;
		new_customer->bill_cent = 0;
		new_customer->dine = 0;
		new_customer->drink = 0;
		new_customer->club = 0;
		new_customer->call_local = 0;
		new_customer->call_long = 0;

		//to allocate the customer into the linked-list according to alphabetical order
		if (NUM_OF_CUS_T[STANDARD] == 0 && NUM_OF_CUS_T[LUXURY] == 0)
		{
			first_customer = new_customer;
			new_customer->next = NULL;
		}
		else if (strcmp(first_customer->name, new_customer->name) > 0)
		{
			new_customer->next = first_customer;
			first_customer = new_customer;
		}
		else
		{
			while (cur != NULL && (strcmp(cur->name, new_customer->name)) <= 0)
			{
				pre = cur;
				cur = cur->next;
			}
			if (cur == NULL)
			{
				pre->next = new_customer;
				new_customer->next = NULL;
			}
			else
			{
				pre->next = new_customer;
				new_customer->next = cur;
			}
		}
		return new_customer;
	}

	//find a certain customer by a name
	void find_customer_by_customer(const char* name)
	{
		Customer *search[2] = { NULL, NULL };//to traval the linked list of customer

		if (NUM_OF_CUS_T[STANDARD] == 0 && NUM_OF_CUS_T[LUXURY] == 0)
		{
			search_result_c[CUR] = NULL;
			search_result_c[PRE] = NULL;
			return;
		}

		if (NUM_OF_CUS_T[STANDARD] + NUM_OF_CUS_T[LUXURY] == 1)
		{
			if (!strcmp(first_customer->name, name))
			{
				search_result_c[CUR] = first_customer;
				search_result_c[PRE] = NULL;
				return;
			}
			else
			{
				search_result_c[CUR] = NULL;
				search_result_c[PRE] = NULL;
				return;
			}
		}

		search[CUR] = first_customer;
		while (strcmp(search[CUR]->name, name) && search[CUR]->next != NULL)
		{
			search[PRE] = search[CUR];
			search[CUR] = search[CUR]->next;
		}
		if (!strcmp(search[CUR]->name, name))
		{
			search_result_c[CUR] = search[CUR];
			search_result_c[PRE] = search[PRE];
			return;
		}
		else
		{
			search_result_c[CUR] = NULL;
			search_result_c[PRE] = NULL;
			return;
		}
	}

	//find a room by a certain name of a customer
	void find_room_by_customer(const char* name)
	{
		find_customer_by_customer(name);

		if (search_result_c[CUR] == NULL)
		{
			search_result_r[CUR] = NULL;
			search_result_r[PRE] = NULL;
			return;
		}
		
		if (search_result_c[PRE] == NULL)
		{
			search_result_r[CUR] = search_result_c[CUR]->room;
			search_result_r[PRE] = NULL;
			return;
		}
		
		search_result_r[CUR] = search_result_c[CUR]->room;
		search_result_r[PRE] = search_result_c[PRE]->room;
		return;
		
	}

	//find a room by certain room number
	void find_room_by_room(const int room_number)
	{
		Room *search[2] = { NULL, NULL };//to tralvel the linked list of room

		if (NUM_OF_ROOM_T[STANDARD] == 1 && first_room[STANDARD]->room_number == room_number)
		{
			search_result_r[CUR] = first_room[STANDARD];
			search_result_r[PRE] = NULL;
			return;
		}

		search[CUR] = first_room[STANDARD];
		
		while (search[CUR]->room_number != room_number&&search[CUR]->next != NULL)
		{
			search[PRE] = search[CUR];
			search[CUR] = search[CUR]->next;
		}

		if (search[CUR]->room_number == room_number)
		{
			search_result_r[CUR] = search[CUR];
			search_result_r[PRE] = search[PRE];
			return;
		}

		if (NUM_OF_ROOM_T[LUXURY] == 1 && first_room[LUXURY]->room_number == room_number)
		{
			search_result_r[CUR] = first_room[LUXURY];
			search_result_r[PRE] = NULL;
			return;
		}

		search[CUR] = first_room[LUXURY];
		search[PRE] = NULL;
		
		while (search[CUR]->room_number != room_number&&search[CUR]->next != NULL)
		{
			search[PRE] = search[CUR];
			search[CUR] = search[CUR]->next;
		}		

		if (search[CUR]->room_number == room_number)
		{
			search_result_r[CUR] = search[CUR];
			search_result_r[PRE] = search[PRE];
			return;
		}

		search_result_r[CUR] = NULL;
		search_result_r[PRE] = NULL;
		return;
	}

	//to find a customer by a certain room number
	void find_customer_by_room(const int room_number)
	{
		find_room_by_room(room_number);

		if (search_result_r[CUR] == NULL)
		{
			search_result_c[CUR] = NULL;
			search_result_c[PRE] = NULL;
			return;
		}

		if (search_result_r[PRE] == NULL)
		{
			search_result_c[CUR] = search_result_r[CUR]->customer;
			search_result_c[PRE] = NULL;
			return;
		}
		
		search_result_c[CUR] = search_result_r[CUR]->customer;
		search_result_c[PRE] = search_result_r[PRE]->customer;
		return;
	}

	//check out service
	void check_out()
	{
		int check_out_date,room_number;//to record the check out date and room number

		printf("\n\n********CHECK OUT SERVICE********");

		fflush(stdin);
		printf("\nPlease enter the your room number\nRoom number:");
		scanf("%d", &room_number);
		
		find_room_by_room(room_number);
		find_customer_by_room(room_number);

		if (NULL ==search_result_c[CUR] )
		{
			printf("\nThere is no customer for this room.");
			return;
		}

		printf("\nDear %s, thank you for choosing our hotel.", search_result_c[CUR]->name);

		while (1)
		{
			fflush(stdin);
			printf("\nPlease enter your check-out date(from 1 to 31): Day__\b\b");
			scanf("%d", &check_out_date);
			if (search_result_c[CUR]->check_in_date >= check_out_date)
			{
				printf("\nThe check-out date should be after check-in date.\n");
				continue;
			}
			break;
		}

		search_result_c[CUR]->room_rate = 700 * (check_out_date - (search_result_c[CUR]->check_in_date));
		search_result_c[CUR]->service_fee = 70 * (check_out_date - (search_result_c[CUR]->check_in_date));
		search_result_c[CUR]->bill_dollar += (search_result_c[CUR]->service_fee + search_result_c[CUR]->room_rate);

		printf("\n\n******BILL******");
		printf("\n$%d dollar %d cent", search_result_c[CUR]->bill_dollar, search_result_c[CUR]->bill_cent);
		printf("\nHere is your detial receipt: ");
		
		printf("\nRoom fee charge: $%d. $700 each day.", search_result_c[CUR]->room_rate);
		printf("\nService fee charge: $%d. 10 percent of room fee.", search_result_c[CUR]->service_fee);
		if (search_result_c[CUR]->drink != 0)
			printf("\nDrink: %dunits, each for $90", search_result_c[CUR]->drink);
		if (search_result_c[CUR]->dine != 0)
			printf("\nDine: %dunits, each for $50", search_result_c[CUR]->dine);
		if (search_result_c[CUR]->club != 0)
			printf("\nClub: %dtimes, each for $50", search_result_c[CUR]->club);
		if (search_result_c[CUR]->call_local != 0)
			printf("\nLoca call: %d minutes, each for $0.5", search_result_c[CUR]->call_local);
		if (search_result_c[CUR]->call_long != 0)
			printf("\nLong distanct call: %d minutes, each for $5", search_result_c[CUR]->call_long);

		search_result_r[CUR]->room_state = EMPTY;
		search_result_r[CUR]->customer = NULL;

		--NUM_OF_CUS_T[search_result_r[CUR]->room_type];
		--NUM_OF_CUS_B[search_result_r[CUR]->room_type][search_result_r[CUR]->bed_type];

		if (search_result_c[PRE]!= NULL)
			search_result_c[PRE]->next = search_result_c[CUR]->next;
		else
			first_customer = search_result_c[CUR]->next;

		free(search_result_c[CUR]->name);
		free(search_result_c[CUR]->address);
		free(search_result_c[CUR]);

	}

	//provide entertainment service
	void entertainment()
	{
		char name[20];//to store the name of the customer
		int room_num, choice;//to record the room number and customer choice

		printf("\n\n******ENTERTAINMENT SERVICE******");

		fflush(stdin);
		printf("\nPlease enter the your name and room number\nName:");
		gets(name);
		lower_case(name);
		fflush(stdin);
		printf("\nRoom number:");
		scanf("%d", &room_num);
		
		find_customer_by_customer(name);
		find_room_by_customer(name);

		if (search_result_c[CUR] == NULL)//exam existing of the customer
		{
			printf("\nThere is no such customer.");
			return;
		}

		if (search_result_r[CUR]->room_number != room_num)//exam the customer provide the true room number
		{
			printf("\nYou are not occupying this room.");
			return;
		}

		fflush(stdin);
		printf("\nPlease choose a service\n1. Dine servic\n2. Drink service\n3. Club service\n4. Phone call service\nChoice:");
		scanf("%d", &choice);

		if (choice == 4)
		{
			printf("\nLocal or long distance?5. Local   6. Long distance\nChoice:");
			scanf("%d", &choice);
		}

		if (choice == 2 || choice == 3 || choice == 6)//exam the customer occupied a luxury if neccessary
		{
			if (search_result_r[CUR]->room_type == STANDARD)
			{
				printf("\n\nSorry, you occupy a standard room. You are not eligible to such service.");
				return;
			}
		}

		switch (choice)
		{
		case 1://dine service
			dine();
			break;
		case 2://drink service
			drink();
			break;
		case 3://club service
			enter_club();
			break;
		case 5://both phone call service
		case 6:
			phone_call((phone_call_type)choice);
		}
		return;
	}

	//dine service
	void dine()
	{
		int n;

		printf("\n\n******DINE SERVICE******");

		fflush(stdin);
		printf("\nHow many meals?\nNumber:");
		scanf("%d", &n);

		search_result_c[CUR]->dine += n;
		search_result_c[CUR]->bill_dollar = search_result_c[CUR]->bill_dollar + (n * 50);
		printf("\nHere are your meals. The total bill is %d dollar.", n * 50);
	}

	//drink service
	void drink()
	{
		int n;//to record the number of drinks

		printf("\n\n******DRINK SERVICE******");

		fflush(stdin);
		printf("\nHow many drinks?\nNumber:");
		scanf("%d",&n);

		search_result_c[CUR]->drink += n;
		(search_result_c[CUR]->bill_dollar) = (search_result_c[CUR]->bill_dollar) + (n * 90);
		printf("\nHere are your wines. The total bill is %d dollar.", n * 90);
	}

	//club service
	void enter_club()
	{
		printf("\n\n******CLUB SERVICE******");

		++(search_result_c[CUR]->club);
		search_result_c[CUR]->bill_dollar = search_result_c[CUR]->bill_dollar + 50;
		printf("\nWelcome to the club. The total bill is 50 dollar.");
	}

	//phone call service
	void phone_call(phone_call_type phone_type)
	{
		int n;//to record: number of minutes

		printf("\n\n******PHONE CALL SERVICE******");

		fflush(stdin);
		printf("\nHow many minutes?\nNumber:");
		scanf("%d", &n);

		if (phone_type == LOCAL)
		{
			search_result_c[CUR]->call_local += n;

			search_result_c[CUR]->bill_dollar += ((50 * n) / 100);
			search_result_c[CUR]->bill_cent += ((50 * n) % 100);

			printf("The total bill is %d dollar %d cent.", (50 * n) / 100, (50 * n) % 100);
			return;
		}
		else
		{
			search_result_c[CUR]->call_long += n;
			search_result_c[CUR]->bill_dollar += (5 * n);

			printf("The total bill is %d dollar.", n*5);
			return;
		}
	}

	//to whoe the customer list
	void customers()
	{
		Customer* cur = first_customer;//to travel the linked list of customer

		printf("\n\n******CUSTOMER LIST******");

		if (NUM_OF_CUS_T[STANDARD] + NUM_OF_CUS_T[LUXURY] == 0)
		{
			printf("\n\nThere is no customer now.");
			return;
		}
		while (cur != NULL)
		{
			printf("\n%s", cur->name);
			cur = cur->next;
		} 
		return;
	}

	//to change the name of customer into lower case
	void lower_case(char *word)
	{
		int i = 0;//to control the loop
		for (i = 0; i < 20;++i)
		{
			if (word[i] >= 'A' && word[i] <= 'Z')
			{
				word[i] = word[i] + 32;
			}
		}
		return;
	}

	//free dynamic memory
	void free_memory()
	{
		Room *cur_r = (first_room[STANDARD]->next), *next_r = NULL;//pointers to each current and previous room in order to free the memory one by one
		Customer *cur_c = first_customer, *next_c = NULL;//pointers to each current and previous customer in order to free the memory one by one
		while (NULL != cur_r)
		{
			next_r = cur_r->next;
			free(cur_r);
			cur_r = next_r;
		}

		cur_r = first_room[LUXURY]->next;
		next_r = NULL;
		while (cur_r != NULL)
		{
			next_r = cur_r->next;
			free(cur_r);
			cur_r = next_r;
		}

		while (cur_c != NULL)
		{
			next_c = cur_c->next;
			free(cur_c->name);
			free(cur_c->address);
			free(cur_c);
			cur_c = next_c;
		}
		printf("\n\n********ALL DYNAMIC MEMORY HAVE BEEN FREE********");
		return;
	}