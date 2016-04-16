#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <malloc.h>
#include <sys/wait.h>

int main() {
  char temp1[256];
  for (int i = 0; i < 256; i++) temp1[i] = '\0';
  char temp2[256];
  fscanf(stdin, "%s", temp1);
  FILE *f = fopen("Fuck.bat", "r");
  for (int i = 0; i < 256; i++) temp2[i] = '\0';
  fgets(temp2, 256 - 1, f);
  printf("%d\n", strlen(temp1));
  printf("%d\n", strlen(temp2));
  fscanf(stdin, "%s", temp1);
  while (1) printf("\n");
  printf("End");
}