#include "header.h"

char user[20];
int num = 0;
int timeSlotRecord[10];
extern int timeSet;
void printAccept(Record* rd);
void printReject(Record* rd);
int(*printTimeHelper(int a))[2];
void printTime(Record* rd);
void printType(Record* rd);
void printRequester(Record* rd);
void printDevice(Record* rd, int type);
void format(int num);

int printSchd(Record* rd) {
  //Accepted Part
  printf(" ____________________________________________________________________________ \n");
  printf("|                                                                            |\n");
  printf("|***Room Booking - ACCEPTED***                                               |\n");
  printf("|                                                                            |\n");
  printf("|Room_A has the following bookings:                                          |\n");
  printf("|                                                                            |\n");
  printf("|Date         Start   End     Type          Requester  Device                |\n");
  printf("|============================================================================|\n");
  Record* cur = rd;
  int rejectedA = 0;
  int rejectedB = 0;
  int total = 0;
  int i;
  char messageToChild[2];
  char messageFromChild[2];
  while (cur->next != NULL) {
    cur = cur->next;
    total++;
    if (cur->room_A == 1 && cur->accept == 1) {
      printAccept(cur);
    } else if (cur->room_A == 1)rejectedA++;
  }
  printf("|                                                                            |\n");
  printf("|Room_B has the following bookings:                                          |\n");
  printf("|                                                                            |\n");
  printf("|Date         Start   End     Type          Requester  Device                |\n");
  printf("|============================================================================|\n");
  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_B == 1 && cur->accept == 1) printAccept(cur);
    else if (cur->room_B == 1)rejectedB++;

  }
  printf("|                                                                            |\n");
  printf("|   -End-                                                                    |\n");
  printf("|============================================================================|\n");
  printf("|____________________________________________________________________________|\n");


  //Rejected Part
  printf(" ____________________________________________________________________________ \n");
  printf("|                                                                            |\n");
  printf("|***Room Booking - REJECTED***                                               |\n");
  printf("|                                                                            |\n");
  printf("|Room_A, there are %d bookings rejected.                                      ", rejectedA);
  format(rejectedA);
  printf("|\n");
  printf("|                                                                            |\n");
  printf("|Date         Start   End     Type          Device                           |\n");
  printf("|============================================================================|\n");
  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_A == 1 && cur->accept == 0) printReject(cur);

  }
  printf("|                                                                            |\n");
  printf("|Room_B, there are %d bookings rejected.                                      ", rejectedB);
  format(rejectedB);
  printf("|\n");
  printf("|                                                                            |\n");
  printf("|Date         Start   End     Type          Device                           |\n");
  printf("|============================================================================|\n");
  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_B == 1 && cur->accept == 0) printReject(cur);

  }
  printf("|                                                                            |\n");
  printf("|   -End-                                                                    |\n");
  printf("|============================================================================|\n");
  printf("|____________________________________________________________________________|\n");


  printf(" ____________________________________________________________________________ \n");
  printf("|                                                                            |\n");
  printf("|Performance:                                                                |\n");
  printf("|                                                                            |\n");
  printf("|Total Number of Bookings Received: %d (100%%)                                 ", total);
  format(total);
  printf("|\n");
  printf("|      Number of Bookings Assigned: %d (%d%%)                                   ", total - rejectedA - rejectedB, total == 0 ? 0 : (total - rejectedA - rejectedB) * 100 / total);
  format(total - rejectedA - rejectedB);
  format(total == 0 ? 0 : (total - rejectedA - rejectedB) * 100 / total);
  printf("|\n");
  printf("|      Number of Bookings Rejected: %d (%d%%)                                   ", rejectedA + rejectedB, total == 0 ? 0 : (rejectedA + rejectedB) * 100 / total);
  format(rejectedA + rejectedB);
  format(total == 0 ? 0 : (rejectedA + rejectedB) * 100 / total);
  printf("|\n");
  printf("|Utilization of Time Slot:                                                   |\n");
  printf("|                                                                            |\n");

  messageToChild[2] = 'T';
  for (i = 0; i < 10; i++) {
    timeSlotRecord[i] = Send(i, messageToChild);
  }
  printf("|      Room_A                  - %d%%                                          ", timeSlotRecord[0] * 100 / 126);
  format(timeSlotRecord[0] * 100 / 126);
  printf("|\n");

  printf("|      Room_B                  - %d%%                                          ", timeSlotRecord[1] * 100 / 126);
  format(timeSlotRecord[1] * 100 / 126);
  printf("|\n");

  printf("|      webcam_720p             - %d%%                                          ", timeSlotRecord[2] * 100 / 126);
  format(timeSlotRecord[2] * 100 / 126);
  printf("|\n");

  printf("|      webcam_1080p            - %d%%                                          ", timeSlotRecord[3] * 100 / 126);
  format(timeSlotRecord[3] * 100 / 126);
  printf("|\n");

  printf("|      monitor_50              - %d%%                                          ", timeSlotRecord[4] * 100 / 126);
  format(timeSlotRecord[4] * 100 / 126);
  printf("|\n");

  printf("|      monitor_75              - %d%%                                          ", timeSlotRecord[5] * 100 / 126);
  format(timeSlotRecord[5] * 100 / 126);
  printf("|\n");

  printf("|      projector_fhd           - %d%%                                          ", timeSlotRecord[6] * 100 / 126);
  format(timeSlotRecord[6] * 100 / 126);
  printf("|\n");

  printf("|      projector_xga           - %d%%                                          ", timeSlotRecord[7] * 100 / 126);
  format(timeSlotRecord[7] * 100 / 126);
  printf("|\n");

  printf("|      screen_100              - %d%%                                          ", timeSlotRecord[8] * 100 / 126);
  format(timeSlotRecord[8] * 100 / 126);
  printf("|\n");

  printf("|      screen_150              - %d%%                                          ", timeSlotRecord[9] * 100 / 126);
  format(timeSlotRecord[9] * 100 / 126);
  printf("|\n");

  printf("|____________________________________________________________________________|\n");
}

