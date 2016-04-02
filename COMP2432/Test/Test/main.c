#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <malloc.h>
#include <unistd.h>
#include <sys/wait.h>

typedef struct Card {
  char* name;
  int suit;
  int rank;
} Card;

typedef struct CardRecord {
  int count;
  Card* card[4];
} CardRecord;

typedef struct RummyResult {
  int isRummy;
  int set[3];
  int numOfSet;
  int run[18];
  int numOfRun;
} RummyResult;

void examSet(int* tempRecord, int* tempRecordBackUp, RummyResult* results, int numOfResult);
void examRun(int* tempRecord, RummyResult* results, int numOfResult, int j);
void decideRummy(RummyResult* results, int* numOfResult, int* tempRecord, int* runCopy);
int convertSuitToNumber(const char c);
int convertRankToNumber(const char c);
void examRummy(const CardRecord* const record, const int numOfCard, RummyResult* const resultCount);
int findMax(const RummyResult* const results, const int numOfResult);
Card* whichToDiscard(const CardRecord* const record);
int isMeld(const int card, const CardRecord* const record);
void discard(Card* card, CardRecord* record);
int goingToRummy(CardRecord* record);
Card* pickOneCard(int rank, const CardRecord* const record);
void printRummy(CardRecord* cardRecord, RummyResult testResult, int playerNumber);
int isVitualMeld(const int card, const CardRecord* const record);

