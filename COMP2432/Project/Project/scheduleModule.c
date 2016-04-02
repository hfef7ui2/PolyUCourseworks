#include "header.h"

int IsAvailable(Record* rd);
void Book(Record* rd);
void Cancel(Record* rd);
void SortTime();
int BroadCast(Record* cur, char ch);
void Clean();
void ReSchedule();
void SortWeight();

unsigned char messageToChild[3];
unsigned char messageFromChild[2];

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
  printSchd(head);
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
      } else cur->accept = 0;
    }
  }
  printSchd(head);
}

void SchdOpti() {
  Clean();
  SortTime();
  int* lastCompatible = malloc(numOfBooking * sizeof(int));
  int* lastInCompatible = malloc(numOfBooking * sizeof(int));
  int* optimal = malloc(numOfBooking * sizeof(int));
  int* decison = malloc(numOfBooking * sizeof(int));
  int* choice = malloc(numOfBooking * sizeof(int));

  int i = 0, j;
  for (i = 0; i < numOfBooking; i++) {
    lastInCompatible[i] = -1;
    lastCompatible[i] = -1;
  }
  int foundCompatible = 0;
  int foundInCompatible = 0;
  int result;
  Record* cur = headS;
  Record* pin;
  i = 0;
  do {
    j = i;
    cur = cur->next;
    foundCompatible = 0;
    foundInCompatible = 0;
    pin = cur;
    do {
      if (i == 0) {
        break;
      }
      pin = pin->pre;
      j--;
      result = IsCompatible(pin, cur);
      if (foundCompatible == 0 && result) {
        foundCompatible = 1;
        lastCompatible[i] = j;
      }
      if (foundInCompatible == 0 && !result) {
        foundInCompatible = 1;
        lastInCompatible[i] = j;
      }
      if (foundCompatible == 1 && foundInCompatible == 1)
        break;
    } while (pin->pre != NULL);
    i++;
  } while (cur->next != NULL);

  cur = headS->next;

  if (cur != NULL) {
    optimal[0] = cur->weight;
    decison[0] = 1;
  }

  for (i = 1; i < numOfBooking; i++) {
    if (numOfBooking == 0) break;

    cur = cur->next;

    if (lastInCompatible[i] == -1 || (optimal[lastCompatible[i]] + cur->weight >= optimal[lastInCompatible[i]])) {
      optimal[i] = optimal[lastCompatible[i]] + cur->weight;
      decison[i] = 1;
    } else {
      optimal[i] = optimal[lastInCompatible[i]];
      decison[i] = 0;
    }
  }

  for (i = numOfBooking - 1; i >= 0;) {
    if (decison[i] == 1) {
      choice[i] = 1;
      i = lastCompatible[i];
    } else {
      i = lastInCompatible[i];
    }
  }

  i = 0;
  cur = headS;

  do {
    cur = cur->next;
    if (choice[i++] == 1) {
      Book(cur);
      cur->accept = 1;
    }
  } while (cur->next != NULL);
  SortWeight();
  ReSchedule();
}

void ReSchedule() {
  int* hourBackup = malloc(numOfBooking * sizeof(int));
  int i, j = 0;
  for (i = 0; i < numOfBooking; i++)
    hourBackup[i] = -1;
  if (numOfBooking < 2) {
    SchdFcfs();
    return;
  }
  Record* cur = headS->next;

  int startHour;
  startHour = (cur->hour / 9) * 9;
  do {
    hourBackup[j] = cur->hour;
    if (cur->accept == 0) {
      for (i = hourBackup[j]; i < (startHour + 9 * 14); i++) {
        if (i / 9 == (i + cur->duration) / 9) {
          cur->hour = i;
          if (IsAvailable(cur)) {
            Book(cur);
            cur->accept = 1;
            goto jump;
          }
        }
      }
      for (i = hourBackup[j]; i >= startHour; i--) {
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
    cur = cur->next;
  } while (cur != NULL);
  printSchd(headS);
  j = 0;
  cur = headS;
  do {
    cur = cur->next;
    cur->hour = hourBackup[j++];
  } while (cur->next != NULL);
}

int IsAvailable(Record* rd) {
  return !BroadCast(rd, 'Q');
}

void Book(Record* rd) {
  BroadCast(rd, 'M');
}

void Cancel(Record* rd) {
  BroadCast(rd, 'C');
}

void Clean() {
  messageToChild[2] = 'C';
  Record *cur, *curS;
  cur = head;
  curS = headS;
  if (numOfBooking > 0) {
    do {
      cur = cur->next;
      curS = curS->next;
      cur->accept = 0;
      curS->accept = 0;
    } while (cur->next != NULL);
  }

  int i;
  for (i = 0; i < 10; i++) {
    Send(i, messageToChild);
  }
}

int BroadCast(Record* cur, char ch) {
  messageToChild[0] = (char) cur->hour;
  messageToChild[1] = (char) cur->duration;
  messageToChild[2] = ch;
  if (cur->room_A)
    if (Send(0, messageToChild)) return 1;
  if (cur->room_B)
    if (Send(1, messageToChild)) return 1;
  if (cur->webcam_720p)
    if (Send(2, messageToChild)) return 1;
  if (cur->webcam_1080p)
    if (Send(3, messageToChild)) return 1;
  if (cur->monitor_50)
    if (Send(4, messageToChild)) return 1;
  if (cur->monitor_75)
    if (Send(5, messageToChild)) return 1;
  if (cur->projector_fhd)
    if (Send(6, messageToChild)) return 1;
  if (cur->projector_xga)
    if (Send(7, messageToChild)) return 1;
  if (cur->screen_100)
    if (Send(8, messageToChild)) return 1;
  if (cur->screen_150)
    if (Send(9, messageToChild)) return 1;
  return 0;
}

void SortTime() {
  Record *pre, *cur, *nex, *end;
  end = pre = cur = nex = headS->next;
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
  end = pre = cur = nex = headS->next;
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
void WaitForSchd(int childNumber) {
  int i, hour, duration, count = 0;
  int input = 1;

  while (1) {
  jump:
    read(parentToChild[childNumber][0], messageToChild, 3 * sizeof(int));

    hour = (int) messageToChild[0];
    duration = (int) messageToChild[1];
    if (messageToChild[2] == 'Q') {//Query whether certain time period is occupied or available
      for (i = hour; i < hour + duration; i++) {
        if (timeSlot[i] == 1) {
          messageFromChild[0] = 'O';//Response occupied
          write(childToParent[childNumber][1], messageFromChild, 2);
          goto jump;
        }
      }
      messageFromChild[0] = 'A';//Response available
      write(childToParent[childNumber][1], messageFromChild, 2);
    } else if (messageToChild[2] == 'M') {//Mark a booking
      for (i = hour; i < hour + duration; i++) {
        timeSlot[i] = 1;
      }
      write(childToParent[childNumber][1], messageFromChild, 2);
    } else if (messageToChild[2] == 'C') {//Clean
      for (i = 0; i < 243; i++) {
        timeSlot[i] = 0;
      }
      write(childToParent[childNumber][1], messageFromChild, 2);
    } else if (messageToChild[2] == 'T') {//ask time slot
      count = 0;
      for (i = 0; i < 243; i++) {
        if (timeSlot[i] == 1) count++;
      }
      messageFromChild[0] = (char) count;
      write(childToParent[childNumber][1], messageFromChild, 2);
    } else if (messageToChild[2] == '0') {//exit
      break;
    }
  }
}