#include "header.h"

Record* createRecordList(Record* rd) {
  if (rd != NULL) {
    rd->type = -1;
    rd->tenant_A = -1;
    rd->tenant_B = -1;
    rd->tenant_C = -1;
    rd->room_A = -1;
    rd->room_B = -1;
    rd->webcam_720p = -1;
    rd->webcam_1080p = -1;
    rd->monitor_50 = -1;
    rd->monitor_75 = -1;
    rd->projector_fhd = -1;
    rd->projector_xga = -1;
    rd->screen_100 = -1;
    rd->screen_150 = -1;
    rd->hour = -1;
    rd->duration = -1;
    rd->next = NULL;
    rd->pre = NULL;
    rd->accept = -1;
    rd->weight = -1;
    return rd;
  }
  return rd;
}

int addRecord(int tenant_A, int tenant_B, int tenant_C, int room_A, int room_B, int webcam_720p, int webcam_1080p, int monitor_50, int monitor_75, int projector_fhd, int projector_xga, int screen_100, int screen_150, int hour, int duration, int type) {
  if (NULL == head) return 0;

  numOfBooking++;

  Record* nex = head->next;
  Record* cur = head;
  Record* nexS = headS->next;
  Record* curS = headS;

  while (NULL != nex) {
    cur = nex;
    nex = nex->next;
    curS = nexS;
    nexS = nexS->next;
  }

  nex = (Record*) malloc(sizeof(Record));
  cur->next = nex;
  nexS = (Record*) malloc(sizeof(Record));
  curS->next = nexS;

  nex->type = type;
  nex->tenant_A = tenant_A;
  nex->tenant_B = tenant_B;
  nex->tenant_C = tenant_C;
  nex->room_A = room_A;
  nex->room_B = room_B;
  nex->webcam_720p = webcam_720p;
  nex->webcam_1080p = webcam_1080p;
  nex->monitor_50 = monitor_50;
  nex->monitor_75 = monitor_75;
  nex->projector_fhd = projector_fhd;
  nex->projector_xga = projector_xga;
  nex->screen_100 = screen_100;
  nex->screen_150 = screen_150;
  nex->hour = hour;
  nex->duration = duration;
  nex->pre = cur;
  nex->next = NULL;
  nex->accept = 0;

  nex->weight = 0;
  if (nex->room_A) nex->weight += duration;
  if (nex->room_B) nex->weight += duration;
  if (nex->webcam_720p) nex->weight += duration;
  if (nex->webcam_1080p) nex->weight += duration;
  if (nex->monitor_50) nex->weight += duration;
  if (nex->monitor_75) nex->weight += duration;
  if (nex->projector_fhd) nex->weight += duration;
  if (nex->projector_xga) nex->weight += duration;
  if (nex->screen_100) nex->weight += duration;
  if (nex->screen_150) nex->weight += duration;

  nexS->type = type;
  nexS->tenant_A = tenant_A;
  nexS->tenant_B = tenant_B;
  nexS->tenant_C = tenant_C;
  nexS->room_A = room_A;
  nexS->room_B = room_B;
  nexS->webcam_720p = webcam_720p;
  nexS->webcam_1080p = webcam_1080p;
  nexS->monitor_50 = monitor_50;
  nexS->monitor_75 = monitor_75;
  nexS->projector_fhd = projector_fhd;
  nexS->projector_xga = projector_xga;
  nexS->screen_100 = screen_100;
  nexS->screen_150 = screen_150;
  nexS->hour = hour;
  nexS->duration = duration;
  nexS->pre = curS;
  nexS->next = NULL;
  nexS->accept = 0;
  nexS->weight = nex->weight;

  return 1;
}

int Send(int i, char* message) {
  char ch = message[2];
  char messageFromChild[2];
  write(parentToChild[i][1], message, 3);
  read(childToParent[i][0], messageFromChild, 2);

  if (ch == 'Q') {
    if (messageFromChild[0] == 'O') {
      return 1;
    } else
      return 0;
  } else if (ch == 'T') {
    return (int) messageFromChild[0];
  }
  return 0;
}