int main(int argc, char* argv[]) {
  int numOfPlayer = (int)argv[1][0] - '0';
  int playerNumber;
  int cardUsed = 0;
  int isParent = 0;
  int* pidCount = (int*)malloc(numOfPlayer * sizeof(int));
  char feedback[4][3];
  int rummyPlayer = -1;

  RummyResult testResult = { 0,{ 0 }, 0,{ 0 }, 0 };
  char message[3];
  message[3] = 0;
  int isDiscarded = 0;
  Card* discardedCard;
  int readResult;
  int pre;
  int count;

  Card cardOfPlayer[54] = { NULL, 0, 0 };
  CardRecord cardRecord[17] = { { 0,{ NULL } } };

  printf("%s", cardOfPlayer[0].name);

  pid_t pid;
  int i, j, k;
  int parentToChild[2][2];
  int childToParent[2][2];
  int isGoingToRummy = 0;
  int meld;
  int current;

  for (i = 0; i < numOfPlayer; i++) {
    //create the pipe from parent to child
    if (pipe(parentToChild[i]) < 0) {
      printf("Pipe creation error\n");
      exit(1);
    }
    //create the pipe from child to parent
    if (pipe(childToParent[i]) < 0) {
      printf("Pipe creation error\n");
      exit(1);
    }

    fflush(0);
    pid = fork();

    if (pid == 0) {
      close(parentToChild[i][1]);
      close(childToParent[i][0]);
      isParent = 0;
      playerNumber = i;
      break;
    } else {
      //close one port of the pipe
      close(parentToChild[i][0]);
      close(childToParent[i][1]);
      pidCount[i] = pid;
      isParent = 1;
    }
  }

  //parent process
  if (isParent) {
    //print out all children firstly
    printf("Parent: the child players are ");
    for (i = 0; i < numOfPlayer; i++) {
      printf("%d ", pidCount[i]);
    }
    printf("\n");
    i = 0;

    //loop to continue sending cards to children
    while (1) {
      current = i % numOfPlayer;
      pre = (current == 0 ? numOfPlayer - 1 : current - 1);

      //if last child discarded a card, parent do not need to read a new card
      //else parent read a new card
      if (isDiscarded == 0) {
        if (scanf("%s", &message[1]) == EOF) break;
        //if the card is a new card, mark it as 'N' and child can not skip it
        message[0] = 'N';
      }

      //if it is a new card, parent anounce the deal
      if (message[0] != 'D') {
        if (i + 1 > numOfPlayer * 8)
          printf("Parent: dealing %s to child %d\n", &message[1], i % numOfPlayer + 1);
        write(parentToChild[i % numOfPlayer][1], message, 3 * sizeof(char));//send the card to next child
      } else {//if it is a discarded card, parent send it to all other children exept the one who discarded this card
        for (j = 0; j < numOfPlayer; j++) {
          if (j == pre) continue;
          write(parentToChild[j][1], message, 3 * sizeof(char));
          fflush(0);
          read(childToParent[j][0], feedback[j], 3);//parent record the response of each child
        }

        //if any child delare rummy, his or her rummy will be fulfilled
        //if more than one child delare runmy, the one at front in the order will be fulfilled
        //i.e. child discard a card and child 4 and child 1 both declare rummy
        //then child 4 would get the rummy card
        for (j = current;; j++) {
          if (j == numOfPlayer) j = 0;
          if (j == pre) break;
          if (feedback[j][0] == 'R') {//request for rummy
            if (j == current) message[0] = 'Y';//accept the seize rummy request
            else message[0] = 'V';//accept the normal rummy request
            write(parentToChild[j][1], message, 3 * sizeof(char));
            message[0] = 'X';//reject other request
            for (k = 0; k < numOfPlayer; k++) {
              if (k == j || k == pre) continue;
              write(parentToChild[k][1], message, 3 * sizeof(char));
            }
            i = j;
            goto out;
          }
        }
        //else if any child can use this card to make a set, his or her request for this card would be fulfilled
        //but he or she need to show the set
        for (j = 0; j < numOfPlayer; j++) {
          if (j == pre) continue;
          if (feedback[j][0] == 'T') {//set request
            if (j == current) message[0] = 'Y';//accept the seize for set request
            else message[0] = 'V';//accept normal set request
            write(parentToChild[j][1], message, 3 * sizeof(char));
            message[0] = 'X';//reject other request
            for (k = 0; k < numOfPlayer; k++) {
              if (k == j || k == pre) continue;
              write(parentToChild[k][1], message, 3 * sizeof(char));
            }
            i = j;
            goto out;
          }
        }
        //if not the two situations aboce, then just deal card to next child
        message[0] = 'Y';//accept normal request of next child
        write(parentToChild[current][1], message, 3 * sizeof(char));
        message[0] = 'X';//reject other request
        for (k = 0; k < numOfPlayer; k++) {
          if (k == current || k == pre) continue;
          write(parentToChild[k][1], message, 3 * sizeof(char));
        }
      }
    out:
      for (k = 0; k < numOfPlayer; k++) {
        feedback[k][0] = '0';
      }


      readResult = read(childToParent[i % numOfPlayer][0], message, 3);
      if (message[0] == 'R') {//if the child is rummy
        rummyPlayer = i % numOfPlayer;
        printf("Parent: child %d rummy\n", i % numOfPlayer + 1);
        break;
      } else if (message[0] == 'S') {//if the child ask for skip
        i--;
        isDiscarded = 0;
      } else if (message[0] == 'D') {//if the child take the card and returned a discarded card
        printf("Parent: child %d discards %s\n", i % numOfPlayer + 1, &message[1]);
        isDiscarded = 1;
      }
      i++;
    }

    if (message[0] != 'R')//if the loop ends and no one rummy
      printf("Parent: no more card, it is a draw\n");

    //tell all remaining children that the game is over and print their cards at hand
    for (i = 0; i < numOfPlayer; i++) {
      if (i == rummyPlayer) continue;
      message[0] = 'F';
      write(parentToChild[i][1], message, 3);
    }

    //collect the children
    for (i = 0; i < numOfPlayer; i++) {
      waitpid(pidCount[i], NULL, 0);
    }
    free(pidCount);
    exit(0);
  }

  //child process
  while (1) {
    fflush(0);
    readResult = read(parentToChild[playerNumber][0], message, 3 * sizeof(char));

    //if the parent said the game is over
    if (message[0] == 'F') {
      printf("Child %d: ", playerNumber + 1);
      for (i = 14; i >= 2; i--) {
        for (j = 0; j < 4; j++) {
          if (cardRecord[i].card[j] != NULL)  printf("%s ", cardRecord[i].card[j]->name);
        }
      }
      printf("\n");
      break;
    }

    //record the new card
    cardOfPlayer[cardUsed].name = (char*)malloc(2 * sizeof(char));
    strncpy(cardOfPlayer[cardUsed].name, &message[1], 2);
    cardOfPlayer[cardUsed].suit = convertSuitToNumber(message[1]);
    cardOfPlayer[cardUsed].rank = convertRankToNumber(message[2]);
    cardRecord[cardOfPlayer[cardUsed].rank].card[cardOfPlayer[cardUsed].suit] = &cardOfPlayer[cardUsed];
    cardRecord[cardOfPlayer[cardUsed].rank].count++;

    //if the child has 8 cards, just print the cards at hand at once
    if (cardUsed + 1 == 8) {
      printf("Child %d, pid %d: ", playerNumber + 1, getpid());
      for (i = 0; i < 8; i++) {
        printf("%s ", cardOfPlayer[i].name);
      }
      printf("\n");
      isGoingToRummy = goingToRummy(cardRecord);
    }

    if (cardUsed + 1 >= 9) {
      meld = isMeld(cardOfPlayer[cardUsed].rank, cardRecord);
      examRummy(cardRecord, cardUsed, &testResult);

      if (testResult.isRummy) {//if the new card leads to rummy
                               //if it is a discarded card, child need send his rummy request to parents, as other children might also request this card
        if (message[0] == 'D') {
          message[0] = 'R';//request for rummy
          write(childToParent[playerNumber][1], message, sizeof(message));
          readResult = read(parentToChild[playerNumber][0], message, 3 * sizeof(char));
          if (message[0] == 'X') {//if the request is rejected by parent, then the child give up this card
            discard(&cardOfPlayer[cardUsed], cardRecord);
            continue;
          }
        }

        //if seize for rummy request was accepted
        if (message[0] == 'V') printf("Child %d: take %s (seize for rummy)\n", playerNumber + 1, &message[1]);
        else printf("Child %d: take %s\n", playerNumber + 1, &message[1]);

        printf("Child %d: rummy\n", playerNumber + 1);
        printRummy(cardRecord, testResult, playerNumber);

        message[0] = 'R';//confirm rummy
        write(childToParent[playerNumber][1], message, sizeof(message));
        break;
      } else if (message[0] == 'D' && (isGoingToRummy || meld == 0)) {//if the card is a discarded card which is discarded by last player and it meets the condition for skip
        message[0] = 'S';//skip request
                         //as it is a discarded card, child need firstly send request for skip
        write(childToParent[playerNumber][1], message, sizeof(message));
        fflush(0);
        readResult = read(parentToChild[playerNumber][0], message, 3 * sizeof(char));

        //if the skip request is rejected, which means the card has been dealed to other child, then current child does not need to skip this card
        if (message[0] == 'X') {
          discard(&cardOfPlayer[cardUsed], cardRecord);
          continue;
        }

        printf("Child %d£ºskip %s\n", playerNumber + 1, &message[1]);
        discard(&cardOfPlayer[cardUsed], cardRecord);
        message[0] = 'S';//confirm skip
        write(childToParent[playerNumber][1], message, sizeof(message));
        continue;
      } else {//take the card
              //if it is a discarded card, the child need firstly send take request to parent
        if (message[0] == 'D') {
          if (meld == 2) message[0] = 'T';//T means it is a take for set
          else message[0] = 'U';//U means it is a take for run
          write(childToParent[playerNumber][1], message, sizeof(message));
          fflush(0);
          readResult = read(parentToChild[playerNumber][0], message, 3 * sizeof(char));
          if (message[0] == 'X') {//if the request is rejected, the child need to give up this card
            discard(&cardOfPlayer[cardUsed], cardRecord);
            continue;
          }
        }

        if (message[0] == 'V') {//if this card is seized from other child, current child need to print the set to show the proof
          printf("Child %d: take %s (seize for set", playerNumber + 1, &message[1]);
          printf(" <");
          for (j = 0; j < 4 && count != 3; j++) {
            if (cardRecord[cardOfPlayer[cardUsed].rank].card[j] != NULL) {
              printf("%s ", cardRecord[cardOfPlayer[cardUsed].rank].card[j]->name);
              count++;
            }
          }
          printf("\b>)\n");
        } else printf("Child %d: take %s\n", playerNumber + 1, &message[1]);
        if (isGoingToRummy) {
          discardedCard = &cardOfPlayer[cardUsed];
        } else discardedCard = whichToDiscard(cardRecord);
        printf("Child %d: discard %s\n", playerNumber + 1, discardedCard->name);
        message[0] = 'D';
        memcpy(&message[1], discardedCard->name, 2);
        write(childToParent[playerNumber][1], message, sizeof(message));
        discard(discardedCard, cardRecord);
        isGoingToRummy = goingToRummy(cardRecord);
        cardUsed++;
        continue;
      }
    }
    cardUsed++;
    message[0] = 'T';//default take response
    write(childToParent[playerNumber][1], message, 3 * sizeof(char));
  }
  exit(0);
}

