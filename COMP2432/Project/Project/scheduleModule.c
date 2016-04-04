#include "header.h"

int IsAvailable(const Record* const rd);
void Book(const Record* const rd);
void Cancel(const Record* const rd);
void SortTime();
int SendAll(const Record* const cur, const char ch);
void Clean();
void ReSchedule();
void SortWeight();

unsigned char message_to_child[3];
unsigned char message_from_child[2];

int IsCompatible(const Record* a, const Record* b) {
  if (b->hour > (a->hour + a->duration)) return 1;
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

void SchdFcfs() {
  Clean();
  Record* cur = head;
  while (cur->next != NULL) {
    cur = cur->next;
    if (IsAvailable(cur)) {
      Book(cur);
      cur->accept = 1;
    } else cur->accept = 0;
  }
  PrintSchd(head);
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
  PrintSchd(head);
}

void SchdOpti() {
  Clean();
  SortTime();

  if (num_of_booking <= 1) {
    SchdFcfs();
    return;
  }

  int* last_compatible = malloc(num_of_booking * sizeof(int));
  int* last_in_compatible = malloc(num_of_booking * sizeof(int));
  int* optimal = malloc(num_of_booking * sizeof(int));
  int* decison = malloc(num_of_booking * sizeof(int));
  int* choice = malloc(num_of_booking * sizeof(int));

  int i = 0, j;
  for (i = 0; i < num_of_booking; i++) {
    last_in_compatible[i] = -1;
    last_compatible[i] = -1;
  }

  int found_compatible = 0;
  int found_in_compatible = 0;
  int result;

  Record* cur = head_s;
  Record* pin;
  i = 0;
  while(cur != NULL && cur->next != NULL) {
    j = i;
    cur = cur->next;
    found_compatible = 0;
    found_in_compatible = 0;
    pin = cur;
    while (pin->pre != NULL && i != 0 && !(found_compatible == 1 && found_in_compatible == 1)) {
      pin = pin->pre;
      j--;
      result = IsCompatible(pin, cur);
      if (found_compatible == 0 && result) {
        found_compatible = 1;
        last_compatible[i] = j;
      }
      if (found_in_compatible == 0 && !result) {
        found_in_compatible = 1;
        last_in_compatible[i] = j;
      }
    } ;
    i++;
  }

  cur = head_s->next;

  if (cur != NULL) {
    optimal[0] = cur->weight;
    decison[0] = 1;
  }

  for (i = 1; i < num_of_booking; i++) {
    if (num_of_booking <= 1) break;

    cur = cur->next;

    if (last_in_compatible[i] == -1 || (optimal[last_compatible[i]] + cur->weight >= optimal[last_in_compatible[i]])) {
      optimal[i] = optimal[last_compatible[i]] + cur->weight;
      decison[i] = 1;
    } else {
      optimal[i] = optimal[last_in_compatible[i]];
      decison[i] = 0;
    }
  }

  for (i = num_of_booking - 1; i >= 0;) {
    if (decison[i] == 1) {
      choice[i] = 1;
      i = last_compatible[i];
    } else {
      i = last_in_compatible[i];
    }
  }

  i = 0;
  cur = head_s;

  while (cur->next != NULL) {
    cur = cur->next;
    if (choice[i++] == 1) {
      Book(cur);
      cur->accept = 1;
    }
  } 

  SortWeight();
  ReSchedule();
}

void ReSchedule() {
  int* hour_backup = malloc(num_of_booking * sizeof(int));
  int i, j = 0;
  for (i = 0; i < num_of_booking; i++)
    hour_backup[i] = -1;

  Record* cur = head_s;

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

  PrintSchd(head_s);

  j = 0;
  cur = head_s;
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
  Record *cur, *cur_s;
  cur = head;
  cur_s = head_s;
  if (num_of_booking > 0) {
    while (cur->next != NULL) {
      cur = cur->next;
      cur_s = cur_s->next;
      cur->accept = 0;
      cur_s->accept = 0;
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

void SortTime() {
  Record *pre, *cur, *nex, *end;
  end = pre = cur = nex = head_s->next;
  int count = 0;
  while (end != NULL) {
    cur = end;
    pre = cur->pre;
    while (pre != NULL && cur->hour < pre->hour && pre->hour >= 0) {
      pre->pre->next = cur;
      if (cur->next != NULL) cur->next->pre = pre;
      cur->pre = pre->pre;
      pre->next = cur->next;
      cur->next = pre;
      pre->pre = cur;
      if (0 == count) end = end->next;
      count++;
      pre = cur->pre;
    }
    end = end->next;
  }
}

void SortWeight() {
  Record *pre, *cur, *nex, *end;
  end = pre = cur = nex = head_s->next;
  int count = 0;
  while (end != NULL) {
    cur = end;
    pre = cur->pre;
    while (pre != NULL && cur->weight > pre->weight && pre->weight >= 0) {
      pre->pre->next = cur;
      if (cur->next != NULL) cur->next->pre = pre;
      cur->pre = pre->pre;
      pre->next = cur->next;
      cur->next = pre;
      pre->pre = cur;
      if (0 == count) end = end->next;
      count++;
      pre = cur->pre;
    }
    end = end->next;
  }
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