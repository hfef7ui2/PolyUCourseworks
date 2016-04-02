#ifndef HEADER_H
#define HEADER_H

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <malloc.h>
#include <sys/wait.h>

//Record is to record the information of each booking
typedef struct Record {
  int type;//0 for device, 1 for meeting, 2 for presentation, 3 for conference
  int tenant_A;//child 10
  int tenant_B;//child 11
  int tenant_C;//child 12
  int room_A;//child 0
  int room_B;//child 1
  int webcam_720p;//child 2
  int webcam_1080p;//child 3
  int monitor_50;//child 4
  int monitor_75;//child 5
  int projector_fhd;//child 6
  int projector_xga;//chil 7
  int screen_100;//child 8
  int screen_150;//child 9
  int hour;//starting time
  int duration;//duration
  struct Record* next;
  struct Record* pre;
  int accept;//0 for no, 1 for yes
  int weight;//represent the revenue from this booking
} Record;

Record* head;//head of record list
Record* headS;//head of sorted record list
int** parentToChild;
int** childToParent;
int* timeSlot;
int numOfBooking;

void SchdFcfs();
void SchdOpti();
void SchdPrio();
Record* createRecordList(Record* rd);
int Send(int i, char* message);
void WaitForSchd(int childNumber);
int WaitForBooking();
int addRecord(int tenant_A, int tenant_B, int tenant_C, int room_A, int room_B, int webcam_720p, int webcam_1080p, int monitor_50, int monitor_75, int projector_fhd, int projector_xga, int screen_100, int screen_150, int hour, int duration, int type);
int printSchd(Record* rd);
int Send(int i, char* message);

#endif