//a virtual means that a meld that can be observed, but it might be overlapped with larger meld
int isVitualMeld(const int card, const CardRecord* const record) {
  int prepre = card == 3 ? record[14].count : record[card - 2].count;
  int pre = card == 2 ? record[14].count : record[card - 1].count;
  int cur = record[card].count;
  int nex = record[card + 1].count;
  int nexnex = record[card + 2].count;

  if (cur == 0) return 0;
  if (cur >= 3) return 1;
  if ((pre >= cur && nex >= cur) || (card > 2 && (prepre >= cur && pre >= cur)) || (card < 13 && (nex >= cur && nexnex >= cur))) {
    return 1;
  } else {
    return 0;
  }
}

//a meld is a real meld which would not be overlapped with larger meld
//return 2 if it is a set; 1 if it is a run
int isMeld(const int card, const CardRecord* const record) {
  if (record[card].count == 3) return 2;
  int prepre = card == 3 ? record[14].count : record[card - 2].count;
  int pre = card == 2 ? record[14].count : record[card - 1].count;
  int cur = record[card].count;
  int nex = record[card + 1].count;
  int nexnex = record[card + 2].count;
  int i;

  RummyResult preResult, curResult;
  CardRecord tempRecord[15];
  for (i = 0; i < 15; i++) {
    tempRecord[i].count = record[i].count;
  }
  examRummy(record, 9, &curResult);
  tempRecord[card].count--;
  examRummy(tempRecord, 8, &preResult);

  if (curResult.numOfSet == preResult.numOfSet) {
    if (curResult.numOfSet > 0) {
      for (i = 0; i < curResult.numOfSet; i++) {
        if (curResult.set[i] != preResult.set[i]) return 2;
      }
    }
  } else return 2;

  if (curResult.numOfRun == preResult.numOfRun) {
    if (curResult.numOfRun > 0) {
      for (i = 0; i < curResult.numOfRun; i++) {
        if (curResult.run[i] != preResult.run[i]) return 1;
      }
    }
  } else return 1;

  if ((pre >= cur && nex >= cur) || (card > 2 && (prepre >= cur && pre >= cur)) || (card < 13 && (nex >= cur && nexnex >= cur))) {
    return 1;
  } else {
    return 0;
  }
}

