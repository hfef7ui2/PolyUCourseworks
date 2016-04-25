#include "header.h"

typedef struct Chrom {
  int* bit;
  int fitness;
}chrom;

void Evpop(chrom pop_current[4]);
void Pick(chrom pop_current[4]);
void Crossover(chrom pop_next[4]);
void Mutation(chrom pop_next[4]);
int Fitness(int* chrom);
void MoveChrom(chrom* des, chrom* src);

void Genetic() {
  int num = 100;
  int i, j, k;

  chrom pop_current[4];
  chrom pop_next[4];
  for (i = 0; i < 4; i++) {
    pop_next[i].bit = malloc(num_of_booking * sizeof(int));
  }

  Evpop(pop_current);

  for (i = 0; i < num; i++) {
    for (j = 0; j < 4; j++) {
      pop_next[j].fitness = pop_current[j].fitness;
      for (k = 0; k < num_of_booking; k++) {
        pop_next[j].bit[k] = pop_current[j].bit[k];
      }
    }

    Pick(pop_next);

    Crossover(pop_next);

    for (j = 0; j < 5; j++)
      Mutation(pop_next);


    for (j = 0; j < 4; j++) {
      pop_current[j].fitness = pop_next[j].fitness;
      for (k = 0; k < num_of_booking; k++) {
        pop_current[j].bit[k] = pop_next[j].bit[k];
      }
    }

  }
  Record* cur = head;
  for (i = 0; i < num_of_booking; i++) {
    cur = cur->next;
    if (pop_current[0].bit[i] == 1)
      Book(cur);
  }
}



void Evpop(chrom pop_current[4]) {
  int i, j, value;
  Record* cur = head;
  Clean();
  SchdFcfs(head);
  for (j = 0; j < 2; j++) {
    pop_current[j].bit = malloc(num_of_booking * sizeof(int));
    cur = head;
    for (i = 0; i < num_of_booking; i++) {
      cur = cur->next;
      pop_current[j].bit[i] = cur->accept;
    }
    pop_current[j].fitness = Fitness(pop_current[j].bit);
  }

  Clean();
  SchdPrio();
  for (j = 2; j < 4; j++) {
    pop_current[j].bit = malloc(num_of_booking * sizeof(int));
    cur = head;
    for (i = 0; i < num_of_booking; i++) {
      cur = cur->next;
      pop_current[j].bit[i] = cur->accept;
    }
    pop_current[j].fitness = Fitness(pop_current[j].bit);
  }
}

int Fitness(int* chrom) {
  Record* cur = head;
  Record* cur2;
  int i, j;
  for (i = 0; i < num_of_booking; i++) {
    cur = cur->next;
    if (chrom[i] == 0)
      continue;
    cur2 = cur;
    for (j = i + 1; j < num_of_booking; j++) {
      cur2 = cur2->next;
      if (chrom[j] == 0)
        continue;
      if (!IsCompatible(cur, cur2)) {
        return -1;
      }
    }
  }
  int fit = 0;
  cur = head;
  for (i = 0; i < num_of_booking; i++) {
    cur = cur->next;
    if (chrom[i] == 1)
      fit += cur->weight;
  }
  return fit;
}

void Pick(chrom pop_current[4]) {

  int i, j;
  chrom temp;
  temp.bit = malloc(num_of_booking * sizeof(int));

  for (i = 0; i < 3; i++)
    for (j = 0; j < 3; j++) {
      if (pop_current[j + 1].fitness > pop_current[j].fitness) {
        MoveChrom(&temp, &pop_current[j + 1]);
        MoveChrom(&pop_current[j + 1], &pop_current[j]);
        MoveChrom(&pop_current[j], &temp);
      }
    }

}

void MoveChrom(chrom* des, chrom* src) {
  int i;
  for (i = 0; i < num_of_booking; i++) {
    des->bit[i] = src->bit[i];
  }
  des->fitness = src->fitness;
}

void Crossover(chrom pop_next[4]) {
  int random;
  int i;

  do {
    random = rand();
    random = ((random % num_of_booking) + 1);
    for (i = 0; i < random; i++) {
      pop_next[2].bit[i] = pop_next[0].bit[i];
    }

    for (i = random; i < num_of_booking; i++) {
      pop_next[2].bit[i] = pop_next[1].bit[i];
    }
  } while (Fitness(pop_next[2].bit) < 0);

  do {
    random = rand();
    random = ((random % num_of_booking) + 1);
    for (i = 0; i < random; i++) {
      pop_next[3].bit[i] = pop_next[1].bit[i];
    }

    for (i = random; i < num_of_booking; i++) {
      pop_next[3].bit[i] = pop_next[0].bit[i];
    }
  } while (Fitness(pop_next[3].bit) < 0);

  for (i = 0; i < 4; i++)
    pop_next[i].fitness = Fitness(pop_next[i].bit);
}

void Mutation(chrom pop_next[4]) {

  int random;
  int row, col, value;
  int stop = 1;
  random = rand() % 50;

  if (random < 10) {
    do {
      stop = 1;
      col = rand() % num_of_booking;
      row = rand() % 4;

      int ori = pop_next[row].bit[col];

      if (pop_next[row].bit[col] == 0)
        pop_next[row].bit[col] = 1;
      else if (pop_next[row].bit[col] == 1)
        pop_next[row].bit[col] = 0;

      if (Fitness(pop_next[row].bit) < 0) {
        pop_next[row].bit[col] = ori;
        stop = 0;
      }
    } while (stop == 0);

    pop_next[row].fitness = Fitness(pop_next[row].bit);
  }
}