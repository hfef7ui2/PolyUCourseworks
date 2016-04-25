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
int** parent_to_child;
int** child_to_parent;
int* time_slot;
int num_of_booking;

void SchdFcfs(Record* rd);
void SchdOpti();
void SchdPrio();
Record* createRecordList(Record* const rd);
int Send(const int i, const char* const message);
void WaitForSchd(const int child_number);
int WaitForBooking();
int AddRecord(const int tenant_A, const int tenant_B, const int tenant_C, const int room_A, const int room_B, const int webcam_720p, const int webcam_1080p, const int monitor_50, const int monitor_75, const int projector_fhd, const int projector_xga, const int screen_100, const int screen_150, const int hour, const int duration, const int type);
void PrintSchd(Record* const rd, int i);
int IsCompatible(const Record* a, const Record* b);
void Genetic();
int SendAll(const Record* const cur, const char ch);
void Book(const Record* const rd);
void Clean();

#endif