int convertSuitToNumber(const char c) {
  switch (c) {
  case 'H':	return 2;
  case 'C':	return 1;
  case 'D':	return 0;
  default:	return 3;
  }
}

int convertRankToNumber(const char c) {
  if ((int)(c - '0') >= 2 && (int)(c - '0' <= 9)) {
    return (int)(c - '0');
  } else {
    switch (c) {
    case 'T':	return 10;
    case 'Q':	return 12;
    case 'K':	return 13;
    case 'A':	return 14;
    default:   return 11;
    }
  }
}

//exam whether given hand is rummy or not
void examRummy(const CardRecord* const record, const int numOfCard, RummyResult* const result) {
  int i, j, p, val, temp, m, n, numOfResult = 0;

  int runCopy[18] = { 0 };

  int tempRecord[15];
  int tempRecordBackUp[15];

  for (i = 0; i < 15; i++) {
    tempRecord[i] = tempRecordBackUp[i] = record[i].count;
  }

  RummyResult results[6] = { { 0,{ 0 }, 0,{ 0 }, 0 } };

  examSet(tempRecord, tempRecordBackUp, results, numOfResult);

  for (j = 0; j < 3; j++) {

    if (j > 0) {
      results[numOfResult].numOfSet = results[numOfResult - 1].numOfSet;
      memcpy(results[numOfResult].set, results[numOfResult - 1].set, sizeof(results[numOfResult - 1].set));
    }

    for (i = 0; i < 15; i++) {
      tempRecord[i] = tempRecordBackUp[i];
    }

    examRun(tempRecord, results, numOfResult, j);

    memcpy(runCopy, results[numOfResult].run, sizeof(results[numOfResult].run));

    for (i = 0; i < 15; i++) {
      tempRecord[i] = record[i].count;
    }

    if ((results[numOfResult].numOfSet + results[numOfResult].numOfRun) == 3) results[numOfResult].isRummy = 1;
    numOfResult++;
  }

  for (j = 0; j < 3; j++) {

    for (i = 0; i < 15; i++) {
      tempRecord[i] = tempRecordBackUp[i] = record[i].count;
    }

    examRun(tempRecord, results, numOfResult, j);

    memcpy(runCopy, results[numOfResult].run, sizeof(results[numOfResult].run));

    examSet(tempRecord, tempRecordBackUp, results, numOfResult);

    for (i = 0; i < 15; i++) {
      tempRecord[i] = record[i].count;
    }

    if ((results[numOfResult].numOfSet + results[numOfResult].numOfRun) == 3) results[numOfResult].isRummy = 1;
    numOfResult++;
  }

  int max = findMax(results, numOfResult);

  result->isRummy = results[max].isRummy;
  memcpy(result->set, results[max].set, sizeof(results[max].set));
  result->numOfSet = results[max].numOfSet;
  memcpy(result->run, results[max].run, sizeof(results[max].run));
  result->numOfRun = results[max].numOfRun;
}

