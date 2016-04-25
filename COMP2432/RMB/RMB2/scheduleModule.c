#include "header.h"

int IsAvailable(const Record* const rd);
void Book(const Record* const rd);
void Cancel(const Record* const rd);
void ReSchedule();

unsigned char message_to_child[3];
unsigned char message_from_child[2];

int IsCompatible(const Record* a, const Record* b) {
  if (b->hour >= (a->hour + a->duration) || a->hour >= (b->hour + b->duration)) return 1;
  if (a->room_A && b->room_A) return 0;
  if (a->room_B && b->room_B) return 0;
  if (a->webcam_720p && b->webcam_720p) return 0;
  if (a->webcam_1080p && b->webcam_1080p)return 0;
  if (a->monitor_50 && b->monitor_50) return 0;
  if (a->monitor_75 && b->monitor_75) return 0;
  if (a->projector_fhd && b->projector_fhd) return 0;
  if (a->projector_xga && b->projector_xga) return 0;
  if (a->screen_100 && b->screen_100) return 0;
  if (a->screen_150 && b->screen_150) return 0;
  return 1;
}

void SchdFcfs(Record* rd) {
  Clean();
  Record* cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (IsAvailable(cur)) {
      Book(cur);
      cur->accept = 1;
    } else cur->accept = 0;
  }
}

void SchdPrio() {
  Clean();
  Record* cur;
  int i;
  for (i = 3; i >= 0; i--) {
    cur = head;
    while (cur != NULL) {
      do {
        cur = cur->next;
      } while (cur != NULL && cur->type != i);
      if (cur == NULL) break;
      if (IsAvailable(cur)) {
        Book(cur);
        cur->accept = 1;
      } else
        cur->accept = 0;
    }
  }
}

void SchdOpti() {
  if (num_of_booking > 0) 
    Genetic();
  ReSchedule();
}

void ReSchedule() {
  int* hour_backup = malloc(num_of_booking * sizeof(int));
  int i, j = 0;
  for (i = 0; i < num_of_booking; i++)
    hour_backup[i] = -1;

  Record* cur = head;

  int startHour = (cur->hour / 9) * 9;

  while (cur->next != NULL) {
    cur = cur->next;

    hour_backup[j] = cur->hour;
    if (cur->accept == 0) {
      for (i = hour_backup[j]; i < (startHour + 9 * 14); i++) {
        if (i / 9 == (i + cur->duration) / 9) {
          cur->hour = i;
          if (IsAvailable(cur)) {
            Book(cur);
            cur->accept = 1;
            goto jump;
          }
        }
      }
      for (i = hour_backup[j]; i >= startHour; i--) {
        if (i / 9 == (i + cur->duration) / 9) {
          cur->hour = i;
          if (IsAvailable(cur)) {
            Book(cur);
            cur->accept = 1;
            break;
          }
        }
      }
    }
  jump:
    j++;
  }

  PrintSchd(head, 3);

  j = 0;
  cur = head;
  while (cur->next != NULL) {
    cur = cur->next;
    cur->hour = hour_backup[j++];
  }
}

int IsAvailable(const Record* const rd) {
  return !SendAll(rd, 'Q');
}

void Book(const Record* const rd) {
  SendAll(rd, 'B');
}

void Cancel(const Record* const rd) {
  SendAll(rd, 'C');
}

void Clean() {
  Record *cur;
  cur = head;
  if (num_of_booking > 0) {
    while (cur->next != NULL) {
      cur = cur->next;
      cur->accept = 0;
    }
  }

  int i;
  for (i = 0; i < 10; i++) {
    Send(i, "00C");
  }
}

int SendAll(const Record* const cur, const char ch) {
  message_to_child[0] = (char) cur->hour;
  message_to_child[1] = (char) cur->duration;
  message_to_child[2] = ch;
  if (cur->room_A)
    if (Send(0, message_to_child)) return 1;
  if (cur->room_B)
    if (Send(1, message_to_child)) return 1;
  if (cur->webcam_720p)
    if (Send(2, message_to_child)) return 1;
  if (cur->webcam_1080p)
    if (Send(3, message_to_child)) return 1;
  if (cur->monitor_50)
    if (Send(4, message_to_child)) return 1;
  if (cur->monitor_75)
    if (Send(5, message_to_child)) return 1;
  if (cur->projector_fhd)
    if (Send(6, message_to_child)) return 1;
  if (cur->projector_xga)
    if (Send(7, message_to_child)) return 1;
  if (cur->screen_100)
    if (Send(8, message_to_child)) return 1;
  if (cur->screen_150)
    if (Send(9, message_to_child)) return 1;
  return 0;
}

//for child device process
void WaitForSchd(const int childNumber) {
  int i, hour, duration, count = 0;
  int input = 1;

  while (1) {
  jump:
    read(parent_to_child[childNumber][0], message_to_child, 3 * sizeof(int));

    hour = (int) message_to_child[0];
    duration = (int) message_to_child[1];

    if (message_to_child[2] == 'Q') {//Query whether certain time period is occupied or available
      for (i = hour; i < hour + duration; i++) {
        if (time_slot[i] == 1) {
          message_from_child[0] = 'O';//Response occupied
          write(child_to_parent[childNumber][1], message_from_child, 2);
          goto jump;
        }
      }
      message_from_child[0] = 'A';//Response available
      write(child_to_parent[childNumber][1], message_from_child, 2);
    } else if (message_to_child[2] == 'B') {//Mark a booking
      for (i = hour; i < hour + duration; i++) {
        time_slot[i] = 1;
      }
      write(child_to_parent[childNumber][1], message_from_child, 2);
    } else if (message_to_child[2] == 'C') {//Clean
      for (i = 0; i < 243; i++) {
        time_slot[i] = 0;
      }
      write(child_to_parent[childNumber][1], message_from_child, 2);
    } else if (message_to_child[2] == 'T') {//ask time slot
      count = 0;
      for (i = 0; i < 243; i++) {
        if (time_slot[i] == 1) count++;
      }
      message_from_child[0] = (char) count;
      write(child_to_parent[childNumber][1], message_from_child, 2);
    } else if (message_to_child[2] == '0') {//exit
      break;
    }
  }
}