#include "header.h"

char user[20];
int num = 0;
int time_slot_record[10];
extern int time_set;
void PrintAccept(Record* rd, FILE* fp);
void PrintReject(Record* rd, FILE* fp);
int(*PrintTimeHelper(int a))[2];
void PrintTime(Record* rd, FILE *fp);
void PrintType(Record* rd, FILE *fp);
void PrintRequester(Record* rd, FILE* fp);
void PrintDevice(Record* rd, int type, FILE* fp);
void Format(FILE* fp, int num);
void PrintSchdFile(Record* const rd, int i);

void PrintSchd(Record* const rd, int index) {
  Record* cur = rd;
  int rejected_a = 0;
  int rejected_b = 0;
  int rejected_d = 0;
  int total = 0;
  int i;
  char message_from_child[2];

  //Accepted Part
  printf(" ____________________________________________________________________________ \n");
  printf("|                                                                            |\n");
  printf("|***Room Booking - ACCEPTED***                                               |\n");
  printf("|                                                                            |\n");
  printf("|Room_A has the following bookings:                                          |\n");
  printf("|                                                                            |\n");
  printf("|Date         Start   End     Type          Requester  Device                |\n");
  printf("|============================================================================|\n");

  while (cur->next != NULL) {
    cur = cur->next;
    total++;
    if (cur->room_A == 1 && cur->accept == 1)
      PrintAccept(cur, stdout);
    else if (cur->room_A == 1)
      rejected_a++;
  }

  printf("|                                                                            |\n");
  printf("|Room_B has the following bookings:                                          |\n");
  printf("|                                                                            |\n");
  printf("|Date         Start   End     Type          Requester  Device                |\n");
  printf("|============================================================================|\n");

  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_B == 1 && cur->accept == 1)
      PrintAccept(cur, stdout);
    else if (cur->room_B == 1)
      rejected_b++;
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
  printf("|Room_A, there are %d bookings rejected.                                      ", rejected_a);
  Format(stdout, rejected_a);
  printf("|\n");
  printf("|                                                                            |\n");
  printf("|Date         Start   End     Type          Device                           |\n");
  printf("|============================================================================|\n");

  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_A == 1 && cur->accept == 0) PrintReject(cur, stdout);
  }

  printf("|                                                                            |\n");
  printf("|Room_B, there are %d bookings rejected.                                      ", rejected_b);
  Format(stdout, rejected_b);
  printf("|\n");
  printf("|                                                                            |\n");
  printf("|Date         Start   End     Type          Device                           |\n");
  printf("|============================================================================|\n");

  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_B == 1 && cur->accept == 0)
      PrintReject(cur, stdout);
  }

  printf("|                                                                            |\n");
  printf("|   -End-                                                                    |\n");
  printf("|============================================================================|\n");
  printf("|____________________________________________________________________________|\n");

  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->type == 0 && cur->accept == 0)
      rejected_d++;
  }

  //Performance part
  printf(" ____________________________________________________________________________ \n");
  printf("|                                                                            |\n");
  printf("|Performance:                                                                |\n");
  printf("|                                                                            |\n");
  printf("|Total Number of Bookings Received: %d (100.0%%)                               ", total);
  Format(stdout, total);
  printf("|\n");
  printf("|      Number of Bookings Assigned: %d (%.1f%%)                                 ", total - rejected_a - rejected_b - rejected_d, total == 0 ? 0.0 : (double) (total - rejected_a - rejected_b - rejected_d) * 100.0 / (double) total);
  Format(stdout, total - rejected_a - rejected_b - rejected_d);
  Format(stdout, total == 0 ? 0.0 : (double) (total - rejected_a - rejected_b - rejected_d) * 100.0 / (double) total);
  printf("|\n");
  printf("|      Number of Bookings Rejected: %d (%.1f%%)                                 ", rejected_a + rejected_b + rejected_d, total == 0 ? 0.0 : (double) (rejected_a + rejected_b + rejected_d) * 100.0 / (double) (total));
  Format(stdout, rejected_a + rejected_b + rejected_d);
  Format(stdout, total == 0 ? 0.0 : (double) (rejected_a + rejected_b + rejected_d) * 100.0 / (double) (total));
  printf("|\n");
  printf("|Utilization of Time Slot:                                                   |\n");
  printf("|                                                                            |\n");

  for (i = 0; i < 10; i++) {
    time_slot_record[i] = Send(i, "00T");
  }

  printf("|      Room_A                  - %.1f%%                                        ", (double) time_slot_record[0] * 100.0 / 126.0);
  Format(stdout, time_slot_record[0] * 100 / 126);
  printf("|\n");

  printf("|      Room_B                  - %.1f%%                                        ", (double) time_slot_record[1] * 100.0 / 126.0);
  Format(stdout, time_slot_record[1] * 100 / 126);
  printf("|\n");

  printf("|      webcam_720p             - %.1f%%                                        ", (double) time_slot_record[2] * 100.0 / 126.0);
  Format(stdout, time_slot_record[2] * 100 / 126);
  printf("|\n");

  printf("|      webcam_1080p            - %.1f%%                                        ", (double) time_slot_record[3] * 100.0 / 126.0);
  Format(stdout, time_slot_record[3] * 100 / 126);
  printf("|\n");

  printf("|      monitor_50              - %.1f%%                                        ", (double) time_slot_record[4] * 100.0 / 126.0);
  Format(stdout, time_slot_record[4] * 100 / 126);
  printf("|\n");

  printf("|      monitor_75              - %.1f%%                                        ", (double) time_slot_record[5] * 100.0 / 126.0);
  Format(stdout, time_slot_record[5] * 100 / 126);
  printf("|\n");

  printf("|      projector_fhd           - %.1f%%                                        ", (double) time_slot_record[6] * 100.0 / 126.0);
  Format(stdout, time_slot_record[6] * 100 / 126);
  printf("|\n");

  printf("|      projector_xga           - %.1f%%                                        ", (double) time_slot_record[7] * 100.0 / 126.0);
  Format(stdout, time_slot_record[7] * 100 / 126);
  printf("|\n");

  printf("|      screen_100              - %.1f%%                                        ", (double) time_slot_record[8] * 100.0 / 126.0);
  Format(stdout, time_slot_record[8] * 100 / 126);
  printf("|\n");

  printf("|      screen_150              - %.1f%%                                        ", (double) time_slot_record[9] * 100.0 / 126.0);
  Format(stdout, time_slot_record[9] * 100 / 126);
  printf("|\n");

  printf("|____________________________________________________________________________|\n");
  PrintSchdFile(rd, index);
}