//exam runs in the hand
void examRun(int* tempRecord, RummyResult* results, int numOfResult, int j) {
  int i, p = 0;
  for (i = 13 - j; i >= 2; i--) {
    if (tempRecord[i] >= 1 && tempRecord[i + 1] >= 1 && (i == 2 ? tempRecord[14] >= 1 : tempRecord[i - 1] >= 1)) {
      tempRecord[i]--;
      tempRecord[i + 1]--;
      tempRecord[i == 2 ? 14 : i - 1]--;
      results[numOfResult].run[p++] = i;
      results[numOfResult].numOfRun++;
      i++;
    }
  }
}

//exam sets in the hand
void examSet(int* tempRecord, int* tempRecordBackUp, RummyResult* results, int numOfResult) {
  int i, p = 0;
  for (i = 14; i >= 2 && results[numOfResult].numOfSet != 3; i--) {
    if (tempRecord[i] >= 3) {
      tempRecord[i] -= 3;
      tempRecordBackUp[i] -= 3;
      results[numOfResult].set[p++] = i;
      results[numOfResult].numOfSet++;
    }
  }
}

//find the best rummy option in all possible options
int findMax(const RummyResult* const results, const int numOfResult) {
  int i, max = 0, j;
  for (i = 1; i < numOfResult; i++) {
    if (results[i].numOfSet + results[i].numOfRun > results[max].numOfSet + results[max].numOfRun) max = i;
    else if (results[i].numOfSet + results[i].numOfRun < results[max].numOfSet + results[max].numOfRun) continue;
    else {
      if (results[i].numOfSet > 0 && results[max].numOfSet == 0) max = i;
      else if (results[i].numOfSet == 0 && results[max].numOfSet > 0) continue;
      else if (results[i].numOfSet == 0) {
        for (j = 0; j < results[i].numOfRun; j++) {
          if (results[i].run[j] > results[max].run[j]) max = i;
          else if (results[i].run[j] < results[max].run[j]) break;
        }
      } else {
        for (j = 0; j < results[i].numOfSet && j < results[max].numOfSet; j++) {
          if (results[i].set[j] > results[max].set[j]) max = i;
          else if (results[i].set[j] < results[max].set[j]) break;
        }
        if (results[i].numOfSet > results[max].numOfSet) max = i;
        else if (results[i].numOfSet < results[max].numOfSet) continue;
        else {
          for (j = 0; j < results[i].numOfRun; j++) {
            if (results[i].run[j] > results[max].run[j]) max = i;
            else if (results[i].run[j] < results[max].run[j]) break;
          }
        }
      }
    }
  }
  return max;
}

