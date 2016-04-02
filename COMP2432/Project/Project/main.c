#include "header.h"

int main() {
  printf("~~ WELCOME TO PolySME ~~\n");

  int childNumber, i, pid, status;

  //initialize some core variables
  numOfBooking = 0;
  head = (Record*) malloc(sizeof(Record));
  headS = (Record*) malloc(sizeof(Record));
  createRecordList(head);
  createRecordList(headS);
  timeSlot = malloc(243 * sizeof(int));
  parentToChild = malloc(12 * sizeof(int*));
  childToParent = malloc(12 * sizeof(int*));
  for (i = 0; i < 12; i++) {
    parentToChild[i] = malloc(2 * sizeof(int));
    childToParent[i] = malloc(2 * sizeof(int));
  }

  //create pipes and child process
  for (i = 0; i < 12; i++) {
    //create the pipe from parent to child
    if (pipe(parentToChild[i]) < 0) {
      printf("Pipe creation error\n");
      exit(1);
    }
    //create the pipe from child to parent
    if (pipe(childToParent[i]) < 0) {
      printf("Pipe creation error\n");
      exit(1);
    }

    fflush(0);
    pid = fork();

    if (pid == 0) {//child
      close(parentToChild[i][1]);
      close(childToParent[i][0]);
      childNumber = i;
      break;
    } else {//parent
      close(parentToChild[i][0]);
      close(childToParent[i][1]);
    }
  }

  if (pid == 0) {//child process wait the schedule orders
    WaitForSchd(childNumber);
    exit(0);
  } else {//parent process wait bookings
    WaitForBooking();
    for (i = 0; i < 12; i++) {
      wait(&status);
    }
    exit(0);
  }
}