void printAccept(Record* rd) {
  printTime(rd);
  printType(rd);
  printRequester(rd);
  printDevice(rd, 1);
}

void printReject(Record* rd) {
  printTime(rd);
  printType(rd);
  printDevice(rd, 0);
}

int(*printTimeHelper(int a))[2]{
  int i;
int leap[13] = { 0,31,60,91,121,152,183,213,244,274,305,335,366 };
int temp = a / 9 - 13 + timeSet;
int(*monthday)[2];
monthday = malloc(2 * sizeof(int));
for (i = 1; i < 13; i++) {
  if (temp <= leap[i]) {
    (*monthday)[0] = i;
    break;
  }
}
(*monthday)[1] = temp - leap[i - 1];
return monthday;
}

void printTime(Record* rd) {
  char date[6];
  char start[3];
  char end[3];
  int(*day)[2];
  day = printTimeHelper(rd->hour);
  date[5] = '\0';
  start[2] = '\0';
  end[2] = '\0';
  date[0] = (*day)[0] / 10 + '0';
  date[1] = (*day)[0] % 10 + '0';
  date[2] = '-';
  date[3] = (*day)[1] / 10 + '0';
  date[4] = (*day)[1] % 10 + '0';
  start[0] = (rd->hour) % 9 == 0 ? '0' : '1';
  if (start[0] == '1') {
    start[1] = (rd->hour) % 9 - 1 + '0';
  } else {
    start[1] = '9';
  }
  end[0] = '1';
  end[1] = (rd->hour + rd->duration) % 9 - 1 + '0';
  printf("|2016-%s   %s:00   %s:00   ", date, start, end);
}

void printType(Record* rd) {
  char type[15];//14+1
  switch (rd->type) {
    case 0: strcpy(type, "Device        "); break;
    case 1: strcpy(type, "Meeting       "); break;
    case 2: strcpy(type, "Presentation  "); break;
    case 3: strcpy(type, "Conference    "); break;
  }
  printf("%s", type);
}

void printRequester(Record* rd) {
  char requester[] = "tenant_A   ";//11+1
  if (rd->tenant_B) {
    requester[7] = 'B';
  } else if (rd->tenant_C) {
    requester[7] = 'C';
  }
  printf("%s", requester);
}

void printDevice(Record* rd, int isAccepted) {//ugly
  int isFirst = 1;

  if (rd->webcam_720p) {
    if (!isFirst) {
      printf("|                                           ");
      if (isAccepted)
        printf("           ");
    } else
      isFirst = 0;
    printf("webcam_720p           ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
  if (rd->webcam_1080p) {
    if (!isFirst) {
      printf("|                                           ");
      if (isAccepted)
        printf("           ");
    } else
      isFirst = 0;
    printf("webcam_1080p          ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
  if (rd->monitor_50) {
    if (!isFirst) {
      printf("|                                           ");
      if (isAccepted)
        printf("           ");
    } else
      isFirst = 0;
    printf("monitor_50            ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
  if (rd->monitor_75) {
    if (!isFirst) {
      printf("|                                           ");
      if (isAccepted)
        printf("           ");
    } else
      isFirst = 0;
    printf("monitor_75            ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
  if (rd->projector_fhd) {
    if (!isFirst) {
      printf("|                                           ");
      if (isAccepted)
        printf("           ");
    } else
      isFirst = 0;
    printf("projector_fhd         ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
  if (rd->projector_xga) {
    if (!isFirst) {
      printf("|                                           ");
      if (isAccepted)
        printf("           ");
    } else
      isFirst = 0;
    printf("projector_xga         ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
  if (rd->screen_100) {
    if (!isFirst) {
      printf("|                                           ");
      if (isAccepted)
        printf("           ");
    } else
      isFirst = 0;
    printf("screen_100            ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
  if (rd->screen_150) {
    if (!isFirst) {
      printf("|                                           ");
      if (isAccepted)
        printf("           ");
    } else
      isFirst = 0;
    printf("screen_150            ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
  if (isFirst) {
    printf("-                     ");
    if (!isAccepted)
      printf("           |\n");
    else
      printf("|\n");
  }
}


void format(int num) {
  num /= 10;
  while (num > 0) {
    printf("\b");
    num /= 10;
  }
}
