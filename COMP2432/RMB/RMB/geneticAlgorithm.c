#include "header.h"

typedef struct Chrom             		// creating the chrom structure
{
  int* bit;
  int fit;
}chrom;                           	// now we have a chrom type that we can use

void *evpop(chrom popcurrent[4]);    	//defining the functions that we will use
int x(chrom popcurrent);
int y(int x);
void *pickchroms(chrom popcurrent[4]);
void *crossover(chrom popnext[4]);
void *mutation(chrom popnext[4]);
int Fitness(int* chrom);

void Genetic()								// the main function
{
  int num = 4;								// num is the no. of iterations
  int i, j;

  chrom popcurrent[4];                        	// we make 4 chromes of popcurrent 
  chrom popnext[4];                           	// we make 4 chromes of popnext

  evpop(popcurrent);                       	//initialise pop current

  for (i = 0; i < num; i++)                      	// loop num times
  {
    for (j = 0; j < 4; j++)
      popnext[j] = popcurrent[j];            	//copy popcurrent to popnext in order to adjust it
    pickchroms(popnext);                    	//pick best chromes
    crossover(popnext);                      	//cross over to get children chromes
    mutation(popnext);                       	//mutate with a low probability

    for (j = 0; j < 4; j++)
      popcurrent[j] = popnext[j];             	//copy the chromes of popnext to popcurrent 
  }                                           // loop back until no. of iterations is exceeded

}                                            	//end of main



void *evpop(chrom popcurrent[4])               	//takes a pointer to a chrom of 4 elements
{
  int i, j, value;
  Record* cur = head;
  for (j = 0; j < 4; j++)                          // loop of j to choose chromes from [0] to [3]
  {
    popcurrent[j].bit = malloc(num_of_booking * sizeof(int));
    cur = head;
    for (i = 0; i < num_of_booking; i++)            			// loop of i to choose the gen of the chrom from  [0] to [5]
    {
      cur = cur->next;
      popcurrent[j].bit[i] = cur->accept;  		// initialising the bit[i] of chrom[j] with random
    }   // end of for(i)

    popcurrent[j].fit = Fitness(popcurrent[j].bit);	// calcualte the fitness of chrom[j]

  }    // end of for(j)                                                              
  return(0);
}                              	//end of evpop function

int Fitness(int* chrom) {
  Record* cur = head;
  Record* cur2;
  for (int i = 0; i < num_of_booking; i++) {
    cur = cur->next;
    if (chrom[i] == 0)
      continue;
    cur2 = cur;
    for (int j = i + 1; j < num_of_booking; j++) {
      cur2 = cur2->next;
      if (chrom[j] == 0)
        continue;
      if (!IsCompatible(cur, cur2))
        return -1;      
    }
  }
  int fit = 0;
  for (int i = 0; i < num_of_booking; i++) {
    cur = cur->next;
    if (chrom[i] == 1)
      fit += cur->weight;
  }
  return fit;
}

void *pickchroms(chrom popcurrent[4])   	// pickchroms takes a pointer to array of chroms
{

  int i, j;
  chrom temp;                            	//temp chrome to use in sorting

  for (i = 0; i < 3; i++)               		//sorting the given set due to fitness
    for (j = 0; j < 3; j++) {
      if (popcurrent[j + 1].fit > popcurrent[j].fit) {
        temp = popcurrent[j + 1];
        popcurrent[j + 1] = popcurrent[j];
        popcurrent[j] = temp;

      }   // end of if
    }                // end of for loop

  return(0);
}                       //end of pick chromes function

void *crossover(chrom popnext[4]) // crossover function takes a pointer to array of chromes
{
  int random;
  int i;
  random = rand();                                  	//random cross over point
  random = ((random % num_of_booking) + 1);                    		// cross point should be between (1 - 5)
  for (i = 0; i < random; i++)                     	//crossing the bits below the cross point index
  {
    popnext[2].bit[i] = popnext[0].bit[i];        	//child 1 cross over
    popnext[3].bit[i] = popnext[1].bit[i];     	// child 2 cross over
  } // end of for

  for (i = random; i < num_of_booking; i++)                        	// crossing the bits beyond the cross point index
  {
    popnext[2].bit[i] = popnext[1].bit[i];     	// child 1 cross over
    popnext[3].bit[i] = popnext[0].bit[i];       	// chlid 2 cross over
  }    // end of for

  for (i = 0; i < 4; i++)
    popnext[i].fit = Fitness(popnext[i].bit);     	// calculating the fitness values for the new set
  return(0);
}                  // end crossover function

void *mutation(chrom popnext[4])   // mutation funtion given a pointer to array of chromes
{

  int random;
  int row, col, value;
  random = rand() % 50;                  //random value is between ( 0 - 49 )

  if (random == 25)    // Suppusiong Probability of mutation is 2 % 
  {
    col = rand() % num_of_booking;                           	// random column (gene) choosing 
    row = rand() % 4;                           	// random row ( chrome ) choosing

    if (popnext[row].bit[col] == 0)          	// invert the bit to 1 if it was 0
      popnext[row].bit[col] = 1;
    else if (popnext[row].bit[col] == 1)       	// invert the bit to 0 if it was 1
      popnext[row].bit[col] = 0;

    popnext[row].fit = Fitness(popnext[row].bit);   	// calculate the fitness for the mutated chrome
  }         	// end of if

  return(0);
}                       //end of mutation