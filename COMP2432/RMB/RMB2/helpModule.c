#include "header.h"

Record* createRecordList(Record* const rd) {
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

int AddRecord(const int tenant_A, const int tenant_B, const int tenant_C, const int room_A, const int room_B, const int webcam_720p, const int webcam_1080p, const int monitor_50, const int monitor_75, const int projector_fhd, const int projector_xga, const int screen_100, const int screen_150, const int hour, const int duration, const int type) {
  if (NULL == head) return 0;

  num_of_booking++;

  Record* nex = head->next;
  Record* cur = head;

  while (NULL != nex) {
    cur = nex;
    nex = nex->next;
  }

  nex = (Record*) malloc(sizeof(Record));
  cur->next = nex;

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

  return 1;
}

int Send(const int i, const char* const message_to_child) {
  char command_type = message_to_child[2];
  char message_from_child[2];
  write(parent_to_child[i][1], message_to_child, 3);
  read(child_to_parent[i][0], message_from_child, 2);

  if (command_type == 'Q') {
    if (message_from_child[0] == 'O') {
      return 1;
    } else
      return 0;
  } else if (command_type == 'T') {
    return (int) message_from_child[0];
  }
  return 0;
}