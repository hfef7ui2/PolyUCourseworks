#include "header.h"

int main() {
  printf("~~ WELCOME TO PolySME ~~\n");

  int child_number = 0, i, pid, status;

  //initialize some core variables
  num_of_booking = 0;
  head = (Record*) malloc(sizeof(Record));
  head_s = (Record*) malloc(sizeof(Record));
  createRecordList(head);
  createRecordList(head_s);
  time_slot = malloc(243 * sizeof(int));
  parent_to_child = malloc(12 * sizeof(int*));
  child_to_parent = malloc(12 * sizeof(int*));
  for (i = 0; i < 12; i++) {
    parent_to_child[i] = malloc(2 * sizeof(int));
    child_to_parent[i] = malloc(2 * sizeof(int));
  }

  //create pipes and child process
  for (i = 0; i < 12; i++) {
    //create the pipe from parent to child
    if (pipe(parent_to_child[i]) < 0) {
      printf("Pipe creation error\n");
      exit(1);
    }
    //create the pipe from child to parent
    if (pipe(child_to_parent[i]) < 0) {
      printf("Pipe creation error\n");
      exit(1);
    }

    fflush(0);
    pid = fork();

    if (pid == 0) {//child
      close(parent_to_child[i][1]);
      close(child_to_parent[i][0]);
      child_number = i;
      break;
    } else {//parent
      close(parent_to_child[i][0]);
      close(child_to_parent[i][1]);
    }
  }

  if (pid == 0) {//child process wait the schedule orders
    WaitForSchd(child_number);
    exit(0);
  } else {//parent process wait bookings
    WaitForBooking();
    for (i = 0; i < 12; i++) {
      wait(&status);
    }
    exit(0);
  }
}
