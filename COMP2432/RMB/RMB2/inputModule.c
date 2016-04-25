#include "header.h"

int ConvertDayNumber(const int a, const int b);
void ConvertTime(const char a[], const char b[]);
void ConvertTenant(const char a[]);
void ConvertDuration(const char a[]);
void ConvertEquipment(const char a[]);
int coreInput(FILE *stream);

int para[15];
int time_set = 0;//The # of the day which first request is on, default 0.
int wrong = 0;
int ConvertDayNumber(const int a, const int b) {
  int temp = 0;
  switch (a) {
    case 12:temp += 30;
    case 11:temp += 31;
    case 10:temp += 30;
    case 9:temp += 31;
    case 8:temp += 31;
    case 7:temp += 30;
    case 6:temp += 31;
    case 5:temp += 30;
    case 4:temp += 31;
    case 3:temp += 29;
    case 2:temp += 31;
    case 1:temp += b;
  }
  return temp;
}
void errorInput() {
  printf("Wrong input.\n");
}
void ConvertTime(const char a[], const char b[]) {
  int month = (a[5] - '0') * 10 + (a[6] - '0');//month
  int day = (a[8] - '0') * 10 + (a[9] - '0');//day
  if (!time_set) {
    time_set = ConvertDayNumber(month, day);
  }
  para[13] = (ConvertDayNumber(month, day) - time_set + 13) * 9 + (b[0] - '0') * 10 + (b[1] - '0') - 9;
  int btemp = (b[0] - '0') * 10 + (b[1] - '0');
  int bbtemp = ConvertDayNumber(month, day) - time_set + 13;
  if (btemp < 9 || btemp >= 18 || bbtemp < 0 || bbtemp > 26) {
    //printf("btemp=%d\n",btemp);
    wrong = 1;
  }
  if (wrong) errorInput();
}

void ConvertTenant(const char a[]) {
  if (strcmp(a, "tenant_A") == 0) {
    para[0] = 1;
  } else if (strcmp(a, "tenant_B") == 0) {
    para[1] = 1;
  } else if (strcmp(a, "tenant_C") == 0) {
    para[2] = 1;
  }
}

void ConvertDuration(const char a[]) {
  if (a[1] != '.' || a[2] != '0') {
    //printf("%c %c\n", a[1], a[2]);
    wrong = 1;
  }
  para[14] = (a[0] - '0');
  if ((para[13] / 9 != (para[13] + para[14]) / 9) && ((para[13] + para[14]) % 9 != 0)) {
    //printf("13= %d, 14= %d\n",para[13],para[14]); 
    wrong = 1;
  }
  if (wrong) {
    errorInput();
  }
}



void ConvertEquipment(const char a[]) {
  if (strcmp(a, "room_A") == 0) {
    para[3] = 1;
  } else if (strcmp(a, "room_B") == 0) {
    para[4] = 1;
  } else if (strcmp(a, "webcam_720p") == 0) {
    para[5] = 1;
  } else if (strcmp(a, "webcam_1080p") == 0) {
    para[6] = 1;
  } else if (strcmp(a, "monitor_50") == 0) {
    para[7] = 1;
  } else if (strcmp(a, "monitor_75") == 0) {
    para[8] = 1;
  } else if (strcmp(a, "projector_fhd") == 0) {
    para[9] = 1;
  } else if (strcmp(a, "projector_xga") == 0) {
    para[10] = 1;
  } else if (strcmp(a, "screen_100") == 0) {
    para[11] = 1;
  } else if (strcmp(a, "screen_150") == 0) {
    para[12] = 1;
  }
}

int coreInput(FILE *stream) {
  int isPrint = 0;//whether command is print

  int i;
  int type = -1;
  char t[20];
  char *require[13];
  char temp[256];
  wrong = 0;

  for (i = 0; i < 15; i++) {
    para[i] = 0;
  }

  for (i = 0; i < 15; i++) {
    require[i] = "";
  }

  fscanf(stream, "%s", t);

  switch (t[3]) {
    case 'M'://addMeeting
      type = 1;
      break;
    case 'P':
      if (t[0] == 'a') {//addPresentation
        type = 2;
      } else {//endProgram
        int temp2;
        for (temp2 = 0; temp2 < 12; temp2++) {
          Send(temp2, "E00");
        }
        return 0;
      }
      break;
    case 'C'://addConference
      type = 3;
      break;
    case 'D'://addDevice
      type = 0;
      break;
    case 'B'://addBatch
      break;
    case 'n'://PrintSchd
      isPrint = 1;
      type = -2;
      break;
  }

  i = 0;
  fgets(temp, 256 - 1, stream);
  int length = strlen(temp);


  if (temp[strlen(temp) - 1] != '\n') {
    temp[strlen(temp)] = '\0';
  } else
    temp[length - 1] = '\0';

  require[i++] = strtok(temp, " ;-�C");

  while (require[i++] = strtok(NULL, " ;\n\0"));

  if (type == -1) {//addBatch
    int stop = 0;
    FILE *f = fopen(require[0], "r");
    if (f != NULL) {
      while (!stop) {
        for (i = 0; i < 256; i++) {
          temp[i] = '\0';
        }
        fgets(temp, 256 - 1, f);

        if (temp[strlen(temp) - 1] != '\n') {
          temp[strlen(temp)] = '\0';
        } else
          temp[strlen(temp) - 1] = '\0';

        if (strlen(temp) == 0) {
          stop = 1;
        } else {
          FILE *f2 = fopen("inputModuleTemp.bat", "w");
          fseek(f2, 0, SEEK_SET);
          fwrite(temp, strlen(temp), 1, f2);
          fclose(f2);
          FILE *f3 = fopen("inputModuleTemp.bat", "r");
          coreInput(f3);
        }
      }
    } else printf("You might entered an empty line of command.\n");
  } else if (!isPrint) {//add: equipment, date, time, duration, tenant, equipment, equipment
    ConvertTime(require[1], require[2]);
    if (wrong)goto wrongEnd;
    ConvertDuration(require[3]);
    if (wrong)goto wrongEnd;
    ConvertTenant(require[4]);
    ConvertEquipment(require[0]);
    int ttemp;
    for (ttemp = 5; ttemp < 13; ttemp++) {
      if (require[ttemp] && strcmp(require[ttemp], "") != 0)
        ConvertEquipment(require[ttemp]);
    }
    AddRecord(para[0], para[1], para[2], para[3], para[4], para[5], para[6], para[7], para[8], para[9], para[10], para[11], para[12], para[13], para[14], type);

  } else {//print
    if (strcmp(require[0], "fcfs") == 0) {
      SchdFcfs(head);
      PrintSchd(head, 1);
    } else if (strcmp(require[0], "prio") == 0) {
      SchdPrio();
      PrintSchd(head, 2);
    } else if (strcmp(require[0], "opti") == 0) {
      SchdOpti();
    } else {
      errorInput();
      goto wrongEnd;
    }
  }
  if (isPrint)
    return 2;
  else
    return 1;
wrongEnd: {
  return 1;
  }
}

int WaitForBooking() {
  int i = 1;
  while (i) {
    printf("Please enter booking:\n");
    i = coreInput(stdin);
    if (!wrong) {
      if (i == 2)
        printf("-> [Done!]\n");
      else
        printf("-> [Pending]\n");
    }
    //else printf("\n");
  }
  return 0;
}