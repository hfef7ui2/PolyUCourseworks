// Assignment.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
void put(int hole, int count, int a, int* request);
void FindMax();

struct Record {
  int weight;
  int record[9];
};
static int size = 0;
struct Record record[10000000];


int main() {
  int hole = 600;

  int* request = new int[20];
  for (int i = 0; i < 20; i++) {
    request[i] = 0;
  }

  put(hole, 0, 31, request);
  put(hole, 0, 41, request);
  put(hole, 0, 51, request);
  FindMax();


  return 0;
}

void put(int hole, int count, int a, int* request) {

  if (0 >(hole - a)) {
    int weight = 0;
    for (int i = 0; i < 20; i++) {
      record[size].record[i] = request[i];
      weight += request[i];
    }
    delete[] request;
    record[size].weight = weight;
    size++;
    return;
  }

  int* requect_copy = new int[20];
  for (int i = 0; i < 20; i++)
    requect_copy[i] = request[i];

  delete[] request;

  hole -= a;
  requect_copy[count] = a;

  count++;

  put(hole, count, 31, requect_copy);
  put(hole, count, 41, requect_copy);
  put(hole, count, 51, requect_copy);
  return;
}

void FindMax() {
  int max = 0;
  int max_w = record[0].weight;
  for (int i = 0; i < 10000000; i++) {
    if(record[i].weight > max_w) {
      max = i;
      max_w = record[i].weight;
    }
  }
  printf("Max: %d :", max_w);
  for (int i = 0; i < 20; i++) {
    printf("%d ", record[max].record[i]);
  }
}