void PrintSchdFile(Record* const rd, int index) {
  FILE *fp;
  if (index == 1) {
    fp = fopen("FCFS_Schd.dat", "a");
  }
  else if (index == 2) {
    fp = fopen("PRIO_Schd.dat", "a");
  }
  else {
    fp = fopen("OPTI_Schd.dat", "a");
  }
  Record* cur = rd;
  int rejected_a = 0;
  int rejected_b = 0;
  int rejected_d = 0;
  int total = 0;
  int i;
  char message_from_child[2];

  //Accepted Part
  fprintf(fp, " ____________________________________________________________________________ \n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|***Room Booking - ACCEPTED***                                               |\n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Room_A has the following bookings:                                          |\n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Date         Start   End     Type          Requester  Device                |\n");
  fprintf(fp, "|============================================================================|\n");

  while (cur->next != NULL) {
    cur = cur->next;
    total++;
    if (cur->room_A == 1 && cur->accept == 1)
      PrintAccept(cur, fp);
    else if (cur->room_A == 1)
      rejected_a++;
  }

  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Room_B has the following bookings:                                          |\n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Date         Start   End     Type          Requester  Device                |\n");
  fprintf(fp, "|============================================================================|\n");

  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_B == 1 && cur->accept == 1)
      PrintAccept(cur, fp);
    else if (cur->room_B == 1)
      rejected_b++;
  }

  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|   -End-                                                                    |\n");
  fprintf(fp, "|============================================================================|\n");
  fprintf(fp, "|____________________________________________________________________________|\n");

  //Rejected Part
  fprintf(fp, " ____________________________________________________________________________ \n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|***Room Booking - REJECTED***                                               |\n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Room_A, there are %d bookings rejected.                                      ", rejected_a);
  Format(fp, rejected_a);
  fprintf(fp, "|\n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Date         Start   End     Type          Device                           |\n");
  fprintf(fp, "|============================================================================|\n");

  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_A == 1 && cur->accept == 0) PrintReject(cur, fp);
  }

  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Room_B, there are %d bookings rejected.                                      ", rejected_b);
  Format(fp, rejected_b);
  fprintf(fp, "|\n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Date         Start   End     Type          Device                           |\n");
  fprintf(fp, "|============================================================================|\n");

  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->room_B == 1 && cur->accept == 0)
      PrintReject(cur, fp);
  }

  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|   -End-                                                                    |\n");
  fprintf(fp, "|============================================================================|\n");
  fprintf(fp, "|____________________________________________________________________________|\n");

  cur = rd;
  while (cur->next != NULL) {
    cur = cur->next;
    if (cur->type == 0 && cur->accept == 0)
      rejected_d++;
  }

  //Performance part
  fprintf(fp, " ____________________________________________________________________________ \n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Performance:                                                                |\n");
  fprintf(fp, "|                                                                            |\n");
  fprintf(fp, "|Total Number of Bookings Received: %d (100.0%%)                               ", total);
  Format(fp, total);
  fprintf(fp, "|\n");
  fprintf(fp, "|      Number of Bookings Assigned: %d (%.1f%%)                                 ", total - rejected_a - rejected_b - rejected_d, total == 0 ? 0.0 : (double) (total - rejected_a - rejected_b - rejected_d) * 100.0 / (double) total);
  Format(fp, total - rejected_a - rejected_b - rejected_d);
  Format(fp, total == 0 ? 0.0 : (double) (total - rejected_a - rejected_b - rejected_d) * 100.0 / (double) total);
  fprintf(fp, "|\n");
  fprintf(fp, "|      Number of Bookings Rejected: %d (%.1f%%)                                 ", rejected_a + rejected_b + rejected_d, total == 0 ? 0.0 : (double) (rejected_a + rejected_b + rejected_d) * 100.0 / (double) (total));
  Format(fp, rejected_a + rejected_b + rejected_d);
  Format(fp, total == 0 ? 0.0 : (double) (rejected_a + rejected_b + rejected_d) * 100.0 / (double) (total));
  fprintf(fp, "|\n");
  fprintf(fp, "|Utilization of Time Slot:                                                   |\n");
  fprintf(fp, "|                                                                            |\n");

  for (i = 0; i < 10; i++) {
    time_slot_record[i] = Send(i, "00T");
  }

  fprintf(fp, "|      Room_A                  - %.1f%%                                        ", (double) time_slot_record[0] * 100.0 / 126.0);
  Format(fp,time_slot_record[0] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      Room_B                  - %.1f%%                                        ", (double) time_slot_record[1] * 100.0 / 126.0);
  Format(fp, time_slot_record[1] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      webcam_720p             - %.1f%%                                        ", (double) time_slot_record[2] * 100.0 / 126.0);
  Format(fp, time_slot_record[2] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      webcam_1080p            - %.1f%%                                        ", (double) time_slot_record[3] * 100.0 / 126.0);
  Format(fp, time_slot_record[3] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      monitor_50              - %.1f%%                                        ", (double) time_slot_record[4] * 100.0 / 126.0);
  Format(fp, time_slot_record[4] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      monitor_75              - %.1f%%                                        ", (double) time_slot_record[5] * 100.0 / 126.0);
  Format(fp, time_slot_record[5] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      projector_fhd           - %.1f%%                                        ", (double) time_slot_record[6] * 100.0 / 126.0);
  Format(fp, time_slot_record[6] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      projector_xga           - %.1f%%                                        ", (double) time_slot_record[7] * 100.0 / 126.0);
  Format(fp, time_slot_record[7] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      screen_100              - %.1f%%                                        ", (double) time_slot_record[8] * 100.0 / 126.0);
  Format(fp, time_slot_record[8] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|      screen_150              - %.1f%%                                        ", (double) time_slot_record[9] * 100.0 / 126.0);
  Format(fp, time_slot_record[9] * 100 / 126);
  fprintf(fp, "|\n");

  fprintf(fp, "|____________________________________________________________________________|\n");
}


void PrintAccept(Record* rd, FILE* fp) {
  PrintTime(rd, fp);
  PrintType(rd, fp);
  PrintRequester(rd, fp);
  PrintDevice(rd, 1, fp);
}

void PrintReject(Record* rd, FILE* fp) {
  PrintTime(rd, fp);
  PrintType(rd, fp);
  PrintDevice(rd, 0, fp);
}

int(*PrintTimeHelper(int a))[2]{
  int i;
int leap[13] = { 0,31,60,91,121,152,183,213,244,274,305,335,366 };
int temp = a / 9 - 13 + time_set;
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

void PrintTime(Record* rd, FILE* fp) {
  char date[6];
  char start[3];
  char end[3];
  int(*day)[2];
  day = PrintTimeHelper(rd->hour);
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
  if (end[1] < '0')end[1] = '8';
  fprintf(fp, "|2016-%s   %s:00   %s:00   ", date, start, end);
}

void PrintType(Record* rd, FILE* fp) {
  char type[15];//14+1
  switch (rd->type) {
    case 0: strcpy(type, "Device        "); break;
    case 1: strcpy(type, "Meeting       "); break;
    case 2: strcpy(type, "Presentation  "); break;
    case 3: strcpy(type, "Conference    "); break;
  }
  fprintf(fp, "%s", type);
}

void PrintRequester(Record* rd, FILE* fp) {
  char requester[] = "tenant_A   ";//11+1
  if (rd->tenant_B) {
    requester[7] = 'B';
  } else if (rd->tenant_C) {
    requester[7] = 'C';
  }
  fprintf(fp, "%s", requester);
}

void PrintDevice(Record* rd, int isAccepted, FILE* fp) {//ugly
  int isFirst = 1;

  if (rd->webcam_720p) {
    if (!isFirst) {
      fprintf(fp, "|                                           ");
      if (isAccepted)
        fprintf(fp, "           ");
    } else
      isFirst = 0;
    fprintf(fp, "webcam_720p           ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
  if (rd->webcam_1080p) {
    if (!isFirst) {
      fprintf(fp, "|                                           ");
      if (isAccepted)
        fprintf(fp, "           ");
    } else
      isFirst = 0;
    fprintf(fp, "webcam_1080p          ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
  if (rd->monitor_50) {
    if (!isFirst) {
      fprintf(fp, "|                                           ");
      if (isAccepted)
        fprintf(fp, "           ");
    } else
      isFirst = 0;
    fprintf(fp, "monitor_50            ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
  if (rd->monitor_75) {
    if (!isFirst) {
      fprintf(fp, "|                                           ");
      if (isAccepted)
        fprintf(fp, "           ");
    } else
      isFirst = 0;
    fprintf(fp, "monitor_75            ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
  if (rd->projector_fhd) {
    if (!isFirst) {
      fprintf(fp, "|                                           ");
      if (isAccepted)
        fprintf(fp, "           ");
    } else
      isFirst = 0;
    fprintf(fp, "projector_fhd         ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
  if (rd->projector_xga) {
    if (!isFirst) {
      fprintf(fp, "|                                           ");
      if (isAccepted)
        fprintf(fp, "           ");
    } else
      isFirst = 0;
    fprintf(fp, "projector_xga         ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
  if (rd->screen_100) {
    if (!isFirst) {
      fprintf(fp, "|                                           ");
      if (isAccepted)
        fprintf(fp, "           ");
    } else
      isFirst = 0;
    fprintf(fp, "screen_100            ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
  if (rd->screen_150) {
    if (!isFirst) {
      fprintf(fp, "|                                           ");
      if (isAccepted)
        fprintf(fp, "           ");
    } else
      isFirst = 0;
    fprintf(fp, "screen_150            ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
  if (isFirst) {
    fprintf(fp, "-                     ");
    if (!isAccepted)
      fprintf(fp, "           |\n");
    else
      fprintf(fp, "|\n");
  }
}


void Format(FILE* fp, int num) {
  num /= 10;
  while (num > 0) {
    fprintf(fp, "\b");
    num /= 10;
  }
}