//choose which card to discard
Card* whichToDiscard(const CardRecord* const record) {
  int results[15] = { 0 };
  int i, j;
  CardRecord tempRecord[15];
  RummyResult result;
  int max = 0;

  for (i = 2; i < 15; i++) {
    for (j = 0; j < 15; j++) {
      tempRecord[j].count = record[j].count;
    }
    if (tempRecord[i].count > 0) {
      tempRecord[i].count--;
    } else continue;
    for (j = 2; j < 15; j++) {
      result.numOfRun = 0;
      result.numOfSet = 0;
      result.isRummy = 0;
      tempRecord[j - 1].count--;
      tempRecord[j].count++;
      examRummy(tempRecord, 9, &result);
      if (result.isRummy) {
        results[i]++;
      }
    }
  }

  for (i = 2; i < 15; i++) {
    if (results[i] > results[max]) max = i;
  }
  if (max >= 2) return pickOneCard(max, record);
  else {
    //implementation 2 starts
    for (j = 0; j < 15; j++) {
      tempRecord[j].count = record[j].count;
    }
    for (i = 2; i < 15; i++) {
      if (tempRecord[i].count > 0 && !isVitualMeld
        (i, tempRecord)) {
        return pickOneCard(i, record);
      }
    }
    //implementation 2 ends

    //implementation 1 stars
    for (j = 0; j < 15; j++) {
      tempRecord[j].count = record[j].count;
    }
    examRummy(tempRecord, 9, &result);
    if (result.numOfSet > 0)
      for (i = 0; i < result.numOfSet; i++) {
        tempRecord[result.set[i]].count -= 3;
      }
    if (result.numOfRun > 0) {
      for (i = 0; i < result.numOfRun; i++) {
        tempRecord[result.run[i]].count--;
        tempRecord[result.run[i] + 1].count--;
        tempRecord[result.run[i] == 2 ? 14 : result.run[i] - 1].count--;
      }
    }
    for (i = 2; i < 15; i++) {
      if (tempRecord[i].count > 0) {
        return pickOneCard(i, record);
      }
    }
    //implementation 1 ends
  }
  return NULL;
}

//discard a card
void discard(Card* card, CardRecord* record) {
  record[card->rank].card[card->suit] = NULL;
  record[card->rank].count--;
  card->name = NULL;
  card->suit = 0;
  card->rank = 0;
}

//pick on card that has the given rank
Card* pickOneCard(int rank, const CardRecord* const record) {
  int  i;
  for (i = 0; i < 4; i++) {
    if (record[rank].card[i] != NULL) return record[rank].card[i];
  }
  return NULL;
}

//check whether the condition is going to rummy
int goingToRummy(CardRecord* record) {
  CardRecord tempRecord[15];
  int i;
  RummyResult result;
  for (i = 2; i < 15; i++) {
    tempRecord[i].count = record[i].count;
  }
  for (i = 14; i >= 2; i--) {
    tempRecord[i].count++;
    if (i < 14) tempRecord[i + 1].count--;
    examRummy(tempRecord, 9, &result);
    if (result.isRummy) {
      return 1;
    }
  }
  return 0;
}

//print all melds in hand
void printRummy(CardRecord* cardRecord, RummyResult testResult, int playerNumber) {
  int i, j, count;
  printf("Child %d:", playerNumber + 1);
  for (i = 0; i < testResult.numOfSet; i++) {
    count = 0;
    printf(" <");
    for (j = 0; j < 4 && count != 3; j++) {
      if (cardRecord[testResult.set[i]].card[j] != NULL) {
        printf("%s ", cardRecord[testResult.set[i]].card[j]->name);
        cardRecord[testResult.set[i]].card[j]->rank = -1;
        cardRecord[testResult.set[i]].card[j] = NULL;
        count++;
      }
    }
    printf("\b>");
  }

  count = 0;
  for (i = 0; i < 18 && count != testResult.numOfRun; i++) {
    if (testResult.run[i] < 2) continue;
    count++;
    printf(" <");

    for (j = 0; j < 4; j++) {
      if (cardRecord[testResult.run[i] + 1].card[j] != NULL) {
        printf("%s ", cardRecord[testResult.run[i] + 1].card[j]->name);
        cardRecord[testResult.run[i] + 1].card[j]->rank = -1;
        cardRecord[testResult.run[i] + 1].card[j] = NULL;
        break;
      }
    }

    for (j = 0; j < 4; j++) {
      if (cardRecord[testResult.run[i]].card[j] != NULL) {
        printf("%s ", cardRecord[testResult.run[i]].card[j]->name);
        cardRecord[testResult.run[i]].card[j]->rank = -1;
        cardRecord[testResult.run[i]].card[j] = NULL;
        break;
      }
    }

    for (j = 0; j < 4; j++) {
      if (cardRecord[testResult.run[i] - 1 < 2 ? 14 : testResult.run[i] - 1].card[j] != NULL) {
        printf("%s ", cardRecord[testResult.run[i] - 1 < 2 ? 14 : testResult.run[i] - 1].card[j]->name);
        cardRecord[testResult.run[i] - 1 < 2 ? 14 : testResult.run[i] - 1].card[j]->rank = -1;
        cardRecord[testResult.run[i] - 1 < 2 ? 14 : testResult.run[i] - 1].card[j] = NULL;
        break;
      }
    }
    printf("\b>");
  }
  printf("\n");
}
