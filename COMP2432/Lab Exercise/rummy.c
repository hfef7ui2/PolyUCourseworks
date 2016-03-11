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
	int rJocker;
	int bJocker;
} RummyResult;

int convertSuitToNumber(const char c);
int convertRankToNumber(const char * const c);
void examRummy(const CardRecord* const record, const int numOfCard, RummyResult* const result);
int checkOverlap(const int* const record, RummyResult* const result, const int setForTest);
int findMax(const RummyResult* const results, const int numOfResult);

int main(int argc, char* argv[])
{
	int numOfCard = argc - 2;
	int numOfPlayer = (int) argv[1][0] - '0';
	int numOfCardOfPlayer = 0;
	int playerNumber;
	int cardUsed = 0;
    int isParent = 0;
    int* pidCount = (int*) malloc(numOfPlayer * sizeof(int));
    pid_t done;
	
	Card cardOfPlayer[54] = { { NULL, 0, 0 } };
	CardRecord cardRecord[17] = { { 0, { NULL } } };
	RummyResult testResult = { 0, { 0 }, 0, { 0 }, 0, -1, -1};//*

	int	pid, i, j, count;
    
    for (i = 0; i < numOfPlayer; i++) {
        pid = fork();
        if (pid < 0) {
            printf("Fork Failed\n");
            exit(1);
        } else if (pid == 0) {
            isParent = 0;
            playerNumber = i + 1;
            break;
        } else {
            pidCount[i] = pid;
            isParent = 1;
            continue;
        }
    }
    
    if (isParent) {
        for (i = 0; i < numOfPlayer; i++) {
            waitpid(pidCount[i], NULL, 0);
        }
        free(pidCount);
        exit(0);
    }

	pid = getpid();
	numOfCardOfPlayer = numOfCard / numOfPlayer * numOfPlayer + playerNumber - 1 < numOfCard ? numOfCard / numOfPlayer + 1 : numOfCard / numOfPlayer;
	printf("Child %d, pid %d: I have %d cards\n", playerNumber, pid, numOfCardOfPlayer);

	printf("Child %d, pid %d: ", playerNumber, pid);
	for (j = 0, i = playerNumber + 1; i < argc; i += numOfPlayer) {
		cardOfPlayer[j].name = argv[i];
		cardOfPlayer[j].suit = convertSuitToNumber(argv[i][0]);
		cardOfPlayer[j++].rank = convertRankToNumber(argv[i]);
		printf("%s ", argv[i]);
	}
	
	for (; cardUsed < numOfCardOfPlayer; cardUsed++) {
		cardRecord[cardOfPlayer[cardUsed].rank].card[cardOfPlayer[cardUsed].suit] = &cardOfPlayer[cardUsed];
		cardRecord[cardOfPlayer[cardUsed].rank].count++;
        examRummy(cardRecord, cardUsed + 1, &testResult);
        if (testResult.isRummy) {
            cardUsed++;
            break; 
        }
	}

	if (cardUsed > numOfCardOfPlayer) cardUsed--;

	for (i = 0; i < testResult.numOfSet; i++) {
		count = 0;
		printf("\nChild %d, pid %d: set <", playerNumber, pid);
		
		if (testResult.rJocker == testResult.set[i]) {
			testResult.rJocker = 0;
			printf("JJ ");
			cardRecord[16].card[0]->rank = -1;
			cardRecord[16].card[0] = NULL;
			count++;
		}

		if (testResult.bJocker == testResult.set[i]) {
			testResult.bJocker = 0;
			printf("jj ");
			cardRecord[15].card[0]->rank = -1;
			cardRecord[15].card[0] = NULL;
			count++;
		}

		for (j = 0; j <4 && count != 3; j++) {
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
		printf("\nChild %d, pid %d: run <", playerNumber, pid);

		for (j = 0; j < 4; j++) {

			if (testResult.rJocker == testResult.run[i] + 1) {
				testResult.rJocker = 0;
				printf("JJ ");
				cardRecord[16].card[0]->rank = -1;
				cardRecord[16].card[0] = NULL;
				break;
			}

			if (testResult.bJocker == testResult.run[i] + 1) {
				testResult.bJocker = 0;
				printf("jj ");
				cardRecord[15].card[0]->rank = -1;
				cardRecord[15].card[0] = NULL;
				break;
			}

			if (cardRecord[testResult.run[i] + 1].card[j] != NULL) {
				printf("%s ", cardRecord[testResult.run[i] + 1].card[j]->name);
				cardRecord[testResult.run[i] + 1].card[j]->rank = -1;
				cardRecord[testResult.run[i] + 1].card[j] = NULL;
				break;
			}
		}

		for (j = 0; j < 4; j++) {
			
			if (testResult.rJocker == testResult.run[i]) {
				testResult.rJocker = 0;
				printf("JJ ");
				cardRecord[16].card[0]->rank = -1;
				cardRecord[16].card[0] = NULL;
				break;
			}

			if (testResult.bJocker == testResult.run[i]) {
				testResult.bJocker = 0;
				printf("jj ");
				cardRecord[15].card[0]->rank = -1;
				cardRecord[15].card[0] = NULL;
				break;
			}

			if (cardRecord[testResult.run[i]].card[j] != NULL) {
				printf("%s ", cardRecord[testResult.run[i]].card[j]->name);
				cardRecord[testResult.run[i]].card[j]->rank = -1;
				cardRecord[testResult.run[i]].card[j] = NULL;
				break;
			}
		}

		for (j = 0; j < 4; j++) {

			if (testResult.rJocker == testResult.run[i] - 1) {
				testResult.rJocker = 0;
				printf("JJ ");
				cardRecord[16].card[0]->rank = -1;
				cardRecord[16].card[0] = NULL;
				break;
			}

			if (testResult.bJocker == testResult.run[i] - 1) {
				testResult.bJocker = 0;
				printf("jj ");
				cardRecord[15].card[0]->rank = -1;
				cardRecord[15].card[0] = NULL;
				break;
			}

			if (cardRecord[testResult.run[i] - 1 < 2 ? 14 : testResult.run[i] - 1].card[j] != NULL) {
				printf("%s ", cardRecord[testResult.run[i] - 1 < 2 ? 14 : testResult.run[i] - 1].card[j]->name);
				cardRecord[testResult.run[i] - 1 < 2 ? 14 : testResult.run[i] - 1].card[j]->rank = -1;
				cardRecord[testResult.run[i] - 1 < 2 ? 14 : testResult.run[i] - 1].card[j] = NULL;
				break;
			}
		}
		printf("\b>");
	}

	if (testResult.isRummy) {
		printf("\nChild %d, pid %d: rummy with %d cards, %d cards remaining", playerNumber, pid, cardUsed, cardUsed - 3 * testResult.numOfSet - 3 * testResult.numOfRun);
	}
	else {
		printf("\nChild %d, pid %d: cannot rummy with %d cards, %d cards remaining", playerNumber, pid, cardUsed, cardUsed - 3 * testResult.numOfSet - 3 * testResult.numOfRun);
	}
	count = 0;
	for (i = 0; i < 54; i++) {
		if (cardOfPlayer[i].rank >= 2) {
			printf(" %s", cardOfPlayer[i].name);
			count++;
		}
		if (count == cardUsed - testResult.numOfSet * 3 - testResult.numOfRun * 3) break;
	}
    printf("\n");

	exit(0);
}

int convertSuitToNumber(const char c) {
	switch (c) {
		case 'H':	return 1;
		case 'C':	return 2;
		case 'D':	return 3;
		default:	return 0;
	}
}

int convertRankToNumber(const char * const c) {
	if ((int)(c[1] - '0') >= 2 && (int)(c[1] - '0' <= 9)) {
		return (int)(c[1] - '0');
	} else {
		switch (c[1]) {
			case 'T':	return 10;
			case 'Q':	return 12;
			case 'K':	return 13;
			case 'A':	return 14;
			case 'j':	return 15;
			case 'J':
				if (c[0] == 'J')
					return 16;
				else return 11;
		}
	}
}



void examRummy(const CardRecord* const record, const int numOfCard, RummyResult* const result) {
	int i, j;
	int p, val, atteptedRunNumber, temp, m, n, numOfResult = 0;
	
	int bJockerNum = record[15].count;
	int rJockerNum = record[16].count;
	
	int runCopy[18] = {0};

	int tempRecord[17];
	int tempRecordBackUp[17];


	for (i = 0; i < 17; i++) {
		tempRecord[i] = tempRecordBackUp[i] = record[i].count;
	}

	RummyResult results[1014] = { { 0, { 0 }, 0, { 0 }, 0, 0, 0 } };

	for (m = 14; m >= 2; m--) {

		if (rJockerNum > 0) {
			tempRecord[m + 1]--;
			tempRecordBackUp[m + 1]--;
			tempRecord[m]++;
			tempRecordBackUp[m]++;
		} else m = 2;

		for (n = 14; n >= 2; n--) {

			for (i = 0; i < 15; i++) {
				tempRecord[i] = tempRecordBackUp[i];
			}

			p = 0;
			results[numOfResult].isRummy = 0;
			results[numOfResult].numOfSet = 0;
			results[numOfResult].numOfRun = 0;
			results[numOfResult].bJocker = 0;
			results[numOfResult].rJocker = 0;

			if (rJockerNum > 0) {
				results[numOfResult].rJocker = m;
			}

			if (bJockerNum > 0) {
				if (m < 14 && n == 14) {
					tempRecord[2]--;
					tempRecordBackUp[2]--;
				}
				tempRecord[n + 1]--;
				tempRecordBackUp[n + 1]--;
				tempRecord[n]++;
				tempRecordBackUp[n]++;
				results[numOfResult].bJocker = n;
			} else n = 2;

			for (i = 14; i >= 2 && results[numOfResult].numOfSet != 3; i--) {
				if (tempRecord[i] >= 3) {
                    tempRecord[i] -=3;
                    tempRecordBackUp[i] -=3;
					results[numOfResult].set[p++] = i;
					results[numOfResult].numOfSet++;
				}
			}
			for (j = 0; j < 3; j++) {
                
                if (j > 0) {
                    results[numOfResult].isRummy = 0;
                    results[numOfResult].numOfSet = results[numOfResult - 1].numOfSet;
                    results[numOfResult].bJocker = results[numOfResult - 1].bJocker;
                    results[numOfResult].rJocker = results[numOfResult - 1].rJocker;
                    memcpy(results[numOfResult].set, results[numOfResult - 1].set, sizeof(results[numOfResult - 1].set));
                }
                
				p = 0;
				results[numOfResult].numOfRun = 0;
				for (i = 0; i < 15; i++) {
					tempRecord[i] = tempRecordBackUp[i];
				}
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

                memcpy(runCopy, results[numOfResult].run, sizeof(results[numOfResult].run));
                for (i = 0; i < 15; i++) {
                    tempRecord[i] = tempRecordBackUp[i];
                }

                if (results[numOfResult].numOfSet == 3) {
                    results[numOfResult].isRummy = 1;
                    results[numOfResult].numOfRun = 0;
                    numOfResult++;
                    continue;
                }
                else if (results[numOfResult].numOfSet == 2) {
                    val = checkOverlap(tempRecord, &results[numOfResult], 2);
                    if (val == 1) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfRun = 1;
                        numOfResult++;
                        continue;
                    }

                    memcpy(results[numOfResult].run, runCopy, sizeof(runCopy));
                    val = checkOverlap(tempRecord, &results[numOfResult], 1);
                    if (val == 2) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfSet = 1;
                        results[numOfResult].numOfRun = 2;
                        numOfResult++;
                        continue;
                    }

                    memcpy(results[numOfResult].run, runCopy, sizeof(runCopy));
                    temp = results[numOfResult].set[0];
                    results[numOfResult].set[0] = results[numOfResult].set[1];
                    val = checkOverlap(tempRecord, &results[numOfResult], 1);
                    if (val == 2) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfSet = 1;
                        results[numOfResult].numOfRun = 2;
                        numOfResult++;
                        continue;
                    }
                    if (results[numOfResult].numOfRun >= 3) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfSet = 0;
                        results[numOfResult].numOfRun = 3;
                        numOfResult++;
                        continue;
                    }
                    results[numOfResult].set[0] = temp;
                    results[numOfResult].isRummy = 0;
                    results[numOfResult].numOfSet = 2;
                    results[numOfResult].numOfRun = 0;
                    numOfResult++;
                    continue;
                }
                else if (results[numOfResult].numOfSet == 1) {
                    val = checkOverlap(tempRecord, &results[numOfResult], 1);
                    if (val == 2) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfRun = 2;
                        numOfResult++;
                        continue;
                    }
                    else if (results[numOfResult].numOfRun >= 3) {
                        results[numOfResult].numOfSet = 0;
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfRun = 3;
                        numOfResult++;
                        continue;
                    }
                    else if (val == 1) {					
                        results[numOfResult].numOfRun = 1;
                        numOfResult++;
                        continue;
                    }
                    else if (results[numOfResult].numOfRun == 2) {
                        memcpy(results[numOfResult].run, runCopy, sizeof(runCopy));
                        results[numOfResult].numOfSet = 0;
                        results[numOfResult].numOfRun = 2;
                        numOfResult++;
                        continue;
                    }
                    else {
                        results[numOfResult].numOfSet = 1;
                        results[numOfResult].numOfRun = 0;
                        numOfResult++;
                        continue;
                    }
                }
                else {
                    if (results[numOfResult].numOfRun >= 3) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfRun = 3;
                        numOfResult++;
                        continue;
                    }
                    else {
                        numOfResult++;
                        continue;
                    }
                }
            }
		}
	}
    
    for (i = 0; i < 17; i++) {
		tempRecord[i] = tempRecordBackUp[i] = record[i].count;
	}
    
    for (m = 14; m >= 2; m--) {

		if (rJockerNum > 0) {
			tempRecord[m + 1]--;
			tempRecordBackUp[m + 1]--;
			tempRecord[m]++;
			tempRecordBackUp[m]++;
		} else m = 2;

		for (n = 14; n >= 2; n--) {

			for (i = 0; i < 15; i++) {
				tempRecord[i] = tempRecordBackUp[i];
			}

			results[numOfResult].isRummy = 0;
			results[numOfResult].numOfSet = 0;
			results[numOfResult].numOfRun = 0;
			results[numOfResult].bJocker = 0;
			results[numOfResult].rJocker = 0;

			if (rJockerNum > 0) {
				results[numOfResult].rJocker = m;
			}

			if (bJockerNum > 0) {
				if (m < 14 && n == 14) {
					tempRecord[2]--;
					tempRecordBackUp[2]--;
				}
				tempRecord[n + 1]--;
				tempRecordBackUp[n + 1]--;
				tempRecord[n]++;
				tempRecordBackUp[n]++;
				results[numOfResult].bJocker = n;
			} else n = 2;

			for (j = 0; j < 3; j++) {
                
                if (j > 0) {
                    results[numOfResult].isRummy = 0;
                    results[numOfResult].bJocker = results[numOfResult - 1].bJocker;
                    results[numOfResult].rJocker = results[numOfResult - 1].rJocker;
                }
                
				p = 0;
                results[numOfResult].numOfSet = 0;
				results[numOfResult].numOfRun = 0;
				for (i = 0; i < 15; i++) {
					tempRecord[i] = tempRecordBackUp[i];
				}
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

                memcpy(runCopy, results[numOfResult].run, sizeof(results[numOfResult].run));
                
                p = 0;
                
                for (i = 14; i >= 2 && results[numOfResult].numOfSet != 3; i--) {
				    if (tempRecord[i] >= 3) {
					   results[numOfResult].set[p++] = i;
					   results[numOfResult].numOfSet++;
				    }   
			    }
                
                for (i = 0; i < 15; i++) {
                    tempRecord[i] = tempRecordBackUp[i];
                }
                            
                if (results[numOfResult].numOfSet == 3) {
                    results[numOfResult].isRummy = 1;
                    results[numOfResult].numOfRun = 0;
                    numOfResult++;
                    continue;
                }
                else if (results[numOfResult].numOfSet == 2) {
                    val = checkOverlap(tempRecord, &results[numOfResult], 2);
                    if (val == 1) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfRun = 1;
                        numOfResult++;
                        continue;
                    }

                    memcpy(results[numOfResult].run, runCopy, sizeof(runCopy));
                    val = checkOverlap(tempRecord, &results[numOfResult], 1);
                    if (val == 2) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfSet = 1;
                        results[numOfResult].numOfRun = 2;
                        numOfResult++;
                        continue;
                    }

                    memcpy(results[numOfResult].run, runCopy, sizeof(runCopy));
                    temp = results[numOfResult].set[0];
                    results[numOfResult].set[0] = results[numOfResult].set[1];
                    val = checkOverlap(tempRecord, &results[numOfResult], 1);
                    if (val == 2) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfSet = 1;
                        results[numOfResult].numOfRun = 2;
                        numOfResult++;
                        continue;
                    }
                    if (results[numOfResult].numOfRun >= 3) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfSet = 0;
                        results[numOfResult].numOfRun = 3;
                        numOfResult++;
                        continue;
                    }
                    results[numOfResult].set[0] = temp;
                    results[numOfResult].isRummy = 0;
                    results[numOfResult].numOfSet = 2;
                    results[numOfResult].numOfRun = 0;
                    numOfResult++;
                    continue;
                }
                else if (results[numOfResult].numOfSet == 1) {
                    val = checkOverlap(tempRecord, &results[numOfResult], 1);
                    if (val == 2) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfRun = 2;
                        numOfResult++;
                        continue;
                    }
                    else if (results[numOfResult].numOfRun >= 3) {
                        results[numOfResult].numOfSet = 0;
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfRun = 3;
                        numOfResult++;
                        continue;
                    }
                    else if (val == 1) {					
                        results[numOfResult].numOfRun = 1;
                        numOfResult++;
                        continue;
                    }
                    else if (results[numOfResult].numOfRun == 2) {
                        memcpy(results[numOfResult].run, runCopy, sizeof(runCopy));
                        results[numOfResult].numOfSet = 0;
                        results[numOfResult].numOfRun = 2;
                        numOfResult++;
                        continue;
                    }
                    else {
                        results[numOfResult].numOfSet = 1;
                        results[numOfResult].numOfRun = 0;
                        numOfResult++;
                        continue;
                    }
                }
                else {
                    if (results[numOfResult].numOfRun >= 3) {
                        results[numOfResult].isRummy = 1;
                        results[numOfResult].numOfRun = 3;
                        numOfResult++;
                        continue;
                    }
                    else {
                        numOfResult++;
                        continue;
                    }
                }
            }
		}
	}

    
    
    
	int max = findMax(results, numOfResult);

	result->isRummy = results[max].isRummy;
	memcpy(result->set, results[max].set, sizeof(results[max].set));
	result->numOfSet = results[max].numOfSet;
	memcpy(result->run, results[max].run, sizeof(results[max].run));
	result->numOfRun = results[max].numOfRun;
	result->rJocker = results[max].rJocker;
	result->bJocker = results[max].bJocker;
}

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
			}
			else {
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

int checkOverlap(const int* const record, RummyResult* const result, const int setForTest) {
	int i, j, count = 0;

	for (i = 0; i < result->numOfRun && count != 3 - setForTest; i++, count++) {
		for (j = 0; j < setForTest; j++) {
            if (result->run[i] == 2) {
			    if (result->set[j] <= 3 || result->set[j] == 14) {
                   if (record[result->set[j]] <= 3) {
                        result->run[i] = -1;
			     		count--;
				    	break;
				    }
                }              
            } else {
			    if (result->set[j] <= result->run[i] + 1 && result->set[j] >= result->run[i] - 1) {
                   if (record[result->set[j]] <= 3) {
                        result->run[i] = -1;
			     		count--;
				    	break;
				    }
                }
			}
		}
	}
	return count;
}
